import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Egyszamjatek {
//	(jatekos, tipp)
//	List<Map.Entry<Integer, Integer>>

//	Map<Jatekos, Tipp> ?
//	Map<Tipp, Jatekos> ?
//	Map<Integer, Integer>

//	Map<Tipp, List<Jatekos>>
//	Map<Tipp, Optional<Jatekos>> guesses = new HashMap<>();
	static Map<Integer, Optional<Integer>> guesses = new HashMap<>();
	
	public static void main(String[] args) throws InterruptedException {
		int PLAYER_COUNT = 10;
		for (int i = 0; i < PLAYER_COUNT; i++) {
			var idx = i;
			new Thread(() -> {
				while (true) {
					int guess = ThreadLocalRandom.current().nextInt();
					synchronized (guesses) {
						if (guesses.containsKey(guess)) {
							guesses.put(guess, Optional.empty());
						} else {
//							guesses.put(guess, Optional.of(i));
							guesses.put(guess, Optional.of(idx));
						}
					}
				}
			}).start();
		}

		while (true) {
			TimeUnit.SECONDS.sleep(1);
			synchronized (guesses) {
				System.out.printf("Guess count = %.2fM%n", guesses.keySet().size() / 1_000_000.0d);
				guesses.entrySet().stream()
					.filter(entry -> entry.getValue().isPresent())
					.min(Comparator.comparing(e -> e.getKey()))
					.ifPresent(entry -> System.out.printf("Winner=%d, Guess=%d%n", entry.getValue().get(), entry.getKey()));
				guesses.clear();
			}
		}
	}
}
