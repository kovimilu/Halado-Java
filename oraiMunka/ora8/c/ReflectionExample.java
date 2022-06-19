
package ora8.c;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionExample /* extends Object */ {
	static int staticFld;
	int instanceFld;

	private static <T> T convertForMe(int n, Class<T> out) {
		if (out.getCanonicalName().equals("java.lang.String")) {
			return (T)("converted: " + n);
		}

		return null;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		List<String> result = db.get("SELECT s FROM ...", String.class);
		System.out.println(convertForMe(314, String.class).length());
	}

	public static void main4(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Arrays.stream(SimpleClass.class.getAnnotations())
//		.forEach(System.out::println);
//		System.out.println("---------------------");
		Arrays.stream(SimpleClass.class.getDeclaredAnnotations())
		.forEach(System.out::println);

		CreatedAt annot = SimpleClass.class.getDeclaredAnnotation(CreatedAt.class);
		System.out.println(annot);
		System.out.println(annot.createdAtDate());
		System.out.println(annot.info());
	}

	public static void main3(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<SimpleClass> c1 = SimpleClass.class;

		Method[] methods = c1.getMethods();
		Method[] dMethods = c1.getDeclaredMethods();

		Arrays.stream(methods).forEach(System.out::println);
		System.out.println("---------------------");
		Arrays.stream(dMethods).forEach(System.out::println);

		Field declaredField = c1.getDeclaredField("fld1");
		System.out.println(declaredField);

		// Class<?>...
		Method m1 = c1.getDeclaredMethod("overload");
		Method m2 = c1.getDeclaredMethod("overload", int.class);
		Method m3 = c1.getDeclaredMethod("overload", String.class);
		Method m4 = c1.getDeclaredMethod("overload", int.class, String.class);
		System.out.println(m1);
		System.out.println(m2);
		System.out.println(m3);
		System.out.println(m4);

		var simpleClass = new SimpleClass();
		simpleClass.fld1 = 874964;
		simpleClass.overload();
		simpleClass.overload(1);
		simpleClass.overload("bvfds");
		simpleClass.overload(4653, "gfds");
//		simpleClass.iAmStatic(new int[] {1,2,3});
		SimpleClass.iAmStatic(new int[] {1,2,3});

		// Object...
		m1.invoke(simpleClass);
		m2.invoke(simpleClass, 1324);
		m3.invoke(simpleClass, "hgbuicvowr");
		m4.invoke(simpleClass, -1324, "bcsa");
		m4.invoke(simpleClass, new Object[] {-1324, "bcsa"});

//		c1.getDeclaredMethod("iAmStatic", int[].class)
//			.invoke(simpleClass, new int[] {1,2,3});
		c1.getDeclaredMethod("iAmStatic", int[].class)
			.invoke(null, new int[] {1,2,3});
		c1.getDeclaredMethod("iAmStatic", int[].class)
			.invoke(null, new Object[] {new int[] {1,2,3}});
	}

	public static void main2(String[] args) throws ClassNotFoundException {
		Class<SimpleClass> c1 = SimpleClass.class;
		Class<ReflectionExample> c2 = ReflectionExample.class;

		SimpleClass obj = new SimpleClass();
		Class<? extends SimpleClass> clazz = obj.getClass();

		Runnable r = () -> {};
		Class<? extends Runnable> clazzRunnable = r.getClass();
		
		Class<?> class2 = Class.forName("ora8.c.ReflectionExample");
		
		var field = ReflectionExample.staticFld;

		System.out.println(clazzRunnable.getName());
		System.out.println(clazzRunnable.getCanonicalName());
		System.out.println(clazzRunnable.getSimpleName());

		System.out.println("---------------------");
		System.out.println(c2.getName());
		System.out.println(c2.getCanonicalName());
		System.out.println(c2.getSimpleName());

		System.out.println("---------------------");
		System.out.println(int.class.getName());
		System.out.println(int.class.getCanonicalName());
		System.out.println(int.class.getSimpleName());

		System.out.println("---------------------");
		System.out.println(Integer.class.getName());
		System.out.println(Integer.class.getCanonicalName());
		System.out.println(Integer.class.getSimpleName());

		System.out.println("---------------------");
		System.out.println(int[].class.getName());
		System.out.println(char[].class.getName());
		System.out.println(boolean[].class.getName());
		System.out.println(double[].class.getName());
		System.out.println(String[].class.getName());
		System.out.println(Integer[].class.getName());

		System.out.println("---------------------");
		System.out.println(List.class.getName());
//		System.out.println(List<Integer>.class.getName());

		List<Integer> list1 = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		System.out.println(list1.getClass().getName());
		System.out.println(list2.getClass().getName());
	}
}
