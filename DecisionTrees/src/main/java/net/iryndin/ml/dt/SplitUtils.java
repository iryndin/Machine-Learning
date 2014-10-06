package net.iryndin.ml.dt;

import java.util.*;

/**
 * Split utils
 */
public class SplitUtils {

    public static class DatasetSplitInfo {
        private final DatasetSplit bestSplit;
        private final double bestInfGain;
        private final Map<String, Double> infGainMap;

        public DatasetSplitInfo(DatasetSplit bestSplit, double bestInfGain, Map<String, Double> infGainMap) {
            this.bestSplit = bestSplit;
            this.bestInfGain = bestInfGain;
            this.infGainMap = infGainMap;
        }

        public DatasetSplit getBestSplit() {
            return bestSplit;
        }

        public double getBestInfGain() {
            return bestInfGain;
        }

        public Map<String, Double> getInfGainMap() {
            return infGainMap;
        }

        @Override
        public String toString() {
            return "DatasetSplitInfo{" +
                    "bestSplit=" + bestSplit +
                    ", bestInfGain=" + bestInfGain +
                    ", infGainMap=" + infGainMap +
                    '}';
        }
    };

    public static List<DatasetSplit> generateSplits(DataSet dataset) {
        Set<String> availableColumnNames = dataset.getColumnNames();
        List<DatasetSplit> list = new ArrayList<>(availableColumnNames.size());
        for (String columnName : availableColumnNames) {
            list.add(splitDataSet(dataset, columnName));
        }
        return list;
    }

    public static DatasetSplit splitDataSet(DataSet dataset, String columnName) {
        Map<String, List<DataPiece>> sublistMap = new HashMap<>();
        for (DataPiece dp : dataset) {
            DataPiece newDataPiece = dp.clone();
            String columnValue = newDataPiece.removeValue(columnName);
            List<DataPiece> subList = sublistMap.get(columnValue);
            if (subList == null) {
                subList = new ArrayList<>();
                sublistMap.put(columnValue, subList);
            }
            subList.add(newDataPiece);
        }
        Map<String, DataSet> subdatasetMap = new HashMap<>(sublistMap.size()*2);
        for (Map.Entry<String, List<DataPiece>> e : sublistMap.entrySet()) {
            subdatasetMap.put(e.getKey(), new DataSet(e.getValue()));
        }
        return new DatasetSplit(columnName, subdatasetMap);
    }

    public static DatasetSplitInfo findBestSplit(DataSet dataset) {
        List<DatasetSplit> possibleSplits = generateSplits(dataset);
        int initialDatasetLength = dataset.size();
        double initialEntropy = MathUtils.entropy(dataset);

        return findBestSplit(possibleSplits, initialDatasetLength, initialEntropy);
    }

    public static DatasetSplitInfo findBestSplit(List<DatasetSplit> possibleSplits, int initialDatasetLength, double initialEntropy) {
        Map<String, Double> infGainMap = new HashMap<>(possibleSplits.size()*2);
        double maxInfoGain = 0.0;
        DatasetSplit bestSplit = null;

        for (DatasetSplit split : possibleSplits) {
            double splitInfGain = infGain(split, initialDatasetLength, initialEntropy);
            infGainMap.put(split.getSplitColumnName(), splitInfGain);
            if (splitInfGain > maxInfoGain) {
                maxInfoGain = splitInfGain;
                bestSplit = split;
            }
        }
        return new DatasetSplitInfo(bestSplit, maxInfoGain, infGainMap);
    }

    public static double infGain(DatasetSplit split, double initialDatasetLength, double initialEntropy) {
        return initialEntropy - entropyDelta(split, initialDatasetLength);
    }

    public static double entropyDelta(DatasetSplit split, double initialDatasetLength) {
        Map<String, DataSet> splits = split.getValueSplits();
        double infGain = 0.0;
        for (DataSet subDataSet : splits.values()) {
            double probability = subDataSet.size() / initialDatasetLength;
            infGain += probability*MathUtils.entropy(subDataSet);
        }
        return infGain;
    }
}
