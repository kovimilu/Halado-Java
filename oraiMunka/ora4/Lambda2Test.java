package ora4;


import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Lambda2Test {

    @Test
    void test() {
        Supplier<Integer> pos1 = Lambda2.primeSupplier.get();
        Integer result1 = pos1.get();
        Integer result2 = pos1.get();

        assertEquals(2, result1);
        assertEquals(3, result1);
    }

}
