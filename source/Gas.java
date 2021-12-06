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

        if (LevelController.getTileAt(this.xPos, this.yPos + gasCounterN) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos, this.yPos + gasCounterN)).isPassable()) {
                    int x = this.xPos;
                    int y = this.yPos + gasCounterN;
                    Objects.requireNonNull(LevelController.getTileAt(x, y)).addActivePower(new Gas(x,
                            y, false));
                    gasCounterN++;
            }
        }

        if (LevelController.getTileAt(this.xPos, this.yPos - gasCounterS) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos, this.yPos - gasCounterS)).isPassable()) {
                    int x = this.xPos;
                    int y = this.yPos - gasCounterS;
                    Objects.requireNonNull(LevelController.getTileAt(x, y)).addActivePower(new Gas(x,
                            y, false));
                    gasCounterS++;
            }
        }

        if (LevelController.getTileAt(this.xPos + gasCounterE, this.yPos) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos + gasCounterE, this.yPos)).isPassable()) {
                    int x = this.xPos + gasCounterE;
                    int y = this.yPos;
                    Objects.requireNonNull(LevelController.getTileAt(x, y)).addActivePower(new Gas(x,
                            y, false));
                    gasCounterE++;
            }
        }

        if (LevelController.getTileAt(this.xPos-gasCounterW, this.yPos) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos - gasCounterW, this.yPos)).isPassable()) {
                    int x = this.xPos - gasCounterW;
                    int y = this.yPos;
                    Objects.requireNonNull(LevelController.getTileAt(x, y)).addActivePower(new Gas(x,
                            y, false));
                    gasCounterW++;
            }
        }

        //Diagonal spread
        if (LevelController.getTileAt(this.xPos+gasCounterNE,
                this.yPos+gasCounterNE) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos + gasCounterNE,
                    this.yPos + gasCounterNE)).isPassable()) {
                int x = this.xPos + gasCounterNE;
                int y = this.yPos + gasCounterNE;
                Objects.requireNonNull(LevelController.getTileAt(x, y)).addActivePower(new Gas(x,
                        y, false));
                gasCounterNE++;
            }
        }

        if (LevelController.getTileAt(this.xPos+gasCounterSE,
                this.yPos-gasCounterSE) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos + gasCounterSE,
                    this.yPos - gasCounterSE)).isPassable()) {
                int x = this.xPos + gasCounterSE;
                int y = this.yPos - gasCounterSE;
                Objects.requireNonNull(LevelController.getTileAt(x, y)).addActivePower(new Gas(x,
                        y, false));
                gasCounterSE++;
            }
        }

        if (LevelController.getTileAt(this.xPos - gasCounterNW,
                this.yPos + gasCounterNW) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos - gasCounterNW,
                    this.yPos + gasCounterNW)).isPassable()) {
                int x = this.xPos - gasCounterNW;
                int y = this.yPos + gasCounterNW;
                Objects.requireNonNull(LevelController.getTileAt(x, y)).addActivePower(new Gas(x,
                        y, false));
                gasCounterNW++;
            }
        }

        if (LevelController.getTileAt(this.xPos - gasCounterSW,
                this.yPos - gasCounterSW) != null) {
            if (Objects.requireNonNull(LevelController.getTileAt(this.xPos - gasCounterSW,
                    this.yPos - gasCounterSW)).isPassable()) {
                int x = this.xPos - gasCounterSW;
                int y = this.yPos - gasCounterSW;
                Objects.requireNonNull(LevelController.getTileAt(x, y)).addActivePower(new Gas(x,
                        y, false));
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