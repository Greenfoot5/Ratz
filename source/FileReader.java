import java.io.File;

public class FileReader {
    private static int size;
    private static String tiles;
    private static String ratSpawns;
    private static String powers;
    private static int maxRats;
    private static int parTime;
    private static int[] dropRates;

    public static int getSize() {
        return size;
    }

    public static String getTiles() {
        return tiles;
    }

    public static String getRatSpawns() {
        return ratSpawns;
    }

    public static String getPowers() {
        return powers;
    }

    public static int getMaxRats() {
        return maxRats;
    }

    public static int getParTime() {
        return parTime;
    }

    public static int[] getDropRates() {
        return dropRates;
    }

    public void saveLevel(int size, String tiles, String ratSpawns, String powers,
                          int maxRats, int parTime, int[] dropRates){
        // save shit to a file
    }

    public void saveLevelState(int size, String tiles, String ratSpawns, String powers) {
        // save shit to a file.
    }

}
