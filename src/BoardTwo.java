public class BoardTwo {
    int rotate = 0;

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
    public void createBoard(){
        boardInit();
        createHalf();
        createCenter();
        flipHalf();
    }
    private void boardInit(){
        for(int i = 0;i<15;i++){
            for(int j = 0;j<15;j++){
                boardArray[i][j] = new Square(' ', tileVal.Standard);
            }
        }
    }
    private void createHalf(){
        for(int v = 0;v<7;v++){
            for(int h = 0;h<15;h++){
                switch (v){
                    case 0:
                        if(h == 0 || h == 7 || h == 14){ //Creates triple word values
                            boardArray[v][h].setTileVal(tileVal.TripleWord);
                        }
                        if(h == 3 || h == 11){ //Double letter
                            boardArray[v][h].setTileVal(tileVal.DoubleLetter);
                        }
                    case 1:
                        if(h == 1 || h == 13){ //Double Word
                            boardArray[v][h].setTileVal(tileVal.DoubleWord);
                        }
                        if(h == 5 || h == 9){ //Triple letter
                            boardArray[v][h].setTileVal(tileVal.TripleLetter);
                        }
                    case 2:
                        if(h == 2 || h == 12){ //Double Word
                            boardArray[v][h].setTileVal(tileVal.DoubleWord);
                        }
                        if(h == 6 || h == 8){
                            boardArray[v][h].setTileVal(tileVal.DoubleLetter);
                        }
                    case 3:
                        if(h == 0 || h == 7 || h == 14){
                            boardArray[v][h].setTileVal(tileVal.DoubleLetter);
                        }
                        if(h == 3 || h == 11){
                            boardArray[v][h].setTileVal(tileVal.DoubleWord);
                        }
                    case 4:

                    case 5:
                    case 6:
                }
            }
        }
    }
    private void createCenter(){

    }
    private void flipHalf(){

    }
}