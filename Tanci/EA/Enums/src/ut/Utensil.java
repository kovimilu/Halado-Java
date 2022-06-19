
package ut;

public enum Utensil {
	X,
	Y(),
	SPOON("kanal"),
	FORK("villa", 123),
	KNIFE("kes");

//	static final Utensil UTENSIL_SPOON = new Ut(...);

	Utensil(String name, int prongs) {
		this.name = name;
		this.prongs = prongs;
	}

	Utensil(String name) {
		this(name, 1);
	}

	Utensil() {
		this("kamu");
	}

	public int prongs;
	public final String name;
//	final Operation op = Operation.SAVE;
	
	@Override
	public String toString() {
//		return name() + this.name();
		return String.format("%s[%s, %s]",
			name(), name, prongs);
	}
}
