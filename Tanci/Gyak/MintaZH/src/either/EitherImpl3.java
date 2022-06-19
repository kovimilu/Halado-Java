package either;
import java.util.function.Function;

public class EitherImpl3<L, R> implements Either<L, R> {
	private boolean isLeft;
	private Object value;

	private EitherImpl3(boolean isLeft, Object value) {
		this.isLeft = isLeft;
		this.value = value;
	}

	public static <L2, R2> Either<L2, R2> left(L2 left) {
		return new EitherImpl3<>(true, left);
	}

	public static <L2, R2> Either<L2, R2> right(R2 right) {
		return new EitherImpl3<>(false, right);
	}

	public boolean isLeft()  { return isLeft; }
	@SuppressWarnings("unchecked")
	public L getLeft() {
		if (!isLeft)   throw new IllegalStateException();
		return (L)value;
	}
	@SuppressWarnings("unchecked")
	public R getRight() {
		if (isLeft)   throw new IllegalStateException();
		return (R)value;
	}

	public Either<R, L> swap() {
		return new EitherImpl3<>(!isLeft, value);
	}

	public <T> Either<L, T> map(Function<R, T> other) {
		return isRight() ? right(other.apply(getRight())) : left(getLeft());
	}

	public <T> Either<L, T> bind(Function<R, Either<L, T>> other) {
		return isRight() ? other.apply(getRight()) : left(getLeft());
	}
}
