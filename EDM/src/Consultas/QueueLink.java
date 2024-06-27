package Consultas;

public class QueueLink<E>{
    private Nodo<E> first;
    private Nodo<E> last;

    public QueueLink(){
        this.first = null;
        this.last = null;
    }

    public void enqueue(E x){
        Nodo<E> aux = new Nodo<E>(x);
        if (this.isEmpty()){
            this.first = aux;
            this.last = aux;
        }else {
            this.last.setNext(aux);
            this.last = aux;
        }
    }

    public E dequeue() throws Exception {
        if(this.isEmpty()) {
            return null;
        } else {
            E data = first.getData();
            first = first.getNext();
            return data;
        }

    }
    public E back() throws Exception{
        return last.getData();
    }
    public E front() throws Exception{
        return first.getData();
    }
    public boolean isEmpty() {
        if(first == null) {
            return true;
        } else {
            return false;
        }
    }

    public int count() {
        int conti = 0;
        if(isEmpty()) {
            return conti;
        }
        else{
            Nodo<E> cont = first;
            while(cont != null) {
                conti++;
                cont = cont.getNext();
            }
        }
        return conti;
    }

    @Override
    public String toString() {
        if(first != null) {
            return "QueueLink [first=" + first.getData() + ", last=" + last.getData()
                    + ", isEmpty()=" + isEmpty() + "]";
        } else {
            return "Cola Vacía";
        }
    }
}
