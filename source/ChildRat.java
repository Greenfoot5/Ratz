/**
 * Models a baby rat. It's very cute, and wants to be a real rat one day.
 * @author James McWilliams
 */

public class ChildRat extends LivingRat {
    private int age;
    private boolean sex;

    ChildRat(boolean isInteractive, boolean isPassable, int speed, int direction, int gasTimer,
             int xPos, int yPos, int age, boolean fertile) {
        super(isInteractive, isPassable, speed, direction, gasTimer, xPos, yPos, fertile);
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void growUp() {
        // call AdultMale or AdultFemale constructor as appropriate
    }


}
