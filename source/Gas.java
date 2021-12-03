import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * A class that deals damage to Rats that step over this Power.
 * @author Daumantas Balakauskas
 */

public class Gas extends Power {

    private int ticksActive = 0; //Tick counter since creation of this class.
    private boolean isOriginal = false;

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
     * For Gas - Increase gasTimer of all rats that pass through this Power.
     *
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile used to remove this Power from Tile.
     */


    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {

        //Places a bunch of new Gas on Tiles with isOriginal = false;
        if (isOriginal) {
            gasSurroundingPathTiles();
        }

        //Where the rats get gassed.
        for(Rat r : currentTile.getOccupantRats()) {
            r.incGasTimer();
        }

        if (ticksActive >= 5) {
            currentTile.removeActivePower(this);
        }
    }

    /** Method that finds all Tiles Gas can reach and puts a new Gas there
     * with isOriginal = false.
     */

    private void gasSurroundingPathTiles () {
        int counter = 1;

        while(LevelController.getTileAt(this.xPos, this.yPos+counter).isPassable()) {
            int x = this.xPos;
            int y = this.yPos+counter;
            LevelController.getTileAt(x, y).addActivePower(new Gas(x,
                    y, false));
            counter++;
        }

        while(LevelController.getTileAt(this.xPos, this.yPos-counter).isPassable()) {
            int x = this.xPos;
            int y = this.yPos-counter;
            LevelController.getTileAt(x, y).addActivePower(new Gas(x,
                    y, false));
            counter++;
        }

        while(LevelController.getTileAt(this.xPos+counter, this.yPos).isPassable()) {
            int x = this.xPos+counter;
            int y = this.yPos;
            LevelController.getTileAt(x, y).addActivePower(new Gas(x,
                    y, false));
            counter++;
        }

        while(LevelController.getTileAt(this.xPos-counter, this.yPos).isPassable()) {
            int x = this.xPos-counter;
            int y = this.yPos;
            LevelController.getTileAt(x, y).addActivePower(new Gas(x,
                    y, false));
            counter++;
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
        if (ticksActive < 5) {
            activate(rats, currentTile);
        } else {
            currentTile.removeActivePower(this);
        }
    }
}