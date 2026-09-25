package container;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GenPriorityQueueCmp<E> implements Queue<E>{

    private E queue[];
    private int nb = 0;//numero de elementos actuales
    private final Comparator<? super E> comparator;


    public GenPriorityQueueCmp(int capacity, Comparator<? super E> comparator) {
        if(capacity<=0){
            throw new IllegalArgumentException("capacity must be strictly positive");
        }
        this.queue = (E[]) new Object[capacity];
        this.comparator= comparator;

    }

    public void alargar(int newCapacity){
        E[] newQueue = (E[]) new Comparable[newCapacity];//duplico la capacidad
        for (int i = 0; i < nb; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;//actualizo mi lista
    }

    @Override
    public boolean insertElement(E e) {
        if (nb == queue.length) {
           alargar(queue.length *2);
        }
        queue[nb] = e;//lo añado al final
        nb++;
        subir_pos(nb - 1);//compruebo si tiene que subir
        return true;
    }

    @Override
    public E element() {//ME DEBE DEVOLVER EL ELEMENTO MAS ALTO
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return queue[0];
    }

    @Override
    public E popElement() {//siempre devuelvo el primero que es el mas alto
        E e = element();//consigo el + alto
        nb--;
        queue[0] = queue[nb];
        queue[nb] = null;//aqui lo elimino
        descender(0);//para que vuelva a su posicion correcta
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
                return queue[i++];
            }
        };
    }

    private void subir_pos(int i){
        while(i>0){
            int padre= (i-1)/2;//al coger int me quedo la parte entera
            if(comparator.compare(queue[i],queue[padre])<=0){
                break;
            }
            intercambio(i,padre);
            i= padre;
        }
    }

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

    private void intercambio(int i,int padre){
        E elem = queue[i];//lo copio para no perderlo
        queue[i]=queue[padre];
        queue[padre]= elem;

    }
}
