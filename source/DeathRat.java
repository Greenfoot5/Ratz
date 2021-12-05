import java.util.ArrayList;

/**
 * An class to model the death rat power-up.
 * @author James McWilliams
 */

public class DeathRat extends Rat {
    private int ominousWaiting;
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
    public DeathRat(int speed, Direction direction, int gasTimer, int xPos,
                     int yPos, int killCounter) {
        super(speed, direction, gasTimer, xPos, yPos);
        this.killCounter = killCounter;
        this.ominousWaiting = 8;
    }

    /**
     * A list of things the rat needs to do every tick.
     */
    @Override
    protected void onTickSpecific() {
        if (killCounter >= 5) {
            die();
        }
        ominousWaiting--;
        killRats();
    }

    public int getOminousWaiting() {
        return ominousWaiting;
    }

    /**
     * Kills every rat on the current tile. Will stop midway if the death rat
     * runs out of killing... power?
     */
    public void killRats(){
        Tile currentTile = LevelController.getTileAt(xPos,yPos);
        // kill every rat on the tile unless your kill counter is 5 or greater

        while (currentTile.getOccupantRats().size() > 0 && killCounter < 5) {
            currentTile.getOccupantRats().get(0).die();
            killCounter++;

        }
    }

    public int getKillCounter() {
        return killCounter;
    }
}
