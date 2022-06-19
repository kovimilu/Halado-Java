import java.util.Arrays;
import java.util.function.ToIntFunction;

public class OffsetUtil {
	// Note: this generalises the ZipHeaderComponent.getOffsetSimple solution to any enum.
	public static <E extends Enum<E>> int getOffsetV2(E elem, ToIntFunction<E> getLen) {
		// Note: unfortunately, this is not valid code.
//		E[] values = E.values();

		@SuppressWarnings("unchecked")
		Class<E> enumClass = (Class<E>)elem.getClass();
		E[] values = enumClass.getEnumConstants();

		return Arrays.stream(values).takeWhile(e -> e != elem).mapToInt(getLen).sum();
	}
}
