import java.util.ArrayList;

/**
 * An abstract class which models all powers.
 *
 * @author Daumantas Balakauskas
 * @version 1.0
 */

public abstract class Power extends GameObject {
    protected int xPos;
    protected int yPos;

    /**
     * Power constructor
     *
     * @param isPassable can rats walk through.
     * @param xPos x coordinate
     * @param yPos y coordinate
     */

    Power(boolean isPassable, int xPos, int yPos) {
        super(true);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Abstract method to let any power know it's time to do something.
     *
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile used for removing power from its Tile.
     */

    abstract void activate(ArrayList<Rat> rats, Tile currentTile);

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     * @param currentTile used for calling removeActivePower(this).
     * @param rats used for updating the rat arraylist every game tick.
     */

    abstract void onTick(ArrayList<Rat> rats, Tile currentTile);

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}