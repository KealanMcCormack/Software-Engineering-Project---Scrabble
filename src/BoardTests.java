import static org.junit.jupiter.api.Assertions.*;
/**
 * Gerard Colman - 18327576
 * Lukasz Filanowski - 18414616
 * Kealan McCormack - 18312236
 * @author Kealan McCormack
 *
 * BoardTests is a test class for Board
 */
class BoardTests {

    /**
     * createBoard tests createBoard method
     */
    @org.junit.jupiter.api.Test
    void createBoard() {//Tests createBoard
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        assertEquals(' ', Tester.getCharVal(0, 1));//Checks that the board exists
        assertEquals(' ',  Tester.getCharVal(8, 8));
    }

    /**
     * printBoard tests printBoard method
     */
    @org.junit.jupiter.api.Test
    void printBoard() { //Tests printBoard
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        if(Tester.printBoard() != " "){//Checks that More than an empty String is returned
            assert(true);
        }
    }

    /**
     * placeLetter tests placeLetter method
     */
    @org.junit.jupiter.api.Test
    void placeLetter() { //Tests placeLetter
        Board Tester = new Board(); //New instance of Board
        Frame Letters = new Frame();
        Tester.createBoard();

        Letters.setPlayerTiles('A');//Adds a know character to frame

        Tester.placeLetter(Letters, 8, 2,2);//Places letter
        assertEquals(Tester.getCharVal(2,2), 'A');//Checks if letter has been placed correctly
    }

    /**
     * connecting tests Connecting method
     */
    @org.junit.jupiter.api.Test
    void connecting() { //Tests Connect
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame();


        Tester.placeLetter(Letters, 1, 8, 7);//Places letters in know positions
        Tester.placeLetter(Letters, 1, 8, 9);
        Tester.placeLetter(Letters, 1, 7, 8);
        Tester.placeLetter(Letters, 1, 9, 8);

        assertEquals(true, Tester.Connecting(8, 8));//Checks different orientations of connecting letters
        assertEquals(true, Tester.Connecting(8,6));
        assertEquals(false, Tester.Connecting(7,6));//Ensures diagonals don't work

        Tester.placeLetter(Letters, 1, 0,0);
        assertEquals(true, Tester.Connecting(0,1));//Checks edge of board won't break method

    }

    /**
     * inLine tests inLine method
     */
    @org.junit.jupiter.api.Test
    void inLine() { //Tests inLine
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame();

        Tester.placeLetter(Letters, 1, 6, 6);//Places letters to allow testing
        Tester.placeLetter(Letters, 1, 6, 7);
        Tester.placeLetter(Letters, 1, 6, 8);

        Tester.placeLetter(Letters, 1, 6, 5);
        Tester.placeLetter(Letters, 1, 6, 4);
        Letters.refill();//Stops frame running out of letters
        Tester.placeLetter(Letters, 1, 7, 6);
        Tester.placeLetter(Letters, 1, 8, 6);

        Tester.placeLetter(Letters, 1, 5, 6);
        Tester.placeLetter(Letters, 1, 4, 6);

        int[] arr = new int[6];
        arr[0] = 6;//Sets up array with co-ordinates being tested
        arr[1] = 6;
        arr[2] = 6;
        arr[3] = 7;
        arr[4] = 6;
        arr[5] = 8;

        assertEquals(true, Tester.inLine(arr));//Tests each direction tiles can be placed in

        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 6;
        arr[3] = 5;
        arr[4] = 6;
        arr[5] = 4;
        assertEquals(true, Tester.inLine(arr));

        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 7;
        arr[3] = 6;
        arr[4] = 8;
        arr[5] = 6;
        assertEquals(true, Tester.inLine(arr));
        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 5;
        arr[3] = 6;
        arr[4] = 4;
        arr[5] = 6;
        assertEquals(true, Tester.inLine(arr));
        arr[0] = 4;
        arr[1] = 6;
        arr[2] = 5;
        arr[3] = 8;
        arr[4] = 4;
        arr[5] = 6;
        assertEquals(false, Tester.inLine(arr));//Ensures random placements aren't allowed
    }

    /**
     * inBounds tests inBounds method
     */
    @org.junit.jupiter.api.Test
    void inBounds() {//Tests inBounds
        Board Tester = new Board();
        Tester.createBoard();
        assertEquals(false, Tester.inBounds(-1, 0));//Tests if placements are inBounds
        assertEquals(false, Tester.inBounds(0, 15));
        assertEquals(false, Tester.inBounds(8, -1));
        assertEquals(false, Tester.inBounds(15, 0));
        assertEquals(true, Tester.inBounds(6, 0));
    }

    /**
     * firstPlacement tests firstPlacement
     */
    @org.junit.jupiter.api.Test
    void firstPlacement() {//Tests firstPlacement
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame();

        Letters.setPlayerTiles('A');
        Tester.firstPlacement(Letters, 8);
        assertEquals(Tester.getCharVal(7,7), 'A');//Checks character has been placed int the middle of the board
    }

    /**
     * tileOnTile tests tileOnTile
     */
    @org.junit.jupiter.api.Test
    void tileOnTile() {//Tests tileOnTile
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame();

        Tester.placeLetter(Letters, 1, 2,2);//Plays character

        assertEquals(true, Tester.tileOnTile(2,2));//Checks known used tile
        assertEquals(false, Tester.tileOnTile(4,4 ));//Checks known empty tile
    }

    /**
     * hasTiles tests hasTiles
     */
    @org.junit.jupiter.api.Test
    void hasTiles() { //Tests hasTiles
        Frame Letters = new Frame(); //New instance of Frame
        Board Tester = new Board(); //New instance of Board
        assertEquals(true, Tester.hasTiles(Letters));//Frame starts with tiles so should be true
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        assertEquals(false, Tester.hasTiles(Letters));//Played all tiles so frame should be empty
    }

    /**
     * boardReset tests boardReset
     */
    @org.junit.jupiter.api.Test
    void boardReset() {
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame(); //New instance of Frame
        Letters.setPlayerTiles('A');

        Tester.placeLetter(Letters, 8, 6, 6);//Plays a letter on the board
        assertEquals('A', Tester.getCharVal(6,6));

        Tester.boardReset();
        assertEquals(' ', Tester.getCharVal(6,6));//Checks the letter is no longer present after reset
    }

}
