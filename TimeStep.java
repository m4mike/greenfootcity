/**
 * A class that holds all of the information about how to advance to the next time step of the game (think of these as levels)
 * Time is measured in seconds
 * 
 * 
 * @author (Name Here)
 * @version (Date)
 */
public class TimeStep  
{
    public int areaWidthIncrese;
    public int areaHeightIncrese;
    public int resTiles;
    public int comTiles;
    public int indTiles;
    public int roadTiles;
    public int time;
    public boolean last = false;


    /**
     * Constructor for objects of class TimeStep defaulting last to false
     */
    public TimeStep(int areaWidthIncrese, int areaHeightIncrese, int resTiles, int comTiles, int indTiles, int roadTiles, int time) {
        initVars(areaWidthIncrese, areaHeightIncrese, resTiles, comTiles, indTiles, roadTiles, time);
     }
    
    /**
     * Constructor for objects of class TimeStep
     */
    public TimeStep(int areaWidthIncrese, int areaHeightIncrese, int resTiles, int comTiles, int indTiles, int roadTiles, int time, boolean last) {
        initVars(areaWidthIncrese, areaHeightIncrese, resTiles, comTiles, indTiles, roadTiles, time);
        this.last = last;
    }

    private void initVars(int areaWidthIncrese, int areaHeightIncrese, int resTiles, int comTiles, int indTiles, int roadTiles, int time)
    {
        this.areaWidthIncrese = areaWidthIncrese;
        this.areaHeightIncrese = areaHeightIncrese;
        this.resTiles = resTiles;
        this.comTiles = comTiles;
        this.indTiles = indTiles;
        this.roadTiles = roadTiles;
        this.time = time;
    }
}
