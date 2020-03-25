import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class UI extends Application {

    private static final int Height = 700;
    private static final int Width = 800;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Board2.fxml"));
        primaryStage.setTitle("Scrabble!");
        primaryStage.setScene(new Scene(root,Width,Height));
        primaryStage.setWidth(Width);
        primaryStage.setHeight(Height);
        primaryStage.show();
    }

}