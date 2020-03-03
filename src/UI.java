import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.awt.Desktop;
import java.net.URI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Stage boardStage = new Stage();
        board(boardStage);
    }

    public void board(Stage boardStage) throws FileNotFoundException {
        /*Stage Setup*/
        boardStage.setHeight(700);
        boardStage.setWidth(700);
        boardStage.setTitle("Scrabble");
        StackPane layout = new StackPane();
        /*Getting Image*/
        FileInputStream inputStream = new FileInputStream("assets\\board.png");
        Image board = new Image(inputStream);
        ImageView boardPNG = new ImageView(board);
        /*Image Processing*/
        boardPNG.setFitHeight(500);
        boardPNG.setFitWidth(500);
        boardPNG.setX(0);
        boardPNG.setY(0);
        /*Adding image to board and displaying*/
        Group root = new Group(boardPNG);
        layout.getChildren().add(root);
        Scene boardLayout = new Scene(layout, 500, 500);
        boardStage.setScene(boardLayout);
        boardStage.show();
    }

    public void smoothJazz() {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://www.youtube.com/watch?v=Tv5QRmG9ST0&list=PL7nML7u-x2dyqPDhm0g8eMlCfsrEvk89H");
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
