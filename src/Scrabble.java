/*Gerard Colman - 18327576
        Lukasz Filanowski - 18414616
        Kealan McCormack - 18312236*/

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

//is the placement connected?, exceptions
public class Scrabble {
    int[] letterPoints = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10}; //Array for letter score values
    // starts with blank letter then follows alphabet

    public Scrabble(){

    }

    public int multiplierLetter(int score, int x, int y, Board board){    //Multiplier calculator for special tiles
        switch (board.getTileVal(x, y)){
            case DoubleLetter: score += pointsConversion(board.getCharVal(x, y));
                break;
            case TripleLetter: score += pointsConversion(board.getCharVal(x, y)) * 2;
                break;
            default:
                return score;
        }
        return score;
    }

    public int multiplierWord(int score, int x, int y, Board board){    //Multiplier calculator for special tiles
        switch (board.getTileVal(x, y)){
            case DoubleWord: score += score;
                break;
            case TripleWord: score += (score * 2);
                break;
            case Centre: score += score;
                break;
            default:
                return score;
        }
        return score;
    }

    public int pointsConversion(char letter){  //Converts letters to points
        if(letter == '*'){
            return 0;
        }else{
            return letterPoints[letter - 65];
        }
    }

    int turns = 0;  //Turn counter
    int score = 0;
    public static void main(String[] args) throws Exception {
        Board gameBoard = new Board();  //Instances of Classes necessary to run game
        ScrabbleBag gameBag = new ScrabbleBag();
        Frame playerOneFrame = new Frame(gameBag);
        Frame playerTwoFrame = new Frame(gameBag);
        Scrabble game = new Scrabble();
        Player one = new Player();
        Player two = new Player();
        Board2Controller cont = new Board2Controller();
        Application.launch(UI.class,args);

        //Array to store player number
        Frame[] playerFrame = new Frame[]{playerOneFrame, playerTwoFrame};
        Player[] playerArray = new Player[]{one, two};
        boolean win = false;  //Boolean to run game until bag is empty
        String input;  //Console Input
        ArrayList<Integer> placed = new ArrayList<>();  //ArrayList for storing placed tile coordinate
        //Single word score
        boolean scoreShow;

        Scanner in = new Scanner(System.in);  //Scanner to take input from user
        game.setup(playerArray, in);

        gameBoard.createBoard();

        //player 1 == 0, player 2 == 1
        while(!win){  //Game loop
            if(game.turns % 2 == 0){
                playerOneFrame.refill(gameBag);
            }else{
                playerTwoFrame.refill(gameBag);
            }

            scoreShow = false;
            System.out.println(gameBoard.printBoard());  //Printing board
            System.out.println(playerFrame[game.turns % 2].displayFrame());

            System.out.println("Player " + playerArray[game.turns % 2].getName() + " your turn, what do you want to do");

            input = in.nextLine(); //Taking in user input
            input = input.toUpperCase();   //Converting input to uppercase

            switch (input){  //Checking user input to see if it matches commands
                case "QUIT":  game.quit();  //Run quit method
                    break;
                case "PASS":  System.out.println("Turn passed");
                    break;
                case "HELP": game.help();  //Run help method
                    game.turns--;
                    break;
                case "EXCHANGE": if((game.turns % 2) == 0){   //Run exchange method for given player
                    game.exchange(gameBag, playerOneFrame, in);
                }else{
                    game.exchange(gameBag, playerTwoFrame, in);
                }
                    break;
                case "CHALLENGE": if((game.turns % 2) == 0){//presuming the challenger will be the opposing player
                    if(game.challenge(game.score, two, input)){
                        System.out.println("Score removed: " +  game.score);
                        game.turns--;
                    }else{
                        System.out.println("No score removed and turn lost");
                    }
                } else{
                    if(game.challenge(game.score, one, input)){
                        System.out.println("Score removed: " +  game.score);
                        game.turns--;
                    }else{
                        System.out.println("No score removed and turn lost");
                    }
                }
                    game.turns--;
                    break;
                case "JAZZ": game.smoothJazz();  //Run smoothJazz method (Extra method included for fun)
                    break;
                case "NAME": //setting name of player to the input that is entered when prompted
                    playerArray[game.turns%2].setName(settingName());
                default: if(input.contains("ACROSS") || input.contains("DOWN")){ //X Y across/down WORD
                    if(game.turns % 2 == 0){
                        if(game.placement(input, playerOneFrame, gameBoard, placed, cont)){
                            scoreShow = true;
                            playerOneFrame.refill(gameBag);
                        }else{
                            System.out.println("Incorrect placement, please consult the help");
                            game.turns--;
                        }
                    }else{
                        if(game.placement(input, playerTwoFrame, gameBoard, placed, cont)){
                            scoreShow = true;
                            playerTwoFrame.refill(gameBag);
                        }else{
                            System.out.println("Incorrect placement, please consult the help");
                            game.turns--;
                        }
                    }
                }else{
                    game.turns--;
                    System.out.println("Invalid input, please retake your turn and use the command HELP to see instructions");
                }
            }

            if(scoreShow) {  //Displaying player scores
                if (game.turns % 2 == 0) {
                    //score = one.getScore();
                    //one.increaseScore(placed.get(placed.size() - 1));
                    System.out.println("Total score: " + one.getScore());
                    game.score = one.getScore() - game.score;
                } else {
                    //score = two.getScore();
                    //two.increaseScore(placed.get(placed.size() - 1));
                    System.out.println("Total score: " + two.getScore());
                    game.score = two.getScore() - game.score;
                }
                System.out.println("Score from that word: " + game.score);
            }

            if(gameBag.isEmpty()){//Checks if the game has ended
                win = true;
            }

            game.turns++;  //Incrementing turn counter
        }

        if(one.getScore() > two.getScore()){//Displays a winner once the gameBag is empty
            System.out.println("Congrats to player " + one.getName());
            System.out.println("You have won the game with a score of " + one.getScore());
        }else if(one.getScore() < two.getScore()){
            System.out.println("Congrats to player " + two.getName());
            System.out.println("You have won the game with a score of " + two.getScore());
        }else{
            System.out.println("Its a draw");
        }

        in.close();
    }

    public void setup(Player[] players, Scanner in){   //Setup method for start of game


        System.out.println("Time to set up some names, player 1 your up first");
        System.out.println("Please type in the name you would like to use then hit enter");

        players[0].setName(in.nextLine());  //Taking in player 1 name
        players[0].setScore(0);  //Setting player score to 0

        System.out.println("Perfect, now player 2 please input your name and press enter once correct");

        players[1].setName(in.nextLine());  //Taking in player 2 name
        players[1].setScore(0);  //Setting player score to 0

        System.out.println("Both names are now set so lets start the game");

    }


    public boolean placement(String input, Frame frame, Board board, ArrayList<Integer> placed, Board2Controller cont) throws IOException {  //Place words on board
        placed.clear();  //Clear ArrayList of coordinates
        int X, Y;  //Coordinates of placement
        String direction, word;  //Input storage
        String[] inputStrings;

        inputStrings = input.split(" ");

        if(input.charAt(0) != ' '){  //Interpreting input from user
            X = Integer.parseInt(inputStrings[0]);
        }else{
            return false;
        }

        if(input.charAt(2) != ' '){
            Y = Integer.parseInt(inputStrings[1]);  //Interpreting input from user
        }else{
            return false;
        }

        if(turns == 0){//For first turn makes sure placement is in the centre
            X = 7;
            Y = 7;
        }
        int scoreX = X, scoreY = Y;

        direction = inputStrings[2]; //Interpreting direction from input (across/down)
        word = inputStrings[3];         //Interpreting word from input

        if(word.length() < 2){
            System.out.println("Word must be at least 2 letters long");
            return false;
        }

        if(!board.inBounds(X, Y, direction, word.length())){  //Invalid placement return false
            return false;
        }
        boolean correctPlacement = false;

        if(direction.equals("ACROSS")){  //checking validity of placement
            for(int count = 0;count < word.length();count++){
                if(!(board.getCharVal(X, Y + count) == word.charAt(count) || frame.getLetter(word.charAt(count)))){
                    return false;
                }

                if(board.getCharVal(X, Y + count) == word.charAt(count)){
                    correctPlacement = true;
                }
            }

            if(!correctPlacement && turns != 0){
                System.out.println("Not connected to a placed tile");
                return false;
            }

            for(int count = 0;count < word.length();count++){  //Placement of word and input to ArrayList for scoring
                if(board.getCharVal(X, Y) == ' '){
                    frame.playLetter(word.charAt(count));
                    board.placeLetter(word.charAt(count), X, Y);
                    placed.add(Y);
                }
                Y++;
            }

        }else if(direction.equals("DOWN")){  //checking validity of placement
            for(int count = 0;count < word.length();count++){
                if(!(board.getCharVal(X + count, Y) == word.charAt(count) || frame.getLetter(word.charAt(count)))){
                    return false;
                }

                if(board.getCharVal(X, Y + count) == word.charAt(count)){
                    correctPlacement = true;
                }
            }

            if(!correctPlacement && turns != 0){
                System.out.println("Not connected to a placed tile");
                return false;
            }

            for(int count = 0;count < word.length();count++){  //Placement of word and input to ArrayList for scoring
                if(board.getCharVal(X, Y) == ' '){
                    frame.playLetter(word.charAt(count));
                    board.placeLetter(word.charAt(count), X, Y);
                    placed.add(X);
                }
                X++;
            }
        }else{
            return false;
        }
        contPlacement(word,direction,X,Y,cont);  //Calling methods to update cont board
        contFramePlacement(cont, frame);         //Calling methods to update cont player frame
        score = getScore(scoreX, scoreY, placed, direction, board);
        return true;
    }

    public void contPlacement(String word, String direction, int x, int y, Board2Controller cont) throws IOException {
        String[] wordArr = word.split("");  //String array of the individual letters in word
        for(int i = 0;i<wordArr.length;i++){ //For loop to check the letter in the word and place a corresponding letter on cont board
            if(direction.equals("ACROSS")){
                x++;
            }
            if(direction.equals("DOWN")){
                y++;
            }
            if(wordArr[i].equals("A")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\atile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("B")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\btile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("C")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\ctile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("D")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\dtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("E")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\etile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("F")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\ftile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("G")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\gtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("H")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\htile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("I")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\itile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("J")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\jtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("K")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\ktile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("L")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\ltile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("M")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\mtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("N")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\ntile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("O")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\otile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("P")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\ptile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("Q")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\qtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("R")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\rtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("S")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\stile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("T")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\ttile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("U")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\utile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("V")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\vtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("W")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\wtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("W")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\wtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("X")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\xtile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("Y")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\ytile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("Z")){
                FileInputStream inputstream = new FileInputStream("src\\assets\\ztile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
            if(wordArr[i].equals("*")){ //Blank tile
                FileInputStream inputstream = new FileInputStream("src\\assets\\blanktile.png");
                Image img = new Image(inputstream);
                cont.setGridImage(x,y,img);
            }
        }
    }

    public void contFramePlacement(Board2Controller cont, Frame frame) throws IOException
    {
        if(frame.getPlayerTiles().contains('A')){   //Checking if player frame contains the letter and displaying it in cont frame
            FileInputStream inputstream = new FileInputStream("src\\assets\\atile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('B')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\btile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('C')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\ctile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('D')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\dtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('E')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\etile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('F')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\ftile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('G')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\gtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('H')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\htile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('I')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\itile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('J')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\jtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('K')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\ktile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('L')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\ltile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('M')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\mtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('N')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\ntile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('O')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\otile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('P')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\ptile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('Q')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\qtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('R')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\rtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('S')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\stile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('T')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\ttile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('U')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\utile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('V')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\vtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('W')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\wtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('W')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\wtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('X')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\xtile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('Y')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\ytile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('Z')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\ztile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
        if(frame.getPlayerTiles().contains('*')){
            FileInputStream inputstream = new FileInputStream("src\\assets\\blanktile.png");
            Image img = new Image(inputstream);
            cont.setPlayerFrame(img);
        }
    }

    public int getScore(int x, int y, ArrayList<Integer> placed, String direction, Board gameBoard){   //Calculating score for each play
        int score = 0;

        if(direction.equals("ACROSS")){  //Calculating score of word going across
            while(gameBoard.getCharVal(x, y) != ' '){
                score = score + pointsConversion(gameBoard.getCharVal(x, y));
                y++;
            }

            for (Integer value : placed) {//Checks if any placed letters have multipliers
                score = multiplierLetter(score, x, value, gameBoard);
            }
            //Letter multipliers applied before Word multipliers as specified by scrabble rules

            for (Integer value : placed) {//Checks if any placed letters are placed on word multipliers
                score = multiplierWord(score, x, value, gameBoard);
            }

            int axis = x + 1;
            for (int integer : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(axis, integer) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(axis, integer));
                    axis++;
                }
            }
            axis = x - 1;
            for (int integer : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(axis, integer) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(axis, integer));
                    axis--;
                }
            }

        }else{
            while(gameBoard.getCharVal(x, y) != ' '){
                score = score + pointsConversion(gameBoard.getCharVal(x, y));
                x++;
            }

            for (Integer value : placed) {//Checks if any placed letters have multipliers
                score = multiplierLetter(score, value, y, gameBoard);
            }
            //Letter multipliers applied before Word multipliers as specified by scrabble rules

            for (Integer value : placed) {//Checks if any placed letters are placed on word multipliers
                score = multiplierWord(score, value, y, gameBoard);
            }

            int axis = y + 1;
            for (int integer : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(integer, axis) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(integer, axis));
                    axis++;
                }
            }

            axis = y - 1;
            for (int integer : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(integer, axis--) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(integer, axis));
                    axis--;
                }
            }
        }

        if(placed.size() == 7){  //Checking if all 7 letters have been played and awarding bonus score
            score = score + 50;
            System.out.println("BINGO!");
        }

        return score;
    }

    public boolean challenge(int wordScore, Player player, String word){   //Challenge the players word and subtracts score
        String[] inputStrings;
        inputStrings = word.split(" ");

        if(dictionaryCheck(inputStrings[4])){
            player.increaseScore(-wordScore);
            System.out.println("Correctly challenged, score removed");
            return true;
        }

        return false;
    }

    public boolean dictionaryCheck(String word){  //check to make sure the word is in the dictionary
        if(!dictionarySearch(word)){
            return false;
        }

        return true;
    }

    public boolean dictionarySearch(String word){  //searching dictionary for word entered by player
        char first =  word.charAt(0); //first character in word
        int length = word.length();  //length of word
        String check;
        FileReader file;
        try {
            file = new FileReader("src\\assets\\sowpods.txt");  //accessing dictionary file in assets folder
            BufferedReader in =  new BufferedReader(file);  //buffered reader

            while((check = in.readLine()) != null && check.charAt(0) != first);

            while((check = in.readLine()) != null && !check.equals(word) && check.charAt(0) == first) ;

            if(check == null) {
                return false;
            }

            if(check.equals(word)) {
                return true;
            }else {
                return false;
            }

        } catch (FileNotFoundException e) {  //handling exception if file not found
            System.out.println("ERROR: File not found, check assets folder");
            e.printStackTrace();
        }

        catch(IOException ex) {
            System.out.print("ERROR: Couldn't find the file");
        }
        return false;
    }

    public static String settingName()
    {
        System.out.println("Enter the name you would like: ");
        Scanner in = new Scanner(System.in);

        String output = in.nextLine();

        return output;
    }

    public char[] exchange(ScrabbleBag pool, Frame playerFrame, Scanner in){  //Exchange player tiles
        try {
            char[] swap = {' ', ' ', ' ', ' ', ' ', ' ', ' '};  //swap array

            int count = 0;  //Increments for each valid tile to be exchanged
            String input;  //Input for tiles to be exchanged
            System.out.println("Which letters would you like to exchange?");
            //possibly need an escape clause

            input = in.nextLine();   //User Input

            for (int i = 0; i < input.length(); i++) {   //Placing user input into swap array
                if (input.charAt(i) != ' ') {
                    swap[count] = input.charAt(i);
                    count++;
                }
            }

            count--;

            while (count >= 0) {  //Checking validity of tiles to be replaced
                if (!playerFrame.getLetter(swap[count])) {
                    System.out.println("Sorry one of letters input is not in the frame");
                    throw new IllegalArgumentException("Invalid argument");
                }
                count--;
            }

            playerFrame.swap(swap, pool); //Swapping tiles
            return swap;

        } catch (IllegalArgumentException e) {
            return exchange(pool, playerFrame, in);//Runs the method again
        }
    }

    private void smoothJazz() {  //Plays jazz
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://www.youtube.com/watch?v=Tv5QRmG9ST0&list=PL7nML7u-x2dyqPDhm0g8eMlCfsrEvk89H");
            desktop.browse(oURL);
        } catch (Exception e) {
            System.out.println("Unable to jazz");
        }
    }

    private void help() throws IOException {  //Displays help file to user

        try {
            File helpFile = new File("src\\assets\\Help.txt");
            Desktop desktop = java.awt.Desktop.getDesktop();
            desktop.open(helpFile);
        } catch(Exception e)
        {
            System.out.print("ERROR: Help file not found");
        }

    }

    private void quit()  //Quits the game
    {
        System.exit(0);
    }
}