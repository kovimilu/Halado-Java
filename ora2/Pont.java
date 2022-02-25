package ora2;

public class Pont {
    int x, y;

    Pont(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void shiftLeft() {
        this.x += -1;
    }

    public void shiftUp() {
        this.y += 1;
    }

    public void swapPoints() {
        int temp = x;
        this.x =y;
        this.y = temp;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
