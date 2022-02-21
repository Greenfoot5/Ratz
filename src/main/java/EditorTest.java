import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

//DELETE THIS CLASS AFTER WE NO LONGER NEED TESTING

public class EditorTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "editor.fxml"));
        EditorController editorController = new EditorController();

        loader.setController(editorController);

        Pane root = loader.load();

        Scene scene = new Scene(root, root.getPrefWidth(),
                root.getPrefHeight());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
