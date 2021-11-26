import java.util.ArrayList;

public class Poison extends Power {

    Poison(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    //This class kills all rats that are passed to it.

    public static void killRats(ArrayList<Rat> rats) {
        for (Rat rat : rats) {
            rat.die();
        }
    }

    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        killRats(rats);
    }

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     */

    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {

    }
}
