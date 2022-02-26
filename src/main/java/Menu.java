import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The main class to begin, and the one that handles the main menu
 *
 * @author Tomaszs Fijalkowski
 * @version 1.0
 */
public class Menu extends Application {
	
	public static Stage rootStage = null;
	Scene scene;
	public static void main(String[] args) {
		File tempFile = new File("src/main/resources/levels_images/" + "testlvl" + ".png");
		System.out.println(tempFile.exists());
		tempFile.delete();
		System.out.println(tempFile.exists());
        launch(args);
    }
	
	public void start(Stage primaryStage) throws Exception {
		HighScores.loadData();
		ProfileFileReader.loadData();
		
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu2.fxml")));
		if (rootStage == null) {
			rootStage = primaryStage;
		}
		rootStage.setTitle("asd");
		scene = new Scene(root, 800, 500);
		rootStage.setScene(scene);
		rootStage.show();

		rootStage.setOnCloseRequest(event -> {
		    System.out.println("Stage is closing");

		    // TODO: uncomment it when we want to save data
			try {
				ProfileFileReader.saveDataToFile();
				HighScores.saveDataToFile();
			} catch (IOException e) {
				//TODO do give an alert
				e.printStackTrace();
			}
		});
	}
	
	public static Stage getStage() {
		return rootStage;
	}
	
	public void finishLevel() {
		rootStage.setScene(scene);
		rootStage.show();
	}
	
}
