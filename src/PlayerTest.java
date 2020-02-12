/**
 * Gerard Colman - 18327576
 * Lukasz Filanowski - 18414616
 * Kealan McCormack - 18312236
 * @author Lukasz Filanowski
 * Class to test the methods in the  Player class
 */

public class PlayerTest {

    public static void main(String[] args) {
        /**
         * Tests for Player class, testing the constructor, get methods, increment score methods, reset methods and creating new instances.
         *
         */
        Player p = new Player("Steve", 0);  //Creating object (which inherently tests setName and setScore

        System.out.println("Score: " + p.getScore());           //Testing getScore method
        System.out.println("Name: " + p.getName());            //Testing getName method

        p.increaseScore(10);    //Testing increaseScore method
        System.out.println("Score: " + p.getScore());     //Displaying result of increment

        p.resetScore();                         //Calling resetScore method to reset score
        p.resetName();                          //Calling resetName method to reset name
        System.out.println("Score: " + p.getScore());     //Displaying result of reset
        System.out.println("Name: " + p.getName());      //Displaying result of reset

        p.setScore(43);                     //Setting score following reset to show that setScore works
        p.setName("Mary");                  //Setting name following reset to show that setName works
        System.out.println("Score: " + p.getScore());     //Displaying result of setting score
        System.out.println("Name: " + p.getName());      //Displaying result of setting name
    }
}
