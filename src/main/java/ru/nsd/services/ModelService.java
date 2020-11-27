package ru.nsd.services;

import ru.nsd.Model;
import ru.nsd.dao.ModelDao;

public class ModelService {
    ModelDao modelDao = new ModelDao();

    public void createTable(Model data){
//        modelDao.create(data);
    }

    public void insert(Model data){
        modelDao.insert(data);
    }

}
