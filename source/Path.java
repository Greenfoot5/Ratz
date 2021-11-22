/**
 * Class that defines a Path.
 * @author Alexander Douglas Lloyd-Ward
 */
public class Path extends Tile {
    /**
     * Path constructor.
     * Doesn't really do anything that the Tile doesn't do.
     */
    public Path(boolean isPassable, ArrayList<Power> activePowers, ArrayList<LivingRat> occupantRats) {
        super(true, isPassable = true);
    }
}
