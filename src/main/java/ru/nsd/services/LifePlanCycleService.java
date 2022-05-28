package ru.nsd.services;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.nsd.LifePlan;
import ru.nsd.Noda;
import ru.nsd.exceptions.BuildLifePlanException;
import ru.nsd.models.LifeDirection;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

public class LifePlanCycleService {

    public LifePlan createLifePlan(InputStream inputStream) {
        LifePlan lifePlan = new LifePlan();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputStream);
            lifePlan.setRoot(new Noda(doc.getDocumentElement().getAttribute("name"), null));
            buildLifePlanIter(doc.getDocumentElement(), lifePlan.getRoot(), lifePlan);
        } catch (Exception e) {
            throw new BuildLifePlanException();
        }
        return lifePlan;
    }

    public List<LifeDirection> prepareLifePlanToLifeDirections(LifePlan lifePlan, HttpServletRequest request) {
        List<LifeDirection> lifeDirections = new ArrayList<>();
        long userId = (Long) request.getSession().getAttribute("userId");
        prepareIter(lifePlan.getRoot(), 0, userId, lifeDirections, 1, null);
        return lifeDirections;
    }

    public Noda prepareLifeDirectionsToLifePlan(List<LifeDirection> lifeDirections) {
        LifeDirection lifeDirection = findLifeDirectionsByNumber(lifeDirections, 1).get(0);
        Noda root = new Noda.BuilderNode().withName(lifeDirection.getName()).build();
        setChildrenToNoda(root, lifeDirections, 2);

        setChildren(root, 2, lifeDirections);
        return root;
    }

    private void setChildren(Noda noda, Integer number, List<LifeDirection> lifeDirections) {
        if (isEmpty(lifeDirections)) {
            return;
        }
        for (Noda child : noda.getChildren()) {
            number++;
            setChildrenToNoda(child, lifeDirections, number);
            setChildren(child, number, lifeDirections);
        }
    }

    private void setChildrenToNoda(Noda noda, List<LifeDirection> lifeDirections, Integer number) {
        List<Noda> children = lifeDirectionsToNodas(findLifeDirectionsByNumber(lifeDirections, number));
        noda.setChildren(children);
    }


    private List<LifeDirection> findLifeDirectionsByNumber(List<LifeDirection> lifeDirections, Integer number) {
        List<LifeDirection> lifeDirs = lifeDirections.stream().filter(lifeDirection -> number.equals(lifeDirection.getNumber())).collect(Collectors.toList());
        lifeDirections.removeAll(lifeDirs);
        return lifeDirs;
    }

//    public List<Noda> prepareLifeDirectionsToLifePlanLeaves(List<LifeDirection> lifeDirections) {
//        Map<Integer, List<LifeDirection>> levelAndLifeDirections = buildLevelAndLifeDirections(lifeDirections);
//        LifePlan lifePlan = new LifePlan();
//        if (levelAndLifeDirections.isEmpty()) {
//            return new ArrayList<>();
//        }
//        int size = levelAndLifeDirections.size();
//        lifePlan.setLeaves(lifeDirectionsToNodas(levelAndLifeDirections.get(size - 1)));
//        return lifePlan.getLeaves();
//    }

    private List<Noda> lifeDirectionsToNodas(List<LifeDirection> lifeDirections) {
        List<Noda> nodas = new ArrayList<>();
        for (LifeDirection lifeDirection : lifeDirections) {
            nodas.add(new Noda(lifeDirection.getName(), null));
        }
        return nodas;
    }

    private void buildLifePlanIter(Node nodeFromXmlDocument, Noda noda, LifePlan lifePlan) {
        NodeList children = nodeFromXmlDocument.getChildNodes();
        if (children.getLength() == 0) {
            lifePlan.getLeaves().add(noda);
        }
        for (int i = 0; i < children.getLength(); i++) {
            Node nodeChildXmlDocument = children.item(i);
            if (nodeChildXmlDocument.getNodeType() != Node.TEXT_NODE) {
                Noda nodaChild = new Noda(nodeChildXmlDocument.getAttributes().getNamedItem("name").getNodeValue(), noda);
                noda.getChildren().add(nodaChild);
                buildLifePlanIter(nodeChildXmlDocument, nodaChild, lifePlan);
            }
        }
    }

    private void prepareIter(Noda noda, int level, long userId, List<LifeDirection> lifeDirections, Integer number, Integer parentNumber) {
        LifeDirection lifeDirection = new LifeDirection(userId, level, noda.getName(), number, parentNumber);
        lifeDirections.add(lifeDirection);
        List<Noda> children = noda.getChildren();
        if (children.isEmpty()) {
            return;
        }
        for (Noda child : children) {
            prepareIter(child, level + 1, userId, lifeDirections, number + 1, number);
        }
    }
}
