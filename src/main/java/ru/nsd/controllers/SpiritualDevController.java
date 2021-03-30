package ru.nsd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsd.models.Spirit;
import ru.nsd.services.SpiritService;

@Controller
@RequestMapping("/SpiritDev")
public class SpiritualDevController {

    @Autowired
    SpiritService spiritService;

    @GetMapping("/new")
    public String newSpiritDevForm() {
        return "spirit/spiritualDevelopment";
    }

    @PostMapping
    public String setNewItem(@ModelAttribute Spirit spirit) {
        spiritService.setItem(spirit);
        return "spirit/spiritualDevelopment";
    }
}
