import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NioReadFromFile {
	public static void main(String[] args) throws Exception {
		endianness();
		for (var cs : Charset.availableCharsets().entrySet()) {
			System.out.println(cs);
		}
	}

	public static void main2(String[] args) throws Exception {
		byteBufferIntro();
		
		String sep = File.separator;

		byte[] illustration = {1, 2};
		ByteBuffer wrapped = ByteBuffer.wrap(illustration);
		
		try (
			var fc = FileChannel.open(Path.of("src", "UsualIO.java"),
				StandardOpenOption.READ);
//				var fc2 = new FileInputStream(new File("src" + sep + "UsualIO.java"))
//				.getChannel();
		) {
			// capacity
			// limit
			// position
			// mark
			var bb = ByteBuffer.allocate(4);
			fc.read(bb);
			Files.copy()
			System.out.println(bb);

			bb.flip();
//			char result = (char)bb.get();
//			System.out.println(result);

			// Java bels� k�dol�sa: UCS2
			// f�jlok "�tlag" k�dol�sa: UTF8 / ASCII
			char result = bb.getChar();
			System.out.println(result);
			System.out.println((int)'i');
			System.out.printf("%02x%02x%n", (int)'i', (int)'m');
			System.out.printf("%d%n", (int)result);
			System.out.printf("%02x%n", (int)result);
		}

		
	}

	private static void byteBufferIntro() {
		var bb = ByteBuffer.allocate(4);
		System.out.println(bb);

		bb.putChar('x');
		System.out.println(bb);
		bb.mark();
		bb.putChar('y');
		System.out.println(bb);
		bb.reset();
		System.out.println(bb);
		bb.rewind();
		System.out.println(bb);

		// write/read
		bb.flip();
	}


	private static void endianness() {
		var bb = ByteBuffer.allocate(4);
		System.out.printf("%08x%n", 12345);
		System.out.printf("%08x%n", 0x89_AB_CD_EF);
		System.out.printf("%d%n", 0x89ABCDEF);

		// LSB/MSB: least/most significant byte

		int original = 0x12345678;
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.putInt(original);
		System.out.printf("%08x%n", original);
		
		// nybble
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.flip();
		int result = bb.getInt();
		System.out.println(result);
		System.out.printf("%08x%n", result);
	}
}
