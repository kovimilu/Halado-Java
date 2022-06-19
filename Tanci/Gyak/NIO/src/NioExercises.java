import static java.nio.file.StandardOpenOption.READ;
import static java.util.Map.entry;
import static java.util.stream.IntStream.range;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public class NioExercises {
	static ByteBuffer bb = ByteBuffer.allocate(1);

	// java NioExercises src/NioExercises.java 9 97 10 118 100 1
	public static void main(String[] args) throws IOException {
		var fakeArgs = "src/NioExercises.java 9 97 10 118 100 1".split(" ");

		var filename = fakeArgs[0];

//		Arrays.stream(args).skip(1).   folytatás?????
		
		try (
			var fc = FileChannel.open(Path.of(filename), READ);
		) {
			range(0, (fakeArgs.length-1)/2)
				.mapToObj(i -> entry(Long.parseLong(fakeArgs[1+2*i]), Byte.parseByte(fakeArgs[2+2*i])))
				.map(posExpected -> entry(posExpected, getByteAtPos(fc, posExpected.getKey())))
				.filter(posExpectedResult -> posExpectedResult.getKey().getValue() != posExpectedResult.getValue())
				.forEach(System.out::println);
		}
	}

	private static byte getByteAtPos(FileChannel fc, Long pos) {
		try {
			fc.position(pos);
			bb.rewind();
			fc.read(bb);
			bb.flip();
			return bb.get();
		} catch (Exception e) {
			// won't happen
			return -1;
		}
	}
}
