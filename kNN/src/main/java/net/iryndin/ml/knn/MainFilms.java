package net.iryndin.ml.knn;

import java.io.IOException;

public class MainFilms {
    public static void main(String[] args) throws IOException {
        DataSetFileReader dsfr = new DataSetFileReader(",", true, LabelPlacementEnum.LAST_COLUMN);
        DataSet trainingDataSet = dsfr.read("data.txt");
        //System.out.println(trainingDataSet);
        DataPieceLineReader lineReader = new DataPieceLineReader(",",false,LabelPlacementEnum.NOLABEL);
        DataPiece dataPiece = lineReader.read("18,90");
        KnnClassifier knnClassifier = new KnnClassifier(trainingDataSet, new EuclidDistance());
        for (int N=1; N<=trainingDataSet.size(); N++) {
            KnnResult knnResult = knnClassifier.classify(dataPiece, N);
            System.out.println("N="+N+": " + knnResult);
        }
    }
}
