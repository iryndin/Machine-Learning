package net.iryndin.ml.rf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Single data piece (data row)
 */
public class DataPiece {
    private final Map<String,String> values;
    private final String label;

    public DataPiece(DataPiece dp) {
        this(dp.values, dp.label);
    }

    public DataPiece(Map<String, String> values, String label) {
        this.values = values;
        this.label = label;
    }

    public Set<String> getRowNames() {
        return values.keySet();
    }

    public String getValue(String columnName) {
        return values.get(columnName);
    }

    public String removeValue(String columnName) {
        return values.remove(columnName);
    }

    public String getLabel() {
        return label;
    }

    public int getFeaturesQty() {
        return values.size();
    }

    @Override
    public DataPiece clone() {
        return new DataPiece(new HashMap<>(values), label);
    }

    @Override
    public String toString() {
        return "DataPiece{" +
                "label='" + label + '\'' +
                ", values=" + values +
                '}';
    }

    public void setValue(String columnName, String value) {
        values.put(columnName, value);
    }
}
