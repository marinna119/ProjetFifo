package container;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * File de priorité générique, représentée par un tas (max-heap) stocké
 * dans un tableau. Le type d'élément doit implémenter {@link Comparable}
 * pour définir l'ordre de priorité.
 *
 * @param <E> type des éléments stockés, comparables entre eux
 */
public class GenPriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private E[] lista;
    private int nb = 0;

    /**
     * Construit une file de priorité vide avec la capacité initiale donnée.
     *
     * @param capacity capacité initiale du tableau interne ; doit être strictement positive
     * @throws IllegalArgumentException si capacity est négative ou nulle
     */

    public GenPriorityQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be strictly positive");
        }
        this.lista = (E[]) new Object[capacity];
    }

    @Override
    public boolean insertElement(E e) {
        if (nb == lista.length) {
            alargar(lista.length * 2);
        }
        lista[nb] = e;
        nb++;
        subir(nb - 1);
        return true;
    }

    @Override
    public E element() {
        if (isEmpty()) throw new NoSuchElementException();
        return lista[0];
    }

    @Override
    public E popElement() {
        E e = element();
        nb--;
        lista[0] = lista[nb];
        lista[nb] = null;
        descender(0);
        return e;
    }

    @Override
    public boolean isEmpty() {
        return nb == 0;
    }

    @Override
    public int size() {
        return nb;
    }

    /**
     * Agrandit le tableau interne à la nouvelle capacité donnée, en
     * conservant tous les éléments déjà présents. Appelée automatiquement
     * par insertElement lorsque la file est pleine.
     *
     * @param newCapacity nouvelle capacité du tableau interne
     */

    public void alargar(int newCapacity) {
        E[] newHeap = (E[]) new Object[newCapacity];
        for (int i = 0; i < nb; i++) {
            newHeap[i] = lista[i];
        }
        lista = newHeap;
    }

    private void subir(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (lista[i].compareTo(lista[parent]) <= 0) break;
            intercambio(i, parent);
            i = parent;
        }
    }

    private void descender(int i) {
        while (true) {
            int gauche = 2 * i + 1;
            int droite = 2 * i + 2;
            int plusGrand = i;

            if (gauche < nb && lista[gauche].compareTo(lista[plusGrand]) > 0) plusGrand = gauche;
            if (droite < nb && lista[droite].compareTo(lista[plusGrand]) > 0) plusGrand = droite;
            if (plusGrand == i) break;

            intercambio(i, plusGrand);
            i = plusGrand;
        }
    }

    private void intercambio(int i, int j) {
        E tmp = lista[i];
        lista[i] = lista[j];
        lista[j] = tmp;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < nb;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return lista[i++];
            }
        };
    }
}
