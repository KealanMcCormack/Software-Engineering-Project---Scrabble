import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Board2Controller {
    @FXML //fx:id = "boardGrid"
    private GridPane boardGrid;
    @FXML //fx:id = "playerFrame"
    private GridPane playerFrame;

    public void setGridImage(int x, int y, Image img){
        ObservableList<Node> children = boardGrid.getChildren();
        for (Node node : children){
            if(boardGrid.getRowIndex(node) == y && boardGrid.getColumnIndex(node) == x){
                ImageView imgview = (ImageView) node;
                imgview.setImage(img);
            }
        }
    }
    public void setPlayerFrame(Image img) {
        ObservableList<Node> children = playerFrame.getChildren();
        for(Node node : children)
        {
            ImageView iv = (ImageView) node;
            iv.setImage(img);
        }
    }
}