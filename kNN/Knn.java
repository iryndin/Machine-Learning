import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Override;
import java.util.*;

public class Knn {

    static int K = 3;

    static interface Distance {
        double distance(double[] a1, double[] a2);
    };

    static class EuclidDistance implements Distance {

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
    };

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            K = Integer.parseInt(args[0]);
        }
        List<Data> training = readData("data.txt");
        System.out.println(training);
        Data d = readLine("unknown,18,90,x");
        double[] da = d.getValuesArray();
        System.out.println("To be classified: " + d);
        Distance dist = new EuclidDistance();
        NavigableMap<Double, String> map = new TreeMap<>();
        for (Data i : training) {
            double[] ia = i.getValuesArray();
            double distance = dist.distance(ia,da);
            //System.out.println(d+" and " + i +": " + m);
            map.put(distance, i.getLabel());
        }
        int i = 0;
        Map<String, Integer> labelMap = new HashMap<>();
        for (Map.Entry<Double, String> e : map.entrySet()) {
            i++;
            if (i > K) {
                break;
            }
            String label = e.getValue();
            Integer count = labelMap.get(label);
            if (count == null) {
                count = 0;
            }
            count++;
            labelMap.put(label, count);
        }
        System.out.println(labelMap);
    }

    static List<Data> readData(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<Data> list = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.startsWith("#")) {
                    continue;
                }
                list.add(readLine(line));
            }
            return list;
        }
    }

    static Data readLine(String line) {
        String[] a = line.split(",");
        String name = a[0];
        String label = a[a.length-1];
        List<Double> nums = new ArrayList<>();
        for (int i=1; i<a.length-1; i++) {
            double n = Double.parseDouble(a[i]);
            nums.add(n);
        }
        return new Data(name, nums, label);
    }

}