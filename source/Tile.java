import java.util.ArrayList;

/**
 * Abstract class that defines what a tile even is anyway
 * @author Alexander Douglas Lloyd-Ward
 */

public class Tile extends GameObject {
    //private boolean canRatMove;
    private ArrayList<Power> activePowers = new ArrayList<Power>();
    private ArrayList<LivingRat> occupantRats = new ArrayList<LivingRat>();

    /**
     * Tile constructor.
     *
     * @param activePowers    What powers are active on this tile.
     * @param occupantRats    What rats are present on this tile.
     */

    public Tile(boolean isPassable,/* boolean canRatMove,*/ ArrayList<Power> activePowers, ArrayList<LivingRat> occupantRats) {
        super(false, isPassable);
        //this.canRatMove = canRatMove;
        this.activePowers = activePowers;
        this.occupantRats = occupantRats;
    }




    //========================Getters and setters========================

    public ArrayList<Power> getActivePowers() {
        return activePowers;
    }

    public void setActivePowers(ArrayList<Power> activePowers) {
        this.activePowers = activePowers;
    }

    public ArrayList<LivingRat> getOccupantRats() {
        return occupantRats;
    }

    public void setOccupantRats(ArrayList<LivingRat> occupantRats) {
        this.occupantRats = occupantRats;
    }
}

