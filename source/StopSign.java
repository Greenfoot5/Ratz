import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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

    @Override
    public void draw(int x, int y, GraphicsContext g) {
        x = GameObject.getWIDTH()* x;
        y = GameObject.getWIDTH() * y;

        String path = "file:" + getTextureFolder() + "/power" + HP + ".png";

        g.drawImage(new Image(path),x,y);
    }
}
