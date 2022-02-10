import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Menu extends Application{
	
	Stage rootStage = null;
	
	public static void main(String[] args) {
        launch(args);
    }
	
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		if (rootStage == null) {
			rootStage = primaryStage;
		}
		rootStage.setTitle("asd");
		rootStage.setScene(new Scene(root, 800, 500));
		rootStage.show();
	}
	
	
}
