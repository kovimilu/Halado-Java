package dumper;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

import java.lang.reflect.Modifier;

public class ClassDumper {
	public static String dump(Class<?> clazz) {
		String flds = stream(clazz.getDeclaredFields())
			.map(fld -> String.format("    %s %s %s;",
				Modifier.toString(fld.getModifiers()),
				fld.getType().getName(),
				fld.getName()))
			.collect(joining("\n"));
		String methods = stream(clazz.getDeclaredMethods())
			.map(m -> String.format("    %s %s %s(...) { /* method body */ }",
					Modifier.toString(m.getModifiers()),
					m.getReturnType().getName(),
					m.getName()))
			.collect(joining("\n"));

		String baseClass = ofNullable(clazz.getSuperclass())
			.map(cl -> " extends " + cl.getName()).orElse("");
		String ifaces = stream(clazz.getInterfaces())
			.map(Class::getName)
			.collect(joining(", "));

		return String.format("""
				%s
				%s

				%s
				}
				""",
				getHeader(clazz),
				flds,
				methods
		);
	}

	static String getHeader(Class<?> clazz) {
		String baseClass = ofNullable(clazz.getSuperclass())
			.map(cl -> " extends " + cl.getName()).orElse("");

		String ifaces = stream(clazz.getInterfaces())
			.map(Class::getName)
			.collect(joining(", "));
	
		return String.format("%s class %s%s%s {",
				Modifier.toString(clazz.getModifiers()),
				clazz.getSimpleName(),
				baseClass,
				ifaces.equals("") ? "" : " implements " + ifaces);
	}
}
