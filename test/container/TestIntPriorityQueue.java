package container;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestIntPriorityQueue {

    @Test
    public void test_emptyCreation() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void test_insertOneElement() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        queue.insertElement(5);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(5, queue.element());
    }

    @Test
    public void test_elementDoesNotRemove() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        queue.insertElement(9);
        queue.element();
        assertEquals(1, queue.size());
        assertEquals(9, queue.element());
    }

    @Test
    public void test_ascendingInsertion() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3);

        assertEquals(3, queue.popElement());
        assertEquals(2, queue.popElement());
        assertEquals(1, queue.popElement());
    }

    @Test
    public void test_descendingInsertion() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        queue.insertElement(3);
        queue.insertElement(2);
        queue.insertElement(1);

        assertEquals(3, queue.popElement());
        assertEquals(2, queue.popElement());
        assertEquals(1, queue.popElement());
    }

    @Test
    public void test_duplicateValues() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        queue.insertElement(4);
        queue.insertElement(4);
        queue.insertElement(2);

        assertEquals(4, queue.popElement());
        assertEquals(4, queue.popElement());
        assertEquals(2, queue.popElement());
    }

    @Test
    public void test_sizeDecreasesAfterPop() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        queue.insertElement(1);
        queue.insertElement(2);
        queue.popElement();
        assertEquals(1, queue.size());
    }

    @Test
    public void test_isEmptyAfterPoppingAll() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        queue.insertElement(1);
        queue.popElement();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void test_elementOnEmptyQueueThrows() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        assertThrows(NoSuchElementException.class, () -> queue.element());
    }

    @Test
    public void test_popOnEmptyQueueThrows() {
        IntPriorityQueue queue = new IntPriorityQueue(10);
        assertThrows(NoSuchElementException.class, () -> queue.popElement());
    }

    @Test
    public void test_resizeOnFullQueue() {
        IntPriorityQueue queue = new IntPriorityQueue(2);
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3); // capacité initiale dépassée, redimensionnement attendu

        assertEquals(3, queue.size());
        assertEquals(3, queue.popElement());
        assertEquals(2, queue.popElement());
        assertEquals(1, queue.popElement());
    }


}