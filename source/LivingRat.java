/**
 * An abstract class which models any rat that isn't a death rat.
 * @author James McWilliams
 */

public abstract class LivingRat {
    private boolean fertile;

    public void setFertile(boolean newFertility) {
        this.fertile = newFertility;
    }

    public boolean getFertile() {
        return this.fertile;
    }
}
