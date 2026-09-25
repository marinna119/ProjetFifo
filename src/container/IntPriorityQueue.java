package container;

import java.util.Iterator;
import java.util.NoSuchElementException;

//Todo nodo tiene valor igual o mayor al de sus hijos (almacenamos arbol en lista)
//necesito definir aparte, tras insertar nodo que suba de posicion
//tras quitar la raiz que descienda
//intercambio una vez sube/baja

/**
 * File de priotité d'entiers, représentée par un tas stocké dans un tableau, où tout
 * noeud a une valeur supérieure ou égale à celle de ses fils. L'élément de plus haute
 * priorité (le plus grand) se trouve toujous à la racine (indice 0)
 */


public class IntPriorityQueue implements Queue<Integer> {

    private Integer List[];
    private int nb = 0;//numero de elementos actuales

    /**
     * Construit une file de priorité vide avec la capacité initiale donnée
     *
     * @param capacity capacité initiale du tableau interne, strictement positive
     * @throws IllegalArgumentException si capacity est négative ou nulle
     */
    public IntPriorityQueue(int capacity) {
        if(capacity<=0){
            throw new IllegalArgumentException("capacity must be strictly positive");
        }
        this.List = new Integer[capacity];
    }


    @Override
    public boolean insertElement(Integer integer) {
        if (nb == List.length) {
            alargarFila(List.length * 2);
        }
        List[nb] = integer;//lo añado al final
        nb++;
        subir_pos(nb - 1);//compruebo si tiene que subir
        return true;
    }

    @Override
    public Integer element() {//ME DEBE DEVOLVER EL ELEMENTO MAS ALTO
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return List[0];
    }

    @Override
    public Integer popElement() {//siempre devuelvo el primero que es el mas alto
        Integer e = element();//consigo el + alto
        nb--;
        List[0] = List[nb];
        List[nb] = null;//aqui lo elimino
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
    public Iterator<Integer> iterator() {

        return new Iterator<Integer>(){
            private int i =0;

            @Override//como anotacion de que lo implementamos, no es imprescindible que este
            public boolean hasNext(){
                return i<nb;//cuidado no coger el i++ porque iria sumando cada vez que lo llame
            }
            @Override
            public Integer next(){
                return List[i++];
            }

        };
    }

    /**
     * Agrandit le tableau interne à la nouvelle capacité donnée, en conservant tous les
     * éléments déjà présents. Cette méthode est appelé automatiquement par insertElement
     * lorsque la file est pleine
     *
     * @param newCapacity nouvelle capacité du tableau interne, doit être strictement
     *                    supérieure au nombre d'éléments courant
     */
    public void alargarFila(int newCapacity){
        Integer[] newList = new Integer[newCapacity];//duplico la capacidad
        for (int i = 0; i < nb; i++) {
            newList[i] = List[i];
        }
        List = newList;//actualizo mi lista
    }

    /**
     * Fait remonter l'élément à l'indice donné tant qu'il est plus grand que
     * son père, pour restaurer l'invariant de tas après une insertion
     * @param i indice de l'élément à faire remonter
     */

    private void subir_pos(int i){
        while(i>0){
            int padre= (i-1)/2;//al coger int me quedo la parte entera
            if(List[i]<=List[padre]){
                break;
            }
            intercambio(i,padre);
            i= padre;
        }
    }
    //CREAR CON MAVEN IMPORTANTE PARA TEST

    /**
     * Fait descendre l'élément à l'indice donné en l'echangeant avec le plus grand de ses
     * fils tant que l'invariant de tas n'est pas respecté, pour restaurer l'invariant
     * après un retrait de la racine
     * @param i indice de l'élément à faire descendre
     */
    private void descender(int i){
        while(true){
            int left= i * 2 + 1;
            int right = i*2 + 2;
            int bigger= i;

            if(left<nb && List[left]> List[bigger]){
                bigger = left;
            }if(right < nb && List[right]>List[bigger]){
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
     * Échange les éléments aux deux indices donnés dans le tableau interne
     * @param i premier indice
     * @param padre second indice
     */
    private void intercambio(int i,int padre){
        Integer elem = List[i];//lo copio para no perderlo
        List[i]=List[padre];
        List[padre]=elem;

    }

}
