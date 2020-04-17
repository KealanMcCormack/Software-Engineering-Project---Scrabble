package Bot;

import java.util.ArrayList;
import java.util.Random;

public class Bot0 implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the game objects
    // It may only inspect the state of the board and the player objects

    private PlayerAPI me;
    private OpponentAPI opponent;
    private BoardAPI board;
    private UserInterfaceAPI info;
    private DictionaryAPI dictionary;
    private int turnCount;

    Bot0(PlayerAPI me, OpponentAPI opponent, BoardAPI board, UserInterfaceAPI ui, DictionaryAPI dictionary) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.info = ui;
        this.dictionary = dictionary;
        turnCount = 0;
    }

    public String getCommand() {
        // Add your code here to input your commands
        // Your code must give the command NAME <botname> at the start of the game
        String command = "";
        switch (turnCount) {
            case 0:
                command = "NAME Bot0";
                break;
            case 1:
                command = "PASS";
                break;
            case 2:
                command = "HELP";
                break;
            case 3:
                command = "SCORE";
                break;
            case 4:
                command = "POOL";
                break;
            default:
                command = "H8 A AN";
                break;
        }
        turnCount++;
        return command;
    }
     /**
      * 1. Get Frame Reference - DONE
      * 2. Fill * in list with letters from frame
      * 3. if letter made is a word put into outlist
      * 4. if not try again
      * 5. return outlist
      * */
    public ArrayList searchDictionary(ArrayList<String> list){
        ArrayList<String> outList = new ArrayList<>(); //Output
        int listSize = list.size();
        String test = makeWord(list.get(0));
        System.out.println(test);
    }

    /*Takes in a string with * and returns string with letters in the *'s place*/
    private String makeWord(String input){
        char[] inputArr = input.toCharArray();
        Random random = new Random();
        String frame = me.getFrameAsString(); //Getting player frame
        for(int i = 0;i<input.length();i++){
            if(inputArr[i] != '*'){ //Incrementing pointer if the letter is already set
                i++;
            }
            inputArr[i] = frame.charAt(random.nextInt(frame.length())); //Fills a section of the array with a letter from the frame
        }
        return inputArr.toString();
    }

    public ArrayList<String> placements(){
        char array[][] = new char[15][15];
        ArrayList<String> returnArray = new ArrayList<String>();

        for(int i = 0;i < 15;i++){ //Fills the array with the current board layout
            for(int j = 0;j < 15;j++){
                if(board.getSquareCopy(i,j).getTile().getLetter() == '_'){
                    array[i][j] = '*';
                }else{
                    array[i][j] = board.getSquareCopy(i,j).getTile().getLetter();
                }
            }
        }

        //Figure out what placements are available

        for(int i = 0;i < 15;i++){ //Fills the array with the current board layout
            for(int j = 0;j < 15;j++){
                if(array[i][j] != '*'){
                    //Add a helper to make the strings to be added to the array being returned
                    returnArray.addAll();



                }
            }
        }


    }
}
