/**
 * Abstract class that defines what a tile even is anyway
 * @author Alexander Douglas Lloyd-Ward
 */

public class Tile extends GameObject {
    private boolean isPassable;
    private boolean canRatMove;
    private Arraylist<Power> activePowers = new ArrayList<Power>();
    private ArrayList<LivingRat> occupantRats = new ArrayList<LivingRat>();

    public Tile(boolean isPassable, boolean canRatMove, Arraylist<Power> activePowers, ArrayList<LivingRat> occupantRats) {
        this.isPassable = isPassable;
        this.canRatMove = canRatMove;
        this.activePowers = activePowers;
        this.occupantRats = occupantRats;
    }

    public void setCanRatMove(boolean canRatMove) {
        this.canRatMove = canRatMove;
    }
    public Tile(boolean canRatMove) {
        this.canRatMove = canRatMove;
    }
    public boolean isCanRatMove() {
        return canRatMove;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }
    public boolean isPassable() {
        return isPassable;
    }

}

