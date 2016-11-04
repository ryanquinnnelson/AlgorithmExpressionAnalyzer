/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forTesting;

/**
 * Class serves as a way to test tree classes.
 * @author ryan.quinn.nelson and michael.kleinsasser
 */
public class Player 
{
    private String name;
    private String positionPlayed;
    private int jerseyNumber;

    public Player(String name, String positionPlayed, int jerseyNumber) {
        this.name = name;
        this.positionPlayed = positionPlayed;
        this.jerseyNumber = jerseyNumber;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionPlayed() {
        return positionPlayed;
    }

    public void setPositionPlayed(String positionPlayed) {
        this.positionPlayed = positionPlayed;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }
    
    public String toString()
    {
        return "("+name + ", " + positionPlayed + ", " + jerseyNumber + ")";
    }
    
    public boolean equals(Object o)
    {
        if( o instanceof Player)
        {
           Player other = (Player) o;
           
           return other.jerseyNumber == jerseyNumber;
        }
        return false;
    }
}
