import javafx.application.Application;
import javafx.stage.Stage;

public class MainMenuManager extends Application {
    // Profiles
    private String selectedProfile = null;

    // The dimensions of the window
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 400;

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
     * @throws Exception /shrug
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO - Implement LevelManager stuff
        //loader = new FXMLLoader(getClass().getResource("level.fxml"));
        //LevelController levelController = new LevelController(--level file reader instance goes here--);
        //
        //loader.setController(levelController);
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
