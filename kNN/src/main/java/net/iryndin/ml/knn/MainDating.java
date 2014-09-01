package net.iryndin.ml.knn;

import java.io.IOException;

public class MainDating {

    public static void main(String[] args) throws IOException {
        DataSetFileReader dsfr = new DataSetFileReader("\t", false, true);
        DataSet trainingDataSet = dsfr.read("datingTestSet.txt");
        System.out.println(trainingDataSet);
        trainingDataSet.normalize();
        System.out.println(trainingDataSet);
    }
}
