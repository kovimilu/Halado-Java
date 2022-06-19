import static java.lang.Integer.parseInt;
import static op.Operation.valueOf;

import java.util.Arrays;
import java.util.List;

import op.Operation;
import ut.Utensil;

public class Enums {
	final int MAGIC_NUMBER = 0xCAFEBABE;
	final long MAGIC_NUMBER2 = 1_000_000_000_000_000L;
	final long MAGIC_NUMBER3 = 1000_0000_0000_0000L;

	static final int UTENSIL_SPOON = 1;
	static final int UTENSIL_FORK = 2;
	static final int UTENSIL_KNIFE = 93;

	static final int OP_SAVE = 1;
	static final int OP_LOAD = 2;
	static final int OP_FORK = 3;

	public static void main(String[] args) {
		System.out.println(Integer.parseInt("123") + 1);
		System.out.println(parseInt("123") + 2);
		System.out.println(valueOf("FORK"));

		String[] names = {"123"};
		String[] names2 = new String[]{"123"};
		var whatTypeIsThis = "123";
		var whatTypeIsThis2 = 123;
		
//		System.out.println(toString(new String[]{"123"}));
		System.out.println(Arrays.toString(new Object[]{"123"}));
	}

	public static void main2(String[] args) {
		System.out.println(UTENSIL_KNIFE);
		System.out.println(93);
		
		Utensil ut = Utensil.FORK;

//		Enum en = Utensil.FORK;
//		System.out.println(en);
//		System.out.println(Utensil.FORK instanceof Enum);

		System.out.println(Utensil.FORK);
		System.out.println(Utensil.KNIFE.toString());

		System.out.println(Utensil.KNIFE.name);
		System.out.println(Utensil.KNIFE.ordinal());
		System.out.println(Utensil.SPOON.ordinal());

		System.out.println(Utensil.FORK.ordinal());
		System.out.println(Operation.FORK.ordinal());

		Operation[] values = Operation.values();
		Operation[] values2 = {
			Operation.SAVE,
			Operation.LOAD,
			Operation.FORK
		};
		
		// [SAVE, LOAD, FORK]
		System.out.println(Operation.values());
		System.out.println(Arrays.toString(Operation.values()));
		
		// Collections, Objects
		
		var myList = List.of(Operation.SAVE,
			Operation.LOAD,
			Operation.FORK);
		System.out.println(myList);

		Operation fork1 = Operation.valueOf("FORK");
		Utensil fork2 = Utensil.valueOf("FORK");
		
		System.out.println(Operation.valueOf("FORK"));
		System.out.println(Utensil.valueOf("FORK"));

		System.out.println(UTENSIL_FORK == OP_FORK);
		// statikusan típusos
//		System.out.println(Utensil.FORK == Operation.FORK);

		System.out.println(Utensil.KNIFE);
		Utensil.KNIFE.prongs = 123;
		System.out.println(Utensil.KNIFE);
//		System.out.println(Utensil.FORK == Operation.FORK);

//		Utensil utensil = new Utensil();

		System.out.println(Utensil.X);
		System.out.println(Utensil.Y);
	}

	public static int collatz(int n) {
		if (n == 1)   return 1;
		if (n % 2 == 0)   return collatz(n/2);
		return collatz(3 * n + 1);
	}
}
