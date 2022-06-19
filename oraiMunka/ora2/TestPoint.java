package ora2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TestPoint {

    Pont p;

    @BeforeEach
    void setUp() {
        p = new Pont(1,1);
    }

    @Test
    void testSwap() {
        p.shiftLeft();
        assertEquals(p.getX(), 0);
    }

    @Test
    void testShiftUp() {
        p.shiftUp();
        assertEquals(p.getY(), 2);
    }
}
