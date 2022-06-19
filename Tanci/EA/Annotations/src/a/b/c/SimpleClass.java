package a.b.c;

import java.util.Arrays;

@Annot3(value = "abchjk")
@MarkerAnnot
@CreatedAt(createdAtDate = 20220329, info = "abc")
public class SimpleClass {
	public int fld1;
	
	void overload() { System.out.println("v1 " + this.fld1); }
	void overload(int i) { System.out.println("v2 " + fld1 + " " + i); }
	void overload(String s) { System.out.println("v3 " + fld1 + " " + s); } 
	void overload(int i, String s) { System.out.println("v4 " + fld1); }

	static void iAmStatic(int[] ints) { System.out.println("vst" + Arrays.toString(ints)); }

	@MarkerAnnot()
	@CreatedAt(createdAtDate = 20220329, info = "abc")
	enum Y {X, Z()}

	// The annotation @CreatedAt is disallowed for this location
	class TP<@CreatedAt(createdAtDate = 20220329, info = "abc") T> {}
	
//	@Override
	@CreatedAt(createdAtDate = 20220329, info = "abc")
	public static void main(@CreatedAt(createdAtDate = 20220329, info = "abc") String[] args) {
//		@CreatedAt(createdAtDate = 20220329, info = "abc")
		f1();
	}

	@Annot3("abchjk")
	@CreatedAt
	private static void f1() {
	}

	private static void f2() {
	}

	@CreatedAt(info = "fhgdyesagiu")
	@Override
	public String toString() {
		return super.toString();
	}
}
