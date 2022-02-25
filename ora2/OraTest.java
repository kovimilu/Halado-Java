package ora2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.TimeUnit;


public class OraTest {
    ToBeTested testSubject = new ToBeTested();

    @Test
    void testIterativ() {
       ArrayList<Integer> exp = new ArrayList<>();
       exp.add(1);
       exp.add(1);
       exp.add(2);
       exp.add(3);
       exp.add(5);
       assertEquals(exp, testSubject.iterativ(5));
    }

    @Test
    @Timeout(3)
    void someTest() throws InterruptedException {
        testSubject.elszall();
    }
}
