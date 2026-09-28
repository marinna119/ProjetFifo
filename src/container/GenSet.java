package container;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Ensemble générique représenté par un arbre binaire de rechercre. Un élément n'apparaît
 * qu'une seule fois dans l'ensemble. Pour chaque noeud, tous les éléments du sous-arbre
 * gauche sont plus petits et tous ceux du sous-arbre droit plus grands.
 *
 * @param <E> type des éléments stockés, comparables entre eux
 */

public class GenSet<E extends Comparable<E>> implements SetContainer<E> {
    /**
     * noeud de l'arbre: une valeur et deux sous-arbres éventuellement vides
     * @param <E>
     */
    private static class Node<E>{
        private final E value;
        private Node<E> left= null;
        private Node<E> right = null;

        private Node(E value){
            this.value=value;
        }
    }

    private Node<E> root= null;//la raiz sera el central
    private int nb =0; // number of elements

    /**
     * construit un ensemble vide
     */
    public GenSet(){}//CONSTRUCTOR VACIO

    @Override
    public boolean insertElement(E e) {
        if (e == null) {//si no hay nada a añadir, lanzo excepción porque apunta a nada
            throw new NullPointerException();
        }
        if (root == null) {//la raiz si es nula, añado un elemento creando un nodo raiz
            root = new Node<>(e);
            nb++;
            return true;//lo devuelvo y salgo de la función
        }
        Node<E> current = root;//el nodo actual es la raiz
        while (true) {
            int cmp = e.compareTo(current.value);//nos devolvera cero, negativo o positivo . comparamos valor a insertar con el actual
            if (cmp == 0) {//en conjunto no se repiten valores
                return false; // déjà présent : un ensemble n'a pas de doublons
            }
            if (cmp < 0) {//si es engativo, vamos a la izquierda
                if (current.left == null) {//si no hay ninguno a la izquierda, creamos nodo nuevo y añadimos elemento
                    current.left = new Node<>(e);
                    nb++;
                    return true;
                }
                current = current.left;//bajamos por la izquierda
            } else {
                if (current.right == null) {
                    current.right = new Node<>(e);
                    nb++;
                    return true;
                }
                current = current.right;
            }
        }
    }

    @Override
    public boolean contains(E e) {
        if (e == null) {//comprobamos antes que sea pposible el valor
            throw new NullPointerException();
        }
        Node<E> current = root;//el actual es la raiz del nodo
        while (current != null) {//si existe
            int cmp = e.compareTo(current.value);//comparamos el nuevo al actual
            if (cmp == 0) {//al ser cero es que existe
                return true;//devolvemos booleano que si que lo contiene
            }
            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }//mientras no sea nulo seguira con el bucle hasta comprobar si esta en el conjunto o no
        }
        return false;
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
    public @NotNull Iterator<E> iterator() {
        return new Iterator<E>() {
            // contiene los nodos cuyo valor y subarbol derecho quedan por visitar
            private final Deque<Node<E>> stack = new ArrayDeque<>();//cogemos estructura lifo, ultimo en entrar es primero en salir
            //push -> mete x arriba
            //pull -> saca y devuelve lo de arriba
            {
                pushLeftBranch(root);//es como inicializar un constructor
                //cargamos la pila inicial: raiz y rama izquierda
            }

            /** Empile le noeud donné puis tous ses descendants les plus à gauche. */
            private void pushLeftBranch(Node<E> node) {
                while (node != null) {//mientras no haya nodo
                    stack.push(node);//el nodo mas pequeño queda arriba de la pila (lo mas a la izquierda es el mas pequeño)
                    node = node.left;//ahora es el izquierdo, cambiamos la variable local
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();//mientra haya algo en la pila quedan cosas por devolver
            }

            @Override
            public E next() {
                if (!hasNext()) {//Si no tiene siguiente -> lanzamos excepción
                    throw new NoSuchElementException();
                }
                Node<E> node = stack.pop();//saca el dee arriba, que es el siguiente en orden creciente
                pushLeftBranch(node.right); //prepara lo que viene despues, mayor que node al estar a la derecha
                return node.value;//devolvemos valor
                //seguimos el patron izquieda, nodo, derecha -> mirar ejemplo visual
            }
        };

    }
}
