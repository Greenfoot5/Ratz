import java.util.ArrayList;

public class Poison extends Power{

    //This class kills all rats that are passed to it.

    public static void killRats(ArrayList<Rat> rats) {
        for (int i : rats){
            rats.get(i).die;
        }
    }

    @Override
    void activate(ArrayList<Rat> rats) {
        killRats(rats);
    }
}
