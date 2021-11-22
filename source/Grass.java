/**
 * Class that defines green stuff.
 * @author Alexander Douglas Lloyd-Ward
 */
public class Grass extends Tile {
    /**
     * Grass constructor.
     * Cannot be interacted with or passed.
     */
    public Grass(boolean isPassable, ArrayList<Power> activePowers, ArrayList<LivingRat> occupantRats) {
        super(false, isPassable = false);
    }
}

