import java.util.ArrayList;

/**
 * A class that swaps female rat to male if it steps on this Power.
 *
 * @author Daumantas Balakauskas
 */

public class MaleSwapper extends Power {

    private static final String MALE_PATH = "resources/maleSwapperSound.mp3";

    /**
     * MaleSwapper constructor
     *
     * @param xPos x coordinate
     * @param yPos y coordinate
     */

    MaleSwapper(int xPos, int yPos) {
        super(true, xPos, yPos);
    }

    /**
     * For MaleSwapper - Means creating a copy of the rat that stepped on
     * this power with the opposite gender and removing the original rat.
     *
     * @param rats        used to interact with all rats that stepped on the power.
     * @param currentTile is used to remove this power from Tile.
     */

    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        SeaShantySimulator seaSim = new SeaShantySimulator();
        seaSim.playAudioClip(MALE_PATH, 1.0);

        for (Rat r : rats) {
            if (r instanceof AdultFemale) {
                AdultMale copyRat = new AdultMale(r.getSpeed(), r.getDirection(), r.getGasTimer(),
                        this.xPos, this.yPos, ((AdultFemale) r).isFertile);

                currentTile.removeActivePower(this);
                currentTile.addOccupantRat(copyRat);
                currentTile.removeOccupantRat(r);

                LevelController.ratAdded(copyRat);
                LevelController.ratRemoved(r);
            } else {
                currentTile.removeActivePower(this);
            }
            if (r instanceof ChildRat) {
                ((ChildRat) r).setIsFemale(false);
            }
        }
    }

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     *
     * @param currentTile used for calling removeActivePower(this).
     * @param rats        used for updating the rat arraylist every game tick.
     *                    <p>
     *                    onTick not used for this Power.
     */

    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {

    }

}
