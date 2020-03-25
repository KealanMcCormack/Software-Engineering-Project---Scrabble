import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.*;
import java.io.IOException;
import java.util.ArrayList;


public class Board2Controller {
    @FXML
    private GridPane boardGrid;
    @FXML
    private GridPane playerFrame;
    @FXML
    private TextArea outfield;
    @FXML
    private TextField infield;

    int cmdCutoff = 10;
    ArrayList<String> cmdOut = new ArrayList<>();

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
    public void getCMD(){
        String input = "";
        input = infield.getText();
        printCMD(input);
    }
    public void printCMD(String cmd) {
        cmdOut.add(cmd + "\n");
        String finalOut = "";
        for (int i = 0; i < cmdOut.size(); i++) {
            finalOut = finalOut + cmdOut.get(i);
        }
        outfield.setText(finalOut);
    }
}