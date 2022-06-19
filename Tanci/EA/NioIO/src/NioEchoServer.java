import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioEchoServer {
    public static final int PORT = 12345;

    private static final String POISON_PILL = "POISON_PILL";
    private static final String POISON_PILL_END = "POISON_PILL_END";
    private static volatile boolean isRunning = true;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", PORT));
        serverSocket.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        
        ByteBuffer buffer = ByteBuffer.allocate(256);

        while (isRunning) {
//            selector.select();
            selector.select(10 /* millis */);
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            for (SelectionKey key : selectedKeys) {
                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }

                if (key.isReadable()) {
                    answerWithEcho(buffer, key);
                }
            }

            selectedKeys.clear();
        }
    }

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);

        if (new String(buffer.array()).trim().equals(POISON_PILL_END)) {
            client.close();
            isRunning = false;
            System.out.println("Finishing up");
        } else if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        } else {
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }
}
