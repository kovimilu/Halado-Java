package either;
import java.util.function.Function;

public class EitherImpl1<L, R> implements Either<L, R> {
	private L left;
	private R right;

	private EitherImpl1(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public static <L2, R2> Either<L2, R2> left(L2 left) {
		return new EitherImpl1<>(left, null);
	}

	public static <L2, R2> Either<L2, R2> right(R2 right) {
		return new EitherImpl1<>(null, right);
	}

	public boolean isLeft()  { return left != null; }
	public L getLeft()  {
		if (left == null)   throw new IllegalStateException();
		return left;
	}
	public R getRight() {
		if (right == null)   throw new IllegalStateException();
		return right;
	}

	public Either<R, L> swap() {
		return new EitherImpl1<>(right, left);
	}

	public <T> Either<L, T> map(Function<R, T> other) {
		return isRight() ? right(other.apply(getRight())) : left(getLeft());
	}

	public <T> Either<L, T> bind(Function<R, Either<L, T>> other) {
		return isRight() ? other.apply(getRight()) : left(getLeft());
	}
}
