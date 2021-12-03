import java.util.ArrayList;

/**
 * A class that makes rat's sterile once they step on this Power.
 * @author Daumantas Balakauskas
 */

public class Sterilisation extends Power{

    private int ticksActive = 0; //Tick counter since creation of this class.

    /**
     * Sterilisation constructor
     *
     * @param xPos x coordinate
     * @param yPos y coordinate
     */

    Sterilisation(int xPos, int yPos) {
        super(true, xPos, yPos);
    }

    /**
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile used to remove the power from Tile.
     */
    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        ArrayList<Tile> tilesToSterilise = findPathTiles();
        tilesToSterilise.add(currentTile);

        for (Tile tile : tilesToSterilise) {
            for (Rat rat : tile.getOccupantRats()) {
                LivingRat lr = (LivingRat) rat;
                lr.infertilize();
            }
        }
        tilesToSterilise.clear();
    }

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     * @param currentTile used for calling removeActivePower(this).
     * @param rats used for updating the rat arraylist every game tick.
     */

    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {
        ticksActive = ticksActive + 1;
        if (ticksActive < 4) {
            activate(rats, currentTile);
        } else {
            currentTile.removeActivePower(this);
        }
    }

    /** Method that finds all Tiles Sterilisation can reach.
     *  @return All Tiles that Sterilisation can reach in all 4 directions.
     *  (radius = 2)
     */
    private ArrayList<Tile> findPathTiles () {
        ArrayList<Tile> tilesToSterilise = new ArrayList<>();

        int counter = 1;

        while(counter < 3) {
            tilesToSterilise.add(LevelController.getTileAt(this.xPos,
                    this.yPos+counter));

            tilesToSterilise.add(LevelController.getTileAt(this.xPos,
                    this.yPos-counter));

            tilesToSterilise.add(LevelController.getTileAt(this.xPos+counter,
                    this.yPos));

            tilesToSterilise.add(LevelController.getTileAt(this.xPos-counter,
                    this.yPos));

            counter++;
        }
        return tilesToSterilise;
    }

    /**
     * Getter for fileReader
     */

    public int getTicksActive() {
        return ticksActive;
    }
}