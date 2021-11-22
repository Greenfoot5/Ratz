import java.util.ArrayList;
/**
 * Class that defines green stuff.
 * @author Alexander Douglas Lloyd-Ward
 */
public class Grass extends Tile {
    /**
     * Grass constructor.
     * Cannot be interacted with or passed.
     */
    public Grass() {
        super(false, new ArrayList<>(), new ArrayList<>());
    }
}
