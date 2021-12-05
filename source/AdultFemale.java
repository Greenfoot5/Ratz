/**
 * A class to model a female rat. Gives birth to baby rats.
 *
 * @author James McWilliams
 */


public class AdultFemale extends LivingRat {
    private int pregnancyTime;
    private boolean pregnant;
    private int ratFetusCount;
    private static final int DEFAULT_PREGNANCY_TIME = 40;
    private static final int RAT_BABY_MULTIPLIER = 4;


    /**
     * AdultFemale constructor.
     *
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     * @param isFertile     whether or not the rat can breed.
     * @param pregnancyTime how long the rat has left being pregnant.
     * @param ratFetusCount how many baby rats the mother rat is carrying
     */
    public AdultFemale(int speed, Direction direction, int gasTimer, int xPos,
                       int yPos, boolean isFertile, int pregnancyTime, int ratFetusCount) {
        super(speed, direction, gasTimer, xPos, yPos, isFertile);
        this.pregnancyTime = pregnancyTime;
        this.pregnant = pregnancyTime > 0;
        this.ratFetusCount = ratFetusCount;
    }

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
            ratFetusCount = (int) (Math.ceil(Math.random() * RAT_BABY_MULTIPLIER) + Math.ceil(Math.random() * RAT_BABY_MULTIPLIER));
        }
    }


    public int getPregnancyTime() {
        return pregnancyTime;
    }

    /**
     * Creates a new baby rat at the mother's position.
     */
    public void birth() {
        if (ratFetusCount > 0) {
            ratFetusCount--;
            boolean newIsFemale;
            if (Math.round(Math.random()) == 0) {
                newIsFemale = true;
            } else {
                newIsFemale = false;
            }

            ChildRat newBaby = new ChildRat(Rat.getDEFAULT_SPEED() * 2, direction, 0,
                    xPos, yPos, true, 0, newIsFemale);
            LevelController.ratAdded(newBaby);
            LevelController.getTileAt(xPos, yPos).addOccupantRat(newBaby);

        }
    }
}
