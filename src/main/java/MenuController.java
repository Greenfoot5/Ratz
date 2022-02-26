import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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

	private static final int PROFILES_LIMIT = 10;
	private static final int CUSTOM_LEVELS_LIMIT = 10;
	// private static final String delfaultLevelRegex = "level-[1-5]";
	// private static final String savedGameStringPart = "inProgress";
	private static Stage stage;
	private static Scene scene;
	private Parent root;
	private static String selectedLevelName = "";
	private static String selectedEditLevelName = "";
	private static boolean menuViewUpdated = false;
	private static boolean profilesViewUpdated = false;
	private static boolean levelsViewUpdated = false;
	private static boolean levelsCreationViewUpdated = false;
	private final static String PART_OF_BUTTON_NAME = "styleClass=button]'";
	private static final int LENGHT_OF_FIXED_PART_OF_BUTTON_NAME = 19;
	private static final int MAX_WIDTH_CREATION = 390;
	private static final int MAX_HEIGHT_CREATION = 350;
	private static final int MAX_WIDTH_SELECTION = 180;
	private static final int MAX_HEIGHT_SELECTION = 160;
	private static final double MENU_BUTTON1_WIDTH = 155;
	private static final double MENU_BUTTON1_HEIGHT = 30;
	private static final String MENU_BUTTON1_ID = "menu-button1";
	private static final String NO_LOGGED_PROFILE_LABEL = "...";
	private static final String SELECTED_RADIO_BUTTON_STYLE = "-fx-background-image: url('gui/selected-radio-button.png')";
	private static final String NOT_SELECTED_RADIO_BUTTON_STYLE = "-fx-background-image: url('gui/radio-button.png')";
	private static final String WINDOW_TITLE = "Ratz";
	private static final double WINDOW_WIDTH = 800;
	private static final double WINDOW_HEIGHT = 500;
	private static final String LETTERS_AND_NUMBERS_REGEX = "[a-zA-Z0-9]*";
	private static final String SAVED_GAMES_IMAGES_PATH = "saved_games_images\\";
	private static final String LEVELS_IMAGES = "levels_images\\";
	private static final String RESOURCES_PATH = "src\\main\\resources\\";
	private static final String SELECT_LEVEL_LABEL = "Select level";

	@FXML
	private VBox menuRoot;
	@FXML
	private BorderPane profileSelectionRoot;
	@FXML
	private BorderPane levelsSelectionRoot;
	@FXML
	private BorderPane levelCreationRoot;
	@FXML
	private ImageView levelView;
	@FXML
	private ImageView levelViewSelection;
	@FXML
	private Button deleteLevelButton;
	@FXML
	private Button deleteSavedGameButton;
	@FXML
	private RadioButton editCustomLevelsRadioButton;
	@FXML
	private RadioButton editDefaultLevelsRadioButton;
	@FXML
	private VBox levelsButtonsLevelCreationVBox;
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
	private Label selectedLevelHeadingLabel;
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
	 * Method initialize initial state of each scene.
	 */
	@FXML
	private void initialize() {
		if (this.profileSelectionRoot != null) {
			updateProfilesView();
		} else if (this.menuRoot != null) {
			updateMenuView();
		} else if (this.levelsSelectionRoot != null) {
			defaultLevelsRadioButton.getStyleClass().remove("radio-button");
			defaultLevelsRadioButton.getStyleClass().add("toggle-button");
			selectedLevelHeadingLabel.setAlignment(Pos.CENTER);
			updateLevelsView();
		} else if (this.levelCreationRoot != null) {
			updateLevelCreationView();
		}
	}

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
		// File f = new File("target/classes/menu.css");
		// scene.getStylesheets().clear();
		// scene.getStylesheets().add("file://" + f.getAbsolutePath().replace("\\",
		// "/"));

		window.setScene(scene);
		window.setResizable(false);
		window.showAndWait();
	}

	/**
	 * Changes scene to start menu.
	 * 
	 * @param event button event
	 * @throws IOException if fxml file is missing
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
			if (ProfileFileReader.getLoggedProfile() != null) {
				loggedProfileMenuLabel.setText("Welcome " + ProfileFileReader.getLoggedProfile());
			} else {
				loggedProfileMenuLabel.setText("You are not logged in. Please select profile");
			}
		}
	}

	/**
	 * Loads level creation menu.
	 * 
	 * @param event button event
	 * @throws IOException if fxml file is missing
	 */
	public void changeToLevelCreation(ActionEvent event) throws IOException {
		levelsCreationViewUpdated = false;
		System.out.println("moved to level creation");

		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("levelCreation.fxml")));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Changes scene to level selection.
	 * 
	 * @param event button event
	 * @throws IOException if fxml file is missing
	 */
	public void changeToLevelSelection(ActionEvent event) throws IOException {
		if (ProfileFileReader.getLoggedProfile() != null) {
			levelsViewUpdated = false;
			System.out.println("moved to level selection");

			root = FXMLLoader.load(getClass().getResource("levelsSelection.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else {
			alert("You need to log in");
		}
	}

	/**
	 * Changes screen to profile selection.
	 * 
	 * @param event button event
	 * @throws IOException if fxml file is missing
	 */
	public void changeToProfileSelection(ActionEvent event) throws IOException {
		profilesViewUpdated = false;
		System.out.println("moved to profile selection");

		Parent root = FXMLLoader.load(getClass().getResource("profileSelection.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	// profiles code

	/**
	 * Adds profile to database and the screen.
	 */
	@FXML
	void addProfile() {
		try {
			if (profileButtons.getChildren().size() > PROFILES_LIMIT) {
				// Check we don't have too many profiles already
				alert("Too many profiles!");
			} else if (newProfileTextField.getText().contains(" ")) {
				alert("Profile name can not contins spaces");
			} else if (!newProfileTextField.getText().matches(LETTERS_AND_NUMBERS_REGEX)) {
				alert("Only letters and numbers allowed");
			} else if (!newProfileTextField.getText().equals("")
					&& !ProfileFileReader.doesProfileExist(newProfileTextField.getText())) {
				// Check there's at least something in the text box
				// and the profiles doesn't already exist
				ProfileFileReader.createNewProfile(newProfileTextField.getText());
				ProfileFileReader.loginProfile(newProfileTextField.getText());

				newProfileTextField.setText("");
				profilesViewUpdated = false;
				this.updateProfilesView();

			} else if (!newProfileTextField.getText().equals("")) {
				// If the profile already exists
				alert("Profile already exists");
			} else {
				// There's nothing in the text box
				alert("Please, type a name");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Delete logged profile from database and screen.
	 */
	@FXML
	void removeProfile() {
		if (ProfileFileReader.getLoggedProfile() == null) {
			alert("No profile is selected");
		} else {
			ProfileFileReader.deleteProfile(ProfileFileReader.getLoggedProfile());
			ProfileFileReader.logout();
			HighScores.deleteProfile(ProfileFileReader.getLoggedProfile());

			profilesViewUpdated = false;
			this.updateProfilesView();
		}
	}

	/**
	 * Update screen. Adds buttons, logged profile label, and best scores.
	 */
	public void updateProfilesView() {
		if (!profilesViewUpdated) {
			profilesViewUpdated = true;

			updateProfilesScoreTable();

			String[] s = { "" };
			s = ProfileFileReader.getProfiles();

			profileButtons.getChildren().clear();
			// Display a button for each profile
			Button[] profButton = new Button[s.length];

			for (int i = 0; i < profButton.length; i++) {
				profButton[i] = new Button(s[i]);
				profButton[i].setMaxSize(MENU_BUTTON1_WIDTH, MENU_BUTTON1_HEIGHT);
				profButton[i].setMinSize(MENU_BUTTON1_WIDTH, MENU_BUTTON1_HEIGHT);
				profButton[i].setId(MENU_BUTTON1_ID);

				profileButtons.getChildren().add(profButton[i]);

				final int buttonIndex = i;

				// Adds the action for each button
				profButton[i].setOnAction(event -> {
					ProfileFileReader.loginProfile(profButton[buttonIndex].getText());
					profilesViewUpdated = false;
					updateProfilesView();
				});
			}

			if (ProfileFileReader.getLoggedProfile() != null) {
				loggedProfileLabel.setText(ProfileFileReader.getLoggedProfile());
			} else {
				loggedProfileLabel.setText(NO_LOGGED_PROFILE_LABEL);
			}
		}
	}

	/**
	 * Updates scores in profiles menu.
	 */
	public void updateProfilesScoreTable() {
		ArrayList<String> levelNames = ProfileFileReader.getDeafaultLevelsNames();
		profileScoresVBox.getChildren().clear();

		for (String lvl : levelNames) {
			Label scoreLabel = new Label(
					lvl + " " + ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), lvl));
			profileScoresVBox.getChildren().add(scoreLabel);
		}
	}

	////////////////////////////////////////////////////////////////////// levels

	/**
	 * Change background image of radio buttons. First argument will have selected
	 * radio button texture, other two not selected.
	 * 
	 * @param r1 radio button to be selected
	 * @param r2 radio button to be not selected
	 * @param r3 radio button to be not selected
	 */
	public void selectRadioButton(RadioButton r1, RadioButton r2, RadioButton r3) {
		r1.setStyle(SELECTED_RADIO_BUTTON_STYLE);
		r2.setStyle(NOT_SELECTED_RADIO_BUTTON_STYLE);
		r3.setStyle(NOT_SELECTED_RADIO_BUTTON_STYLE);
	}

	/**
	 * Updates buttons and score table in level selection menu.
	 */
	public void updateLevelsView() {
		if (!levelsViewUpdated) {
			levelsViewUpdated = true;

			levelButtonsVBox.getChildren().clear();
			levelViewSelection.setImage(null);
			ArrayList<String> levelNames = null;
			Button[] levelButtons;

			// Checking which radio button is selected
			// to choose type of levels to display
			if (defaultLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReader.getDeafaultLevelsNames();
				selectRadioButton(defaultLevelsRadioButton, createdLevelsRadioButton, savedGamesRadioButton);
				deleteSavedGameButton.setDisable(true);
			} else if (createdLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReader.getCreatedLevelsNames();
				selectRadioButton(createdLevelsRadioButton, savedGamesRadioButton, defaultLevelsRadioButton);
				deleteSavedGameButton.setDisable(true);
			} else if (savedGamesRadioButton.isSelected()) {
				levelNames = ProfileFileReader.getSavedGamesNames(ProfileFileReader.getLoggedProfile());
				selectRadioButton(savedGamesRadioButton, defaultLevelsRadioButton, createdLevelsRadioButton);
				deleteSavedGameButton.setDisable(false);
			}

			levelButtons = new Button[levelNames.size()];

			for (int i = 0; i < levelNames.size(); i++) {
				// Creating a button for each level
				levelButtons[i] = new Button(levelNames.get(i));
				levelButtons[i].setMaxSize(MENU_BUTTON1_WIDTH, MENU_BUTTON1_HEIGHT);
				levelButtons[i].setMinSize(MENU_BUTTON1_WIDTH, MENU_BUTTON1_HEIGHT);
				levelButtons[i].setId(MENU_BUTTON1_ID);

				levelButtonsVBox.getChildren().add(levelButtons[i]);

				final int buttonIndex = i;

				levelButtons[i].setOnAction(event -> {
					// attaching action to each button
					selectedLevelHeadingLabel.setText(levelButtons[buttonIndex].getText());
					selectedLevelName = levelButtons[buttonIndex].getText();
					updateScoreTableLevels();

					levelViewSelection.setImage(getPreview(selectedLevelName, savedGamesRadioButton.isSelected(),
							MAX_WIDTH_SELECTION, MAX_HEIGHT_SELECTION));

				});
			}
		}
	}

	/**
	 * Make a level preview.
	 * @param levelName	name of the level to display preview
	 * @param savedGame	true if level is of saved game type (in progress), false otherwise
	 * @param maxWidth	max width of preview
	 * @param maxHeight	max height of preview
	 * @return	image of preview with chosen width and height, null in case of missing png file 	
	 */
	public Image getPreview(String levelName, boolean savedGame, int maxWidth, int maxHeight) {
		Image img = null;
		try {
			String folderPath = "";
			if (savedGame) {
				folderPath = SAVED_GAMES_IMAGES_PATH + ProfileFileReader.getLoggedProfile() + "\\";
			} else {
				folderPath = LEVELS_IMAGES;
			}

			File f = new File(RESOURCES_PATH + folderPath + levelName + ".png");

			if (f.exists()) {

				Image tempImg = new Image(
						new FileInputStream(RESOURCES_PATH + folderPath + levelName + ".png"));

				int width = (int) tempImg.getWidth();
				int height = (int) tempImg.getHeight();
				float widthCompare = (float) maxWidth / (float) width;
				float heightComare = (float) maxHeight / (float) height;

				if (widthCompare < heightComare) {
					width *= widthCompare;
					height *= widthCompare;
				} else {
					width *= heightComare;
					height *= heightComare;
				}

				img = new Image(new FileInputStream(RESOURCES_PATH + folderPath + levelName + ".png"), width,
						height, false, false);
			} else {
				img = null;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Screenshot loading error");
		}

		return img;
	}

	/*
	 * Deletes selected saved game and connected screenshot.
	 */
	public void deleteSavedGame() {
		File fileToDelete = new File(RESOURCES_PATH + "levels/saved_games/" + ProfileFileReader.getLoggedProfile()
				+ "/" + selectedLevelName + ".txt");
		fileToDelete.delete();

		File imageToDelete = new File(RESOURCES_PATH + SAVED_GAMES_IMAGES_PATH + ProfileFileReader.getLoggedProfile()
				+ "/" + selectedLevelName + ".png");
		imageToDelete.delete();

		selectedLevelName = "";
		levelsViewUpdated = false;
		updateLevelsView();
	}

	/**
	 * Updates score table in level selection menu.
	 */
	public void updateScoreTableLevels() {
		scoreTableLevelsVBox.getChildren().clear();

		String[] scores = HighScores.getTopScores(selectedLevelName);
		if (scores != null) {
			for (String score : scores) {
				Label scrLabel = new Label(score);
				scoreTableLevelsVBox.getChildren().add(scrLabel);
			}
		} else {
			scoreTableLevelsVBox.getChildren().add(new Label("Complete level to see score table"));
		}
	}

	/**
	 * Change type of level buttons displayed.
	 */
	public void levelTypeChanged() {
		levelsViewUpdated = false;
		selectedLevelName = "";
		selectedLevelHeadingLabel.setText(SELECT_LEVEL_LABEL);
		updateLevelsView();
	}

	/**
	 * Calls function loading the game.
	 * 
	 * @param event
	 */
	@FXML
	void playTheGame() {
		try {
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
		String levelType = "";

		if (defaultLevelsRadioButton.isSelected()) {
			levelType = "default_levels/";
			LevelFileReader.loadNormalLevelFile("src/main/resources/levels/" + levelType + selectedLevelName, true);
		} else if (createdLevelsRadioButton.isSelected()) {
			levelType = "created_levels/";
			LevelFileReader.loadNormalLevelFile("src/main/resources/levels/" + levelType + selectedLevelName, true);
		} else if (savedGamesRadioButton.isSelected()) {
			levelType = "saved_games/" + ProfileFileReader.getLoggedProfile() + "/";
			LevelFileReader.loadSavedLevelFile("src/main/resources/levels/" + levelType + selectedLevelName);
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("level.fxml"));
		LevelController levelController = new LevelController(selectedLevelName, this);

		loader.setController(levelController);
		Pane root = loader.load();

		scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
		stage.setScene(scene);
	}

	/**
	 * Called when a level is finished.
	 */
	public void finishLevel() {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("menu2.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		stage.setTitle(WINDOW_TITLE);
		scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}

	// level creation code

	/**
	 * Updates buttons and score table in level creation menu.
	 */
	@FXML
	public void updateLevelCreationView() {
		if (!levelsCreationViewUpdated) {
			levelsCreationViewUpdated = true;

			levelsButtonsLevelCreationVBox.getChildren().clear();
			levelView.setImage(null);

			ArrayList<String> levelNames = null;
			Button[] levelButtons;

			// Checking which radio button is selected
			// to display level buttons
			if (editDefaultLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReader.getDeafaultLevelsNames();
				selectRadioButton(editDefaultLevelsRadioButton, editCustomLevelsRadioButton,
						editCustomLevelsRadioButton);
			} else if (editCustomLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReader.getCreatedLevelsNames();
				selectRadioButton(editCustomLevelsRadioButton, editDefaultLevelsRadioButton,
						editDefaultLevelsRadioButton);
			}

			levelButtons = new Button[levelNames.size()];

			for (int i = 0; i < levelNames.size(); i++) {
				levelButtons[i] = new Button(levelNames.get(i));
				levelButtons[i].setMaxSize(MENU_BUTTON1_WIDTH, MENU_BUTTON1_HEIGHT);
				levelButtons[i].setMinSize(MENU_BUTTON1_WIDTH, MENU_BUTTON1_HEIGHT);
				levelButtons[i].setId(MENU_BUTTON1_ID);

				levelsButtonsLevelCreationVBox.getChildren().add(levelButtons[i]);

				final int buttonIndex = i;

				levelButtons[i].setOnAction(event -> {
					selectedEditLevelName = levelButtons[buttonIndex].getText();
					levelView.setImage(
							getPreview(selectedEditLevelName, false, MAX_WIDTH_CREATION, MAX_HEIGHT_CREATION));
				});
			}
		}
	}

	/**
	 * Runs level editor with loaded selected level.
	 * 
	 * @param event button event
	 * @throws IOException if files are missing
	 */
	public void editCreatedLevel(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("editor.fxml"));

		if (editDefaultLevelsRadioButton.isSelected()) {
			LevelFileReader.loadNormalLevelFile("src/main/resources/levels/default_levels/" + selectedEditLevelName,
					true);
		} else if (editCustomLevelsRadioButton.isSelected()) {
			LevelFileReader.loadNormalLevelFile("src/main/resources/levels/created_levels/" + selectedEditLevelName,
					true);
		}

		EditorController editorController = new EditorController(selectedEditLevelName, this);
		loader.setController(editorController);
		Pane root = loader.load();

		scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Deletes created level file and screenshot from memory and form screen. Also
	 * deletes all in progress files which use selected level as reference. //TODO:
	 * actually do that
	 * 
	 * @param event
	 */
	public void deleteCreatedLevel(ActionEvent event) {
		// TODO delete all in progress files which use this level
		LevelFileReader.deleteAllConnectedFiles("src/main/resources/levels/created_levels/" + selectedEditLevelName);
		
		File tempFile = new File("src/main/resources/levels/created_levels/" + selectedEditLevelName + ".txt");
		tempFile.delete();

		levelView.getImage().cancel();
		
		//TODO: fix deleting png
		File tempImage = new File("src/main/resources/levels_images/" + selectedEditLevelName + ".png");
		tempImage.deleteOnExit();
		System.out.println(tempImage.exists());
		tempImage.delete();
		System.out.println(tempImage.exists());

		ProfileFileReader.deleteLevel(selectedEditLevelName);
		HighScores.deleteLevel(selectedEditLevelName);
		levelsCreationViewUpdated = false;
		updateLevelCreationView();
	}

	/**
	 * Disable/unlock delete level button.
	 * 
	 * @param event
	 */
	public void editLevelTypeChanged(ActionEvent event) {
		if (editDefaultLevelsRadioButton.isSelected()) {
			deleteLevelButton.setDisable(true);
		} else if (editCustomLevelsRadioButton.isSelected()) {
			deleteLevelButton.setDisable(false);
		}
		levelsCreationViewUpdated = false;
		updateLevelCreationView();
	}

	/**
	 * Runs level editor without any map loaded.
	 * 
	 * @throws IOException if fxml file is missing
	 */
	public void openLevelEditor() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("editor.fxml"));
		EditorController editorController = new EditorController(this);

		loader.setController(editorController);
		Pane root = loader.load();

		scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Close the window and save necessary data to text files.
	 */
	public void exitTheGame() {
		try {
			ProfileFileReader.saveDataToFile();
			HighScores.saveDataToFile();
		} catch (IOException e) {
			// TODO give an alert
			e.printStackTrace();
		}
		System.out.println("stage closing");
		Menu.getStage().close();
	}
}
