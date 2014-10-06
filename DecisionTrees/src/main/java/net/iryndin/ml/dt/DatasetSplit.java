package net.iryndin.ml.dt;

import java.util.Map;

/**
 * Possible dataset split
 */
public class DatasetSplit {
    /**
     * Column name by which split is made
     */
    private final String splitColumnName;
    /**
     * Map with splitted datasets
     * Keys are column values
     * Values are datasets with such column value
     */
    private final Map<String, DataSet> valueSplits;

    public DatasetSplit(String splitColumnName, Map<String, DataSet> valueSplits) {
        this.splitColumnName = splitColumnName;
        this.valueSplits = valueSplits;
    }

    public String getSplitColumnName() {
        return splitColumnName;
    }

    public Map<String, DataSet> getValueSplits() {
        return valueSplits;
    }

    @Override
    public String toString() {
        return "DatasetSplit{" +
                "splitColumnName='" + splitColumnName + '\'' +
                ", valueSplits=" + valueSplits +
                '}';
    }
}
