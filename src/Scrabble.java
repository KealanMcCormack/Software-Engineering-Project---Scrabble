import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

//getscore, firstplacement, exceptions
public class Scrabble {
    int[] letterPoints = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    // starts with blank letter then follows alphabet

    public Scrabble(){

    }

    public int multiplier(int score, int x, int y, Board board){
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

        }
        return score;
    }

    public int pointsConversion(char letter){
        if(letter == '*'){
            return 0;
        }else{
            return letterPoints[letter - 65];
        }
    }

    public static void main(String[] args) throws IOException {
        Board gameBoard = new Board();
        ScrabbleBag gameBag = new ScrabbleBag();
        Frame playerOneFrame = new Frame(gameBag);
        Frame playerTwoFrame = new Frame(gameBag);
        Scrabble game = new Scrabble();
        Player one = new Player();
        Player two = new Player();
        Player playerArray[] = {one, two};
        boolean win = false;
        String input;
        ArrayList<Integer> placed = new ArrayList<Integer>();
        int turns = 1;
        int score = 0;

        game.setup(playerArray);

        while(!win){
            Scanner in = new Scanner(System.in);
            gameBoard.printBoard();
            System.out.println("Player " + (turns % 2) + " your turn, what do you want to do");

            input = in.nextLine();
            input.toUpperCase();
            switch (input){
                case "QUIT":  game.quit();
                    break;
                case "PASS":  System.out.println("Turn passed");
                    break;
                case "HELP": game.help();
                    break;
                case "EXCHANGE": if((turns % 2) == 1){
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
                case "JAZZ": game.smoothJazz();
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

            turns++;
        }
    }

    public void setup(Player[] players){
        Scanner in = new Scanner(System.in);
        System.out.println("Time to set up some names, player 1 your up first");
        System.out.println("Please type in the name you would like to use then hit enter");
        players[0].setName(in.nextLine());
        players[0].setScore(0);
        System.out.println("Perfect, now player 2 please input your name and press enter once correct");
        players[1].setName(in.nextLine());
        players[1].setScore(0);
        System.out.println("Both names are now set so lets start the game");
        in.close();
    }

    //maybe change the return type to string to act as simple logs
    public boolean placement(String input, Frame frame, Board board, ArrayList<Integer> placed){
        placed.clear();
        int X, Y;
        String direction, word;

        X = input.charAt(0);
        Y = input.charAt(2);

        direction = input.substring(4, 8).trim();
        word = input.substring(10).trim();

        if(!board.inBounds(X, Y)){
            return false;
        }

        if(direction == "ACROSS"){
            for(int count = 0;count < word.length();count++){
                if(board.getCharVal(X, Y + count) != word.charAt(count) || !frame.getLetter(word.charAt(count))){
                    return false;
                }
            }
                //need to grab the tiles being played
            for(int count = 0;count < word.length();count++){
                if(board.getCharVal(X, Y) == ' '){
                    frame.playLetter(word.charAt(count));
                    board.placeLetter(word.charAt(count), X, Y);
                    placed.add(Y);
                }
                Y++;
            }

        }else if(direction == "DOWN"){
            for(int count = 0;count < word.length();count++){
                if(board.getCharVal(X + count, Y) != word.charAt(count) || !frame.getLetter(word.charAt(count))){
                    return false;
                }
            }

            for(int count = 0;count < word.length();count++){
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
//will need placement info from placement method
    public int getScore(int x, int y, ArrayList<Character> placed, String direction, Board gameBoard){
        int score = 0, add = 0, multiplier = 1;

        if(direction == "ACROSS"){
            while(gameBoard.getCharVal(x, y) != ' '){
                score = score + pointsConversion(gameBoard.getCharVal(x, y));
                y++;
            }

            //multiplier from arraylist, letter then word


            int axis = x;
            for (Character character : placed) {
                while (gameBoard.getCharVal(axis++, character) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(axis, character));
                }
            }
            axis = x;
            for (Character character : placed) {
                while (gameBoard.getCharVal(axis--, character) != ' ') {
                    score = score + pointsConversion(gameBoard.getCharVal(axis, character));
                }
            }
            //check other axis
        }


        if(placed.size() == 7){
            score = score + 50;
            System.out.println("BINGO!");
        }

        return score;
    }

    public boolean challenge(int wordScore, Player player){
        player.increaseScore(-wordScore);
        return true;
    }

    public char[] exchange(ScrabbleBag pool, Frame playerFrame){
        try {
            char[] swap = new char[7];
            int count = 0;
            String input;
            System.out.println("Which letters would you like to exchange?");
            //possibly need an escape clause
            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            for (int i = 0; i < input.length(); i++) {
                if (input.indexOf(i) != ' ') {
                    swap[count] = (char) input.indexOf(i);
                    count++;
                }
            }
            while (count >= 0) {
                if (!playerFrame.getLetter(swap[count])) {
                    System.out.println("Sorry one of letters input is not in the frame");
                    throw new IllegalArgumentException("Inavlid argument");
                }
                count--;
            }

            playerFrame.swap(swap, pool);
            return swap;
        } catch (IllegalArgumentException e) {
            return exchange(pool, playerFrame);//Runs the method again
        }
    }

    private void smoothJazz() {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://www.youtube.com/watch?v=Tv5QRmG9ST0&list=PL7nML7u-x2dyqPDhm0g8eMlCfsrEvk89H");
            desktop.browse(oURL);
        } catch (Exception e) {
            System.out.println("Unable to jazz");
        }
    }

    private void help() throws IOException {

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

    private void quit()
    {
        System.exit(0);
    }
}

