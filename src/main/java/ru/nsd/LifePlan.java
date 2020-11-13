package ru.nsd;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LifePlan {

    private Noda root;

    private List<Noda> leaves;

    public LifePlan(){
        leaves = new ArrayList<>();
        buildLifePlan();
    }

    public Noda getRoot() {
        return root;
    }

    public void setRoot(Noda root) {
        this.root = root;
    }

    private void buildLifePlan(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            File file = new File("./src/main/resources/lifePlan.xml");
            Document doc = docBuilder.parse(file);
            this.setRoot(new Noda(doc.getDocumentElement().getAttribute("name"), null));
            buildLifePlanIter(doc.getDocumentElement(), this.root);
        }catch(Exception e){
            return;
        }
    }
    private void buildLifePlanIter(Node node, Noda noda){
        NodeList children = node.getChildNodes();
        if(children.getLength() == 0){
            leaves.add(noda);
        }
        for(int i = 0; i < children.getLength(); i++){
            Node nodeChild = children.item(i);
            if(nodeChild.getNodeType() != Node.TEXT_NODE) {
                Noda nodaChild = new Noda(nodeChild.getAttributes().getNamedItem("name").getNodeValue(), noda);
                noda.getChildren().add(nodaChild);
                buildLifePlanIter(nodeChild, nodaChild);
            }
        }
    }

    public void fillPlanOfLeaves(DayPlan dayPlan){
        Map<String, String> plans = dayPlan.getDayPlan();
        for(Map.Entry<String, String> entry:plans.entrySet()){
            for(Noda noda:this.leaves){
                if(entry.getKey().equals(noda.getName())){
                    noda.setPlan(entry.getValue());
                }
            }
        }
    }

    public void print(){
        printLifePlanIter(this.root, "");
    }

    public void printLifePlanIter(Noda noda, String gaps){
        System.out.println(gaps + noda.getName());
        gaps = gaps + "   ";
        List<Noda> children = noda.getChildren();
        for(int i = 0; i < children.size(); i++){
            printLifePlanIter(children.get(i), gaps);
        }
    }

    public void fillVisitNodesForPrinting(){
        List<Noda> leaves = this.leaves;
        for(int i = 0; i < leaves.size(); i++){
            if(leaves.get(i).getPlan() != null) {
                fillVisitNodesForPrintingIter(leaves.get(i));
            }
        }
    }

    private void fillVisitNodesForPrintingIter(Noda noda){
        if(noda == null){
            return;
        }
        noda.setVisit(1);
        fillVisitNodesForPrintingIter(noda.getParent());
    }

    public void printDayPlanToFile(){
        File file = new File("./src/main/resources/out.txt");
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try{
            fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            Noda noda = this.root;
            bufferedWriter.write("-----------------------------------------------------------\n");
            printDayPlanToFileIter(noda, bufferedWriter, "");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void printDayPlanToFileIter(Noda noda, BufferedWriter bufferedWriter, String gaps){
        try {
            if (noda.getVisit() == 1) {
                bufferedWriter.write(gaps + noda.getName() + "\n");
                List<Noda> children = noda.getChildren();
                if(children.size() == 0){
                    bufferedWriter.write(gaps + "  " + noda.getPlan() + "\n");
                }
                for (int i = 0; i < children.size(); i++) {
                    printDayPlanToFileIter(children.get(i), bufferedWriter, gaps + "  ");
                }
            }
        }catch (Exception ex){
            return;
        }

    }





    public static void main(String[] args) {
        LifePlan lifePlan = new LifePlan();
        String str = "sdsd";
        lifePlan.print();
    }

}
