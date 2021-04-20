package ru.nsd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsd.models.spiritModels.Category;
import ru.nsd.models.spiritModels.Spirit;
import ru.nsd.services.CategoryService;
import ru.nsd.services.SpiritService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/SpiritDev")
public class SpiritualDevController {

    @Autowired
    SpiritService spiritService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/new")
    public String newSpiritDevForm() {
        return "spirit/spiritualDevelopment";
    }

    @PostMapping
    public String setNewItem(@RequestParam(value = "item", required = false) String item,
                             @RequestParam(value = "category", required = false) String categoryName) {
        Category category = new Category(categoryName);
        Spirit spirit = new Spirit(item, category);
        spiritService.setItem(spirit);
        return "spirit/spiritualDevelopment";
    }
}
