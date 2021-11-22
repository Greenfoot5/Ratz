/**
 * Class that defines a Tunnel.
 * @author Alexander Douglas Lloyd-Ward
 */
public class Tunnel extends Tile {
 /**
 * Tunnel constructor.
 * Doesn't really do anything that the Tile doesn't do.
 */
    public Tunnel(boolean isPassable, ArrayList<Power> activePowers, ArrayList<LivingRat> occupantRats) {
         super(false, isPassable = true);
    }
}
