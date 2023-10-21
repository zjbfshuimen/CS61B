import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestArrayDequeGold {
    private String errorString = "";
    private final int outtime = 3000;
    @Test(timeout = outtime)
    public void testfirst() {
        /*
         * 1 for addFirst 2 for addLast
         * 3 for removeFirst     4 for removeLast
         * 5 for size()
         */
        List<Integer> usedMethod = new ArrayList<>();
        int methodNum = 5;
        int[] randomBox = new int[methodNum];
        ArrayDequeSolution<Integer> items = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> actems = new StudentArrayDeque<>();
        for (int i = 0; i < methodNum; i++) {
            randomBox[i] = i;
            items.addFirst(i);
            actems.addFirst(i);
            usedMethod.add(1);
            errorString += "addFirst(" + i + ")\n";
        }


        while (true) {
            int randomNumber = StdRandom.discrete(randomBox);
            switch (randomNumber) {
                case 0:
                    usedMethod.add(4);
                    errorString += "removeLast()\n";
                    assertEquals(errorString,
                            items.removeLast(), actems.removeLast());
                    break;
                case 1:
                    usedMethod.add(5);
                    errorString += "size()\n";
                    if (items.size() != 0 && actems.size() != 0) {
                        usedMethod.add(1);
                        errorString += "removeFirst()\n";
                        assertEquals(errorString,
                            items.removeFirst(), actems.removeFirst());
                    }
                    break;
                case 2:
                    usedMethod.add(5);
                    errorString += "size()\n";
                    assertEquals(items.size(), actems.size());
                    break;
                default:
                    int i = StdRandom.discrete(randomBox);
                    items.addFirst(i);
                    items.addLast(i + 1);
                    usedMethod.add(1);
                    errorString += "addFirst(" + i + ")\n";
                    usedMethod.add(2);
                    errorString += "addFirst(" + (i+1) + ")\n";
                    actems.addFirst(i);
                    actems.addLast(i + 1);

                    usedMethod.add(5);
                    errorString += "size()\n";
                    if (items.size() != 0 && actems.size() != 0) {
                        usedMethod.add(3);
                        errorString += "removeFirst()\n";
                        assertEquals(errorString,
                            items.removeFirst(), actems.removeFirst());
                    }
            }
        }
    }

    private String errorCausedRank(List<Integer> usedMethod) {
        String returnString = "";
        for (Integer a : usedMethod) {
            switch (a) {
                case 1:
                    returnString += "addFirst()\n";
                    break;
                case 2:
                    returnString += "addLast()\n";
                    break;
                case 3:
                    returnString += "removeFirst()\n";
                    break;
                case 4:
                    returnString += "removeLast()\n";
                    break;
                default:
                    returnString += "size()\n";
                    break;
            }
        }
        return returnString;
    }

}
