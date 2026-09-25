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
    //Programación defensiva -> no acepto capacidad invalida
    @Test
    public void test_constructorWithZero(){
        assertThrows(IllegalArgumentException.class,()->new IntPriorityQueue(0));
    }

    @Test
    public void test_constructorWithNegative(){
        assertThrows(IllegalArgumentException.class,()->new IntPriorityQueue(-5));
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
    public void test_elementDoesNotRemove() {//comprobamos que element no quite ningun integrante
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
        queue.insertElement(3);

        assertEquals(3, queue.size());
        assertEquals(3, queue.popElement());
        assertEquals(2, queue.popElement());
        assertEquals(1, queue.popElement());
    }
    @Test
    public void test_iterator(){
        IntPriorityQueue queue = new IntPriorityQueue(5);//la creo con capacidad 5 y añado elementos
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3);

        Iterator<Integer> it= queue.iterator();
        //comprobamos que recorra todos los elementos
        int count=0;
        while(it.hasNext()){
            it.next();
            count++;
        }
        assertEquals(3,count);
    }

    @Test
    public void test_iterator_forEach(){//comprobamos que este bien especificado
        IntPriorityQueue queue = new IntPriorityQueue(5);//la creo con capacidad 5 y añado elementos
        queue.insertElement(1);
        queue.insertElement(2);
        queue.insertElement(3);

        Iterator<Integer> it= queue.iterator();
        int sum = 0;
        int count=0;
        for (int value:queue){
            sum+=value;
            count++;

        }

        assertEquals(3,count,"The number of elements is correct");
        assertEquals(6,sum,"The sum is correct");
    }

    //que cuando no haya mas, next lance exception
    @Test
    public void test_nextOnExhaustedIteratorThrows() {
        IntPriorityQueue queue = new IntPriorityQueue(5);
        queue.insertElement(1);

        Iterator<Integer> it = queue.iterator();
        it.next(); // consomme l'unique élément
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }


}