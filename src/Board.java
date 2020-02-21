public class Board {

    protected static Square[][] boardArray = new Square[15][15];

    enum tileVal{
        Standard("    "),
        Centre(" () "),
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
        }//TO BE REMOVED

        //CREATING THE BOARD WITH TILE VALUES

        for(int rotations = 0; rotations < 4; rotations++)
        {
            for(int row_count = 0; row_count < 8; row_count++)
            {
                for(int column_count = 0; column_count < 8; column_count++)
                {
                    if ((row_count == 2 && column_count == 6) || (row_count == 6 && column_count == 2) || (row_count == 6 && column_count == 6))
                    {
                        boardArray[row_count][column_count] = new Square(' ', tileVal.DoubleLetter);
                    }

                    else if ((row_count == 0 && column_count == 3) || (row_count == 3 && column_count == 0) || (row_count == 7 && column_count == 3))
                    {
                        boardArray[row_count][column_count] = new Square(' ', tileVal.DoubleLetter);
                    }

                    else if ((row_count == 1 && column_count == 5) || (row_count == 5 && column_count == 1) || (row_count == 5 && column_count == 5)) {
                        boardArray[row_count][column_count] = new Square(' ', tileVal.TripleLetter);
                    }

                    else if (row_count == column_count && (row_count < 7) && row_count > 0)
                    {
                        boardArray[row_count][column_count] = new Square(' ', tileVal.DoubleWord);
                    }

                    else if ((row_count == 7 && column_count == 0) || (row_count == 0 && column_count == 0))
                    {
                        boardArray[row_count][column_count] = new Square(' ', tileVal.TripleWord);
                    }

                    else
                    {
                        boardArray[row_count][column_count] = new Square(' ', tileVal.Standard);
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
        Board b = new Board();
        b.createBoard();
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


    protected boolean inLine(int arr[]){//Needs input of the previous 3 inputs
        int x = 0, y=0;

        x = arr[0] - arr[2];
        y = arr[1] - arr[3];

        if(x != 0 && y!= 0){
            return false;
        }

        if(x != 0) {
            for(int i = 2;i < arr.length;i += 2) {
                x = arr[i - 2] - arr[i];
                y = arr[i - 1] - arr[i + 1];
                if(y != 0){
                    return false;
                }
                if (x > 0) {
                    for (int count = 1; count < x; count++) {
                        if (boardArray[(arr[i] + count)][arr[1]].getCharacterVal() == ' ') {
                            return false;
                        }
                    }
                }

                if (x < 0) {
                    for (int count = x; count < 0; count++) {
                        if (boardArray[(arr[i] + count)][arr[1]].getCharacterVal() == ' ') {
                            return false;
                        }
                    }
                }

            }
        }

        if(y != 0) {
            for(int i = 2;i < arr.length;i += 2) {
                x = arr[i - 2] - arr[i];
                y = arr[i - 1] - arr[i + 1];
                if(x != 0){
                    return false;
                }
                if (y > 0) {
                    for (int count = 1; count < y; count++) {
                        if (boardArray[(arr[0])][arr[i] + count].getCharacterVal() == ' ') {
                            return false;
                        }
                    }
                }

                if (y < 0) {
                    for (int count = y; count < 0; count++) {
                        if (boardArray[(arr[0])][arr[i] + count].getCharacterVal() == ' ') {
                            return false;
                        }
                    }
                }

            }
        }

        return true;
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

    protected boolean inBounds(int x, int y){
        if(x > -1 && x < 15 && y > -1 && y < 15){
            return true;
        }else{
            return false;
        }
    }

    protected boolean firstPlacement(int x, int y){
        if(x == 7 && y == 7){
            return true;
        }else{
            return false;
        }
    }

    protected boolean tileOnTile(int x, int y){
        if(boardArray[x][y].getCharacterVal() != ' '){
            return true;
        }else{
            return false;
        }
    }

    protected boolean hasTiles(Frame frame)
    {
        return frame.empty();
    }
}