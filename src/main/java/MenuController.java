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
	private static final int MAX_WIDTH_CREATION = 420;
	private static final int MAX_HEIGHT_CREATION = 350;
	private static final int MAX_WIDTH_SELECTION = 200;
	private static final int MAX_HEIGHT_SELECTION = 160;

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

	@FXML
	private void initialize() {
		if (this.profileSelectionRoot != null) {
			updateProfilesView();
		} else if (this.menuRoot != null) {
			updateMenuView();
		} else if (this.levelsSelectionRoot != null) {
			defaultLevelsRadioButton.getStyleClass().remove("radio-button");
			defaultLevelsRadioButton.getStyleClass().add("toggle-button");
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
		//File f = new File("target/classes/menu.css");
		//scene.getStylesheets().clear();
		//scene.getStylesheets().add("file://" + f.getAbsolutePath().replace("\\", "/"));

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
	 * @param event
	 * @throws IOException
	 */
	public void changeToLevelCreation(ActionEvent event) throws IOException {
		levelsCreationViewUpdated = false;
		System.out.println("move to level creation");
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("levelCreation.fxml")));
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
		if (ProfileFileReader.getLoggedProfile() != null) {
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

	/**
	 * Delete logged profile from database and screen.
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void removeProfile(ActionEvent event) throws Exception {
		if (ProfileFileReader.getLoggedProfile() == null) {
			alert("No profile is selected");
		} else {
			HighScores.deleteProfile(ProfileFileReader.getLoggedProfile());
			ProfileFileReader.deleteProfile(ProfileFileReader.getLoggedProfile());
			ProfileFileReader.logout();
			;
			HighScores.deleteProfile(ProfileFileReader.getLoggedProfile());
			profilesViewUpdated = false;
			this.updateProfilesView();
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
			s = ProfileFileReader.getProfiles();

			profileButtons.getChildren().clear();
			// Display a button for each profile
			Button[] profButton = new Button[s.length];

			for (int i = 0; i < profButton.length; i++) {
				profButton[i] = new Button(s[i]);
				profButton[i].setMaxSize(155, 30);
				profButton[i].setMinSize(155, 30);
				profButton[i].setId("menu-button1");

				profileButtons.getChildren().add(profButton[i]);

				final int buttonIndex = i;
				// Adds the action for each button
				profButton[i].setOnAction(event -> {
					ProfileFileReader.loginProfile(profButton[buttonIndex].getText());
					System.out.print("button pressed lvl");
					profilesViewUpdated = false;
					updateProfilesView();

				});
			}

			if (ProfileFileReader.getLoggedProfile() != null) {
				loggedProfileLabel.setText(ProfileFileReader.getLoggedProfile());
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

		ArrayList<String> levelNames = ProfileFileReader.getDeafaultLevelsNames();
		profileScoresVBox.getChildren().clear();
		for (String lvl : levelNames) {
			Label scoreLabel = new Label(
					lvl + " " + ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), lvl));
			profileScoresVBox.getChildren().add(scoreLabel);

		}
	}

	////////////////////////////////////////////////////////////////////// levels

	public void selectRadioButton(RadioButton r1, RadioButton r2, RadioButton r3) {
		r1.setStyle("-fx-background-image: url('gui/selected-radio-button.png')");
		r2.setStyle("-fx-background-image: url('gui/radio-button.png')");
		r3.setStyle("-fx-background-image: url('gui/radio-button.png')");

	}

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
				levelNames = ProfileFileReader.getDeafaultLevelsNames();
				selectRadioButton(defaultLevelsRadioButton, createdLevelsRadioButton, savedGamesRadioButton);
			} else if (createdLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReader.getCreatedLevelsNames();
				selectRadioButton(createdLevelsRadioButton, savedGamesRadioButton, defaultLevelsRadioButton);
			} else if (savedGamesRadioButton.isSelected()) {
				levelNames = ProfileFileReader.getSavedGamesNames(ProfileFileReader.getLoggedProfile());
				selectRadioButton(savedGamesRadioButton, defaultLevelsRadioButton, createdLevelsRadioButton);
			}
			levelButtons = new Button[levelNames.size()];

			for (int i = 0; i < levelNames.size(); i++) {
				levelButtons[i] = new Button(levelNames.get(i));
				levelButtons[i].setMaxSize(155, 30);
				levelButtons[i].setMinSize(155, 30);
				levelButtons[i].setId("menu-button1");
				levelButtonsVBox.getChildren().add(levelButtons[i]);
				

				levelButtons[i].setOnAction(event -> {

					levelButtonPressed(event);
					updateScoreTableLevels();

					{
						// Preview display
						Image img = null;
						try {

							File f = new File("src\\main\\resources\\levels_images\\" + selectedLevelName + ".png");

							if (f.exists()) {

								Image tempImg = new Image(
										new FileInputStream("src\\main\\resources\\levels_images\\" + selectedLevelName + ".png"));

								int width = (int) tempImg.getWidth();
								int height = (int) tempImg.getHeight();
								float widthCompare = (float) MAX_WIDTH_SELECTION / (float) width;
								float heightComare = (float) MAX_HEIGHT_SELECTION / (float) height;
								if (widthCompare < heightComare) {
									width *= widthCompare;
									height *= widthCompare;
								} else {
									width *= heightComare;
									height *= heightComare;
								}
								img = new Image(
										new FileInputStream("src\\main\\resources\\levels_images\\" + selectedLevelName + ".png"),
										width, height, false, false);

								levelViewSelection.setImage(img);
							} else {
								// TODO: do something in case of missing file
							}

						} catch (FileNotFoundException e) {
							// levelView.
						}
					}

				});
			}
		}
	}

	/**
	 * Updates score table in level selection menu.
	 */
	public void updateScoreTableLevels() {
		System.out.println(selectedLevelName + "asfhf");
		String[] scores = HighScores.getTopScores(selectedLevelName);
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
			System.out.println(ProfileFileReader.getLoggedProfile());
			levelType = "saved_games/" + ProfileFileReader.getLoggedProfile() + "/";
		}
		LevelFileReader.loadLevelFile("target/classes/levels/" + levelType + selectedLevelName);

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
				levelNames = ProfileFileReader.getDeafaultLevelsNames();
				selectRadioButton(editDefaultLevelsRadioButton, editCustomLevelsRadioButton, editCustomLevelsRadioButton);
			} else if (editCustomLevelsRadioButton.isSelected()) {
				levelNames = ProfileFileReader.getCreatedLevelsNames();
				selectRadioButton(editCustomLevelsRadioButton, editDefaultLevelsRadioButton, editDefaultLevelsRadioButton);
			}

			levelButtons = new Button[levelNames.size()];

			for (int i = 0; i < levelNames.size(); i++) {
				levelButtons[i] = new Button(levelNames.get(i));
				levelButtons[i].setMaxSize(155, 30);
				levelButtons[i].setMinSize(155, 30);
				levelButtons[i].setId("menu-button1");
				levelsButtonsLevelCreationVBox.getChildren().add(levelButtons[i]);

				levelButtons[i].setOnAction(event -> {
					try {
						selectedEditLevelName = getButtonName(event);
					} catch (Exception e) {
						e.printStackTrace();
					}

					{
						// Preview display
						Image img = null;
						try {

							File f = new File("src\\main\\resources\\levels_images\\" + selectedEditLevelName + ".png");
							if (f.exists()) {
								Image tempImg = new Image(new FileInputStream(
										"src\\main\\resources\\levels_images\\\\" + selectedEditLevelName + ".png"));

								int width = (int) tempImg.getWidth();
								int height = (int) tempImg.getHeight();
								float widthCompare = (float) MAX_WIDTH_CREATION / (float) width;
								float heightComare = (float) MAX_HEIGHT_CREATION / (float) height;
								if (widthCompare < heightComare) {
									width *= widthCompare;
									height *= widthCompare;
								} else {
									width *= heightComare;
									height *= heightComare;
								}
								img = new Image(
										new FileInputStream(
												"src\\main\\resources\\levels_images\\" + selectedEditLevelName + ".png"),
										width, height, false, false);
								levelView.setImage(img);
							} else {
								System.out.println("ops");
								// TODO: do something in case of missing file
							}

						} catch (FileNotFoundException e) {
							// levelView.
						}
					}

				});
			}
		}
	}

	public void createLevel(ActionEvent event) {
		File tempFile = new File("src\\main\\resources\\levels\\created_levels\\" + newLevelNameTextField.getText() + ".txt");
		try {
			tempFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(newLevelNameTextField.getText() + " " + tempFile.exists() + " - exist?");
		ProfileFileReader.createNewLevel(newLevelNameTextField.getText());
		HighScores.createNewLevel(newLevelNameTextField.getText());
		levelsCreationViewUpdated = false;
		updateLevelCreationView();
		System.out.println("crt");
	}

	public void editCreatedLevel(ActionEvent event) throws IOException {
		System.out.println("edit");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("editor.fxml"));

		if (editDefaultLevelsRadioButton.isSelected()) {
			LevelFileReader.loadLevelFile("target/classes/levels/default_levels/" + selectedEditLevelName);
		} else if (editCustomLevelsRadioButton.isSelected()) {
			LevelFileReader.loadLevelFile("target/classes/levels/created_levels/" + selectedEditLevelName);
		}

		EditorController editorController = new EditorController(selectedEditLevelName, this);

		loader.setController(editorController);

		Pane root = loader.load();

		scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

		stage.setScene(scene);
		stage.show();
	}

	public void deleteCreatedLevel(ActionEvent event) {
		File tempFile = new File("levels/created_levels/" + selectedEditLevelName + ".txt");
		tempFile.delete();
		System.out.println(selectedEditLevelName + " " + tempFile.exists() + " - exist_after_delete?");
		ProfileFileReader.deleteLevel(newLevelNameTextField.getText());
		HighScores.deleteLevel(newLevelNameTextField.getText());
		levelsCreationViewUpdated = false;
		updateLevelCreationView();
		System.out.println("delete");
	}

	public void editLevelTypeChanged(ActionEvent event) {
		if (editDefaultLevelsRadioButton.isSelected()) {
			deleteLevelButton.setDisable(true);
		} else if (editCustomLevelsRadioButton.isSelected()) {
			deleteLevelButton.setDisable(false);
		}
		levelsCreationViewUpdated = false;
		updateLevelCreationView();
	}

	public void openLevelEditor(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("editor.fxml"));
		EditorController editorController = new EditorController();

		loader.setController(editorController);

		Pane root = loader.load();

		scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

		stage.setScene(scene);
		stage.show();
	}
}
