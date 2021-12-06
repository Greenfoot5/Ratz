import java.util.ArrayList;

/**
 * A class that kills rats that step on this Power.
 * @author Daumantas Balakauskas
 * @version 1.0
 */

public class Poison extends Power {

    private int ticksActive = 0;
    private static final String POISON_SOUND_PATH = "resources/poisonSound.mp3";

    /** Poison constructor
     *
     * @param xPos x coordinate
     * @param yPos y coordinate
     */

    Poison(int xPos, int yPos) {
        super(true, xPos, yPos);
    }

    /** Kills rats standing on this Powers's Tile.
     *
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile used to remove this Power from Tile.
     */

    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        int numOfRats = rats.size();
        for (int i = 0; i < numOfRats; i++) {
            rats.get(i).die();
            numOfRats = rats.size();
        }
        currentTile.removeActivePower(this);
    }

    /**
     * Abstract method for certain powers that need to do something after a
     * certain amount of game ticks.
     * @param currentTile used for calling removeActivePower(this).
     * @param rats used for updating the rat arraylist every game tick.
     *
     * onTick only used for playing a sound right after it's placed.
     */

    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {
        ticksActive = ticksActive + 1;

        if (ticksActive == 1) {
            SeaShantySimulator seaSim = new SeaShantySimulator();
            seaSim.playAudioClip(POISON_SOUND_PATH, 0.1);
        }
    }
}
