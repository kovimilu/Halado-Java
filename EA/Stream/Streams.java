import static java.lang.System.out;
import static java.nio.file.Files.lines;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static java.util.stream.Stream.generate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
	static int counter2 = 0;

	private static int myParseInt(String txt) {
		return Integer.parseInt(txt);
	}
	
	public static void main(String[] args) throws IOException {
		Stream.of(643, 2314, 12, 45)
			.sorted()
			.forEach(System.out::println);

		Stream.of(643, 2314, 12, 45, -6435)
//			.sorted((n1, n2) -> (n2 + "").length() - (n1 + "").length())
//			.sorted(Comparator.comparing())
//			.sorted(comparing(n -> (n + "").length()))

			// Problem detected during type inference: Unknown error at invocation of
			// thenComparing(Function<? super Object,? extends Comparable<? super Comparable<? super U>>>)
//			.sorted(comparing(n -> (n + "").length()).thenComparing(n -> n))
			.sorted(comparing((Integer n) -> (n + "").length()).thenComparing(n -> n))
			.forEach(System.out::println);

		String result = Stream.of(643, 2314, 12, 45, -6435)
			.map(n -> (n + "").length())
			.sorted()
//			.forEach(System.out::println);
			.map(n -> n + "")
//			.collect(Collectors.joining());
//			.collect(Collectors.joining(", "));
			.collect(joining(", "));
		System.out.println(result);

		Optional<Integer> result2 = Stream.of(643, 2314, 12, 45, -6435)
			.reduce((n1, n2) -> n1 * n2);
		System.out.println(result2.orElse(0));
	}

	public static void main11(String[] args) throws IOException {
		OptionalInt max = range(0, 10).max();
		System.out.println(max.getAsInt());

//		OptionalInt max2 = range(0, 0).max();
//		System.out.println(max2.getAsInt());

		IntSummaryStatistics stats = range(0, 10).summaryStatistics();
		System.out.println(stats);
		System.out.println(stats.getAverage());

//		Stream.of("a", "dsagfhjs").average();
	}

	public static void main10(String[] args) throws IOException {
		Optional<String> result = lines(Path.of("abc.txt"))
			.dropWhile(line -> line.length() < 49)
			.findFirst();

		System.out.println(result);

//		String baaaaaaaaaad = null;
//		System.out.println(baaaaaaaaaad.lastIndexOf('x'));

		System.out.println(result.get());

		String resultTxt = Stream.of("a")
				.map(txt -> txt + "e")
				.filter(txt -> false)
				.findFirst()
				.map(txt -> txt + "d")
				.get();

		System.out.println(Optional.of("abc").orElse("xyz"));
		System.out.println(Optional.empty().orElse("xyz"));

		System.out.println(Optional.of("abc").orElseGet(() -> "xyz"));

		System.out.println(Optional.of("abc").isEmpty());
		System.out.println(Optional.of("abc").isPresent());
		System.out.println(Optional.empty().isEmpty());
		System.out.println(Optional.empty().isPresent());
		
		System.out.println(Optional.of("abc").map(txt -> txt + "d"));
		System.out.println(Optional.empty().map(txt -> txt + "d"));

//		Optional<int>
		OptionalInt findAny = range(0, 0)
			.filter(n -> n > 100)
			.findAny();
	}

	public static void main9(String[] args) throws IOException {
//		Stream.of(1, 2, 3, 754)
//			.skip(2)
//			.forEach(System.out::println);
//
//		Stream.of(1, 2, 3, 754)
//			.limit(2)
//			.forEach(System.out::println);

		range(0, 10)
			.limit(2)
			.filter(n -> n > 5)
			.forEach(System.out::println);

		range(0, 10)
			.filter(n -> n > 5)
			.limit(2)
			.forEach(System.out::println);

//		Stream.of() ...

//		range(0, 10)
//		Stream.of(1, 2, 3, 754, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3)
//			.takeWhile(n -> n < 5)
//			.forEach(System.out::println);
	
//		range(0, 10)
//			.dropWhile(n -> n < 5)
//			.forEach(System.out::println);

		lines(Path.of("abc.txt"))
			.dropWhile(line -> line.length() < 49)
			.forEach(System.out::println);

		boolean allMatch = lines(Path.of("abc.txt"))
				.dropWhile(line -> line.length() < 49)
				.allMatch(line -> line.length() < 70);
			
		boolean anyMatch = lines(Path.of("abc.txt"))
				.dropWhile(line -> line.length() < 49)
				.anyMatch(line -> line.length() < 70);
			
		boolean noneMatch = lines(Path.of("abc.txt"))
				.dropWhile(line -> line.length() < 49)
				.noneMatch(line -> line.length() < 70);
			
		System.out.println(allMatch);
		System.out.println(anyMatch);
		System.out.println(noneMatch);
	}

	public static void main8(String[] args) throws IOException {
//		List<Integer> myList = List.of(1, 2, 3);

//		Stream<Integer> stream2 = myList.stream();
//		Stream<Integer> parallelStream = myList.parallelStream();
		
		// (((1+2)+3)+4)+5+6+7+8+9+10
		// (1.1+2.2+3.3+4)+(5+6+7+8+9+10)
		
		Stream.of(1, 2, 3, 643, 7536)
			.parallel()
			.filter(n -> n % 2 == 0)
			.forEach(System.out::println);

		range(0, 10)
			.filter(n -> n % 2 == 0)
			.forEach(System.out::println);

		Stream.of(1, 2, 3)
			.map(n -> new int[] {n,n,n,n,n})      // Stream<int[]>
			.map(ns -> Arrays.toString(ns))
			.forEach(System.out::println);

		Stream.of(1, 2, 3, 754)
			.flatMap(n -> Stream.of(n, n, n, n, n))
			.forEach(System.out::println);
	}

	public static void main7(String[] args) throws IOException {
		// Stream<Integer>
		Stream.of(1, 2, 3)
			.map(n -> 2 * n)
			.forEach(System.out::println);

		Stream.of(1, 2, 3)
			.mapToInt(n -> 2 * n)
			.forEach(System.out::println);

		Stream.of("1", "2", "3")
//			.mapToInt(txt -> Integer.parseInt(txt))
			.mapToInt(Integer::parseInt)
			.forEach(System.out::println);

		Stream.of("1", "2", "3")
			.mapToInt(Streams::myParseInt)
			.forEach(System.out::println);
	

		Stream.of(1, 2, 3)
			.map(n -> 2 * n + " abc")		// Stream<String>
			.forEach(System.out::println);

		// IntStream
		range(0, 10)
			.map(n -> 2 * n)
			.forEach(System.out::println);

		range(0, 10)
			.mapToObj(n -> 2 * n + " abc")
			.forEach(System.out::println);

		range(0, 10)
			.boxed()
			.map(n -> 2 * n + " abc")
			.forEach(System.out::println);
	}

	public static void main6(String[] args) throws IOException {
		range(0, 10).boxed().forEach(e -> System.out.println(e));
		range(0, 10).boxed().forEach(System.out::println);

		range(0, 10).boxed().forEach(n -> {
			System.out.println(n + n);
			System.out.println(n);
		});

		List<Integer> collect = range(0, 10).boxed().collect(Collectors.toList());
		var collect2 = range(0, 10).boxed().collect(toList());
		Set<Integer> collect3 = range(0, 10).boxed().collect(Collectors.toSet());

		Object[] array = range(0, 10).boxed().toArray();
//		Integer[] result = range(0, 10).boxed().toArray();
		Integer[] array2 = range(0, 10).boxed().toArray(n -> new Integer[n]);
		Integer[] array3 = range(0, 10).boxed().toArray(Integer[]::new);

		int[] array4 = range(0, 10).toArray();
	}

	public static void main4(String[] args) throws IOException {
//		Supplier<Integer> supp = () -> 1;
//		System.out.println(supp.get());

		generate(() -> 1)
			.limit(3)
			.forEach(System.out::println);

		Stream.generate(new Supplier<Integer>() {
			int counter = 0;
			@Override
			public Integer get() {
				return ++counter;
			}
		})
		.limit(13)
		.forEach(System.out::println);

		int[] counter2v2 = {0};
//		Stream.generate(() -> ++counter2)
		Stream.generate(() -> ++counter2v2[0])
			.limit(3)
			.forEach(System.out::println);

		// value, f(v), f(f(v)), f(f(f(v))), f(f(f(f(v)))), ...
		Stream.iterate(0, n -> n + 1)
			.limit(123)
			.forEach(System.out::println);

		Stream.iterate("a", txt -> txt + "b")
			.limit(20)
			.forEach(System.out::println);

		// függvénykompozíció
//		Files.lines(Path.of("src", "Streams.java"))
		Files.lines(Path.of("abc.txt"))
			.limit(3)
			.forEach(System.out::println);
	}

	public static void main3(String[] args) {
		Stream.of("a", "b", "c").forEach(System.out::println);

		args = "x y z".split(" ");
		Arrays.stream(args).forEach(System.out::println);
		stream(args).forEach(System.out::println);

		IntStream.range(0, 1000).forEach(System.out::println);
		IntStream.rangeClosed(0, 1000).forEach(System.out::println);
		range(0, 1000).forEach(out::println);
		rangeClosed(0, 1_000_000).forEach(out::println);
		
		for (int i = 0; i < 1_000_000; i++) {
			System.out.println(i);
		}
	}

	public static void main2(String[] args) {
		// nem I/O

		List<Integer> oneTwoThreeList = List.of(1, 2, 3);
		Stream<Integer> oneTwoThree = Stream.of(1, 2, 3);

		System.out.println(oneTwoThreeList);
		System.out.println(oneTwoThree);

		for (Integer integer : oneTwoThreeList) {
			System.out.println(integer);
		}
		
		for (int i = 0; i < oneTwoThreeList.size(); i++) {
			System.out.println(i + ". elem: " + oneTwoThreeList.get(i));
		}
		
		// par: Consumer<Integer>
		oneTwoThree.forEach(num -> System.out.println(num));
//		oneTwoThree.forEach(num -> System.out.println(num));

		Stream.of(1, 2, 3)
			.forEach(num -> System.out.println(num));

		Stream.of(1, 2, 3)
			.forEach(num -> System.out.println(num));
	}
}
