import java.util.ArrayList;

public class Sterilisation extends Power{

    Sterilisation(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    public static void main (String[] args) {

    }

    private static void startTimer() {
        long startTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        //To-Do
    }

    @Override
    void activate(ArrayList<Rat> rats) {
        for(int i : rats) {
            rats.get(i).setFertile(false);
        }
    }
    
}