package ora8.c;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

//@Retention(RetentionPolicy.RUNTIME)
@Retention(RUNTIME)
//@Target(METHOD)
//@Target(ElementType.METHOD)
//@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CreatedAt {
	int createdAtDate() default -987;
	String info() default "bvcrs";
}
