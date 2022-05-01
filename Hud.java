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
    String[] messages = new String[] { "Welcome to city planner!",
        "Use the arrow keys to move the camera.","Hover over a grid cell then use the keys 1-4 to place a city object." , "1- Res(idential)",
        "2- Com(mercial)","3- Ind(ustrial)", "4- Road", "Use the L key to load a data file to start the game." };
       
    
    /**
     * Use the constructor to display some starting data
     */
    public Hud(GridWorld world) {
        
        //GreenfootImage textImage = new GreenfootImage("Press run to start", 24, new Color(0, 255, 128), new Color(0, 0, 0, 0));
        world.showText("Press run to start", GridWorld.WORLD_X/2 ,GridWorld.WORLD_Y/4);
    }
    /**
     * Drawhe HUD to the screen and handle placment
     */
    public void act()
    {
        GridWorld gw = this.getGridWorld();
        if( gw.isLoaded() ){
            clearInfo();
            drawData(gw.getCurrentDataAsTimeStep());
            MouseInfo mouse = Greenfoot.getMouseInfo();
            Camera camera  = gw.getCamera();
            if(mouse != null)
            {
                int x = (mouse.getX() + camera.cameraX)/gw.cellSize;
                int y = (mouse.getY() + camera.cameraY)/gw.cellSize;
            
                if(Greenfoot.isKeyDown("1"))gw.place("RES", x, y);
                if(Greenfoot.isKeyDown("2"))gw.place("COM", x, y);
                if(Greenfoot.isKeyDown("3"))gw.place("IND", x, y);
                if(Greenfoot.isKeyDown("4"))gw.place("ROAD", x, y);
                
            }
        }
        else
        {
            this.drawInfo();
        }
    }
    
    /**
     * Used to draw instructions to the screen before the user has loaded a level
     */
    private void drawInfo() {
      
        int posY = GridWorld.WORLD_Y / 4;
        GridWorld gw = (GridWorld) this.getWorld();
         for( int i = 0 ; i< messages.length;i++)
        {
            gw.showText(messages[i], GridWorld.WORLD_X/2, posY + ((i+1) * 32 ));
        }

        
        }
    
    /**
     * Clears the instuctions from the screen once the user has loaded a file
     */
    private void clearInfo() {
        int posY = GridWorld.WORLD_Y / 4;
        GridWorld gw = (GridWorld) this.getWorld();
         for( int i = 0 ; i< messages.length;i++)
        {
            gw.showText(null, GridWorld.WORLD_X, posY + ((i+1) * 32 ));
        }
        
    }
    
    /**
     * Draws stats and data to the screen
     * 
     * @param data The timestep containing the data to display
     */
    private void drawData(TimeStep data) {
        /*
         * The private method drawData should take a TimeStep as a parameter.
The Time step’s res tiles should be drawn 1/5 the way acrost the screen and 48 pixels down
The Time steps com tiles should be drawn 2/5 the way acrost the screen and 48 pixels down
The Time step’s ind tiles should be drawn 3/5 the way acrost the screen and 48 pixels down
The Time step’s road tiles should be drawn 4/5 the way acrost the screen and 48 pixels down
The Time step’s area should be drawn halfway acrost the screen and 64 pixels down

         */
        
        GridWorld gw = (GridWorld) this.getWorld();
        gw.showText(Integer.toString(data.resTiles), GridWorld.WORLD_X * 1 / 5, 48);
        gw.showText(Integer.toString(data.comTiles), GridWorld.WORLD_X * 2 / 5, 48);
        gw.showText(Integer.toString(data.indTiles), GridWorld.WORLD_X * 3 / 5, 48);
        gw.showText(Integer.toString(data.roadTiles), GridWorld.WORLD_X * 4 / 5, 48);
        //todo
        gw.showText(Integer.toString(data.areaWidthIncrese), GridWorld.WORLD_X / 2 , 64);
        
        
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
        if(check == null)return 0;
        int total = 0;
        if(up != null && check == up) total++;
        if(down != null && check == up) total++;
        if(left != null && check == up) total++;
        if(right!= null && check == up) total++;
        return total;
    }
    
    private GridWorld getGridWorld() {
        return (GridWorld) getWorld();
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
