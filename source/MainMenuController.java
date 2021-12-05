import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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
	private Stage mainStage;
	private Scene mainScene;

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
		mainStage = primaryStage;
		primaryStage.setResizable(false);
		// Create a new pane to hold our GUI
		BorderPane root = new BorderPane();

		HBox bottom = getBottomRatsMain();
		HBox top = getTopRatsMain();

		// Create a scene based on the pane.
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		mainScene = scene;
		File f = new File("source/menu.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		// VBox middle = new VBox(5);
		VBox middle = getMiddleMain(scene, primaryStage);
		middle.setAlignment(Pos.CENTER);

		root.setTop(top);
		root.setBottom(bottom);
		root.setCenter(middle);
		// Show the scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private VBox getMiddleMain(Scene scene, Stage primaryStage) {
		// Create a few GUI elements
		// Label title = new Label("RATZ");
		// title.getStyleClass().add("title");
		VBox middle = new VBox(5);
		Label motd = new Label(MOTD.GETMotd());
		Button playButton = new Button("Play!");
		playButton.setPrefWidth(100);
		Button selectProfile = new Button("Select Profile!");
		selectProfile.setPrefWidth(100);
		selectProfile.setPadding(new Insets(10, 0, 0, 0));

		Label loggedProfileText = new Label("You are logged as ");
		loggedProfile = new Label();
		if (ProfileFileReader.getLoggedProfile() == null) {
			loggedProfile.setText("NOBODY. Please log in before starting the game");
		} else {
			loggedProfile.setText(ProfileFileReader.getLoggedProfile());
		}
		HBox loggedProfileBox = new HBox();
		loggedProfileBox.setAlignment(Pos.CENTER);
		loggedProfileBox.setStyle("-fx-text-fill: #Fd062a");
		loggedProfileBox.getChildren().addAll(loggedProfileText, loggedProfile);

		ImageView ratzImageView = getRatzImageViewMain();

		middle.getChildren().addAll(ratzImageView, motd, playButton, selectProfile, loggedProfileBox);
		// Handle a button event
		playButton.setOnAction(event -> {
			if (ProfileFileReader.getLoggedProfile() == null) {
				alert("You are not logged in.\nPlease log in before starting the game");
				// TODO: remove these lines
//						primaryStage.setScene(loadLevelSelect(primaryStage, scene));
//						primaryStage.show();
			} else {
				primaryStage.setScene(loadLevelSelect(primaryStage, scene));
				primaryStage.show();
			}
		});
		selectProfile.setOnAction(event -> {
			primaryStage.setScene(selectProfiles(primaryStage, scene));
			primaryStage.show();
		});
		return middle;
	}

	private ImageView getRatzImageViewMain() {

		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream("resources/ratzLabel.png");
		} catch (FileNotFoundException e) {
		}
		Image ratzImage = new Image(inputstream);
		ImageView imgView = new ImageView(ratzImage);
		return imgView;
	}

	private HBox getTopRatsMain() {
		HBox top = new HBox();
		FileInputStream inputstream = null;
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

		top.setAlignment(Pos.BASELINE_RIGHT);
		top.setSpacing(60);
		top.getChildren().addAll(imageView_2, imageView22, imageView32, imageView42, imageView52, imageView62,
				imageView72);
		return top;
	}

	private HBox getBottomRatsMain() {
		HBox bottom = new HBox();
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

		bottom.setSpacing(60);
		bottom.getChildren().addAll(imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7);
		return bottom;
	}

	public Scene selectProfiles(Stage profileStage, Scene scene) {
		// Create reader if we don't have one yet

		// Layout items
		BorderPane profilePane = new BorderPane();
		VBox left = new VBox(10);
		left.setAlignment(Pos.TOP_CENTER);
		left.setPadding(new Insets(10, 10, 10, 40));

		VBox middle = new VBox(10);
		middle.setAlignment(Pos.TOP_CENTER);
		middle.setPadding(new Insets(5, 0, 5, 0));

		VBox right = new VBox(10);
		right.setAlignment(Pos.TOP_CENTER);
		right.setPadding(new Insets(10, 100, 10, 0));

		HBox bottom = new HBox(20);
		bottom.setAlignment(Pos.TOP_CENTER);
		bottom.setPadding(new Insets(10, 80, 30, 0));
		HBox top = new HBox();
		// Get the profiles
		String[] s = { "" };
		try {
			s = ProfileFileReader.getProfiles();
		} catch (FileNotFoundException e) {
			s[0] = "No profiles. Please Create a profile";
		}

		// Display who's logged in

		Label loggedLabelText = new Label("You are logged as ");
		loggedLabelText.getStyleClass().add("loggingLabel");
		Label loggedLabel = new Label();
		loggedLabel.getStyleClass().add("loggingLabel");
		loggedLabel.setStyle("-fx-text-fill: #Fd062a");
		if (ProfileFileReader.getLoggedProfile() == null) {
			loggedLabel.setText("...");
		} else {
			loggedLabel.setText(ProfileFileReader.getLoggedProfile());
		}
		top.getChildren().addAll(loggedLabelText, loggedLabel);
		top.setAlignment(Pos.CENTER);

		// Display the best scores for a user
		Label scoresHeading;
		Label[] profileScore = new Label[ProfileFileReader.getNumberOfLevels()];

		if (ProfileFileReader.getLoggedProfile() != null) {
			scoresHeading = new Label("Best " + ProfileFileReader.getLoggedProfile() + "'s scores:");
			middle.getChildren().add(scoresHeading);

			boolean unlocked = true;
			for (int i = 0; i < profileScore.length; i++) {
				try {
					if (ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), i + 1) == 0 && unlocked) {
						profileScore[i] = new Label("Lvl" + (i + 1) + " "
								+ ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), i + 1));
						unlocked = false;
					} else {
						profileScore[i] = new Label("Lvl" + (i + 1) + " is locked");
					}
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

		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream("resources/poison.png");
		} catch (FileNotFoundException e) {
		}
		Image image = new Image(inputstream);
		ImageView imageView = new ImageView(image);
		ImageView imageView2 = new ImageView(image);

		HBox selectMenuPics = new HBox();
		selectMenuPics.setSpacing(100);
		selectMenuPics.setPadding(new Insets(40, 130, 0, 160));
		selectMenuPics.getChildren().addAll(imageView, imageView2);
		middle.getChildren().add(selectMenuPics);

		// Display a button for each profile
		Button[] profButton = new Button[s.length];
		for (int i = 0; i < profButton.length; i++) {
			profButton[i] = new Button(s[i]);
			profButton[i].setPrefWidth(100);
			left.getChildren().add(profButton[i]);

			final int ii = i;
			profButton[i].setOnAction(event -> {
				ProfileFileReader.loginProfile(profButton[ii].getText());
				loggedProfile.setText(ProfileFileReader.getLoggedProfile());
				displayProfileBests(loggedLabel, scoresHeading, profileScore);
			});
		}

		// Return to the main menu
		Button goBack = new Button("Main menu");
		goBack.setPrefWidth(100);
		goBack.setOnAction(event -> {
			profileStage.setScene(scene);
			profileStage.show();
		});

		// Removes a profile
		Button removeProfile = new Button("Remove profile");
		removeProfile.setMinWidth(100);
		removeProfile.setOnAction(event -> {
			try {
				ProfileFileReader.deleteProfile(ProfileFileReader.getLoggedProfile());
				HighScores.deleteProfile(ProfileFileReader.getLoggedProfile());
				ObservableList<Node> obL = left.getChildren();

				// remove profile button and change the labels
				boolean isRemoved = false;
				for (int i = 0; i < obL.size(); i++) {
					if (obL.get(i).toString().contains("'" + ProfileFileReader.getLoggedProfile() + "'")) {
						isRemoved = true;
						obL.remove(obL.get(i));
						loggedLabel.setText("...");
						scoresHeading.setText("Best ...'s scores:");

						for (int j = 0; j < ProfileFileReader.getNumberOfLevels(); j++) {
							profileScore[j].setText("Lvl" + (j + 1) + " 0");
						}
					}
				}
				if (!isRemoved) {
					alert("No profile is selected");
				}

				ProfileFileReader.logout();
				loggedProfile.setText("NOBODY. Please log in before starting the game");
			} catch (IOException ignored) {
			}
		});

		// Adding profile stuff
		Label newProfLabel = new Label("Add new profile: ");
		TextField newProfField = new TextField();
		Button addProfButton = new Button("Add");
		addProfButton.setPrefWidth(70);
		addProfButton.setOnAction(event -> {
			try {
				if (left.getChildren().size() > 8) {
					alert("Too much profiles");
				} else if (!newProfField.getText().equals("")
						&& !ProfileFileReader.doesProfileExist(newProfField.getText())) {

					ProfileFileReader.createNewProfile(newProfField.getText());

					Button newProfButton = new Button(newProfField.getText());
					newProfButton.setPrefWidth(100);
					ProfileFileReader.loginProfile(newProfButton.getText());

					newProfButton.setOnAction(event2 -> {
						// Logging in
						ProfileFileReader.loginProfile(newProfButton.getText());
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
		loggedLabel.setText(ProfileFileReader.getLoggedProfile());
		scoresHeading.setText("Best " + ProfileFileReader.getLoggedProfile() + "'s scores:");

		boolean unlocked = true;
		for (int i = 0; i < profileScore.length; i++) {
			try {
				if (ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), i + 1) > 0) {
					profileScore[i].setText("Lvl" + (i + 1) + " "
							+ ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), i + 1));
				} else if (ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), i + 1) == 0
						&& unlocked) {
					profileScore[i].setText("Lvl" + (i + 1) + " "
							+ ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), i + 1));
					unlocked = false;
				} else {
					profileScore[i].setText("Lvl" + (i + 1) + " is locked");
				}
			} catch (IOException e) {
				profileScore[i].setText("Lvl" + (i + 1) + " unknown error");
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
	public Scene loadLevelSelect(Stage selectStage, Scene scene) {
		// Create a new pane to hold our GUI
		// TODO: use this variable to choose a level (when levels will work)
		AtomicInteger selectedLevel = new AtomicInteger(1);

		BorderPane root = new BorderPane();

		VBox topBox = new VBox();
		topBox.setAlignment(Pos.CENTER);
		topBox.setPrefHeight(80);
		Label title = new Label("Level Select");
		Separator separator = new Separator();
		title.getStyleClass().add("loggingLabel");
		topBox.getChildren().addAll(title, separator);

		VBox middleBox = new VBox();
		middleBox.setAlignment(Pos.CENTER);

		Label scoreHeading = new Label("Lvl " + selectedLevel + " best scores:");
		scoreHeading.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold");
		middleBox.getChildren().add(scoreHeading);

		Label[] scoresLabel = new Label[10];
		String[] scoresString = null;
		try {
			scoresString = HighScores.getTopScores(selectedLevel.get());
		} catch (FileNotFoundException ignored) {
		}

		for (int i = 0; i < 10; i++) {
			scoresLabel[i] = new Label();
			scoresLabel[i].setPadding(new Insets(3, 0, 3, 0));
			;
			try {
				assert scoresString != null;
				scoresLabel[i].setText((i + 1) + " " + scoresString[i]);
			} catch (Exception e2) {
				scoresLabel[i].setText((i + 1) + " ...");
			}
			middleBox.getChildren().add(scoresLabel[i]);
		}

		VBox leftBox = new VBox(10);
		leftBox.setAlignment(Pos.CENTER_RIGHT);
		leftBox.setPrefWidth(180);

		boolean[] isUnlocked = new boolean[5];
		boolean unlocked = true;
		for (int i = 0; i < ProfileFileReader.getNumberOfLevels(); i++) {
			try {
				if (ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), i + 1) > 0) {
					isUnlocked[i] = true;
				} else if (ProfileFileReader.getBestScore(ProfileFileReader.getLoggedProfile(), i + 1) == 0
						&& unlocked) {
					isUnlocked[i] = true;
					unlocked = false;
				} else {
					isUnlocked[i] = false;
				}
			} catch (Exception e) {
				isUnlocked[i] = false;
			}
		}

		Button[] lvl = new Button[5];
		for (int i = 0; i < lvl.length; i++) {
			int levelIndex = i + 1;
			lvl[i] = new Button("Level " + (levelIndex));
			lvl[i].setPrefWidth(70);

			if (isUnlocked[i]) {
				lvl[i].setOnAction(event -> {
					scoreHeading.setText("Lvl " + (levelIndex) + " best scores:");
					selectedLevel.set(levelIndex);

					String[] newScores = null;
					try {
						newScores = HighScores.getTopScores(levelIndex);
					} catch (FileNotFoundException ignored) {
					}

					for (int j = 0; j < 10; j++) {
						try {
							assert newScores != null;
							scoresLabel[j].setText((j + 1) + " " + newScores[j]);
						} catch (Exception e2) {
							scoresLabel[j].setText((j + 1) + " ...");
						}
					}
				});
			} else {
				lvl[i].setOnAction(event -> {
					alert("You haven'y unlocked this level");
				});
				lvl[i].getStyleClass().add("buttonBlocked");
			}

			leftBox.getChildren().add(lvl[i]);
		}

		VBox rightBox = new VBox(5);
		rightBox.setAlignment(Pos.CENTER_LEFT);
		rightBox.setPrefWidth(180);
		rightBox.setPadding(new Insets(5, 0, 5, 0));

		FileInputStream inputstream1 = null;
		FileInputStream inputstream2 = null;
		FileInputStream inputstream3 = null;
		try {
			inputstream1 = new FileInputStream("resources/bomb1.png");
			inputstream2 = new FileInputStream("resources/bomb3.png");
			inputstream3 = new FileInputStream("resources/bomb4.png");
		} catch (FileNotFoundException e) {
		}
		Image image1 = new Image(inputstream1);
		ImageView imageView1 = new ImageView(image1);
		Image image2 = new Image(inputstream2);
		ImageView imageView2 = new ImageView(image2);
		Image image3 = new Image(inputstream3);
		ImageView imageView3 = new ImageView(image3);

		Button playButton = new Button("Play!");
		playButton.setPrefWidth(80);
		playButton.setOnAction(event -> {
			try {
				// TODO - Get the level number
				loadLevel(selectStage, selectedLevel.get());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button backToMenu = new Button("Main Menu");
		backToMenu.setOnAction(event -> {
			selectStage.setScene(scene);
			selectStage.show();
		});
		backToMenu.setPrefWidth(85);

		rightBox.getChildren().addAll(imageView1, playButton, imageView2, backToMenu, imageView3);

		FileInputStream inputs1 = null;
		FileInputStream inputs2 = null;
		FileInputStream inputs3 = null;
		FileInputStream inputs4 = null;
		FileInputStream inputs5 = null;
		FileInputStream inputs6 = null;
		try {
			inputs1 = new FileInputStream("resources/childratEAST.png");
			inputs2 = new FileInputStream("resources/deathratEAST.png");
			inputs3 = new FileInputStream("resources/childratEAST.png");
			inputs4 = new FileInputStream("resources/deathratEAST.png");
			inputs5 = new FileInputStream("resources/childratEAST.png");
			inputs6 = new FileInputStream("resources/deathratEAST.png");
		} catch (FileNotFoundException e) {
		}
		Image imageB1 = new Image(inputs1);
		ImageView imageViewB1 = new ImageView(imageB1);
		Image imageB2 = new Image(inputs2);
		ImageView imageViewB2 = new ImageView(imageB2);
		Image imageB3 = new Image(inputs3);
		ImageView imageViewB3 = new ImageView(imageB3);
		Image imageB4 = new Image(inputs4);
		ImageView imageViewB4 = new ImageView(imageB4);
		Image imageB5 = new Image(inputs5);
		ImageView imageViewB5 = new ImageView(imageB5);
		Image imageB6 = new Image(inputs6);
		ImageView imageViewB6 = new ImageView(imageB6);

		HBox bottomBox = new HBox(82);
		bottomBox.setPrefHeight(80);
		bottomBox.getChildren().addAll(imageViewB1, imageViewB2, imageViewB3, imageViewB4, imageViewB5, imageViewB6);

		root.setCenter(middleBox);
		root.setTop(topBox);
		root.setRight(rightBox);
		root.setLeft(leftBox);
		root.setBottom(bottomBox);

		// Create a scene based on the pane.
		Scene levelsScene = new Scene(root, 800, 500);

		File f = new File("source/menu.css");
		levelsScene.getStylesheets().clear();
		levelsScene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		return levelsScene;
	}

	/**
	 * Loads a level through the LevelController
	 *
	 * @param levelStage The stage
	 * @throws IOException If we cannot load a level
	 */
	private void loadLevel(Stage levelStage, int levelNumber) throws IOException {
		LevelFileReader.loadLevelFile("./resources/level-" + levelNumber);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("level.fxml"));
		LevelController levelController = new LevelController(levelNumber, this);

		loader.setController(levelController);

		Pane root = loader.load();

		Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

		levelStage.setScene(scene);
	}

	public void finishLevel() {
		mainStage.setScene(mainScene);
		mainStage.show();
	}
}
