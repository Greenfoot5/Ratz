import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

/**
 * A class which reads levels from files, and saves levels to files.
 * @author James McWilliams
 */
public class LevelFileReader {
    private static int height;
    private static int width;
    private static String[] tiles;
    private static String[] ratSpawns;
    private static String[] powers;
    private static int maxRats;
    private static int parTime;
    private static int[] dropRates;


    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

    /**
     * `tiles` is a 2d array of strings with a length equal to the height of the level.
     * Each item in the array is a new row of tiles. They can be:
     * G for grass, P for path, and T for tunnel.
     * @return tiles
     */
    public static String[] getTiles() {
        return tiles;
    }

    /**
     * Returns a tile at the specified location. G for grass, P for path, T for tunnel.
     * @param x X coordinate of the tile.
     * @param y Y coordinate of the tile.
     * @return A char representing the type of tile in the given location.
     */
    public static char getTile(int x, int y) {
        return tiles[y].charAt(x);
    }

    public static String[] getRatSpawns() {
        return ratSpawns;
    }

    public static String[] getPowers() {
        return powers;
    }

    public static int getMaxRats() {
        return maxRats;
    }

    public static int getParTime() {
        return parTime;
    }

    /**
     * `dropRates` will contain exactly 8 ints, each representing the odds of a certain power being given to the player.
     * These powers are, in order:
     * Bombs, Gas, Sterilisation items, Poison, Male sex changes, Female sex changes, No-entry signs, and Death rats.
     * @return dropRates the chances that each power will drop.
     */
    public static int[] getDropRates() {
        return dropRates;
    }

    public void saveLevel(int size, String[] tiles, String[] ratSpawns, String[] powers,
                          int maxRats, int parTime, int[] dropRates){
        // save shit to a file
    }

    public void saveLevelState(int size, String[] tiles, String[] ratSpawns, String[] powers) {
        // save shit to a file.
    }

    public static void loadLevelFile(String filename) throws FileNotFoundException {
        File levelData = new File(filename);

        Scanner reader = new Scanner(levelData);

        // get level width and height
        if (reader.hasNextLine()) {
            String[] levelSize = reader.nextLine().split(",");
            width = Integer.parseInt(levelSize[0]);
            height = Integer.parseInt(levelSize[1]);
        }


        // get tile data
        String currentTiles = "";
        for (int i = 0; i < height; i++) {
            if (reader.hasNext()) {
                currentTiles = currentTiles.concat(reader.nextLine());
            }
        }

        // this ugly regex splits currentTiles based on the level's width
        tiles = currentTiles.split("(?<=\\G.{" + width + "})");
        reader.close();

    }

}