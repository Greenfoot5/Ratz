import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that kills all rats standing on the tile at the time of explosion -
 * (5 game ticks after it's been placed).
 * @author Daumantas Balakauskas
 * @version 1.0
 */

public class Bomb extends Power {

    private static final String BOMB_SOUND_PATH = "resources/bombSound.mp3";
    private static final int EXPLODE_TICK = 5; // How many ticks to reach to explode

    private int ticksActive = 0; //Tick counter since creation of this class.


    /**
     * Constructs a bomb
     * @param xPos The x position to create the bomb
     * @param yPos The y position to create the bomb
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

        // Explody bit
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

    /**
     * Method that finds all Tiles bomb can reach.
     * @return All Tiles that bomb can reach (not grass) in all 4 directions.
     */
    private ArrayList<Tile> findPathTiles () {
        ArrayList<Tile> tilesToExplode = new ArrayList<>();

        int counter = 1;

        // North
        if (LevelController.getTileAt(this.xPos, this.yPos + counter) != null) {
            while (Objects.requireNonNull(LevelController.getTileAt(this.xPos, this.yPos + counter)).isPassable()) {
                tilesToExplode.add(LevelController.getTileAt(this.xPos,
                        this.yPos + counter));
                counter++;
            }
        }

        // South
        counter = 1;
        if (LevelController.getTileAt(this.xPos, this.yPos - counter) != null) {
            while (Objects.requireNonNull(LevelController.getTileAt(this.xPos, this.yPos - counter)).isPassable()) {
                tilesToExplode.add(LevelController.getTileAt(this.xPos,
                        this.yPos - counter));
                counter++;
            }
        }

        // East
        counter = 1;
        if (LevelController.getTileAt(this.xPos + counter, this.yPos) != null) {
            while (Objects.requireNonNull(LevelController.getTileAt(this.xPos + counter, this.yPos)).isPassable()) {
                tilesToExplode.add(LevelController.getTileAt(this.xPos + counter,
                        this.yPos));
                counter++;
            }
        }

        // West
        counter = 1;
        if (LevelController.getTileAt(this.xPos - counter, this.yPos) != null) {
            while(Objects.requireNonNull(LevelController.getTileAt(this.xPos - counter, this.yPos)).isPassable()) {
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
            SeaShantySimulator seaSim = new SeaShantySimulator();
            seaSim.playAudioClip(BOMB_SOUND_PATH, 0.2);
        }

        ticksActive = ticksActive + 1;

        if (ticksActive >= EXPLODE_TICK) {
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
     * How many ticks since the creation of the class
     * @return ticksActive
     */
    public int getTicksActive() {
        return ticksActive;
    }

    /**
     * Sets how many ticks have passed since the creation of the class
     * @param ticksActive ticks since class creation
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
