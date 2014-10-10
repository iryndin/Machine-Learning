package net.iryndin.ml.rf;

import java.util.List;

public class RandomForestClassifier implements Classifier {

    private final List<DTNode> trees;

    public RandomForestClassifier(List<DTNode> trees) {
        this.trees = trees;
    }

    @Override
    public ClassificationResult classify(DataPiece dataPiece) {
        Voting voting = new Voting();
        for (DTNode tree : trees) {
            String s = classifyWithOneTree(tree, dataPiece);
            voting.add(s);
        }
        return voting.getMajority();
    }

    private static String classifyWithOneTree(DTNode root, DataPiece dataPiece) {
        DTNode node = root;
        while (!node.isLeaf()) {
            String columnName = node.getColumnName();
            String columnValue = dataPiece.getValue(columnName);
            node = node.getChildNodes().get(columnValue);
        }
        return node.getLabel();
    }
}
