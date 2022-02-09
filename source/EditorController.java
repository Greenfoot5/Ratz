import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

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

    public HBox ratSpawnToolbar;

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
        setupDraggableSpawns();
        setupCanvasDrawing();
        setupCanvasDragBehaviour();
    }

    /**
     *
     */
    private void setupCanvasDragBehaviour() {

        levelCanvas.setOnDragOver(event -> {
            int x = (int) event.getX() / TILE_SIZE;
            int y = (int) event.getY() / TILE_SIZE;

            if (event.getGestureSource() instanceof ImageView) {
                if (x < width && x >=0 && y>=0 && y < height){ //if x and y are in the size of the tilemap
                    if(tileMap[x][y] instanceof Path) { //if the tile at (x,y) is a path
                        event.acceptTransferModes(TransferMode.ANY);
                    }
                }
                event.consume();
            }
        });

        // This code allows the canvas to react to a dragged object when it is finally dropped.
        levelCanvas.setOnDragDropped(event -> {
            String dbContent = event.getDragboard().getString();
            char ratType = dbContent.charAt(0);
            spawnDropped(event, ratType);
        });
    }

    private void spawnDropped(DragEvent event, char type) {
        int x = (int) event.getX() / TILE_SIZE;
        int y = (int) event.getY() / TILE_SIZE;

        if(tileMap[x][y].getOccupantRats().size() != 0) {
            tileMap[x][y].getOccupantRats().clear();
        }
        switch (type) {
            case 'm':
                tileMap[x][y].addOccupantRat(new AdultMale(1,Rat.Direction.NORTH,0,0,0,false));
                break;
            case 'f':
                tileMap[x][y].addOccupantRat(new AdultFemale(1,Rat.Direction.NORTH,0,0,0,false,0,0));
                break;
            case 'i':
                tileMap[x][y].addOccupantRat(new AdultIntersex(1,Rat.Direction.NORTH,0,0,0,false,0,0));
                break;
        }

        renderBoard();
    }
    /**
     *  Sets up ability to drag rat spawns onto tilemap.
     */
    private void setupDraggableSpawns() {
        AdultMale adultMale = new AdultMale(1,Rat.Direction.NORTH,0,0,0,false);
        AdultFemale adultFemale = new AdultFemale(1,Rat.Direction.NORTH,0,0,0,false,0,0);
        AdultIntersex adultIntersex = new AdultIntersex(1,Rat.Direction.NORTH,0,0,0,false,0,0);

        ImageView adultMaleImageView = new ImageView(adultMale.getImg());
        ImageView adultFemaleImageView = new ImageView(adultFemale.getImg());
        ImageView adultIntersexImageView = new ImageView(adultIntersex.getImg());
        ImageView deleteImageView = new ImageView(new Image("file:resources/delete.png"));

        ratSpawnToolbar.getChildren().add(adultMaleImageView);
        ratSpawnToolbar.getChildren().add(adultFemaleImageView);
        ratSpawnToolbar.getChildren().add(adultIntersexImageView);
        ratSpawnToolbar.getChildren().add(deleteImageView);

        makeDraggable(adultMaleImageView,'m');
        makeDraggable(adultFemaleImageView,'f');
        makeDraggable(adultIntersexImageView,'i');
        makeDraggable(deleteImageView,'d');
    }

    /**
     * Makes ImageView draggable onto tilemap.
     * @param item image view to be made draggable.
     * @param type type of item.
     */
    private static void makeDraggable(final ImageView item, char type) {
        item.setOnDragDetected(event -> {
            Dragboard dragboard = item.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(String.valueOf(type));
            dragboard.setContent(clipboardContent);
            event.consume();
        });
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
                    if(selectedTile instanceof Grass) {
                        tileMap[x][y] = new Grass();
                    } else if (selectedTile instanceof Path) {
                        tileMap[x][y] = new Path();
                    } else {
                        tileMap[x][y] = new Tunnel();
                    }
                    renderBoard();
                }
            }
        });
        levelCanvas.setOnMouseDragged( event -> {
            int x = (int) event.getX() / TILE_SIZE;
            int y = (int) event.getY() / TILE_SIZE;

            if (x < width && x >=0 && y>=0 && y < height){
                if((!(tileMap[x][y].getClass() == selectedTile.getClass()))){
                    if(selectedTile instanceof Grass) {
                        tileMap[x][y] = new Grass();
                    } else if (selectedTile instanceof Path) {
                        tileMap[x][y] = new Path();
                    } else {
                        tileMap[x][y] = new Tunnel();
                    }
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
