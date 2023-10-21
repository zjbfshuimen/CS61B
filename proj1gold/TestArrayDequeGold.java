import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    private final int outtime = 3000;
    @Test(timeout = outtime)
    public void testfirst() {
        int methodNum = 5;
        int[] randomBox = new int[methodNum];
        ArrayDequeSolution<Integer> items = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> actems = new StudentArrayDeque<>();
        for (int i = 0; i < methodNum; i++) {
            randomBox[i] = i;
            items.addFirst(i);
            actems.addFirst(i);
        }

        while (true) {
            int randomNumber = StdRandom.discrete(randomBox);
            switch (randomNumber) {
                case 0: assertEquals("your ArrayDeque.removeLast is not correct ",
                        items.removeLast(), actems.removeLast());
                    break;
                case 1:
                    if (items.size() != 0 && actems.size() != 0) {
                        assertEquals("your ArrayDeque.removeFirst is not correct",
                                items.removeFirst(), actems.removeFirst());
                    }
                    break;
                case 2:
                    assertEquals(items.size(), actems.size());
                    break;
                case 3:
                default:
                    int i = StdRandom.discrete(randomBox);
                    items.addFirst(i);
                    items.addLast(i + 1);
                    actems.addFirst(i);
                    actems.addLast(i + 1);
                    if (items.size() != 0 && actems.size() != 0) {
                        assertEquals("After addFirst and addLast, removeFirst(), in which case "
                                        + "your ArrayDeque.removeFirst is not correct",
                                items.removeFirst(), actems.removeFirst());
                    }

            }
        }

    }
}
