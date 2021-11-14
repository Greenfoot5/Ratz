import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * Class that implements a playable level.
 */

//TODO:
    //How does LevelController get an instance of LevelFileReader?
    //Initialize level map
    //Change drop action from drawing to actually adding a power onto a tile in tileMap
    //Fix game canvas sizing
    //How do rats and some powers know to tick() ?
    //tick() exit clauses (both rat counters == 0 or time ended (game won/lost)) and save and exit
    //Implement time tracking for ability to lose level
    //Implement giving new powers (how often does this happen?)
    //Don't let rats have babies (ban sex) if rat counter too high
    //Implement rat counter (visual + methods)
    //Make code for canvas receiving item dropping shorter (enums/Item class ??) (toolbar + image + counter in one thing)
    //Don't allow item drop on anything that isn't a path

public class LevelController {
    @FXML
    public Canvas levelCanvas; //Game map canvas

    public Button saveAndExitButton;

    //Toolbars for different game items
    public HBox bombToolbar;
    public HBox gasToolbar;
    public HBox sterilisationToolbar;
    public HBox poisonToolbar;
    public HBox maleSwapToolbar;
    public HBox femaleSwapToolbar;
    public HBox stopSignToolbar;
    public HBox deathRatToolbar;

    //Images for different game items
    private final Image BOMB_IMAGE = new Image("file:res/bomb.png");
    private final Image GAS_IMAGE = new Image("file:res/gas.png");
    private final Image STERILISATION_IMAGE = new Image("file:res/sterilisation.png");
    private final Image POISON_IMAGE = new Image("file:res/poison.png");
    private final Image MALE_SWAP_IMAGE = new Image("file:res/maleswapper.png");
    private final Image FEMALE_SWAP_IMAGE = new Image("file:res/femaleswapper.png");
    private final Image STOP_SIGN_IMAGE = new Image("file:res/stopsign.png");
    private final Image DEATH_RAT_IMAGE = new Image("file:res/deathrat.png");

    //Game timeline
    private Timeline tickTimeline;

    //Game map
    private Array<Tile>[][] tileMap;

    //Rat counters
    private int femaleRatCounter;
    private int maleRatCounter;

    //Item counters
    private int bombCounter = 4;
    private int gasCounter = 4;
    private int sterilisationCounter = 4;
    private int poisonCounter = 4;
    private int maleSwapCounter = 4;
    private int femaleSwapCounter = 4;
    private int stopSignCounter = 4;
    private int deathRatCounter = 4;

    /**
     * Initializes game.
     */
    public void initialize() {
        renderAllItems();
        setupCanvasDragBehaviour();

        //render new game map here!!

        tickTimeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
    }

    /**
     * Lets game screen accept drag and drop events.
     */
    private void setupCanvasDragBehaviour() {

        levelCanvas.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // Mark the drag as acceptable if the source is a draggable ImageView
                if (event.getGestureSource() instanceof ImageView ) {
                    event.acceptTransferModes(TransferMode.ANY);
                    event.consume();
                }
            }
        });

        // This code allows the canvas to react to a dragged object when it is finally dropped.
        levelCanvas.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getDragboard().getString() == "bomb" ) {
                    itemDropped(event, BOMB_IMAGE);
                    event.consume();
                    bombCounter--;
                    renderItem(bombToolbar,BOMB_IMAGE,bombCounter,"bomb");
                }
                if (event.getDragboard().getString() == "gas" ) {
                    itemDropped(event, GAS_IMAGE);
                    event.consume();
                    gasCounter--;
                    renderItem(gasToolbar,GAS_IMAGE,gasCounter,"gas");
                }
                if (event.getDragboard().getString() == "sterilisation" ) {
                    itemDropped(event, STERILISATION_IMAGE);
                    event.consume();
                    sterilisationCounter--;
                    renderItem(sterilisationToolbar,STERILISATION_IMAGE,sterilisationCounter,"sterilisation");
                }
                if (event.getDragboard().getString() == "poison" ) {
                    itemDropped(event, POISON_IMAGE);
                    event.consume();
                    poisonCounter--;
                    renderItem(poisonToolbar,POISON_IMAGE,poisonCounter,"poison");
                }
                if (event.getDragboard().getString() == "maleswap" ) {
                    itemDropped(event, MALE_SWAP_IMAGE);
                    event.consume();
                    maleSwapCounter--;
                    renderItem(maleSwapToolbar,MALE_SWAP_IMAGE,maleSwapCounter,"maleswap");
                }
                if (event.getDragboard().getString() == "femaleswap" ) {
                    itemDropped(event, FEMALE_SWAP_IMAGE);
                    event.consume();
                    femaleSwapCounter--;
                    renderItem(femaleSwapToolbar,FEMALE_SWAP_IMAGE,femaleSwapCounter,"femaleswap");
                }
                if (event.getDragboard().getString() == "stopsign" ) {
                    itemDropped(event, STOP_SIGN_IMAGE);
                    event.consume();
                    stopSignCounter--;
                    renderItem(stopSignToolbar,STOP_SIGN_IMAGE,stopSignCounter,"stopsign");
                }
                if (event.getDragboard().getString() == "deathrat" ) {
                    itemDropped(event, DEATH_RAT_IMAGE);
                    event.consume();
                    deathRatCounter--;
                    renderItem(deathRatToolbar,DEATH_RAT_IMAGE,deathRatCounter,"deathrat");
                }

            }
        });
    }

    /**
     * Periodically refreshes game screen.
     */
    public void tick() {
        renderGame();
    }

    /**
     * Renders game screen.
     */
    private void renderGame() {

    }

    /**
     * Renders all item toolbars.
     */
    private void renderAllItems() {
        renderItem(gasToolbar,GAS_IMAGE,gasCounter,"gas");
        renderItem(bombToolbar,BOMB_IMAGE,bombCounter,"bomb");
        renderItem(sterilisationToolbar,STERILISATION_IMAGE,sterilisationCounter,"sterilisation");
        renderItem(poisonToolbar,POISON_IMAGE,poisonCounter,"poison");
        renderItem(maleSwapToolbar,MALE_SWAP_IMAGE,maleSwapCounter,"maleswap");
        renderItem(femaleSwapToolbar,FEMALE_SWAP_IMAGE,femaleSwapCounter,"femaleswap");
        renderItem(stopSignToolbar,STOP_SIGN_IMAGE,stopSignCounter,"stopsign");
        renderItem(deathRatToolbar,DEATH_RAT_IMAGE,deathRatCounter,"deathrat");
    }

    /**
     * Draws dropped item to screen.
     * @param event Drag and drop event.
     * @param itemImage Image of dropped item.
     */
    private void itemDropped(DragEvent event, Image itemImage) {
        double x = event.getX();
        double y = event.getY();

        // Draw an icon at the dropped location.
        GraphicsContext gc = levelCanvas.getGraphicsContext2D();
        // Draw the the image so the top-left corner is where we dropped.
        gc.drawImage(itemImage, x, y);
    }

    /**
     * Actions performed when user decides to save the game and exit to main menu.
     */
    @FXML
    public void saveAndExit() {
        GraphicsContext gc = levelCanvas.getGraphicsContext2D();

        gc.drawImage(STOP_SIGN_IMAGE,0,0);

    }

    /**
     * Renders toolbar of one specific item type.
     * @param toolbar toolbar being rendered.
     * @param itemImage image of item.
     * @param counter how many of that item is currently in inventory;
     * @param dbContent String used in setupCanvasDragBehaviour.
     */
    private void renderItem(HBox toolbar, Image itemImage, int counter, String dbContent) {
        toolbar.getChildren().clear();

        ImageView[] items = new ImageView[counter];
        for (int i=0; i<counter; i++) {
            ImageView item = new ImageView(itemImage);
            items[i] = item;
            toolbar.getChildren().add(items[i]);
            makeDraggable(item,dbContent);
        }
    }

    /**
     * Makes ImageView draggable.
     * @param item item that is being made draggable.
     * @param dbContent String used in setupCanvasDragBehaviour.
     */
    public static void makeDraggable( final ImageView item, String dbContent ) {
        item.setOnDragDetected( new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent event ) {
                Dragboard dragboard = item.startDragAndDrop( TransferMode.MOVE );
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(dbContent);
                dragboard.setContent( clipboardContent );
                event.consume();
            }
        } );
    }
}
