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

    public Noda(String name, String plan, List<Noda> children, Noda parent, int visit) {
        this.name = name;
        this.plan = plan;
        this.children = children;
        this.parent = parent;
        this.visit = visit;
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

    public static class BuilderNode {

        private String name;
        private String plan;
        private List<Noda> children;
        private Noda parent;
        private int visit;

        public BuilderNode withName(String name) {
            this.name = name;
            return this;
        }

        public BuilderNode withPlan(String plan) {
            this.plan = plan;
            return this;
        }

        public BuilderNode withChildren(List<Noda> children) {
            this.children = children;
            return this;
        }

        public BuilderNode withParent(Noda parent) {
            this.parent = parent;
            return this;
        }

        public BuilderNode withVisit(int visit) {
            this.visit = visit;
            return this;
        }

        public Noda build() {
            return new Noda(name, plan, children, parent, visit);
        }
    }
}
