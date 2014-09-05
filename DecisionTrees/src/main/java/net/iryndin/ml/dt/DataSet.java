package net.iryndin.ml.dt;

import java.util.*;

public class DataSet implements Cloneable, Iterable<DataPiece> {
    private final List<DataPiece> data;

    public DataSet(List<DataPiece> data) {
        this.data = data;
    }

    public int size() {
        return data.size();
    }

    public int getFeaturesQty() {
        return data.get(0).getFeaturesQty();
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
            if (dp.getFeaturesQty() != featuresQty) {
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

    @Override
    public Iterator<DataPiece> iterator() {
        return data.iterator();
    }

    public Map<String, Integer> getLabels() {
        Map<String, Integer> map = new HashMap<>();
        for (DataPiece dp : data) {
            String label = dp.getLabel();
            Integer count = map.get(label);
            if (count == null) {
                count=0;
            }
            count++;
            map.put(label, count);
        }
        return map;
    }

    public Map<String, Integer> getColumnValues(String columnName) {
        Map<String, Integer> map = new HashMap<>();
        for (DataPiece dp : data) {
            String columnValue = dp.getValue(columnName);
            Integer count = map.get(columnValue);
            if (count == null) {
                count=0;
            }
            count++;
            map.put(columnValue, count);
        }
        return map;
    }
}
