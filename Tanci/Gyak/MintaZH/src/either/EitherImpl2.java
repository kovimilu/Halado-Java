package either;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class EitherImpl2<L, R> implements Either<L, R> {
	private Optional<L> left;
	private Optional<R> right;

	private EitherImpl2(Optional<L> left, Optional<R> right) {
		this.left = left;
		this.right = right;
	}

	public static <L2, R2> Either<L2, R2> left(L2 left) {
		return new EitherImpl2<>(Optional.of(left), Optional.empty());
	}

	public static <L2, R2> Either<L2, R2> right(R2 right) {
		return new EitherImpl2<>(Optional.empty(), Optional.of(right));
	}

	public boolean isLeft()  { return left.isPresent(); }
	public L getLeft()  {
		return left.orElseThrow(() -> new IllegalStateException());
	}
	public R getRight() {
		return right.orElseThrow(() -> new IllegalStateException());
	}

	public Either<R, L> swap() {
		return new EitherImpl2<>(right, left);
	}

	public <T> Either<L, T> map(Function<R, T> other) {
		return isRight() ? right(other.apply(getRight())) : left(getLeft());
	}

	public <T> Either<L, T> bind(Function<R, Either<L, T>> other) {
		return isRight() ? other.apply(getRight()) : left(getLeft());
	}
}
