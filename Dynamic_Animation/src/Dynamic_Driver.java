/**
 * Created by Rahul Basu on 7/20/2016.
 */
import javax.swing.*;
import java.awt.*;

public class Dynamic_Driver
{
    public static void main(String args[])
    {
        Dynamic_Driver dd = new Dynamic_Driver();
        dd.run();
    }


    private static final DisplayMode modes1[] = {
            new DisplayMode(800,600,32,0),
            new DisplayMode(800,600,32,0),
            new DisplayMode(800,600,32,0),
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,32,0),
    }; //storing 6 (very common) display modes in an arraylist

    private static final long DEMO_TIME = 10000;

    private Sprite sprite;
    private Animation a;
    private BufferedScreen bs;
    private Image bg;


    public void loadImages()
    {
        bg = loadImage("C:\\Users\\Rahul Basu\\Downloads\\New Downloads\\Skyrim.jpg");
        Image faceJens1 = loadImage("C:\\Users\\Rahul Basu\\Downloads\\New Downloads\\Ezio.jpg");
        Image faceJens2 = loadImage("C:\\Users\\Rahul Basu\\Downloads\\New Downloads\\Ezio1.jpg");


        a = new Animation();
        a.addScene(faceJens1,250);
        a.addScene(faceJens2,250);

        sprite = new Sprite(a);
        sprite.setVelocityX(0.3f);
        sprite.setVelocityX(0.3f);


    }

    private Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    public void run()
    {
        bs = new BufferedScreen();
        try
        {
            DisplayMode displayMode = bs.findFirstCompatibleMode(modes1);
            bs.setFullScreen(displayMode);
            loadImages();
            playMovie();
        }
        finally
        {
            bs.restoreScreen();
        }
    }

    //to play the movie
    public void playMovie()
    {
        long startTime = System.currentTimeMillis();
        long currTime = startTime;

        while (currTime - startTime < DEMO_TIME) {
            long elapsedTime =
                    System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            update(elapsedTime);

            // draw and update screen
            Graphics2D g = bs.getGraphics();
            draw(g);
            g.dispose();
            bs.update();

            // take a nap
            try {
                Thread.sleep(20);
            }
            catch (InterruptedException ex) { }
        }

    }

    public void update(long elapsedTime)
    {
        if(sprite.getX()<0) /* if sprite reaches the left-most side */
        {
            sprite.setVelocityX(Math.abs(sprite.getVelocityX())); /*reverses the direction of the sprite upon encountering the left-most side
                                                                    of the screen*/
        }
        else
            if (sprite.getX()+sprite.getWidth() >= sprite.getWidth()) /* if sprite reaches the right most side */
            {
                sprite.setVelocityX(-Math.abs(sprite.getVelocityX())); /* assign a negative velocity to reverse its direction */
            }
        if(sprite.getY()<0) /* repeat the same for  the vertical direction */
        {
            sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
        }
        else
        if (sprite.getY()+sprite.getWidth() >= sprite.getWidth())
        {
            sprite.setVelocityY(-Math.abs(sprite.getVelocityY()));
        }

        sprite.update(elapsedTime);
    }

    public void draw(Graphics g)
    {
        g.drawImage(bg, 0, 0, null); //for the background
        g.drawImage(sprite.getImage(), Math.round(sprite.getX()),Math.round(sprite.getY()),null); //SPRITE!!
    }
}
