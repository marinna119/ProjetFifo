package container;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * File de type FIFO (First In First Out) d'entiers, implémentée à l'aide d'un tableau
 * circulaire (circular buffer) pour garantir une complexité amortie en 0(1)
 * pour l'insertion et le retrait d'éléments
 */

public class IntFIFO implements Queue<Integer> {

    //PARA LOS MÉTODOS QUE NO HEREDO DE LA INTERFAZ DEBO EXPLICARLOS BIEN AQUÍ CON COMENTARIOS (JAVADOC) -> CONSTRUCTOR Y ALARGAR

    private Integer List[];
    //private int capacity;//tamaño del array -- ES REDUNDANTE
    private int in = 0;//indice para proximo elemento insertado
    private int out = 0;//indice de elemento mas antiguo (el que sale)
    private int nb = 0;//numero de elementos actuales


    /**
     * Construit une file FIFO vide avec la capacité initiale donnée
     *
     * @param capacity capacité initiale du tableau interne; doit être strictement positive
     * @throws IllegalArgumentException si capacity est négative ou nulle
     */
    public IntFIFO(int capacity) {
        //NO SUSPENDE PERO DEBERÍA COMPROBAR QUE TIENE SENTIDO
        if(capacity<=0){
            throw new IllegalArgumentException("capacity must be strictly positive");
        }
        this.List = new Integer[capacity];
    }

    /**
     * Agrandit le tableau interne à la nouvelle capacité donnée, en réalignant
     * les éléments existantas dans l'ordre FIFO à partir de l'indice 0.
     * Cette méthode est appelée automatiquement par insertElement lorsque
     * la file est pleine, mais peut ausse être appelée directement
     * pour préallouer de l'espace
     *
     * @param newCapacity nouvelle capacité du tableau interne; doit être strictement
     *                    supérieure au nombre d'éléments courant
     */

    public void alargarFifo(int newCapacity){//ASÍ DEJAMOS INNSERTAR ELEMENT MINIMO
        Integer[] newList = new Integer[newCapacity];//creamos nuevo de tamaño doble
        for (int i = 0; i < nb; i++) {//para cada elemento
            newList[i] = List[(out + i) % List.length];//lo recorremos y ponemos en orden para que out=0
        }
        List = newList;//reasignamos la lista, la capacidad
        out = 0;
        in = nb;
    }

    @Override
    public boolean insertElement(Integer e) {
        if (nb == List.length) {//si el tamaño de la lista es igual a numero de elementos, agrandamos
            alargarFifo(List.length * 2);
        }
        List[in] = e;
        in = (in + 1) % List.length;//in avanza una posición, con % capacity para que si llega al final del array, vuelva al índice 0
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
        out = (out + 1) % List.length;//lo hago asi para mantener la forma circular
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
        return new Iterator<Integer>() {
            private int i=0;
            @Override
            public boolean hasNext() {
                return i<nb;
            }

            @Override
            public Integer next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                Integer value = List[(out + i)% List.length];
                i++;
                return value;
            }
        };

    }
    //Necesario porque la interfaz extiende iterable
    //Ese metodo tiene que devolver un objeto capaz de "recorrer" tu estructura elemento a elemento.

}