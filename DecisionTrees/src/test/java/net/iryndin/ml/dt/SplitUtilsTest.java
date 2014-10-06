package net.iryndin.ml.dt;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by iryndin on 12.09.14.
 */
public class SplitUtilsTest {

    @Test
    public void testDatasetSplit() throws IOException {
        DataSet dataset = readDataSet();
        Map<String, DataSet> map = SplitUtils.splitDataSet(dataset, "Location").getValueSplits();

        {
            DataSet ds = map.get("USA");
            assertEquals(4, ds.size());
            System.out.println(ds);
        }

        {
            DataSet ds = map.get("UK");
            assertEquals(5, ds.size());
            System.out.println(ds);
        }

        {
            DataSet ds = map.get("France");
            assertEquals(4, ds.size());
            System.out.println(ds);
        }

        {
            DataSet ds = map.get("New Zealand");
            assertEquals(2, ds.size());
            System.out.println(ds);
        }
    }

    @Test
    public void testDatasetBestSplit() throws IOException {
        DataSet dataset = readDataSet();
        System.out.println("initial dataset:");
        System.out.println(dataset);
        modifyDataSet(dataset);
        System.out.println("modified dataset:");
        System.out.println(dataset);

        SplitUtils.DatasetSplitInfo datasetSplitInfo = SplitUtils.findBestSplit(dataset);

        assertEquals("Pages viewed", datasetSplitInfo.getBestSplit().getSplitColumnName());

        System.out.println(datasetSplitInfo.getInfGainMap());
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
