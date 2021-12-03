import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The main class to begin, and the one that handles the main menu
 * @author harvey
 * @version 1.0
 */
public class MainMenuController extends Application {
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;

	ProfileFileReader reader;
	Scene profileScene;

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
		// TODO - Display the motd
		Label motd = new Label(MOTD.GETMotd());
		Button playButton = new Button("Play!");
		Button selectProfile = new Button("Select Profile!");

		root.getChildren().addAll(title, motd, playButton, selectProfile);
		// Handle a button event
		playButton.setOnAction(event -> loadLevelSelect(primaryStage));
		selectProfile.setOnAction(event -> {
			primaryStage.setScene(profileScene);
			primaryStage.show();
		});

		// Create a scene based on the pane.
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Show the scene
		primaryStage.setScene(scene);
		primaryStage.show();

		// ProfileScene code
		// -------------------------------------------------------------------

		reader = new ProfileFileReader();

		BorderPane profilePane = new BorderPane();
		VBox profileButtons = new VBox();
		VBox profileScoreLabels = new VBox();
		VBox rightButtons = new VBox();
		
		String[] s = { "" };
		try {
			s = reader.getProfiles();
		} catch (FileNotFoundException e) {
			s[0] = "No profiles. Please Create a profile";
		}

		Label loggedLabel = new Label();
		loggedLabel.setAlignment(Pos.CENTER);
		if (reader.getLoggedProfile() == null) {
			loggedLabel.setText("You are looged as...");
		} else {
			loggedLabel.setText("You are looged as" + reader.getLoggedProfile());
		}

		Label scoresHeading = new Label("Best ... scores:");
		profileScoreLabels.getChildren().add(scoresHeading);

		Label[] profileScore = new Label[reader.getNumberOfLevels()];
		for (int i = 0; i < profileScore.length; i++) {
			profileScore[i] = new Label("Lvl" + String.valueOf(i + 1) + " 0");
			profileScoreLabels.getChildren().add(profileScore[i]);
		}

		Button[] profButton = new Button[s.length];
		for (int i = 0; i < profButton.length; i++) {
			profButton[i] = new Button(s[i]);
			profileButtons.getChildren().add(profButton[i]);

			final int ii = i;
			profButton[i].setOnAction(event -> {
				reader.loginProfile(profButton[ii].getText());
				loggedLabel.setText("You are looged as " + reader.getLoggedProfile());
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
		}

		Button goBack = new Button("Go back to main menu");
		goBack.setOnAction(event -> {
			primaryStage.setScene(scene);
			primaryStage.show();
		});
		Button removeProfile = new Button("Remove profile");
		removeProfile.setOnAction(event -> {
			try {
				System.out.println(reader.getLoggedProfile());
				reader.deleteProfile(reader.getLoggedProfile());
				ObservableList<Node> obL = profileButtons.getChildren();
				for (Node n : obL) {
					if (n.toString().contains("'" + reader.getLoggedProfile() + "'")) {
						System.out.println(n.toString());
					}
				}
			} catch (IOException e) {
			}
		});
		rightButtons.getChildren().addAll(goBack, removeProfile);

		profilePane.setCenter(profileScoreLabels);
		profilePane.setTop(loggedLabel);
		profilePane.setRight(rightButtons);
		profilePane.setLeft(profileButtons);
		profileScene = new Scene(profilePane, WINDOW_WIDTH, WINDOW_HEIGHT);

	}

	/**
	 * Displays the level select screen
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

			lvl[i] = new Button("Level " + Integer.toString(i));
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

	public void loadLevel(Stage levelStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("level.fxml"));
		LevelController levelController = new LevelController(new LevelFileReader(), this, new ProfileFileReader());

		loader.setController(levelController);

		Pane root = loader.load();

		Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

		levelStage.setScene(scene);
	}

	/**
	 * Allows the user to login to a profile
	 * 
	 * @param username The username to login to
	 */
//    public void login(String username) throws Exception {
//        ProfileFileReader reader = new ProfileFileReader();
//        // TODO - Check if the profile exists
//        reader.createNewProfile(username);
//        selectedProfile = username;
//    }

	/**
	 * Logs the user out of an account.
	 */
//    public void logout() {
//        selectedProfile = null;
//    }
}
