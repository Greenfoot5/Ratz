import java.util.ArrayList;

/**
 * A class that swaps female rat to male if it steps on this Power.
 * @author Daumantas Balakauskas
 */

public class MaleSwapper extends Power{

    /** MaleSwapper constructor
     *
     * @param isInteractive can player place power on.
     * @param isPassable can rats walk through.
     */

    MaleSwapper(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    /**
     * For MaleSwapper - Means creating a copy of the rat that stepped on
     * this power with the opposite gender and removing the original rat.
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile is used to remove this power from Tile.
     */

    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        for (Rat r : rats) {
            if(r instanceof AdultFemale) {
                /* Will not work until AdultMale&AdultFemale getters are set up.

                AdultMale copyRat = new AdultMale(true,false,
                        r.getSpeed(), r.getDirection(), r.getGasTimer(),
                        r.getX(), r.getY(), r.getFertile());

                currentTile.addOccupantRat(copyRat);
                currentTile.removeOccupantRat(r);
                */
                currentTile.removeActivePower(this);
            }
        }
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
