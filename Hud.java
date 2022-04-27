import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;

/**
 * This is the Heads up display.
 * This class provides information onhow to play the game and other values used during and after gameplay
 * This class also handles placing and scoring
 * 
 * @author (Name Here)
 * @version (Date)
 */
public class Hud extends Actor
{
    /**
     * Use the constructor to display some starting data
     */
    public Hud(GridWorld world) {
        GreenfootImage textImage = new GreenfootImage("Press run to start", 24, new Color(0, 255, 128), new Color(0, 0, 0, 0));
    }
    
    /**
     * Draw the HUD to the screen and handle placment
     */
    public void act()
    {
        
    }
    
    /**
     * Used to draw instructions to the screen before the user has loaded a level
     */
    private void drawInfo() {
        
    }
    
    /**
     * Clears the instuctions from the screen once the user has loaded a file
     */
    private void clearInfo() {
        
    }
    
    /**
     * Draws stats and data to the screen
     * 
     * @param data The timestep containing the data to display
     */
    private void drawData(TimeStep data) {
        
    }
    
    /**
     * Method used to create a unique string key for any two integer inputs
     * 
     * @param x The x input
     * @param y The y input
     * @returns The generated key
     */
    private String getKey(int x, int y) {
        
    }
    
    /**
     * Check neighbors for tile type and return amount found
     * 
     * @param check Tiletype to check for
     * @param up The up neighbor
     * @param down The down neighbor
     * @param left The left neighbor
     * @param right The right neighbor
     * @returns The amount found
     */
    private int checkAny(String check, String up, String down, String left, String right) {
        
    }
    
    private GridWorld getGridWorld() {
        //return getWorld();
    }
    
    /**
     * Scores the game.
     * The world will call this after the last time step has finished
     * 
     * I would recommend not changing this! ~Alex Dollar
     * 
     * @param cityMap The current map of the city
     */
    public void score(HashMap<String, String> cityMap) { 
        GridWorld world = getGridWorld();
        TimeStep currentData = world.getCurrentDataAsTimeStep();
        int points = 0;
        
        for (int x=0; x<currentData.areaWidthIncrese; x++) {
            for (int y=0; y<currentData.areaHeightIncrese; y++) {
                String tile = cityMap.get(getKey(x,y));
                if (tile != null) {
                    String up = cityMap.get(getKey(x,y-1));
                    String down = cityMap.get(getKey(x,y+1));
                    String left = cityMap.get(getKey(x-1,y));
                    String right = cityMap.get(getKey(x+1,y));
                    
                    int found;
                    switch (tile) {
                        case "RES":
                            found = checkAny("RES", up, down, left, right);
                            found *= 5;
                            points += found;
                            found = checkAny("COM", up, down, left, right);
                            found *= 5;
                            points += found;
                            found = checkAny("ROAD", up, down, left, right);
                            if (found == 0) {
                                points -= 10;
                            }
                            break;
                        case "COM":
                            found = checkAny("COM", up, down, left, right);
                            found *= 5;
                            points += found;
                            found = checkAny("ROAD", up, down, left, right);
                            if (found == 0) {
                                points -= 10;
                            }
                            break;
                        case "IND":
                            found = checkAny("IND", up, down, left, right);
                            found *= 5;
                            points += found;
                            found = checkAny("RES", up, down, left, right);
                            found *= -5;
                            points += found;
                            found = checkAny("COM", up, down, left, right);
                            found *= -5;
                            points += found;
                            found = checkAny("ROAD", up, down, left, right);
                            if (found == 0) {
                                points -= 10;
                            }
                            break;
                        case "ROAD":
                            found = checkAny("ROAD", up, down, left, right);
                            found *= 5;
                            points += found;
                            found = checkAny("ROAD", up, down, left, right);
                            if (found == 0) {
                                points -= 10;
                            }
                            break;
                    }
                }
            }
        }
        currentData.resTiles *= 5;
        points -= currentData.resTiles;
        currentData.comTiles *= 5;
        points -= currentData.comTiles;
        currentData.indTiles *= 5;
        points -= currentData.indTiles;
        currentData.roadTiles *= 5;
        points -= currentData.roadTiles;
        
        world.showText("Score: " + points, GridWorld.WORLD_X/2, GridWorld.WORLD_Y/4+1);
        Greenfoot.stop();
    }
}
