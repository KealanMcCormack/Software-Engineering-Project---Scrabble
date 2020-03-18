import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage boardStage = new Stage();
        Stage playerStage = new Stage();
        board(boardStage);
        //playerSelect(playerStage);
    }

    public void board(Stage boardStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Board.fxml"));
        Scene boardScene = new Scene(root);
        boardStage.setTitle("Scrabble!");
        boardStage.setScene(boardScene);
        boardStage.show();
    }
    public void playerSelect(Stage selectStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene playerScene = new Scene(root);
        selectStage.setTitle("Player Selection!");
        selectStage.setScene(playerScene);
        selectStage.show();
    }
}