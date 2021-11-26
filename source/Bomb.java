import java.util.ArrayList;

/**
 * A class that kills rats in all directions until it hits a grass Tile after
 * 5 ticks.
 * @author Daumantas Balakauskas
 */

public class Bomb extends Power {

    /**
     * Bomb constructor
     *
     * @param isInteractive can player place power on.
     * @param isPassable can rats walk through.
     */

    Bomb(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     */

    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {

    }

    /**
     * Abstract method to let any power know it's time to do something.
     * For Bomb - kill all rats in the current tile and adds another bomb
     * following Tiles in a direction that aren't grass and instantly
     * activates itself.
     *
     * @param rats used to interact with all rats that stepped on the power.
     */

    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        for (Rat r : rats) {
            r.die();
        }


    }
    
}
