import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FileDialog;
import java.io.*;
import java.util.Scanner; 
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * The Grid world.
 * 
 * @author Liam Bentein
 */
public class GridWorld extends World
{

    public static final int WORLD_X = 600;
    public static final int WORLD_Y = 600;
    public static final int WORLD_CELL_SIZE = 1;

    public int area_width = 0;
    public int area_height = 0;
    public int cellSize = 50;

    private Camera camera;;
    private WorldBorder worldBorder;
    private Hud hud;
    //tesje

    private boolean loaded = false;
    private boolean ended = false;
    public int resTiles = 0;
    public int comTiles = 0;
    public int indTiles = 0;
    public int roadTiles = 0;
    public int TimeStep = 0;

    private long lastTime = System.currentTimeMillis();
    private long frames = 0;
    private long secondsPassed = 0;

    private long currentTimeStepStartTime = 0;
    private int currentTimeStepIndex = -1;
    private ArrayList<TimeStep> timeSteps = new ArrayList<>();

    // Create an instance var for the map which will hold what tiles are where (I would recommend not changing this ~Alex Dollar)
    private HashMap<String, String> cityMap; 
    

    /**
     * Constructor for objects of class GridWorld.
     * 
     */
    public GridWorld()
    {    
        super(WORLD_X, WORLD_Y, WORLD_CELL_SIZE);
        this.cityMap = new HashMap<>();
        camera = new Camera();            this.addObject(camera, 0, 0);
        worldBorder  = new WorldBorder(); this.addObject(worldBorder, 0,0);
        hud = new Hud(this);              this.addObject(hud,0,0);
    }
    
    /**
     * Get the current camera
     * 
     * @returns The current camera
     */
    public Camera getCamera() {
        return camera;
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
        if(System.currentTimeMillis() / 1000  == lastTime / 1000 ) 
            frames++;
        else
        {
            lastTime = System.currentTimeMillis();
            frames = 0;
            secondsPassed++;
        }


        TimeStep current = timeSteps.get( currentTimeStepIndex ) ;  
        //If the value of currentTimeStepStartTime plus the value of the property time of the current var is less than or equal to seconds passed then:
        if ( currentTimeStepStartTime + current.time <= secondsPassed )
            // If the time step is not the last time step then 
            if( current != timesteps.get(timesteps.size()-1) ) // waar halen we de last timestep ? uit de lijst van timesteps? 
                //Set the value of current to the TimeStep at the index currentTimeStepIndex+1 and then 
                curent = timesteps.get(currentTimeStepIndex + 1)
                //call the applyTimeStepMethod with the arguments current, and currentTimeStepIndex + 1
                applyTimeStepMethod(current,currentTimeStepIndex + 1)

        //The next part handles loading. If the game is not loaded, has not ended, and the L key is pressed:
        if(! loaded && !ended ){  // todo : HOE TEST JE DAT L IS  PRESSED ?
            FileDialog fd = new FileDialog(fd, "Pick a data file", FileDialog.LOAD);
            fd.setVisible(true);
            String fname = fd.getDirectory() + fd.getFile();
                    
            //create scanner
            Scanner s; 
                    
            //If the line starts with # it is a comment
            if(String == "#") return null;
            //line starts with “road”
            if(String == "road");
            //call the addTile method with “ROAD”
            {
                ROAD.addTile();
            }    
            if(String == "res");
            {
                RES.addTitle();
                }
                if(String == "com");
                {
                    COM.addTitle();
                }
            }
        }

        
    
    
    /**
     * Method used to create a unique string key for any two integer inputs
     * 
     * @param x The x input
     * @param y The y input
     * @returns The generated key
     */
    private String getKey(int x, int y) {
        return String.format("%d - %d", x, y);
    }
    
     
    /**
     * Method to apply time step values to the world
     * 
     * @param step The timestep to apply
     * @param index The index of the timestep in the timesteps array
     */
    private void applyTimeStep(TimeStep step, int index) {
        currentTimeStepStartTime = secondsPassed;
        currentTimeStepIndex  = index;
        area_width += step.areaWidthIncrese;
        area_height += step.areaHeightIncrese;
        resTiles += step.resTiles;
        comTiles += step.comTiles;
        indTiles += step.indTiles;
        roadTiles += step.roadTiles;
       
    }
    
    
    /**
     * Method called when the game finished
     * This method calles the score method under the hud
     */
    private void score() {
        hud.score(cityMap);
    }
    
    /**
     * Has the user loaded a file?
     * 
     * @returns loaded
     */
    public boolean isLoaded() {
        return loaded;
    }
    
    /**
     * Method to get the current time step
     */
    public TimeStep getCurrentTimeStep() {
        /* The public method getCurrentTimestep() should return the current time step if the game is loaded
        If the value of the instance var loaded is false, return null
        Otherwise return the TimeStep at the index currentTimeStepIndex in the timeSteps list
        code hieronder is juist , maar dan zonder jouw lijn .. dat moest in act() staan
        */

        if(loaded) 
            return timeSteps.get(currentTimeStepIndex);
 
        return null;
    }
    
    /**
     * Get the current world data as a timestep
     * 
     * @returns The timestep containing the current world data
     */
    public TimeStep getCurrentDataAsTimeStep() {
        if (! loaded ) return null;
        
        return new TimeStep( this.area_width, this.area_height, this.resTiles, 
                                    this.comTiles, this.indTiles, this.roadTiles, 0); // with time = 0
        

    }                            
    
    
    /**
     * Place a object into the world
     * 
     * @param type The type of the object to place: ["RES", "COM", "IND", "ROAD"]
     * @param x The x object position
     * @param y The y object position
     */
    public void place(String type, int x, int y) {
        if ( ! loaded) return;
        if ( cityMap.get(getKey(x,y)) != null ) return; 
        if (x < 0 || y < 0) return;
        if ( x > area_width-1 || y > area_height-1) return;
        /*
         * todo
         * Check if the player has more of the tiles they are trying to place 
         * if so remove one of the tiles and call the addTile method with type, x, and y
         */
        switch(type) {
            case "RES":
            // code block
            break;
            case "COM":
            // code block
            break;
            case "IND":
            // code block
            break;
            case "ROAD":
            return(x,y);
            break;
        }
    }
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