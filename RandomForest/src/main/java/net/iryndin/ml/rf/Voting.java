package net.iryndin.ml.rf;

import java.util.HashMap;
import java.util.Map;

public class Voting {
    private final Map<String, Integer> map = new HashMap<>();

    public void add(String s) {
        Integer count = map.get(s);
        if (count == null) {
            count = 0;
        }
        count++;
        map.put(s, count);
    }

    public ClassificationResult getMajority() {
        String result = null;
        int max = -1;
        int total = 0;
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                result = e.getKey();
            }
            total += e.getValue();
        }
        return new ClassificationResult(result, max / (double)total);
    }
}
