package net.iryndin.ml.knn;

import java.util.*;

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
                "size=" + data.size()+
                ",data=" + data +
                '}';
    }

    public Set<String> getLabels() {
        Set<String> labels = new TreeSet<>();
        for (DataPiece dp : data) {
            if (dp.getLabel() != null) {
                labels.add(dp.getLabel());
            }
        }
        return labels;
    }

    @Override
    public Iterator<DataPiece> iterator() {
        return data.iterator();
    }

    public DataSet[] sampling(double portion) {
        if (portion <= 0 || portion >= 1.0) {
            throw new IllegalArgumentException("portion should be between 0.0 and 1.0 exclusive");
        }

        if (portion <= 0.5) {
            int sampleSize = (int)(data.size()*portion);
            Set<Integer> indexSet = randomIndexes(sampleSize, data.size());
            List<DataPiece> list1 = new ArrayList<>(sampleSize);
            List<DataPiece> list2 = new ArrayList<>(data.size() - sampleSize);
            for (int i=0; i<data.size(); i++) {
                if (indexSet.contains(i)) {
                    list1.add(data.get(i).clone());
                } else {
                    list2.add(data.get(i).clone());
                }
            }
            return new DataSet[]{new DataSet(list1), new DataSet(list2)};
        } else {
            int sampleSize = (int)Math.round(data.size()*(1.0-portion));
            Set<Integer> indexSet = randomIndexes(sampleSize, data.size());
            List<DataPiece> list1 = new ArrayList<>(data.size() - sampleSize);
            List<DataPiece> list2 = new ArrayList<>(sampleSize);
            for (int i=0; i<data.size(); i++) {
                if (indexSet.contains(i)) {
                    list2.add(data.get(i).clone());
                } else {
                    list1.add(data.get(i).clone());
                }
            }
            return new DataSet[]{new DataSet(list1), new DataSet(list2)};
        }
    }

    static Set<Integer> randomIndexes(int setSize, int maxValue) {
        Set<Integer> indexSet = new HashSet<>(setSize);
        while (indexSet.size() < setSize) {
            int idx;
            do {
                idx = new Random().nextInt(maxValue);
            } while (indexSet.contains(idx));
            indexSet.add(idx);
        }
        return indexSet;
    }

    //public DataSet
}
