import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * Class that defines a Path.
 *
 * @author Alexander Douglas Lloyd-Ward
 */
public class Path extends Tile {
    /**
     * Path constructor.
     * Doesn't really do anything that the Tile doesn't do.
     */
    public Path() {
        super(true, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Draws path and all of the powers and rats on it
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

        for(int i = 0; i < getActivePowers().size() ; i++) {
            for(Power p: getActivePowers()) {
                if(p instanceof Gas){
                    drawGas = true;
                } else {
                    p.draw(x,y,g);
                }
            }
        }

        for(int i = 0; i < getOccupantRats().size() ; i++) {
            for(Rat r: getOccupantRats()) {
                r.draw(x,y,g);
            }
        }

        if (drawGas) {
            (new Gas()).draw(x,y,g);
        }

    }

    /**
     * Returns whether players can place items on the tile.
     * @return interactivity.
     */
    @Override
    public boolean isInteractive() {
        if(getOccupantRats().size() != 0) {
            return false;
        }
        for (Power p: getActivePowers()) {
            if(p instanceof StopSign) {
                return false;
            }
        }
        return true;
    }

}