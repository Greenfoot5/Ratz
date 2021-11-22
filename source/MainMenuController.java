import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        Button playButton = new Button("Play!");
        Label outputLabel = new Label("I am a label");
        root.getChildren().addAll(title, playButton, outputLabel);
        // Handle a button event
        playButton.setOnAction(event -> outputLabel.setText("I was Clicked"));

        // Create a scene based on the pane.
        Scene scene = new Scene(root, 400, 400);

        // Show the scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Displays the level select screen
     */
    private void displayLevels() {}

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
