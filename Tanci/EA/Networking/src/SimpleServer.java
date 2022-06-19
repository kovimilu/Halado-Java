import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SimpleServer {
	static int counter = 0;
	
	public static void main(String[] args) throws IOException {
		// 0-65535 : 0-1023-49152-...
		final int PORT = 12345;
		
		// protokoll
		try (
			var ss = new ServerSocket(PORT);
		) {
			// diszpécserszál
			while (true) {
				++counter;

				// full duplex
				Socket s = ss.accept();
				var pw = new PrintWriter(s.getOutputStream());
				var sc = new Scanner(s.getInputStream());

				new Thread(() -> {
					try (s; sc; pw) {
						String txt = sc.nextLine();
						System.out.println(txt);

						pw.println(txt + counter);
						pw.flush();
					} catch (Exception e) {}
				}).start();
			}
		}
	}
}
