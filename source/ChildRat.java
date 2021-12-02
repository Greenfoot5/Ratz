/**
 * Models a baby rat. It's very cute, and wants to be a real rat one day.
 * @author James McWilliams
 */

public class ChildRat extends LivingRat {
    private int age;
    private boolean isFemale;

    /**
     * ChildRat constructor.
     *  @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     * @param isFertile       whether or not the rat can breed.
     * @param age           how old the rat is (in frames? figure out later lol)
     * @param isFemale        whether or not the rat is female.
     */
    ChildRat(int speed, Direction direction, int gasTimer, int xPos,
             int yPos, boolean isFertile, int age, boolean isFemale) {
        super(speed, direction, gasTimer, xPos, yPos, isFertile);
        this.age = age;
        this.isFemale = isFemale;
    }

    public void setFemale(boolean isFemale) {
        this.isFemale = isFemale;
    }

    public boolean getIsFemale() {
        return isFemale;
    }

    public void growUp() {
        // call AdultMale or AdultFemale constructor as appropriate
    }


}
