import java.util.Arrays;

public enum ZipHeaderComponent implements WithOffset<ZipHeaderComponent> {
	LOCAL_FILE_HEADER_SIGNATURE(4),
	VERSION_NEEDED_TO_EXTRACT(2),
	GENERAL_PURPOSE_BIT_FLAG(2),
	COMPRESSION_METHOD(2),
	LAST_MOD_FILE_TIME(2),
	LAST_MOD_FILE_DATE(2),
	CRC32(4),
	COMPRESSED_SIZE(4),
	UNCOMPRESSED_SIZE(4),
	FILE_NAME_LENGTH(2),
	EXTRA_FIELD_LENGTH(2),
	END(0);
	
	int len;

	ZipHeaderComponent(int len) {
		this.len = len;
	}

	@Override
	public int getLen() {
		return len;
	}

	// Note: the WithOffset.getOffset solution is much more elegant than this. 
	// Note: this is a much simpler replacement. 
	public int getOffsetSimple() {
		return Arrays.stream(ZipHeaderComponent.values()).takeWhile(e -> e != this).mapToInt(e -> e.len).sum();
	}
}
