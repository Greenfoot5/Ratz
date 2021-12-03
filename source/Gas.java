import java.util.ArrayList;

/**
 * A class that deals damage to Rats that step over this Power.
 * @author Daumantas Balakauskas
 */

public class Gas extends Power {

    private int ticksActive = 0; //Tick counter since creation of this class.

    /**
     * Gas constructor
     */

    Gas(int xPos, int yPos) {
        super(true, xPos, yPos);
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
        for (Rat r : rats) {
            r.incGasTimer();
        }
        if (ticksActive >= 5) {
            currentTile.removeActivePower(this);
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