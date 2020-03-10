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
        Frame playerOneFrame = new Frame();
        Frame playerTwoFrame = new Frame();
        ScrabbleBag gameBag = new ScrabbleBag();
        Scrabble game = new Scrabble();
        boolean quit = false;
        String input;
        int turns = 1;

        while(!quit){
            Scanner in = new Scanner(System.in);
            input = in.nextLine();

            switch (input){
                case "QUIT":  quit = true;
                    break;
                case "PASS":
                    break;
                case "HELP":
                    break;
                case "Exchange":
                    break;
                default: if(input.contains("across") || input.contains("down")){

                }else{
                    //invalid input
                }
            }



        }


    }

    public int getScore(int x, int y, Board Game, String direction){
        int score = 0, add = 0, multiplier = 1;

        if(direction == "Across"){
            while(Game.getCharVal(x, y) != ' '){
                score = score + pointsConversion(Game.getCharVal(x, y));
                y++;
                Game.getTileVal(x, y);//add switch statement
            }
        }
        return score;
    }

    public boolean challenge(){
        return true;
    }

}
