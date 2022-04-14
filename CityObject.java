import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a abstract class that is used to display objects at a grid position.
 * This class does not need to be abstract. I made it abstract because you sbould not create an instance of this class.
 * You should only create an instnce of any child class this class may have.
 * 
 * @author (Name Here)
 * @version (Date)
 */
public abstract class CityObject extends Actor
{
    public CityObject(int x, int y) {
        
    }
    
    /**
     * CityObject wants to resize its image and move to a position based on the camera position
     * and its grid position
     */
    public void act()
    {
        
    }
    
    private GridWorld getGridWorld() {
        
    }
}
