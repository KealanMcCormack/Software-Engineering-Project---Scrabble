import static org.junit.jupiter.api.Assertions.*;

class BoardTests {

    @org.junit.jupiter.api.Test
    void createBoard() {
        Board Tester = new Board();
        Tester.createBoard();
        assertEquals(' ', Tester.getCharVal(0, 1));
        assertEquals(' ',  Tester.getCharVal(8, 8));
    }

    @org.junit.jupiter.api.Test
    void printBoard() {
        Board Tester = new Board();
        Tester.createBoard();
        Tester.printBoard();
    }

    @org.junit.jupiter.api.Test
    void placeLetter() {
        Board Tester = new Board();
        Frame Letters = new Frame();
        Tester.createBoard();
        Tester.printBoard();

        Letters.setPlayerTiles('A');

        Tester.placeLetter(Letters, 8, 2,2);
        assertEquals(Tester.getCharVal(2,2), 'A');
    }

    @org.junit.jupiter.api.Test
    void connecting() {
        Board Tester = new Board();
        Tester.createBoard();
        Frame Letters = new Frame();


        Tester.placeLetter(Letters, 1, 8, 7);
        Tester.placeLetter(Letters, 1, 8, 9);
        Tester.placeLetter(Letters, 1, 7, 8);
        Tester.placeLetter(Letters, 1, 9, 8);

        assertEquals(true, Tester.Connecting(8, 8));
        assertEquals(true, Tester.Connecting(8,6));
        assertEquals(false, Tester.Connecting(7,6));

        Tester.placeLetter(Letters, 1, 0,0);
        assertEquals(true, Tester.Connecting(0,1));

    }

    @org.junit.jupiter.api.Test
    void inLine() {
        Board Tester = new Board();
        Tester.createBoard();
        Frame Letters = new Frame();

        Tester.placeLetter(Letters, 1, 6, 6);
        Tester.placeLetter(Letters, 1, 6, 7);
        Tester.placeLetter(Letters, 1, 6, 8);

        int[] arr = new int[6];
        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 6;
        arr[3] = 7;
        arr[4] = 6;
        arr[5] = 8;

        assertEquals(true, Tester.inLine(arr));

        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 6;
        arr[3] = 5;
        arr[4] = 6;
        arr[5] = 4;
        assertEquals(false, Tester.inLine(arr));

        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 7;
        arr[3] = 6;
        arr[4] = 8;
        arr[5] = 6;
        assertEquals(false, Tester.inLine(arr));
        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 5;
        arr[3] = 6;
        arr[4] = 4;
        arr[5] = 6;
        assertEquals(false, Tester.inLine(arr));
        arr[0] = 4;
        arr[1] = 6;
        arr[2] = 5;
        arr[3] = 8;
        arr[4] = 4;
        arr[5] = 6;
        assertEquals(false, Tester.inLine(arr));
    }

    @org.junit.jupiter.api.Test
    void inBounds() {
        Board Tester = new Board();
        Tester.createBoard();
        assertEquals(false, Tester.inBounds(-1, 0));
        assertEquals(false, Tester.inBounds(0, 15));
        assertEquals(false, Tester.inBounds(8, -1));
        assertEquals(false, Tester.inBounds(15, 0));
        assertEquals(true, Tester.inBounds(6, 0));
    }

    @org.junit.jupiter.api.Test
    void firstPlacement() {
        Board Tester = new Board();
        Tester.createBoard();
        Frame Letters = new Frame();

        Letters.setPlayerTiles('A');
        Tester.firstPlacement(Letters, 8);
        assertEquals(Tester.getCharVal(7,7), 'A');
    }

    @org.junit.jupiter.api.Test
    void tileOnTile() {
        Board Tester = new Board();
        Tester.createBoard();
        Frame Letters = new Frame();

        Tester.placeLetter(Letters, 1, 2,2);

        assertEquals(true, Tester.tileOnTile(2,2));
        assertEquals(false, Tester.tileOnTile(4,4 ));
    }

    @org.junit.jupiter.api.Test
    void hasTiles() {
        Frame Letters = new Frame();
        Board Tester = new Board();
        assertEquals(true, Tester.hasTiles(Letters));
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        assertEquals(false, Tester.hasTiles(Letters));
    }

    @org.junit.jupiter.api.Test
    void boardReset() {
        Board Tester = new Board();
        Tester.createBoard();
        Frame Letters = new Frame();
        Letters.setPlayerTiles('A');

        Tester.placeLetter(Letters, 8, 6, 6);
        assertEquals('A', Tester.getCharVal(6,6));

        Tester.boardReset();
        assertEquals(' ', Tester.getCharVal(6,6));
    }

}