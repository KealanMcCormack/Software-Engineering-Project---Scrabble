import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.io.IOException;
  //Import statements

public class UI extends Application {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Board2.fxml"));  //Liking the Board fxml to UI
    Parent root = loader.load();  //Loading root
    GridPane boardGrid = (GridPane)loader.getNamespace().get("boardGrid"); //Linking grid pane for board to UI boardGrid
    Scene boardScene = new Scene(root);  //Creating scene
    GridPane frameGrid = (GridPane)loader.getNamespace().get("playerFrame");  //Linking grid pane for player frame to UI frameGrid

    public UI() throws IOException {
        Stage primaryStage = new Stage();  //Starts primary stage when called
        start(primaryStage);
    }

    public static void main(String[] args) {  //main to launch application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        JFXPanel jfxpanel = new JFXPanel();
        Stage boardStage = new Stage();  //Creating board stage and passing it into board method
        board(boardStage);
    }

    public void board(Stage boardStage) throws IOException {
        loader.setController(new UI());  //Controller for UI
        boardStage.setTitle("Scrabble!");  //Setting title
        boardStage.setScene(boardScene);  //Setting stage
        boardStage.show();  //Displaying board stage
    }
    public void setGridImage(int x, int y, Image img){  //display tile images on the board
        ObservableList<Node> children = boardGrid.getChildren(); //list of the letters in the word being played
        for (Node node : children){
            if(boardGrid.getRowIndex(node) == y && boardGrid.getColumnIndex(node) == x){
                ImageView imgview = (ImageView) node;
                imgview.setImage(img);   //displaying image on board
            }
        }
    }

    public void setPlayerFrame(Image img)  //display tiles on player frame
    {
        ObservableList<Node> children = frameGrid.getChildren();  //list of the player tiles in frame
        for(Node node : children)
        {
            ImageView iv = (ImageView) node;
            iv.setImage(img);  //displaying image in frame
        }
    }

}