import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Class that implements a playable level.
 */

//FOR OTHER CLASSES:

    //NOT SURE WHO:
        //ratKilled() and ratRemoved() are two different things:
            //ratKilled() is for when a rat is killed by a power,
            //ratRemoved() is for when one instance of a rat is changed for another (gender swap, growing up).

    //RATS:
        //Rats have to ask the government whether they are allowed to have babies! (.canReproduce()).

    //FILE READER:
        //How are multiple rats/powers on one tile and pregnant rats(+num of babies) represented?

//TODO:
    //Add powers to level creation from level file
    //Implement game saving

public class LevelController {

    private static final int ITEM_NUM = 8;

    //Game map
    private static Tile[][] tileMap = new Tile[0][0];

    //TODO: Change to images from an actual class
    //Images for different game items
    private final List<Image> itemImages = Arrays.asList(new Image("file:resources/bomb.png"),new Image("file:resources/gas.png"),
            new Image("file:resources/sterilisation.png"), new Image("file:resources/poison.png"),
            new Image("file:resources/maleswapper.png"),new Image("file:resources/femaleswapper.png"),
            new Image("file:resources/stopsign.png"),new Image("file:resources/deathrat.png"));
    private final int[] counters = new int[ITEM_NUM];

    private final int WIDTH;
    private final int HEIGHT;

    private final int MAX_RATS;
    private final int PAR_TIME;

    private final int[] DROP_RATES;
    private final int[] timeUntilDrop = new int [ITEM_NUM];

    //Current level reader
    private final LevelFileReader LEVEL_READER;
    private final MainMenuController MAIN_MENU;
    private final ProfileFileReader PROFILE_READER;

    //Milliseconds between frames
    private final int FRAME_TIME = 500;

    //Item toolbars
    private List<HBox> toolbars;

    //Game timeline
    private Timeline tickTimeline;
    private int currentTimeLeft;

    private static int score;

    //Rat counters
    private static int femaleRatCounter;
    private static int maleRatCounter;
    private static int childRatCounter;

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

    public Label timerLabel;
    public Label femaleRatCounterLabel;
    public Label maleRatCounterLabel;

    public Pane gameEndPane;
    public TextFlow gamePaneText;
    public TextFlow gamePaneScore;

    /**
     * Constructor for LevelController class.
     * @param fileReader instance of LevelFileReader that the level will be loaded from.
     */
    public LevelController (LevelFileReader fileReader, MainMenuController mainMenuController, ProfileFileReader profileFileReader) {
        LEVEL_READER = fileReader;
        MAIN_MENU = mainMenuController;
        PROFILE_READER = profileFileReader;
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
        //currentTimeLeft = PAR_TIME;
        timerLabel.setText(millisToString(currentTimeLeft));

        toolbars = Arrays.asList(bombToolbar,gasToolbar, sterilisationToolbar, poisonToolbar, maleSwapToolbar, femaleSwapToolbar, stopSignToolbar, deathRatToolbar);
        Arrays.fill(counters, 0);

        renderAllItems();
        setupCanvasDragBehaviour();

        renderGame();

        //System.arraycopy(DROP_RATES, 0, timeUntilDrop, 0, timeUntilDrop.length);

        tickTimeline = new Timeline(new KeyFrame(Duration.millis(FRAME_TIME), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
    }

    /**
     * Builds new level from level file.
     */
    private void buildNewLevel() {

        femaleRatCounter = 0;
        maleRatCounter = 0;
        childRatCounter = 0;
        score = 0;

        //tileMap = new Tile[WIDTH][HEIGHT];
        //tileMap = LevelLoader.loadTileMap(LEVEL_READER,this);
    }

    /**
     * Renders female and male counters on the screen.
     */
    public void renderCounters() {
        String mc = String.valueOf(maleRatCounter);
        String fc = String.valueOf(femaleRatCounter);

        maleRatCounterLabel.setText(mc);
        femaleRatCounterLabel.setText(fc);
    }

    public static Tile getTileAt(int x, int y) {
        return tileMap[x][y];
    }

    /**
     * Returns whether tile at position (x,y) on canvas can be interacted with.
     * @param x canvas x position.
     * @param y canvas y position.
     * @return interactivity.
     */
    private boolean tileInteractivityAt(double x, double y) {
        //if (x >= (WIDTH*64) || y >= (HEIGHT*64)){
        //    return false;
        //} else {
            int xPos = (int) (Math.floor(x) / 64);
            int yPos = (int) (Math.floor(y) / 64);

            return tileMap[xPos][yPos].isInteractive();
        //}
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
        if ((femaleRatCounter + maleRatCounter + childRatCounter) == 0) {
            endGame(true);
        } else if (currentTimeLeft <= 0){
            endGame(false);
        } else {
            addPowers();

            for (Tile[] tiles : tileMap) {
                for (Tile tile : tiles) {
                    tile.update(FRAME_TIME);
                }
            }

            renderGame();
            renderCounters();
            renderAllItems();

            currentTimeLeft = currentTimeLeft - FRAME_TIME;
            timerLabel.setText(millisToString(currentTimeLeft));
        }
    }

    private void addPowers() {
        for (int i = 0; i < counters.length; i++) {
            timeUntilDrop[i] -= FRAME_TIME;
            if(timeUntilDrop[i] <= 0 && counters[i] < 4) {
                counters[i]++;
                //timeUntilDrop[i] = DROP_RATES[i];
            }
        }
    }

    /**
     * Ends game in a win/lose scenario.
     * @param wonGame whether level was won.
     */
    private void endGame(boolean wonGame) {
        tickTimeline.stop();
        disableToolbars();

        gameEndPane.setVisible(true);
        saveAndExitButton.setVisible(false);

        if(wonGame) {
            gamePaneText.getChildren().add(new Text("You've won! :)"));
            score += currentTimeLeft/1000;
            gamePaneScore.getChildren().add(new Text("Score: " + score));
            //TODO: PROFILE_READER.saveBestScore(score);
        } else {
            gamePaneText.getChildren().add(new Text("You've lost! :("));
        }
    }

    /**
     * Exits level and goes back to main menu.
     */
    @FXML
    private void exitGame() {
        //TODO: MAIN_MENU.finishedLevel();
        levelCanvas.getGraphicsContext2D().drawImage(itemImages.get(4),0,0);
    }

    /**
     * Lets interactive tiles on the game screen accept drag and drop events.
     */
    private void setupCanvasDragBehaviour() {

        levelCanvas.setOnDragOver(event -> {
            // Mark the drag as acceptable if the source is a draggable ImageView
            if (event.getGestureSource() instanceof ImageView ) {
                if (tileInteractivityAt(event.getSceneX(), event.getSceneY()))
                    event.acceptTransferModes(TransferMode.ANY);
                event.consume();
            }
        });

        // This code allows the canvas to react to a dragged object when it is finally dropped.
        levelCanvas.setOnDragDropped(event -> {
            String dbContent = event.getDragboard().getString();
            try {
                int index=Integer.parseInt(dbContent);
                if(index >= 0 && index < ITEM_NUM) {
                    itemDropped(event, index);
                }
            }
            catch( Exception ignored) {
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
        for (int i = 0; i < counters.length ; i++) {
            renderItem(i);
        }
    }

    /**
     * Draws dropped item to screen.
     * @param event Drag and drop event.
     */
    private void itemDropped(DragEvent event, int index) {
        int x = (int) event.getX() / 64;
        int y = (int) event.getY() / 64;

        Power power = null;
        boolean addPower = true;
        switch(index) {
            case 0:
                power = new Bomb(x, y);
                break;
            case 1:
                power = new Gas(x, y);
                break;
            case 2:
                power = new Sterilisation(x, y);
                break;
            case 3:
                power = new Poison(x, y);
                break;
            case 4:
                power = new MaleSwapper(x, y);
                break;
            case 5:
                power = new FemaleSwapper(x, y);
                break;
            case 6:
                power = new StopSign(x, y);
                break;
            case 7:
                //tileMap[x][y].addOccupantRat(new DeathRat);
                addPower = false;
                break;
            default: addPower = false;
        }
        if(addPower) {
            tileMap[x][y].addActivePower(power);
        }

        counters[index]--;
        renderItem(index);
    }

    /**
     * Actions performed when user decides to save the game and exit to main menu.
     */
    @FXML
    public void saveAndExit() {
        tickTimeline.stop();
        GraphicsContext gc = levelCanvas.getGraphicsContext2D();

        gc.drawImage(itemImages.get(2),0,0);

        //SAVE GAME HERE

        exitGame();
    }

    /**
     * Renders toolbar of one specific item type.
     */
    private void renderItem(int index) {
        toolbars.get(index).getChildren().clear();

        ImageView[] items = new ImageView[counters[index]];
        for (int i=0; i < counters[index]; i++) {
            ImageView item = new ImageView(itemImages.get(index));
            items[i] = item;
            toolbars.get(index).getChildren().add(items[i]);
            makeDraggable(item,index);
        }
    }

    private void disableToolbars(){
        for(int i=0; i < toolbars.size(); i++) {
            toolbars.get(i).getChildren().clear();

            ImageView[] items = new ImageView[counters[i]];
            for (int j=0; j < counters[i]; j++) {
                ImageView item = new ImageView(itemImages.get(i));
                items[j] = item;
                toolbars.get(i).getChildren().add(items[i]);
            }
        }
    }

    /**
     * Makes ImageView draggable.
     * @param item item that is being made draggable.
     * @param dbContent String used in setupCanvasDragBehaviour.
     */
    private static void makeDraggable( final ImageView item, int dbContent ) {
        item.setOnDragDetected(event -> {
            Dragboard dragboard = item.startDragAndDrop( TransferMode.MOVE );
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(String.valueOf(dbContent));
            dragboard.setContent( clipboardContent );
            event.consume();
        });
    }

    /**
     * Decides whether rats can reproduce (the number of rats doesn't exceed maximum).
     * @return can rats reproduce.
     */
    public boolean canReproduce() {
        return (femaleRatCounter + maleRatCounter) < MAX_RATS;
    }

    /**
     * Removes rat from a rat counter.
     * @param rat the rat being removed.
     */
    public static void ratRemoved(LivingRat rat) {
        if (rat instanceof AdultMale) {
            maleRatCounter--;
        } else if (rat instanceof AdultFemale) {
            femaleRatCounter--;
        } else if (rat instanceof ChildRat) {
            childRatCounter--;
        }
    }

    /**
     * Adds rat to rat counter.
     * @param rat the rat being added.
     */
    public static void ratAdded(LivingRat rat) {
        if (rat instanceof ChildRat) {
            childRatCounter++;
        } else if (rat instanceof AdultMale) {
            maleRatCounter++;
        } else if (rat instanceof AdultFemale) {
            femaleRatCounter++;
        }
    }

    /**
     * Removes rat that has been killed from rat counter and adds to score.
     * @param rat rat that has been killed.
     */
    public static void ratKilled(Rat rat) {
        if(rat instanceof AdultFemale) {
            //if (((AdultFemale) rat).isPregnant()) {
                score += 10;
                //TODO: Figure out how many babies a pregnant rat has and +10 for each baby (combo kill)
            //}
            femaleRatCounter--;
        } else if(rat instanceof AdultMale) {
            score += 10;
            maleRatCounter--;
        } else if(rat instanceof ChildRat) {
            score += 10;
            childRatCounter--;
        }
    }
}
