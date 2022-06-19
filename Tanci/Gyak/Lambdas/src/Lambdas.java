import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class Lambdas {
	public static Supplier<Integer> positives = new Supplier<Integer>() {
		private int counter = 0;
		
		@Override
		public Integer get() {
			return ++counter;
		}
	};

	private static int[] externalCounter = { 0 };
	public static Supplier<Integer> positives2 = () -> ++externalCounter[0];

	public static Supplier<Integer> positives2Wrong = () -> {
		int internalCounter = 0;
		return ++internalCounter;
	};

	// List<List<Integer>>
	public static Supplier<Supplier<Integer>> positivesGen = () -> {
		int[] counter = { 0 };
		return () -> ++counter[0];
	};

	public static Supplier<IntSupplier> positivesGen2 = () -> {
		int[] counter = { 0 };
		return () -> ++counter[0];	
	};

//	public static IntSupplier positives2;

	private static BiConsumer<Integer, Function<Integer, Integer>> printNum = (n, times) -> {
		for (int i = 0; i < times.apply(i); i++) {
			System.out.println(i + ". " + n);
		}
	};

	public static Consumer<Integer> printV1 = n -> printNum.accept(n, i -> n);
	public static Consumer<Integer> printV2 = n -> printNum.accept(n, i -> ThreadLocalRandom.current().nextInt(10));
	public static Consumer<Integer> printV3 = n -> printNum.accept(n, i -> i);

	public static Function<Integer, Integer> factIter1 = n -> {
		int result = 1;
		for (int i = 1; i <= n; i++) result *= i;
		return result;
	};
//	// Function<int, int> fact1 = n -> n;
//	IntFunction<Integer> fact2 = n -> n;
//	IntUnaryOperator fact2 = n -> n;

	public static Function<Integer, Integer> factRec = new Function<>() {
		@Override
		public Integer apply(Integer n) {
			return n == 1 ? 1 : n * apply(n-1);
		}
	};

	// Cannot reference a field before it is defined
//	public static Function<Integer, Integer> factRec2wrong =
//		n -> n == 1 ? 1 : n * factRec2wrong.apply(n-1);

	public static Function<Integer, Integer> factRec2 =
		n -> n == 1 ? 1 : n * Lambdas.factRec2.apply(n-1);
		
		
//	public static void main(String[] args) {
//      // nem megoldható lokális változóval, lambda alakban, rekurzív hívással
//		Function<Integer, Integer> factRecLocal =
//           n -> n == 1 ? 1 : n * factRecLocal.apply(n-1);
//	}

		
//	M ::= Map<String, Integer>
//	public static BiFunction<M, M, M> mapper =
	public static BiFunction<Map<String, Integer>, Map<String, Integer>, Map<String, Integer>> mapper =
		(map1, map2) -> {
//			Map<String, Integer> result = new HashMap<>(map1);
			var result = new HashMap<>(map1);
//			for (Map.Entry<String, Integer> entry : map2.entrySet()) {
			for (var entry : map2.entrySet()) {
				var key = entry.getKey();
				var newValue = entry.getValue();

//				if (result.containsKey(key)) {
//					result.put(key, result.get(key) + newValue);
//				} else {
//					result.put(key, newValue);
//				}

//				int addendum = result.containsKey(key) ? result.get(key) : 0;  
//				result.put(key, addendum + newValue);

//				int addendum = result.getOrDefault(key, 0);  
//				result.put(key, addendum + newValue);

				result.compute(key,
					(keyIgnored, oldValue) ->
						(oldValue == null ? 0 : oldValue) + newValue);
			}
			return result;
		};
}
