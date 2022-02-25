import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * Class that defines a Path.
 *
 * @author Alexander Douglas Lloyd-Ward
 * @version 1.0
 */
public class PathB extends Tile {
    /**
     * Constructs a new path
     */
    public PathB() {
        super(true, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Draws path and all the powers and rats on it
     *
     * @param x Horizontal position.
     * @param y Vertical position.
     * @param g Graphics context being drawn on.
     */
    @Override
    public void draw(int x, int y, GraphicsContext g) {
        int xDraw = GameObject.getWIDTH() * x;
        int yDraw = GameObject.getWIDTH() * y;

        g.drawImage(getImg(),xDraw,yDraw);

        boolean drawGas = false;

        // Draws the powers
        for(int i = 0; i < getActivePowers().size() ; i++) {
            for(Power p: getActivePowers()) {
                if(p instanceof Gas){
                    drawGas = true;
                } else {
                    p.draw(x,y,g);
                }
            }
        }

        // Draws the rats
        for(int i = 0; i < getOccupantRats().size() ; i++) {
            for(Rat r: getOccupantRats()) {
                r.draw(x,y,g);
            }
        }

        // Gas is always drawn on top
        if (drawGas) {
            (new Gas(x, y, true)).draw(x,y,g);
        }

    }

    /**
     * Returns whether players can place items on the tile.
     *
     * @return interactivity.
     */
    @Override
    public boolean isInteractive() {
        // If a rat is already there
        if(getOccupantRats().size() != 0) {
            return false;
        }
        // If a StopSign is on the tile
        for (Power p: getActivePowers()) {
            if(p instanceof StopSign) {
                return false;
            }
        }
        return true;
    }

}