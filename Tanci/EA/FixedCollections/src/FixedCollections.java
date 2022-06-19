import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FixedCollections {
	public static void main(String[] args) {
		Map<String, Integer> txtToInt = Map.of("KP", 1234, "NE", 64737);
//		txtToInt.put("KP", 1234);
//		txtToInt.put("NE", 64737);

		System.out.println(txtToInt);
		for (String key : txtToInt.keySet()) {
			System.out.println("key " + key);
		}
		for (Integer value : txtToInt.values()) {
			System.out.println("value " + value);
		}
		for (Map.Entry<String, Integer> entry : txtToInt.entrySet()) {
			System.out.println("key " + entry.getKey() + ", value " + entry.getValue());
		}
		for (var entry : txtToInt.entrySet()) {
			System.out.println("key " + entry.getKey() + ", value " + entry.getValue());
		}

		Entry<String, Integer> entry = Map.entry("fdsgahj", 643);
		System.out.println(Map.entry("fdsgahj", 643));
		System.out.println(entry("fdsgahj", 643));
	}

	public static void main2(String[] args) {
		List<Integer> intList = List.of();
		Set<Integer> intSet = Set.of();
		Map<String, Integer> txtToInt = Map.of();

		List<Integer> intList1 = new ArrayList<>();
		List<Integer> intList2 = new LinkedList<>();
		Set<Integer> intSet1 = new HashSet<>();
		Set<Integer> intSet2 = new TreeSet<>();
		Map<String, Integer> txtToInt1 = new HashMap<>();
		Map<String, Integer> txtToInt2 = new TreeMap<>();

		System.out.println(intList.getClass().getName());
		System.out.println(intList1.getClass().getName());
		System.out.println(intList2.getClass().getName());
		
		// immutable ("const")
		String composite = "Hello";
		composite += "World";
		System.out.println(composite);
		
		// UnsupportedOperationException
//		intList.add(123);
		
//		List<Integer> intList3 = () -> ...;

//		intList1.add(3124);
//		intList1.add(-7456);
//		System.out.println(intList1);
//
//		List<Integer> intList3 = List.of(3124, -7456);
//		System.out.println(intList3);
//
//		for (Integer elem : intList1) {
//			System.out.println(elem);
//		}
//
//		for (Integer elem : intList3) {
//			System.out.println(elem);
//		}

		Set<Integer> intSet4 = Set.of(213, 5342, 57436, 23, -4532);
		System.out.println(intSet4);
		System.out.println(intSet4.equals(Set.of(213, 5342, 57436, 23, -4532)));
		System.out.println(new int[] {213, 5342, 57436, 23, -4532});
		System.out.println(new int[] {213, 5342, 57436, 23, -4532}.equals(new int[] {213, 5342, 57436, 23, -4532}));

		int[] simple1 = {1};
		int[] simple2 = {1};
		Set<int[]> wrongUsage = new HashSet<>();
		wrongUsage.add(simple1);
		System.out.println(wrongUsage.contains(simple1));
		System.out.println(wrongUsage.contains(simple2));
		
//		Map<String, Integer> txtToInt = Map.of();

		// java.lang.IllegalArgumentException: duplicate element: 1
//		Set<Integer> intSet5 = Set.of(1, 1);

		Set<Integer> intSet6 = Set.of(213, 5342, 57436, 23, -4532);
	}
}
