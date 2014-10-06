package net.iryndin.ml.dt;

import java.util.List;
import java.util.Map;

/**
 * Builds decision tree with ID3 algorithm
 */
public class ID3DecisionTreeBuilder implements DecisionTreeBuilder {

    private final int minDataPieces;

    public ID3DecisionTreeBuilder(int minDataPieces) {
        this.minDataPieces = minDataPieces;
    }

    @Override
    public DecisionTreeClassifier buildTree(DataSet dataset) {
        DTNode root = buildTree2(null, dataset);
        return new DecisionTreeClassifier(root);
    }

    private DTNode buildTree2(DTNode root, DataSet dataset) {
        if (dataset.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty");
        }

        if (checkStopCriteriaForDataset(dataset)) {
            String label = getMajorityLabel(dataset);
            DTNode leaf = new DTNode(root);
            leaf.setLabel(label);
            return leaf;
        }

        List<DatasetSplit> possibleSplits = SplitUtils.generateSplits(dataset);
        int initialDatasetLength = dataset.size();
        double initialEntropy = MathUtils.entropy(dataset);

        if (checkStopCriteriaForSplits(possibleSplits, initialDatasetLength, initialEntropy)) {
            String label = getMajorityLabel(dataset);
            DTNode leaf = new DTNode(root);
            leaf.setLabel(label);
            return leaf;
        } else {
            SplitUtils.DatasetSplitInfo bestSplitInfo = SplitUtils.findBestSplit(possibleSplits, initialDatasetLength, initialEntropy);
            DatasetSplit bestSplit = bestSplitInfo.getBestSplit();

            DTNode currentNode = new DTNode(root, bestSplit.getSplitColumnName());

            Map<String, DataSet> valueSplits = bestSplit.getValueSplits();
            for (Map.Entry<String, DataSet> e : valueSplits.entrySet()) {
                String columnValue = e.getKey();
                DataSet columnDataset = e.getValue();
                DTNode childNode = buildTree2(currentNode, columnDataset);
                currentNode.getChildNodes().put(columnValue, childNode);
            }

            return currentNode;
        }
    }

    private String getMajorityLabel(DataSet dataset) {
        String label = null;
        int maxLabelCount = -1;
        for (Map.Entry<String, Integer> e : dataset.getLabels().entrySet()) {
            if (e.getValue() > maxLabelCount) {
                maxLabelCount = e.getValue();
                label = e.getKey();
            }
        }
        return label;
    }

    private boolean checkStopCriteriaForDataset(DataSet dataset) {
        return allHaveSameLabel(dataset) || minimalDataPieces(dataset) || noAvailableFeatures(dataset);
    }

    private boolean noAvailableFeatures(DataSet dataset) {
        return dataset.getFeaturesQty() == 0;
    }

    private boolean minimalDataPieces(DataSet dataset) {
        return dataset.size() < minDataPieces;
    }

    private boolean allHaveSameLabel(DataSet dataset) {
        return dataset.getLabels().size() == 1;
    }

    private boolean checkStopCriteriaForSplits(List<DatasetSplit> splits, int initialDatasetLength, double initialEntropy) {
        return checkAllSplitsHaveNegativeInfGain(splits, initialDatasetLength, initialEntropy);
    }

    private boolean checkAllSplitsHaveNegativeInfGain(List<DatasetSplit> possibleSplits, int initialDatasetLength, double initialEntropy) {
        for (DatasetSplit split : possibleSplits) {
            if (SplitUtils.infGain(split, initialDatasetLength, initialEntropy) > 0) {
                return false;
            }
        }
        return true;
    }
}
