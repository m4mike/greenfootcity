import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * The camera that all city objects position themselves releative to
 * This class is also incharge of drawing the grid
 * 
 * @author Liam Bentein
<<<<<<< HEAD
 * @version (Date)
=======
>>>>>>> 8235762851f3c323cc5a1bf71cb6cae7e35e4dd8
 */
public class Camera extends Actor
{
    public int cameraX = 0;
    public int cameraY = 0;
    private GreenfootImage image;
    private int cellSizeHash;
    public int cellSize;
    public int offsetX;
    public int offsetY;

    /**
     * Act - do whatever the Camera wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(Greenfoot.isKeyDown("up"))
        {
            setLocation(getX(), getY() -2);
        }
         if(Greenfoot.isKeyDown("down"))
        {
            setLocation(getX(), getY() +2);
        }
        if(Greenfoot.isKeyDown("left"))
        {
            setLocation(getX()-2, getY() );
        }
        if(Greenfoot.isKeyDown("right"))
        {
            setLocation(getX() +2, getY() );
        }
        cellSize = getGridWorld().cellSize;
        offsetX = cameraX%cellSize;
        offsetY = cameraY%cellSize;
        if(cellSizeHash  != cellSize){
            cellSizeHash = cellSize;
            image = createImage();

        }
        setLocation(-offsetX + GridWorld.WORLD_X / 2, -offsetY + GridWorld.WORLD_Y / 2);
        


        
    }
    
    private GridWorld getGridWorld() {
        return (GridWorld) getWorld();
    }
    
    private GreenfootImage createImage() {
        GreenfootImage img = new GreenfootImage(GridWorld.WORLD_X + getGridWorld().cellSize*2, GridWorld.WORLD_Y + getGridWorld().cellSize*2);
        int cellSize = getGridWorld().cellSize;
        img.setColor( Color.GRAY);
        for(int x = 0;  x <= img.getWidth(); x += cellSize)
            img.drawLine(x, 0, x, img.getHeight());
   
        for(int y = 0; y <= img.getHeight() ; y += cellSize)
            img.drawLine(0, y ,img.getWidth(), y);
            
        return img;
    }
}
