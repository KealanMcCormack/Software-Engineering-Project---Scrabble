/*Gerard Colman - 18327576
        Lukasz Filanowski - 18414616
        Kealan McCormack - 18312236*/

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
    private GridPane boardGrid;  //GridPane for board
    @FXML
    private GridPane playerFrame;  //GridPane for frame
    @FXML
    private TextArea outfield;  //Text output field
    @FXML
    private TextField infield;  //Text input field

    int cmdCutoff = 10;  //Cutoff for number of commands displayed
    ArrayList<String> cmdOut = new ArrayList<>();  //ArrayList of commands

    public void setGridImage(int x, int y, Image img){   //Setting tile images onto board
        ObservableList<Node> children = boardGrid.getChildren();  //Creating list of individual letters from words
        for (Node node : children){
            if(boardGrid.getRowIndex(node) == y && boardGrid.getColumnIndex(node) == x){
                ImageView imgview = (ImageView) node;
                imgview.setImage(img);  //Setting image in ImageView contained in GridPane
            }
        }
    }
    public void setPlayerFrame(Image img) {  //Setting tile images to frame
        ObservableList<Node> children = playerFrame.getChildren();  //Creating list of individual letters from frame
        for(Node node : children)
        {
            ImageView iv = (ImageView) node;
            iv.setImage(img);   //Setting image in ImageView contained in GridPane
        }
    }
    public void getCMD(){  //getting command input from TextField
        String input = "";
        input = infield.getText();
        printCMD(input);
    }
    public void printCMD(String cmd) {  //displaying text to player in TextArea
        cmdOut.add(cmd + "\n");
        String finalOut = "";
        for (int i = 0; i < cmdOut.size(); i++) {
            finalOut = finalOut + cmdOut.get(i);
        }
        outfield.setText(finalOut);
    }
}