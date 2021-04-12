package ru.nsd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsd.dao.CategoryDao;
import ru.nsd.dao.SpiritDao;
import ru.nsd.models.spiritModels.Category;
import ru.nsd.models.spiritModels.Spirit;

@Component
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    public void setCategory(Category category) {
        categoryDao.setCategory(category);
    }

}