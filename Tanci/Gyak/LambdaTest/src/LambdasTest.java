import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

class LambdasTest {
	@Test
	void testRun1() {
		Integer result1 = Lambdas.positives.get();
		Integer result2 = Lambdas.positives.get();
		Integer result3 = Lambdas.positives.get();
		Integer result4 = Lambdas.positives.get();
		
		assertAll(
			() -> assertEquals(1, result1),
			() -> assertEquals(2, result2),
			() -> assertEquals(3, result3),
			() -> assertEquals(4, result4)
		);
	}

//	@Test
//	void testRun1OnceMore() {
//		Integer result1 = Lambdas.positives.get();
//		Integer result2 = Lambdas.positives.get();
//		Integer result3 = Lambdas.positives.get();
//		Integer result4 = Lambdas.positives.get();
//		
//		assertAll(
//			() -> assertEquals(1, result1),
//			() -> assertEquals(2, result2),
//			() -> assertEquals(3, result3),
//			() -> assertEquals(4, result4)
//		);
//	}

//	@Test
//	void testWrong() {
//		Integer result1 = Lambdas.positives2Wrong.get();
//		Integer result2 = Lambdas.positives2Wrong.get();
//		Integer result3 = Lambdas.positives2Wrong.get();
//		Integer result4 = Lambdas.positives2Wrong.get();
//		
//		assertAll(
//			() -> assertEquals(1, result1),
//			() -> assertEquals(2, result2),
//			() -> assertEquals(3, result3),
//			() -> assertEquals(4, result4)
//		);
//	}

	@Test
	void testRun2() {
		Integer result1 = Lambdas.positives2.get();
		Integer result2 = Lambdas.positives2.get();
		Integer result3 = Lambdas.positives2.get();
		Integer result4 = Lambdas.positives2.get();
		
		assertEquals(1, result1);
		assertEquals(2, result2);
		assertEquals(3, result3);
		assertEquals(4, result4);
	}

	@Test
	void testRun3() {
		Supplier<Integer> pos1 = Lambdas.positivesGen.get();
		Integer result11 = pos1.get();
		Integer result12 = pos1.get();
		Integer result13 = pos1.get();
		Integer result14 = pos1.get();
		
		assertEquals(1, result11);
		assertEquals(2, result12);
		assertEquals(3, result13);
		assertEquals(4, result14);

		Supplier<Integer> pos2 = Lambdas.positivesGen.get();
		Integer result21 = pos2.get();
		Integer result22 = pos2.get();
		Integer result23 = pos2.get();
		Integer result24 = pos2.get();
		
		assertEquals(1, result21);
		assertEquals(2, result22);
		assertEquals(3, result23);
		assertEquals(4, result24);

		Integer result15 = pos1.get();
		Integer result25 = pos2.get();

		assertEquals(5, result15);
		assertEquals(5, result25);
	}

	@Test
	public void fact() {
		assertAll(
			() -> assertEquals(1, Lambdas.factIter1.apply(1)),
			() -> assertEquals(6, Lambdas.factIter1.apply(3))
		);
	}

	@Test
	public void factRec() {
		assertEquals(1, Lambdas.factRec.apply(1));
		assertEquals(6, Lambdas.factRec.apply(3));
	}

	@Test
	public void factRec2() {
		assertEquals(1, Lambdas.factRec2.apply(1));
		assertEquals(6, Lambdas.factRec2.apply(3));
	}

	@Test
	public void mapper() {
		var in1 = Map.of("a", 1);
		var in2 = Map.of("a", 2, "b", 99);
		var out = Map.of("a", 3, "b", 99);
		assertEquals(out, Lambdas.mapper.apply(in1, in2));
	}

}
