import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class that describes a game object.
 *
 * @author Vilija Pundyte
 * @version 1.0
 */
public abstract class GameObject {
    private static final int WIDTH = 64;

    private final Image img;
    private final boolean isPassable;

    /**
     * Object constructor.
     *
     * @param isPassable can rats walk through.
     */
    public GameObject(boolean isPassable) {
        this.isPassable = isPassable;
        img = new Image(createTexturePath());
    }

    /**
     * Draws object on screen.
     *
     * @param x Horizontal position.
     * @param y Vertical position.
     * @param g Graphics context being drawn on.
     */
    public void draw(int x, int y, GraphicsContext g) {
        x = WIDTH * x;
        y = WIDTH * y;
        g.drawImage(img,x,y);
    }

    /**
     * Gets width of a single game object texture in pixels.
     *
     * @return width of game object.
     */
    protected static int getWIDTH() {
        return WIDTH;
    }

    /**
     * Gets game object image.
     *
     * @return game object image.
     */
    protected Image getImg() {
        return img;
    }

    /**
     * Creates file path to the texture of a specific object.
     *
     * @return File path as String.
     */
    public String createTexturePath() {
        String className = this.getClass().getSimpleName().toLowerCase();
        return "file:" + className + ".png";
    }

    /**
     * Returns whether rats can pass through game object.
     *
     * @return can be passed.
     */
    public boolean isPassable() {
        return isPassable;
    }
}
