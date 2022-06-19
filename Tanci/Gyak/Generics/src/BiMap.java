import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

//public class BiMap<K, V> implements SortedMap<K, V> {
//public class BiMap<K, V> extends TreeMap<K, V> {

//public class BiMap<K extends Comparable<K>, V extends Comparable<V>> {
public class BiMap<K, V> {
	SortedMap<K, V> fwd;
	SortedMap<V, K> back;

	private BiMap() {
		this.fwd = new TreeMap<>();
		this.back = new TreeMap<>();
	}
	
	private BiMap(Comparator<K> keyComp, Comparator<V> valueComp) {
		this.fwd = new TreeMap<>(keyComp);
		this.back = new TreeMap<>(valueComp);
	}
	
	/** Van természetes rendezésem. */
	public static <K1 extends Comparable<K1>, V1 extends Comparable<V1>> BiMap<K1, V1> create() {
		// Cannot make a static reference to the non-static type V
//		return new BiMap<K1, V1>();
		return new BiMap<>();
	}

	/** Nincs természetes rendezésem. */
	public static <K1, V1> BiMap<K1, V1> create(Comparator<K1> keyComp, Comparator<V1> valueComp) {
		return new BiMap<>(keyComp, valueComp);
	}
	
	public void put(K key, V value) {
		fwd.put(key, value);
		back.put(value, key);
	}

	public V getFwd(K key) {
		return fwd.get(key);
	}

	public K getBack(V backKey) {
		return back.get(backKey);
	}
	
//	public void putAll(List<K> keys, List<V> values) {
	public void putAll(List<? extends K> keys, List<? extends V> values) {
		if (keys.size() != values.size())   throw new UnsupportedOperationException();

		IntStream.range(0, keys.size())
			.forEach(i -> put(keys.get(i), values.get(i)));
//		for (int i = 0; i < keys.size(); i++) {
//			put(keys.get(i), values.get(i));
//		}
	}
}
