import java.util.ArrayList;

/**
 * A class that makes rat's sterile once they step on this Power.
 * @author Daumantas Balakauskas
 */

public class Sterilisation extends Power{

    private int ticksActive = 0;

    /**
     * Sterilisation constructor
     *
     * @param isInteractive can player place power on.
     * @param isPassable can rats walk through.
     */

    Sterilisation(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
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