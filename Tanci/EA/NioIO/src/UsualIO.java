import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class UsualIO {
	public static void main(String[] args) throws Exception {
		// Reader/Writer
		// InputStream/OutputStream

		String sep = File.separator;

		try (
			var br = new BufferedReader(new FileReader(new File("src" + sep + "UsualIO.java")));
		) {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
}
