import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.List;

public class ObjServer {
	static int counter = 0;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// 0-65535 : 0-1023-49152-...
		final int PORT = 12345;
		
		// protokoll
		try (
			var ss = new ServerSocket(PORT);
			var s = ss.accept();
			var ois = new ObjectInputStream(s.getInputStream());
			var oos = new ObjectOutputStream(s.getOutputStream());
		) {
			List<Integer> elems = (List)ois.readObject();
			System.out.println(elems);

			X x = (X)ois.readObject();
			x.x = 7864;
			
			oos.writeObject(elems.parallelStream().mapToInt(i->i).sum());
			oos.writeObject(x);
			oos.flush();
		}
	}
}
