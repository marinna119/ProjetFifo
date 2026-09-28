package container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGenSet {

    //para no tener que repetir en cada test, asi es mas rapido
    private GenSet<Integer> setOf(int... values) {
        GenSet<Integer> set = new GenSet<>();
        for (int v : values) {
            set.insertElement(v);
        }
        return set;
    }

    private <E extends Comparable<E>> List<E> toList(GenSet<E> set) {
        List<E> result = new ArrayList<>();
        for (E e : set) {
            result.add(e);
        }
        return result;
    }

    @Test
    public void testEmptyCreation() {
        GenSet<Integer> set = new GenSet<>();

        assertTrue(set.isEmpty());
        assertEquals(0, set.size());
    }

    @Test
    public void testInsertOneElement() {
        GenSet<Integer> set = new GenSet<>();

        assertTrue(set.insertElement(5));

        assertFalse(set.isEmpty());
        assertEquals(1, set.size());
        assertTrue(set.contains(5));
    }

    @Test
    public void testInsertDuplicateReturnsFalseAndKeepsSize() {
        GenSet<Integer> set = setOf(5, 3, 8);

        assertFalse(set.insertElement(3));//esta repetido -> quiero que devuelva false la funcion
        assertFalse(set.insertElement(5));
        assertEquals(3, set.size());
    }

    @Test
    public void testContainsOnEmptySet() {
        assertFalse(new GenSet<Integer>().contains(1));
    }

    @Test
    public void testContainsPresentAndAbsentElements() {
        GenSet<Integer> set = setOf(50, 30, 70, 20, 40, 60, 80);

        // présents
        assertTrue(set.contains(50));
        assertTrue(set.contains(20));
        assertTrue(set.contains(80));
        assertTrue(set.contains(40));
        // absents
        assertFalse(set.contains(10));
        assertFalse(set.contains(90));
        assertFalse(set.contains(55));
    }

    @Test
    public void testSizeCountsOnlyDistinctElements() {
        GenSet<Integer> set = setOf(4, 2, 6, 2, 4, 1, 6);

        assertEquals(4, set.size());
    }

    @Test
    public void testInsertNullThrows() {//compruebo que funcione el lanzar esta excepción
        GenSet<Integer> set = new GenSet<>();

        assertThrows(NullPointerException.class, () -> set.insertElement(null));
    }

    @Test
    public void testContainsNullThrows() {
        GenSet<Integer> set = new GenSet<>();

        assertThrows(NullPointerException.class, () -> set.contains(null));
    }

    @Test
    public void testIteratorOnEmptySet() {
        assertFalse(new GenSet<Integer>().iterator().hasNext());
    }

    @Test
    public void testIteratorGivesAscendingOrderWhateverTheInsertionOrder() {//probar que lo devuelva ordenado
        GenSet<Integer> set = setOf(50, 30, 70, 20, 40, 60, 80, 10, 90, 65);

        assertEquals(Arrays.asList(10, 20, 30, 40, 50, 60, 65, 70, 80, 90), toList(set));
    }

    @Test
    public void testIteratorWithIncreasingInsertion() {
        // arbre dégénéré (une seule branche droite)
        GenSet<Integer> set = setOf(1, 2, 3, 4, 5);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5), toList(set));
    }

    @Test
    public void testIteratorWithDecreasingInsertion() {
        // arbre dégénéré (une seule branche gauche)
        GenSet<Integer> set = setOf(5, 4, 3, 2, 1);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5), toList(set));
    }


    @Test
    public void testIteratorOnLargeDegenerateTree() {
        // l'itérateur est itératif : un arbre très profond ne doit pas faire déborder la pile
        GenSet<Integer> set = new GenSet<>();
        for (int i = 1; i <= 5000; i++) {
            set.insertElement(i);
        }

        int expected = 1;
        for (int value : set) {
            assertEquals(expected, value);
            expected++;
        }
        assertEquals(5001, expected);
    }

    @Test
    public void testGenericityWithStrings() {
        GenSet<String> set = new GenSet<>();
        set.insertElement("pear");
        set.insertElement("apple");
        set.insertElement("fig");

        assertTrue(set.contains("fig"));
        assertFalse(set.contains("kiwi"));
        assertEquals(Arrays.asList("apple", "fig", "pear"), toList(set));
    }

    @Test
    public void testNextOnExhaustedIteratorThrows() {
        GenSet<Integer> set = setOf(1);

        Iterator<Integer> it = set.iterator();
        it.next();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

}