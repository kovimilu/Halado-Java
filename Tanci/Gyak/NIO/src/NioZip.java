import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class NioZip {
	static int expectedSignature = 0x04034b50;

	public static void main(String[] args) throws IOException {
		int currHdrOffset = 0;

		try (var fc = FileChannel.open(Path.of("sample.zip"))) {
			while (currHdrOffset < fc.size()) {
				Optional<Integer> result = processHdr2(fc, currHdrOffset);
				if (result.isEmpty())    break;
				currHdrOffset += result.get();
			}
		}
	}

	private static Optional<Integer> processHdr2(FileChannel fc, int currHdrOffset) throws IOException {
		var bb = ByteBuffer.allocate(ZipHeaderComponent.END.getOffset()).order(ByteOrder.LITTLE_ENDIAN);
		fc.position(currHdrOffset);
		fc.read(bb);

		int signature = bb.getInt(ZipHeaderComponent.LOCAL_FILE_HEADER_SIGNATURE.getOffset());
		short compressionMethod = bb.getShort(ZipHeaderComponent.COMPRESSION_METHOD.getOffset());
		int compressedSize = bb.getInt(ZipHeaderComponent.COMPRESSED_SIZE.getOffset());
		int uncompressedSize = bb.getInt(ZipHeaderComponent.UNCOMPRESSED_SIZE.getOffset());

		short filenameLength = bb.getShort(ZipHeaderComponent.FILE_NAME_LENGTH.getOffsetSimple());
		short extraFieldLength = bb.getShort(OffsetUtil.getOffsetV2(ZipHeaderComponent.EXTRA_FIELD_LENGTH, ZipHeaderComponent::getLen));

		System.out.printf("%08x == %08x = %b%n", signature, expectedSignature, signature == expectedSignature);
		System.out.println(compressionMethod);
		System.out.println(compressedSize + " " + uncompressedSize);
		System.out.println(filenameLength);
		System.out.println(extraFieldLength);

		if (compressionMethod != 0)    return Optional.empty();

		if (filenameLength > 0) {
			var bbFilename = ByteBuffer.allocate(filenameLength);
			fc.position(currHdrOffset + ZipHeaderComponent.END.getOffset());
			fc.read(bbFilename);
			var filename = new String(bbFilename.array());

			System.out.println("filename: " + filename);

			writeFile(fc, currHdrOffset, filename, filenameLength, extraFieldLength, compressedSize);
		}
		
		return Optional.of(ZipHeaderComponent.END.getOffset() + filenameLength + extraFieldLength + compressedSize);
	}

	private static Optional<Integer> processHdr(FileChannel fc, int currHdrOffset) throws IOException {
		var bb4 = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		var bb2 = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);

		fc.position(currHdrOffset + ZipHeaderComponent.LOCAL_FILE_HEADER_SIGNATURE.getOffset());
		fc.read(bb4);
		int signature = bb4.getInt(0);
		System.out.printf("%08x == %08x = %b%n", signature, expectedSignature, signature == expectedSignature);

//		fc.position(currHdrOffset + 8);
//		fc.position(currHdrOffset + 4 + 2 + 2);
//		fc.position(currHdrOffset +
//						ZipHeaderComponent.LOCAL_FILE_HEADER_SIGNATURE.len +
//						ZipHeaderComponent.VERSION_NEEDED_TO_EXTRACT.len +
//						ZipHeaderComponent.GENERAL_PURPOSE_BIT_FLAG.len);
		fc.position(currHdrOffset + ZipHeaderComponent.COMPRESSION_METHOD.getOffset());
		fc.read(bb2);
//		short compressionMethod = bb2.getShort();
		short compressionMethod = bb2.getShort(0);
		System.out.println(compressionMethod);
		if (compressionMethod != 0)    return Optional.empty();

		fc.position(currHdrOffset + ZipHeaderComponent.COMPRESSED_SIZE.getOffset());
		bb4.rewind();
		fc.read(bb4);
		int compressedSize = bb4.getInt(0);

		fc.position(currHdrOffset + ZipHeaderComponent.UNCOMPRESSED_SIZE.getOffset());
		bb4.rewind();
		fc.read(bb4);
		int uncompressedSize = bb4.getInt(0);
		System.out.println(compressedSize + " " + uncompressedSize);

		fc.position(currHdrOffset + ZipHeaderComponent.FILE_NAME_LENGTH.getOffset());
		bb2.rewind();
		fc.read(bb2);
		short filenameLength = bb2.getShort(0);
		System.out.println(filenameLength);
	
		fc.position(currHdrOffset + ZipHeaderComponent.EXTRA_FIELD_LENGTH.getOffset());
		bb2.rewind();
		fc.read(bb2);
		short extraFieldLength = bb2.getShort(0);
		System.out.println(extraFieldLength);
	
		var bbFilename = ByteBuffer.allocate(filenameLength);
		fc.position(currHdrOffset + ZipHeaderComponent.END.getOffset());
		fc.read(bbFilename);
		var filename = new String(bbFilename.array());
		System.out.println("filename: " + filename);

		if (filenameLength > 0) {
			writeFile(fc, currHdrOffset, filename, filenameLength, extraFieldLength, compressedSize);
		}
		
		return Optional.of(ZipHeaderComponent.END.getOffset() + filenameLength + extraFieldLength + compressedSize);
	}

	private static void writeFile(FileChannel fc, int currHdrOffset, String filename, int filenameLength, short extraFieldLength, int compressedSize) throws IOException {
		var bb = ByteBuffer.allocate(compressedSize);
		fc.position(currHdrOffset + ZipHeaderComponent.END.getOffset() + filenameLength + extraFieldLength);
		fc.read(bb);
		try (var outFc = FileChannel.open(Path.of("out_" + filename), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
			bb.flip();
			outFc.write(bb);
		}
	}
}
