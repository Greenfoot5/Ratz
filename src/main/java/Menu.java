import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The main class to begin, and the one that handles the main menu
 *
 * @author Tomaszs Fijalkowski
 * @version 1.0
 */
public class Menu{
	
	Stage rootStage = null;
	Scene scene;
	public static void main(String[] args) {
        // Creating a File object for directory
        File directoryPath = new File("");
        System.out.println(directoryPath.getAbsolutePath());

        // List of all files and directories
        String[] contents = directoryPath.list();
        ArrayList<String> levels = new ArrayList<>();

        if (contents == null || contents.length == 0)
            System.out.println("Empty");
        else {
            for (String content : contents) {
                System.out.println(content);
                levels.add(content.substring(0, content.length() - 4));
            }
        }

        //launch(args);
    }
	
//	public void start(Stage primaryStage) throws Exception {
//        for (String levelName : ProfileFileReader.getLevelNames()) {
//            System.out.println(levelName);
//        }
//
//		//HighScores.loadData();
//		//ProfileFileReader.loadData();
//		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu2.fxml")));
//		if (rootStage == null) {
//			rootStage = primaryStage;
//		}
//		rootStage.setTitle("asd");
//		scene = new Scene(root, 800, 500);
//		rootStage.setScene(scene);
//		rootStage.show();
//
//		rootStage.setOnCloseRequest(event -> {
//		    System.out.println("Stage is closing");
//
//		    // TODO: uncomment it when we want to save data
////			try {
////				ProfileFileReader.saveDataToFile();
////				HighScores.saveDataToFile();
////			} catch (IOException e) {
////				//TODO do give an alert
////				e.printStackTrace();
////			}
//		});
//	}
	
	public void finishLevel() {
		rootStage.setScene(scene);
		rootStage.show();
	}
	
}
