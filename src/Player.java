/**
 * @author: Lukasz Filanowski
 * The purpose of this class is to create a player instance for the Scrabble game with
 * a name and score tied to the specific player instance.
 */

public class Player {
    /**
     * @param name: Name of player
     * @param score: Score of player
     * Private instance variables
     */
    private String name;    //Instance variables for player data
    private int score;

    /**
     * @param name: Name of player
     * @param score: Score of player
     * Constructor for PLayer
     */
    public Player (String name, int score) //Constructor
    {
        setName(name);
        setScore(score);

        if(name.isEmpty())   //Exception that's thrown if player doesn't enter name initially (upon instance creation)
        {
            throw new IllegalArgumentException("Name is not initialised");
        }

        if(score != 0)  //Exception that's thrown if score is anything other than 0 (upon instance creation)
        {
            throw new IllegalArgumentException("Score has invalid value");
        }
    }

    /**
     * @param name
     * Setting player name
     */
    public void setName(String name)   //Method to set name of player
    {
        this.name = name;
    }

    /**
     *
     * @param score
     * Setting player score
     */
    public void setScore(int score)    //Method to set score of player
    {
        this.score = score;

        if(score < 0)     //Exception that's thrown if score is less than 0
        {
            throw new IllegalArgumentException("Score is an invalid value");
        }
    }

    /**
     *
     * @return
     * Returning score of player
     */
    public int getScore()      //Method to get score of player
    {
        return score;
    }

    /**
     *
     * @return
     * Returning name of player
     */
    public String getName()  //Method to get name of player
    {
        return name;
    }

    /**
     *
     * @param increment
     * Incrementing score of the player
     */
    protected void increaseScore(int increment)   //Method that takes in a value to increment the score by and increases the score
    {
        setScore((this.score + increment));
    }

    /**
     * Resetting score
     */
    protected void resetScore()  //Method to reset the score of the player
    {
        setScore(0);
    }

    /**
     * Resetting name
     */
    protected void resetName()  //Method to reset the name of the player
    {
        setName("");
    }

    public static void main(String[] args) {

    }
}
