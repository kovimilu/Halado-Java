import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NioEchoTest {
    Thread serverThread;
    NioEchoClient client;

    @BeforeEach
    public void setup() throws IOException, InterruptedException {
        serverThread = new Thread(() -> {
			try {
				String[] fakeArgs = "".split(" ");
				NioEchoServer.main(fakeArgs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

        serverThread.start();
        TimeUnit.MILLISECONDS.sleep(100);

        client = NioEchoClient.start();
    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect() {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }

    @AfterEach
    public void teardown() throws IOException {
        client.sendMessage("POISON_PILL_END");
        NioEchoClient.stop();
    }
}
