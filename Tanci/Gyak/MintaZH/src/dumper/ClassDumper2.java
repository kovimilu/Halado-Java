package dumper;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.lang.reflect.Modifier;
import java.util.stream.Stream;

public class ClassDumper2 {
	public static String dump(Class<?> clazz) {
		Stream<String> flds = stream(clazz.getDeclaredFields())
			.map(fld -> String.format("    %s %s %s;",
				Modifier.toString(fld.getModifiers()),
				fld.getType().getName(),
				fld.getName()));

		Stream<String> methods = stream(clazz.getDeclaredMethods())
			.map(m -> String.format("    %s %s %s(...) { /* method body */ }",
					Modifier.toString(m.getModifiers()),
					m.getReturnType().getName(),
					m.getName()));

		return Stream.of(
					Stream.of(ClassDumper.getHeader(clazz)),
					flds,
					Stream.of(""),
					methods,
					Stream.of("}"))
				.flatMap(txtStream -> txtStream)
				.collect(joining("\n"));
	}
}
