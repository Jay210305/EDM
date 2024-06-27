package HistorialMedico;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Key.
 */
public class Key <E>{

    /** La clave. */
    int key;

    /** La lista de valores para la clave. Establecer solo para nodos externos */
    List<E> values;

    /**
     * Instancia una nueva clave y su valor.
     *
     * @param key
     *            la clave
     * @param value
     *            el valor
     */
    public Key(int key, E value) {
        this.key = key;
        if (null == this.values) {
            values = new ArrayList<>();
        }
        this.values.add(value);
    }

    /**
     * Instancia una nueva clave
     *
     * @param key
     *            la clave
     */
    public Key(int key) {
        this.key = key;
        this.values = new ArrayList<>();
    }

    /**
     * Obtiene la clave.
     *
     * @return la clave
     */
    public int getKey() {
        return key;
    }

    /**
     * Establece la clave.
     *
     * @param key
     *            la nueva clave
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Obtiene los valores.
     *
     * @return los valores
     */
    public List<E> getValues() {
        return values;
    }

    /**
     * Establece los valores.
     *
     * @param values
     *            los nuevos valores
     */
    public void setValues(List<E> values) {
        this.values = values;
    }

    public String toString() {
        return "Key [key=" + key + ", values=" + values + "]";
    }

}
