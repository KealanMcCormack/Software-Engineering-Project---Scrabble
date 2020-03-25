import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class UI extends Application {

    private static final int Height = 900;
    private static final int Width = 700;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Board2.fxml"));
        primaryStage.setTitle("Scrabble!");
        primaryStage.setScene(new Scene(root,Width,Height));
        primaryStage.setWidth(Width);
        primaryStage.setHeight(Height);
        primaryStage.show();
    }

}