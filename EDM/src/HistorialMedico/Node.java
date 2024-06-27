package HistorialMedico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase Node.
 */
public class Node {

    /** La lista de pares clave-valor en el nodo. */
    private List<Key> keys;

    /** Los hijos de este nodo. Establecer solo para nodos internos */
    private List<Node> children;

    /** El elemento anterior en la lista enlazada. Establecer solo para nodos externos. */
    private Node prev;

    /** El siguiente elemento en la lista enlazada. Establecer solo para nodos externos. */
    private Node next;

    /** El padre de este nodo. NULO para la raíz */
    private Node parent;

    /**
     * Instancia un nuevo nodo.
     */
    public Node() {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.prev = null;
        this.next = null;
    }

    /**
     * Obtiene las claves.
     *
     * @return las claves
     */
    public List<Key> getKeys() {
        return keys;
    }

    /**
     * Establece las claves.
     *
     * @param keys
     *            las nuevas claves
     */
    public void setKeys(List<Key> keys) {
        Iterator<Key> iter = keys.iterator();
        while (iter.hasNext()) {
            this.keys.add(iter.next());
        }
    }

    /**
     * Obtiene los hijos.
     *
     * @return los hijos
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * Establece los hijos.
     *
     * @param children
     *            los nuevos hijos
     */
    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * Obtiene el anterior.
     *
     * @return el anterior
     */
    public Node getPrev() {
        return prev;
    }

    /**
     * Establece el anterior.
     *
     * @param prev
     *            el nuevo anterior
     */
    public void setPrev(Node prev) {
        this.prev = prev;
    }

    /**
     * Obtiene el siguiente.
     *
     * @return el siguiente
     */
    public Node getNext() {
        return next;
    }

    /**
     * Establece el siguiente.
     *
     * @param next
     *            el nuevo siguiente
     */
    public void setNext(Node next) {
        this.next = next;
    }

    /**
     * Obtiene el padre.
     *
     * @return el padre
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Establece el padre.
     *
     * @param parent
     *            el nuevo padre
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Keys =" + keys.toString();
    }

}
