import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//TODO:
    //@Override draw in Path (that draws the path + everything that is on it)
    //@Override draw in Rat (that makes rat sprite face a certain direction)
    //@Override draw in Bomb (that makes the countdown of a bomb visible to player)

/**
 * Class that describes a game object.
 */
public abstract class GameObject {
    private static final int WIDTH = 64;

    private static String TEXTURE_FOLDER = "resources";
    private final Image img;

    private final boolean isPassable;

    /**
     * Object constructor.
     * @param isPassable can rats walk through.
     */
    public GameObject(boolean isPassable) {
        this.isPassable = isPassable;
        img = new Image(createTexturePath());
    }

    /**
     * Draws object on screen.
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
     * @return width of game object.
     */
    protected static int getWIDTH() {
        return WIDTH;
    }

    /**
     * Gets folder that textures are being held in.
     * @return texture folder.
     */
    protected static String getTextureFolder() {
        return TEXTURE_FOLDER;
    }

    /**
     * Gets game object image.
     * @return game object image.
     */
    protected Image getImg() {
        return img;
    }

    /**
     * Creates file path to the texture of a specific object.
     * @return File path as String.
     */
    public String createTexturePath() {
        String className = this.getClass().getSimpleName().toLowerCase();
        return "file:" + TEXTURE_FOLDER + "/" + className + ".png";
    }

    /**
     * Sets the the folder where all textures are stored.
     * @param folder Name of texture folder.
     */
    public void setTextureFolder(String folder) {
        TEXTURE_FOLDER = folder;
    }

    /**
     * Returns whether rats can pass through game object.
     * @return can be passed.
     */
    public boolean isPassable() {
        return isPassable;
    }
}
