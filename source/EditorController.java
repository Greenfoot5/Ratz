import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EditorController {

    private static final int TILE_SIZE = 64;

    //Size of game map
    private int width;
    private int height;

    //Game losing conditions (maximum number of rats, time taken for a level)
    private int maxRats;
    private int parTime;

    //Time between item drops
    private int[] dropRates;

    private Tile selectedTile = new Grass();

    @FXML
    public Canvas levelCanvas;

    public Button saveLevelButton;

    @FXML
    public RadioButton rbGrass;
    public RadioButton rbPath;
    public RadioButton rbTunnel;

    //Level map
    private static Tile[][] tileMap = new Tile[0][0];

    public EditorController() {
        width = 10;
        height = 7;
        tileMap = new Tile[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tileMap[i][j] = new Grass();
            }
        }
        maxRats = 20;
        parTime = 150;
    }

    public void initialize() {
        renderBoard();
        setupRadioButtons();
        setupCanvasDrawing();
    }

    /**
     * Sets up the ability to draw selected tile onto tilemap.
     */
    private void setupCanvasDrawing() {
        levelCanvas.setOnMousePressed( event -> {
            int x = (int) event.getX() / TILE_SIZE;
            int y = (int) event.getY() / TILE_SIZE;

            if (x < width && x >=0 && y>=0 && y < height){
                if((!(tileMap[x][y].getClass() == selectedTile.getClass()))){
                    tileMap[x][y] = selectedTile;
                    renderBoard();
                }
            }
        });
        levelCanvas.setOnMouseDragged( event -> {
            int x = (int) event.getX() / TILE_SIZE;
            int y = (int) event.getY() / TILE_SIZE;

            if (x < width && x >=0 && y>=0 && y < height){
                if((!(tileMap[x][y].getClass() == selectedTile.getClass()))){
                    tileMap[x][y] = selectedTile;
                    renderBoard();
                }
            }
        });
    }

    /**
     * Sets up radio buttons for grass/path/tunnel selection.
     */
    private void setupRadioButtons() {
        final ToggleGroup tileRadioButtons = new ToggleGroup();

        rbGrass.setToggleGroup(tileRadioButtons);
        rbPath.setToggleGroup(tileRadioButtons);
        rbTunnel.setToggleGroup(tileRadioButtons);

        ImageView grassImageView = new ImageView(new Grass().getImg());
        ImageView pathImageView = new ImageView(new Path().getImg());
        ImageView tunnelImageView = new ImageView(new Tunnel().getImg());

        ImageView grassImageViewSelected = new ImageView(new Image("file:resources/grass_selected.png"));
        ImageView pathImageViewSelected = new ImageView(new Image("file:resources/path_selected.png"));
        ImageView tunnelImageViewSelected = new ImageView(new Image("file:resources/tunnel_selected.png"));

        rbGrass.setSelected(true);

        rbGrass.setGraphic(grassImageViewSelected);
        rbPath.setGraphic(pathImageView);
        rbTunnel.setGraphic(tunnelImageView);


        tileRadioButtons.selectedToggleProperty().addListener((ob, o, n) -> {
            if (rbGrass.isSelected()) {
                selectedTile = new Grass();
                rbGrass.setGraphic(grassImageViewSelected);
                rbPath.setGraphic(pathImageView);
                rbTunnel.setGraphic(tunnelImageView);
            } else if (rbPath.isSelected()) {
                selectedTile = new Path();
                rbGrass.setGraphic(grassImageView);
                rbPath.setGraphic(pathImageViewSelected);
                rbTunnel.setGraphic(tunnelImageView);
            } else if (rbTunnel.isSelected()) {
                selectedTile = new Tunnel();
                rbGrass.setGraphic(grassImageView);
                rbPath.setGraphic(pathImageView);
                rbTunnel.setGraphic(tunnelImageViewSelected);
            }
        });
    }

    /**
     * Renders tilemap onto window.
     */
    private void renderBoard() {
        GraphicsContext gc = levelCanvas.getGraphicsContext2D();
        if (tileMap != null) {
            for (int i = 0; i < tileMap.length; i++) {
                for (int j = 0; j < tileMap[i].length; j++) {
                    tileMap[i][j].draw(i, j, gc);
                }
            }
        }
    }

    /**
     * Saves level once "Save Level" button is pressed.
     */
    public void saveLevel() {
        System.out.println("Can't do that yet :(");
    }
}
