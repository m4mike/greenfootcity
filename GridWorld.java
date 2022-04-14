import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FileDialog;
import java.io.*;
import java.util.Scanner; 
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * The Grid world. Many things happen here
 * 
 * @author Liam
 * @version (Data)
 */
public class GridWorld extends World
{

    
    // Create an instance var for the map which will hold what tiles are where (I would recommend not changing this ~Alex Dollar)
    private HashMap<String, String> cityMap; 
    

    /**
     * Constructor for objects of class GridWorld.
     * 
     */
    public GridWorld()
    {    
        
        // I would recommend not changing this ~Alex Dollar
        this.cityMap = new HashMap<>();
    }
    
    /**
     * Get the current camera
     * 
     * @returns The current camera
     */
    public Camera getCamera() {
        
    }
    
    
    /**
     * Used for file reading, time keeping, and game updating
     * 
     * If you are having trouble with skipping comments when loading the file try this code:
     * 
     *          // This will be used later
                boolean flag = false;
                // While this line is a comment and there is a next line go to it
                while (word.charAt(0) == '#') {
                    try { // reader.hasNextLine() was returning true even on the last line. 
                        reader.nextLine();
                        word = reader.next();
                    } catch (NoSuchElementException e) {
                        // If there is no next line, set the flag then break out of the loop
                        flag = true;
                        break;
                    }
                }
                // Break out of the outer loop if the last line was a comment (flag was set)
                if (flag) {
                    break;
                }
     */
    public void act() {
        
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
     * Method to apply time step values to the world
     * 
     * @param step The timestep to apply
     * @param index The index of the timestep in the timesteps array
     */
    private void applyTimeStep(TimeStep step, int index) {
        
    }
    
    
    /**
     * Method called when the game finished
     * This method calles the score method under the hud
     */
    private void score() {
        
    }
    
    /**
     * Has the user loaded a file?
     * 
     * @returns loaded
     */
    public boolean isLoaded() {
        
    }
    
    /**
     * Method to get the current time step
     */
    public TimeStep getCurrentTimeStep() {
        
    }
    
    /**
     * Get the current world data as a timestep
     * 
     * @returns The timestep containing the current world data
     */
    public TimeStep getCurrentDataAsTimeStep() {
        
    }
    
    /**
     * Place a object into the world
     * 
     * @param type The type of the object to place: ["RES", "COM", "IND", "ROAD"]
     * @param x The x object position
     * @param y The y object position
     */
    public void place(String type, int x, int y) {
        
    }
    
    /**
     * Handles adding a tile to the world ad storing it in the HashMap
     * 
     * I would recommend not changing this ~Alex Dollar
     * 
     * @param type The type of time to place
     * @param x The x tile coord
     * @param y The y tile coord
     */
    private void addTile(String type, int x, int y) {
        switch (type) {
            case "RES":
                cityMap.put(getKey(x,y), type);
                this.addObject(new Residential(x, y), 0, 0);
                break;
            case "COM":
                cityMap.put(getKey(x,y), type);
                this.addObject(new Commercial(x, y), 0, 0);
                break;
            case "IND":
                cityMap.put(getKey(x,y), type);
                this.addObject(new Industrial(x, y), 0, 0);
                break;
            case "ROAD":
                cityMap.put(getKey(x,y), type);
                this.addObject(new Road(x, y), 0, 0);
                break;
            default:
                break;
        }
    }
}