package net.iryndin.ml.dt;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by iryndin on 18.09.14.
 */
public class ID3DecisionTreeBuilderTest {

    @Test
    public void test() throws IOException {
        DataSet dataset = readDataSet();
        modifyDataSet(dataset);
        ID3DecisionTreeBuilder builder = new ID3DecisionTreeBuilder(2);
        Classifier classifier = builder.buildTree(dataset);
        //System.out.println(root);
    }

    private DataSet readDataSet() throws IOException {
        final String filename = "data/user_behavior.csv";
        DataSetFileReader dsfr = new DataSetFileReader(",", "Service chosen");
        return dsfr.read(filename);
    }

    private void modifyDataSet(DataSet dataset) {
        for (DataPiece dp : dataset) {
            int pv = Integer.parseInt(dp.getValue("Pages viewed"));
            String pvNew = "lt20";
            if (pv >= 20) {
                pvNew = "gte20";
            }
            dp.setValue("Pages viewed", pvNew);
        }
    }
}
