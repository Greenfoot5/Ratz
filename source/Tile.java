import java.util.ArrayList;

/**
 * Abstract class that defines what a tile even is anyway
 *
 * @author Alexander Douglas Lloyd-Ward
 */
public class Tile extends GameObject {
    private final ArrayList<Power> activePowers;
    private final ArrayList<Rat> occupantRats;

    /**
     * Tile constructor.
     *
     * @param isPassable   Whether rats can walk past this tile.
     * @param activePowers What powers are active on this tile.
     * @param occupantRats What rats are present on this tile.
     */
    public Tile(boolean isPassable, ArrayList<Power> activePowers,
                ArrayList<Rat> occupantRats) {
        super(isPassable);
        this.activePowers = activePowers;
        this.occupantRats = occupantRats;
    }

    /**
     * Adds Power to Tile
     *
     * @param p Power to add to Tile
     */
    public void addActivePower(Power p) {
        this.activePowers.add(p);
    }

    /**
     * Adds Rat to Tile
     *
     * @param r Rat to add to Tile
     */
    public void addOccupantRat(Rat r) {
        this.occupantRats.add(r);
    }


    /**
     * Removes Power from Tile
     *
     * @param p Power to remove from Tile
     */
    public void removeActivePower(Power p) {
        this.activePowers.remove(p);
    }

    /**
     * Removes Rat from Tile
     *
     * @param r Rat to remove from Tile
     */
    public void removeOccupantRat(Rat r) {
        this.occupantRats.remove(r);
    }

    /**
     * update
     * <p>
     * actively updates the powers/rats on a tile per tick
     */
    public void update() {
        int numOfPowers = activePowers.size();
        int numOfRats = occupantRats.size();

        for (int i = 0; i < numOfPowers; i++) {
            activePowers.get(i).onTick(occupantRats, this);
            if (activePowers.size() == numOfPowers) {
                Power power = activePowers.get(i);
                if (power instanceof MaleSwapper || power instanceof FemaleSwapper || power instanceof Poison) {
                    if (numOfRats > 0) {
                        activePowers.get(i).activate(occupantRats, this);
                    }
                }
            }
            numOfRats = occupantRats.size();
            numOfPowers = activePowers.size();
        }

        numOfRats = occupantRats.size();
        for (int i = 0; i < numOfRats; i++) {
            occupantRats.get(i).onTick();
            numOfRats = occupantRats.size();
        }
    }

    /**
     * Returns the Rats on the requested Tile
     *
     * @return The rats on a tile
     */
    public ArrayList<Rat> getOccupantRats() {
        return occupantRats;
    }

    /**
     * Returns the active Powers on the requested Tile
     *
     * @return The powers on a tile
     */
    public ArrayList<Power> getActivePowers() {
        return activePowers;
    }

    /**
     * Returns whether players can place items on the Tile.
     *
     * @return interactivity.
     */
    public boolean isInteractive() {
        return false;
    }
}