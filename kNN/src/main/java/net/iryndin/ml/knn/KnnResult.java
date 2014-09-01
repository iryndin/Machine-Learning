package net.iryndin.ml.knn;

public class KnnResult {
    private final String label;
    private final double probability;

    public KnnResult(String label, double probability) {
        this.label = label;
        this.probability = probability;
    }

    public String getLabel() {
        return label;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "KnnResult{" +
                "label='" + label + '\'' +
                ", probability=" + probability +
                '}';
    }
}
