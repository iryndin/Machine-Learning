package net.iryndin.ml.dt;

import java.util.HashMap;
import java.util.Map;

public class DataPieceLineReader {

    private final String separator;
    private final String labelColumnName;
    private final Map<Integer, String> columnNumberColumnNameAssoc;

    public DataPieceLineReader(String separator, String labelColumnName, Map<Integer, String> columnNumberColumnNameAssoc) {
        this.separator = separator;
        this.labelColumnName = labelColumnName;
        this.columnNumberColumnNameAssoc = columnNumberColumnNameAssoc;
    }

    public DataPiece read(String line) {
        if (line == null) {
            return null;
        }
        line = line.trim();
        if (line.isEmpty()) {
            return null;
        }
        if (line.startsWith("#")) {
            return null;
        }
        String[] a = line.split(separator);
        Map<String, String> map = new HashMap<>(a.length*2);
        String label = null;
        for (int i=0; i<a.length; i++) {
            String value = a[i].trim();
            String columnName = columnNumberColumnNameAssoc.get(i);
            if (labelColumnName != null && labelColumnName.equals(columnName)) {
                label = value;
            } else {
                map.put(columnName, value);
            }
        }
        return new DataPiece(map, label);
    }
}
