import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class Test1 {}
class Test2 extends Test1 {}

class BiMapTest {
	@Test
	void test() {
		var biMap = BiMap.<String, Integer>create();
		biMap.put("abc", 123);

		assertAll(
			() -> assertEquals(123, biMap.getFwd("abc")),
			() -> assertEquals("abc", biMap.getBack(123))
		);
	}

	@Test
	void test2() {
		var biMap = BiMap.<Test1, Integer>create((t1, t2) -> 0, (t1, t2) -> 0);

		var test1s = List.of(new Test2());
		var test2s = List.of(1);

		biMap.putAll(test1s, test2s);
	}

}
