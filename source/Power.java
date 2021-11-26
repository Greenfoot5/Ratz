import java.util.ArrayList;

/**
 * An abstract class which models all powers.
 * @author Daumantas Balakauskas
 */

public abstract class Power extends GameObject{

    /**
     * Power constructor
     *
     * @param isInteractive can player place power on.
     * @param isPassable can rats walk through.
     */

    Power(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    /**
     * Abstract method to let any power know it's time to do something.
     * @param rats used to interact with all rats that stepped on the power.
     */
    abstract void activate(ArrayList<Rat> rats, Tile currentTile);

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     */

    abstract void onTick(ArrayList<Rat> rats, Tile currentTile);
}