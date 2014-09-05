package net.iryndin.ml.dt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataSetFileReader {

    private final String separator;
    private final String labelColumnName;

    public DataSetFileReader(String separator, String labelColumnName) {
        this.separator = separator;
        this.labelColumnName = labelColumnName;
    }

    public DataSet read(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<DataPiece> list = new ArrayList<>();
            String line;
            boolean columnNamesRead = false;
            DataPieceLineReader dataPieceLineReader = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.startsWith("#")) {
                    continue;
                }
                if (!columnNamesRead) {
                    Map<Integer, String> columnNumberColumnNameAssoc = readColumnNames(separator, line);
                    dataPieceLineReader = new DataPieceLineReader(separator, labelColumnName, columnNumberColumnNameAssoc);
                    columnNamesRead = true;
                } else {
                    DataPiece dataPiece = dataPieceLineReader.read(line);
                    if (dataPiece != null) {
                        list.add(dataPiece);
                    }
                }

            }
            return new DataSet(list);
        }
    }

    public static Map<Integer, String> readColumnNames(String separator, String line) {
        String[] a = line.split(separator);
        Map<Integer, String> map = new TreeMap<>();
        for (int i=0; i<a.length; i++) {
            String columnName = a[i].trim();
            map.put(i, columnName);
        }
        return map;
    }
}
