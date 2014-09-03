package net.iryndin.ml.knn;

public class DataPieceLineReader {

    private final String separator;
    private final boolean hasName;
    private final LabelPlacementEnum labelPlacementEnum;

    public DataPieceLineReader(String separator, boolean hasName, LabelPlacementEnum labelPlacementEnum) {
        this.separator = separator;
        this.hasName = hasName;
        this.labelPlacementEnum = labelPlacementEnum;
    }

    public DataPiece read(String line) {
        if (line == null) {
            return null;
        }
        line = line.trim();
        if (line.isEmpty()) {
            return null;
        }
        if (line.startsWith("#")) {
            return null;
        }
        String[] a = line.split(separator);
        String name = null;
        int startIdx = 0;
        int endIdx = a.length;
        if (hasName) {
            name = a[0];
            startIdx = 1;
        }
        String label;
        switch (labelPlacementEnum) {
            case NOLABEL:
                label = null;
                endIdx = a.length;
                break;
            case FIRST_COLUMN:
                label = a[0];
                startIdx++;
                endIdx = a.length;
                break;
            case LAST_COLUMN:
                label = a[a.length-1];
                endIdx = a.length-1;
                break;
            default:
                throw new IllegalStateException("Unhandled value of labelPlacementEnum: "  + labelPlacementEnum);
        }
        double[] values = new double[endIdx - startIdx];
        for (int i=startIdx; i<endIdx; i++) {
            values[i-startIdx] = Double.parseDouble(a[i]);
        }
        return new DataPiece(name, values, label);
    }
}
