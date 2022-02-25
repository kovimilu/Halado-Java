package ora3;

import java.util.Collections;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {

        Supplier<Integer> supp = new Supplier<Integer>() {
            int counter = 0;
            @Override
            public Integer get() {
                return ++counter;
            }
        };
        System.out.println(supp.get());
        System.out.println(supp.get());
        System.out.println(supp.get());
        System.out.println(supp.get());
        System.out.println(supp.get());

        System.out.println();

        int[] megaChad = {0};
        Supplier<Integer> supp2 = () -> ++megaChad[0];
        System.out.println(supp2.get());
        System.out.println(supp2.get());
        System.out.println(supp2.get());

        Runnable r1 = () -> {

        };

        System.out.println();

        Consumer<Integer> con = (Integer n) -> {
            for (int i = 0; i < n; ++i) {
                System.out.println(n);
            }
        };
        con.accept(3);


        System.out.println();

        Consumer<Integer> con2 = (Integer n) -> {
            Random rand = new Random();
            int r = rand.nextInt() % 100 + 1;
            for (int i = 0; i < r; ++i) {
                System.out.println(n);
            }
        };
        con2.accept(5);


        System.out.println();

        int[] counter = {0};
        Consumer<Integer> con3 = (Integer n) -> {
            ++counter[0];
            for (int i = 0; i < counter[0]; ++i) {
                System.out.println(n);
            }
            System.out.println();
        };
        con3.accept(7);
        con3.accept(7);
        con3.accept(7);

        Consumer<Integer> factorial = (Integer n) -> {
            int sum = 1;
            int i = 1;
            while(i <= n) {
                sum *= i;
                ++i;
            }
            System.out.println(sum);
        };
        factorial.accept(5);


        //eof
        }
    }