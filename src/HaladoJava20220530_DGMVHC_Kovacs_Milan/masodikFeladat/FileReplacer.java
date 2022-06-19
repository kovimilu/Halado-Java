package HaladoJava20220530_DGMVHC_Kovacs_Milan.masodikFeladat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.parseInt;

class Replacer {
    public int from, to, step, shift;

    public Replacer(int from, int to, int step, int shift) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.shift = shift;
    }
}

public class FileReplacer {

    public static void main(String[] args) throws IOException {
        final int PORT = 60123;

        List<Replacer> replacers = new ArrayList<>();

        AtomicBoolean serverRunning = new AtomicBoolean(true);

        AtomicInteger n = new AtomicInteger(1);


        Files.copy(Path.of("src", "HaladoJava20220530_DGMVHC_Kovacs_Milan", "in.txt"),
                Path.of("src", "HaladoJava20220530_DGMVHC_Kovacs_Milan","out" + 0 + ".txt"), StandardCopyOption.REPLACE_EXISTING);


        try (
                var ss = new ServerSocket(PORT);
        ) {
            new Thread(() -> {
                while(serverRunning.get()) {
                    if (replacers.size() != 0) {
                        try {
                            Files.copy(Path.of("src", "HaladoJava20220530_DGMVHC_Kovacs_Milan","out" + (n.get() - 1) + ".txt"),
                                    Path.of("src", "HaladoJava20220530_DGMVHC_Kovacs_Milan", "out" + n.get() + ".txt"), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        n.getAndIncrement();


                        FileChannel fc = null;
                        FileChannel fcOut = null;
                        try {
                            fc = FileChannel.open(Path.of("src", "HaladoJava20220530_DGMVHC_Kovacs_Milan","out" + (n.get() - 1) + ".txt"), StandardOpenOption.READ);
                            fcOut = FileChannel.open(Path.of("src", "HaladoJava20220530_DGMVHC_Kovacs_Milan","out" + (n.get() - 1) + ".txt"), StandardOpenOption.WRITE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        while (replacers.size() > 0){
                            Replacer rr = replacers.get(0);
                            replacers.remove(0);

                                var bb = ByteBuffer.allocate(rr.to - rr.from);
                                try {
                                    fc.position((long)rr.from);
                                    fc.read(bb);

                                    for(int i = 0; i < rr.to - rr.from; i = i + rr.step) {
                                        //System.out.println(i + "\n");
                                        //if(i > 0) bb.flip();
                                        byte tempShift = (byte) (bb.get(i) + rr.shift);
                                        //System.out.println(bb.get(i) + rr.shift);
                                        //bb.flip();
                                        bb.put(i, tempShift);
                                    }
                                    bb.flip();
                                    fcOut.position((long)rr.from);
                                    fcOut.write(bb);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }).start();

            while (serverRunning.get()) {

                Socket s = ss.accept();
                var pw = new PrintWriter(s.getOutputStream());
                var sc = new Scanner(s.getInputStream());

                String txt2 = sc.nextLine();
                //System.out.println(txt2);
                if (txt2.equals("EXIT")) serverRunning.set(false);
                else {
                    String[] parts = txt2.split(" ");
                    Replacer r = new Replacer(parseInt(parts[0]), parseInt(parts[1]), parseInt(parts[2]), parseInt(parts[3]));
                    replacers.add(r);
                    //System.out.println(replacers.get(0));
                }
                //System.out.println(replacers.get(0));


            }
        }
    }

}

