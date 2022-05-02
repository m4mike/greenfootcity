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

    private Camera camera;
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
        //false as last parameter to allow actors to move of screen
        super(WORLD_X, WORLD_Y, WORLD_CELL_SIZE,false);
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
        if (loaded)
        {
            TimeStep current = timeSteps.get( currentTimeStepIndex ) ;  
              //If the value of currentTimeStepStartTime plus the value of the property time of the current var is less than or equal to seconds passed then:
            if ( currentTimeStepStartTime + current.time <= secondsPassed )
            {
                // If the time step is not the last time step then 
                if( current != timeSteps.get(timeSteps.size()-1) )
                { // waar halen we de last timestep ? uit de lijst van timesteps? 
                    //Set the value of current to the TimeStep at the index currentTimeStepIndex+1 and then 
                    current = timeSteps.get(currentTimeStepIndex + 1);
                    //call the applyTimeStepMethod with the arguments current, and currentTimeStepIndex + 1
                    applyTimeStep(current,currentTimeStepIndex + 1);
                }
                else //Otherwise set the instance var ended to true and call the score method
                {
                    
                    this.ended = true;
                    this.score();   
                }
            }
        }
        
                
            
        //The next part handles loading. If the game is not loaded, has not ended, and the L key is pressed:
        if(! loaded && !ended && Greenfoot.isKeyDown("L")){  
           
            FileDialog fd = null;
            fd = new FileDialog(fd, "Pick a data file", FileDialog.LOAD);
            fd.setVisible(true);
            String fname = fd.getDirectory() + fd.getFile();

            File text = new File(fname);
            try{
                Scanner s = new Scanner(text);
    
                //Reading each line of the file using Scanner class
                while(s.hasNextLine()){
                    String line = s.nextLine();
                    if( line.startsWith("#")) continue;
                    //split the line into an array
                    String[] words = line.split(" ");
    
                    //If the line starts with “road” 
                    //the next two integers are the position you should call the addTile method with 
                    //“ROAD” and the two integers you collected
                    if( words[0].equals("road")){
                        addTile("ROAD",Integer.parseInt(words[1]),Integer.parseInt(words[2]));
                        continue;
                    }
                    //If the line starts with “res” the next two integers are the position 
                    //  you should call the addTile method with “RES” and the two integers you collected
                    if( words[0].equals("res")){
                        addTile("RES",Integer.parseInt(words[1]),Integer.parseInt(words[2]));
                        continue;
                    }
                    //If the line starts with “com” the next two integers are the position 
                    //  you should call the addTile method with “COM” and the two integers you collected
                    if( words[0].equals("com")){
                        addTile("COM",Integer.parseInt(words[1]),Integer.parseInt(words[2]));
                        continue;
                    }
                    //If the line starts with “ind” the next two integers are the position 
                    //  you should call the addTile method with “IND” and the two integers you collected
                    if( words[0].equals("ind")){
                        addTile("IND",Integer.parseInt(words[1]),Integer.parseInt(words[2]));
                        continue;
                    }
                    //If the line starts with “time_step” the next 7 integers will be the arguments 
                    //  to pass to the TimeStep constructor. Create a TimeStep using the 7 collected integers 
                    //  then add the new timestep to the timeSteps list using timeSteps.add()
                    if( words[0].equals("time_step")){
                        TimeStep ts =  new TimeStep(Integer.parseInt(words[1]),
                        Integer.parseInt(words[2]), Integer.parseInt(words[3]), Integer.parseInt(words[4]),
                        Integer.parseInt(words[5]), Integer.parseInt(words[6]), Integer.parseInt(words[7]));
                        //TimeStep(int areaWidthIncrese 1, int areaHeightIncrese 2, int resTiles 3,
                        // int comTiles 4, int indTiles 5 , int roadTiles 6, int time 7) 
                        timeSteps.add(ts);
                        continue;
                    }
                    //If the line starts with something unknown an error should be logged to the console
                    System.out.println("Unknown command line in data file "); 
                    s.close();
                    fd.dispose();
                }
            }catch( Exception e) 
                {
                    System.out.println("Error reading data file ");
                }
              
            
            //If the size of the timeSteps list is less that 1 
            //print an error to the console saying the file contained 0 time steps
            if(timeSteps.size() < 1)  
                System.out.println("The date file contained 0 time steps "); 
            else{
                //Otherwise set the last property of the TimeStep at the index timeSteps.size()-1 
                //of the list timeSteps to true. Set the value of the instance var loaded to true, 
                //and call the applyTimeStep method with the first element of the timeSteps list and 0
                timeSteps.get(timeSteps.size() - 1).last = true;
                loaded = true;
                applyTimeStep(timeSteps.get(0),0);

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
        if(loaded) 
            return timeSteps.get(currentTimeStepIndex);
        //at index of currentTimeStepIndex
        TimeStep = currentTimeStepIndex;
        //in the list of timeSteps
        //
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
                if(resTiles> 0)
                {
                    resTiles--;
                    addTile(type, x, y);
            }
                break;
            case "COM":
                if(comTiles> 0)
                {
                    comTiles--;
                    addTile(type, x, y);
                }
                break;
            case "IND":
                if(indTiles > 0)
                {
                    indTiles--; 
                    addTile(type, x, y);
                }
                break;
            case "ROAD":
                if(roadTiles> 0)
                {
                  roadTiles--; 
                  addTile(type, x, y);  
                }
                break;
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