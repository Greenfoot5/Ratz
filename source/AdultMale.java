public class AdultMale extends LivingRat {


    /**
     * AdultMale constructor.
     *
     * @param isInteractive can players place powers on.
     * @param isPassable    can rats walk through.
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     * @param fertile       whether or not the rat can breed.
     */
    public AdultMale(boolean isInteractive, boolean isPassable, int speed, int direction,
                     int gasTimer, int xPos, int yPos, boolean fertile) {
        super(isInteractive, isPassable, speed, direction, gasTimer, xPos, yPos, fertile);
    }

    public void theRatSexFunction() {
        // do(ratsex);
    }


}
