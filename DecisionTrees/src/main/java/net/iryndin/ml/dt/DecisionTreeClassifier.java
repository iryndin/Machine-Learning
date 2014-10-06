package net.iryndin.ml.dt;

/**
 * Decision Tree classifier
 */
public class DecisionTreeClassifier implements Classifier {

    private final DTNode root;

    public DecisionTreeClassifier(DTNode root) {
        this.root = root;
    }

    @Override
    public ClassificationResult classify(DataPiece dataPiece) {
        DTNode node = root;
        while (!node.isLeaf()) {
            String columnName = node.getColumnName();
            String columnValue = dataPiece.getValue(columnName);
            node = node.getChildNodes().get(columnValue);
        }
        String label = node.getLabel();
        return new ClassificationResult(label, -1.0);
    }
}
