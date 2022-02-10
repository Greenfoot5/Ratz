import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private Integer selectedLevel = 1;
	private boolean profilesViewUpdated = false;

	@FXML private VBox profileButtons;
	

    @FXML
    public Label l1;

	public void buttonCl() {
		System.out.println("button clicked");
	}

	public void changeToLevelSelection(ActionEvent event) throws IOException {
		System.out.println("move to level selection");

		root = FXMLLoader.load(getClass().getResource("levelsSelection.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void changeToProfileSelection(ActionEvent event) throws IOException {
		System.out.println("move to profile selection");

		Parent root = FXMLLoader.load(getClass().getResource("profileSelection.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void changeToMenu(ActionEvent event) throws IOException {
		System.out.println("move to menu");

		root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

	public void updateProfilesView() {
		if (!this.profilesViewUpdated) {
			this.profilesViewUpdated = true;
			
			String[] s = {""};
	        try {
	            s = ProfileFileReader.getProfiles();
	        } catch (FileNotFoundException e) {
	            s[0] = "No profiles. Please Create a profile";
	        }

	        // Display a button for each profile
	        Button[] profButton = new Button[s.length];
	        for (int i = 0; i < profButton.length; i++) {
	            profButton[i] = new Button(s[i]);
	            profButton[i].setPrefWidth(100);
	            profileButtons.getChildren().add(profButton[i]);

	            final int buttonIndex = i;
	            // Adds the action for each button
	            profButton[i].setOnAction(event -> {
	                ProfileFileReader.loginProfile(profButton[buttonIndex].
	                        getText());
	            });
	        }
		} 

	}
}
