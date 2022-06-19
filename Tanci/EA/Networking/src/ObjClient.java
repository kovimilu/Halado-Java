import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class ObjClient {
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
//		var HOST = "localhost";
		var HOST = "127.0.0.1";
		final int PORT = 12345;

		try (
			var s = new Socket(HOST, PORT);
			var oos = new ObjectOutputStream(s.getOutputStream());
			var ois = new ObjectInputStream(s.getInputStream());
		) {
			var someList = List.of(1, 22, 35423, 135);
			var x = new X();
			x.x = 2435;
			oos.writeObject(someList);
			oos.writeObject(x);
			oos.flush();

			Integer sum = (Integer)ois.readObject();
			System.out.println(sum);

			X xResult = (X)ois.readObject();
			System.out.println(x.x);
			System.out.println(xResult.x);
		}
	}
}
