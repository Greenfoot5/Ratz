import java.util.ArrayList;

/**
 * An class to model the death rat power-up.
 * @author James McWilliams
 */

public abstract class DeathRat extends Rat {
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
     * Kills every rat on the current tile. Will stop midway if the death rat
     * runs out of killing... power?
     */
    public void killRats(){
        Tile currentTile = LevelController.getTileAt(this.xPos,this.yPos);
        // kill every rat on the tile unless your kill counter is 5 or greater
        for (int i = 0; i <= currentTile.getOccupantRats().size(); i++) {
            currentTile.getOccupantRats().get(0).die();
            killCounter++;

            if (killCounter >= 5) {
                this.die();
                break;
            }
        }
    }
}
