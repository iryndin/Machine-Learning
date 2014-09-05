package net.iryndin.ml.dt;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Single data piece (data row)
 */
public class DataPiece {
    private final Map<String,String> values;
    private final String label;

    public DataPiece(Map<String, String> values, String label) {
        this.values = values;
        this.label = label;
    }

    public Set<String> getRowNames() {
        return values.keySet();
    }

    public String getValue(String name) {
        return values.get(name);
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
}
