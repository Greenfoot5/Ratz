import java.util.ArrayList;

/**
 * A class that swaps male rat to female if it steps on this Power.
 * @author Daumantas Balakauskas
 */

public class FemaleSwapper extends Power {

    /** FemaleSwapper constructor
     *
     * @param xPos x coordinate
     * @param yPos y coordinate
     */

    FemaleSwapper(int xPos, int yPos) {
        super(true, xPos, yPos);
    }


    /**
     * @param rats        used to interact with all rats that stepped on the power.
     * @param currentTile used to access Tile of the Power.
     */
    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        for (Rat r : rats) {
            if(r instanceof AdultMale) {
                AdultFemale copyRat = new AdultFemale(r.getSpeed(), r.getDirection(), r.getGasTimer(),
                        this.xPos, this.yPos, ((AdultMale) r).isFertile, 0
                        , 0);

                currentTile.removeActivePower(this);
                currentTile.addOccupantRat(copyRat);
                currentTile.removeOccupantRat(r);
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
