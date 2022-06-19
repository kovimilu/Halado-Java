import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparing;
import static java.util.Map.entry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// "Consumer<String>"
@FunctionalInterface
interface NoExcConsumer {
	void accept(String value) throws IOException;
}

public class Streams {
	private static Consumer<String> ignoreException(NoExcConsumer in) {
		return txt -> {
			try {
				in.accept(txt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	}
	
	public static void main2(String[] args) throws IOException {
		// try-with-resources
		try (
			FileWriter fw = new FileWriter("out.txt");
//			PrintWriter pw = new PrintWriter("out.txt");
		) {
			Files.lines(Path.of("src", "Streams.java"))
				.map(txt -> txt + "\n")
//				.sorted(Comparator.comparing(txt -> txt.length()))
				.sorted(Comparator.comparing(txt -> txt.chars().boxed().collect(Collectors.toSet()).size()))
//				.sorted(Comparator.comparing(txt -> txt.chars().distinct().count()))
				.forEach(ignoreException(fw::write));
//			.forEach(fw::write);
//			.forEach(txt -> fw.write(txt));
//			.forEach(txt -> {
//				try {
//					fw.write(txt + "\n");
//				} catch (IOException e) {
//					// ...............
//				}
//			});

			
//				.forEach(fw::write);
//				.forEach(txt -> {
//					// Unhandled exception type IOException
//					fw.write(txt);
//					fw.write(txt);
//					fw.write(txt);
//				});
//				.forEach(pw::println);
//				.forEach(System.out::println);
		}

//		PrintStream out = System.out;
	}

	public static void main3(String[] args) throws IOException {
		try (
//			Scanner sc = new Scanner("a b c d e");
			Scanner sc = new Scanner(new File("src/Streams.java"));
		) {
//			sc.tokens()
//				.forEach(System.out::println);

//			sc.forEachRemaining(System.out::println);

//			Stream.generate(() -> sc.hasNext() ? sc.next() : null)
//				.takeWhile(token -> token != null)
//				.forEach(System.out::println);

			Stream.generate(() -> sc.hasNext() ? Optional.of(sc.next()) : Optional.empty())
				.takeWhile(Optional::isPresent)
//				.takeWhile(token -> token.isPresent())
				.map(Optional::get)        // Stream<Optional<String>>
				.forEach(System.out::println);
		}
	}
	
	
	public static void main(String[] args) throws IOException {
//		Optional<Entry<Integer, Integer>> max = Stream.of("13;54", "76;65", "-6453;3")
		Optional<Entry<Integer, Integer>> max = Stream.<String>of()
//			.map(Point::new)
//			.map(txt -> new Point(txt))
			.map(line -> line.split(";"))		// Stream<String[]>
			.map(parts -> entry(parseInt(parts[0]), parseInt(parts[1])))		// Map.Entry<Integer, Integer>
			.max(comparing(Streams::getDistFromOrigin))
			;

		System.out.println(max);
	}

	private static double getDistFromOrigin(Entry<Integer, Integer> point) {
		var x = point.getKey();
		var y = point.getValue();
		return Math.hypot(x, y);
	}
}
