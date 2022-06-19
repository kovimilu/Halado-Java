import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SimpleClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
//		var HOST = "localhost";
		var HOST = "127.0.0.1";
		final int PORT = 12345;

		try (
			var s = new Socket(HOST, PORT);
			var sc = new Scanner(s.getInputStream());
			var pw = new PrintWriter(s.getOutputStream());
		) {
			pw.println("dschjfkds");
			pw.flush();

			String txt = sc.nextLine();
			System.out.println(txt);
		}
	}
}
