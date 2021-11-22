import java.util.ArrayList;
/**
 * Abstract class that defines what a tile even is anyway
 * @author Alexander Douglas Lloyd-Ward
 */

public class Tile extends GameObject {
    //private boolean canRatMove;
    private ArrayList<Power> activePowers;
    private ArrayList<LivingRat> occupantRats;

    /**
     * Tile constructor.
     *
     * @param activePowers    What powers are active on this tile.
     * @param occupantRats    What rats are present on this tile.
     */
    public Tile(boolean isPassable,ArrayList<Power> activePowers, ArrayList<LivingRat> occupantRats) {
        super(false, isPassable);
        this.activePowers = activePowers;
        this.occupantRats = occupantRats;
    }

    //===========================ArrayList controllers===========================

    public void addActivePower(ArrayList<Power> activePowers) {
       // this.activePowers.add(new Power(), activePowers.size()+1);
    }
    public void addOccupantRat(ArrayList<LivingRat> occupantRats) {
       // this.occupantRats.add(new LivingRat(), occupantRats.size()+1);
    }
    //==================================Getters==================================
    public ArrayList<LivingRat> getOccupantRats() {
        return occupantRats;
    }
    public ArrayList<Power> getActivePowers() {
        return activePowers;
    }
}
