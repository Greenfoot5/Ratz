import java.util.ArrayList;

public class MaleSwapper extends Power{

    MaleSwapper(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        /*
        if(rats.get(0).getGender == "female") {

        }

        Need to copy the rat that stepped on, kill it, spawn a copy of the old one but with opposite gender.
        */
    }

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     */
    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {

    }

}
