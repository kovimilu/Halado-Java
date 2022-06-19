package genetic;

import static java.lang.String.format;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Stream;

public class GeneticKnapsack {
	public static void main(String[] args) {
		solveKnapsack();
	}
	
	private static void solveKnapsack() {
		var rnd = ThreadLocalRandom.current();

		int ITEM_COUNT = 25;
		int MAX_ITEM_WEIGHT = 10;
		int MAX_ITEM_VALUE = 10;
		int MAX_TOTAL_WEIGHT = ThreadLocalRandom.current().nextInt(ITEM_COUNT * MAX_ITEM_WEIGHT / 6, ITEM_COUNT * MAX_ITEM_WEIGHT / 2);
		int PENALTY = MAX_TOTAL_WEIGHT / 2;

		System.out.println("Max allowed weight is " + MAX_TOTAL_WEIGHT);
		
		// KnapsackItem ::= Map.Entry<Integer, Integer>
		var weightAndSizes = range(0, ITEM_COUNT)
			.mapToObj(i -> entry(rnd(1, MAX_ITEM_WEIGHT), rnd(1, MAX_ITEM_VALUE)))
			.collect(toList());

		// KnapsackEntity ::= List<Boolean>
		Function<List<Boolean>, String> showEntity = sack -> {
			var content = range(0, ITEM_COUNT)
				.filter(sack::get)
				.mapToObj(i -> format("#%d=%d=%d", i, weightAndSizes.get(i).getKey(), weightAndSizes.get(i).getValue()))
				.collect(joining(", ", "[", "]"));
			var totalWeight = range(0, ITEM_COUNT)
				.filter(sack::get)
				.map(i -> weightAndSizes.get(i).getKey())
				.sum();
			var itemCount = (int)range(0, ITEM_COUNT).filter(sack::get).count();
			return format("w%s%s=%dx%s", totalWeight, totalWeight > MAX_TOTAL_WEIGHT ? "!" : "", itemCount, content);
		};

		Function<List<Boolean>, Integer> fitness = sack -> {
			var totalWeight = range(0, ITEM_COUNT).filter(sack::get).map(i -> weightAndSizes.get(i).getKey()).sum();
			var totalValue = range(0, ITEM_COUNT).filter(sack::get).map(i -> weightAndSizes.get(i).getValue()).sum();
			var itemCount = (int)range(0, ITEM_COUNT).filter(sack::get).count();
			return totalValue * 10
					- itemCount
					- (totalWeight > MAX_TOTAL_WEIGHT ? PENALTY * (totalWeight - MAX_ITEM_VALUE) : 0); 
//					- (totalWeight > MAX_TOTAL_WEIGHT ? PENALTY : 0);
		};

		int GENE_MUTATION_PROBABILITY = 1;

		List<List<Boolean>> result = Genetic.geneticAlgorithm(10, 20, 5, 3, 6,
			() -> range(0, ITEM_COUNT).mapToObj(i -> rnd.nextBoolean()).collect(toList()),
			(sack1, sack2) -> Stream.of(range(0, ITEM_COUNT).mapToObj(i -> rnd.nextBoolean() ? sack1.get(i) : sack2.get(i)).collect(toList())),
			sack -> sack.stream().map(isIn -> rnd.nextInt(100) == GENE_MUTATION_PROBABILITY ? rnd.nextBoolean() : isIn).collect(toList()),
			fitness,
			Optional.empty()
//			Optional.of(pop -> Genetic.showPopulation(pop, fitness, showEntity))
			);
		
		Genetic.showPopulation(result, fitness, showEntity);
	}

	private static int rnd(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max+1);
	}
}
