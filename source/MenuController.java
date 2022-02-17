import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class to control main menu.
 * 
 * @author Tomasz Fijalkowski
 *
 */
public class MenuController {

	private static final int PROFILES_LIMIT = 8;
	private static final String delfaultLevelRegex = "level-[1-5]";
	private static final String savedGameStringPart = "inProgress";
	private static Stage stage;
	private static Scene scene;
	private Parent root;
	// private Integer selectedLevel = 1;
	private static String selectedLevelName = "";
	private static boolean profilesViewUpdated = false;
	private static boolean levelsViewUpdated = false;
	private final static int FIRST_LETTER_OF_BUTTON_NAME = 35;

	@FXML
	private RadioButton createdLevelsRadioButton;
	@FXML
	private RadioButton defaultLevelsRadioButton;
	@FXML
	private ToggleGroup levelTypeGroup;
	@FXML
	private RadioButton savedGamesRadioButton;
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
	private VBox levelButtonsVBox;
	@FXML
	private VBox profileScoresVBox;
	@FXML
	private Button removeProfileButton;

	/**
	 * Generates a popup alert
	 *
	 * @param message The message to display in the alert
	 */
	private static void alert(String message) {
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
	
	/**
	 * Changes scene to start menu.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void changeToMenu(ActionEvent event) throws IOException {
		System.out.println("move to menu");
		profilesViewUpdated = false;
		levelsViewUpdated = false;
		
		root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void changeToLevelCreation(ActionEvent event) throws IOException {
		System.out.println("move to level creation");
		root = FXMLLoader.load(getClass().getResource("levelCreation.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		System.out.println(stage == null);
		System.out.println(scene == null);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Changes scene to level selection.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void changeToLevelSelection(ActionEvent event) throws IOException {
		if (ProfileFileReaderV2.getLoggedProfile() != null) {
			System.out.println("move to level selection");
			root = FXMLLoader.load(getClass().getResource("levelsSelection.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			System.out.println(stage == null);
			System.out.println(scene == null);
			stage.setScene(scene);
			stage.show();
		} else {
			alert("You need to log in");
		}
	}

	/**
	 * Changes screen to profile selection.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void changeToProfileSelection(ActionEvent event) throws IOException {
		System.out.println("move to profile selection");

		Parent root = FXMLLoader.load(getClass().getResource("profileSelection.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	////////////////////////////////////////////////////////////////////////////////////// profiles
	/**
	 * Adds profile to database and the screen.
	 * 
	 * @param event
	 */
	@FXML
	void addProfile(ActionEvent event) {
		try {
			// Check we don't have too many profiles already
			if (profileButtons.getChildren().size() > PROFILES_LIMIT) {
				alert("Too many profiles!");
				// Check there's at least something in the text box
				// and the profiles doesn't already exist
			} else if (!newProfileTextField.getText().equals("")
					&& !ProfileFileReaderV2.doesProfileExist(newProfileTextField.getText())) {

				ProfileFileReaderV2.createNewProfile(newProfileTextField.getText());
				ProfileFileReaderV2.loginProfile(newProfileTextField.getText());

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

	/**
	 * Delete logged profile from database and screen.
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void removeProfile(ActionEvent event) throws Exception {
		try {

			if (ProfileFileReaderV2.getLoggedProfile() == null) {
				alert("No profile is selected");
			} else {
				ProfileFileReaderV2.deleteProfile(ProfileFileReaderV2.getLoggedProfile());
				ProfileFileReaderV2.logout();
				;
				HighScores.deleteProfile(ProfileFileReaderV2.getLoggedProfile());
				profilesViewUpdated = false;
				this.updateProfilesView();
			}

		} catch (IOException ignored) {
		}
	}

	/**
	 * Update screen. Adds buttons, logged profile label (not yet), and best
	 * scores(not yet).
	 * 
	 * @throws Exception
	 */
	public void updateProfilesView() {
		if (!profilesViewUpdated) {
			profilesViewUpdated = true;

			String[] s = { "" };
			s = ProfileFileReaderV2.getProfiles();

			profileButtons.getChildren().clear();
			// Display a button for each profile
			Button[] profButton = new Button[s.length];

			for (int i = 0; i < profButton.length; i++) {
				profButton[i] = new Button(s[i]);
				profButton[i].setPrefWidth(200);
				profileButtons.getChildren().add(profButton[i]);

				final int buttonIndex = i;
				// Adds the action for each button
				profButton[i].setOnAction(event -> {
					ProfileFileReaderV2.loginProfile(profButton[buttonIndex].getText());

				});
			}

		}

	}

	////////////////////////////////////////////////////////////////////// levels

	@FXML
	public void updateLevelsView() {
		System.out.print("-");
		if (!levelsViewUpdated) {
			levelsViewUpdated = true;

			levelButtonsVBox.getChildren().clear();
			ArrayList<String> levelNames = null;
			Button[] levelButtons;
			
			if (defaultLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReaderV2.getDeafaultLevelsNames();
			} else if (createdLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReaderV2.getCreatedLevelsNames();
			} else if (savedGamesRadioButton.isSelected()) {
				System.out.println(ProfileFileReaderV2.getLoggedProfile());
				levelNames = ProfileFileReaderV2.getSavedGamesNames(ProfileFileReaderV2.getLoggedProfile());
			}
			levelButtons = new Button[levelNames.size()];

			for (int i = 0; i < levelNames.size(); i++) {
				levelButtons[i] = new Button(levelNames.get(i));
				levelButtons[i].setPrefWidth(100);
				levelButtonsVBox.getChildren().add(levelButtons[i]);
				levelButtons[i].setOnAction(event -> {
					levelButtonPressed(event);
				});
			}
//			ArrayList<String> levelNames = ProfileFileReaderV2.getLevelNames();
//			Button[] levelButtons = new Button[levelNames.size()];
//
//			for (int i = 0; i < levelNames.size(); i++) {
//				levelButtons[i] = new Button(levelNames.get(i));
//				levelButtons[i].setPrefWidth(100);
//
//				if (defaultLevelsRadioButton.isSelected() && isDefaultLevel(levelNames.get(i))) {
//					levelButtonsVBox.getChildren().add(levelButtons[i]);
//				} else if (createdLevelsRadioButton.isSelected() && isCreatedLevel(levelNames.get(i))) {
//					levelButtonsVBox.getChildren().add(levelButtons[i]);
//				} else if (savedGamesRadioButton.isSelected() && isSavedGame(levelNames.get(i))) {
//					levelButtonsVBox.getChildren().add(levelButtons[i]);
//				}
//				levelButtons[i].setOnAction(event -> {
//					levelButtonPressed(event);
//				});
//			}
		}
	}

	public void levelTypeChanged() {
		levelsViewUpdated = false;
		selectedLevelName = "";
		updateLevelsView();
	}

	/**
	 * Get the text from a button.
	 * 
	 * @param event button pressed
	 * @return name of the button
	 * @throws Exception if source of action event wasn't button
	 */
	public static String getButtonName(ActionEvent event) throws Exception {
		String source = event.getSource().toString();
		if (!source.contains("[styleClass=button]")) {
			System.out.println(source);

			throw new Exception("Element is not a button");
		}
		String s = event.getSource().toString();
		// System.out.println(s.charAt(35));
		return s.substring(FIRST_LETTER_OF_BUTTON_NAME, s.length() - 1);
	}

	/**
	 * Change selected level to a level with the same name as text on the button.
	 * 
	 * @param event
	 */
	public static void levelButtonPressed(ActionEvent event) {
		try {
			System.out.println(getButtonName(event));
			selectedLevelName = getButtonName(event);
			System.out.println(selectedLevelName);

		} catch (Exception e) {
			alert("This level data is missing :(");
		}
	}


	/**
	 * Loads the game.
	 * 
	 * @param event
	 */
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
	// ************************************* Currently not in use
	// ****************************
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

	/**
	 * Loads the game.
	 * 
	 * @throws IOException
	 */
	private void loadLevel() throws IOException {
		System.out.println(stage == null);
		System.out.println(scene == null);
		LevelFileReader.loadLevelFile("./resources/levels/" + selectedLevelName);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("level.fxml"));
		LevelController levelController = new LevelController(selectedLevelName, this);

		loader.setController(levelController);

		Pane root = loader.load();

		scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

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

	public boolean isDefaultLevel(String levelName) {

		return levelName.matches(delfaultLevelRegex);
	}

	public boolean isSavedGame(String levelName) {

		return levelName.contains(savedGameStringPart) && levelName.contains(ProfileFileReaderV2.getLoggedProfile());
	}

	public boolean isCreatedLevel(String levelName) {
		return !isSavedGame(levelName) && !isDefaultLevel(levelName);
	}

}
