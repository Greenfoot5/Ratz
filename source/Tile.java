import java.util.ArrayList;

/**
 * Abstract class that defines what a tile even is anyway
 * @author Alexander Douglas Lloyd-Ward
 */

public class Tile extends GameObject {
    private boolean isPassable;
    private boolean canRatMove;
    private ArrayList<Power> activePowers = new ArrayList<Power>();
    private ArrayList<LivingRat> occupantRats = new ArrayList<LivingRat>();

    public Tile(boolean isPassable, boolean canRatMove, ArrayList<Power> activePowers, ArrayList<LivingRat> occupantRats) {
        super(false, isPassable);
        this.canRatMove = canRatMove;
        this.activePowers = activePowers;
        this.occupantRats = occupantRats;
    }

    public void setCanRatMove(boolean canRatMove) {
        this.canRatMove = canRatMove;
    }
    public Tile(boolean isPassable, boolean canRatMove) {
        super(false, isPassable);
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

