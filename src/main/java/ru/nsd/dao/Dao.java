package ru.nsd.dao;

import java.util.List;

public interface Dao<T> {
    boolean insert(T data);
}
