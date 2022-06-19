package dumper;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ClassDumperTest {
	@CsvSource({
		"1,1,dumper.test.TestClass1",
		"2,1,dumper.test.TestClass2",
		"3,1,dumper.test.TestClass3",
		"4,1,java.lang.Object",

		"1,2,dumper.test.TestClass1",
		"2,2,dumper.test.TestClass2",
		"3,2,dumper.test.TestClass3",
		"4,2,java.lang.Object",
	})
	@ParameterizedTest
	void test(int expectedIdx, int dumperVsn, String className) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(className);
		Function<Class<?>, String> dumper =
			dumperVsn == 1 ? ClassDumper::dump : ClassDumper2::dump;
		var expected = expecteds[expectedIdx-1];
		assertEquals(ignoreBlanks(expected), ignoreBlanks(dumper.apply(clazz)));
	}

	private String ignoreBlanks(String txt) {
		return txt.trim().lines().filter(line -> !line.isBlank()).collect(joining(System.lineSeparator()));
	}
	
	static String[] expecteds = {
	"""
public class TestClass1 extends java.lang.Thread implements java.lang.Runnable, java.io.Serializable {
    private static final int CONST;
    transient volatile boolean flag;
    protected java.lang.Object obj;
    public int n;

    public static void printConst(...) { /* method body */ }
}
	""",

	"""
public class TestClass2 extends java.lang.Thread {
    private static final int CONST;
    transient volatile boolean flag;
    protected java.lang.Object obj;
    public int n;
	
    public static void printConst(...) { /* method body */ }
}
	""",

	"""
 class TestClass3 extends java.lang.Object {
    private static final int CONST;
    transient volatile boolean flag;
    protected java.lang.Object obj;
    public int n;
	
    public static void printConst(...) { /* method body */ }
}
	""",

	"""
public class Object {
    protected void finalize(...) { /* method body */ }
    public final void wait(...) { /* method body */ }
    public final void wait(...) { /* method body */ }
    public final native void wait(...) { /* method body */ }
    public boolean equals(...) { /* method body */ }
    public java.lang.String toString(...) { /* method body */ }
    public native int hashCode(...) { /* method body */ }
    public final native java.lang.Class getClass(...) { /* method body */ }
    protected native java.lang.Object clone(...) { /* method body */ }
    public final native void notify(...) { /* method body */ }
    public final native void notifyAll(...) { /* method body */ }
}
	"""
	};
}
