import java.util.ArrayList;

public class Gas extends Power{

    Gas(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    private static void spreadTimer() {
        long startTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        //To-Do after 5s ?
    }

    private static void dissapationTimer() {
        long startTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        //To-Do after 5s ?
    }

    @Override
    void activate(ArrayList<Rat> rats) {
        for (Rat r : rats) {
            r.incGasTimer();
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
