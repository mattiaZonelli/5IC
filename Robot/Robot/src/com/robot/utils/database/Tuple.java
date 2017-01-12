package com.robot.utils.database;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe che rappresenta una tupla del database.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 */
public class Tuple implements Iterable {

    /**
     * La lista dei campi.
     */
    private final List<Field> fields;

    /**
     * Costruttore.
     */
    public Tuple() {
        this.fields = new LinkedList<>();
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void insert(String name, Object value) {
        Field f = new Field(name, value);
        fields.add(f);
    }

    public List<Field> getFields() {
        return fields;
    }

    public boolean isEmpty() {
        return fields.isEmpty();
    }

    public String getColumnName(int index) {
        return fields.get(index).getName();
    }

    public Field getField(String fieldName) {
        Field field = null;
        for (Field f : fields) {
            if (f.getName().equals(fieldName)) {
                field = f;
            }
        }
        return field;
    }

    @Override
    public String toString() {
        return fields.toString();
    }

    public Field getField(int index) {
        return fields.get(index);
    }

    @Override
    public Iterator iterator() {
        return fields.iterator();
    }

    public boolean contains(String s) {
        return fields.contains(s);
    }

}
