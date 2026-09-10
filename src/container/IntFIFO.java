package container;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntFIFO implements Queue<Integer> {

    private Integer List[];
    private int capacity;//tamaño del array
    private int in = 0;//indice para proximo elemento insertado
    private int out = 0;//indice de elemento mas antiguo (el que sale)
    private int nb = 0;//numero de elementos actuales

    public IntFIFO(int capacity) {
        this.capacity = capacity;
        this.List = new Integer[capacity];
    }

    @Override
    public boolean insertElement(Integer e) {
        if (nb == capacity) {//si el tamaño de la lista es igual a numero de elementos, agrandamos
            Integer[] newList = new Integer[capacity * 2];//creamos nuevo de tamaño doble
            for (int i = 0; i < nb; i++) {//para cada elemento
                newList[i] = List[(out + i) % capacity];//lo recorremos y ponemos en orden para que out=0
            }
            List = newList;//reasignamos la lista, la capacidad
            capacity = capacity * 2;
            out = 0;
            in = nb;
        }
        List[in] = e;
        in = (in + 1) % capacity;//in avanza una posición, con % capacity para que si llega al final del array, vuelva al índice 0
        nb++;//hay un elemento mas
        return true;
    }

    @Override
    public Integer element() {//me indica elemento mas alto EN FIFO, ESTE HACE REFERENCIA AL MAS ANTIGUO
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return List[out];
    }

    @Override
    public Integer popElement() {
        Integer e = element();//consigo el mas alto de la funcion anterior
        List[out]=null;//lo elimino
        out = (out + 1) % capacity;//lo hago asi para mantener la forma circular
        nb--;//hay un elemento menos
        return e;//me devuelve el mas alto, el que quito
    }

    @Override
    public boolean isEmpty() {
        return nb == 0;
    }

    @Override
    public int size() {
        return nb;
    }

    @Override
    public Iterator<Integer> iterator() {
        return null;
    }
    //Necesario porque la interfaz extiende iterable
    //Ese metodo tiene que devolver un objeto capaz de "recorrer" tu estructura elemento a elemento.

}