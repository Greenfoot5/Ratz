import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * A class that acts as an unpassable tile for Rat, activating 5 times before
 * disappearing.
 *
 * @author Daumantas Balakauskas
 * @version 1.0
 */

public class StopSign extends Power {

    private int ticksActive = 0;

    private static final String STOP_SIGN_SOUND_PATH = "resources/stopSignSound.mp3";
    private static final double SOUND_VOLUME = 0.1;

    private int HP = 5;

    /**
     * StopSign constructor
     *
     * @param xPos x coordinate.
     * @param yPos y coordinate.
     */

    StopSign(int xPos, int yPos) {
        super(true, xPos, yPos);
    }

    /**
     * Reduces the HP of the sign, removes itself from Tile when HP hits 0.
     *
     * @param rats not used for this power specifically.
     * @param currentTile used for removing this Power from its Tile.
     */

    @Override
    void activate(ArrayList<Rat> rats, Tile currentTile) {
        HP = HP - 1;

        //If sign is hit by a rat 5 times, remove it from that Tile.
        if (HP == 0) {
            currentTile.removeActivePower(this);
        }
    }

    /**
     * Abstract method for certain powers that need to do something after a
     * certain amount of game ticks.
     * onTick only used for playing a sound right after it's placed here.
     *
     * @param currentTile not used for this power specifically.
     * @param rats not used for this power specifically.
     */

    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {
        ticksActive = ticksActive + 1;

        if (ticksActive == 1) {
            SeaShantySimulator seaSim = new SeaShantySimulator();
            seaSim.playAudioClip(STOP_SIGN_SOUND_PATH, SOUND_VOLUME);
        }
    }

    /**
     * Uses current HP of StopSign to draw the needed texture.
     *
     * @param x Horizontal position.
     * @param y Vertical position.
     * @param g Graphics context being drawn on.
     */

    @Override
    public void draw(int x, int y, GraphicsContext g) {
        x = GameObject.getWIDTH()* x;
        y = GameObject.getWIDTH() * y;

        String path = "file:" + getTextureFolder() + "/stopsign" + HP + ".png";

        g.drawImage(new Image(path),x,y);
    }

    /**
     * Creates path to texture of a stop sign.
     *
     * @return path.
     */

    @Override
    public String createTexturePath() {
        return "file:" + getTextureFolder() + "/stopsign5.png";
    }

    /**
     * Gets the current hit points of StopSign.
     *
     * @return hit points value.
     */

    public int getHP() {
        return HP;
    }

    /**
     * Sets the current hit points of StopSign.
     *
     * @param HP value of hit points to set.
     */

    public void setHP(int HP) {
        this.HP = HP;
    }
}
