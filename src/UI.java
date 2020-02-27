import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UI extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label lab = new Label("Hello");
        Scene scene = new Scene(lab);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void board(Stage boardStage) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("assests\board.png");
        Image board = new Image(inputStream);
    }
}
