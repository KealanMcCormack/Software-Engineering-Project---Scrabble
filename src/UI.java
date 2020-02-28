import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.awt.Desktop;
import java.net.URI;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

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
        smoothJazz();
    }

    public void board(Stage boardStage) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("assets\\board.png");
        Image board = new Image(inputStream);
    }

    public void smoothJazz()
    {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://www.youtube.com/watch?v=Tv5QRmG9ST0&list=PL7nML7u-x2dyqPDhm0g8eMlCfsrEvk89H");
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
