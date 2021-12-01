/**
 * A class to model a male rat. Gets female rats pregnant.
 * @author James McWilliams
 */

public class AdultMale extends LivingRat {


    /**
     * AdultMale constructor.
     *
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     * @param fertile       whether or not the rat can breed.
     */
    public AdultMale(int speed, int direction, int gasTimer, int xPos,
                     int yPos, boolean fertile) {
        super(speed, direction, gasTimer, xPos, yPos, fertile);
    }

    public void ratSexFunction() {
        if (this.isFertile) {
            Tile currentTile = LevelController.getTileAt(this.xPos,this.yPos);

            for (int i = 0; i <= currentTile.getOccupantRats().size(); i++) {
                Rat currentRat = currentTile.getOccupantRats().get(i);

                if (currentRat instanceof LivingRat) {
                    if ((LivingRat) currentRat instanceof AdultFemale) {
                        ((AdultFemale) currentRat).becomePregnant();
                    }
                }

            }
        }
    }


}
