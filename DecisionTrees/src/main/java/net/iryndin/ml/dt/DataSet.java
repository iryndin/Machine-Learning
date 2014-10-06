package net.iryndin.ml.dt;

import java.util.*;

public class DataSet implements Cloneable, Iterable<DataPiece> {
    private final Set<String> columnNames;
    private final List<DataPiece> data;
    private Map<String, Integer> labelMap;

    public DataSet(List<DataPiece> data) {
        this.data = data;
        columnNames = data.get(0).getRowNames();
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.size() == 0;
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

    public Set<String> getColumnNames() {
        return columnNames;
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

    /**
     * Return labels with occurence count
     * @return
     */
    public Map<String, Integer> getLabels() {
        if (labelMap == null) {
            labelMap = calculateLabels();
        }
        return labelMap;
    }

    public Map<String, Integer> calculateLabels() {
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
