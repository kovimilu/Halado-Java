
package most.important.code;

import static most.important.code.Factorial.allFacs;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ut.Utensil;

public class FactorialTest {
//	@CsvSource("5,120")
	@CsvSource({"5,120,abc,SPOON", "6,720,xyz,KNIFE"})
	@ParameterizedTest
	void fac(int in, int expected, String txt, Utensil utensil) {
		assertEquals(expected, Factorial.fac(in));
	}

	@Test
	void facNegative() {
		assertThrows(IllegalArgumentException.class,
			() -> Factorial.fac(-1) 
		);
	}


	@Test
	public void arrayTest() {
//		assertEquals(new int[] {1, 1, 2}, allFacs(3));
		assertArrayEquals(new int[] {1, 1, 2}, allFacs(3));

//		assertEquals(false, 1 == 2);
//		assertTrue(false);
//		assertSame(getClass(), getClass());
	}
}
