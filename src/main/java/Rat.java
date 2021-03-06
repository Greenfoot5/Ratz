import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * An abstract class to model a rat. Scurries around and dies when appropriate.
 *
 * @author James McWilliams
 */

public abstract class Rat extends GameObject {
    protected final int speed;
    protected Direction direction;
    protected int gasTimer;
    protected int xPos;
    protected int yPos;
    private int tickCounter;
    private static final int DEFAULT_SPEED = 4;
    private static final int RANDOM_START_DELAY = 6;
    private static final int GAS_DEATH_TIME = 6;
    private static final String DEATH_SOUND_PATH = "deathSound.mp3";

    /**
     * Directions the rat can face.
     */
    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    /**
     * Sexes a rat can be.
     */
    public enum Sex {
        MALE, FEMALE, INTERSEX
    }

    /**
     * Rat constructor.
     *
     * @param speed     how fast the rat moves.
     * @param direction the direction the rat is facing.
     * @param gasTimer  how long the rat has spent inside poison gas.
     * @param xPos      where the rat is on the x-axis.
     * @param yPos      where the rat is on the y-axis.
     */
    Rat(int speed, Direction direction, int gasTimer, int xPos, int yPos) {
        super(true);
        this.speed = speed;
        this.direction = direction;
        this.gasTimer = gasTimer;
        this.xPos = xPos;
        this.yPos = yPos;
        this.tickCounter = (int) Math.floor(Math.random() * RANDOM_START_DELAY);
    }

    /**
     * gets the speed of the rat
     *
     * @return the rat's speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * gets the default speed of a rat
     *
     * @return the rat's default speed
     */
    public static int getDEFAULT_SPEED() {
        return DEFAULT_SPEED;
    }

    /**
     * gets the direction the rat is facing
     *
     * @return the rat's direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * gets the time the rat has been in gas
     *
     * @return the rat's gas timer
     */
    public int getGasTimer() {
        return gasTimer;
    }

    /**
     * gets rat's position on the x-axis
     *
     * @return the rat's x position
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * gets rat's position on the y-axis
     * @return the rat's y position
     */
    public int getYPos() {
        return yPos;
    }


    /**
     * A list of things the rat needs to do every tick.
     */
    public void onTick() {
        tickCounter++;
        // 12 ticks in gas = 2.5 seconds. increase or decrease later as appropriate
        if (gasTimer >= GAS_DEATH_TIME) {
            die();
        }
        if (tickCounter % speed == 0) {
            walk();
        }
        if (gasTimer > 0) {
            decGasTimer();
        }
        onTickSpecific();
    }

    /**
     * Does things that only certain subclasses of Rat need to do.
     */
    protected void onTickSpecific() { }

    /**
     * Causes the rat to walk in a direction. If multiple directions are valid, it will choose a random one.
     * The rat will only go backwards if no other directions are valid.
     */
    public void walk() {
        direction = pickNewDirection();
        boolean stopSignAhead = false;
        int numOfPowers = getForwardTile().getActivePowers().size();
        for (int i = 0; i < numOfPowers; i++) {
            Power powerAhead = getForwardTile().getActivePowers().get(i);
            // if any powers on the tile ahead are stop signs
            if (powerAhead instanceof StopSign) {
                // damage the stop sign
                stopSignAhead = true;
                powerAhead.activate(new ArrayList<>(), getForwardTile());
            }
        }

        if (!stopSignAhead && direction != null) {
            boolean move = true;
            if (!(this instanceof DeathRat)) {
                // add the rat to the next tile and remove it from the current one
                getForwardTile().addOccupantRat(this);
                Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).removeOccupantRat(this);

            } else {
                if (((DeathRat) this).getOminousWaiting() == 0) {
                    // death rats can only move if their wait time is up
                    // add the rat to the next tile and remove it from the current one
                    getForwardTile().addOccupantRat(this);
                    Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).removeOccupantRat(this);

                } else {
                    move = false;
                }
            }
            if (move){
                // update rat position
                if (direction == Direction.NORTH) {
                    yPos--;
                }
                if (direction == Direction.EAST) {
                    xPos++;
                }
                if (direction == Direction.SOUTH) {
                    yPos++;
                }
                if (direction == Direction.WEST) {
                    xPos--;
                }
            }
        }

    }


    /**
     * Returns a direction that the rat would be facing if it turned right.
     *
     * @return a direction that the rat would be facing if it turned right.
     */
    public Direction turnRight() {
        switch (direction) {
            case NORTH:
                return Direction.EAST;
            case EAST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.WEST;
            default:
                return Direction.NORTH;

        }
    }

    /**
     * Returns a direction that the rat would be facing if it turned left.
     *
     * @return a direction that the rat would be facing if it turned left.
     */
    public Direction turnLeft() {
        switch (direction) {
            case NORTH:
                return Direction.WEST;
            case WEST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.EAST;
            default:
                return Direction.NORTH;

        }
    }

    /**
     * Returns a direction that the rat would be facing if it turned around.
     *
     * @return a direction that the rat would be facing if it turned around.
     */
    public Direction turnBack() {
        switch (direction) {
            case NORTH:
                return Direction.SOUTH;
            case EAST:
                return Direction.WEST;
            case SOUTH:
                return Direction.NORTH;
            default:
                return Direction.EAST;

        }
    }

    /**
     * Chooses a direction for the rat to move in, such that it will
     * not land on a grass tile. It will prioritize moving forward,
     * left or right, only moving backwards when the other three
     * options are not valid (i.e a dead end).
     * IMPORTANT: This will return null if the rat is trapped by 4
     * grass squares. This shouldn't happen outside of levels
     * created via level editing.
     *
     * @return a valid direction for the rat to move in.
     */
    public Direction pickNewDirection() {
        ArrayList<Direction> validDirections = new ArrayList<>();
        if (getForwardTile() != null && getForwardTile().isPassable()) {
            validDirections.add(direction);
        }
        if (getRightTile() != null && getRightTile().isPassable()) {
            validDirections.add(turnRight());
        }
        if (getLeftTile() != null && getLeftTile().isPassable()) {
            validDirections.add(turnLeft());
        }

        Direction chosenDirection;
        if (validDirections.size() == 0) {
            // forward, right, and left aren't options. Try going back.
            if (getRearTile() != null && getRearTile().isPassable()) {
                chosenDirection = turnBack();
            } else {
                // the only time we should get to the point is if the rat is
                // stuck in a box. this is pretty cruel to the rat.
                return null;
            }
        } else {
            // select a random item from validDirections
            chosenDirection = validDirections.get((int)
                    Math.floor(Math.random() * validDirections.size()));
        }

        return chosenDirection;
    }


    /**
     * Gets the tile to the rat's front, given its current direction.
     *
     * @return The tile directly ahead of the rat.
     */
    public Tile getForwardTile() {
        Tile forwardTile;
        switch (direction) {
            case NORTH:
                forwardTile = LevelController.getTileAt(xPos, yPos - 1);
                break;
            case EAST:
                forwardTile = LevelController.getTileAt(xPos + 1, yPos);
                break;
            case SOUTH:
                forwardTile = LevelController.getTileAt(xPos, yPos + 1);
                break;
            default:
                forwardTile = LevelController.getTileAt(xPos - 1, yPos);
                break;

        }
        return forwardTile;
    }

    /**
     * Gets the tile to the rat's left, given its current direction.
     *
     * @return The tile to the rat's left
     */
    public Tile getLeftTile() {
        Tile leftTile;
        switch (direction) {
            case NORTH:
                leftTile = LevelController.getTileAt(xPos - 1, yPos);
                break;
            case EAST:
                leftTile = LevelController.getTileAt(xPos, yPos - 1);
                break;
            case SOUTH:
                leftTile = LevelController.getTileAt(xPos + 1, yPos);
                break;
            default:
                leftTile = LevelController.getTileAt(xPos, yPos + 1);
                break;
        }
        return leftTile;
    }

    /**
     * Gets the tile to the rat's right, given its current direction.
     *
     * @return The tile to the rat's right
     */
    public Tile getRightTile() {
        Tile rightTile;
        switch (direction) {
            case NORTH:
                rightTile = LevelController.getTileAt(xPos + 1, yPos);
                break;
            case EAST:
                rightTile = LevelController.getTileAt(xPos, yPos + 1);
                break;
            case SOUTH:
                rightTile = LevelController.getTileAt(xPos - 1, yPos);
                break;
            default:
                rightTile = LevelController.getTileAt(xPos, yPos - 1);
                break;

        }
        return rightTile;
    }

    /**
     * Gets the tile behind the rat, given its current direction.
     *
     * @return The tile behind the rat
     */
    public Tile getRearTile() {
        Tile rearTile;
        switch (direction) {
            case NORTH:
                rearTile = LevelController.getTileAt(xPos, yPos + 1);
                break;
            case EAST:
                rearTile = LevelController.getTileAt(xPos - 1, yPos);
                break;
            case SOUTH:
                rearTile = LevelController.getTileAt(xPos, yPos - 1);
                break;
            default:
                rearTile = LevelController.getTileAt(xPos + 1, yPos);
                break;

        }
        return rearTile;
    }

    /**
     * Adds 2 to the rat's record of how long it's been in toxic gas.
     */
    public void incGasTimer() {
        this.gasTimer += 2;
    }

    /**
     * Subtracts 1 from the rat's record of how long it's been in toxic gas.
     */
    public void decGasTimer() {
        this.gasTimer--;
    }

    /**
     * Causes the rat to die.
     */
    public void die() {
        LevelController.ratKilled(this);
        if (LevelController.getTileAt(xPos, yPos) != null) {
            Objects.requireNonNull(LevelController.getTileAt(xPos, yPos)).removeOccupantRat(this);
            AudioClip deathSound = new AudioClip(
                    new File("target/classes/" + DEATH_SOUND_PATH).toURI().toString());
            deathSound.play();
        }


    }

    /**
     * Draws rat to screen.
     *
     * @param x  Horizontal position.
     * @param y  Vertical position.
     * @param gc Graphics context.
     */
    @Override
    public void draw(int x, int y, GraphicsContext gc) {
        x = getWIDTH() * x;
        y = getWIDTH() * y;

        String className = this.getClass().getSimpleName().toLowerCase();

        String path;

        if (direction == null) {
            path = "file:target/classes/" + className + "NORTH" + ".png";
        } else {
            path = createTexturePath();
        }

        gc.drawImage(new Image(path), x, y);
    }

    /**
     * Creates path to texture of a rat.
     *
     * @return path.
     */
    public String createTexturePath() {
        String className = this.getClass().getSimpleName().toLowerCase();
        if (direction == null) {
            return "file:target/classes/" + className + "NORTH" + ".png";
        } else {
            return "file:target/classes/" + className + direction.name() + ".png";
        }
    }
}
