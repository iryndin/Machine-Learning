package net.iryndin.ml.dt;

/**
 * Interface for classifiers
 */
public interface Classifier {
    ClassificationResult classify(DataPiece dataPiece);
}
