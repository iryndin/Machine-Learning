package net.iryndin.ml.dt;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DataSetFileReaderTest {

    @Test
    public void readColumnNamesTest() {
        String[] columnNames = {
                "referrer",
                "location",
                "read faq",
                "pages viewed",
                "service chosen"
        };
        final String separator = "\t";
        String line = join(columnNames, separator);
        Map<Integer, String> map = DataSetFileReader.readColumnNames(separator, line);
        for (int i = 0; i < columnNames.length; i++) {
            String columnName = map.get(i);
            assertEquals(columnName, columnNames[i]);
        }
    }

    @Test
    public void readDataSetTest1() throws IOException {
        final String filename = "data/user_behavior.csv";
        DataSetFileReader dsfr = new DataSetFileReader(",", "Service chosen");
        DataSet dataset = dsfr.read(filename);
        // test labels
        {
            Map<String, Integer> map = dataset.getLabels();
            assertEquals(6, (int) map.get("None"));
            assertEquals(6, (int) map.get("Basic"));
            assertEquals(3, (int) map.get("Premium"));
        }
        // test values of 'Referrer' column
        {
            Map<String, Integer> map = dataset.getColumnValues("Referrer");
            assertEquals(2, (int) map.get("Slashdot"));
            assertEquals(5, (int) map.get("Google"));
            assertEquals(3, (int) map.get("Digg"));
            assertEquals(3, (int) map.get("Kiwitobes"));
            assertEquals(2, (int) map.get("(direct)"));
        }
        // test values of 'Location' column
        {
            Map<String, Integer> map = dataset.getColumnValues("Location");
            assertEquals(4, (int) map.get("USA"));
            assertEquals(4, (int) map.get("France"));
            assertEquals(5, (int) map.get("UK"));
            assertEquals(2, (int) map.get("New Zealand"));
        }
        // test values of 'Read FAQ' column
        {
            Map<String, Integer> map = dataset.getColumnValues("Read FAQ");
            assertEquals(8, (int) map.get("Yes"));
            assertEquals(7, (int) map.get("No"));
        }
    }

    public static String join(String[] columnNames, String separator) {
        StringBuilder sb = new StringBuilder(columnNames.length * 255);
        for (int i = 0; i < columnNames.length - 1; i++) {
            sb.append(columnNames[i]).append(separator);
        }
        sb.append(columnNames[columnNames.length - 1]);
        return sb.toString();
    }
}
