package net.iryndin.ml.rf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bootstrapping {

    public DataSet withReplacement(DataSet dataset, double percentage) {
        int totalSize = dataset.size();
        int sampleSize = 1+(int)Math.ceil(totalSize*percentage);
        List<DataPiece> list = new ArrayList<>(sampleSize);
        for (int i=0; i< sampleSize; i++) {
            int j = new Random().nextInt(totalSize);
            list.add(dataset.get(j));
        }

        return new DataSet(list);
    }
}
