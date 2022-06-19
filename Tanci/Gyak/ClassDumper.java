import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassDumper {

    public String dump(Class<?> clazz) {
        var superClass = getNonObjectSuperclass(clazz);
        var interfaces = getInterfacesFormatted(clazz);
        var variables = getVariablesFormatted(clazz);
        var methods = getMethodsFormatted(clazz);

        StringBuilder builder = new StringBuilder(clazz.toGenericString() + " ");

        superClass.ifPresent(superClazz -> builder.append("extends ").append(superClazz).append(" "));
        interfaces.ifPresent(interfaceString -> builder.append("implements ").append(interfaceString).append(" "));

        builder.append("{\n");
        variables.ifPresent(builder::append);
        builder.append("\n");


        methods.ifPresent(builder::append);
        builder.append("}");

        return builder.toString();
    }

    public Optional<String> getNonObjectSuperclass(Class<?> clazz) {
        var superClass = Optional.ofNullable(clazz.getSuperclass());
        return superClass.map(Class::getName);
    }

    public Optional<String> getInterfacesFormatted(Class<?> clazz) {
        var interfaces = clazz.getInterfaces();
        var interfaceString = Arrays.stream(interfaces).map(Class::getName).collect(Collectors.joining(", "));

        if (interfaceString.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(interfaceString);
    }

    public Optional<String> getVariablesFormatted(Class<?> clazz) {
        var variables = clazz.getDeclaredFields();
        var variablesString = Arrays.stream(variables).map(ClassDumper::formatField).map(stringyfied -> "    " + stringyfied + ";\n").collect(Collectors.joining());

        if (variablesString.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(variablesString);
    }

    public Optional<String> getMethodsFormatted(Class<?> clazz) {
        var methods = clazz.getDeclaredMethods();
        var methodsString = Arrays.stream(methods).filter(Predicate.not(Method::isSynthetic)).map(ClassDumper::formatMethod).map(stringified -> "    " + stringified + "\n").collect(Collectors.joining());

        if (methodsString.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(methodsString);

    }


    private static String formatField(Field field) {
        int mod = field.getModifiers();
        return (((mod == 0) ? "" : (Modifier.toString(mod) + " "))
                + field.getType().getTypeName() + " "
                + field.getName());
    }

    private static String formatMethod(Method method) {
        int mod = method.getModifiers();
        return (((mod == 0) ? "" : (Modifier.toString(mod) + " "))
                + method.getReturnType().getTypeName() + " "
                + method.getName())
                + "(...) { /* method body */ }";
    }
}
