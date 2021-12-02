import java.util.ArrayList;

/**
 * An abstract class to model a rat. Scurries around and dies when appropriate.
 * @author James McWilliams
 */

public abstract class Rat extends GameObject {
    protected int speed;
    Direction direction;
    protected int gasTimer;
    protected int xPos;
    protected int yPos;

    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    /**
     * Rat constructor.
     *
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     */
    Rat(int speed, Direction direction, int gasTimer, int xPos, int yPos) {
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

        switch(direction) {
            case NORTH:
                LevelController.getTileAt(xPos,yPos--).addOccupantRat(this);
                yPos--;
                break;
            case EAST:
                LevelController.getTileAt(xPos++,yPos).addOccupantRat(this);
                xPos++;
                break;
            case SOUTH:
                LevelController.getTileAt(xPos,yPos++).addOccupantRat(this);
                yPos++;
                break;
            case WEST:
                LevelController.getTileAt(xPos--,yPos).addOccupantRat(this);
                xPos--;
                break;
        }
        LevelController.getTileAt(xPos,yPos).removeOccupantRat(this);
    }

    /**
     * Swaps the rat's direction to the given value.
     */
    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public Direction findValidDirection() {
        // add valid dirs to a list using getForwardTile() and co.
        // pick one at random
        // if the list is empty, go backwards because you've hit a dead end
        return null;
    }



    public Tile getForwardTile() {
        Tile forwardTile;
        switch(direction) {
            case NORTH:
                forwardTile = LevelController.getTileAt(xPos,yPos--);
                break;
            case EAST:
                forwardTile = LevelController.getTileAt(xPos++,yPos);
                break;
            case SOUTH:
                forwardTile = LevelController.getTileAt(xPos,yPos++);
                break;
            default:
                forwardTile = LevelController.getTileAt(xPos--,yPos);
                break;

        }
        return forwardTile;
    }

    public Tile getLeftTile() {
        Tile leftTile;
        switch(direction) {
            case NORTH:
                leftTile = LevelController.getTileAt(xPos--,yPos);
                break;
            case EAST:
                leftTile = LevelController.getTileAt(xPos,yPos--);
                break;
            case SOUTH:
                leftTile = LevelController.getTileAt(xPos++,yPos);
                break;
            default:
                leftTile = LevelController.getTileAt(xPos,yPos++);
                break;
        }
        return leftTile;
    }

    public Tile getRightTile() {
        Tile rightTile;
        switch(direction) {
            case NORTH:
                rightTile = LevelController.getTileAt(xPos++,yPos);
                break;
            case EAST:
                rightTile = LevelController.getTileAt(xPos,yPos++);
                break;
            case SOUTH:
                rightTile = LevelController.getTileAt(xPos--,yPos);
                break;
            default:
                rightTile = LevelController.getTileAt(xPos,yPos--);
                break;

        }
        return rightTile;
    }

    public Tile getBackwardsTile() {
        Tile backwardsTile;
        switch(direction) {
            case NORTH:
                backwardsTile = LevelController.getTileAt(xPos,yPos++);
                break;
            case EAST:
                backwardsTile = LevelController.getTileAt(xPos--,yPos);
                break;
            case SOUTH:
                backwardsTile = LevelController.getTileAt(xPos,yPos--);
                break;
            default:
                backwardsTile = LevelController.getTileAt(xPos++,yPos);
                break;

        }
        return backwardsTile;
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
        // tell level controller that a rat has died
        LevelController.ratKilled(this);
        // remove rat from current tile
        LevelController.getTileAt(this.xPos,this.yPos).removeOccupantRat(this);
    }
}
