package Pacientes;

public class NodoAVL<E extends Comparable<E>> {

    E valor;
    NodoAVL<E> izquierdo;
    NodoAVL<E> derecho;
    int altura;

    NodoAVL(E valor) {
        this.valor = valor;
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

    public int getAltura() {
        return altura;
    }

    public void setValor(E valor) {
        this.valor = valor;
    }

    public void setIzquierdo(NodoAVL<E> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoAVL<E> derecho) {
        this.derecho = derecho;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}