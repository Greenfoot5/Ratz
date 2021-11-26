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
        for(Rat rat : rats) {
            // TODO - rat.setFertile(false);
        }
    }

    /**
     * Abstract method for certain powers that need to activate after a
     * certain amount of time.
     */

    @Override
    void onTick() {

    }

}