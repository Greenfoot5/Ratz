import java.util.ArrayList;

/**
 * Class that defines green stuff.
 *
 * @author Alexander Douglas Lloyd-Ward
 * @version 1.0
 */
public class GrassB extends Tile {
    /**
     * Grass constructor.
     * Cannot be interacted with or passed.
     */
    public GrassB() {
        super(false, new ArrayList<>(), new ArrayList<>());
    }
}