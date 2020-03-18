import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class Scrabble {
    int[] letterPoints = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    // starts with blank letter then follows alphabet

    public Scrabble(){

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
        boolean quit = false;
        String input;
        int turns = 1;

        while(!quit){
            Scanner in = new Scanner(System.in);
            System.out.println("Player " + (turns % 2) + " your turn, what do you want to do");
            input = in.nextLine();
            input.toUpperCase();
            switch (input){
                case "QUIT":  quit = true;
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
                case "CHALLENGE": game.challenge();
                    break;
                case "JAZZ": game.smoothJazz();
                        break;
                default: if(input.contains("across") || input.contains("down") || input.contains("ACROSS") || input.contains("DOWN")){ //X Y across/down WORD
                    if(turns % 2 == 1){
                        game.placement(input, playerOneFrame, gameBoard);
                    }else{
                        game.placement(input, playerTwoFrame, gameBoard);
                    }
                }else{
                    turns--;
                    System.out.println("Invalid input, please retake your turn and use the command HELP to see instructions");
                }
            }
            System.out.println(game.getScore());
            turns++;
        }
    }

    //maybe change the return type to string to act as simple logs
    public boolean placement(String input, Frame frame, Board board){
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
                }
                X++;
            }
        }else{
            return false;
        }

        return true;
    }

    public int getScore(){
        int score = 0, add = 0, multiplier = 1;

        /*if(direction == "Across"){
            while(Game.getCharVal(x, y) != ' '){
                score = score + pointsConversion(Game.getCharVal(x, y));
                y++;
                Game.getTileVal(x, y);//add switch statement
            }
        }*/
        return score;
    }

    public boolean challenge(){

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

    public void smoothJazz() {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://www.youtube.com/watch?v=Tv5QRmG9ST0&list=PL7nML7u-x2dyqPDhm0g8eMlCfsrEvk89H");
            desktop.browse(oURL);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to jazz");
        }
    }

    public void help() throws IOException {
        File helpFile = new File("assets\\Help.txt");

        if(!helpFile.exists())
        {
            throw new FileNotFoundException("ERROR: File not found");
        }

        else
        {
            Desktop desktop = java.awt.Desktop.getDesktop();
            desktop.open(helpFile);
        }
    }

    public void quitMethod()
    {
        System.exit(0);
    }
}

