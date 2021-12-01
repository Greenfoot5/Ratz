import java.util.ArrayList;

/**
 * An class to model the death rat power-up.
 * @author James McWilliams
 */

public class DeathRat extends Rat {
    private int killCounter;

    /**
     * LivingRat constructor.
     *
     * @param speed         how fast the rat moves.
     * @param direction     the direction the rat is facing.
     * @param gasTimer      how long the rat has spent inside poison gas.
     * @param xPos          where the rat is on the x axis.
     * @param yPos          where the rat is on the y axis.
     * @param killCounter   how many times the death rat has killed another rat
     */
    public DeathRat(int speed, int direction, int gasTimer, int xPos,
                     int yPos, int killCounter) {
        super(speed, direction, gasTimer, xPos, yPos);
        this.killCounter = killCounter;
    }

    /**
     * A list of things the rat needs to do every tick.
     */
    @Override
    public void onTick() {
        if (killCounter >= 5) {
            die();
        }
        walk();
        decGasTimer();
        killRats();

    }

    /**
     * Kills every rat on the current tile. Will stop midway if the death rat
     * runs out of killing... power?
     */
    public void killRats(){
        Tile currentTile = LevelController.getTileAt(xPos, yPos);
        // kill every rat on the tile unless your kill counter is 5 or greater
        int numRats = currentTile.getOccupantRats().size();

        while (currentTile.getOccupantRats().size() > 0 && killCounter < 5) {
            // killing the rat removes it from the list, no need to .get(i)
            currentTile.getOccupantRats().get(0).die();
            killCounter++;
        }
    }
}
