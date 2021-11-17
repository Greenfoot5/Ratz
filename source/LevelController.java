import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Class that implements a playable level.
 */

//HOW TO INITIALIZE FROM EXTERNAL CLASS:
    //loader = new FXMLLoader(getClass().getResource("level.fxml"));
    //LevelController levelController = new LevelController(--level file reader instance goes here--);
    //
    //loader.setController(levelController); (and then do all the pane and scene stuff)

//TODO:
    //Fix sizing of game canvas and overall window
    //Implement giving new powers
    //Make code for canvas receiving item dropping shorter (enums/Item class ??) (toolbar + image + counter in one thing)
    //Add powers to level creation from level file

//Questions and notes for other classes:
    //How big are our levels (height and width)? Are they different?
    //Which class initializes LevelController?
    //How do rats and some powers know to tick()?
    //How often are items given (what do the numbers mean)?
    //What on gods green earth is the score?
    //Consensus on adding like 10 different powers on a tile (+ adding powers directly on top of a rat)
    //How are powers represented in a level file?
    //How to go back to MainMenuManager at the end of a level?
    //
    //Rats have to ask the government whether they are allowed to have babies!! (.canReproduce())
    //Stop Sing isInteractive = false (to avoid having to deal with someone putting a death rat on a stop sign)

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
    private final Image BOMB_IMAGE = new Image("file:resources/bomb.png");
    private final Image GAS_IMAGE = new Image("file:resources/gas.png");
    private final Image STERILISATION_IMAGE = new Image("file:resources/sterilisation.png");
    private final Image POISON_IMAGE = new Image("file:resources/poison.png");
    private final Image MALE_SWAP_IMAGE = new Image("file:resources/maleswapper.png");
    private final Image FEMALE_SWAP_IMAGE = new Image("file:resources/femaleswapper.png");
    private final Image STOP_SIGN_IMAGE = new Image("file:resources/stopsign.png");
    private final Image DEATH_RAT_IMAGE = new Image("file:resources/deathrat.png");

    //Current level reader
    private final LevelFileReader LEVEL_READER;

    //Game timeline
    private Timeline tickTimeline;
    private final int FRAME_TIME = 500;
    private int currentTimeLeft;

    public Label timerLabel;

    private final int WIDTH;
    private final int HEIGHT;

    //Game map
    private Tile[][] tileMap = new Tile[0][0];

    //Rat counters
    private int femaleRatCounter;
    private int maleRatCounter;
    private int childRatCounter;
    public Label femaleRatCounterLabel;
    public Label maleRatCounterLabel;

    //Item counters
    private int bombCounter = 4;
    private int gasCounter = 4;
    private int sterilisationCounter = 4;
    private int poisonCounter = 4;
    private int maleSwapCounter = 4;
    private int femaleSwapCounter = 4;
    private int stopSignCounter = 4;
    private int deathRatCounter = 4;

    private final int MAX_RATS;
    private final int PAR_TIME;
    private final int[] DROP_RATES;

    /**
     * Constructor for LevelController class.
     * @param fileReader instance of LevelFileReader that the level will be loaded from.
     */
    public LevelController (LevelFileReader fileReader) {
        LEVEL_READER = fileReader;
        WIDTH = LEVEL_READER.getWidth();
        HEIGHT = LEVEL_READER.getHeight();

        buildNewLevel();

        MAX_RATS = LEVEL_READER.getMaxRats();
        PAR_TIME = LEVEL_READER.getParTime();
        DROP_RATES = LEVEL_READER.getDropRates();
    }

    /**
     * Initializes game.
     */
    public void initialize() {
        currentTimeLeft = PAR_TIME;
        timerLabel.setText(millisToString(currentTimeLeft));

        renderAllItems();
        setupCanvasDragBehaviour();

        renderGame();

        renderCounters();

        tickTimeline = new Timeline(new KeyFrame(Duration.millis(FRAME_TIME), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();

    }

    /**
     * Builds new level from level file.
     */
    private void buildNewLevel() {

        tileMap = new Tile[WIDTH][HEIGHT];
        String tileString = LEVEL_READER.getTiles();
        populateTileMap(tileString);

        femaleRatCounter = 0;
        maleRatCounter = 0;
        childRatCounter = 0;

        String ratString = LEVEL_READER.getRatSpawns();
        addRatsToTileMap(ratString);
    }

    private void addRatsToTileMap(String rats) {
        int charPos = -1;
        for(int i = 0; i < HEIGHT ; i++) {
            for (int j = 0; j < WIDTH; j++) {
                charPos ++;
                if(rats.charAt(charPos) != '-'){
                    switch (rats.charAt(charPos)) {
                        case 'f':
                            tileMap[j][i].addRat(new AdultFemale());
                            femaleRatCounter++;
                            break;
                        case 'm':
                            tileMap[j][i].addRat(new AdultMale());
                            maleRatCounter++;
                            break;
                        case 'c':
                            tileMap[j][i].addRat(new ChildRat());
                            childRatCounter++;
                    }
                }
            }
        }
    }

    private void populateTileMap(String tiles) {
        int charPos = -1;
        for(int i = 0; i < HEIGHT ; i++) {
            for (int j = 0; j < WIDTH; j++) {
                charPos ++;
                switch (tiles.charAt(charPos)) {
                    case 'G':
                        tileMap[j][i] = new Grass(1);
                        break;
                    case 'P':
                        tileMap[j][i] = new Path(1);
                        break;
                    case 'T':
                        tileMap[j][i] = new Tunnel(1);
                }
            }
        }
    }

    public void renderCounters() {
        String mc = String.valueOf(maleRatCounter);
        String fc = String.valueOf(femaleRatCounter);

        maleRatCounterLabel.setText(mc);
        femaleRatCounterLabel.setText(fc);
    }


    private boolean tileInteractivityAt(double x, double y) {
        if (x >= (WIDTH*64) || y >= (HEIGHT*64)){
            return false;
        } else {
            int xPos = (int) (Math.floor(x) / 64);
            int yPos = (int) (Math.floor(y) / 64);

            return tileMap[xPos][yPos].isInteractive();
        }
    }

    /**
     * Converts milliseconds (as int) to a string of format mm:ss.
     * @param millis milliseconds as int.
     * @return String mm:ss.
     */
    public String millisToString(int millis) {
        int seconds = millis/1000;
        int minutes = (int) TimeUnit.SECONDS.toMinutes(seconds);
        int remainSeconds = seconds - (int) TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, remainSeconds);
    }

    /**
     * Periodically refreshes game screen.
     */
    public void tick() {
        if ((childRatCounter + femaleRatCounter + maleRatCounter) == 0) {
            endGame(true);
        } else if (currentTimeLeft <= 0){
            endGame(false);
        } else {
            //TELL EVERYTHING TO UPDATE ONCE HERE

            renderGame();
            renderCounters();

            currentTimeLeft = currentTimeLeft - FRAME_TIME;
            timerLabel.setText(millisToString(currentTimeLeft));
        }
    }

    private void endGame(boolean wonGame) {
        tickTimeline.stop();
        //disableToolbars();
        GraphicsContext gc = levelCanvas.getGraphicsContext2D();
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,levelCanvas.getWidth(),levelCanvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        if(wonGame) {
            gc.fillText("You've won! :)",levelCanvas.getWidth()/2,levelCanvas.getHeight()/2);
        } else {
            gc.fillText("You've lost! :(",levelCanvas.getWidth()/2,levelCanvas.getHeight()/2);
        }

        saveAndExitButton.setText("Back to Main Menu");

        saveAndExitButton.setOnAction( e -> {
            exitGame();
        });

    }

    private void exitGame() {
        levelCanvas.getGraphicsContext2D().drawImage(POISON_IMAGE,0,0);
    }

    /**
     * Lets interactive tiles on the game screen accept drag and drop events.
     */
    private void setupCanvasDragBehaviour() {

        levelCanvas.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // Mark the drag as acceptable if the source is a draggable ImageView
                if (event.getGestureSource() instanceof ImageView ) {
                    if (tileInteractivityAt(event.getSceneX(), event.getSceneY()))
                        event.acceptTransferModes(TransferMode.ANY);
                    event.consume();
                }
            }
        });

        // This code allows the canvas to react to a dragged object when it is finally dropped.
        levelCanvas.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getDragboard().getString() == "bomb" ) {
                    itemDropped(event, new Bomb());
                    event.consume();
                    bombCounter--;
                    renderItem(bombToolbar,BOMB_IMAGE,bombCounter,"bomb");
                }
                if (event.getDragboard().getString() == "gas" ) {
                    itemDropped(event, new Gas());
                    event.consume();
                    gasCounter--;
                    renderItem(gasToolbar,GAS_IMAGE,gasCounter,"gas");
                }
                if (event.getDragboard().getString() == "sterilisation" ) {
                    itemDropped(event, new Sterilisation());
                    event.consume();
                    sterilisationCounter--;
                    renderItem(sterilisationToolbar,STERILISATION_IMAGE,sterilisationCounter,"sterilisation");
                }
                if (event.getDragboard().getString() == "poison" ) {
                    itemDropped(event, new Poison());
                    event.consume();
                    poisonCounter--;
                    renderItem(poisonToolbar,POISON_IMAGE,poisonCounter,"poison");
                }
                if (event.getDragboard().getString() == "maleswap" ) {
                    itemDropped(event, new MaleSwapper());
                    event.consume();
                    maleSwapCounter--;
                    renderItem(maleSwapToolbar,MALE_SWAP_IMAGE,maleSwapCounter,"maleswap");
                }
                if (event.getDragboard().getString() == "femaleswap" ) {
                    itemDropped(event, new FemaleSwapper());
                    event.consume();
                    femaleSwapCounter--;
                    renderItem(femaleSwapToolbar,FEMALE_SWAP_IMAGE,femaleSwapCounter,"femaleswap");
                }
                if (event.getDragboard().getString() == "stopsign" ) {
                    itemDropped(event, new StopSign());
                    event.consume();
                    stopSignCounter--;
                    renderItem(stopSignToolbar,STOP_SIGN_IMAGE,stopSignCounter,"stopsign");
                }
                if (event.getDragboard().getString() == "deathrat" ) {
                    itemDropped(event, new DeathRat());
                    event.consume();
                    deathRatCounter--;
                    renderItem(deathRatToolbar,DEATH_RAT_IMAGE,deathRatCounter,"deathrat");
                }
            }
        });
    }

    /**
     * Renders current tile map.
     */
    private void renderGame() {
        GraphicsContext gc = levelCanvas.getGraphicsContext2D();
        if (tileMap != null) {
            for (int i = 0; i < tileMap.length; i++) {
                for (int j = 0; j < tileMap[i].length; j++) {
                    tileMap[i][j].draw(i,j,gc);
                }
            }
        }
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
     * @param p dropped item.
     */
    private void itemDropped(DragEvent event, Power p) {
        int x = (int) event.getX() / 64;
        int y = (int) event.getY() / 64;

        tileMap[x][y].addPower(p);

        // Draw an icon at the dropped location.
        //GraphicsContext gc = levelCanvas.getGraphicsContext2D();
        // Draw the the image so the top-left corner is where we dropped.
        //gc.drawImage(itemImage, x, y);
    }

    /**
     * Actions performed when user decides to save the game and exit to main menu.
     */
    @FXML
    public void saveAndExit() {
        tickTimeline.stop();
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
    private static void makeDraggable( final ImageView item, String dbContent ) {
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

    /**
     * Decides whether rats can reproduce (the number of rats doesn't exceed maximum).
     * @return can rats reproduce.
     */
    public boolean canReproduce() {
        boolean canReproduce = (femaleRatCounter + maleRatCounter + childRatCounter) < MAX_RATS;
        return canReproduce;
    }

    /**
     * Removes rat from a rat counter.
     * @param rat the rat being removed.
     */
    public void ratRemoved(LivingRat rat) {
        if (rat instanceof ChildRat) {
            childRatCounter--;
        } else if (rat instanceof AdultMale) {
            maleRatCounter--;
        } else if (rat instanceof AdultFemale) {
            femaleRatCounter--;
        }
    }

    /**
     * Adds rat to rat counter.
     * @param rat the rat being added.
     */
    public void ratAdded(LivingRat rat) {
        if (rat instanceof ChildRat) {
            childRatCounter++;
        } else if (rat instanceof AdultMale) {
            maleRatCounter++;
        } else if (rat instanceof AdultFemale) {
            femaleRatCounter++;
        }
    }
}
