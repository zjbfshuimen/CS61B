
import static  org.junit.Assert.*;
import org.junit.Test;

public class testFlik {
    @Test
    public void testF(){
        int a = 128;
        int b = 128;
        boolean actual = Flik.isSameNumber(a, b);
        boolean exp = true;
        assertEquals(exp, actual);

    }

}
