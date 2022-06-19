import java.util.Arrays;

public interface WithOffset<E extends Enum<E> & WithOffset<E>> {
	int getLen();

	// Note: this generalises the ZipHeaderComponent.getOffsetSimple solution to any enum
	//  (provided that the enum implements this interface).
	public default int getOffset() {
		// Note: unfortunately, this is not valid code.
//		E[] values = E.values();

		@SuppressWarnings("unchecked")
		Class<WithOffset<E>> enumClass = (Class<WithOffset<E>>)this.getClass();
		WithOffset<E>[] values = enumClass.getEnumConstants();

		return Arrays.stream(values).takeWhile(e -> e != this).mapToInt(WithOffset::getLen).sum();
	}
}
