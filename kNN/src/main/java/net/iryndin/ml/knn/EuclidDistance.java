package net.iryndin.ml.knn;

public class EuclidDistance implements Distance {
    @Override
    public double distance(double[] a1, double[] a2) {
        assert a1.length == a2.length;
        double sum = 0;
        for (int i=0; i<a1.length; i++) {
            double b = a1[i]-a2[i];
            sum += b*b;
        }
        return Math.sqrt(sum);
    }
}
