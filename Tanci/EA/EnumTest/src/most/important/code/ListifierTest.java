package most.important.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ListifierTest {
	Listifier listifier;

	@BeforeAll
	public static void beforeAll() {
		// erõforrás megnyit
	}
	
	@BeforeEach
	public void beforeEach() {
		listifier = new Listifier();
		listifier.add(1);

		// tmp fájl nyit
	}

	@AfterEach
	public void afterEach() {
		// tmp fájl zár
	}
	
	@AfterAll
	public static void afterAll() {
		// erõforrás bezár
	}

	@Test
	public void single() {
		assertEquals(3, listifier.get(0));
	}

	@Test
	public void single2() {
//		listifier.add(643);
		assertEquals(3, listifier.get(0));
	}

	@Nested
	class Add64 {
		@BeforeEach
		public void beforeEach() {
			listifier.add(64);
		}

		@Test
		public void elems2() {
			assertEquals(132, listifier.get(1));
		}
	}

	@Nested
	class Add2 {
		@BeforeEach
		public void beforeEach() {
			listifier.add(2);
		}

		@Test
		public void elems2() {
			assertEquals(8, listifier.get(1));
		}

		@Nested
		class Add3 {
			@BeforeEach
			public void beforeEach() {
				listifier.add(3);
			}

			@Test
			public void elems3() {
				assertEquals(18, listifier.get(2));
			}
		}

	}
}
