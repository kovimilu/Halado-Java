import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Lambdas {
	public static void main(String[] args) {
		BiFunction<Integer, Integer, Boolean> isEqualTo = (n1, n2) -> n1 == n2;
		BiFunction<String, Integer, String> gluedTogether = (txt, n) -> n + txt + n;

		var result = gluedTogether.apply("abc", 123);
		System.out.println(result);
	}

	public static void main6(String[] args) {
		Function<Integer, Boolean> isEven = n -> n % 2 == 0;

		Predicate<Integer> isEvenPred = n -> n % 2 == 0;

		for (int i = 0; i < 10; i++) {
			System.out.println(i + " " + isEven.apply(i) + " " + isEvenPred.test(i));
		}
	}

	public static void main5(String[] args) {
		Function<Integer, Integer> fun = n -> 2 * n + 1; 
		System.out.println(fun.apply(1));
		System.out.println(fun.apply(2));
		System.out.println(fun.apply(5));
		System.out.println(fun.apply(4756));

		IntFunction<Integer> ifun = n -> 2 * n + 1;
		IntUnaryOperator iuop = n -> 2 * n + 1;

		System.out.println(ifun.apply(4123));
		System.out.println(iuop.applyAsInt(6734));

		IntFunction<String> intToTxt = n -> "" + n;
		System.out.println(intToTxt.apply(1324));
	}

	public static void main4(String[] args) {
		Consumer<String> consumer = txt -> System.out.println(txt + "-" + txt);
		Consumer<Integer> consumerMul2 = (Integer n) -> System.out.println(n + "x2 = " + (n*2));

//		consumer.consume();
		consumer.accept("abc");
		consumerMul2.accept(123);

		Consumer<String> simplePrint = txt -> System.out.println(txt);
		// method reference
		Consumer<String> simplePrint2 = System.out::println;
	}

	public static void main3(String[] args) {
		Supplier<Integer> supp = () -> 1;
//		supp.supply();
		System.out.println(supp.get());

		
		Supplier<Integer> supp2 = () -> {
			int x = 1;
			++x;
			return x;
		};

		System.out.println(supp2.get());
		System.out.println(supp2.get());
		System.out.println(supp2.get());
		System.out.println(supp2.get());

		Supplier<Integer> supp3 = new Supplier<Integer>() {
			int counter = 0;
			
			@Override
			public Integer get() {
				return ++counter;
			}
		};

//		supp3.counter = 13543738;
		
		System.out.println(supp3.get());
		System.out.println(supp3.get());
		System.out.println(supp3.get());
		System.out.println(supp3.get());
		System.out.println(supp3.get());
		System.out.println(supp3.get());
		System.out.println(supp3.get());
		System.out.println(supp3.get());

		// Local variable hackCounter defined in an enclosing scope
		// must be final or effectively final
		int[] hackCounter = {1};
		Supplier<Integer> supp4 = () -> hackCounter[0] *= 2;
//		hackCounter = new int[] {654789, 6543, 234};

		System.out.println(supp4.get());
		System.out.println(supp4.get());
		System.out.println(supp4.get());
		hackCounter[0] = 123;
		System.out.println(supp4.get());
		System.out.println(supp4.get());
		System.out.println(supp4.get());
		System.out.println(supp4.get());
		System.out.println(supp4.get());
	}

	public static void main2(java.lang.String[] args) {
		// névtelen fv / anonymous fun / lambda
		//		(String s, int x, List<double[]> abc) -> 123;
//		(s, x, abc) -> 123

		java.lang.Runnable rMaidenName = () -> {};
		Runnable r = () -> {};
		r.run();

		// statikus típus =======> dinamikus típus
		// funkcionális interfész
		Runnable r2 = () -> {
			System.out.println("Hello world");
		};
		r2.run();
		r2.run();
		r2.run();
		r2.run();
		r2.run();
		r2.run();
		
		System.out.println(r.getClass().getName());
		
		MyFunInterface mfi = (s, x, abc) -> { System.out.println("lambdaimpl"); };
		MyFunInterface mfi2 = new MyFunImpl();
		MyFunInterface mfi3 = (s, x, abc) -> {
			int locVar;
			System.out.println("3 " + s);
		};

		mfi.dsghfajgdshajk(null, 0, null);
		mfi2.dsghfajgdshajk(null, 0, null);
		mfi3.dsghfajgdshajk(null, 0, null);

		// Cannot instantiate the type MyFunInterface
//		new MyFunInterface();
		
		MyFunInterface mfi4 = new MyFunInterface() {
			int field;
			
			@Override
			public void dsghfajgdshajk(String s, int x, List<double[]> abc) {
				System.out.println("névtelen osztály");
			}
		};

		mfi4.dsghfajgdshajk(null, 0, null);
		System.out.println(mfi4.getClass().getName());

		doubleRun(() -> {System.out.println("hello");}).run();
		doubleRun(() -> System.out.println("world")).run();
		
		List<java.lang.Integer> ints = List.of(1, 2, 3);
		List<String> txts = List.of("dsafdsa", "dgsahgsdfg");
		
		// type inference
		// nincs List<int>
		var ints2 = List.of(1, 2, 3);
		var txts2 = List.of("dsafdsa", "dgsahgsdfg");
		var elems = List.of(List.of("dsafdsa", "dgsahgsdfg"));
		

		// manifeszt típusozás
		List<Runnable> runnables =
			List.of(() -> System.out.println("hello"), () -> System.out.println("world"));
		List<MyRunnable> runnables2 =
			List.of(() -> System.out.println("hello"), () -> System.out.println("world"));

		Runnable mrR = () -> System.out.println("hello");
		MyRunnable mrMR = () -> System.out.println("hello");

		var mrR2 = (Runnable)() -> System.out.println("hello");
	}
	
	public static Runnable doubleRun(Runnable r) {
		return () -> {
			r.run();
			r.run();
		};
	}

	public static int fhfgdsjkalghjdksal(String s, int x, List<double[]> abc) {
		return 123;
	}
}
