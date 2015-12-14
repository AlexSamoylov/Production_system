package org.dnu.samoylov.storage;

import java.util.LinkedList;
import java.util.List;

public abstract class Storage<T> {
    List<T> list = new LinkedList<>();

    public List<T> getList() {
        return list;
    }

    public void add(T obj) {
        list.add(obj);
    }
}
