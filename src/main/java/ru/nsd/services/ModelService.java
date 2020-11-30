package ru.nsd.services;

import ru.nsd.Model;
import ru.nsd.Noda;
import ru.nsd.dao.ModelDao;

import java.util.List;

public class ModelService {
    ModelDao modelDao = new ModelDao();

    public void createTable(List<Noda> leaves){
        modelDao.create(leaves);
    }

    public void insert(Model data){
        modelDao.insert(data);
    }

}
