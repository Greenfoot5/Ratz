import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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
	// private static final String savedGameStringPart = "inProgress";
	private static Stage stage;
	private static Scene scene;
	private Parent root;
	private static String selectedLevelName = "";
	private static boolean menuViewUpdated = false;
	private static boolean profilesViewUpdated = false;
	private static boolean levelsViewUpdated = false;
	private static boolean levelsCreationViewUpdated = false;
	private final static String PART_OF_BUTTON_NAME = "[styleClass=button]'";
	private static final int LENGHT_OF_FIXED_PART_OF_BUTTON_NAME = 20;

	@FXML
	private RadioButton editCustomLevelsRadioButton;
	@FXML
	private RadioButton editDefaultLevelsRadioButton;
	@FXML
	private VBox levelsButtonsLevelCreationVBox;
	@FXML
	private TextField newLevelNameTextField;
	@FXML
	private VBox scoreTableLevelsVBox;
	@FXML
	private Label loggedProfileMenuLabel;
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
		menuViewUpdated = false;
		root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Updates menu with name of logged profile.
	 */
	public void updateMenuView() {
		if (!menuViewUpdated) {
			menuViewUpdated = true;
			if (ProfileFileReaderV2.getLoggedProfile() != null) {
				loggedProfileMenuLabel.setText("Welcome " + ProfileFileReaderV2.getLoggedProfile());
			} else {
				loggedProfileMenuLabel.setText("You are not logged in. Please select profile");

			}
		}
	}

	/**
	 * Loads level creation menu.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void changeToLevelCreation(ActionEvent event) throws IOException {
		levelsCreationViewUpdated = false;
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
			levelsViewUpdated = false;
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
		profilesViewUpdated = false;

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
	 * Update screen. Adds buttons, logged profile label, and best scores.
	 * 
	 * @throws Exception
	 */
	public void updateProfilesView() {
		if (!profilesViewUpdated) {
			profilesViewUpdated = true;

			updateProfilesScoreTable();

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
					System.out.print("button pressed lvl");
					profilesViewUpdated = false;
					updateProfilesView();

				});
			}

			if (ProfileFileReaderV2.getLoggedProfile() != null) {
				loggedProfileLabel.setText(ProfileFileReaderV2.getLoggedProfile());
				System.out.println("label set1");
			} else {
				loggedProfileLabel.setText("...");
				System.out.println("label set2");
			}

		}

	}

	/**
	 * Updates scores in profiles menu.
	 */
	public void updateProfilesScoreTable() {

		ArrayList<String> levelNames = ProfileFileReaderV2.getDeafaultLevelsNames();
		profileScoresVBox.getChildren().clear();
		for (String lvl : levelNames) {
			Label scoreLabel = new Label(
					lvl + " " + ProfileFileReaderV2.getBestScore(ProfileFileReaderV2.getLoggedProfile(), lvl));
			profileScoresVBox.getChildren().add(scoreLabel);

		}
	}

	////////////////////////////////////////////////////////////////////// levels

	/**
	 * Updates buttons and score table in level selection menu.
	 */
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
					updateScoreTableLevels();

				});
			}
		}
	}

	/**
	 * Updates score table in level selection menu.
	 */
	public void updateScoreTableLevels() {
		System.out.println(selectedLevelName + "asfhf");
		String[] scores = HighScoresV2.getTopScores(selectedLevelName);
		scoreTableLevelsVBox.getChildren().clear();

		for (String score : scores) {
			Label scrLabel = new Label(score);
			scoreTableLevelsVBox.getChildren().add(scrLabel);
		}

	}

	/**
	 * Change type of level buttons displayed.
	 */
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
		System.out.println(source);

		if (!source.contains(PART_OF_BUTTON_NAME)) {
			System.out.println(source);

			throw new Exception("Element is not a button");
		}

		int buttonNameBegginingIndex = source.indexOf(PART_OF_BUTTON_NAME) + LENGHT_OF_FIXED_PART_OF_BUTTON_NAME;
		System.out.println(buttonNameBegginingIndex);
		return source.substring(buttonNameBegginingIndex, source.length() - 1);
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

	/**
	 * Loads the game.
	 * 
	 * @throws IOException
	 */
	private void loadLevel() throws IOException {
		System.out.println(stage == null);
		System.out.println(scene == null);
		// :TODO fix loading games in progress
		String levelType = "";
		if (defaultLevelsRadioButton.isSelected()) {
			levelType = "default_levels/";
		} else if (createdLevelsRadioButton.isSelected()) {
			levelType = "created_levels/";
		} else if (savedGamesRadioButton.isSelected()) {
			System.out.println(ProfileFileReaderV2.getLoggedProfile());
			levelType = "saved_games/" + ProfileFileReaderV2.getLoggedProfile() + "/";
		}
		LevelFileReader.loadLevelFile("./resources/levels/" + levelType + selectedLevelName);

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
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		stage.setTitle("asd");
		scene = new Scene(root, 800, 500);
		stage.setScene(scene);
		stage.show();
	}

	///////////////////////////////////////////////////////////////////////////////////// level_creation

	@FXML
	public void updateLevelCreationView() {
		if (!levelsCreationViewUpdated) {
			levelsCreationViewUpdated = true;
			
			
			levelsButtonsLevelCreationVBox.getChildren().clear();
			ArrayList<String> levelNames = null;
			Button[] levelButtons;

			if (editDefaultLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReaderV2.getDeafaultLevelsNames();
			} else if (editCustomLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReaderV2.getCreatedLevelsNames();
			}
			
			levelButtons = new Button[levelNames.size()];

			for (int i = 0; i < levelNames.size(); i++) {
				levelButtons[i] = new Button(levelNames.get(i));
				levelButtons[i].setPrefWidth(100);
				levelsButtonsLevelCreationVBox.getChildren().add(levelButtons[i]);

				levelButtons[i].setOnAction(event -> {
					//TODO add code to edit/delete levels
					try {
						System.out.println(getButtonName(event));
					} catch (Exception e) {
						e.printStackTrace();
					}

				});
			}
		}
	}

	public void createLevel(ActionEvent event) {
		System.out.println("crt");
	}
	
	public void editCreatedLevel(ActionEvent event) {
		System.out.println("edit");
	}

	public void deleteCreatedLevel(ActionEvent event) {
		System.out.println("delete");
	}

	public void editLevelTypeChanged(ActionEvent event) {
		levelsCreationViewUpdated = false;
		updateLevelCreationView();
	}
}
