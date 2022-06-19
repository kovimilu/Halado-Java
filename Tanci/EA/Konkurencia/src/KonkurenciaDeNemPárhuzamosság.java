import java.util.List;

public class KonkurenciaDeNemPárhuzamosság {
	public static void main(String[] args) {
		List<Runnable> task1 = List.of(
			() -> System.out.println("11"),
			() -> System.out.println("12"),
			() -> System.out.println("13"),
			() -> System.out.println("14")
		);

		List<Runnable> task2 = List.of(
			() -> System.out.println("21"),
			() -> System.out.println("22"),
			() -> System.out.println("23"),
			() -> System.out.println("24")
		);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				(j == 0 ? task1 : task2).get(i).run();
			}			
		}
	}

}
