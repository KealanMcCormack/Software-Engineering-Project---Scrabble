import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
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
import java.util.ArrayList;

public class UI extends Application {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Board2.fxml"));
    Parent root = loader.load();
    GridPane boardGrid = (GridPane)loader.getNamespace().get("boardGrid");
    Scene boardScene = new Scene(root);

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
        loader.setController(new UI());
        boardStage.setTitle("Scrabble!");
        boardStage.setScene(boardScene);
        boardStage.show();
    }
    public void setGridImage(int x, int y, Image img){
        ObservableList<Node> children = boardGrid.getChildren();
        for (Node node : children){
            if(boardGrid.getRowIndex(node) == y && boardGrid.getColumnIndex(node) == x){
                ImageView imgview = (ImageView) node;
                imgview.setImage(img);
            }
        }
    }
}