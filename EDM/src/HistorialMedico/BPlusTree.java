package HistorialMedico;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Clase BPlusTree.
 */

public class BPlusTree <E> {

    /** Grado del árbol */
    private int m;

    /** Raíz del árbol B+ */
    private Node root;

    /**
     * Constructor.
     */
    public BPlusTree() {

    }

    /**
     * Inicializa el árbol B Plus. Establece el grado del árbol BPlus como m
     * @param order el grado del árbol B Plus
     */
    public void initialize(int order) {
        // Durante la inicialización, el orden del árbol se establece en m. La raíz está configurada como nula
        this.m = order;
        this.root = null;
        // System.out.println("B+ Árbol inicializado. El grado es " + m);
    }

    /**
     * Inserte un par de clave y valor en el árbol B Plus
     * @param key
     * Clave para insertar
     * @param value
     * el valor a insertar
     */
    public void insert(int key, E value) {

        // Caso 1: Insertar en un árbol B Plus vacío
        if (null == this.root) {
            // Para un árbol B vacío, se crea la raíz y su clave se establece como
            // clave recién insertada
            Node newNode = new Node();
            newNode.getKeys().add(new Key(key, value));
            this.root = newNode;
            // Como la raíz no tiene padre, el padre se establece en nulo
            this.root.setParent(null);
        }

        // Caso 2: Sólo un nodo que no está lleno
        else if (this.root.getChildren().isEmpty() && this.root.getKeys().size() < (this.m - 1)) {
            // Para todas las inserciones hasta que la raíz se sobrellene por primera vez,
            // simplemente actualizamos el nodo raíz y agregamos las nuevas claves.
            insertWithinExternalNode(key, value, this.root);
        }

        // Caso 3: Inserción normal
        else {
            Node curr = this.root;
            // Como insertamos el elemento solo en el nodo externo, recorremos hasta el último nivel
            while (!curr.getChildren().isEmpty()) {
                curr = curr.getChildren().get(binarySearchWithinInternalNode(key, curr.getKeys()));
            }
            insertWithinExternalNode(key, value, curr);
            if (curr.getKeys().size() == this.m) {
                // Si el nodo externo se llena, lo dividimos
                splitExternalNode(curr, this.m);
            }
        }

    }

    /**
     * Inserte el par clave-valor en el nodo externo.
     *
     * @param key
     * la llave a insertar
     * @param value
     * el valor a insertar
     * @param node
     * el nodo donde se debe insertar el par clave-valor
     */
    private void insertWithinExternalNode(int key, E value, Node node) {
        // Se ejecuta una búsqueda binaria para encontrar el lugar
        // correcto donde se insertará el nodo
        int indexOfKey = binarySearchWithinInternalNode(key, node.getKeys());
        if (indexOfKey != 0 && node.getKeys().get(indexOfKey - 1).getKey() == key) {
            // La clave ya existe. Agregue el nuevo valor a la lista
            node.getKeys().get(indexOfKey - 1).getValues().add(value);
        } else {
            // La clave no existe. Agregar clave y valor
            Key newKey = new Key(key, value);
            node.getKeys().add(indexOfKey, newKey);
        }
    }

    /**
     * División del nodo externo
     *
     * @param curr
     *            nodo cuya capacidad se sobre paso para dividir
     * @param m
     *            grado del árbol B+
     */
    private void splitExternalNode(Node curr, int m) {

        // Encuentra el índice medio
        int midIndex = m / 2;

        Node middle = new Node();
        Node rightPart = new Node();

        // Establece la parte derecha para que tenga el elemento central y los
        // elementos justo en el elemento central
        rightPart.setKeys(curr.getKeys().subList(midIndex, curr.getKeys().size()));
        rightPart.setParent(middle);
        // Mientras hacemos el medio como nodo interno, agregamos solo la clave
        // ya que los nodos internos del árbol bplus no contienen valores
        middle.getKeys().add(new Key(curr.getKeys().get(midIndex).getKey()));
        middle.getChildren().add(rightPart);
        // Curr contiene la parte izquierda, así que actualiza el nodo dividido
        // para que contenga solo la parte izquierda
        curr.getKeys().subList(midIndex, curr.getKeys().size()).clear();

        boolean firstSplit = true;
        // propaga el elemento del medio hacia arriba en el árbol y lo
        // fusiona con el padre del nodo previamente lleno
        splitInternalNode(curr.getParent(), curr, m, middle, firstSplit);

    }

    /**
     * División de nodo Interno
     *
     * @param curr
     *            el nodo inetrno actual
     * @param prev
     *            el hijo del nodo interno actual (nodo interno anterior)
     * @param m
     *            el grado del árbol B Plus
     * @param toBeInserted
     *            la parte dividida que se insertará en este nodo interno
     * @param firstSplit
     *            indica si la división está ocurriendo en la primera nodo interno
     *            desde abajo
     */
    private void splitInternalNode(Node curr, Node prev, int m, Node toBeInserted, boolean firstSplit) {
        if (null == curr) {
            // si dividimos la raíz antes, entonces se debe crear una nueva raíz
            this.root = toBeInserted;
            // encontramos dónde se debe insertar el niño haciendo una búsqueda binaria en las claves
            int indexForPrev = binarySearchWithinInternalNode(prev.getKeys().get(0).getKey(), toBeInserted.getKeys());
            prev.setParent(toBeInserted);
            toBeInserted.getChildren().add(indexForPrev, prev);
            if (firstSplit) {
                // actualiza la lista vinculada solo para la primera división (para nodos externos)
                if (indexForPrev == 0) {
                    toBeInserted.getChildren().get(0).setNext(toBeInserted.getChildren().get(1));
                    toBeInserted.getChildren().get(1).setPrev(toBeInserted.getChildren().get(0));
                } else {
                    toBeInserted.getChildren().get(indexForPrev + 1)
                            .setPrev(toBeInserted.getChildren().get(indexForPrev));
                    toBeInserted.getChildren().get(indexForPrev - 1)
                            .setNext(toBeInserted.getChildren().get(indexForPrev));
                }
            }
        } else {
            // fusionar el nodo interno con el centro + derecho de la división anterior
            mergeInternalNodes(toBeInserted, curr);
            if (curr.getKeys().size() == m) {
                // do a split again if the internal node becomes full
                int midIndex = (int) Math.ceil(m / 2.0) - 1;
                Node middle = new Node();
                Node rightPart = new Node();

                // dado que los nodos internos siguen una división como el árbol b, la parte derecha contiene
                // elementos a la derecha del elemento medio y el medio se convierte en padre de la parte derecha
                rightPart.setKeys(curr.getKeys().subList(midIndex + 1, curr.getKeys().size()));
                rightPart.setParent(middle);

                middle.getKeys().add(curr.getKeys().get(midIndex));
                middle.getChildren().add(rightPart);

                List<Node> childrenOfCurr = curr.getChildren();
                List<Node> childrenOfRight = new ArrayList<>();

                int lastChildOfLeft = childrenOfCurr.size() - 1;

                // actualiza los hijos que deben enviarse a la parte derecha desde el nodo dividido
                for (int i = childrenOfCurr.size() - 1; i >= 0; i--) {
                    List<Key> currKeysList = childrenOfCurr.get(i).getKeys();
                    if (middle.getKeys().get(0).getKey() <= currKeysList.get(0).getKey()) {
                        childrenOfCurr.get(i).setParent(rightPart);
                        childrenOfRight.add(0, childrenOfCurr.get(i));
                        lastChildOfLeft--;
                    } else {
                        break;
                    }
                }

                rightPart.setChildren(childrenOfRight);

                // actualiza el nodo sobrecargado para que contenga solo la parte izquierda y actualiza sus hijos
                curr.getChildren().subList(lastChildOfLeft + 1, childrenOfCurr.size()).clear();
                curr.getKeys().subList(midIndex, curr.getKeys().size()).clear();

                // propagar la división un nivel hacia arriba
                splitInternalNode(curr.getParent(), curr, m, middle, false);
            }
        }
    }

    /**
     * Fusionar nodos internos.
     *
     * @param mergeFrom
     *            a la parte desde la cual tenemos que fusionarnos
     *            (en medio del nodo dividido anterior)
     * @param mergeInto
     *            el nodo interno al que se fusionará
     */
    private void mergeInternalNodes(Node mergeFrom, Node mergeInto) {
        Key keyToBeInserted = mergeFrom.getKeys().get(0);
        Node childToBeInserted = mergeFrom.getChildren().get(0);
        // Encuentra el índice donde se debe insertar la clave haciendo una búsqueda binaria
        int indexToBeInsertedAt = binarySearchWithinInternalNode(keyToBeInserted.getKey(), mergeInto.getKeys());
        int childInsertPos = indexToBeInsertedAt;
        if (keyToBeInserted.getKey() <= childToBeInserted.getKeys().get(0).getKey()) {
            childInsertPos = indexToBeInsertedAt + 1;
        }
        childToBeInserted.setParent(mergeInto);
        mergeInto.getChildren().add(childInsertPos, childToBeInserted);
        mergeInto.getKeys().add(indexToBeInsertedAt, keyToBeInserted);

        // Actualizar la lista vinculada de nodos externos
        if (!mergeInto.getChildren().isEmpty() && mergeInto.getChildren().get(0).getChildren().isEmpty()) {

            // Si la fusión se produce en el último elemento, entonces solo es necesario
            // actualizar el puntero entre el nuevo nodo y el último elemento anterior
            if (mergeInto.getChildren().size() - 1 != childInsertPos
                    && mergeInto.getChildren().get(childInsertPos + 1).getPrev() == null) {
                mergeInto.getChildren().get(childInsertPos + 1).setPrev(mergeInto.getChildren().get(childInsertPos));
                mergeInto.getChildren().get(childInsertPos).setNext(mergeInto.getChildren().get(childInsertPos + 1));
            }
            // Si la fusión se produce en el último elemento, entonces solo es necesario
            // actualizar el puntero entre el nuevo nodo y el último elemento anterior
            else if (0 != childInsertPos && mergeInto.getChildren().get(childInsertPos - 1).getNext() == null) {
                mergeInto.getChildren().get(childInsertPos).setPrev(mergeInto.getChildren().get(childInsertPos - 1));
                mergeInto.getChildren().get(childInsertPos - 1).setNext(mergeInto.getChildren().get(childInsertPos));
            }
            // Si se produce una fusión en el medio, entonces el siguiente elemento y los punteros anterior
            // y siguiente del elemento anterior deben actualizarse
            else {
                mergeInto.getChildren().get(childInsertPos)
                        .setNext(mergeInto.getChildren().get(childInsertPos - 1).getNext());
                mergeInto.getChildren().get(childInsertPos).getNext()
                        .setPrev(mergeInto.getChildren().get(childInsertPos));
                mergeInto.getChildren().get(childInsertPos - 1).setNext(mergeInto.getChildren().get(childInsertPos));
                mergeInto.getChildren().get(childInsertPos).setPrev(mergeInto.getChildren().get(childInsertPos - 1));
            }
        }

    }

    /**
     * Método auxiliar: imprime el árbol utilizando un recorrido de orden de nivel
     */
    public void printTree() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(this.root);
        queue.add(null);
        Node curr = null;
        int levelNumber = 2;
        System.out.println("Printing level 1");
        while (!queue.isEmpty()) {
            curr = queue.poll();
            if (null == curr) {
                queue.add(null);
                if (queue.peek() == null) {
                    break;
                }
                System.out.println("\n" + "Printing level " + levelNumber++);
                continue;
            }

            printNode(curr);

            if (curr.getChildren().isEmpty()) {
                break;
            }
            for (int i = 0; i < curr.getChildren().size(); i++) {
                queue.add(curr.getChildren().get(i));
            }
        }

        curr = curr.getNext();
        while (null != curr) {
            printNode(curr);
            curr = curr.getNext();
        }

    }

    /**
     * Método auxiliar para impresión de un nodo del árbol.
     *
     * @param curr
     *            el nodo a imprimir
     */
    private void printNode(Node curr) {
        for (int i = 0; i < curr.getKeys().size(); i++) {
            System.out.print(curr.getKeys().get(i).getKey() + ":(");
            String values = "";
            for (int j = 0; j < curr.getKeys().get(i).getValues().size(); j++) {
                values = values + curr.getKeys().get(i).getValues().get(j) + ",";
            }
            System.out.print(values.isEmpty() ? ");" : values.substring(0, values.length() - 1) + ");");
        }
        System.out.print("||");
    }

    /**
     * Búsqueda binaria modificada dentro del nodo interno.
     *
     * @param key
     *            la clave a buscar
     * @param keyList
     *            la lista de claves a buscar
     * @return el primer índice de la lista en el que la clave es mayor
     *         que la tecla de entrada
     */
    public int binarySearchWithinInternalNode(int key, List<Key> keyList) {
        int st = 0;
        int end = keyList.size() - 1;
        int mid;
        int index = -1;
        // Devuelve el primer índice si la clave es menor que el primer elemento
        if (key < keyList.get(st).getKey()) {
            return 0;
        }
        // Devuelve el tamaño de la matriz + 1 como la nueva posición de
        // la clave si es mayor que el último elemento
        if (key >= keyList.get(end).getKey()) {
            return keyList.size();
        }
        while (st <= end) {
            mid = (st + end) / 2;
            // La siguiente condición garantiza que encontremos una ubicación s.t.
            // La clave es menor que el elemento en ese índice y es mayor o igual que
            // el elemento en el índice anterior. Esta ubicación es donde se insertaría la llave.
            if (key < keyList.get(mid).getKey() && key >= keyList.get(mid - 1).getKey()) {
                index = mid;
                break;
            } // Las siguientes condiciones siguen la búsqueda binaria normal
            else if (key >= keyList.get(mid).getKey()) {
                st = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return index;
    }

    /**
     * Buscar valores para una clave
     *
     * @param key
     *            la clave a buscar
     * @return la lista de valores para la clave
     */
    public List<String> search(int key) {
        List<String> searchValues = null;

        Node curr = this.root;
        // Atraviesa el nodo externo correspondiente que 'debería' contener esta clave
        while (curr.getChildren().size() != 0) {
            curr = curr.getChildren().get(binarySearchWithinInternalNode(key, curr.getKeys()));
        }
        List<Key> keyList = curr.getKeys();
        // Realiza una búsqueda lineal en este nodo de la clave. Establezca el
        // parámetro searchValues' solo si tiene éxito
        for (int i = 0; i < keyList.size(); i++) {
            if (key == keyList.get(i).getKey()) {
                searchValues = keyList.get(i).getValues();
            }
            if (key < keyList.get(i).getKey()) {
                break;
            }
        }

        return searchValues;
    }

    public Node searchNode(int key) {
        Node curr = this.root;
        // Recorrer hasta el nodo externo correspondiente que 'debería' contener esta clave
        while (curr.getChildren().size() != 0) {
            curr = curr.getChildren().get(binarySearchWithinInternalNode(key, curr.getKeys()));
        }
        List<Key> keyList = curr.getKeys();
        // Realizar una búsqueda lineal en este nodo para la clave.
        // Devolver el nodo actual si se encuentra la clave
        for (int i = 0; i < keyList.size(); i++) {
            if (key == keyList.get(i).getKey()) {
                return curr;
            }
            if (key < keyList.get(i).getKey()) {
                break;
            }
        }
        // Si no se encuentra la clave, devuelve null o el nodo más cercano
        return null;
    }


    /**
     * Busque todos los pares de valores clave entre clave1 y clave2.
     *
     * @param key1
     *            la clave de inicio
     * @param key2
     *            la clave final
     * @return la lista de pares clave-valor entre las dos claves
     */

    public List<Key> search(int key1, int key2) {
        //System.out.println("Buscando entre teclas " + tecla1 + ", " + tecla2);
        List<Key> searchKeys = new ArrayList<>();
        Node currNode = this.root;
        // Atraviesa el nodo externo correspondiente que 'debería' contener la clave inicial (clave1)
        while (currNode.getChildren().size() != 0) {
            currNode = currNode.getChildren().get(binarySearchWithinInternalNode(key1, currNode.getKeys()));
        }

        // Comience desde el nodo actual y agregue claves cuyo valor se encuentre entre clave1 y clave2 con sus
        // pares correspondientes. Deténgase si se encuentra el final de la lista o si el valor encontrado en la
        // lista es mayor que clave2

        boolean endSearch = false;
        while (null != currNode && !endSearch) {
            for (int i = 0; i < currNode.getKeys().size(); i++) {
                if (currNode.getKeys().get(i).getKey() >= key1 && currNode.getKeys().get(i).getKey() <= key2)
                    searchKeys.add(currNode.getKeys().get(i));
                if (currNode.getKeys().get(i).getKey() > key2) {
                    endSearch = true;
                }
            }
            currNode = currNode.getNext();
        }

        return searchKeys;
    }

}

