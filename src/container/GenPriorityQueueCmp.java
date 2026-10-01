package container;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GenPriorityQueueCmp<E> implements Queue<E>{

    private E queue[];
    private int nb = 0;//number of actual elements
    private final Comparator<? super E> comparator;


    public GenPriorityQueueCmp(int capacity, Comparator<? super E> comparator) {
        if(capacity<=0){
            throw new IllegalArgumentException("capacity must be strictly positive");
        }
        this.queue = (E[]) new Object[capacity];
        this.comparator= comparator;

    }

    /**
     * Agrandit le tableau interne à la nouvelle capacité donnée, en
     * conservant tous les éléments déjà présents. Appelée automatiquement
     * par insertElement lorsque la file est pleine.
     *
     * @param newCapacity nouvelle capacité du tableau interne
     */
    public void alargar(int newCapacity){
        E[] newQueue = (E[]) new Object[newCapacity];//I will double the capacity
        for (int i = 0; i < nb; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;//I update my list
    }

    @Override
    public boolean insertElement(E e) {
        if(e==null){
            throw new NullPointerException();
        }

        if (nb == queue.length) {
           alargar(queue.length *2);
        }
        queue[nb] = e;//I add it to the final place
        nb++;
        subir_pos(nb - 1);
        return true;
    }

    @Override
    public E element() {//it must turn me out the higher element
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return queue[0];
    }

    @Override
    public E popElement() {//I always turn out the higher which is the first one
        E e = element();//I have the higher one
        nb--;
        queue[0] = queue[nb];
        queue[nb] = null;//I erease it
        descender(0);//now it's at its correct position
        return e;

    }

    @Override
    public boolean isEmpty() {
        return nb==0;
    }

    @Override
    public int size() {
        return nb;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>(){
            private int i=0;
            @Override
            public boolean hasNext() {
                return i<nb;
            }

            @Override
            public E next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                return queue[i++];
            }
        };
    }
    /**
     * Fait remonter l'élément à l'indice donné tant qu'il est strictement
     * plus grand que son père, pour restaurer l'invariant de tas après une insertion.
     *
     * @param i indice de l'élément à faire remonter
     */
    private void subir_pos(int i){
        while(i>0){
            int padre= (i-1)/2;//I only want the integer part
            if(comparator.compare(queue[i],queue[padre])<=0){
                break;
            }
            intercambio(i,padre);
            i= padre;
        }
    }

    /**
     * Fait descendre l'élément à l'indice donné en l'échangeant avec le
     * plus grand de ses fils tant qu'un de ses fils lui est strictement
     * supérieur, pour restaurer l'invariant de tas après un retrait de la racine.
     *
     * @param i indice de l'élément à faire descendre
     */

    private void descender(int i){
        while(true){
            int left= i * 2 + 1;
            int right = i*2 + 2;
            int bigger= i;

            if(left<nb && comparator.compare(queue[left],queue[bigger])>0 ){
                bigger = left;
            }if(right < nb && comparator.compare(queue[right],queue[bigger])>0){
                bigger = right;
            }
            if(bigger == i){
                break;
            }
            intercambio(i,bigger);
            i= bigger;
        }

    }
    /**
     * Échange les éléments situés aux deux indices donnés du tableau interne.
     *
     * @param i indice du premier élément
     * @param padre indice du second élément
     */
    private void intercambio(int i,int padre){
        E elem = queue[i];//I save it
        queue[i]=queue[padre];
        queue[padre]= elem;

    }
}
