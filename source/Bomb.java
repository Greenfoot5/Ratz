import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.util.ArrayList;

/**
 * A class that kills all rats standing on the tile at the time of explosion -
 * (5 game ticks after it's been placed).
 * @author Daumantas Balakauskas
 */

public class Bomb extends Power {

    private int ticksActive = 0; //Tick counter since creation of this class.
    private static final String BOMB_SOUND_PATH = "resources/bombSound.mp3";

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
        ArrayList<Tile> tilesToExplode = findPathTiles();
        tilesToExplode.add(currentTile);

        //Explody bit
        for (Tile tile : tilesToExplode) {
            if (tile != null) {
                int numOfRats = tile.getOccupantRats().size();
                for (int i = 0; i < numOfRats; i++) {
                    tile.getOccupantRats().get(i).die();
                    numOfRats = tile.getOccupantRats().size();
                }
            }
        }

        currentTile.removeActivePower(this);
    }

    /** Method that finds all Tiles bomb can reach.
     *  @return All Tiles that bomb can reach (not grass) in all 4 directions.
     */
    private ArrayList<Tile> findPathTiles () {
        ArrayList<Tile> tilesToExplode = new ArrayList<>();

        int counter = 1;

        if (LevelController.getTileAt(this.xPos, this.yPos + counter) != null) {
            while (LevelController.getTileAt(this.xPos, this.yPos + counter).isPassable()) {
                tilesToExplode.add(LevelController.getTileAt(this.xPos,
                        this.yPos + counter));
                counter++;
            }
        }

        counter = 1;
        if (LevelController.getTileAt(this.xPos, this.yPos - counter) != null) {
            while (LevelController.getTileAt(this.xPos, this.yPos - counter).isPassable()) {
                tilesToExplode.add(LevelController.getTileAt(this.xPos,
                        this.yPos - counter));
                counter++;
            }
        }

        counter = 1;
        if (LevelController.getTileAt(this.xPos + counter, this.yPos) != null) {
            while (LevelController.getTileAt(this.xPos + counter, this.yPos).isPassable()) {
                tilesToExplode.add(LevelController.getTileAt(this.xPos + counter,
                        this.yPos));
                counter++;
            }
        }

        counter = 1;
        if (LevelController.getTileAt(this.xPos - counter, this.yPos) != null) {
            while(LevelController.getTileAt(this.xPos - counter, this.yPos).isPassable()) {
                tilesToExplode.add(LevelController.getTileAt(this.xPos-counter,
                        this.yPos));
                counter++;
            }
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
        if (ticksActive == 0) {
            AudioClip deathSound = new AudioClip(
                    new File(BOMB_SOUND_PATH).toURI().toString());
            deathSound.setVolume(0.1);
            deathSound.play();
        }

        ticksActive = ticksActive + 1;

        if (ticksActive >= 5) {
            activate(rats, currentTile);
        }
    }

    @Override
    public void draw(int x, int y, GraphicsContext g) {
        x = GameObject.getWIDTH()* x;
        y = GameObject.getWIDTH() * y;

        String path = "file:" + getTextureFolder() + "/bomb" + ticksActive + ".png";

        g.drawImage(new Image(path),x,y);
    }

    /**
     * Getter for fileReader
     */

    public int getTicksActive() {
        return ticksActive;
    }

    /**
     * Setter for fileReader
     */

    public void setTicksActive(int ticksActive) {
        this.ticksActive = ticksActive;
    }

    /**
     * Creates path to texture of a bomb.
     * @return path.
     */
    @Override
    public String createTexturePath() {
        return "file:" + getTextureFolder() + "/bomb" + ticksActive + ".png";
    }
}
