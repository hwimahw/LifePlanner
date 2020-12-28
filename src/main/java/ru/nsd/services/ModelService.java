package ru.nsd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsd.LifePlan;
import ru.nsd.Model;
import ru.nsd.Noda;
import ru.nsd.dao.ModelDao;

import java.util.List;
import java.util.Map;

@Component
public class ModelService {
    @Autowired
    ModelDao modelDao;

    public void createTable(List<Noda> leaves){
        modelDao.create(leaves);
    }

    public void insert(Model data){
        modelDao.insert(data);
    }

    public List<Map<String, String>> select(LifePlan lifePlan){
        return modelDao.select(lifePlan);
    }




}
