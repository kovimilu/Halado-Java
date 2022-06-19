package either;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public interface Either<L, R> {
	public boolean isLeft();
	public default boolean isRight() { return !isLeft(); }
	public L getLeft();
	public R getRight();

	public Either<R, L> swap();
	public <T> Either<L, T> map(Function<R, T> other);
	public <T> Either<L, T> bind(Function<R, Either<L, T>> other);

	public default R orElseGet(Supplier<R> other) {
		return isRight() ? getRight() : other.get();
	}

	public static <E> E iterate(Either<E, E> either, int n, UnaryOperator<E> fun) {
		return either.isLeft() ?
				either.getLeft() :
				Stream.iterate(either.getRight(), fun).skip(n).findFirst().get();
	}
}
