import java.util.ArrayList;

/**
 * A class that kills rats in all directions until it hits a grass Tile after
 * X ticks.
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
    void onTick() {

    }

    @Override
    void activate(ArrayList<Rat> rats) {
        for (Rat r : rats) {
            r.die();
        }
    }
    
}
