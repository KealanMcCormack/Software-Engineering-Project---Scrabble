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

    ArrayList<Word> outList = new ArrayList<>(); //Output

    Word challengeUpdate;
    
    public String getCommand() {
        String command = "PASS";

        if(turnCount == 0)  //on first turn, set name of bot
        {
            return "NAME DUMB_DUMBER_DUMBEST";
        }

        if(!getError()){
            outList.clear();
            outList.addAll(searchDictionary(placements()));  //find potential placements
        }

        Word temp = challengeUpdate;  //create temporary word as update for challenge
        String turnOutput = getPlacementRanking(outList);  //output of turn saved as the top plays available

        if(getError())
        {
            deleteFromChallengeArray(temp);  //deleting word from challenge array if error is thrown

            if(turnOutput == null)  //if there's no word to play, pass the turn
            {
                return command;
            }
        }

        if(challenge())  //if opponents word isn't in dictionary, return challenge and challenge
        {
            return "CHALLENGE";
        }


        command = turnOutput;  //setting command to turn output

        updateChallengeArray();  //update array with word

        turnCount++;  //increment turn count
        return command;  //return command
    }

     /**
      * 1. Change to output array of word objects
      * 2. list contains (XY DIRECTION WORD)
      * */
    public ArrayList searchDictionary(ArrayList<String> list) {
        ArrayList<Word> outList = new ArrayList<>(); //Output
        int listSize = list.size();
        for(int i = 0;i<listSize;i++){
            boolean isWord = false;
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
            try{
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
            }catch(Exception e){
                System.out.println("COULDN'T FIND DICTIONARY FILE (GERARD CRIES)");
            }
        }
        return outList;
    }

    char[][] challengeArray = new char[15][15];

    public boolean challenge(){ // Needs to be called before play and after to not call challenge on our own play
        String check = "";
        char direction = ' ';
        int count = 0,x = 0,y = 0;

        for(int i = 0;i < 15;i++){   //for loops to traverse board
            for(int j = 0;j < 15;j++){
                char onBoard = board.getSquareCopy(i, j).getTile().getLetter();  //checking current letter on tile
                if(onBoard != '_' && challengeArray[i][j] != onBoard){  //if letter isn't blank and not in previous word, add it to the challenge array
                    challengeArray[i][j] = onBoard;
                    check += onBoard;
                    if(count == 0){  //setting coordinate of letter
                        x = i;
                        y = j;
                        count++;
                    }
                    if(count == 1){  //setting direction of word
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


            Word wordA = new Word(x, y, true, check);  //creating word across

            Word wordD = new Word(x, y, false, check); //creating word down



        ArrayList<Word> checkingList = new ArrayList<>(); //List to pass to areWords function
        if(direction == 'A'){
            checkingList.add(wordA);
        }else{
            checkingList.add(wordD);
        }


        return dictionary.areWords(checkingList);  //returning true/false depending on if the word is valid

    }

    public void updateChallengeArray(){ //Updates challenge Array with our placement

        int x = challengeUpdate.getRow(); //Sets basic variables
        int y = challengeUpdate.getColumn();

        char direction;

        if(challengeUpdate.isHorizontal()){
            direction = 'A';
        }else{
            direction = 'D';
        }

        String phrase = challengeUpdate.getLetters();

        if(direction == 'A'){ //Adds the word to the array
            for(int count = 0;count < phrase.length();count++){
                challengeArray[x][y + count] = phrase.charAt(count);
            }
        }else{
            for(int count = 0;count < phrase.length();count++){
                challengeArray[x + count][y] = phrase.charAt(count);
            }
        }
    }

    public void deleteFromChallengeArray(Word input){ //Removes our previous play from the array

        int x = input.getRow(); //Sets up basic variables
        int y = input.getColumn();

        char direction;

        if(input.isHorizontal()){
            direction = 'A';
        }else{
            direction = 'D';
        }

        String phrase = input.getLetters();

        if(direction == 'A'){ //Fills the spaces with blanks
            for(int count = 0;count < phrase.length();count++){
                challengeArray[x][y + count] = '_';
            }
        }else{
            for(int count = 0;count < phrase.length();count++){
                challengeArray[x + count][y] = '_';
            }
        }
    }

    public ArrayList<String> placements(){ //Finds valid placements for words
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

    int[] letterPoints = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10}; //Array for letter score values

    public int pointsConversion(char letter){  //Converts letters to points
        if(letter == '_'){
            return 0;
        }else{
            return letterPoints[letter - 65];
        }
    }


    public boolean getError(){
        return info.getLatestInfo().contains("Error");
    } //Checks if an error code was printed

    private ArrayList<Coordinates> newLetterCoords;

    public String getPlacementRanking(ArrayList<Word> rankingArray){

        if(rankingArray.size() == 0){
            challengeUpdate = null;
            return null;
        }

        int highest = 0, highestIndex = 0;
        for(int count = 0;count < rankingArray.size();count++){
            newLetterCoords = new ArrayList<>();

            int x = rankingArray.get(count).getRow(); //Gets basic info
            int y = rankingArray.get(count).getColumn();
            String lookUp = rankingArray.get(count).getLetters();
            char direction = ' ';
            if(rankingArray.get(count).isHorizontal()){
                direction = 'A';
            }else{
                direction = 'D';
            }

            //Find placement co-ordinates
            for(int i = 0;i < lookUp.length();i++){
                if(direction == 'A'){
                    if(board.getSquareCopy(x, y + i).getTile().getLetter() != '_'){
                        newLetterCoords.add(new Coordinates(x,y + i));
                    }
                }else{
                    if(board.getSquareCopy(x + i, y).getTile().getLetter() != '_'){
                        newLetterCoords.add(new Coordinates(x + i,y));
                    }
                }
            }

            ArrayList<Word> a = new ArrayList<>();
            a.add(rankingArray.get(count));

            if(getAllPoints(a) > highest){ //Finds highest and keeps index
                highestIndex = count;
                highest = getAllPoints(a);
            }

        }

        challengeUpdate = rankingArray.get(highest); //Updates variable

        return wordToString(rankingArray.remove(highestIndex));

    }

    public String wordToString(Word input){ //Makes a word into a correctly formatted string

        int x = input.getRow(); //Set basic variable
        int y = input.getColumn();
        char direction;
        String blankReplace = "";

        if(input.isHorizontal()){
            direction = 'A';
        }else{
            direction = 'D';
        }

        String phrase = input.getLetters();

        //Check for use of blank tile
        if(me.getFrameAsString().contains("_")){
            if(direction == 'A'){
                for(int i = 0;i < phrase.length();i++){
                    if(me.getFrameAsString().indexOf(phrase.charAt(i)) == -1 && board.getSquareCopy(x, y + i).getTile().getLetter() == '_'){
                        blankReplace += phrase.charAt(i);
                        phrase.replace(phrase.charAt(i), '_');
                    }
                }
            }else{
                for(int i = 0;i < phrase.length();i++){
                    if(me.getFrameAsString().indexOf(phrase.charAt(i)) == -1 && board.getSquareCopy(x + i, y).getTile().getLetter() == '_'){
                        blankReplace += phrase.charAt(i);
                        phrase.replace(phrase.charAt(i), '_');
                    }
                }
            }
        }

        //Get ready for correct layout for command
        char row = (char) (x + 65);
        if(blankReplace != ""){
            return " " + row + "" + y + " " + direction + " " + phrase + " " + blankReplace;
        }

        return " " + row + "" + y + " " + direction + " " + phrase;
    }


    private boolean isAdditionalWord(int r, int c, boolean isHorizontal) {
        if ((isHorizontal &&
                (r>0 && board.getSquareCopy(r-1, c).getTile().getLetter() != '_' || (r<15-1 && board.getSquareCopy(r+1, c).getTile().getLetter() != '_'))) ||
                (!isHorizontal) &&
                        (c>0 && board.getSquareCopy(r, c - 1).getTile().getLetter() != '_' || (c<15-1 && board.getSquareCopy(r, c + 1).getTile().getLetter() != '_')) ) {
            return true;
        }
        return false;
    }

    private Word getAdditionalWord(int mainWordRow, int mainWordCol, boolean mainWordIsHorizontal, Word mainWord) {
        int firstRow = mainWordRow;
        int firstCol = mainWordCol;

        int x = mainWordRow, y = mainWordCol;
        for(int i = 0;i < mainWord.getLetters().length();i++){
            challengeArray[x][y] = mainWord.getLetter(i);
            if(mainWordIsHorizontal){
                y++;
            }else{
                x++;
            }
        }
        // search up or left for the first letter
        while (firstRow >= 0 && firstCol >= 0 && challengeArray[firstCol][firstRow] != '_') {
            if (mainWordIsHorizontal) {
                firstRow--;
            } else {
                firstCol--;
            }
        }
        // went too far
        if (mainWordIsHorizontal) {
            firstRow++;
        } else {
            firstCol++;
        }
        // collect the letters by moving down or right
        String letters = "";
        int r = firstRow;
        int c = firstCol;
        while (r<15 && c< 15 && challengeArray[r][c] != '_') {
            letters = letters + challengeArray[r][c];
            if (mainWordIsHorizontal) {
                r++;
            } else {
                c++;
            }
        }

        x = mainWordRow;
        y = mainWordCol;
        for(int i = 0;i < mainWord.getLetters().length();i++){
            challengeArray[x][y] = '_';
            if(mainWordIsHorizontal){
                y++;
            }else{
                x++;
            }
        }

        return new Word (firstRow, firstCol, !mainWordIsHorizontal, letters);
    }

    public ArrayList<Word> getAllWords(Word mainWord) { //Scoring method
        ArrayList<Word> words = new ArrayList<>();
        words.add(mainWord);

        int r = mainWord.getFirstRow();
        int c = mainWord.getFirstColumn();

        for (int i=0; i<mainWord.length(); i++) {
            if (newLetterCoords.contains(new Coordinates(r,c))) {
                if (isAdditionalWord(r, c, mainWord.isHorizontal())) {
                    words.add(getAdditionalWord(r, c, mainWord.isHorizontal(), mainWord));
                }
            }
            if (mainWord.isHorizontal()) {
                c++;
            } else {
                r++;
            }
        }
        return words;
    }

    private int getWordPoints(Word word) { //Scoring method
        int wordValue = 0;
        int wordMultipler = 1;
        int r = word.getFirstRow();
        int c = word.getFirstColumn();
        for (int i = 0; i<word.length(); i++) {
            int letterValue = pointsConversion(word.getDesignatedLetter(i));
            if (newLetterCoords.contains(new Coordinates(r,c))) {
                wordValue = wordValue + letterValue * board.getSquareCopy(r, c).getLetterMuliplier();
                wordMultipler = wordMultipler * board.getSquareCopy(r, c).getWordMultiplier();
            } else {
                wordValue = wordValue + letterValue;
            }
            if (word.isHorizontal()) {
                c++;
            } else {
                r++;
            }
        }
        return wordValue * wordMultipler;
    }

    public int getAllPoints(ArrayList<Word> words) { //Scoring method
        int points = 0;
        for (Word word : words) {
            points = points + getWordPoints(word);
        }
        if (newLetterCoords.size() == Frame.MAX_TILES) {
            points = points + 50;
        }
        return points;
    }


}
