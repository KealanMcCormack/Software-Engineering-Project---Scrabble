import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

//getscore, firstplacement, exceptions
public class Scrabble {
    int[] letterPoints = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10}; //Array for letter score values
    // starts with blank letter then follows alphabet

    public Scrabble(){

    }

    public int multiplier(int score, int x, int y, Board board){    //Multiplier calculator for special tiles
        switch (board.getTileVal(x, y)){
            case DoubleLetter: score += pointsConversion(board.getCharVal(x, y));
                break;
            case TripleLetter: score += pointsConversion(board.getCharVal(x, y)) * 2;
                break;
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

    public static void main(String[] args) throws IOException {
        Board gameBoard = new Board();  //Instances of Classes necessary to run game
        ScrabbleBag gameBag = new ScrabbleBag();
        Frame playerOneFrame = new Frame(gameBag);
        Frame playerTwoFrame = new Frame(gameBag);
        Scrabble game = new Scrabble();
        Player one = new Player();
        Player two = new Player();

        Player playerArray[] = {one, two};  //Array to store player number
        boolean win = false;  //Boolean to run game until bag is empty
        String input;  //Console Input
        ArrayList<Integer> placed = new ArrayList<Integer>();  //ArrayList for storing placed tile coordinate
        int turns = 1;  //Turn counter
        int score = 0;  //Single word score

        game.setup(playerArray);


        Scanner in = new Scanner(System.in);  //Scanner to take input from user
        gameBoard.createBoard();

        while(!win){  //Game loop
            gameBoard.printBoard();  //Printing board
            System.out.println("Player " + (turns % 2) + " your turn, what do you want to do");


            input = in.nextLine(); //Taking in user input
            input.toUpperCase();   //Converting input to uppercase

            switch (input){  //Checking user input to see if it matches commands
                case "QUIT":  game.quit();  //Run quit method
                    break;
                case "PASS":  System.out.println("Turn passed");
                    break;
                case "HELP": game.help();  //Run help method
                    break;
                case "EXCHANGE": if((turns % 2) == 1){   //Run exchange method for given player
                    game.exchange(gameBag, playerOneFrame);
                }else{
                    game.exchange(gameBag, playerTwoFrame);
                }
                    break;
                case "CHALLENGE": if((turns % 2) == 0){//presuming the challenger will be the opposing player
                    game.challenge(score, one);
                } else{
                    game.challenge(score, two);
                }
                    break;
                case "JAZZ": game.smoothJazz();  //Run smoothJazz method (Extra method included for fun)
                        break;
                default: if(input.contains("across") || input.contains("down") || input.contains("ACROSS") || input.contains("DOWN")){ //X Y across/down WORD
                    if(turns % 2 == 1){
                        game.placement(input, playerOneFrame, gameBoard, placed);
                    }else{
                        game.placement(input, playerTwoFrame, gameBoard, placed);
                    }
                }else{
                    turns--;
                    System.out.println("Invalid input, please retake your turn and use the command HELP to see instructions");
                }
            }

            System.out.println("Score from that word: " + score);
            if(turns % 2 == 1){
                System.out.println("Total score: " + one.getScore());
            }else{
                System.out.println("Total score: " + two.getScore());
            }

            turns++;  //Incrementing turn counter
        }
        in.close();
    }

    public void setup(Player[] players){   //Setup method for start of game
        Scanner in = new Scanner(System.in);  //Creating scanner

        System.out.println("Time to set up some names, player 1 your up first");
        System.out.println("Please type in the name you would like to use then hit enter");

        players[0].setName(in.nextLine());  //Taking in player 1 name
        players[0].setScore(0);  //Setting player score to 0

        System.out.println("Perfect, now player 2 please input your name and press enter once correct");

        players[1].setName(in.nextLine());  //Taking in player 2 name
        players[1].setScore(0);  //Setting player score to 0

        System.out.println("Both names are now set so lets start the game");
        in.close();  //Closing Scanner
    }


    public boolean placement(String input, Frame frame, Board board, ArrayList<Integer> placed){  //Place words on board
        placed.clear();  //Clear ArrayList of coordinates
        int X, Y;  //Coordinates of placement
        String direction, word;  //Input storage

        X = input.charAt(0);  //Interpreting input from user
        Y = input.charAt(2);  //Interpreting input from user

        direction = input.substring(4, 8).trim();  //Interpreting direction from input (across/down)
        word = input.substring(10).trim();         //Interpreting word from input

        if(!board.inBounds(X, Y)){  //Invalid placement return false
            return false;
        }

        if(direction == "ACROSS"){  //checking validity of placement
            for(int count = 0;count < word.length();count++){
                if(board.getCharVal(X, Y + count) != word.charAt(count) || !frame.getLetter(word.charAt(count))){
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
                if(board.getCharVal(X + count, Y) != word.charAt(count) || !frame.getLetter(word.charAt(count))){
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
        return true;
    }

    public int getScore(int x, int y, ArrayList<Character> placed, String direction, Board gameBoard){   //Calculating score for each play
        int score = 0, add = 0, multiplier = 1;

        if(direction == "ACROSS"){  //Calculating score of word going across
            while(gameBoard.getCharVal(x, y) != ' '){
                score = score + pointsConversion(gameBoard.getCharVal(x, y));
                y++;
            }

            //multiplier from arraylist, letter then word


            int axis = x;
            for (Character character : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(axis++, character) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(axis, character));
                }
            }
            axis = x;
            for (Character character : placed) {  //Checking either side of letter placed to see if any other additional word is attached which will increase score
                while (gameBoard.getCharVal(axis--, character) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(axis, character));
                }
            }
            //check other axis
        }


        if(placed.size() == 7){  //Checking if all 7 letters have been played and awarding bonus score
            score = score + 50;
            System.out.println("BINGO!");
        }

        return score;
    }

    public boolean challenge(int wordScore, Player player){   //Challenge the players word and subtracts score
        player.increaseScore(-wordScore);
        return true;
    }

    public char[] exchange(ScrabbleBag pool, Frame playerFrame){  //Exchange player tiles
        try {
            char[] swap = new char[7];  //swap array
            int count = 0;  //Increments for each valid tile to be exchanged
            String input;  //Input for tiles to be exchanged
            System.out.println("Which letters would you like to exchange?");
            //possibly need an escape clause
            Scanner in = new Scanner(System.in);  //Initiating Scanner
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
            return exchange(pool, playerFrame);//Runs the method again
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

