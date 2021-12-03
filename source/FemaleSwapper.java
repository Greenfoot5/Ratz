import java.util.ArrayList;

/**
 * A class that swaps male rat to female if it steps on this Power.
 * @author Daumantas Balakauskas
 */

public class FemaleSwapper extends Power {

    /**
     * FemaleSwapper constructor
     */

    FemaleSwapper(int xPos, int yPos) {
        super(true, xPos, yPos);
    }



    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        for (Rat r : rats) {
            if(r instanceof AdultMale) {
                /* Will not work until AdultMale&AdultFemale getters are set up.

                AdultFemale copyRat = new AdultFemale(true,false,
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
