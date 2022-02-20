/**
 * An abstract class which models any rat that isn't a death rat.
 *
 * @author James McWilliams
 * @version 1.0
 */

public abstract class LivingRat extends Rat {
    protected boolean isFertile;

    /**
     * LivingRat constructor.
     *
     * @param speed     how fast the rat moves.
     * @param direction the direction the rat is facing.
     * @param gasTimer  how long the rat has spent inside poison gas.
     * @param xPos      where the rat is on the x-axis.
     * @param yPos      where the rat is on the y-axis.
     * @param isFertile whether the rat can breed.
     */
    public LivingRat(int speed, Direction direction, int gasTimer, int xPos,
                     int yPos, boolean isFertile) {
        super(speed, direction, gasTimer, xPos, yPos);
        this.isFertile = isFertile;
    }

    /**
     * Makes a rat infertile.
     */
    public void infertilize() {
        this.isFertile = false;
    }

    /**
     * Gets if a rat is fertile
     * @return the current value of isFertile
     */
    public boolean getFertile() {
        return this.isFertile;
    }
}
