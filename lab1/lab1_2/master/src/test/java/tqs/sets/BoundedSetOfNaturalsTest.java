/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(2);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());
    
        assertThrows(IllegalArgumentException.class, () -> setA.add(99));
        assertThrows(IllegalArgumentException.class, () -> setA.add(-1));
        
        setA.add(11);
        assertTrue(setA.contains(11), "add: added element not found in set.");
        assertEquals(2, setA.size(), "add: elements count not as expected.");
    
        assertThrows(IllegalArgumentException.class, () -> setA.add(55));
    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }
    
    @Test
    public void testIntersection() {
        assertTrue(setB.intersects(setC));
        assertFalse(setC.intersects(setB));
        assertTrue(setB.intersects(setA));
    }


}
