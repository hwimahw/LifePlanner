package ru.nsd;

import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.nsd.exceptions.BuildLifePlanException;
import ru.nsd.exceptions.DayPlanPrintToFileException;
import ru.nsd.models.DayPlan;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LifePlan {

    private Noda root;
    private List<Noda> leaves;

    public LifePlan() {
        leaves = new ArrayList<>();
//        buildLifePlan();
    }

    public LifePlan(InputStream inputStream) {
        leaves = new ArrayList<>();
        buildLifePlan(inputStream);
    }

    public Noda getRoot() {
        return root;
    }

    public void setRoot(Noda root) {
        this.root = root;
    }

    private void buildLifePlan() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(new FileInputStream("./src/main/resources/lifePlan.xml"));
            this.setRoot(new Noda(doc.getDocumentElement().getAttribute("name"), null));
            buildLifePlanIter(doc.getDocumentElement(), this.root);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BuildLifePlanException();
        }
    }

    private void buildLifePlan(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputStream);
            this.setRoot(new Noda(doc.getDocumentElement().getAttribute("name"), null));
            buildLifePlanIter(doc.getDocumentElement(), this.root);
        } catch (Exception e) {
            throw new BuildLifePlanException();
        }
    }

    private void buildLifePlanIter(Node nodeFromXmlDocument, Noda noda) {
        NodeList children = nodeFromXmlDocument.getChildNodes();
        if (children.getLength() == 0) {
            leaves.add(noda);
        }
        for (int i = 0; i < children.getLength(); i++) {
            Node nodeChildXmlDocument = children.item(i);
            if (nodeChildXmlDocument.getNodeType() != Node.TEXT_NODE) {
                Noda nodaChild = new Noda(nodeChildXmlDocument.getAttributes().getNamedItem("name").getNodeValue(), noda);
                noda.getChildren().add(nodaChild);
                buildLifePlanIter(nodeChildXmlDocument, nodaChild);
            }
        }
    }

    public void fillPlanOfLeaves(DayPlan dayPlan) {
        Map<String, String> plans = dayPlan.getDayPlan();
        for (Map.Entry<String, String> entry : plans.entrySet()) {
            for (Noda noda : leaves) {
                if (entry.getKey().toUpperCase().equals(noda.getName().toUpperCase())) {
                    if (StringUtils.hasText(entry.getValue())) {
                        noda.setPlan(entry.getValue());
                    }
                }
            }
        }
    }

    public void print() {
        printLifePlanIter(root, "");
    }

    private void printLifePlanIter(Noda noda, String gaps) {
        System.out.println(gaps + noda.getName());
        gaps = gaps + "   ";
        List<Noda> children = noda.getChildren();
        for (int i = 0; i < children.size(); i++) {
            printLifePlanIter(children.get(i), gaps);
        }
    }

    public void fillVisitNodesForPrinting() {
        List<Noda> leaves = this.leaves;
        for (int i = 0; i < leaves.size(); i++) {
            if (leaves.get(i).getPlan() != null) {
                fillVisitNodesForPrintingIter(leaves.get(i));
            }
        }
    }

    public void fillNonVisitNodes() {
        fillNonVisitNodesIter(root);
    }

    private void fillNonVisitNodesIter(Noda root) {
        root.setVisit(0);
        root.setPlan(null);
        List<Noda> children = root.getChildren();
        for (int i = 0; i < children.size(); i++) {
            fillNonVisitNodesIter(children.get(i));
        }
    }

    private void fillVisitNodesForPrintingIter(Noda noda) {
        if (noda == null) {
            return;
        }
        noda.setVisit(1);
        fillVisitNodesForPrintingIter(noda.getParent());
    }

    public void printDayPlanToFile(DayPlan dayPlan) {
        File file = new File("out.txt");
        try {
            file.createNewFile();
        }catch (IOException ex){

        }
        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write("-----------------------------------------------------------\n");
            bufferedWriter.write(dayPlan.getDate() + "\n\n");
            printDayPlanToFileIter(root, bufferedWriter, "");
        } catch (IOException exception) {
            throw new DayPlanPrintToFileException();
        }

    }

    public void printDayPlanToFileIter(Noda noda, BufferedWriter bufferedWriter, String gaps) {
        try {
            if (noda.getVisit() == 1) {
                bufferedWriter.write(gaps + noda.getName() + "\n");
                List<Noda> children = noda.getChildren();
                if (children.size() == 0) {
                    bufferedWriter.write(gaps + "  " + noda.getPlan() + "\n");
                }
                for (Noda child : children) {
                    printDayPlanToFileIter(child, bufferedWriter, gaps + "  ");
                }
            }
        } catch (IOException exception) {
            throw new DayPlanPrintToFileException();
        }
    }

    public void dayPlanToFile(DayPlan dayPlan) {
        fillPlanOfLeaves(dayPlan);
        fillVisitNodesForPrinting();
        printDayPlanToFile(dayPlan);
    }

    public List<Noda> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<Noda> leaves) {
        this.leaves = leaves;
    }
}
