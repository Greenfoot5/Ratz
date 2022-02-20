/**
 * A class to model a male rat. Gets female rats pregnant.
 *
 * @author James McWilliams
 * @version 1.0
 */

public class AdultMale extends LivingRat {


    /**
     * Creates a new Male Adult Rat
     *
     * @param speed     how fast the rat moves.
     * @param direction the direction the rat is facing.
     * @param gasTimer  how long the rat has spent inside poison gas.
     * @param xPos      where the rat is on the x-axis.
     * @param yPos      where the rat is on the y-axis.
     * @param isFertile whether the rat can breed.
     */
    public AdultMale(int speed, Direction direction, int gasTimer, int xPos,
                     int yPos, boolean isFertile) {
        super(speed, direction, gasTimer, xPos, yPos, isFertile);
    }

    /**
     * A list of things the rat needs to do every tick.
     */
    @Override
    protected void onTickSpecific() {
        ratSexFunction();
    }

    /**
     * Makes every female or intersex rat on the tile pregnant.
     */
    public void ratSexFunction() {
        if (this.isFertile) {
            Tile currentTile = LevelController.getTileAt(xPos, yPos);

            assert currentTile != null;
            for (Rat currentRat : currentTile.getOccupantRats()) {
                if (currentRat instanceof AdultFemale ) {
                    ((AdultFemale) currentRat).becomePregnant();
                } else if (currentRat instanceof  AdultIntersex ) {
                    ((AdultIntersex) currentRat).becomePregnant();
                }
            }
        }
    }
}
