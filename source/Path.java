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
}
