import java.util.List;

public class Data {
    private final String name;
    private final List<Double> values;
    private final String label;

    public Data(String name, List<Double> values, String label) {
        this.name = name;
        this.values = values;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public List<Double> getValues() {
        return values;
    }

    public double[] getValuesArray() {
        double[] a = new double[values.size()];
        int i=0;
        for (double d : values) {
            a[i++] = d;
        }
        return a;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", values=" + values +
                ", label='" + label + '\'' +
                '}';
    }
}