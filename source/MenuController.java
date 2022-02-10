import java.io.IOException;

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

	public void buttonCl() {
		System.out.println("asdasd");
	}

	public void changeToLevelSelection(ActionEvent event) throws IOException {
		System.out.println("asdasd2222");

		Parent root = FXMLLoader.load(getClass().getResource("levelsS.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void changeToMenu(ActionEvent event) throws IOException {
		System.out.println("asdasd2222");

		Parent root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
