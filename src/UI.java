        /*Gerard Colman - 18327576
        Lukasz Filanowski - 18414616
        Kealan McCormack - 18312236*/


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class UI extends Application {

    private static final int Height = 700; //Setting stage sizes
    private static final int Width = 800;

    public static void main(String[] args) {
        launch(args); //Launching UI
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Board2.fxml"));   //Linking fxml to UI
        primaryStage.setTitle("Scrabble!");  //Setting title
        primaryStage.setScene(new Scene(root,Width,Height));  //Creating scene
        primaryStage.setWidth(Width);  //Setting dimensions
        primaryStage.setHeight(Height);
        primaryStage.show();  //Showing stage
    }

}