package net.iryndin.ml.knn;

import java.util.Arrays;

public class DataPiece implements Cloneable {
    private final String name;
    private final double[] values;
    private final String label;

    public DataPiece(String name, double[] values, String label) {
        this.name = name;
        this.values = values;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public double getValue(int i) {
        return values[i];
    }

    public void setValue(double v, int i) {
        values[i] = v;
    }

    public double[] getValues() {
        return values;
    }

    public String getLabel() {
        return label;
    }

    public int getFeaturesQty() {
        return values.length;
    }

    @Override
    public DataPiece clone() {
        return new DataPiece(name, Arrays.copyOf(values, values.length), label);
    }

    @Override
    public String toString() {
        return "DataPiece{" +
                "name='" + name + '\'' +
                ", values=" + Arrays.toString(values) +
                ", label='" + label + '\'' +
                '}';
    }
}
