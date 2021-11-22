/**
 * An abstract class which models any rat that isn't a death rat.
 * @author James McWilliams
 */

public abstract class LivingRat extends Rat {
    private boolean isFertile;

    /**
     * LivingRat constructor.
     *
     * @param isInteractive can players place powers on.
     * @param isPassable    can rats walk through.
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     * @param isFertile     whether or not the rat can breed.
     */
    public LivingRat(boolean isInteractive, boolean isPassable, int speed, int direction,
                     int gasTimer, int xPos, int yPos, boolean isFertile) {
        super(isInteractive, isPassable, speed, direction, gasTimer, xPos, yPos);
        this.isFertile = isFertile;
    }

    public void infertilize() {
        this.isFertile = false;
    }

    public boolean getFertile() {
        return this.isFertile;
    }
}
