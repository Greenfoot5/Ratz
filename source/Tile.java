import java.util.ArrayList;
import java.util.Iterator;

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
        super(isPassable);
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

    public void update(int frameTime) {
        int numOfPowers = activePowers.size();
        for(int i = 0; i < numOfPowers; i++) {
            activePowers.get(i).onTick(occupantRats, this);
            numOfPowers = activePowers.size();
        }
        int numOfRats = occupantRats.size();
        for(int i = 0; i < numOfRats; i++) {
            occupantRats.get(i).onTick();
            numOfRats = occupantRats.size();
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

    /**
     * Returns whether players can place items on the tile.
     * @return interactivity.
     */
    public boolean isInteractive() {
        return false;
    }
}