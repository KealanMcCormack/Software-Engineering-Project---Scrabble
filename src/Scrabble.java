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

    public static void main(String[] args){
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
                default: if(input.contains("across") || input.contains("down")){

                }else{
                    turns--;
                    System.out.println("Invalid input, please retake your turn and use the command HELP to see instructions");
                }
            }
            System.out.println(game.getScore());
            turns++;
        }
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
        char[] swap = new char[7];
        int count = 0;
        String input;
        System.out.println("Which letters would you like to exchange?");
        Scanner in = new Scanner(System.in);
        input = in.nextLine();

        for(int i = 0;i < input.length();i++){
            if(input.indexOf(i) != ' '){
                swap[count] = (char) input.indexOf(i);
                count++;
            }
        }

        //Check if the characters in swap are in the players frame
        playerFrame.swap(swap, pool);

        return swap;
    }

    public void help(){//print from a file or something
        System.out.println("");
    }

}
