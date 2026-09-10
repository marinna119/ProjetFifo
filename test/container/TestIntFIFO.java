package container;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestIntFIFO {

    @Test
    public void test_emptyCreation() {
        IntFIFO queue = new IntFIFO(10);
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void test_insertOneElement() {
        IntFIFO queue = new IntFIFO(10);
        queue.insertElement(5);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(5, queue.element());
    }

    @Test
    public void test_insertSeveralElements() {
        IntFIFO queue = new IntFIFO(10);
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3);
        assertEquals(3, queue.size());
    }

    @Test
    public void test_elementDoesNotRemove() {
        IntFIFO queue = new IntFIFO(10);
        queue.insertElement(7);
        queue.element();
        assertEquals(1, queue.size());
        assertEquals(7, queue.element());
    }

    @Test
    public void test_fifoOrderRespected() {
        IntFIFO queue = new IntFIFO(10);
        queue.insertElement(10);
        queue.insertElement(20);
        queue.insertElement(30);

        assertEquals(10, queue.popElement());
        assertEquals(20, queue.popElement());
        assertEquals(30, queue.popElement());
    }

    @Test
    public void test_sizeDecreasesAfterPop() {
        IntFIFO queue = new IntFIFO(10);
        queue.insertElement(1);
        queue.insertElement(2);
        queue.popElement();
        assertEquals(1, queue.size());
    }

    @Test
    public void test_isEmptyAfterPoppingAll() {
        IntFIFO queue = new IntFIFO(10);
        queue.insertElement(1);
        queue.popElement();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void test_elementOnEmptyQueueThrows() {
        IntFIFO queue = new IntFIFO(10);
        assertThrows(NoSuchElementException.class, () -> queue.element());
    }

    @Test
    public void test_popOnEmptyQueueThrows() {
        IntFIFO queue = new IntFIFO(10);
        assertThrows(NoSuchElementException.class, () -> queue.popElement());
    }

    @Test
    public void test_resizeOnFullQueue() {
        IntFIFO queue = new IntFIFO(2);
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3); // capacité initiale dépassée, redimensionnement attendu

        assertEquals(3, queue.size());
        assertEquals(1, queue.popElement());
        assertEquals(2, queue.popElement());
        assertEquals(3, queue.popElement());
    }

    @Test
    public void test_circularBehavior() {
        IntFIFO queue = new IntFIFO(3);
        queue.insertElement(1);
        queue.insertElement(2);
        queue.popElement();      // libère la première case du tableau circulaire
        queue.insertElement(3);
        queue.insertElement(4);  // doit boucler sur le tableau

        assertEquals(2, queue.popElement());
        assertEquals(3, queue.popElement());
        assertEquals(4, queue.popElement());
    }


}