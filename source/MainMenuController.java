import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

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
		BorderPane root = new BorderPane();
		
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream("resources/adultfemaleNORTH.png");
		} catch (FileNotFoundException e) {
		} 
		Image image = new Image(inputstream);
		ImageView imageView = new ImageView(image);
		ImageView imageView2 = new ImageView(image);
		ImageView imageView3 = new ImageView(image);
		ImageView imageView4 = new ImageView(image);
		ImageView imageView5 = new ImageView(image);
		ImageView imageView6 = new ImageView(image);
		ImageView imageView7 = new ImageView(image);
		HBox bottom = new HBox();
		bottom.setSpacing(60);
		bottom.getChildren().addAll(imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7);
		
		try {
			inputstream = new FileInputStream("resources/adultmaleSOUTH.png");
		} catch (FileNotFoundException e) {
		} 
		Image image2 = new Image(inputstream);
		ImageView imageView_2 = new ImageView(image2);
		ImageView imageView22 = new ImageView(image2);
		ImageView imageView32 = new ImageView(image2);
		ImageView imageView42 = new ImageView(image2);
		ImageView imageView52 = new ImageView(image2);
		ImageView imageView62 = new ImageView(image2);
		ImageView imageView72 = new ImageView(image2);
		HBox top = new HBox();
		top.setAlignment(Pos.BASELINE_RIGHT);
		top.setSpacing(60);
		top.getChildren().addAll(imageView_2,imageView22,imageView32,imageView42,imageView52,imageView62,imageView72);
		
		VBox middle = new VBox(5);
		middle.setAlignment(Pos.CENTER);
		
		root.setTop(top);
		root.setBottom(bottom);
		root.setCenter(middle);

		// Create a few GUI elements
		Label title = new Label("RATZ");
		title.getStyleClass().add("title");
		Label motd = new Label(MOTD.GETMotd());
		Button playButton = new Button("Play!");
		Button selectProfile = new Button("Select Profile!");
		
		Label loggedProfileText = new Label("You are logged as ");
		loggedProfile = new Label();
		if (reader.getLoggedProfile() == null) {
			loggedProfile.setText("NOBODY. Please log in before starting the game");
		} else {
			loggedProfile.setText(reader.getLoggedProfile());
		}
		HBox loggedProfileBox = new HBox();
		loggedProfileBox.setAlignment(Pos.CENTER);
		loggedProfileBox.setStyle("-fx-text-fill: #Fd062a");
		loggedProfileBox.getChildren().addAll(loggedProfileText, loggedProfile);
		// Create a scene based on the pane.
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		File f = new File("source/menu.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		
		middle.getChildren().addAll(title, motd, playButton, selectProfile, loggedProfileBox);
		// Handle a button event
		playButton.setOnAction(event -> {
			if (reader.getLoggedProfile() == null) {
				alert("You are not logged in.\nPlease log in before starting the game");
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
		VBox left = new VBox(10);
		left.setAlignment(Pos.TOP_CENTER);
		VBox middle = new VBox(10);
		middle.setAlignment(Pos.TOP_CENTER);
		VBox right = new VBox();
		HBox bottom = new HBox(); // adding profile stuff on bottom
		HBox top = new HBox();
		// Get the profiles
		String[] s = { "" };
		try {
			s = reader.getProfiles();
		} catch (FileNotFoundException e) {
			s[0] = "No profiles. Please Create a profile";
		}

		// Display who's logged in
		
		Label loggedLabelText = new Label("You are logged as ");
		loggedLabelText.getStyleClass().add("loggingLabel");
		Label loggedLabel = new Label();
		loggedLabel.getStyleClass().add("loggingLabel");
		loggedLabel.setStyle("-fx-text-fill: #Fd062a");
		if (reader.getLoggedProfile() == null) {
			loggedLabel.setText("...");
		} else {
			loggedLabel.setText(reader.getLoggedProfile());
		}
		top.getChildren().addAll(loggedLabelText, loggedLabel);
		top.setAlignment(Pos.CENTER);

		// Display the best scores for a user
		Label scoresHeading;
		Label[] profileScore = new Label[reader.getNumberOfLevels()];

		if (reader.getLoggedProfile() != null) {
			scoresHeading = new Label("Best " + reader.getLoggedProfile() + "'s scores:");
			middle.getChildren().add(scoresHeading);

			for (int i = 0; i < profileScore.length; i++) {
				try {
					profileScore[i] = new Label(
							"Lvl" + (i + 1) + " " + reader.getBestScore(reader.getLoggedProfile(), i + 1));
				} catch (IOException e) {
					profileScore[i] = new Label("Lvl" + (i + 1) + " unknown error");
				}
				middle.getChildren().add(profileScore[i]);
			}
		} else {
			scoresHeading = new Label("Best ... scores:");
			middle.getChildren().add(scoresHeading);

			for (int i = 0; i < profileScore.length; i++) {
				profileScore[i] = new Label("Lvl" + (i + 1) + " 0");
				middle.getChildren().add(profileScore[i]);
			}
		}

		// Display a button for each profile
		Button[] profButton = new Button[s.length];
		for (int i = 0; i < profButton.length; i++) {
			profButton[i] = new Button(s[i]);
			profButton[i].setPrefWidth(100);
			left.getChildren().add(profButton[i]);

			final int ii = i;
			profButton[i].setOnAction(event -> {
				reader.loginProfile(profButton[ii].getText());
				loggedProfile.setText(reader.getLoggedProfile());
                displayProfileBests(loggedLabel, scoresHeading, profileScore);
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
				ObservableList<Node> obL = left.getChildren();

				// remove profile button and change the labels
				boolean isRemoved = false;
				for (int i = 0; i < obL.size(); i++) {
					if (obL.get(i).toString().contains("'" + reader.getLoggedProfile() + "'")) {
						isRemoved = true;
						obL.remove(obL.get(i));
						loggedLabel.setText("...");
						scoresHeading.setText("Best ...'s scores:");

						for (int j = 0; j < reader.getNumberOfLevels(); j++) {
							profileScore[j].setText("Lvl" + (j + 1) + " 0");
						}
					}
				}
				if (!isRemoved) {
					alert("No profile is selected");
				}
				
				
				reader.logout();
				loggedProfile.setText("NOBODY. Please log in before starting the game");
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
                        displayProfileBests(loggedLabel, scoresHeading, profileScore);
                    });
					left.getChildren().add(newProfButton);

				} else if (!newProfField.getText().equals("")) {
					alert("Profile already exists");
				} else {
					alert("Please, type a name");
				}
				
			} catch (Exception e) {
				System.out.println("Problem here/ Adding button action");
			}
		});

		bottom.getChildren().addAll(newProfLabel, newProfField, addProfButton);
		right.getChildren().addAll(goBack, removeProfile);

		// Adds the elements to the layout
		profilePane.setCenter(middle);
		profilePane.setTop(top);
		profilePane.setRight(right);
		profilePane.setLeft(left);
		profilePane.setBottom(bottom);
		Scene profileScene = new Scene(profilePane, WINDOW_WIDTH, WINDOW_HEIGHT);
		File f = new File("source/menu.css");
		profileScene.getStylesheets().clear();
		profileScene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		return profileScene;
	}

    private void displayProfileBests(Label loggedLabel, Label scoresHeading, Label[] profileScore) {
        loggedLabel.setText(reader.getLoggedProfile());
        scoresHeading.setText("Best " + reader.getLoggedProfile() + "'s scores:");

        for (int j = 0; j < reader.getNumberOfLevels(); j++) {
            try {
                profileScore[j].setText("Lvl" + (j + 1) + " "
                        + reader.getBestScore(reader.getLoggedProfile(), j + 1));
            } catch (IOException e) {
                profileScore[j].setText("Lvl" + (j + 1) + " error");

            }
        }
    }

    private void alert(String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Alert");
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
		AtomicInteger selectedLevel = new AtomicInteger(1);
		BorderPane root = new BorderPane();
		// root.setAlignment(Pos.CENTER);
		HighScores scoresReader = new HighScores();

		HBox topBox = new HBox();
		topBox.setAlignment(Pos.CENTER);
		Label title = new Label("Level Select");
		topBox.getChildren().add(title);

		VBox scores = new VBox();
		scores.setAlignment(Pos.CENTER);
		Label scoreHeading = new Label("Lvl " + selectedLevel + " best scores:");
		scores.getChildren().add(scoreHeading);

		Label[] scoresLabel = new Label[10];
		String[] scoresString = null;
		try {
			scoresString = scoresReader.getTopScores(selectedLevel.get());
		} catch (FileNotFoundException ignored) { }

		for (int i = 0; i < 10; i++) {
			scoresLabel[i] = new Label();
			try {
                assert scoresString != null;
                scoresLabel[i].setText((i + 1) + " " + scoresString[i]);
			} catch (Exception e2) {
				scoresLabel[i].setText((i + 1) + " ...");
			}
			scores.getChildren().add(scoresLabel[i]);
		}
		VBox levels = new VBox();
		levels.setAlignment(Pos.CENTER);

		Button[] lvl = new Button[5];
		for (int i = 0; i < lvl.length; i++) {
            int levelIndex = i + 1;
			lvl[i] = new Button("Level " + (levelIndex));

            lvl[i].setOnAction(event -> {
				scoreHeading.setText("Lvl " + (levelIndex) + " best scores:");
                selectedLevel.set(levelIndex);

				String[] newScores = null;
				try {
					newScores = scoresReader.getTopScores(levelIndex);
				} catch (FileNotFoundException ignored) { }

				for (int j = 0; j < 10; j++) {
					try {
                        assert newScores != null;
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
                // TODO - Get the level number
				loadLevel(selectStage, selectedLevel.get());
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
	private void loadLevel(Stage levelStage, int levelNumber) throws IOException {
		LevelFileReader.loadLevelFile("./resources/level-" + levelNumber + ".txt");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("level.fxml"));
		LevelController levelController = new LevelController(levelNumber, this, new ProfileFileReader());

		loader.setController(levelController);

		Pane root = loader.load();

		Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

		levelStage.setScene(scene);
	}

	public void finishLevel() {

	}
}
