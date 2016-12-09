
package com.database;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe rappresentante un campo della tupla.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 * @param <V> il tipo del valore del campo
 */
public class Field<V> implements Serializable{

    private String name;
    private V value;
    private String control;
    private boolean update;

    public Field(String name, V value) {
        this.name = name;
        this.value = value;
        control=null;
        update=false;
    }

    public Field(String name, V value, String control){
        this(name,value);
        this.control=control;
        update=false;
    }

    public Field(String name, V value, String control,boolean update) {
        this(name, value, control);
        this.update = update;
    }

    public boolean isUpdate() {
        return update;
    }



    public String getControl() {
        return control;
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
        return name + ": " + value;
    }
    
    public boolean isEmpty(){
        return value==null;
    }

}
