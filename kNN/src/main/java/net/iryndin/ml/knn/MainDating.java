package net.iryndin.ml.knn;

import java.io.IOException;

public class MainDating {

    public static void main(String[] args) throws IOException {
        DataSetFileReader dsfr = new DataSetFileReader("\t", false, true);
        DataSet initialDataSet = dsfr.read("datingTestSet.txt");
        System.out.println("Labels: " + initialDataSet.getLabels());
        DataSet[] sampledSets = initialDataSet.sampling2(0.95);
        DataSet trainingDataSet = sampledSets[0];
        DataSet testDataSet = sampledSets[1];

        trainingDataSet.normalize();
        testDataSet.normalize();

        KnnClassifier knnClassifier = new KnnClassifier(trainingDataSet, new EuclidDistance());
        for (int N=3; N<=20; N++) {
            double[] errorRates = new double[10];
            for (int i=0; i< errorRates.length; i++) {
                errorRates[i] = errorRate(knnClassifier, testDataSet, N);
            }
            System.out.println("N="+N+", average error rate=" + String.format("%6.4f",avg(errorRates)));
        }
    }

    static double errorRate(KnnClassifier knnClassifier, DataSet testDataSet, int N) {
        int errors = 0;
        for (DataPiece testDataPiece : testDataSet) {
            KnnResult knnResult = knnClassifier.classify(testDataPiece, N);
            if (!knnResult.getLabel().equals(testDataPiece.getLabel())) {
                errors++;
            }
        }
        double errorRate = (double)errors/(double)testDataSet.size();
        return errorRate;
    }

    static double avg(double... a) {
        double sum = 0.0;
        for (double i : a) {
            sum += i;
        }
        return sum/a.length;
    }
}
