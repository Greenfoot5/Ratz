import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class which reads levels from files, and saves levels to files.
 * @author James McWilliams
 */
public class LevelFileReader {
    private static int height;
    private static int width;
    private static String[] tiles;
    private static String[] powers;
    private static int maxRats;
    private static int parTime;
    private static int[] dropRates;
    private static ArrayList<Rat> ratArrayList = new ArrayList<Rat>();
    private static Rat[] ratSpawns;

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

    public static Rat[] getRatSpawns() {
        ratSpawns = ratArrayList.toArray(new Rat[0]);
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

        /*
         rat strings are divided by commas.
         each string between the commas is an argument for the constructor.
         NOTE: the final item for a child rat is its age, whereas the final
         item for an adult rat is the pregnancy timer.
        */

        while (reader.nextLine().charAt(0) == '(') {
            String[] currentItem = reader.nextLine().replaceAll("[()]", "").split(",");
            // if current item is a female baby rat
            if (currentItem[0] == "f") {
                int speed = Integer.parseInt(currentItem[1]);
                int direction = Integer.parseInt(currentItem[2]);
                int gasTimer = Integer.parseInt(currentItem[3]);
                int xPos = Integer.parseInt(currentItem[4]);
                int yPos = Integer.parseInt(currentItem[5]);
                boolean isFertile;
                if (currentItem[6] == "1") {
                    isFertile = true;
                } else {
                    isFertile = false;
                }
                int age = Integer.parseInt(currentItem[7]);
                ChildRat newRat = new ChildRat(speed, direction, gasTimer, xPos, yPos, isFertile, age, true);
                ratArrayList.add(newRat);
            }

            // if current item is a male baby rat
            if (currentItem[0] == "m") {
                int speed = Integer.parseInt(currentItem[1]);
                int direction = Integer.parseInt(currentItem[2]);
                int gasTimer = Integer.parseInt(currentItem[3]);
                int xPos = Integer.parseInt(currentItem[4]);
                int yPos = Integer.parseInt(currentItem[5]);
                boolean isFertile;
                if (currentItem[6] == "1") {
                    isFertile = true;
                } else {
                    isFertile = false;
                }
                int age = Integer.parseInt(currentItem[7]);
                ChildRat newRat = new ChildRat(speed, direction, gasTimer, xPos, yPos, isFertile, age, false);
                ratArrayList.add(newRat);
            }

            // if current item is a female adult rat
            if (currentItem[0] == "F") {
                int speed = Integer.parseInt(currentItem[1]);
                int direction = Integer.parseInt(currentItem[2]);
                int gasTimer = Integer.parseInt(currentItem[3]);
                int xPos = Integer.parseInt(currentItem[4]);
                int yPos = Integer.parseInt(currentItem[5]);
                boolean isFertile;
                if (currentItem[6] == "1") {
                    isFertile = true;
                } else {
                    isFertile = false;
                }
                int pregnancyTimer = Integer.parseInt(currentItem[7]);
                AdultFemale newRat = new AdultFemale(speed, direction, gasTimer, xPos, yPos, isFertile, pregnancyTimer);
                ratArrayList.add(newRat);
            }

            // if current item is a male adult rat
            if (currentItem[0] == "M") {
                int speed = Integer.parseInt(currentItem[1]);
                int direction = Integer.parseInt(currentItem[2]);
                int gasTimer = Integer.parseInt(currentItem[3]);
                int xPos = Integer.parseInt(currentItem[4]);
                int yPos = Integer.parseInt(currentItem[5]);
                boolean isFertile;
                if (currentItem[6] == "1") {
                    isFertile = true;
                } else {
                    isFertile = false;
                }
                AdultMale newRat = new AdultMale(speed, direction, gasTimer, xPos, yPos, isFertile);
                ratArrayList.add(newRat);
            }
        }

    }

}