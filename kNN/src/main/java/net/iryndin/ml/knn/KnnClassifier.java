package net.iryndin.ml.knn;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class KnnClassifier {
    private final DataSet trainingDataSet;
    private final Distance distance;

    public KnnClassifier(DataSet trainingDataSet, Distance distance) {
        this.trainingDataSet = trainingDataSet;
        this.distance = distance;
    }

    public KnnResult classify(DataPiece testDataPiece, int N) {
        if (N > trainingDataSet.size()) {
            throw new IllegalArgumentException("N should be <= training dataset size");
        }
        if (testDataPiece.getFeaturesQty() != trainingDataSet.getFeaturesQty()) {
            throw new IllegalArgumentException("Test dataset should have the same features qty as training data set");
        }
        NavigableMap<Double, String> distanceMap = new TreeMap<>();
        for (DataPiece i : trainingDataSet) {
            double dist = distance.distance(i.getValues(), testDataPiece.getValues());
            distanceMap.put(dist, i.getLabel());
        }

        Map<String, Integer> labelMap = new HashMap<>();
        int i=0;
        for (Map.Entry<Double, String> e : distanceMap.entrySet()) {
            i++;
            if (i > N) {
                break;
            }
            String label = e.getValue();
            Integer count = labelMap.get(label);
            if (count == null) {
                count = 0;
            }
            count++;
            labelMap.put(label, count);
        }
        double totalSum = 0;
        String maxLabel = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> e : labelMap.entrySet()) {
            totalSum += e.getValue();
            if (e.getValue() > maxCount) {
                maxLabel = e.getKey();
                maxCount = e.getValue();
            }
        }
        return new KnnResult(maxLabel, maxCount / totalSum);
    }


}
