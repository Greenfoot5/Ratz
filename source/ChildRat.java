import java.util.Objects;

/**
 * Models a baby rat. It's very cute, and wants to be a real rat one day.
 *
 * @author James McWilliams
 * @version 1.0
 */

public class ChildRat extends LivingRat {
    private static final int GROWTH_AGE = 40;
    private int age;
    private boolean isFemale;

    /**
     * ChildRat constructor.
     *
     * @param speed     how fast the rat moves.
     * @param direction the direction the rat is facing.
     * @param gasTimer  how long the rat has spent inside poison gas.
     * @param xPos      where the rat is on the x-axis.
     * @param yPos      where the rat is on the y-axis.
     * @param isFertile whether the rat can breed.
     * @param age       how old the rat is
     * @param isFemale  whether the rat is female.
     */
    ChildRat(int speed, Direction direction, int gasTimer, int xPos,
             int yPos, boolean isFertile, int age, boolean isFemale) {
        super(speed, direction, gasTimer, xPos, yPos, isFertile);
        this.age = age;
        this.isFemale = isFemale;
    }

    /**
     * A list of things the rat needs to do every tick.
     */
    @Override
    protected void onTickSpecific() {
        age++;
        if (age >= GROWTH_AGE) {
            growUp();
        }
    }

    /**
     * Sets the rat's gender
     *
     * @param isFemale If the rat is female or not
     */
    public void setIsFemale(boolean isFemale) {
        this.isFemale = isFemale;
    }

    /**
     * Gets if the rat is female
     *
     * @return the current value of isFemale
     */
    public boolean isFemale() {
        return isFemale;
    }

    /**
     * Replaces this rat with an equivalent adult rat.
     */
    public void growUp() {
        if (isFemale) {
            AdultFemale newAdult = new AdultFemale(Rat.getDEFAULT_SPEED(),
                    direction, gasTimer, xPos, yPos, isFertile, 0, 0);
            Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).
                    addOccupantRat(newAdult);
            LevelController.ratAdded(newAdult);
        } else {
            AdultMale newAdult = new AdultMale(Rat.getDEFAULT_SPEED(),
                    direction, gasTimer, xPos, yPos, isFertile);
            Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).
                    addOccupantRat(newAdult);
            LevelController.ratAdded(newAdult);
        }
        Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).
                removeOccupantRat(this);
        LevelController.ratRemoved(this);
    }

    /**
     * Gets the age of the rat
     * @return The age of the child
     */
    public int getAge() {
        return age;
    }
}
