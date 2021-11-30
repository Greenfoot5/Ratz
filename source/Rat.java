/**
 * An abstract class to model a rat. Scurries around and dies when appropriate.
 * @author James McWilliams
 */

public abstract class Rat extends GameObject {
    protected int speed;
    protected int direction; // TODO: swap this to an enum
    protected int gasTimer;
    protected int xPos;
    protected int yPos;

    /**
     * Rat constructor.
     *
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     */
    Rat(int speed, int direction, int gasTimer, int xPos, int yPos) {
        super(true);
        this.speed = speed;
        this.direction = direction;
        this.gasTimer = gasTimer;
        this.xPos = xPos;
        this.yPos = yPos;
    }


    /**
     * Causes the rat to walk in a direction. If multiple directions are valid, it will choose a random one.
     * The rat will only go backwards if no other directions are valid.
     */
    public void walk() {
        // walk in your direction
    }

    /**
     * Swaps the rat's direction to the given value.
     * Note that 0, 1, 2, 3 equal north, east, south, and west respectively. (until I get around to making this an enum)
     */
    public void changeDirection(int direction){
        // swap your direction
    }

    /**
     * Adds 1 to the rat's record of how long it's been in toxic gas.
     */
    public void incGasTimer(){
        // increment gasTimer
    }

    /**
     * Causes the rat to die.
     */
    public void die() {
        // cease existing. lucky.
    }
}
