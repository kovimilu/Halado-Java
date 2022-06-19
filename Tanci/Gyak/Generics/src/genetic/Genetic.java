package genetic;

import static java.util.Comparator.comparing;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.iterate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Genetic {
	public static <Entity> void showPopulation(List<Entity> result, Function<Entity, Integer> fitness, Function<Entity, String> showEntity) {
		result.stream()
			.sorted(comparing(fitness).reversed())
			.map(entity -> entry(fitness.apply(entity), showEntity.apply(entity)))
			.forEach(System.out::println);
		System.out.println("----------------------------");
	}

	public static <Entity> List<Entity> geneticAlgorithm(
			int populationCount, int iterationCount, int mutationProbabilty, int crossoverCount, int removeWorstCount,
			Supplier<Entity> createEntity,
			BiFunction<Entity, Entity, Stream<Entity>> crossover,
			Function<Entity, Entity> mutate,
			Function<Entity, Integer> fitness,
			Optional<Consumer<List<Entity>>> showPopulation) {

		List<Entity> initPopulation = range(0, populationCount).mapToObj(i -> createEntity.get()).collect(toList());

		return iterate(initPopulation, pop -> geneticAlgorithmIter(crossoverCount, mutationProbabilty, removeWorstCount, crossover, mutate, fitness, createEntity, pop))
			.limit(iterationCount)
			.peek(pop -> showPopulation.ifPresent(showPop -> showPop.accept(pop)))
			.skip(iterationCount - 1)
			.findFirst()
			.get();
	}

	// Population ::= List<Entity>
	private static <Entity> List<Entity> geneticAlgorithmIter(
			int crossoverCount, int mutationProbabilty, int removeWorstCount,
			BiFunction<Entity, Entity, Stream<Entity>> crossover,
			Function<Entity, Entity> mutatorFun,
			Function<Entity, Integer> fitness,
			Supplier<Entity> createEntity,
			List<Entity> pop) {

		return 
			Stream.concat(
				concat(
					pop.stream().map(entity -> ifMutate(mutationProbabilty) ? mutatorFun.apply(entity) : entity),
					range(0, crossoverCount).mapToObj(i -> crossover.apply(rndListElem(pop), rndListElem(pop))).flatMap(stream -> stream)
				)
					.sorted(comparing(fitness))
					.skip(removeWorstCount),
				range(0, removeWorstCount - crossoverCount).mapToObj(i -> createEntity.get())
			)
			.collect(toList());
	}

	private static boolean ifMutate(int mutationProbabilty) {
		// new Random()
		// Math.rnd()
		return ThreadLocalRandom.current().nextInt(100) < mutationProbabilty;
	}

	private static <Entity> Entity rndListElem(List<Entity> pop) {
		int rndIdx = ThreadLocalRandom.current().nextInt(pop.size());
		return pop.get(rndIdx);
	}
}
