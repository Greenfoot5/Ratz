import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    List<Direction> dirsList = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

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
     * A list of things the rat needs to do every tick.
     */
    public void onTick() {
        // 12 ticks in gas = 2.5 seconds. increase or decrease later as appropriate
        if (gasTimer > 12) {
            die();
        }
        walk();
        decGasTimer();
        onTickSpecific();
    }

    /**
     * Does things that only certain subclasses of Rat need to do.
     */
    protected void onTickSpecific(){

    }



    /**
     * Causes the rat to walk in a direction. If multiple directions are valid, it will choose a random one.
     * The rat will only go backwards if no other directions are valid.
     */
    public void walk() {
        direction = setNewDirection();
        if (direction != null){
            getForwardTile().addOccupantRat(this);
            LevelController.getTileAt(xPos,yPos).removeOccupantRat(this);
        }

    }

    /**
     * Swaps the rat's direction to the given value.
     */
    public void setDirection(Direction direction){
        this.direction = direction;
    }


    public Direction turnRight() {
        return dirsList.get((dirsList.indexOf(direction) + 1) % 4);
    }

    public Direction turnLeft() {
        return dirsList.get((dirsList.indexOf(direction) - 1) % 4);
    }

    public Direction turnBack() {
        return dirsList.get((dirsList.indexOf(direction) + 2) % 4);
    }


    public Direction setNewDirection() {
        ArrayList<Direction> validDirections = new ArrayList<Direction>();
        if (getForwardTile() instanceof Path || getForwardTile() instanceof Tunnel) {
            validDirections.add(direction);
        }
        if (getRightTile() instanceof Path || getRightTile() instanceof Tunnel) {
            validDirections.add(turnRight());
        }
        if (getLeftTile() instanceof Path || getLeftTile() instanceof Tunnel) {
            validDirections.add(turnLeft());
        }

        Direction chosenDirection;

        if (validDirections.size() == 0){
            // forward, right, and left aren't options. Try going back.
            if (getRearTile() instanceof Path || getRearTile() instanceof Tunnel) {
                chosenDirection = turnBack();
            } else {
                // the only time we should get to the point is if the rat is stuck in a box.
                // this is pretty cruel to the rat.
                return null;
            }
        } else {
            // select a random item from validDirections
            chosenDirection = validDirections.get((int) Math.ceil(Math.random() * validDirections.size()));
        }

        return chosenDirection;
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

    public Tile getRearTile() {
        Tile rearTile;
        switch(direction) {
            case NORTH:
                rearTile = LevelController.getTileAt(xPos,yPos++);
                break;
            case EAST:
                rearTile = LevelController.getTileAt(xPos--,yPos);
                break;
            case SOUTH:
                rearTile = LevelController.getTileAt(xPos,yPos--);
                break;
            default:
                rearTile = LevelController.getTileAt(xPos++,yPos);
                break;

        }
        return rearTile;
    }

    /**
     * Adds 2 to the rat's record of how long it's been in toxic gas.
     */
    public void incGasTimer(){
        this.gasTimer += 2;
    }

    /**
     * Subtracts 1 from the rat's record of how long it's been in toxic gas.
     */
    public void decGasTimer(){
        this.gasTimer--;
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
