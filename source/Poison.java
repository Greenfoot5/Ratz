import java.util.ArrayList;

/**
 * A class that kills rats which stepped on this Power.
 * @author Daumantas Balakauskas
 */

public class Poison extends Power {

    /**
     * Poison constructor
     */

    Poison() {
        super(true);
    }

    /**M
     *
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile used to remove this Power from Tile.
     */
    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        for (Rat rat : rats) {
            rat.die();
        }
        currentTile.removeActivePower(this);
    }

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     * @param currentTile used for calling removeActivePower(this).
     * @param rats used for updating the rat arraylist every game tick.
     *
     * onTick not used for this Power.
     */

    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {

    }
}
