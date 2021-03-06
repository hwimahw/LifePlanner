package ru.nsd;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Manager {

    Menu menu = new Menu();
    LifePlan lifePlan = new LifePlan();

    public void select() throws Exception {
        int exit = 0;
        do {
            menu.buildPrintMenu();
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            int choice = scanner.nextInt();
            switch (choice) {
                case (1): {
                    lifePlan.print();
                    System.out.println();
                    break;
                }
                case (2): {
                    Map<String, String> subjectPlan = new HashMap<>();
                    scanner.nextLine();
                    String date = scanner.nextLine();
                    while (exit == 0) {
                        String subjectPlanString = scanner.nextLine();
                        if (subjectPlanString.contains("/")) {
                            subjectPlanString = subjectPlanString.replace("/", "");
                            exit = 1;
                        }
                        String[] split = subjectPlanString.split(":");
                        subjectPlan.put(split[0], split[1]);
                    }
                    DayPlan dayPlan = new DayPlan(subjectPlan);
                    lifePlan.addDayPlan(dayPlan, date);
                }
                case (3): {
                    exit = 1;
                    break;
                }
            }
        } while (exit == 0);
    }
}
