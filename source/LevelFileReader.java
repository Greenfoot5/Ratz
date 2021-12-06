import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * A class which reads levels from files, and saves levels to files.
 *
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
    private static Tile[][] tileMap;

    private static int inProgTimer;
    private static int[] inProgInv = new int[8];


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
     *
     * @return tiles
     */
    public static String[] getTiles() {
        return tiles;
    }

    /**
     * Returns a tile at the specified location. G for grass, P for path, T for tunnel.
     *
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
     * dropRates will contain exactly 8 ints, each representing the odds of a certain power being given to the player.
     * These powers are, in order:
     * Bombs, Gas, Sterilisation items, Poison, Male sex changes, Female sex changes, No-entry signs, and Death rats.
     *
     * @return dropRates the chances that each power will drop.
     */
    public static int[] getDropRates() {
        return dropRates;
    }

    public static int getInProgTimer() {
        return inProgTimer;
    }

    /**
     * Gets the items in a player's saved inventory.
     * The powers are in the following order:
     * Bombs, Gas, Sterilisation items, Poison, Male sex changes, Female sex changes, No-entry signs, and Death rats.
     *
     * @return
     */
    public static int[] getInProgInv() {
        return inProgInv;
    }

    /**
     * Writes a level to a .txt file in a format that can be read back by the file reader.
     *
     * @param levelName The name for the level. Formatted as "level-X-USER.txt" where X is a number and USER
     *                  is the profile's name.
     * @throws IOException if it can't find the file specified.
     */
    public static void saveLevel(String levelName) throws IOException {

        File saveFile = new File(levelName + "-inProgress-" + ProfileFileReader.getLoggedProfile() + ".txt");
        // if an in progress file doesn't exist yet
        if (!new File(levelName + "-inProgress-" + ProfileFileReader.getLoggedProfile() + ".txt").isFile()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error occurred creating save file.");
                e.printStackTrace();
            }
        }
        FileWriter writer = new FileWriter(saveFile);

        String inventory = "";

        for (int i = 0; i < LevelController.getCounters().length; i++) {
            inventory += LevelController.getCounters() + ",";
        }

        String allObjects = "";

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (LevelController.getTileAt(x,y).getOccupantRats().size() > 0) {
                    for (Rat rat : LevelController.getTileAt(x,y).getOccupantRats()) {
                        allObjects += "(" + ratToStr(rat) + ")\n";
                    }
                }
                if (LevelController.getTileAt(x,y).getActivePowers().size() > 0) {
                    for (Power power : LevelController.getTileAt(x,y).getActivePowers()) {
                        allObjects += "(" + powerToStr(power) + ")\n";
                    }
                }
            }
        }

        String fileString = String.format("%d\n%s\n%s\n", LevelController.getCurrentTimeLeft(),inventory,allObjects);
    }

    /**
     * Turns a rat into a string that can be pasted into a level file to be loaded later.
     *
     * @param rat The rat to convert
     * @return A string that can be read by the file reader
     */
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

            return type + "," + speed + "," + direction + "," + gasTimer + "," + xPos + "," + yPos + "," + item6 + "," + item7;
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

            return type + "," + speed + "," + direction + "," + gasTimer + "," + xPos + "," + yPos + "," + item6 + "," + item7 + "," + item8;
        }

        if (rat instanceof AdultMale) {
            type = "M";

            if (((AdultMale) rat).getFertile()) {
                item6 = "1";
            } else {
                item6 = "0";
            }

            return type + "," + speed + "," + direction + "," + gasTimer + "," + xPos + "," + yPos + "," + item6;

        }

        if (rat instanceof DeathRat) {
            type = "D";

            item6 = Integer.toString(((DeathRat) rat).getKillCounter());

            return type + "," + speed + "," + direction + "," + gasTimer + "," + xPos + "," + yPos + "," + item6;
        }

        // if nothing has been returned yet, someone's added in some kind of new rat.
        return null;
    }

    /**
     * Turns a power into a string that can be pasted into a level file to be loaded later.
     *
     * @param power The power to convert
     * @return A string that can be read by the file reader
     */
    public static String powerToStr(Power power) {
        String xPos;
        String yPos;
        String special;

        xPos = Integer.toString(power.getxPos());
        yPos = Integer.toString(power.getyPos());

        if (power instanceof Bomb) {
            special = String.valueOf(((Bomb) power).getTicksActive());
            return xPos + "," + yPos + "," + special;
        }
        if (power instanceof Gas) {
            special = String.valueOf(((Gas) power).getTicksActive());
            return xPos + "," + yPos + "," + special;
        }
        if (power instanceof Sterilisation) {
            special = String.valueOf(((Sterilisation) power).getTicksActive());
            return xPos + "," + yPos + "," + special;
        }
        if (power instanceof Poison) {
            return xPos + "," + yPos;
        }
        if (power instanceof MaleSwapper) {
            return xPos + "," + yPos;
        }
        if (power instanceof FemaleSwapper) {
            return xPos + "," + yPos;
        }
        if (power instanceof StopSign) {
            special = String.valueOf(((StopSign) power).getHP());
            return xPos + "," + yPos + "," + special;
        }

        // if nothing has been returned yet, someone's added in a new power.
        return null;
    }


    /**
     * Converts a direction stored as an int into a Direction enum.
     *
     * @param directionInt int from 0-3 representing north, east, south, west
     * @return Direction
     */
    private static Rat.Direction directionIntToEnum(int directionInt) {
        switch (directionInt) {
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
     * Converts a Direction enum to an int.
     *
     * @param directionEnum Direction
     * @return int from 0-3 representing north, east, south, west.
     */
    private static int directionEnumToInt(Rat.Direction directionEnum) {
        switch (directionEnum) {
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

    /**
     * Loads game objects from the text in level files.
     *
     * @param filename The file to open.
     * @throws FileNotFoundException if the file can't be found.
     */
    public static void loadLevelFile(String filename) throws FileNotFoundException {

        File levelDataInProgress = new File(filename + "-inProgress-" + ProfileFileReader.getLoggedProfile() + ".txt");
        File levelData = new File(filename + ".txt");
        Scanner reader = new Scanner(levelData);

        ratArrayList.clear();
        inProgInv = null;
        inProgTimer = 0;

        // get level width, height, max rats, par time
        if (reader.hasNextLine()) {
            String[] levelStats = reader.nextLine().split(",");
            width = Integer.parseInt(levelStats[0]);
            height = Integer.parseInt(levelStats[1]);
            maxRats = Integer.parseInt(levelStats[2]);
            parTime = Integer.parseInt(levelStats[3]);
        }

        // get drop rate data
        String[] dropRatesString = reader.nextLine().split(",");
        for (int i = 0; i < dropRates.length; i++) {
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

        tileMap = tilesToTileMap(tiles);

        // check if a saved level exists. if it does, grab the rats, timer, and stored inventory from it from it
        if (levelDataInProgress.isFile()) {
            reader = new Scanner(levelDataInProgress);
            inProgInv = new int[8];
            if (reader.hasNextLine()) {
                String[] savedInfo = reader.nextLine().split(",");
                inProgTimer = Integer.parseInt(savedInfo[0]);
                String[] inProgInvString = reader.nextLine().split(",");
                for (int i = 0; i < inProgInvString.length; i++) {
                    inProgInv[i] = Integer.parseInt(inProgInvString[i]);
                }
                readObjects(reader);


            }
        } else {
            // if no saved data exists, just read the objects from the default file.
            reader = new Scanner(levelData);
            readObjects(reader);
        }
        reader.close();
    }

    /**
     * Reads rats and powers from the level file.
     *
     * @param reader The scanner to read data from
     */
    private static void readObjects(Scanner reader) {
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
                tileMap[xPos][yPos].addOccupantRat(newRat);
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
                tileMap[xPos][yPos].addOccupantRat(newRat);
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
                tileMap[xPos][yPos].addOccupantRat(newRat);
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
                tileMap[xPos][yPos].addOccupantRat(newRat);
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
                tileMap[xPos][yPos].addOccupantRat(newRat);
            }

            // if currentItem item is a bomb
            if (currentItem[0].equals("B")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                int ticksActive = Integer.parseInt(currentItem[3]);
                Bomb newBomb = new Bomb(xPos, yPos);
                newBomb.setTicksActive(ticksActive);
                powerArrayList.add(newBomb);
                tileMap[xPos][yPos].addActivePower(newBomb);
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
                tileMap[xPos][yPos].addActivePower(newGas);
            }

            // if currentItem item is steriliser
            if (currentItem[0].equals("S")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                int ticksActive = Integer.parseInt(currentItem[3]);
                Sterilisation newSterilisation = new Sterilisation(xPos, yPos);
                newSterilisation.setTicksActive(ticksActive);
                powerArrayList.add(newSterilisation);
                tileMap[xPos][yPos].addActivePower(newSterilisation);
            }

            // if currentItem item is poison
            if (currentItem[0].equals("P")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                Poison newPoison = new Poison(xPos, yPos);
                powerArrayList.add(newPoison);
                tileMap[xPos][yPos].addActivePower(newPoison);
            }

            // if currentItem item is a male sex change
            if (currentItem[0].equals("T")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                MaleSwapper newMaleSwapper = new MaleSwapper(xPos, yPos);
                powerArrayList.add(newMaleSwapper);
                tileMap[xPos][yPos].addActivePower(newMaleSwapper);
            }

            // if currentItem item is a female sex change
            if (currentItem[0].equals("E")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                FemaleSwapper newFemaleSwapper = new FemaleSwapper(xPos, yPos);
                powerArrayList.add(newFemaleSwapper);
                tileMap[xPos][yPos].addActivePower(newFemaleSwapper);
            }

            // if currentItem item is a no-entry sign (StopSign)
            if (currentItem[0].equals("N")) {
                int xPos = Integer.parseInt(currentItem[1]);
                int yPos = Integer.parseInt(currentItem[2]);
                int HP = Integer.parseInt(currentItem[3]);
                StopSign newStopSign = new StopSign(xPos, yPos);
                newStopSign.setHP(HP);
                powerArrayList.add(newStopSign);
                tileMap[xPos][yPos].addActivePower(newStopSign);
            }

        }
    }


    /**
     * Converts the strings that store the tiles into a 2d array that can be used by other classes.
     *
     * @param tiles The
     * @return
     */
    private static Tile[][] tilesToTileMap(String[] tiles) {
        Tile[][] tileMap = new Tile[width][height];
        for (int i = 0; i < tileMap[0].length; i++) {
            int charPos = -1;
            for (int j = 0; j < tileMap.length; j++) {
                charPos++;
                switch (tiles[i].charAt(charPos)) {
                    case 'G':
                        tileMap[j][i] = new Grass();
                        break;
                    case 'P':
                        tileMap[j][i] = new Path();
                        break;
                    case 'T':
                        tileMap[j][i] = new Tunnel();
                        break;
                }
            }
        }
        return tileMap;
    }

    public static Tile[][] getTileMap() {
        return tileMap;
    }
}

