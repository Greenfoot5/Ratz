import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//TODO:
    //@Override draw in Path (that draws the path + everything that is on it)
    //@Override draw in Rat (that makes rat sprite face a certain direction)
    //@Override draw in Bomb (that makes the countdown of a bomb visible to player)
    //@Override draw in StopSign (that makes the deterioration of stop sign visible to player)

/**
 * Class that describes a game object.
 */
public abstract class GameObject {
    private final Image img;

    private final static int WIDTH = 64;
    private static String TEXTURE_FOLDER = "resources";

    private final boolean isInteractive;
    private final boolean isPassable;

    /**
     * Object constructor.
     * @param isInteractive can players place powers on.
     * @param isPassable can rats walk through.
     */
    GameObject(boolean isInteractive, boolean isPassable) {
        this.isInteractive = isInteractive;
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

    public static int getWIDTH() {
        return WIDTH;
    }

    public static String getTextureFolder() {
        return TEXTURE_FOLDER;
    }

    public Image getImg() {
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

    public boolean isInteractive() {
        return  isInteractive;
    }

    public boolean isPassable() {
        return isPassable;
    }
}
