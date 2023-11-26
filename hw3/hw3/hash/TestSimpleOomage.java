package hw3.hash;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        assertNotEquals((new SimpleOomage(5, 10, 30)).hashCode(), (new SimpleOomage(5, 10, 25)).hashCode());
        assertNotEquals((new SimpleOomage(5, 100, 30)).hashCode(), (new SimpleOomage(5, 200, 25)).hashCode());
        assertNotEquals((new SimpleOomage(55, 105, 30)).hashCode(), (new SimpleOomage(50, 10, 250)).hashCode());
        assertNotEquals((new SimpleOomage(155, 10, 205)).hashCode(), (new SimpleOomage(50, 105, 255)).hashCode());
        assertNotEquals((new SimpleOomage(5, 145, 205)).hashCode(), (new SimpleOomage(5, 175, 25)).hashCode());
        assertNotEquals((new SimpleOomage(5, 10, 185)).hashCode(), (new SimpleOomage(5, 180, 205)).hashCode());
        assertNotEquals((new SimpleOomage(195, 125, 135)).hashCode(), (new SimpleOomage(55, 10, 25)).hashCode());
        assertNotEquals((new SimpleOomage(85, 10, 75)).hashCode(), (new SimpleOomage(55, 10, 25)).hashCode());
        assertNotEquals((new SimpleOomage(95, 5, 55)).hashCode(), (new SimpleOomage(55, 75, 25)).hashCode());
        assertEquals((new SimpleOomage(5, 10, 30)).hashCode(), (new SimpleOomage(5, 10, 30)).hashCode());
        assertEquals((new SimpleOomage(50, 100, 200)).hashCode(), (new SimpleOomage(50, 100, 200)).hashCode());
        assertEquals((new SimpleOomage(55, 105, 200)).hashCode(), (new SimpleOomage(55, 105, 200)).hashCode());
        assertEquals((new SimpleOomage(55, 120, 220)).hashCode(), (new SimpleOomage(55, 120, 220)).hashCode());

    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }


    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
        System.out.println(Integer.MAX_VALUE);
    }
}
