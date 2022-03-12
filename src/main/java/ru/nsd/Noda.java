package ru.nsd;

import java.util.ArrayList;
import java.util.List;

public class Noda {

    private String name;
    private String plan;
    private List<Noda> children;
    private Noda parent;
    private int visit;

    public Noda(String name, Noda parent) {
        children = new ArrayList<>();
        this.name = name;
        this.plan = null;
        this.parent = parent;
        visit = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public List<Noda> getChildren() {
        return children;
    }

    public void setChildren(List<Noda> children) {
        this.children = children;
    }

    public Noda getParent() {
        return parent;
    }

    public void setParent(Noda parent) {
        this.parent = parent;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }
}
