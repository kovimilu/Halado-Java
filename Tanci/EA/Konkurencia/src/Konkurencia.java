import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

public class Konkurencia {
	// erõforrás / resource
	static int value = 0; 

	public static synchronized void myPrint(String txt) {
		synchronized (Konkurencia.class) { // ha static a fv
//		synchronized (this) { // ha nem static
			for (int i = 0; i < txt.length(); ++i) {
				System.out.print(txt.charAt(i));
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Function<String, Thread> makePrinter = txt -> new Thread(() -> {
			for (int j = 0; j < 10000; j++) {
				myPrint(txt);
			}
		});
		
		makePrinter.apply("aaaaaaaaaaaaaaa").start();
		makePrinter.apply("ddddddddddddddd").start();
	}

	public static void main4(String[] args) throws InterruptedException {
		// interferencia
		Function<String, Thread> makePrinter = txt -> new Thread(() -> {
			for (int j = 0; j < 10000; j++) {
//				synchronized (args) {
//				synchronized (Konkurencia.class) {
				synchronized (System.out) {
					for (int i = 0; i < txt.length(); ++i) {
						System.out.print(txt.charAt(i));
					}
					System.out.println();
				}
//				System.out.println(txt);
			}
		});
		
		makePrinter.apply("aaaaaaaaaaaaaaa").start();
		makePrinter.apply("ddddddddddddddd").start();
	}

	
	
	public static void main3(String[] args) throws InterruptedException {
		Object obj = new Object();
		Konkurencia konk = new Konkurencia();
		
		Supplier<Thread> makeIncreaser = () -> new Thread(() -> {
			for (int i = 0; i < 100_000; i++) {
				// race condition / versenyhelyzet
//				++value;

				// monitor
				// kritikus szakasz / critical section
//				synchronized (konk) {		// lock / zár
//				synchronized (obj) {		// lock / zár
//				synchronized (System.out) {		// lock / zár
				synchronized (args) {		// lock / zár
					int reg = value;	// 1 0
					reg += 1;			// 2 1 
					value = reg;		// 2 1        1 value
				}
			}
		});

		Thread t1 = makeIncreaser.get();
		Thread t2 = makeIncreaser.get();
		t1.start();
		t2.start();

		synchronized (args) {
			int reg = value;	// 1 0
			reg += 1;			// 2 1 
			value = reg;		// 2 1        1 value
		}

		t1.join();
		t2.join();


		// NINCS futási idejû szál
//		t1.run();
//		t2.run();

		System.out.println(value);
	}

	public static void mainIrrelevant(String[] args) throws InterruptedException {
		// AutoCloseable
		try (Scanner sc = new Scanner("dfsa")) {
			
		}
	}

	public static void main2(String[] args) throws InterruptedException {
		Runnable runnable = () -> {
			for (int i = 0; i < 1000; i++) {
				System.out.println("1 " + i);
			}
		};
		Thread t1 = new Thread(runnable);

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				System.out.println("2 " + i);
			}
		});

		// deadlock / holtpont
		
		// futási idejû szál
		t1.start();		// spawn
		t2.start();

		t2.join();
		System.out.println("t2 Finish");
		t1.join();   // blokkoló hívás
		
		System.out.println("Finish");
		
		// daemon szál
		
//		
//		// TILOS
////		t1.stop();
//		
//		t1.setPriority(0);
//		// yield
//
//		// ütemezés / scheduling 
//		
//		System.gc();
	}

}
