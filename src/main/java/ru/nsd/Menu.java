package ru.nsd;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<String> menu = new ArrayList<>();

    Menu(){
        buildMainMenu();
    }

    public void buildPrintMenu() {
        for (int i = 0; i < menu.size(); i++) {
            System.out.println(menu.get(i));
        }
    }

    private void buildMainMenu() {
        menu.add("Сделайте выбор");
        menu.add("1) Показать LifePlan");
        menu.add("2) Планирование дня");
        menu.add("3) Выход");
    }
}
