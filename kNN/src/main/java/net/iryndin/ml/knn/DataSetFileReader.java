package net.iryndin.ml.knn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSetFileReader {
    private final DataPieceLineReader dataPieceLineReader;

    public DataSetFileReader(String separator, boolean hasName, boolean hasLabel) {
        this.dataPieceLineReader = new DataPieceLineReader(separator, hasName, hasLabel);
    }

    public DataSet read(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<DataPiece> list = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                DataPiece dataPiece = dataPieceLineReader.read(line);
                if (dataPiece != null) {
                    list.add(dataPiece);
                }
            }
            return new DataSet(list);
        }
    }
}
