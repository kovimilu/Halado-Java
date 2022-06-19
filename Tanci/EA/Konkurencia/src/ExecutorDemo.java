import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ExecutorDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// ThreadGroup

//		ExecutorService ex = Executors.newFixedThreadPool(10);
//		ExecutorService ex = Executors.newCachedThreadPool();
		ExecutorService ex = Executors.newWorkStealingPool();
		Runnable runnable = () -> {
			for (int j = 0; j < 10000; j++) {
				System.out.println("Hello");
			}
		};

		Function<String, Runnable> makePrinter = txt -> () -> {
			for (int j = 0; j < 10000; j++) {
				System.out.println(txt);
			}
		};

		ex.submit(makePrinter.apply("Hello"));
		ex.submit(makePrinter.apply("World"));

		Future<Integer> future1 = ex.submit(() -> 1);
		var future2 = ex.submit(() -> 2);
		
		ex.shutdown();
		ex.awaitTermination(1, TimeUnit.MINUTES);
		ex.shutdownNow();

		int result = future1.get() + future2.get();
		
		System.out.println("Finished: " + result);
		
//		ex.submit(runnable);
//
//		ex.submit(() -> {
//			for (int j = 0; j < 1000000; j++) {
//				System.out.println("World");
//			}
//		});
	}
}
