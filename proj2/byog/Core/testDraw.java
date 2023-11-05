package byog.Core;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;


public class testDraw {


    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        int count = 0;
        for (int i = 0; i < 103000; i++) {
            int r = RANDOM.nextInt(4);
            if ( r == 4) {
                count ++;
            }
        }
        System.out.println(count);
    }
}