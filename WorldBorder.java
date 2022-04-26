import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Displays a red border where the edge of your city is
 * 
 * @author (Name Here)
 * @version (Date)
 */
public class WorldBorder extends Actor
{
    private int cellSizeHash;
    private int widthHash;
    private int heightHash;

    /**
     * Draw the border and move to the correct position
     */
    public void act()
    {
        int cellSize = getGridWorld().cellSize;
        int width = getGridWorld().area_width * cellSize;
        int height = getGridWorld().area_height * cellSize;
        Camera camera = getGridWorld().getCamera();
        if(cellSizeHash != cellSize || widthHash != width || heightHash != height)
        {
            cellSizeHash = cellSize;
            widthHash = width;
            heightHash = height;
            if(width > 0 && height > 0 )
            {
                setImage(createImage(width, height));
            }
        }
        setLocation(-camera.cameraX + width/2, -camera.cameraY + height/2);
        
    }
    
    private GridWorld getGridWorld() {
        return (GridWorld) getWorld();
    }
    
    private GreenfootImage createImage(int width, int height) {
        GreenfootImage img = new GreenfootImage(width,height);
        img.setColor( Color.RED);
        img.drawRect(0, 0,width-1, height-1);
        return img;
    }
    
}
