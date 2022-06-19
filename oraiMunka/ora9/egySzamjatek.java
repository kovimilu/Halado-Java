package ora9;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class egySzamjatek {

    private final Map<String, List<Integer>> nums = new HashMap<>();
    private final Random random = new Random(System.currentTimeMillis());
    private final List<Thread> threads = new ArrayList<>();
    private volatile boolean stop = false;

   public void start() {
        int round = 10;
        makePlayers(5);

        while(round > 0) {



            round--;
        }
        stop = true;
   }

    private void makePlayers(int numOfPlayers) {
        for (int i = 0; i < numOfPlayers; ++i) {
            makePlayer(String.format("%d", i));
        }
    }

    private void makePlayer(String name) {
        var playerThread = new Thread(() -> {
           while(!stop) {
               var r = threadSafeRandomInt();
               synchronized (nums) {
                   nums.putIfAbsent(name, new ArrayList<>());
                   nums.computeIfPresent(name, (s, integers) -> {
                       integers.add(r);
                       return integers;
                   });
               }
               System.out.println(name + " Guessed: " + r);
           }
        });
        playerThread.start();
        threads.add(playerThread);
    }

    private int threadSafeRandomInt() {
        return ThreadLocalRandom.current().nextInt(0, 1000000);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            System.out.println("kaka");
        }
    }


}
