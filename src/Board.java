public class Board {

    protected static Square[][] boardArray = new Square[15][15];

    enum tileVal{
        Standard("    "),
        Star(" () "),
        DoubleWord(" DW "),
        TripleWord(" TW "),
        DoubleLetter(" DL "),
        TripleLetter(" TL ");

        private final String tile;

        tileVal(String tile)
        {
            this.tile = tile;
        }

        @Override
        public String toString()
        {
            return this.tile;
        }
    }

    private class Square{

        private char characterVal;
        private tileVal tile;

        Square(char CharacterVal, tileVal t){
            setCharacterVal(CharacterVal);
            setTileVal(t);
        }

        public char getCharacterVal() {
            return characterVal;
        }

        public tileVal getTileVal() {
            return tile;
        }

        public void setCharacterVal(char CharacterVal) {
            characterVal = CharacterVal;
        }

        public void setTileVal(tileVal t) {
            this.tile = t;
        }
    }

    protected void createBoard() {
        for (int row_count = 0; row_count < 15; row_count++)  //For loops that initially create a new square object for each space on the board
        {
            for (int column_count = 0; column_count < 15; column_count++) {
                boardArray[row_count][column_count] = new Square(' ', tileVal.Standard); //Creating new square object
            }
        }

        //CREATING THE BOARD WITH TILE VALUES

        for (int rotations = 0; rotations < 4; rotations++)
        {
            for (int row_count = 0; row_count < 8; row_count++)
            {
                for (int column_count = 0; column_count < 8; column_count++)
                {
                    if ((row_count == 2 && column_count == 6) || (row_count == 6 && column_count == 2) || (row_count == 6 && column_count == 6))
                    {
                        boardArray[row_count][column_count].setTileVal(tileVal.DoubleLetter);
                    }

                    else if (((row_count == 3 || row_count == 7) && (column_count == 3 || column_count == 7) && !((row_count == 7) && (column_count == 7)) && !((row_count == 3) && (column_count == 3))))
                    {
                        boardArray[row_count][column_count].setTileVal(tileVal.DoubleLetter);
                    }

                    else if ((row_count == 0 && column_count == 3) || (row_count == 3 && column_count == 0))
                    {
                        boardArray[row_count][column_count].setTileVal(tileVal.DoubleLetter);
                    }

                    else if ((row_count == 1 && column_count == 5) || (row_count == 5 && column_count == 1) || (row_count == 5 && column_count == 5)) {
                        boardArray[row_count][column_count].setTileVal(tileVal.TripleLetter);
                    }

                    else if (row_count == 7 && column_count == 7)
                    {
                        boardArray[row_count][column_count].setTileVal(tileVal.Star);
                    }

                    else if (row_count == column_count && row_count > 0)
                    {
                        boardArray[row_count][column_count].setTileVal(tileVal.DoubleWord);
                    }

                    else if (row_count % 7 == 0 && column_count % 7 == 0)
                    {
                        boardArray[row_count][column_count].setTileVal(tileVal.TripleWord);
                    }
                }
            }
        }
    }
    protected void boardReset(){
        createBoard();
    }

    public String printBoard(){
        String output = "";

        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                output += "|";
                if(boardArray[i][j].getCharacterVal() == ' ')
                {
                    output += boardArray[i][j].getTileVal();
                }

                else
                {
                    output += boardArray[i][j].getCharacterVal();
                }
            }
            output += "|" + "\n";
        }
        return output;
    }


    public static void main(String[] args) {
        BoardTwo b = new BoardTwo();
        System.out.println(b.printBoard());
    }

    protected boolean Connecting(int x, int y){ //returns true if a tile is found in a conjoining tile
        if(x != 0) {
            if (boardArray[x - 1][y].getCharacterVal() != ' ') {
                return true;
            }
        }

        if(x != 14) {
            if (boardArray[x + 1][y].getCharacterVal() != ' ') {
                return true;
            }
        }

        if(y != 14) {
            if (boardArray[x][y + 1].getCharacterVal() != ' ') {
                return true;
            }
        }

        if(y != 0) {
            if (boardArray[x][y - 1].getCharacterVal() != ' ') {
                return true;
            }
        }

        return false;
    }

    protected int[] ConnectingInt(int x, int y){ //returns true if a tile is found in a conjoining tile
        int[] a = new int[2];
        a[0] = x;
        a[1] = y;

        if(x != 0) {
            if (boardArray[x - 1][y].getCharacterVal() != ' ') {
                a[0] -= 1;
                return a;
            }
        }

        if(x != 14) {
            if (boardArray[x + 1][y].getCharacterVal() != ' ') {
                a[0] += 1;
                return a;
            }
        }

        if(y != 14) {
            if (boardArray[x][y + 1].getCharacterVal() != ' ') {
                a[1] += 1;
                return a;
            }
        }

        if(y != 0) {
            if (boardArray[x][y - 1].getCharacterVal() != ' ') {
                a[1] -= 1;
                return a;
            }
        }

        return a;
    }

    protected boolean inLine(int arr[]){//Needs input of the previous 3 inputs
        int a = 0, b=0;

        int[] check = ConnectingInt(arr[0], arr[1]);//work to be done
        if(check[0] == arr[2] && check[1] == arr[3]){

        } else if(boardArray[check[0]][check[1]].getCharacterVal() != ' '){

        }

        return false;

    }

    protected char placeLetter(int letterIndex, int x, int y){
        Frame f = new Frame();

        if(boardArray[x][y].getCharacterVal() == ' '){
            throw new IllegalArgumentException("Already a tile placed on this square");
        }

        char a = f.getLetterIndex(letterIndex);
        boardArray[x][y].setCharacterVal(f.playLetter(letterIndex));
        return a;
    }
}