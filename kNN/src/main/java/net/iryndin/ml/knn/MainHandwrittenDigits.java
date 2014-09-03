package net.iryndin.ml.knn;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainHandwrittenDigits {

    public static final String FILE_TRAINSET = "handwrittenDigits_train.csv";
    public static final String FILE_TESTSET = "handwrittenDigits_test.csv";
    public static final String FILE_RESULT = "result.csv";

    public static void main(String[] args) throws IOException {
        //calibrateN();
        handleKaggleTestSet();
    }

    public static void handleKaggleTestSet() throws IOException {
        DataSetFileReader dsfr = new DataSetFileReader(",", false, LabelPlacementEnum.FIRST_COLUMN);
        DataSet trainingDataSet = dsfr.read(FILE_TRAINSET);
        System.out.println("training dataset size: " + trainingDataSet.size());
        System.out.println("Labels: " + trainingDataSet.getLabels());

        dsfr = new DataSetFileReader(",", false, LabelPlacementEnum.NOLABEL);
        DataSet testDataSet = dsfr.read(FILE_TESTSET);
        System.out.println("test dataset size: " + testDataSet.size());

        KnnClassifier knnClassifier = new KnnClassifier(trainingDataSet, new EuclidDistance());

        final int N=6;
        List<Integer> result = new ArrayList<>(testDataSet.size());

        int counter = 0;
        final long startMillis = System.currentTimeMillis();
        long startMillis2 = System.currentTimeMillis();
        for (DataPiece testDataPiece : testDataSet) {
            KnnResult knnResult = knnClassifier.classify(testDataPiece, N);
            int digit = Integer.parseInt(knnResult.getLabel());
            result.add(digit);
            counter++;
            if (counter % 500 == 0) {
                final long elapsedMillis2 = System.currentTimeMillis() - startMillis2;
                startMillis2 = System.currentTimeMillis();
                System.out.println("Processed " + counter+", seconds: " + elapsedMillis2/1000);
            }
        }
        final long elapsedMillis = System.currentTimeMillis() - startMillis;
        System.out.println("Total elapsed seconds: " + elapsedMillis/1000);
        writeFile(FILE_RESULT,result);
    }

    private static void writeFile(String filename, List<Integer> result) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            int j = 1;
            writer.write("ImageId,Label\n");
            for (int i : result) {
                writer.write(""+j+","+i+"\n");
                j++;
            }
        }
        System.out.println("File written: " + filename);
    }

    public static void calibrateN() throws IOException {
        DataSetFileReader dsfr = new DataSetFileReader(",", false, LabelPlacementEnum.FIRST_COLUMN);
        DataSet initialDataSet = dsfr.read("handwrittenDigits_train.csv");
        System.out.println("Initial set size: " + initialDataSet.size());
        System.out.println("Labels: " + initialDataSet.getLabels());

        //trainingDataSet.normalize();
        //testDataSet.normalize();


        for (int N=3; N<=20; N++) {
            double[] errorRates = new double[10];
            for (int i=0; i< errorRates.length; i++) {
                final long startMillis = System.currentTimeMillis();

                DataSet[] sampledSets = initialDataSet.sampling(0.99);
                DataSet trainingDataSet = sampledSets[0];
                DataSet testDataSet = sampledSets[1];


                KnnClassifier knnClassifier = new KnnClassifier(trainingDataSet, new EuclidDistance());

                errorRates[i] = errorRate(knnClassifier, testDataSet, N);
                final long elapsedMillis = System.currentTimeMillis() - startMillis;
                System.out.println("N="+N + ", i="+i+", elapsed secs="+elapsedMillis/1000+", error rate=" + errorRates[i]);
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
