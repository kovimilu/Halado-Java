package ora5;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;


public class Main {
    public static void main(String[] args) throws IOException {

        lines(Path.of("1.txt")).dropWhile(line -> line.length() > 5)
                .skip(3)
                .limit(10)
                .sorted()
                .distinct()
                .map(line -> line + "asd")
                .forEach(System.out::println);

        AtomicInteger sum = new AtomicInteger();
        IntStream.range(0, Integer.MAX_VALUE)
                .map(n -> sum.addAndGet(n))
                .skip(Integer.MAX_VALUE-1)
                .forEach(System.out::println);


        Stream.of(1,2,3).filter(i -> i > 1);


        }
    }