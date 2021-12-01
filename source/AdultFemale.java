/**
 * A class to model a female rat. Gives birth to baby rats.
 * @author James McWilliams
 */


public class AdultFemale extends LivingRat {
    private int pregnancyTime;
    private boolean pregnant;
    private int ratFetusCount;


    /**
     * AdultFemale constructor.
     *
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     * @param fertile       whether or not the rat can breed.
     * @param pregnancyTime how long the rat has left being pregnant.
     * @param ratFetusCount how many baby rats the mother rat is carrying
     */
    public AdultFemale(int speed, int direction,  int gasTimer, int xPos,
                       int yPos, boolean fertile, int pregnancyTime, int ratFetusCount) {
        super(speed, direction, gasTimer, xPos, yPos, fertile);
        this.pregnancyTime = pregnancyTime;
        this.pregnant = pregnancyTime > 0;
        this.ratFetusCount = ratFetusCount;
    }

    public int getRatFetusCount() {
        return ratFetusCount;
    }

    public void birth() {
        if (ratFetusCount > 0) {
            ratFetusCount--;
            boolean newIsFemale;
            if (Math.round(Math.random()) == 0) {
                newIsFemale = true;
            } else {
                newIsFemale = false;
            }

            ChildRat newBaby = new ChildRat(40, this.direction, 0,
                    this.xPos, this.yPos, true, 0, newIsFemale);
            LevelController.ratAdded(newBaby);
            LevelController.getTileAt(this.xPos,this.yPos).addOccupantRat(newBaby);

        }
    }
}
