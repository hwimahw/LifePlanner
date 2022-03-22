package ru.nsd;

import ru.nsd.exceptions.DateBuildException;
import ru.nsd.exceptions.DayPlanBuildException;
import ru.nsd.utils.Utils;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Manager {

    private Menu menu = new Menu();
    private LifePlan lifePlan = new LifePlan();
    private boolean exit = false;

    public void select() throws Exception {
        do {
            menu.buildPrintMenu();
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            switch (scanner.nextInt()) {
                case (1): {
                    lifePlan.print();
                    System.out.println();
                    break;
                }
                case (2): {
                    Map<String, String> subjectAndPlanMap = new HashMap<>();
                    scanner.nextLine();
                    String date = scanner.nextLine();
                    LocalDate localDate = Utils.buildDate(date);
                    while (!exit) {
                        String subjectAndPlan = scanner.nextLine();
                        buildAndAddSubjectAndPlan(subjectAndPlan, subjectAndPlanMap);
                    }
                    DayPlan dayPlan = new DayPlan(localDate, subjectAndPlanMap);
                    lifePlan.dayPlanToFile(dayPlan);
                }
                case (3): {
                    exit = true;
                    break;
                }
            }
        } while (!exit);
    }

    private void buildAndAddSubjectAndPlan(String subjectAndPlan, Map<String, String> subjectPlan) {
        try {
            if (subjectAndPlan.contains("/")) {
                subjectAndPlan = subjectAndPlan.replace("/", "");
                exit = true;
            }
            String[] split = subjectAndPlan.split(":");
            subjectPlan.put(split[0], split[1]);
        } catch (Exception exception) {
            throw new DayPlanBuildException();
        }
    }
}
