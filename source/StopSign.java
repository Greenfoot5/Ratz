import java.util.ArrayList;

public class StopSign extends Power{

    private int HP = 5;

    StopSign(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
        //TODO Auto-generated constructor stub
    }

    @Override
    void activate(ArrayList<Rat> rats) {
        HP = HP - 1;
    }
    

    public int getHP() {
        return HP;
    }
}
