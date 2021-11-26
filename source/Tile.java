import java.util.ArrayList;

/**
 * Abstract class that defines what a tile even is anyway
 *
 * @author Alexander Douglas Lloyd-Ward
 */

public class Tile extends GameObject {
    //private boolean canRatMove;
    private ArrayList<Power> activePowers;
    private ArrayList<Rat> occupantRats;

    /**
     * Tile constructor.
     *
     * @param activePowers What powers are active on this tile.
     * @param occupantRats What rats are present on this tile.
     */
    public Tile(boolean isPassable, ArrayList<Power> activePowers,
                ArrayList<Rat> occupantRats) {
        super(false, isPassable);
        this.activePowers = activePowers;
        this.occupantRats = occupantRats;
    }

    //===========================ArrayList controllers===========================

    /**
     * Occupancy Controllers
     * <p>
     * Adds and removes powers/rats from the tile.
     */
    public void addActivePower(Power p) {
        this.activePowers.add(p);
    }

    public void addOccupantRat(Rat r) {
        this.occupantRats.add(r);
    }

    public void removeActivePower(Power p) {
        this.activePowers.remove(p);
    }

    public void removeOccupantRat(Rat r) {
        this.occupantRats.remove(r);
    }

    public void tickPowers() {
        for(Power p : activePowers) {
            p.onTick(occupantRats, this);
        }
    }

    //==================================Getters==================================

    /**
     * Getters
     *
     * @return The rats and powers on a tile
     */
    public ArrayList<Rat> getOccupantRats() {
        return occupantRats;
    }

    public ArrayList<Power> getActivePowers() {
        return activePowers;
    }
}