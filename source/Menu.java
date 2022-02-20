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
	Scene scene;
	public static void main(String[] args) {
        launch(args);
    }
	
	public void start(Stage primaryStage) throws Exception {
		
		HighScores.loadData();
		ProfileFileReader.loadData();
		Parent root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		if (rootStage == null) {
			rootStage = primaryStage;
		}
		rootStage.setTitle("asd");
		scene = new Scene(root, 800, 500);
		rootStage.setScene(scene);
		rootStage.show();
		
		rootStage.setOnCloseRequest(event -> {
		    System.out.println("Stage is closing");

			try {
				ProfileFileReader.saveDataToFile();
				HighScores.saveDataToFile();
			} catch (IOException e) {
				//TODO do give an alert
				e.printStackTrace();
			}
		});
	}
	
	public void finishLevel() {
		rootStage.setScene(scene);
		rootStage.show();
	}
	
}
