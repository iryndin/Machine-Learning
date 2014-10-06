package net.iryndin.ml.dt;

import java.util.HashMap;
import java.util.Map;

public class DTNode {
    private final DTNode parentNode;
    private final String columnName;
    private Map<String, DTNode> childNodes;
    private String label;

    public DTNode(DTNode parentNode) {
        this.parentNode = parentNode;
        this.columnName = null;
        this.childNodes = null;
    }

    public DTNode(DTNode parentNode, String columnName) {
        this.parentNode = parentNode;
        this.columnName = columnName;
        this.childNodes = new HashMap<>();
    }

    public DTNode getParentNode() {
        return parentNode;
    }

    public String getColumnName() {
        return columnName;
    }

    public Map<String, DTNode> getChildNodes() {
        return childNodes;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        this.childNodes = null;
    }

    public boolean isLeaf() {
        return label != null;
    }

    public boolean isRoot() {
        return parentNode == null;
    }

    @Override
    public String toString() {
        return "DTNode{" +
                "parentNode=" + (parentNode != null ? parentNode.columnName : "null") +
                ", columnName='" + columnName + '\'' +
                ", childNodes=" + childNodes +
                ", label='" + label + '\'' +
                '}';
    }
}