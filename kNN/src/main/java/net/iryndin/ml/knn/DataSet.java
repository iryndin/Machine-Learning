package net.iryndin.ml.knn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataSet implements Cloneable, Iterable<DataPiece> {
    private final List<DataPiece> data;

    public DataSet(List<DataPiece> data) {
        this.data = data;
    }

    public int size() {
        return data.size();
    }

    public void normalize() {
        int fq = getFeaturesQty();
        for (int i=0; i<fq; i++) {
            final double[] minMax = findMinMax(data, i);
            final double min = minMax[0];
            final double delta = minMax[1]-min;
            for (DataPiece dp : data) {
                double normalizedValue = (dp.getValue(i) - min)/delta;
                dp.setValue(normalizedValue, i);
            }
        }
    }

    public int getFeaturesQty() {
        return data.get(0).getValues().length;
    }

    public static double[] findMinMax(List<DataPiece> list, int i) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (DataPiece dp : list) {
            double b = dp.getValue(i);
            if (b > max) {
                max = b;
            }
            if (b < min) {
                min = b;
            }
        }
        return new double[]{min, max};
    }

    @Override
    public DataSet clone() {
        List<DataPiece> list = new ArrayList<>(data.size());
        for (DataPiece dp : data) {
            list.add(dp.clone());
        }
        return new DataSet(list);
    }

    public void selfCheck() throws Exception {
        final int featuresQty = getFeaturesQty();
        for (DataPiece dp : data) {
            if (dp.getValues().length != featuresQty) {
                throw new Exception("All data pieces should have the same quantity of values. Expected qty: " + featuresQty + ", wrong data piece: " + dp);
            }
        }
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "data=" + data +
                '}';
    }

    @Override
    public Iterator<DataPiece> iterator() {
        return data.iterator();
    }
}
