import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private Integer selectedLevel = 1;

	public void buttonCl() {
		System.out.println("button clicked");
	}

	public void changeToLevelSelection(ActionEvent event) throws IOException {
		System.out.println("move to level selection");

		Parent root = FXMLLoader.load(getClass().getResource("levelsSelection.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void changeToProfileSelection(ActionEvent event) throws IOException {
		System.out.println("move to profile selection");

		Parent root = FXMLLoader.load(getClass().getResource("profileSelection.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void changeToMenu(ActionEvent event) throws IOException {
		System.out.println("move to menu");

		Parent root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
