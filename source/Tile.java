import java.util.ArrayList;

/**
 * Abstract class that defines what a tile even is anyway
 *
 * @author Alexander Douglas Lloyd-Ward
 */

public class Tile extends GameObject {
    //private boolean canRatMove;
    private final ArrayList<Power> activePowers;
    private final ArrayList<Rat> occupantRats;

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
     *      Adds and removes powers/rats from the tile.
     * </p>
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

    /**
     * update
     * <p>
     *     actively updates the powers/rats on a tile per tick
     * </p>
     */
    public void update() {
        int numOfPowers = activePowers.size();
        int numOfRats = occupantRats.size();

        for(int i = 0; i < numOfPowers; i++) {
            activePowers.get(i).onTick(occupantRats, this);
            if(activePowers.size() == numOfPowers) {
                Power power = activePowers.get(i);
                if(power instanceof MaleSwapper || power instanceof FemaleSwapper || power instanceof Poison) {
                    if (numOfRats > 0){
                        activePowers.get(i).activate(occupantRats,this);
                    }
                }
            }
            numOfRats = occupantRats.size();
            numOfPowers = activePowers.size();
        }

        for(int i = 0; i < numOfRats; i++) {
            occupantRats.get(i).onTick();
            numOfRats = occupantRats.size();
        }
    }

    //==================================Getters==================================

    /**
     * Rat Getter
     *
     * @return The rats on a tile
     */
    public ArrayList<Rat> getOccupantRats() {
        return occupantRats;
    }

    /**
     * Power Getter
     * @return The powers on a tile
     */
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