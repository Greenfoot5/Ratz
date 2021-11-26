import java.util.ArrayList;

/**
 * An abstract class which models all powers.
 * @author Daumantas Balakauskas
 */

public abstract class Power extends GameObject{

    Power(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    /**
     * Abstract method to let any power know it's time to do something.
     * @param rats used to interact with all rats that stepped on the power.
     */
    abstract void activate(ArrayList<Rat> rats);
}