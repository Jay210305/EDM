package Pacientes;

class Register<E extends Comparable<E>> implements Comparable<Register<E>> {

    private int key;
    private E value;

    public Register(int key, E value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public E getValue() {
        return value;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Register) {
            Register<?> r = (Register<?>) o;
            return this.key == r.key;
        }
        return false;
    }

    @Override
    public int compareTo(Register<E> other) {
        return Integer.compare(this.key, other.key);
    }

    @Override
    public String toString() {
        return this.key + " : " + this.value.toString();
    }
}
