import java.util.ArrayList;

//Abstract Power class for communication with Tile and activating the right type of power.

public abstract class Power extends GameObject{

    Power(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    abstract void activate(ArrayList<Rat> rats);
}