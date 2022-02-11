import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {

	private static Stage stage;
	private static Scene scene;
	private Parent root;
	//private Integer selectedLevel = 1;
	private static String selectedLevelName = "";
	private boolean profilesViewUpdated = false;
	private final int FIRST_LETTER_OF_BUTTON_NAME = 35;
	
	@FXML
	private Button addProfilebutton;
	@FXML
	private VBox bestScoresLabel;
	@FXML
	private Label l1;
	@FXML
	private Label loggedProfileLabel;
	@FXML
	private TextField newProfileTextField;
	@FXML
	private VBox profileButtons;
	@FXML
	private Button removeProfileButton;

	/**
	 * Generates a popup alert
	 *
	 * @param message The message to display in the alert
	 */
	private void alert(String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Alert");
		window.setWidth(200);
		window.setHeight(200);

		Label label = new Label(message);
		Button button = new Button("Ok");
		button.setPrefWidth(100);
		button.setOnAction(event -> window.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		File f = new File("source/menu.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		window.setScene(scene);
		window.setResizable(false);
		window.showAndWait();
	}

	public void changeToLevelSelection(ActionEvent event) throws IOException {
		System.out.println("move to level selection");
		root = FXMLLoader.load(getClass().getResource("levelsSelection.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		System.out.println(stage == null);
		System.out.println(scene == null);
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

	////////////////////////////////////////////////////////////////////////////////////// profiles
	@FXML
	void addProfile(ActionEvent event) {
		try {
			// Check we don't have too many profiles already
			if (profileButtons.getChildren().size() > 8) {
				alert("Too many profiles!");
				// Check there's at least something in the text box
				// and the profiles doesn't already exist
			} else if (!newProfileTextField.getText().equals("")
					&& !ProfileFileReader.doesProfileExist(newProfileTextField.getText())) {

				ProfileFileReader.createNewProfile(newProfileTextField.getText());
				ProfileFileReader.loginProfile(newProfileTextField.getText());

				newProfileTextField.setText("");
				profilesViewUpdated = false;
				this.updateProfilesView();

			}
			// If the profile already exists
			else if (!newProfileTextField.getText().equals("")) {
				alert("Profile already exists");
			}
			// There's nothing in the text box
			else {
				alert("Please, type a name");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@FXML
	void removeProfile(ActionEvent event) {
		try {

			if (ProfileFileReader.getLoggedProfile() == null) {
				alert("No profile is selected");
			} else {
				ProfileFileReader.deleteProfile(ProfileFileReader.getLoggedProfile());
				ProfileFileReader.logout();
				;
				HighScores.deleteProfile(ProfileFileReader.getLoggedProfile());
				profilesViewUpdated = false;
				this.updateProfilesView();
			}

		} catch (IOException ignored) {
		}
	}

	public void updateProfilesView() {
		if (!this.profilesViewUpdated) {
			this.profilesViewUpdated = true;

			String[] s = { "" };
			try {
				s = ProfileFileReader.getProfiles();
			} catch (FileNotFoundException e) {
				s[0] = "No profiles. Please Create a profile";
			}

			profileButtons.getChildren().clear();
			// Display a button for each profile
			Button[] profButton = new Button[s.length];

			for (int i = 0; i < profButton.length; i++) {
				profButton[i] = new Button(s[i]);
				profButton[i].setPrefWidth(100);
				profileButtons.getChildren().add(profButton[i]);

				final int buttonIndex = i;
				// Adds the action for each button
				profButton[i].setOnAction(event -> {
					ProfileFileReader.loginProfile(profButton[buttonIndex].getText());

				});
			}

		}

	}

	////////////////////////////////////////////////////////////////////// levels

	public String getButtonName(ActionEvent event) throws Exception {
		String source = event.getSource().toString();
		if (!source.contains("[styleClass=button]")) {
			System.out.println(source);

			throw new Exception("Element is not a button");
		} 
		String s = event.getSource().toString();
		//System.out.println(s.charAt(35));
		return s.substring(FIRST_LETTER_OF_BUTTON_NAME, s.length() - 1);
	}
	
	public void levelButtonPressed(ActionEvent event) {
		try {
			System.out.println(getButtonName(event));
			selectedLevelName = getButtonName(event);
			System.out.println(selectedLevelName);

		} catch (Exception e) {
			alert("This level data is missing :(");
		}
	}

	public void changeToMenu(ActionEvent event) throws IOException {
		System.out.println("move to menu");

		root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	void playTheGame(ActionEvent event) {
		try {
			System.out.println(event.getSource().toString());
			System.out.println(selectedLevelName);
			System.out.println(stage == null);
			System.out.println(scene == null);
            loadLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
     * Loads a level through the LevelController
     *
     * @param levelStage The stage
     * @throws IOException If we cannot load a level
     */
//    private void loadLevel(Stage levelStage, int levelNumber)
//            throws IOException {
//        LevelFileReader.loadLevelFile("./resources/levels/" + selectedLevelName);
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(
//                "level.fxml"));
//        LevelController levelController = new LevelController(levelNumber,
//                this);
//
//        loader.setController(levelController);
//
//        Pane root = loader.load();
//
//        scene = new Scene(root, root.getPrefWidth(),
//                root.getPrefHeight());
//
//        levelStage.setScene(scene);
//    }
    private void loadLevel()
            throws IOException {
    	System.out.println(stage == null);
		System.out.println(scene == null);
        LevelFileReader.loadLevelFile("./resources/levels/" + selectedLevelName);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "level.fxml"));
        LevelController levelController = new LevelController(selectedLevelName,
                this);

        loader.setController(levelController);

        Pane root = loader.load();

        scene = new Scene(root, root.getPrefWidth(),
                root.getPrefHeight());

        System.out.println(stage == null);
        System.out.println(scene == null);
        stage.setScene(scene);
    }
    
    /**
     * Called when a level is finished
     */
    public void finishLevel() {
        stage.setScene(scene);
        stage.show();
    }

}
