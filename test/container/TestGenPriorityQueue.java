package container;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGenPriorityQueue {

    @Test
    public void testEmptyCreation() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(10);

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void testInsertOneElement() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(10);

        queue.insertElement(5);

        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(5, queue.element());
    }

    @Test
    public void testElementDoesNotRemove() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(10);

        queue.insertElement(9);

        assertEquals(9, queue.element());
        assertEquals(1, queue.size());
        assertEquals(9, queue.element());
    }

    @Test
    public void testHighestPriorityFirst() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(10);

        queue.insertElement(5);
        queue.insertElement(1);
        queue.insertElement(9);
        queue.insertElement(3);

        assertEquals(9, queue.popElement());
        assertEquals(5, queue.popElement());
        assertEquals(3, queue.popElement());
        assertEquals(1, queue.popElement());
        //comprobamos que la expresión booleana es true
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testDuplicateValues() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(10);

        queue.insertElement(4);
        queue.insertElement(4);
        queue.insertElement(2);

        assertEquals(4, queue.popElement());
        assertEquals(4, queue.popElement());
        assertEquals(2, queue.popElement());
    }

    @Test
    public void testSizeDecreasesAfterPop() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(10);

        queue.insertElement(1);
        queue.insertElement(2);

        assertEquals(2, queue.popElement());
        assertEquals(1, queue.size());
        assertEquals(1, queue.element());
    }

    @Test
    public void testIsEmptyAfterPoppingAllElements() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(10);

        queue.insertElement(1);
        queue.popElement();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void testElementOnEmptyQueueThrows() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(10);

        assertThrows(NoSuchElementException.class, () -> queue.element());
    }

    @Test
    public void testPopOnEmptyQueueThrows() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(10);
        //comprobamos que lance la excepcion especifica en ese momento de codigo, popelement
        assertThrows(NoSuchElementException.class, () -> queue.popElement());
    }

    @Test
    public void testResizeOnFullQueue() {
        GenPriorityQueue<Integer> queue = new GenPriorityQueue<>(2);

        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3);

        assertEquals(3, queue.size());//primero el valor que deberia salir, luego el que devuelve mi programa
        assertEquals(3, queue.popElement());
        assertEquals(2, queue.popElement());
        assertEquals(1, queue.popElement());
    }
    
}