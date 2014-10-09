package net.iryndin.ml.rf;

public class ClassificationResult {
    private final String label;
    private final double probability;

    public ClassificationResult(String label, double probability) {
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
        return "ClassificationResult{" +
                "label='" + label + '\'' +
                ", probability=" + probability +
                '}';
    }
}
