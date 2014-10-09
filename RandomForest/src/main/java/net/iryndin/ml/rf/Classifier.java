package net.iryndin.ml.rf;

public interface Classifier {
    ClassificationResult classify(DataPiece dataPiece);
}
