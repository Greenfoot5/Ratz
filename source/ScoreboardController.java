import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * It displays the scoreboard *woah*
 * @author harvey
 * @version 1.0
 */
public class ScoreboardController {

    /**
     * Main function to display the scoreboard
     */
//    public Scene displayScoreboard(Stage scoreboardStage, MainMenuController mainMenu)
//    {
//        // Layout items
//        BorderPane profilePane = new BorderPane();
//        VBox profileButtons = new VBox();
//        VBox profileScoreLabels = new VBox();
//        VBox rightButtons = new VBox();
//
//        // Get the profiles
//        String[] s = {""};
//        try {
//            s = reader.getProfiles();
//        } catch (FileNotFoundException e) {
//            s[0] = "No profiles. Please Create a profile";
//        }
//
//        // Display who's logged in
//        Label loggedLabel = new Label();
//        loggedLabel.setAlignment(Pos.CENTER);
//        if (reader.getLoggedProfile() == null) {
//            loggedLabel.setText("You are logged as...");
//        } else {
//            loggedLabel.setText("You are logged as" + reader.getLoggedProfile());
//        }
//
//        // Display the best scores for a user
//        Label scoresHeading = new Label("Best ... scores:");
//        profileScoreLabels.getChildren().add(scoresHeading);
//
//        Label[] profileScore = new Label[reader.getNumberOfLevels()];
//        for (int i = 0; i < profileScore.length; i++) {
//            profileScore[i] = new Label("Lvl" + (i + 1) + " 0");
//            profileScoreLabels.getChildren().add(profileScore[i]);
//        }
//
//        // Display a button for each profile
//        Button[] profButton = new Button[s.length];
//        for (int i = 0; i < profButton.length; i++) {
//            profButton[i] = new Button(s[i]);
//            profileButtons.getChildren().add(profButton[i]);
//
//            final int ii = i;
//            profButton[i].setOnAction(event -> {
//                reader.loginProfile(profButton[ii].getText());
//                loggedLabel.setText("You are logged as " + reader.getLoggedProfile());
//                scoresHeading.setText("Best " + reader.getLoggedProfile() + "'s scores:");
//
//                for (int j = 0; j < reader.getNumberOfLevels(); j++) {
//                    try {
//                        profileScore[j].setText("Lvl" + (j + 1) + " "
//                                + reader.getBestScore(reader.getLoggedProfile(), j + 1));
//                    } catch (IOException e) {
//                        profileScore[j].setText("Lvl" + (j + 1) + " error");
//
//                    }
//                }
//            });
//        }
//
//        // Return to the main menu
//        Button goBack = new Button("Go back to main menu");
//        goBack.setOnAction(event -> {
//            profileStage.setScene(scene);
//            profileStage.show();
//        });
//
//        // Removes a profile
//        // TODO - Remove the button
//        Button removeProfile = new Button("Remove profile");
//        removeProfile.setOnAction(event -> {
//            try {
//                System.out.println(reader.getLoggedProfile());
//                reader.deleteProfile(reader.getLoggedProfile());
//                ObservableList<Node> obL = profileButtons.getChildren();
//                for (Node n : obL) {
//                    if (n.toString().contains("'" + reader.getLoggedProfile() + "'")) {
//                        System.out.println(n);
//                    }
//                }
//            } catch (IOException ignored) {
//            }
//        });
//        rightButtons.getChildren().addAll(goBack, removeProfile);
//
//        // Adds the elements to the layout
//        profilePane.setCenter(profileScoreLabels);
//        profilePane.setTop(loggedLabel);
//        profilePane.setRight(rightButtons);
//        profilePane.setLeft(profileButtons);
//
//        return new Scene(profilePane, WINDOW_WIDTH, WINDOW_HEIGHT);
//    }
}
