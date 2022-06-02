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
import java.util.ArrayList;
import java.util.List;
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
        int number[] = new int[1];
        number[0] = 1;
        prepareIter(lifePlan.getRoot(), 0, userId, lifeDirections, number, null);
        return lifeDirections;
    }


    public LifePlan prepareLifeDirectionsToLifePlan(List<LifeDirection> lifeDirections) {
        if (isEmpty(lifeDirections)) {
            return new LifePlan();
        }
        LifePlan lifePlan = new LifePlan();
        List<Noda> nodas = lifeDirectionsToNodas(lifeDirections);
        prepareLifeDirectionsToLifePlanIter(lifeDirections, nodas);
        Noda root = getRootNoda(lifeDirections, nodas);
        List<Noda> leaves = getLeaves(nodas);
        lifePlan.setRoot(root);
        lifePlan.setLeaves(leaves);
        return lifePlan;
    }

    private void prepareLifeDirectionsToLifePlanIter(List<LifeDirection> lifeDirections, List<Noda> nodas) {
        if (isEmpty(lifeDirections) || isEmpty(nodas)) {
            return;
        }
        for (Noda nod : nodas) {
            if (isEmpty(nod.getChildren())) {
                LifeDirection lifeDirect = getLifeDirectionByName(lifeDirections, nod.getName());
                List<LifeDirection> lifeDirs = lifeDirections.stream().filter(lifeDirection -> lifeDirection.getParentNumber().equals(lifeDirect.getNumber())).collect(Collectors.toList());
                List<Noda> children = lifeDirectionsToNodas(lifeDirs);
                nod.setChildren(children);
                prepareLifeDirectionsToLifePlanIter(lifeDirections, nod.getChildren());
            }
        }
    }

    private Noda getRootNoda(List<LifeDirection> lifeDirections, List<Noda> nodas) {
        List<LifeDirection> lifeDirs = lifeDirections.stream().filter(lifeDirection -> lifeDirection.getParentNumber().equals(0)).collect(Collectors.toList());
        LifeDirection lifeDirection = lifeDirs.get(0);
        List<Noda> nods = nodas.stream().filter(noda -> noda.getName().equals(lifeDirection.getName())).collect(Collectors.toList());
        return nods.get(0);
    }

    private List<Noda> getLeaves(List<Noda> nodas) {
        List<Noda> nods = nodas.stream().filter(noda -> isEmpty(noda.getChildren())).collect(Collectors.toList());
        return nods;
    }

    private List<Noda> lifeDirectionsToNodas(List<LifeDirection> lifeDirections) {
        List<Noda> nodas = new ArrayList<>();
        for (LifeDirection lifeDirection : lifeDirections) {
            nodas.add(new Noda(lifeDirection.getName(), null));
        }
        return nodas;
    }

    private LifeDirection getLifeDirectionByName(List<LifeDirection> lifeDirections, String name) {
        for (LifeDirection lifeDirection : lifeDirections) {
            if (name.equals(lifeDirection.getName())) {
                return lifeDirection;
            }
        }
        return null;
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

    private void prepareIter(Noda noda, int level, long userId, List<LifeDirection> lifeDirections, int[] number, Integer parentNumber) {
        LifeDirection lifeDirection = new LifeDirection(userId, level, noda.getName(), number[0], parentNumber);
        lifeDirections.add(lifeDirection);
        List<Noda> children = noda.getChildren();
        if (children.isEmpty()) {
            return;
        }
        int parNumb = number[0];
        for (Noda child : children) {
            number[0]++;
            prepareIter(child, level + 1, userId, lifeDirections, number, parNumb);
        }
    }
}
