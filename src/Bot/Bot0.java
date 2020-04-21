package Bot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

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
        /*
        * 1. Game loop - Lukaszzzzzzzzz
        * 2. Dictionary Overhaul - Gerard
        * 3. getError (use get info) - Kealan
        * 4. Pass function - Lukas
        * 5. Commenting - Gerard
        * 6. Testing - together <3
        * 7. Scoring - Kealan
         */
    }
     /**
      * 1. Change to output array of word objects
      * 2. list contains (XY DIRECTION WORD)
      * 3. Parse that shit white boy
      * */
    public ArrayList searchDictionary(ArrayList<String> list) throws FileNotFoundException {
        ArrayList<Word> outList = new ArrayList<>(); //Output
        int listSize = list.size();
        for(int i = 0;i<listSize;i++){ //Cycles through list
            String[] listStrings = list.get(i).split(" "); //Parsing list string and adding it to word object
            int x = listStrings[0].charAt(0);
            int y = listStrings[0].charAt(1);
            boolean isHorizontal;
            if(listStrings[1].equals("A")){
                isHorizontal = true;
            }else{
                isHorizontal = false;
            }
            /*Searching Dictionary file for correct word*/
            String input = listStrings[3]; //Word were looking for
            FileInputStream inputStream = new FileInputStream("resources\\sowpods.txt");
            Scanner in = new Scanner(inputStream);
            while(in.hasNextLine()){
                String dictionaryEntry = in.nextLine();
                if(dictionaryEntry.charAt(0) == input.charAt(0)){ //Only runs for loop if it starts with correct letter
                    for(int j = 0;i<input.length();i++){
                        if(input.charAt(j) != '*'){
                            if(input.charAt(j) != dictionaryEntry.charAt(j)){
                                break;
                            }
                            j++;
                        }
                        if( (dictionaryEntry.charAt(j) == dictionaryEntry.charAt(dictionaryEntry.length()-1)) && j == input.length() - 1 ){ //If satisfactory word is found
                            Word newWord = new Word(x,y,isHorizontal,dictionaryEntry);
                            outList.add(newWord);
                        }
                    }
                }
            }
        }
        return outList;
    }

    char[][] challengeArray = new char[15][15];

    public boolean challenge(){ // Needs to be called before play and after to not call challenge on our own play
        String check = "";
        char direction = ' ';
        int count = 0,x = 0,y = 0;

        for(int i = 0;i < 15;i++){
            for(int j = 0;j < 15;j++){
                char onBoard = board.getSquareCopy(i, j).getTile().getLetter();
                if(onBoard != '_' && challengeArray[i][j] != onBoard){
                    challengeArray[i][j] = onBoard;
                    check += onBoard;
                    if(count == 0){
                        x = i;
                        y = j;
                        count++;
                    }
                    if(count == 1){
                        if(x == i){
                            direction = 'A';
                        }
                        if(y == i){
                            direction = 'D';
                        }
                    }
                }
            }
        }


            Word wordA = new Word(x, y, true, wordCreate(check));

            Word wordD = new Word(x, y, false, wordCreate(check));



        ArrayList<Word> checkingList = new ArrayList<>(); //List to pass to areWords function
        if(direction == 'A'){
            checkingList.add(wordA);
        }else{
            checkingList.add(wordD);
        }


        return dictionary.areWords(checkingList);

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

        return output.toUpperCase();
    }

    public String stringMakerDown(int startRow, int endRow, int column, char[][] array)
    {
        String output = "";  //output string
        output += startRow;  //adding row coordinate as letter
        output += column + " D ";  //adding column and indicator if word is down or across

        for(int i = startRow; i <= endRow; i++)   //adding spaces between the 2 coordinates to output string
        {
            output += array[i][column];
        }

        return output.toUpperCase();
    }

    public int multiplierLetter(int score, char letter, int x, int y){    //Multiplier calculator for special tiles
        switch (board.getSquareCopy(x, y).getLetterMuliplier()){
            case 2: score += pointsConversion(letter);
                break;
            case 3: score += pointsConversion(letter) * 2;
                break;
            default:
                return score;
        }
        return score;
    }

    public int multiplierWord(int score, int x, int y){    //Multiplier calculator for special tiles
        switch (board.getSquareCopy(x, y).getWordMultiplier()){
            case 2: score += score;
                break;
            case 3: score += (score * 2);
                break;
            default:
                return score;
        }
        return score;
    }

    int[] letterPoints = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10}; //Array for letter score values

    public int pointsConversion(char letter){  //Converts letters to points
        if(letter == '_'){
            return 0;
        }else{
            return letterPoints[letter - 65];
        }
    }


    //input is the string with co-ordinates etc with a word in to be scored
    //rawInput is the string with co-ordinates etc with a word made of asterisks

    //Should possibly use the words off the main word for score as well
    //This will be easier to test though, best to add that later
    public int getScore(String input, String rawInput){
        String[] inputStrings, rawInputStrings;
        inputStrings = input.split(" ");
        rawInputStrings = rawInput.split(" ");
        int score = 0;

        int x, y;

        x = (int) inputStrings[0].charAt(0);
        y = (int) inputStrings[0].charAt(1);

        for(int count = 0;count < inputStrings[2].length();count++){
            score += pointsConversion(inputStrings[2].charAt(count));

            if(inputStrings[1] == "A" && rawInputStrings[2].charAt(count) == '*'){
                score += multiplierLetter(score, inputStrings[2].charAt(count), x, y + count);
            }else{
                score += multiplierLetter(score, inputStrings[2].charAt(count), x + count, y);
            }

        }

        for(int count = 0;count < inputStrings[2].length();count++){
            if(inputStrings[1] == "A" && rawInputStrings[2].charAt(count) == '*'){
                score += multiplierWord(score, x, y + count);
            }else{
                score += multiplierWord(score, x + count, y);
            }

        }

        return score;
    }
}
