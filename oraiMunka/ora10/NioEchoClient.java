import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioEchoClient {
    private static SocketChannel client;
    private static NioEchoClient instance;

    public static NioEchoClient start() {
        if (instance == null)
            instance = new NioEchoClient();

        return instance;
    }

    public static void stop() throws IOException {
        client.close();
    }

    private NioEchoClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", NioEchoServer.PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        try {
            client.write(buffer);
            buffer.clear();

            client.read(buffer);
            String response = new String(buffer.array()).trim();
            System.out.println("response=" + buffer + "=" + response);

            buffer.clear();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
