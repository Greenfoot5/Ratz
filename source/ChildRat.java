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
    private Sex sex;

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
     * @param sex       whether the rat is female, male, or intersex.
     */
    ChildRat(int speed, Direction direction, int gasTimer, int xPos,
             int yPos, boolean isFertile, int age, Sex sex) {
        super(speed, direction, gasTimer, xPos, yPos, isFertile);
        this.age = age;
        this.sex = sex;
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

    public Sex getRatSex() {
        return sex;
    }

    public void setRatSex(Sex newSex) {
        sex = newSex;
    }

    /**
     * Replaces this rat with an equivalent adult rat.
     */
    public void growUp() {
        if (sex == Sex.FEMALE) {
            // make a female rat
            AdultFemale newAdult = new AdultFemale(Rat.getDEFAULT_SPEED(),
                    direction, gasTimer, xPos, yPos, isFertile, 0, 0);
            Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).
                    addOccupantRat(newAdult);
            LevelController.ratAdded(newAdult);
        }

        if (sex == Sex.MALE) {
            // make a male rat
            AdultMale newAdult = new AdultMale(Rat.getDEFAULT_SPEED(),
                    direction, gasTimer, xPos, yPos, isFertile);
            Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).
                    addOccupantRat(newAdult);
            LevelController.ratAdded(newAdult);
        }

        if (sex == Sex.INTERSEX) {
            // make an intersex rat
            AdultIntersex newAdult = new AdultIntersex(Rat.getDEFAULT_SPEED(),
                    direction, gasTimer, xPos, yPos, isFertile, 0, 0);
            Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).
                    addOccupantRat(newAdult);
            LevelController.ratAdded(newAdult);
        }
    }

    /**
     * Gets the age of the rat
     *
     * @return The age of the child
     */
    public int getAge() {
        return age;
    }
}
