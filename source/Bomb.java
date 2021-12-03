import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * A class that kills all rats standing on the tile at the time of explosion -
 * (5 game ticks after it's been placed).
 * @author Daumantas Balakauskas
 */

public class Bomb extends Power {

    private int ticksActive = 0; //Tick counter since creation of this class.

    /**
     * Bomb constructor
     */

    Bomb(int xPos, int yPos) {
        super(true, xPos, yPos);
    }

    /**
     * Abstract method to let any power know it's time to do something.
     * For Bomb - kill all rats standing on it's Tile at the time of explosion.
     *
     * @param rats used to interact with all rats that stepped on the power.
     * @param currentTile used to remove this Power from Tile.
     */

    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        if (ticksActive >= 5) {
            ArrayList<Tile> tilesToExplode = findPathTiles();
            tilesToExplode.add(currentTile);

            //Explody bit
            for (Tile tile : tilesToExplode) {
                for (Rat r : tile.getOccupantRats()) {
                    r.die();
                }
            }

            currentTile.removeActivePower(this);
        }
    }

    /** Method that finds all Tiles bomb can reach.
     *  @return All Tiles that bomb can reach (not grass) in all 4 directions.
     */
    ArrayList<Tile> findPathTiles () {
        ArrayList<Tile> tilesToExplode = new ArrayList<>();

        int counter = 1;

        while(LevelController.getTileAt(this.xPos, this.yPos+counter).isPassable()) {
            tilesToExplode.add(LevelController.getTileAt(this.xPos,
                    this.yPos+counter));
            counter++;
        }

        while(LevelController.getTileAt(this.xPos, this.yPos-counter).isPassable()) {
            tilesToExplode.add(LevelController.getTileAt(this.xPos,
                    this.yPos-counter));
            counter++;
        }

        while(LevelController.getTileAt(this.xPos+counter, this.yPos).isPassable()) {
            tilesToExplode.add(LevelController.getTileAt(this.xPos+counter,
                    this.yPos));
            counter++;
        }

        while(LevelController.getTileAt(this.xPos-counter, this.yPos).isPassable()) {
            tilesToExplode.add(LevelController.getTileAt(this.xPos-counter,
                    this.yPos));
            counter++;
        }

        return tilesToExplode;
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
        if (ticksActive >= 5) {
            activate(rats, currentTile);
        } else {
            currentTile.removeActivePower(this);
        }
    }

    @Override
    public void draw(int x, int y, GraphicsContext g) {
        x = GameObject.getWIDTH()* x;
        y = GameObject.getWIDTH() * y;

        String path = "file:" + getTextureFolder() + "/bomb" + ticksActive + ".png";

        g.drawImage(new Image(path),x,y);
    }

    public int getTicksActive() {
        return ticksActive;
    }
}
