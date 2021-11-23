import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class to begin, and the one that handles the main menu
 */
public class MainMenuController extends Application {
    // The dimensions of the window
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 400;

    // Profiles
    private String selectedProfile = null;

    /**
     * Launches the application
     * @param args Don't do anything atm
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Called when we begin the application
     * @param primaryStage I forgot
     */
    @Override
    public void start(Stage primaryStage) {
        // Create a new pane to hold our GUI
        VBox root = new VBox();
        root.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a few GUI elements
        Label title = new Label("RATZ");
        // TODO - Display the motd
        Label motd = new Label("<MOTD>");
        Button playButton = new Button("Play!");

        root.getChildren().addAll(title, motd, playButton);
        // Handle a button event
        playButton.setOnAction(event -> loadLevelSelect(primaryStage));

        // Create a scene based on the pane.
        Scene scene = new Scene(root, 400, 400);

        // Show the scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Displays the level select screen
     */
    private void loadLevelSelect(Stage selectStage) {
        // Create a new pane to hold our GUI
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        // Create a few GUI elements
        Label title = new Label("Level Select");
        TilePane levels = new TilePane();
        levels.setAlignment(Pos.TOP_LEFT);

        // Handle the levels
        // TODO - Create one play button for every level
        Button playButton = new Button("Play!");
        playButton.setOnAction(event -> {
            try {
                loadLevel(selectStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        levels.getChildren().add(playButton);

        root.getChildren().addAll(title, levels);

        // Create a scene based on the pane.
        Scene scene = new Scene(root, 400, 400);

        // Show the scene
        selectStage.setScene(scene);
        selectStage.show();
    }

    public void loadLevel(Stage levelStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level.fxml"));
        LevelController levelController = new LevelController(new LevelFileReader());

        loader.setController(levelController);

        Pane root = loader.load();

        Scene scene = new Scene(root,root.getPrefWidth(),root.getPrefHeight());

        levelStage.setScene(scene);
    }

    /**
     * Allows the user to login to a profile
     * @param username The username to login to
     */
    public void login(String username) {
        ProfileFileReader reader = new ProfileFileReader();
        // TODO - Check if the profile exists
        reader.createNewProfile(username);
        selectedProfile = username;
    }

    /**
     * Logs the user out of an account.
     */
    public void logout() {
        selectedProfile = null;
    }
}
