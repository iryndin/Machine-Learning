package net.iryndin.ml.rf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RandomForestBuilder {

    public RandomForestClassifier build(DataSet dataset, double sampleCoef, int treeQty, int usedFeaturesQty) {
        List<DTNode> trees = new ArrayList<>(treeQty);
        for (int i=0; i< treeQty; i++) {
            DTNode root = buildDecisionTree(dataset, sampleCoef, usedFeaturesQty);
            trees.add(root);
        }
        return new RandomForestClassifier(trees);
    }

    private DTNode buildDecisionTree(DataSet dataset, double sampleCoef, int usedFeaturesQty) {
        DataSet trainDataset = Bootstrapping.withReplacement(dataset, sampleCoef);
        return buildDecisionTree(trainDataset, usedFeaturesQty);
    }

    private DTNode buildDecisionTree(DataSet trainDataset, int usedFeaturesQty) {
        return buildTree(null, trainDataset, usedFeaturesQty);
    }

    private DTNode buildTree(DTNode root, DataSet dataset, int usedFeaturesQty) {
        if (dataset.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty");
        }

        if (checkStopCriteriaForDataset(dataset)) {
            String label = getMajorityLabel(dataset);
            DTNode leaf = new DTNode(root);
            leaf.setLabel(label);
            return leaf;
        }

        Set<String> allColumnNames = dataset.getColumnNames();
        Set<String> usedColumnNames = MiscUtils.sample(allColumnNames, usedFeaturesQty);

        List<DatasetSplit> possibleSplits = SplitUtils.generateSplits(dataset, usedColumnNames);
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
                DTNode childNode = buildTree(currentNode, columnDataset, usedFeaturesQty);
                currentNode.getChildNodes().put(columnValue, childNode);
            }

            return currentNode;
        }
    }

    private boolean checkStopCriteriaForDataset(DataSet dataset) {
        return allHaveSameLabel(dataset) || noAvailableFeatures(dataset);
    }

    private boolean noAvailableFeatures(DataSet dataset) {
        return dataset.getFeaturesQty() == 0;
    }

    private boolean allHaveSameLabel(DataSet dataset) {
        return dataset.getLabels().size() == 1;
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
