import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        //ADD VM OPTIONS
    }
}
