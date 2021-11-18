import java.lang.FdLibm.Pow;
import java.util.ArrayList;

public class Bomb extends Power {

    Bomb(boolean isInteractive, boolean isPassable) {
        super(isInteractive, isPassable);
    }

    private static void startTimer() {
        long startTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        //To-Do after 5s ?
    }

    @Override
    void activate(ArrayList<Rat> rats) {
        for (Rat r : rats) {
            r.die();
        }
    }
    
}
