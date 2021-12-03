import java.util.ArrayList;

/**
 * A class that makes rat's sterile once they step on this Power.
 * @author Daumantas Balakauskas
 */

public class Sterilisation extends Power{

    private int ticksActive = 0; //Tick counter since creation of this class.

    /**
     * Sterilisation constructor
     *
     * @param xPos x coordinate
     * @param yPos y coordinate
     */

    Sterilisation(int xPos, int yPos) {
        super(true, xPos, yPos);
    }

    /**
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile used to remove the power from Tile.
     */
    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        for (Rat rat : rats) {
            LivingRat lr = (LivingRat) rat;
            lr.infertilize();
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

    /**
     * Getter for fileReader
     */

    public int getTicksActive() {
        return ticksActive;
    }
}