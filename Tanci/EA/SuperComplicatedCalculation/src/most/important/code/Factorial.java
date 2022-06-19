
package most.important.code;

public class Factorial {
	public static int fac(int n) {
		if (n < 0)   throw new IllegalArgumentException();
		if (n == 0)   return 1;
		return n * fac(n - 1);
	}

	public static int[] allFacs(int n) {
		var retval = new int[n];
		for (int i = 0; i < n; i++) {
			retval[i] = fac(i);
		}
		return retval;
	}

}
