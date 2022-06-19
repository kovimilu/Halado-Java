package most.important.code;

import java.util.ArrayList;
import java.util.List;

public class Listifier {
	private List<Integer> elems = new ArrayList<>();

	public Listifier add(int n) {
		int value = 2 * n + 1;
		
		for (var val : elems) {
			value += val;
		}
		
		elems.add(value);
		return this;
	}

	public int get(int idx) {
		return elems.get(idx);
	}

}
