import org.junit.Test;
import static org.junit.Assert.*;
public class testAD {
    @Test
    public void testGrow(){
        ArrayDeque<Integer> a = new ArrayDeque<>();
        a.addFirst(4);
        a.addFirst(5);
        a.addFirst(7);
        a.addFirst(8);
        a.addFirst(9);
        a.addFirst(1);
        a.addFirst(4);
        a.addFirst(9);
        a.addLast(10);
        a.removeFirst();
        a.removeFirst();
        a.removeFirst();
        a.removeFirst();
        a.removeFirst();
        a.removeFirst();
        a.removeFirst();
        a.printDeque();
    }
}
