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
    private static Rat[] ratSpawns;
    private static int maxRats;
    private static int parTime;
    private static int[] dropRates = new int[8];
    private static ArrayList<Rat> ratArrayList = new ArrayList<>();
    private static ArrayList<Power> powerArrayList = new ArrayList<>();


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

    /**
     * Converts a direction stored as an int into a Direction enum.
     * @param directionInt int from 0-3 representing north, east, south, west
     * @return Direction
     */
    private static Rat.Direction directionIntToEnum(int directionInt){
        switch(directionInt) {
            case 0:
                return Rat.Direction.NORTH;
            case 1:
                return Rat.Direction.EAST;
            case 2:
                return Rat.Direction.SOUTH;
            default:
                return Rat.Direction.WEST;
        }
    }

    /**
     * Converts a Direction enum to an int
     * @param directionEnum Direction
     * @return int from 0-3 representing north, east, south, west
     */
    private static int directionEnumToInt(Rat.Direction directionEnum){
        switch(directionEnum) {
            case NORTH:
                return 0;
            case EAST:
                return 1;
            case SOUTH:
                return 2;
            default:
                return 3;
        }
    }

    public static void loadLevelFile(String filename) throws FileNotFoundException {
        File levelData = new File(filename);

        Scanner reader = new Scanner(levelData);

        // get level width and height
        if (reader.hasNextLine()) {
            String[] levelStats = reader.nextLine().split(",");
            width = Integer.parseInt(levelStats[0]);
            height = Integer.parseInt(levelStats[1]);
            maxRats = Integer.parseInt(levelStats[2]);
            parTime = Integer.parseInt(levelStats[3]);
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
         rat and power strings are divided by commas.
         each string between the commas is an argument for the constructor.
        */

        while (reader.nextLine().charAt(0) == '(') {
            String[] currentItem = reader.nextLine().replaceAll("[()]", "").split(",");
            // if current item is a female baby rat
            if (currentItem[0] == "f") {
                int speed = Integer.parseInt(currentItem[1]);
                int directionInt = Integer.parseInt(currentItem[2]);
                Rat.Direction direction = directionIntToEnum(directionInt);
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
                int directionInt = Integer.parseInt(currentItem[2]);
                Rat.Direction direction = directionIntToEnum(directionInt);
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
                int directionInt = Integer.parseInt(currentItem[2]);
                Rat.Direction direction = directionIntToEnum(directionInt);
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
                int ratFetusCount = Integer.parseInt(currentItem[8]);
                AdultFemale newRat = new AdultFemale(speed, direction, gasTimer, xPos, yPos, isFertile, pregnancyTimer, ratFetusCount);
                ratArrayList.add(newRat);
            }

            // if current item is a male adult rat
            if (currentItem[0] == "M") {
                int speed = Integer.parseInt(currentItem[1]);
                int directionInt = Integer.parseInt(currentItem[2]);
                Rat.Direction direction = directionIntToEnum(directionInt);
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

            // if current item is a death rat
            if (currentItem[0] == "D") {
                int speed = Integer.parseInt(currentItem[1]);
                int directionInt = Integer.parseInt(currentItem[2]);
                Rat.Direction direction = directionIntToEnum(directionInt);
                int gasTimer = Integer.parseInt(currentItem[3]);
                int xPos = Integer.parseInt(currentItem[4]);
                int yPos = Integer.parseInt(currentItem[5]);
                int killCounter = Integer.parseInt(currentItem[6]);
                DeathRat newRat = new DeathRat(speed, direction, gasTimer, xPos, yPos, killCounter);
                ratArrayList.add(newRat);
            }

            // if currentItem item is a bomb
            if (currentItem[0] == "B") {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                int ticksActive = Integer.parseInt(currentItem[3]);
                Bomb newBomb = new Bomb(xPos,yPos);
                newBomb.setTicksActive(ticksActive);
                powerArrayList.add(newBomb);
            }

            // if currentItem item is gas
            if (currentItem[0] == "G") {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                boolean isOriginal;
                if (currentItem[3] == "1") {
                    isOriginal = true;
                } else {
                    isOriginal = false;
                }
                int ticksActive = Integer.parseInt(currentItem[4]);
                Gas newGas = new Gas(xPos, yPos, isOriginal);
                newGas.setTicksActive(ticksActive);
                powerArrayList.add(newGas);
            }

            // if currentItem item is steriliser
            if (currentItem[0] == "S") {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                int ticksActive = Integer.parseInt(currentItem[3]);
                Sterilisation newSterilisation = new Sterilisation(xPos, yPos);
                newSterilisation.setTicksActive(ticksActive);
                powerArrayList.add(newSterilisation);
            }

            // if currentItem item is poison
            if (currentItem[0] == "P") {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                Poison newPoison = new Poison(xPos,yPos);
                powerArrayList.add(newPoison);
            }

            // if currentItem item is a male sex change
            if (currentItem[0] == "T") {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                MaleSwapper newMaleSwapper = new MaleSwapper(xPos, yPos);
                powerArrayList.add(newMaleSwapper);
            }

            // if currentItem item is a female sex change
            if (currentItem[0] == "E") {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                FemaleSwapper newFemaleSwapper = new FemaleSwapper(xPos, yPos);
                powerArrayList.add(newFemaleSwapper);
            }

            // if currentItem item is a no-entry sign (StopSign)
            if (currentItem[0] == "N") {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                int HP = Integer.parseInt(currentItem[3]);
                StopSign newStopSign = new StopSign(xPos, yPos);
                newStopSign.setHP(HP);
                powerArrayList.add(newStopSign);
            }
        }

        String[] dropRatesString = reader.nextLine().split(",");
        for (int i=0; i < dropRates.length; i++) {
            dropRates[i] = Integer.parseInt(dropRatesString[i]);
        }

    }

}