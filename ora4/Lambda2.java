package ora4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Lambda2 {
    /*
    public static Supplier<Map<String, Integer>> mapSupplier = (Map<String, Integer> map1,
                                                                Map<String, Integer> map2) -> {
        Map<String, Integer> result = new HashMap<>();
    };


    public static Supplier<Boolean> isPrime = (n) -> {
        if (n <= 1) return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    };
*/
    public static Supplier<Supplier<Integer>> primeSupplier = () -> {
        int[] nextPrime = { 0 };
        int[] counter = { 1 };

        for (int n = 0; n < counter[0]; ++n) {
            if (n <= 1) counter[0]++;
            for (int i = 2; i < n; i++) {
                if (n % i == 0) counter[0]++;
            }
            nextPrime[0] = n;
        }
        
        return () -> nextPrime[0];
    };

}
