/**
 * Tests the scrabble bag class.
 * Gerard Colman - 18327576
 * Lukasz Filanowski - 18414616
 * Kealan McCormack - 18312236
 */
public class ScrabbleBagTest {

    public void tests(){
            ScrabbleBag SB = new ScrabbleBag();

            for(int i = 0;i<7;i++){
                System.out.print(SB.TileToPlayer() + ", ");
                System.out.print("\n");
            }
            System.out.println(SB.isEmpty());
            SB.BagReset();
            System.out.println(SB.NumberOfTiles());
            System.out.println(SB.CurrentTiles());
        }
}