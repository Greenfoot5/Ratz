import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The main class to begin, and the one that handles the main menu
 * 
 * @author harvey
 * @version 1.0
 */
public class MainMenuController extends Application {
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;
	private Label loggedProfile;
	private ProfileFileReader reader;

	/**
	 * Launches the application
	 *
	 * @param args Don't do anything atm
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Called when we begin the application
	 *
	 * @param primaryStage I forgot
	 */
	@Override
	public void start(Stage primaryStage) {
		// Create reader if we don't have one yet
		if (reader == null) {
			reader = new ProfileFileReader();
		}
		// Create a new pane to hold our GUI
		VBox root = new VBox(5);
		root.setAlignment(Pos.CENTER);

		// Create a few GUI elements
		Label title = new Label("RATZ");
		Label motd = new Label(MOTD.GETMotd());
		Button playButton = new Button("Play!");
		Button selectProfile = new Button("Select Profile!");
		loggedProfile = new Label();
		if (reader.getLoggedProfile() == null) {
			loggedProfile.setText("You are not logged in. Please log in before starting the game");
		} else {
			loggedProfile.setText("You are as " + reader.getLoggedProfile());

		}
		// Create a scene based on the pane.
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		File f = new File("source/menu.css");
		//System.out.println(f.exists());
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		
		root.getChildren().addAll(title, motd, playButton, selectProfile, loggedProfile);
		// Handle a button event
		playButton.setOnAction(event -> {
			if (reader.getLoggedProfile() == null) {
				this.alert("Alert", "You are not logged in.\nPlease log in before starting the game");
			} else {
				loadLevelSelect(primaryStage);
			}
		});
		selectProfile.setOnAction(event -> {
			primaryStage.setScene(selectProfiles(primaryStage, scene));
			primaryStage.show();
		});

		// Show the scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Scene selectProfiles(Stage profileStage, Scene scene) {
		// Create reader if we don't have one yet
		if (reader == null) {
			reader = new ProfileFileReader();
		}

		// Layout items
		BorderPane profilePane = new BorderPane();
		VBox profileButtons = new VBox();
		VBox profileScoreLabels = new VBox();
		VBox rightButtons = new VBox();
		HBox bottomAddingProf = new HBox(); // adding profile stuff on bottom

		// Get the profiles
		String[] s = { "" };
		try {
			s = reader.getProfiles();
		} catch (FileNotFoundException e) {
			s[0] = "No profiles. Please Create a profile";
		}

		// Display who's logged in
		Label loggedLabel = new Label();
		loggedLabel.setAlignment(Pos.CENTER);
		if (reader.getLoggedProfile() == null) {
			loggedLabel.setText("You are logged as...");
		} else {
			loggedLabel.setText("You are logged as " + reader.getLoggedProfile());
		}

		// Display the best scores for a user
		Label scoresHeading;
		Label[] profileScore = new Label[reader.getNumberOfLevels()];

		if (reader.getLoggedProfile() != null) {
			scoresHeading = new Label("Best " + reader.getLoggedProfile() + "'s scores:");
			profileScoreLabels.getChildren().add(scoresHeading);

			for (int i = 0; i < profileScore.length; i++) {
				try {
					profileScore[i] = new Label(
							"Lvl" + (i + 1) + " " + reader.getBestScore(reader.getLoggedProfile(), i + 1));
				} catch (IOException e) {
					profileScore[i] = new Label("Lvl" + (i + 1) + " unknown error");
				}
				profileScoreLabels.getChildren().add(profileScore[i]);
			}
		} else {
			scoresHeading = new Label("Best ... scores:");
			profileScoreLabels.getChildren().add(scoresHeading);

			for (int i = 0; i < profileScore.length; i++) {
				profileScore[i] = new Label("Lvl" + (i + 1) + " 0");
				profileScoreLabels.getChildren().add(profileScore[i]);
			}
		}

		// Display a button for each profile
		Button[] profButton = new Button[s.length];
		for (int i = 0; i < profButton.length; i++) {
			profButton[i] = new Button(s[i]);
			profileButtons.getChildren().add(profButton[i]);

			final int ii = i;
			profButton[i].setOnAction(event -> {
				reader.loginProfile(profButton[ii].getText());
				loggedProfile.setText("You are logged as " + reader.getLoggedProfile());
				loggedLabel.setText("You are logged as " + reader.getLoggedProfile());
				scoresHeading.setText("Best " + reader.getLoggedProfile() + "'s scores:");

				for (int j = 0; j < reader.getNumberOfLevels(); j++) {
					try {
						profileScore[j]
								.setText("Lvl" + (j + 1) + " " + reader.getBestScore(reader.getLoggedProfile(), j + 1));
					} catch (IOException e) {
						profileScore[j].setText("Lvl" + (j + 1) + " error");

					}
				}
			});
		}

		// Return to the main menu
		Button goBack = new Button("Go back to main menu");
		goBack.setOnAction(event -> {
			profileStage.setScene(scene);
			profileStage.show();
		});

		// Removes a profile
		Button removeProfile = new Button("Remove profile");
		removeProfile.setOnAction(event -> {
			try {
				reader.deleteProfile(reader.getLoggedProfile());
				ObservableList<Node> obL = profileButtons.getChildren();

				// remove profile button and change the labels
				boolean isRemoved = false;
				for (int i = 0; i < obL.size(); i++) {
					if (obL.get(i).toString().contains("'" + reader.getLoggedProfile() + "'")) {
						isRemoved = true;
						obL.remove(obL.get(i));
						loggedLabel.setText("You are logged as ...");
						scoresHeading.setText("Best ...'s scores:");

						for (int j = 0; j < reader.getNumberOfLevels(); j++) {
							profileScore[j].setText("Lvl" + (j + 1) + " 0");
						}
					}
				}
				if (!isRemoved) {
					alert("Alert", "No profile is selected");
				}
				
				
				reader.logout();
				loggedProfile.setText("You are not logged in. Please log in before starting the game");
			} catch (IOException ignored) {
			}
		});

		// Adding profile stuff
		Label newProfLabel = new Label("Add new profile: ");
		TextField newProfField = new TextField();
		Button addProfButton = new Button("Add");
		addProfButton.setOnAction(event -> {
			try {
				if (!newProfField.getText().equals("") && !reader.doesProfileExist(newProfField.getText())) {

					reader.createNewProfile(newProfField.getText());

					Button newProfButton = new Button(newProfField.getText());
					reader.loginProfile(newProfButton.getText());

					newProfButton.setOnAction(event2 -> {
						// Logging in
						reader.loginProfile(newProfButton.getText());
						// Changing a labels
						loggedLabel.setText("You are logged as " + reader.getLoggedProfile());
						scoresHeading.setText("Best " + reader.getLoggedProfile() + "'s scores:");

						for (int j = 0; j < reader.getNumberOfLevels(); j++) {
							try {
								profileScore[j].setText("Lvl" + String.valueOf(j + 1) + " "
										+ reader.getBestScore(reader.getLoggedProfile(), j + 1));
							} catch (IOException e) {
								profileScore[j].setText("Lvl" + String.valueOf(j + 1) + " error");

							}
						}
					});
					profileButtons.getChildren().add(newProfButton);

				} else if (!newProfField.getText().equals("")) {
					this.alert("Alert", "Profile already exists");
				} else {
					this.alert("Alert", "Please, type a name");
				}
				
			} catch (Exception e) {
				System.out.println("Problemhere/ Adding button action");
			}
		});

		bottomAddingProf.getChildren().addAll(newProfLabel, newProfField, addProfButton);
		rightButtons.getChildren().addAll(goBack, removeProfile);

		// Adds the elements to the layout
		profilePane.setCenter(profileScoreLabels);
		profilePane.setTop(loggedLabel);
		profilePane.setRight(rightButtons);
		profilePane.setLeft(profileButtons);
		profilePane.setBottom(bottomAddingProf);

		return new Scene(profilePane, WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	private void alert(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(240);
		window.setHeight(120);
		
		Label label = new Label(message);
		Button button = new Button("Ok");
		button.setOnAction(event -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, button);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.setResizable(false);
		window.showAndWait();
	}

	/**
	 * Displays the level select screen
	 *
	 * @param selectStage the stage
	 */
	private void loadLevelSelect(Stage selectStage) {
		// Create a new pane to hold our GUI
		// TODO: use this variable to choose a level (when levels will work)
		int selectedLevel = 1;
		BorderPane root = new BorderPane();
		// root.setAlignment(Pos.CENTER);
		HighScores scoresReader = new HighScores();

		HBox topBox = new HBox();
		topBox.setAlignment(Pos.CENTER);
		Label title = new Label("Level Select");
		topBox.getChildren().add(title);

		VBox scores = new VBox();
		scores.setAlignment(Pos.CENTER);
		Label scoreHeading = new Label("Lvl 1 best scores:");
		scores.getChildren().add(scoreHeading);

		Label[] scoresLabel = new Label[10];
		String[] scoresString = null;
		try {
			scoresString = scoresReader.getTopScores(1);
		} catch (FileNotFoundException e1) {
		}
		for (int i = 0; i < 10; i++) {
			scoresLabel[i] = new Label();
			try {
				scoresLabel[i].setText((i + 1) + " " + scoresString[i]);
			} catch (Exception e2) {
				scoresLabel[i].setText((i + 1) + " ...");
			}
			scores.getChildren().add(scoresLabel[i]);
		}
		VBox levels = new VBox();
		levels.setAlignment(Pos.CENTER);

		Button[] lvl = new Button[5];
		for (int i = 0; i < 5; i++) {

			lvl[i] = new Button("Level " + (i + 1));

			final int ii = i;
			lvl[i].setOnAction(event -> {
				scoreHeading.setText("Lvl " + (ii + 1) + " best scores:");
				String[] newScores = null;
				try {
					newScores = scoresReader.getTopScores(ii + 1);
				} catch (FileNotFoundException e) {
				}
				for (int j = 0; j < 10; j++) {
					try {
						scoresLabel[j].setText((j + 1) + " " + newScores[j]);
					} catch (Exception e2) {
						scoresLabel[j].setText((j + 1) + " ...");
					}
				}
			});

			levels.getChildren().add(lvl[i]);
		}
		VBox rightBox = new VBox();
		rightBox.setAlignment(Pos.CENTER);

		Button playButton = new Button("Play!");
		playButton.setOnAction(event -> {
			try {
				loadLevel(selectStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		rightBox.getChildren().add(playButton);

		root.setCenter(scores);
		root.setTop(topBox);
		root.setRight(rightBox);
		root.setLeft(levels);

		// Create a scene based on the pane.
		Scene scene = new Scene(root, 400, 400);

		// Show the scene
		selectStage.setScene(scene);
		selectStage.show();
	}

	/**
	 * Loads a level through the LevelController
	 *
	 * @param levelStage The stage
	 * @throws IOException If we cannot load a level
	 */
	public void loadLevel(Stage levelStage) throws IOException {
		LevelFileReader.loadLevelFile("./resources/level.txt");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("level.fxml"));
		LevelController levelController = new LevelController(new LevelFileReader(), this, new ProfileFileReader());

		loader.setController(levelController);

		Pane root = loader.load();

		Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

		levelStage.setScene(scene);
	}

	public void finishLevel(String levelName) {

	}
}
