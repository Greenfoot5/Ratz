import java.util.ArrayList;

/**
 * A class that swaps male rat to female if it steps on this Power.
 * @author Daumantas Balakauskas
 */

public class FemaleSwapper extends Power {

    private static final String FEMALE_SWAP_SOUND_PATH
            = "resources/femaleSwapperSound.mp3";
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

        SeaShantySimulator seaSim = new SeaShantySimulator();
        seaSim.playAudioClip(FEMALE_SWAP_SOUND_PATH, 0.1);

        for (Rat r : rats) {
            if(r instanceof AdultMale) {
                AdultFemale copyRat = new AdultFemale(r.getSpeed(), r.getDirection(), r.getGasTimer(),
                        this.xPos, this.yPos, ((AdultMale) r).isFertile, 0
                        , 0);

                currentTile.removeActivePower(this);
                currentTile.addOccupantRat(copyRat);
                currentTile.removeOccupantRat(r);

                LevelController.ratAdded(copyRat);
                LevelController.ratRemoved(r);
            } else {
                currentTile.removeActivePower(this);
            }
            if (r instanceof ChildRat) {
                ((ChildRat) r).setIsFemale(true);
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
