package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M];
        int N = oomages.size();
        for (Oomage item : oomages) {
            int index = (item.hashCode() & 0x7FFFFFFF) % M;
            buckets[index]++;
        }
        for (int i = 0; i < M; i++) {
            if (!valid(buckets[i], N)) {
                return false;
            }
        }
        return true;

        /* 
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
    }

    private static boolean valid(int num, int N) {
        double max = N / 2.5;
        double min = N / 50;
        return num > min && num < max;
    }
}
