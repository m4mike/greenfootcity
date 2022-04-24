import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * The camera that all city objects position themselves releative to
 * This class is also incharge of drawing the grid
 * 
 * @author Liam Bentein
 */
public class Camera extends Actor
{
    public int cameraX = 0;
    public int cameraY = 0;
    private GreenfootImage image;
    private int cellSizeHash;

    /**
     * Act - do whatever the Camera wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        
    }
    
    private GridWorld getGridWorld() {
        return (GridWorld) getWorld();
    }
    
    private GreenfootImage createImage() {
        GreenfootImage img = new GreenfootImage(GridWorld.WORLD_X + getGridWorld().cellSize*2, GridWorld.WORLD_Y + getGridWorld().cellSize*2);
        int cellSize = getGridWorld().cellSize;
        img.setColor( Color.GREY);
        for(int x = 0;  x <= img.getWidth(); x += cellSize)
            img.drawLine(x, 0, x, img.getHeight());
   
        for(int y = 0; y <= img.getHeight() ; y += cellSize)
            img.drawLine(0, y ,img.getWidth(), y);
            
        return img;
    }
}
