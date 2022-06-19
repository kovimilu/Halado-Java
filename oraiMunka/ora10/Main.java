package ora10;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args)  {

        if(args.length >= 3)
        {
            String filename = args[0];
            Map<Long, byte[]> posByte = new HashMap<>();
            System.out.println("Filename: " + filename);

            for(int i = 2; i < args.length - 1; ++i) {
                posByte.put(Long.parseLong(args[i - 1]), args[i].getBytes());
                System.out.println(posByte);
            }
        }
        else System.out.println("Not Enough Arguments!");



        String sep = File.separator;

        byte[] illustration = {1, 2};
        ByteBuffer wrapped = ByteBuffer.wrap(illustration);

        try (
            var fc = FileChannel.open(Path.of("src", "UsualIO.java"), StandardOpenOption.READ);
        ) {
            var bb = ByteBuffer.allocate(4);
            fc.read(bb);
            System.out.println(bb);
        }

    ////////////
    }
}