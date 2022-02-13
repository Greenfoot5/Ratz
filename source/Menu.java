import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The main class to begin, and the one that handles the main menu
 *
 * @author Tomaszs Fijalkowski
 * @version 1.0
 */
public class Menu extends Application{
	
	Stage rootStage = null;
	
	public static void main(String[] args) {
        launch(args);
    }
	
	public void start(Stage primaryStage) throws Exception {
		
		HighScoresV2.loadData();
		ProfileFileReaderV2.loadData();
		Parent root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		if (rootStage == null) {
			rootStage = primaryStage;
		}
		rootStage.setTitle("asd");
		rootStage.setScene(new Scene(root, 800, 500));
		rootStage.show();
	}
	
	
}
