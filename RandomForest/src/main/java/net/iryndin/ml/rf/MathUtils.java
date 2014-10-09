package net.iryndin.ml.rf;

import java.util.Map;

public class MathUtils {
    public static double entropy(DataSet dataset) {
        final double len = dataset.size();
        Map<String, Integer> labelValues = dataset.getLabels();
        double result = 0.0;
        for (int labelCount : labelValues.values()) {
            double labelProbability = labelCount/len;
            result -= labelProbability*log2(labelProbability);
        }
        return result;
    }

    public static final double LOG2 = Math.log(2.0);

    public static double log2(double a) {
        return Math.log(a)/LOG2;
    }
}
