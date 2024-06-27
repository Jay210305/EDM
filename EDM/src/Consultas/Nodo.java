package Consultas;

public class Nodo<T> {

    private T data;

    private Nodo<T> next;

    Nodo(T data) {
        this(data, null);
    }

    Nodo(T d, Nodo<T> n) {
        this.data = d;
        this.next = n;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Nodo<T> getNext() {
        return next;
    }

    public void setNext(Nodo<T> next) {
        this.next = next;
    }
}
