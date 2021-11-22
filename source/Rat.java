/**
 * An abstract class to model a rat. Scurries around and dies when appropriate.
 * @author James McWilliams
 */

public abstract class Rat extends GameObject {
    private int speed;
    private int direction; // TODO: swap this to an enum
    private int gasTimer;
    private int xPos;
    private int yPos;

    /**
     * Rat constructor.
     *
     * @param isInteractive can players place powers on.
     * @param isPassable    can rats walk through.
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     */
    Rat(boolean isInteractive, boolean isPassable, int speed, int direction, int gasTimer, int xPos, int yPos) {
        super(isInteractive, isPassable);
        this.speed = speed;
        this.direction = direction;
        this.gasTimer = gasTimer;
        this.xPos = xPos;
        this.yPos = yPos;
    }


    public void walk() {
        // walk in your direction
    }

    public void changeDirection(){
        // swap your direction
    }

    public void incGasTimer(){
        // increment gasTimer
    }

    public void die() {
        // cease existing. lucky.
    }
}
