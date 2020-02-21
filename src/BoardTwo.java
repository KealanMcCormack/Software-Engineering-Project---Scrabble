public class BoardTwo {

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
        /*int row_small = 0;
        int rs = row_small;

        int row_big = 8;
        int rb = row_big;

        int column_small = 0;
        int cs = column_small;

        int column_big = 8;
        int cb = column_big;*/

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

                    else if (row_count == 7 && column_count == 3)
                    {
                        boardArray[row_count][column_count] = new Square(' ', tileVal.DoubleLetter);
                    }

                    else if ((row_count == 0 && column_count == 3) || (row_count == 3 && column_count == 0))
                    {
                        boardArray[row_count][column_count] = new Square(' ', tileVal.DoubleLetter);
                    }

                    else if ((row_count == 1 && column_count == 5) || (row_count == 5 && column_count == 1) || (row_count == 5 && column_count == 5)) {
                        boardArray[row_count][column_count] = new Square(' ', tileVal.TripleLetter);
                    }

                    else if (row_count == column_count && row_count > 0 && !(row_count == 7))
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
        BoardTwo b = new BoardTwo();
        b.createBoard();
        System.out.println(b.printBoard());
    }
}
