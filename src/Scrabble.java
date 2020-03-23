import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
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
    public static void main(String[] args) throws IOException {
        Board gameBoard = new Board();  //Instances of Classes necessary to run game
        ScrabbleBag gameBag = new ScrabbleBag();
        Frame playerOneFrame = new Frame(gameBag);
        Frame playerTwoFrame = new Frame(gameBag);
        Scrabble game = new Scrabble();
        Player one = new Player();
        Player two = new Player();
        //UI ui = new UI();

        //Array to store player number
        Frame[] playerFrame = new Frame[]{playerOneFrame, playerTwoFrame};
        Player[] playerArray = new Player[]{one, two};
        boolean win = false;  //Boolean to run game until bag is empty
        String input;  //Console Input
        ArrayList<Integer> placed = new ArrayList<Integer>();  //ArrayList for storing placed tile coordinate
        int score = 0;  //Single word score

        Scanner in = new Scanner(System.in);  //Scanner to take input from user
        game.setup(playerArray, in);

        gameBoard.createBoard();

        //player 1 == 0, player 2 == 1
        while(!win){  //Game loop

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
                    break;
                case "EXCHANGE": if((game.turns % 2) == 0){   //Run exchange method for given player
                    game.exchange(gameBag, playerOneFrame, in);
                }else{
                    game.exchange(gameBag, playerTwoFrame, in);
                }
                    break;
                case "CHALLENGE": if((game.turns % 2) == 0){//presuming the challenger will be the opposing player
                    game.challenge(score, two);
                } else{
                    game.challenge(score, one);
                }
                    break;
                case "JAZZ": game.smoothJazz();  //Run smoothJazz method (Extra method included for fun)
                        break;
                default: if(input.contains("ACROSS") || input.contains("DOWN")){ //X Y across/down WORD
                    if(game.turns % 2 == 0){
                        System.out.println(game.placement(input, playerOneFrame, gameBoard, placed));
                    }else{
                        game.placement(input, playerTwoFrame, gameBoard, placed);
                    }
                }else{
                    game.turns--;
                    System.out.println("Invalid input, please retake your turn and use the command HELP to see instructions");
                }
            }

            System.out.println("Score from that word: " + score);
            if(game.turns % 2 == 0){
                System.out.println("Total score: " + one.getScore());
            }else{
                System.out.println("Total score: " + two.getScore());
            }

            game.turns++;  //Incrementing turn counter
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


    public boolean placement(String input, Frame frame, Board board, ArrayList<Integer> placed){  //Place words on board
        placed.clear();  //Clear ArrayList of coordinates
        int X, Y;  //Coordinates of placement
        String direction, word;  //Input storage

        X = Integer.parseInt(String.valueOf(input.charAt(0)));;  //Interpreting input from user
        Y = Integer.parseInt(String.valueOf(input.charAt(2)));;  //Interpreting input from user

        if(turns == 0){//For first turn makes sure placement is in the centre
            X = 7;
            Y = 7;
        }

        direction = input.substring(4, 10).trim();  //Interpreting direction from input (across/down)
        word = input.substring(10).trim();         //Interpreting word from input
        System.out.println(direction);
        System.out.println(word);
        if(!board.inBounds(X, Y, direction, word.length())){  //Invalid placement return false
            return false;
        }

        if(direction.equals("ACROSS")){  //checking validity of placement
            for(int count = 0;count < word.length();count++){
                if(!(board.getCharVal(X, Y + count) == word.charAt(count) || frame.getLetter(word.charAt(count)))){
                    System.out.println("This far gasv");
                    return false;
                }
            }

            for(int count = 0;count < word.length();count++){  //Placement of word and input to ArrayList for scoring
                if(board.getCharVal(X, Y) == ' '){
                    frame.playLetter(word.charAt(count));
                    board.placeLetter(word.charAt(count), X, Y);
                    placed.add(Y);
                }
                Y++;
            }

        }else if(direction == "DOWN"){  //checking validity of placement
            for(int count = 0;count < word.length();count++){
                if(!(board.getCharVal(X + count, Y) == word.charAt(count) || frame.getLetter(word.charAt(count)))){
                    return false;
                }
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
        uiPrintBoard(board);
        return true;
    }

    public void uiPrintBoard(Board gameBoard){
        for(int i = 0;i<15;i++){
            for(int j = 0;j<15;j++){
               switch (gameBoard.getCharVal(i,j)){
                   case 'A':

               }
            }
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

            int axis = x;
            for (int integer : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(axis++, integer) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(axis, integer));
                }
            }
            axis = x;
            for (int integer : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(axis--, integer) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(axis, integer));
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

            int axis = y;
            for (int integer : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(integer, axis++) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(integer, axis));
                }
            }

            axis = y;
            for (int integer : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(integer, axis--) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(integer, axis));
                }
            }
        }

        if(placed.size() == 7){  //Checking if all 7 letters have been played and awarding bonus score
            score = score + 50;
            System.out.println("BINGO!");
        }

        return score;
    }

    public int challenge(int wordScore, Player player){   //Challenge the players word and subtracts score
        player.increaseScore(-wordScore);
        return wordScore;
    }

    public char[] exchange(ScrabbleBag pool, Frame playerFrame, Scanner in){  //Exchange player tiles
        try {
            char[] swap = new char[7];  //swap array
            int count = 0;  //Increments for each valid tile to be exchanged
            String input;  //Input for tiles to be exchanged
            System.out.println("Which letters would you like to exchange?");
            //possibly need an escape clause

            input = in.nextLine();   //User Input

            for (int i = 0; i < input.length(); i++) {   //Placing user input into swap array
                if (input.indexOf(i) != ' ') {
                    swap[count] = (char) input.indexOf(i);
                    count++;
                }
            }
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
            File helpFile = new File("assets\\Help.txt");

            if (!helpFile.exists())
                throw new FileNotFoundException();

            Desktop desktop = java.awt.Desktop.getDesktop();
            desktop.open(helpFile);
        }

        catch (Exception e){
            System.out.println("ERROR: Help file not found, check assets folder");
        }
    }

    private void quit()  //Quits the game
    {
        System.exit(0);
    }
}

