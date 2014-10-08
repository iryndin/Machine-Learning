package net.iryndin.ml.rf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by iryndin on 08.10.14.
 */
public class Bootstrapping {

    public DataSet withReplacement(DataSet dataset, double percentage) {
        int dssize = dataset.size();
        int sampleSize = 1+(int)Math.ceil(dssize*percentage);
        List<DataPiece> list = new ArrayList<>(sampleSize);
        for (int i=0; i< sampleSize; i++) {
            int j = new Random().nextInt(dssize);
            list.add(dataset.get(j));
        }

        return new DataSet(list);
    }
}
