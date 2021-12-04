import java.io.*;
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
    private static Power[] powers;
    private static Rat[] ratSpawns;
    private static int maxRats;
    private static int parTime;
    private static int[] dropRates = new int[8];
    private static ArrayList<Tile> tileArrayList = new ArrayList<>();
    private static ArrayList<Power> powerArrayList = new ArrayList<>();
    private static ArrayList<Rat> ratArrayList = new ArrayList<>();
    


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

    public static Power[] getPowers() {
        powers = powerArrayList.toArray(new Power[0]);
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

    public static void saveLevel(String levelName, int maxRats, int parTime, int[] dropRates,
                          String[] tiles, Rat[] ratSpawns, Power[] powers) throws IOException {

        // saving first two lines
        FileWriter writer = new FileWriter(levelName + ".txt");
        String dropRatesString = "";
        for (int i = 0; i < dropRates.length; i++) {
            dropRatesString += dropRates[i] + ",";
        }

        // saving tiles
        String tileString = "";
        for (int i = 0; i < tiles.length; i++) {
            tileString += tiles[i] + "\n";
        }

        // more shit goes here

        String fileString = String.format("%d,%d,%d,%d\n%s\n%s",height,width,maxRats,parTime,dropRatesString,tileString);


    }

    public static String ratToStr(Rat rat) {
        String type;
        String speed;
        String direction;
        String gasTimer;
        String xPos;
        String yPos;
        // the following items can't be named because they vary from rat to rat
        String item6;
        String item7;
        String item8;

        speed = Integer.toString(rat.getSpeed());
        direction = Integer.toString(directionEnumToInt(rat.getDirection()));
        gasTimer = Integer.toString(rat.getGasTimer());
        xPos = Integer.toString(rat.getxPos());
        yPos = Integer.toString(rat.getyPos());

        if (rat instanceof ChildRat) {
            if (((ChildRat) rat).isFemale()) {
                type = "f";

            } else {
                type = "m";
            }
            if (((ChildRat) rat).getFertile()) {
                item6 = "1";
            } else {
                item6 = "0";
            }
            item7 = Integer.toString(((ChildRat) rat).getAge());

            return type + speed + direction + gasTimer + xPos + yPos + item6 + item7;
        }

        if (rat instanceof AdultFemale) {
            type = "F";
            if (((AdultFemale) rat).getFertile()) {
                item6 = "1";
            } else {
                item6 = "0";
            }
            item7 = Integer.toString(((AdultFemale) rat).getPregnancyTime());
            item8 = Integer.toString(((AdultFemale) rat).getRatFetusCount());

            return type + speed + direction + gasTimer + xPos + yPos + item6 + item7 + item8;
        }

        if (rat instanceof AdultMale) {
            type = "M";

            if (((AdultMale) rat).getFertile()) {
                item6 = "1";
            } else {
                item6 = "0";
            }

            return type + speed + direction + gasTimer + xPos + yPos + item6;

        }

        if (rat instanceof DeathRat) {
            type = "D";

            item6 = Integer.toString(((DeathRat) rat).getKillCounter());

            return type + speed + direction + gasTimer + xPos + yPos + item6;
        }

        // if nothing has been returned yet, someone's added in some kind of new rat.
        return null;
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

        // get drop rate data
        String[] dropRatesString = reader.nextLine().split(",");
        for (int i=0; i < dropRates.length; i++) {
            dropRates[i] = Integer.parseInt(dropRatesString[i]);
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


        /*
         rat and power strings are divided by commas.
         each string between the commas is an argument for the constructor.
        */

        while (reader.hasNextLine()) {
            String[] currentItem = reader.nextLine().replaceAll("[()]", "").split(",");
            // if current item is a female baby rat
            if (currentItem[0].equals("f")) {
                int speed = Integer.parseInt(currentItem[1]);
                int directionInt = Integer.parseInt(currentItem[2]);
                Rat.Direction direction = directionIntToEnum(directionInt);
                int gasTimer = Integer.parseInt(currentItem[3]);
                int xPos = Integer.parseInt(currentItem[4]);
                int yPos = Integer.parseInt(currentItem[5]);
                boolean isFertile;
                if (currentItem[6].equals("1")) {
                    isFertile = true;
                } else {
                    isFertile = false;
                }
                int age = Integer.parseInt(currentItem[7]);
                ChildRat newRat = new ChildRat(speed, direction, gasTimer, xPos, yPos, isFertile, age, true);
                ratArrayList.add(newRat);
            }

            // if current item is a male baby rat
            if (currentItem[0].equals("m")) {
                int speed = Integer.parseInt(currentItem[1]);
                int directionInt = Integer.parseInt(currentItem[2]);
                Rat.Direction direction = directionIntToEnum(directionInt);
                int gasTimer = Integer.parseInt(currentItem[3]);
                int xPos = Integer.parseInt(currentItem[4]);
                int yPos = Integer.parseInt(currentItem[5]);
                boolean isFertile;
                if (currentItem[6].equals("1")) {
                    isFertile = true;
                } else {
                    isFertile = false;
                }
                int age = Integer.parseInt(currentItem[7]);
                ChildRat newRat = new ChildRat(speed, direction, gasTimer, xPos, yPos, isFertile, age, false);
                ratArrayList.add(newRat);
            }

            // if current item is a female adult rat
            if (currentItem[0].equals("F")) {
                int speed = Integer.parseInt(currentItem[1]);
                int directionInt = Integer.parseInt(currentItem[2]);
                Rat.Direction direction = directionIntToEnum(directionInt);
                int gasTimer = Integer.parseInt(currentItem[3]);
                int xPos = Integer.parseInt(currentItem[4]);
                int yPos = Integer.parseInt(currentItem[5]);
                boolean isFertile;
                if (currentItem[6].equals("1")) {
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
            if (currentItem[0].equals("M")) {
                int speed = Integer.parseInt(currentItem[1]);
                int directionInt = Integer.parseInt(currentItem[2]);
                Rat.Direction direction = directionIntToEnum(directionInt);
                int gasTimer = Integer.parseInt(currentItem[3]);
                int xPos = Integer.parseInt(currentItem[4]);
                int yPos = Integer.parseInt(currentItem[5]);
                boolean isFertile;
                if (currentItem[6].equals("1")) {
                    isFertile = true;
                } else {
                    isFertile = false;
                }
                AdultMale newRat = new AdultMale(speed, direction, gasTimer, xPos, yPos, isFertile);
                ratArrayList.add(newRat);
            }

            // if current item is a death rat
            if (currentItem[0].equals("D")) {
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
            if (currentItem[0].equals("B")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                int ticksActive = Integer.parseInt(currentItem[3]);
                Bomb newBomb = new Bomb(xPos,yPos);
                newBomb.setTicksActive(ticksActive);
                powerArrayList.add(newBomb);
            }

            // if currentItem item is gas
            if (currentItem[0].equals("G")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                boolean isOriginal;
                if (currentItem[3].equals("1")) {
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
            if (currentItem[0].equals("S")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                int ticksActive = Integer.parseInt(currentItem[3]);
                Sterilisation newSterilisation = new Sterilisation(xPos, yPos);
                newSterilisation.setTicksActive(ticksActive);
                powerArrayList.add(newSterilisation);
            }

            // if currentItem item is poison
            if (currentItem[0].equals("P")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                Poison newPoison = new Poison(xPos,yPos);
                powerArrayList.add(newPoison);
            }

            // if currentItem item is a male sex change
            if (currentItem[0].equals("T")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                MaleSwapper newMaleSwapper = new MaleSwapper(xPos, yPos);
                powerArrayList.add(newMaleSwapper);
            }

            // if currentItem item is a female sex change
            if (currentItem[0].equals("E")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                FemaleSwapper newFemaleSwapper = new FemaleSwapper(xPos, yPos);
                powerArrayList.add(newFemaleSwapper);
            }

            // if currentItem item is a no-entry sign (StopSign)
            if (currentItem[0].equals("N")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                int HP = Integer.parseInt(currentItem[3]);
                StopSign newStopSign = new StopSign(xPos, yPos);
                newStopSign.setHP(HP);
                powerArrayList.add(newStopSign);
            }
            
            for (Rat rat : ratArrayList) {
                //getTile(rat.getxPos(),rat.getyPos()).addOccupantRat(rat);
            }
        }
        reader.close();
    }

}