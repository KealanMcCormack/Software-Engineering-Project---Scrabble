import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UI extends Application {

    public UI() throws IOException {
        Stage primaryStage = new Stage();
        start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage boardStage = new Stage();
        GridPane playerOneFrame = new GridPane();
        board(boardStage);
    }

    public void board(Stage boardStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Board2.fxml"));
        loader.setController(new UI());
        Parent root = loader.load();
        Scene boardScene = new Scene(root);
        boardStage.setTitle("Scrabble!");
        boardStage.setScene(boardScene);
        boardStage.show();
    }
}