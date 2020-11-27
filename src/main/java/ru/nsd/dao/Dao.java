package ru.nsd.dao;

import java.util.List;

public interface Dao<T> {
    public void create(T data);
    public void insert(T data);
}
