package ora2;

import java.util.ArrayList;

public class ToBeTested {

    public ArrayList<Integer> iterativ(int n) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(1);
        for (int i = 1; i < n; ++i) {
            int sum = 0;
            sum = i - 1 + i;

            result.add(sum);
        }
        return result;
    }

    public void elszall() {
        while(true) {

        }
    }


}
