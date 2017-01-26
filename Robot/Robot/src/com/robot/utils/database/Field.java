
package com.robot.utils.database;

import java.util.Objects;

/**
 * Classe rappresentante un campo della tupla.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 * @param <V> il tipo del valore del campo
 */
public class Field<V> {

    private String name;
    private V value;

    public Field(String name, V value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field<V> other = (Field<V>) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return "Field{" + "name=" + name + ", value=" + value + '}';
    }
    
    public boolean isEmpty(){
        return value==null;
    }

}