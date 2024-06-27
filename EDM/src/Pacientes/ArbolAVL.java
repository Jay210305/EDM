package Pacientes;

public class ArbolAVL<E extends Comparable<E>> {

    public static class NodoAVL<E extends Comparable<E>> {
        private E valor;
        NodoAVL<E> izquierdo;
        NodoAVL<E> derecho;
        private int altura;

        public NodoAVL(E valor) {
            this.valor = valor;
            this.izquierdo = this.derecho = null;
            this.altura = 1;
        }

        public E getValor() {
            return valor;
        }

        public NodoAVL<E> getIzquierdo() {
            return izquierdo;
        }

        public NodoAVL<E> getDerecho() {
            return derecho;
        }

        public NodoAVL<E> buscarNodo(E valor) {
            int comparacion = valor.compareTo(this.valor);
            if (comparacion == 0) {
                return this;
            } else if (comparacion < 0 && izquierdo != null) {
                return izquierdo.buscarNodo(valor);
            } else if (comparacion > 0 && derecho != null) {
                return derecho.buscarNodo(valor);
            }
            return null;
        }

    }

    public NodoAVL<E> getRoot() {
        return raiz;
    }

    NodoAVL<E> raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    private int altura(NodoAVL<E> nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    private int obtenerFactorBalance(NodoAVL<E> nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    private NodoAVL<E> rotarDerecha(NodoAVL<E> y) {
        NodoAVL<E> x = y.izquierdo;
        NodoAVL<E> T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;

        return x;
    }

    private NodoAVL<E> rotarIzquierda(NodoAVL<E> x) {
        NodoAVL<E> y = x.derecho;
        NodoAVL<E> T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;

        return y;
    }

    public void insertar(E valor) {
        raiz = insertarRec(raiz, valor);
    }

    private NodoAVL<E> insertarRec(NodoAVL<E> nodo, E valor) {
        if (nodo == null) {
            return new NodoAVL<>(valor);
        }

        if (valor.compareTo(nodo.valor) < 0) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, valor);
        } else if (valor.compareTo(nodo.valor) > 0) {
            nodo.derecho = insertarRec(nodo.derecho, valor);
        } else {
            return nodo; // No se permiten valores duplicados
        }

        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));

        int balance = obtenerFactorBalance(nodo);

        if (balance > 1 && valor.compareTo(nodo.izquierdo.valor) < 0) {
            return rotarDerecha(nodo);
        }

        if (balance < -1 && valor.compareTo(nodo.derecho.valor) > 0) {
            return rotarIzquierda(nodo);
        }

        if (balance > 1 && valor.compareTo(nodo.izquierdo.valor) > 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        if (balance < -1 && valor.compareTo(nodo.derecho.valor) < 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    public void eliminar(E valor) {
        raiz = eliminarRec(raiz, valor);
    }

    private NodoAVL<E> eliminarRec(NodoAVL<E> nodo, E valor) {
        if (nodo == null) {
            return nodo;
        }

        if (valor.compareTo(nodo.valor) < 0) {
            nodo.izquierdo = eliminarRec(nodo.izquierdo, valor);
        } else if (valor.compareTo(nodo.valor) > 0) {
            nodo.derecho = eliminarRec(nodo.derecho, valor);
        } else {
            if (nodo.izquierdo == null || nodo.derecho == null) {
                NodoAVL<E> temp = null;
                if (temp == nodo.izquierdo) {
                    temp = nodo.derecho;
                } else {
                    temp = nodo.izquierdo;
                }

                if (temp == null) {
                    temp = nodo;
                    nodo = null;
                } else {
                    nodo = temp;
                }
            } else {
                NodoAVL<E> temp = minValueNode(nodo.derecho);
                nodo.valor = temp.valor;
                nodo.derecho = eliminarRec(nodo.derecho, temp.valor);
            }
        }

        if (nodo == null) {
            return nodo;
        }

        nodo.altura = Math.max(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;

        int balance = obtenerFactorBalance(nodo);

        if (balance > 1 && obtenerFactorBalance(nodo.izquierdo) >= 0) {
            return rotarDerecha(nodo);
        }

        if (balance > 1 && obtenerFactorBalance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        if (balance < -1 && obtenerFactorBalance(nodo.derecho) <= 0) {
            return rotarIzquierda(nodo);
        }

        if (balance < -1 && obtenerFactorBalance(nodo.derecho) > 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL<E> minValueNode(NodoAVL<E> nodo) {
        NodoAVL<E> current = nodo;
        while (current.izquierdo != null) {
            current = current.izquierdo;
        }
        return current;
    }

    public boolean buscar(E valor) {
        return buscarRec(raiz, valor);
    }

    private boolean buscarRec(NodoAVL<E> nodo, E valor) {
        if (nodo == null) {
            return false;
        }
        if (valor.compareTo(nodo.valor) < 0) {
            return buscarRec(nodo.izquierdo, valor);
        } else if (valor.compareTo(nodo.valor) > 0) {
            return buscarRec(nodo.derecho, valor);
        } else {
            return true;
        }
    }

    public NodoAVL<E> buscarNodo(E valor) {
        return buscarNodoRec(raiz, valor);
    }

    private NodoAVL<E> buscarNodoRec(NodoAVL<E> nodo, E valor) {
        if (nodo == null || nodo.valor.equals(valor)) {
            return nodo;
        }
        if (valor.compareTo(nodo.valor) < 0) {
            return buscarNodoRec(nodo.izquierdo, valor);
        } else {
            return buscarNodoRec(nodo.derecho, valor);
        }
    }

    public void enOrden() {
        enOrdenRec(raiz);
    }

    private void enOrdenRec(NodoAVL<E> nodo) {
        if (nodo != null) {
            enOrdenRec(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            enOrdenRec(nodo.derecho);
        }
    }

}
