package net.iryndin.ml.rf;

import java.util.*;

public class MiscUtils {

    public static <T> Set<T> sample(Set<T> set, int n) {
        if (set.size() < n) {
            throw new IllegalArgumentException("n ("+n+") is greater than set size ("+set.size()+")");
        } else if (set.size() == n) {
            return set;
        } else {
            Set<T> result = new HashSet<>(n*2);
            List<T> list = new ArrayList<>(set);
            while (result.size() < n) {
                int i = new Random().nextInt(list.size());
                result.add(list.get(i));
            }
            return result;
        }
    }
}
