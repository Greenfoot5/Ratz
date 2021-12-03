import java.util.ArrayList;

/**
 * A class that deals damage to Rats that step over this Power.
 * @author Daumantas Balakauskas
 */

public class Gas extends Power {

    private int ticksActive = 0; //Tick counter since creation of this class.

    /**
     * Gas constructor
     */

    Gas(int xPos, int yPos) {
        super(true, xPos, yPos);
    }

    /**
     * Abstract method to let any power know it's time to do something.
     * For Gas - Increase gasTimer of all rats that pass through this Power.
     *
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile used to remove this Power from Tile.
     */


    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {

        ArrayList<Tile> tilesToGas = findPathTiles();
        tilesToGas.add(currentTile);

        //Where the rats get gassed.
        for (Tile tile : tilesToGas) {

            for (Rat r : tile.getOccupantRats()) {
                r.incGasTimer();
            }
        }

        if (ticksActive >= 5) {
            currentTile.removeActivePower(this);
        }
    }

    /** Method that finds all Tiles Gas can reach.
     *  @return All Tiles that Gas can reach (not grass) in all 4 directions.
     */

    ArrayList<Tile> findPathTiles () {
        ArrayList<Tile> tilesToGas = new ArrayList<>();

        int counter = 1;

        while(LevelController.getTileAt(this.xPos, this.yPos+counter).isPassable()) {
            tilesToGas.add(LevelController.getTileAt(this.xPos,
                    this.yPos+counter));
            counter++;
        }

        while(LevelController.getTileAt(this.xPos, this.yPos-counter).isPassable()) {
            tilesToGas.add(LevelController.getTileAt(this.xPos,
                    this.yPos-counter));
            counter++;
        }

        while(LevelController.getTileAt(this.xPos+counter, this.yPos).isPassable()) {
            tilesToGas.add(LevelController.getTileAt(this.xPos+counter,
                    this.yPos));
            counter++;
        }

        while(LevelController.getTileAt(this.xPos-counter, this.yPos).isPassable()) {
            tilesToGas.add(LevelController.getTileAt(this.xPos-counter,
                    this.yPos));
            counter++;
        }

        return tilesToGas;
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
        if (ticksActive < 5) {
            activate(rats, currentTile);
        } else {
            currentTile.removeActivePower(this);
        }
    }
}