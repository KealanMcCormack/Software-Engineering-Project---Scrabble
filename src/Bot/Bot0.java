package Bot;

import java.util.ArrayList;
import java.util.Arrays;
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
        for(int i = 0;i<listSize;i++){
            boolean isWord = false;
            while(!isWord){
                String newWord = makeWord(list.get(i)); //Generate a random new word
                Word temp = new Word(0,0,true, newWord); //making a new word Object
                ArrayList<Word> checkingList = new ArrayList<>(); //List to pass to areWords function
                checkingList.add(temp);
                isWord = dictionary.areWords(checkingList);
                if(isWord){
                    outList.add(newWord);
                }
            }
        }
        return outList;
    }

    /*Takes in a string with * and returns string with letters in the *'s place*/
    private String makeWord(String input) {
        char[] inputArr = input.toCharArray();
        ArrayList<Integer> usedLetterIndex = new ArrayList<>(); //List of used numbers
        usedLetterIndex.add(-1); //making sure the random number generation enters the loop on the first letter
        int temp = 0;
        //int lowerBounds = 0;
        Random random = new Random();
        String frame = me.getFrameAsString(); //Getting player frame
        for (int i = 0; i < input.length(); i++) {
            if (inputArr[i] != '*') { //Incrementing pointer if the letter is already set
                i++;
            }
            temp = frame.charAt(random.nextInt(frame.length())); //Fills a section of the array with a letter from the frame
            for(int j = 0;j<usedLetterIndex.size();j++){ //Random number/letter generation loop
                if(temp == usedLetterIndex.get(j)){
                    temp = frame.charAt(random.nextInt(frame.length()));
                    j=0; //Starting checking loop again
                }
                if(j==usedLetterIndex.size()-1){ //If the number hasn't been used yet
                    usedLetterIndex.add(temp);
                    break;
                }
            }
            inputArr[i] = frame.charAt(temp);
        }
        return Arrays.toString(inputArr);
    }



    public ArrayList<String> placements(){
        char[][] array = new char[15][15];
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
                    returnArray.addAll(makeWordStringAcross(i, j, array));
                    returnArray.addAll(makeWordStringDown(i, j, array));
                }
            }
        }
        return returnArray;
    }

    public ArrayList<String> makeWordStringAcross(int row, int column, char[][] array){
        ArrayList<String> output = new ArrayList<String>();  //output array list
        int count = 0;
        int i;

        for(i = column; i < 15; i++)  //count number of blank squares for potential placement
        {
            if(array[row][i] == '*')
            {
                count++;

                if(count == 7)  //once 7 tile count is exceeded, break the loop
                {
                    break;
                }
            }
        }

        if(i+1 < 15 && array[row][i+1] != '*')  //if the potential word has a letter immediately after the end coordinate, end coordinate increased to encompass extra letters
        {
            i++;
            while(i < 15 && array[row][i] != '*')
            {
                i++;
            }
        }

        for(int x = count; x > 0; x--)  //starting from end coordinate, goes backwards across the row, removing potential placement spaces
        {
            output.add(stringMakerAcross(row, column, i, array));  //saving potential placement options to output ArrayList
            i--;

            while(array[row][i] != '*')
            {
                i--;
            }
        }

        return output;
    }

    public ArrayList<String> makeWordStringDown(int row, int column, char[][] array){
        ArrayList<String> output = new ArrayList<String>();  //output array list
        int count = 0;
        int i;

        for(i = row; i < 15; i++)  //count number of blank squares for potential placement
        {
            if(array[i][column] == '*')
            {
                count++;

                if(count == 7)  //once 7 tile count is exceeded, break the loop
                {
                    break;
                }
            }
        }

        if(i+1 < 15 && array[i+1][column] != '*')  //if the potential word has a letter immediately after the end coordinate, end coordinate increased to encompass extra letters
        {
            i++;
            while(i < 15 && array[i][column] != '*')
            {
                i++;
            }
        }

        for(int x = count; x > 0; x--)  //starting from end coordinate, goes backwards across the row, removing potential placement spaces
        {
            output.add(stringMakerDown(row, i, column, array));  //saving potential placement options to output ArrayList
            i--;

            while(array[i][column] != '*')
            {
                i--;
            }
        }

        return output;
    }


    public String stringMakerAcross(int row, int startColumn, int endColumn, char[][] array)
    {
        String output = "";  //output string
        output += row;  //adding row coordinate as letter
        output += startColumn + " A ";  //adding column and indicator if word is down or across

        for(int i = startColumn; i <= endColumn; i++)  //adding spaces between the 2 coordinates to output string
        {
            output += array[row][i];
        }

        return output;
    }

    public String stringMakerDown(int startRow, int endRow, int column, char[][] array)
    {
        String output = "";  //output string
        output += 65;  //adding row coordinate as letter
        output += column + " D ";  //adding column and indicator if word is down or across

        for(int i = startRow; i <= endRow; i++)   //adding spaces between the 2 coordinates to output string
        {
            output += array[i][column];
        }

        return output;
    }
}
