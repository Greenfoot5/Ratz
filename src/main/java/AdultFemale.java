import java.util.Objects;

/**
 * A class to model a female rat. Gives birth to baby rats.
 *
 * @author James McWilliams
 * @version 1.0
 */
public class AdultFemale extends LivingRat {
    private static final int DEFAULT_PREGNANCY_TIME = 40;
    private static final int RAT_BABY_MULTIPLIER = 4;

    private int pregnancyTime;
    private boolean pregnant;
    private int ratFetusCount;

    /**
     * Creates a new Female Rat
     *
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x-axis.
     * @param yPos          where the rat is on the y-axis.
     * @param isFertile     whether the rat can breed.
     * @param pregnancyTime how long the rat has left being pregnant.
     * @param ratFetusCount how many baby rats the mother rat is carrying
     */
    public AdultFemale(int speed, Direction direction, int gasTimer, int xPos,
                       int yPos, boolean isFertile, int pregnancyTime,
                       int ratFetusCount) {
        super(speed, direction, gasTimer, xPos, yPos, isFertile);
        this.pregnancyTime = pregnancyTime;
        this.pregnant = pregnancyTime > 0;
        this.ratFetusCount = ratFetusCount;
    }

    /**
     * Gets the current fetus count of the rat
     *
     * @return the current value of ratFetusCount
     */
    public int getRatFetusCount() {
        return ratFetusCount;
    }

    /**
     * A list of things the rat needs to do every tick.
     */
    @Override
    protected void onTickSpecific() {
        pregnancyTime--;
        if (pregnancyTime <= 0) {
            pregnant = false;
            birth();
        }
    }

    /**
     * Makes the rat pregnant. Rats will have 2d4 babies.
     */
    public void becomePregnant() {
        if (!pregnant) {
            pregnant = true;
            pregnancyTime = DEFAULT_PREGNANCY_TIME;
            ratFetusCount = (int) (Math.ceil(Math.random() *
                    RAT_BABY_MULTIPLIER) + Math.ceil(Math.random() *
                    RAT_BABY_MULTIPLIER));
        }
    }

    /**
     * Gets the length of the pregnancy
     *
     * @return pregnancyTime
     */
    public int getPregnancyTime() {
        return pregnancyTime;
    }

    /**
     * Creates a new baby rat at the mother's position.
     */
    public void birth() {
        if (ratFetusCount > 0) {
            ratFetusCount--;
            Sex newSex;
            if (Math.round(Math.random()) == 0) {
                newSex = Sex.MALE;
            } else {
                newSex = Sex.FEMALE;
            }

            if (Math.round(Math.random() * 10) == 0) {
                newSex = Sex.INTERSEX;
            }

            ChildRat newBaby = new ChildRat(Rat.getDEFAULT_SPEED() / 2,
                    direction, 0, xPos, yPos, true, 0, newSex);
            LevelController.ratAdded(newBaby);
            Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).
                    addOccupantRat(newBaby);
        }
    }
}
