import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * A class that acts as an unpassable tile for Rat, activating 5 times before
 * disappearing.
 * @author Daumantas Balakauskas
 */

public class StopSign extends Power {

    private int ticksActive = 0;

    private static final String STOP_SIGN_SOUND_PATH
            = "resources/stopSignSound.mp3";

    private int HP = 5;

    /**
     * StopSign constructor
     */

    StopSign(int xPos, int yPos) {
        super(true, xPos, yPos);
    }

    /**
     * For StopSign - Means reducing HP if rat tried to pass it, which will
     * change the texture of this Power. Removes itself when HP hits 0.
     * @param rats used to interact with all rats that stepped on the power.
     * rats are not being used for this power.
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
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     * StopSign doesn't need to track game ticks.
     */

    @Override
    void onTick(ArrayList<Rat> rats, Tile currentTile) {
        ticksActive = ticksActive + 1;

        if (ticksActive == 1) {
            SeaShantySimulator seaSim = new SeaShantySimulator();
            seaSim.playAudioClip(STOP_SIGN_SOUND_PATH, 0.1);
        }
    }

    /**
     * Uses current HP of StopSign to draw the needed texture.
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
     * @return path.
     */
    @Override
    public String createTexturePath() {
        return "file:" + getTextureFolder() + "/stopsign5.png";
    }

    /**
     * Getter for fileReader
     */

    public int getHP() {
        return HP;
    }

    /**
     * Setter for fileReader
     */

    public void setHP(int HP) {
        this.HP = HP;
    }
}
