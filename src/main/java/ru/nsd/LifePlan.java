package ru.nsd;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.nsd.exceptions.InvalidXmlFileException;
import ru.nsd.exceptions.NoLifePlanException;
import ru.nsd.exceptions.WriteToFileException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LifePlan {

    private Noda root;

    private List<Noda> leaves;

    Object object = new Object();

    public LifePlan() {
        leaves = new ArrayList<>();
        buildLifePlan();
    }


    public LifePlan(InputStream inputStream) throws InvalidXmlFileException {
        leaves = new ArrayList<>();
        try {
            buildLifePlan(inputStream);
        }catch(InvalidXmlFileException ex){
            throw ex;
        }
    }

    public Object getObject() {
        return object;
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
            Document doc = docBuilder.parse(getClass().getResourceAsStream("./src/test/resources/lifePlan.xml"));
            this.setRoot(new Noda(doc.getDocumentElement().getAttribute("name"), null));
            buildLifePlanIter(doc.getDocumentElement(), this.root);
        } catch (Exception e) {
            return;
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
            throw new InvalidXmlFileException("XML is invalid");
        }
    }

    private void buildLifePlanIter(Node node, Noda noda) {
        NodeList children = node.getChildNodes();
        if (children.getLength() == 0) {
            leaves.add(noda);
        }
        for (int i = 0; i < children.getLength(); i++) {
            Node nodeChild = children.item(i);
            if (nodeChild.getNodeType() != Node.TEXT_NODE) {
                Noda nodaChild = new Noda(nodeChild.getAttributes().getNamedItem("name").getNodeValue(), noda);
                noda.getChildren().add(nodaChild);
                buildLifePlanIter(nodeChild, nodaChild);
            }
        }
    }

    public void fillPlanOfLeaves(DayPlan dayPlan) {
        Map<String, String> plans = dayPlan.getDayPlan();
        for (Map.Entry<String, String> entry : plans.entrySet()) {
            for (Noda noda : this.leaves) {
                if (entry.getKey().equals(noda.getName().toUpperCase())) {
                    if (!"null".equals(entry.getValue())) {
                        noda.setPlan(entry.getValue());
                    }
                }
            }
        }
    }

    public void addDayPlan(DayPlan dayPlan, String date){
        fillPlanOfLeaves(dayPlan);
        fillVisitNodesForPrinting();
        printDayPlanToFile(date);
    }

    public void print() {
        printLifePlanIter(this.root, "");
    }

    private void printLifePlanIter(Noda noda, String gaps) {
        System.out.println(gaps + noda.getName());
        gaps = gaps + "   ";
        List<Noda> children = noda.getChildren();
        for (int i = 0; i < children.size(); i++) {
            printLifePlanIter(children.get(i), gaps);
        }
    }

    private void fillVisitNodesForPrinting() {
        List<Noda> leaves = this.leaves;
        for (int i = 0; i < leaves.size(); i++) {
            if (leaves.get(i).getPlan() != null) {
                fillVisitNodesForPrintingIter(leaves.get(i));
            }
        }
    }

    public void fillNonVisitNodes() {
        fillNonVisitNodesIter(this.root);
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

    public void printDayPlanToFile(String date) {
        File file = new File("out.txt");
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            Noda noda = this.root;
            bufferedWriter.write("-----------------------------------------------------------\n");
            bufferedWriter.write(date + "\n\n");
            printDayPlanToFileIter(noda, bufferedWriter, "");
        } catch (IOException e) {
            throw new WriteToFileException("Writing to file exception");
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void printDayPlanToFileIter(Noda noda, BufferedWriter bufferedWriter, String gaps) {
        try {
            if (noda.getVisit() == 1) {
                bufferedWriter.write(gaps + noda.getName() + "\n");
                List<Noda> children = noda.getChildren();
                if (children.size() == 0) {
                    bufferedWriter.write(gaps + "  " + noda.getPlan() + "\n");
                }
                for (int i = 0; i < children.size(); i++) {
                    printDayPlanToFileIter(children.get(i), bufferedWriter, gaps + "  ");
                }
            }
        } catch (Exception ex) {
            throw new WriteToFileException("Writing to file exception");
        }

    }

    public List<Noda> getLeaves() {
        return leaves;
    }

}
