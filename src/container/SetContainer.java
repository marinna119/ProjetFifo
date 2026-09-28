package container;

import java.util.NoSuchElementException;

public interface SetContainer<E> extends Iterable<E> {
    /** Add specified element into this set if not already present
     *
     * @return true if the element was successfully added
     */
    boolean insertElement(E e);

    /**
     * Test if specified element is present in the set
     *
     * @return whether the tested element is present in the set
     */
    boolean contains(E e);

    /** Returns true if this queue contains no elements. */
    boolean isEmpty();

    /** Returns the number of elements contained in this queue */
    int size();
}