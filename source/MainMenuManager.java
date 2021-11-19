import javafx.application.Application;
import javafx.stage.Stage;

public class MainMenuManager extends Application {
    // The dimensions of the window
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 400;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO - Implement LevelManager stuff
        //loader = new FXMLLoader(getClass().getResource("level.fxml"));
        //LevelController levelController = new LevelController(--level file reader instance goes here--);
        //
        //loader.setController(levelController);
    }

    private void displayLevels() {}
}
