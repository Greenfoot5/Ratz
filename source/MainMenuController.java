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
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
		// Create a new pane to hold our GUI
		VBox root = new VBox();
		root.setAlignment(javafx.geometry.Pos.CENTER);

		// Create a few GUI elements
		Label title = new Label("RATZ");
		Label motd = new Label(MOTD.GETMotd());
		Button playButton = new Button("Play!");
		Button selectProfile = new Button("Select Profile!");

		// Create a scene based on the pane.
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		root.getChildren().addAll(title, motd, playButton, selectProfile);
		// Handle a button event
		playButton.setOnAction(event -> loadLevelSelect(primaryStage));
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
				for (int i = 0; i < obL.size(); i++) {
					if (obL.get(i).toString().contains("'" + reader.getLoggedProfile() + "'")) {
						obL.remove(obL.get(i));
						loggedLabel.setText("You are logged as ...");
						scoresHeading.setText("Best ...'s scores:");

						for (int j = 0; j < reader.getNumberOfLevels(); j++) {
							profileScore[j].setText("Lvl" + (j + 1) + " 0");
						}
					}
				}
				reader.logout();
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

				}
			} catch (Exception e) {
				System.out.println("Problemhere");
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

	/**
	 * Displays the level select screen
	 *
	 * @param selectStage the stage
	 */
	private void loadLevelSelect(Stage selectStage) {
		// Create a new pane to hold our GUI
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);

		// Create a few GUI elements
		Label title = new Label("Level Select");
		TilePane levels = new TilePane();
		levels.setAlignment(Pos.TOP_LEFT);

		Button[] lvl = new Button[6];
		for (int i = 1; i < 6; i++) {

			lvl[i] = new Button("Level " + i);
			levels.getChildren().add(lvl[i]);
		}
		// Handle the levels
		// TODO - Create one play button for every level
		Button playButton = new Button("Play!");
		playButton.setOnAction(event -> {
			try {
				loadLevel(selectStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		levels.getChildren().add(playButton);

		root.getChildren().addAll(title, levels);

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
