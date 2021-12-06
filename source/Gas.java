import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that deals damage to Rats that step over this Power.
 * @author Daumantas Balakauskas
 */

public class Gas extends Power {

    private int ticksActive = 0; //Tick counter since creation of this class.
    private boolean isOriginal;
    private static final String GAS_SOUND_PATH = "resources/gasSound.mp3";

    private int gasCounterN = 1; // Counts how many gas was placed North
    private int gasCounterS = 1; // Counts how many gas was placed South
    private int gasCounterE = 1; // Counts how many gas was placed East
    private int gasCounterW = 1; // Counts how many gas was placed West

    private int gasCounterNW = 1; // Counts how many gas was placed West
    private int gasCounterSW = 1; // Counts how many gas was placed West
    private int gasCounterNE = 1; // Counts how many gas was placed West
    private int gasCounterSE = 1; // Counts how many gas was placed West

    /**
     * Gas constructor
     */

    Gas(int xPos, int yPos, boolean isOriginal) {
        super(true, xPos, yPos);
        this.isOriginal = isOriginal;
    }

    /**
     * Getter for fileReader
     */

    public int getTicksActive() {
        return ticksActive;
    }

    /**
     * Getter for fileReader
     */

    public boolean isOriginal() {
        return isOriginal;
    }

    /**
     * Setter for fileReader
     */

    public void setTicksActive(int ticksActive) {
        this.ticksActive = ticksActive;
    }

    /**
     * Setter for fileReader
     */

    public void setOriginal(boolean original) {
        isOriginal = original;
    }

    /**
     * Abstract method to let any power know it's time to do something.
     * For Gas - Increase gasTimer of all rats that pass through this Power
     * and spawn new gas around (not on grass Tiles)
     *
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile used to remove this Power from Tile.
     */


    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {

        //Places a bunch of new Gas on Tiles with isOriginal = false;
        if (isOriginal) {
            if (ticksActive == 1 || ticksActive % 4 == 0) {
                SeaShantySimulator seaSim = new SeaShantySimulator();
                seaSim.playAudioClip(GAS_SOUND_PATH, 0.1);
                gasSurroundingPathTiles();
            }
        }

        //Where the rats get gassed.
        for(Rat r : currentTile.getOccupantRats()) {
            r.incGasTimer();
        }
    }

    /** Method that spawns new gas in Tiles around that aren't Grass
     * with isOriginal = false - that new Gas won't duplicate itself forever.
     */
    private void gasSurroundingPathTiles () {
        getSurroundingNonDiagonals();

        getSurroundDiagonals();
    }

    /**
     * Method that spawns new gas in Tiles around that aren't Grass,
     * Does this for North, South East and West
     */
    private void getSurroundingNonDiagonals()
    {
        // North
        if (LevelController.getTileAt(this.xPos, this.yPos + gasCounterN) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos, this.yPos + gasCounterN)).isPassable()) {
                Objects.requireNonNull(LevelController.getTileAt(this.xPos, this.yPos + gasCounterN)).addActivePower(new Gas(this.xPos,
                        this.yPos + gasCounterN, false));
                gasCounterN++;
            }
        }

        // South
        if (LevelController.getTileAt(this.xPos, this.yPos - gasCounterS) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos, this.yPos - gasCounterS)).isPassable()) {
                Objects.requireNonNull(LevelController.getTileAt(this.xPos, this.yPos - gasCounterS)).addActivePower(new Gas(this.xPos, this.yPos - gasCounterS, false));
                gasCounterS++;
            }
        }

        // East
        if (LevelController.getTileAt(this.xPos + gasCounterE, this.yPos) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos + gasCounterE, this.yPos)).isPassable()) {
                Objects.requireNonNull(LevelController.getTileAt(this.xPos + gasCounterE, this.yPos)).addActivePower(new Gas(this.xPos + gasCounterE, this.yPos, false));
                gasCounterE++;
            }
        }

        // South
        if (LevelController.getTileAt(this.xPos-gasCounterW, this.yPos) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos - gasCounterW, this.yPos)).isPassable()) {
                Objects.requireNonNull(LevelController.getTileAt(this.xPos - gasCounterW, this.yPos)).addActivePower(new Gas(this.xPos - gasCounterW,
                        this.yPos, false));
                gasCounterW++;
            }
        }
    }

    /**
     * Method that spawns new gas in Tiles around that aren't Grass,
     * Does this for North-East, South-East, South-West and North-West
     */
    private void getSurroundDiagonals()
    {
        // North East
        if (LevelController.getTileAt(this.xPos+gasCounterNE,
                this.yPos+gasCounterNE) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos + gasCounterNE, this.yPos + gasCounterNE)).isPassable()) {
                Objects.requireNonNull(LevelController.getTileAt(this.xPos + gasCounterNE, this.yPos + gasCounterNE)).addActivePower(new Gas(this.xPos + gasCounterNE, this.yPos + gasCounterNE, false));
                gasCounterNE++;
            }
        }

        // South East
        if (LevelController.getTileAt(this.xPos+gasCounterSE,
                this.yPos-gasCounterSE) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos + gasCounterSE,
                    this.yPos - gasCounterSE)).isPassable()) {
                Objects.requireNonNull(LevelController.getTileAt(this.xPos + gasCounterSE, this.yPos - gasCounterSE)).addActivePower(new Gas(this.xPos + gasCounterSE, this.yPos - gasCounterSE, false));
                gasCounterSE++;
            }
        }

        // North West
        if (LevelController.getTileAt(this.xPos - gasCounterNW,
                this.yPos + gasCounterNW) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos - gasCounterNW,
                    this.yPos + gasCounterNW)).isPassable()) {
                Objects.requireNonNull(LevelController.getTileAt(this.xPos - gasCounterNW, this.yPos + gasCounterNW)).addActivePower(new Gas(this.xPos - gasCounterNW, this.yPos + gasCounterNW, false));
                gasCounterNW++;
            }
        }

        // South West
        if (LevelController.getTileAt(this.xPos - gasCounterSW,
                this.yPos - gasCounterSW) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos - gasCounterSW,
                    this.yPos - gasCounterSW)).isPassable()) {
                Objects.requireNonNull(LevelController.getTileAt(this.xPos - gasCounterSW, this.yPos - gasCounterSW)).addActivePower(new Gas(this.xPos - gasCounterSW,
                        this.yPos - gasCounterSW, false));
                gasCounterSW++;
            }
        }
    }

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     * @param currentTile used for calling removeActivePower(this).
     * @param rats used for updating the rat arraylist every game tick.
     */

    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {
        ticksActive = ticksActive + 1;
        if (ticksActive <= 24) {
            activate(rats, currentTile);
        } else {
            currentTile.removeActivePower(this);
        }
    }
}