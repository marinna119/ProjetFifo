package container;

import java.util.Iterator;
import java.util.NoSuchElementException;

//Todo nodo tiene valor igual o mayor al de sus hijos (almacenamos arbol en lista)
//necesito definir aparte, tras insertar nodo que suba de posicion
//tras quitar la raiz que descienda
//intercambio una vez sube/baja

public class IntPriorityQueue implements Queue<Integer> {

    private Integer List[];
    private int capacity;//tamaño del array
    private int nb = 0;//numero de elementos actuales

    public IntPriorityQueue(int capacity) {
        this.capacity = capacity;
        this.List = new Integer[capacity];
    }

    @Override
    public boolean insertElement(Integer integer) {
        if (nb == capacity) {
            Integer[] newList = new Integer[capacity * 2];//duplico la capacidad
            for (int i = 0; i < nb; i++) {
                newList[i] = List[i];
            }
            List = newList;//actualizo mi lista
            capacity = capacity * 2;
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
        return null;
    }

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
    //CREAR CON MAVEN IMPORTANTE PARA TEST -- CORREGIR
    private void descender(int i){
        while(true){
            int left= i * 2 + 1;
            int right = i*2 + 1;
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

    private void intercambio(int i,int padre){
        Integer elem = List[i];//lo copio para no perderlo
        List[i]=List[padre];
        List[padre]=elem;

    }

}
