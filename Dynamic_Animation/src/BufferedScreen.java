/**
 * Created by Rahul Basu on 7/20/2016.
 */
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

public class BufferedScreen //To use Buffer Strategy and Page Flipping
{
    private GraphicsDevice videocard;


    public BufferedScreen()
    {
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        videocard = genv.getDefaultScreenDevice();
    }

    //get all compatible Display Modes
    public DisplayMode[] getCompatibleDisplayModes() //to get all the Display Modes in the local machine's graphics card.
    {
        return videocard.getDisplayModes();
    }

    public DisplayMode findFirstCompatibleMode(DisplayMode mode[])
    {
        DisplayMode goodmode[] = videocard.getDisplayModes();
        for(int x = 0; x<mode.length; x++) //loop through the Display Modes
        {
            for(int y=0;y<goodmode.length; y++) //loop through video card modes inside the Display Mode
            {
                if(displayModesMatch(mode[x],goodmode[y])) //compare the two modes to see if they match
                {
                    return mode[x]; //return the matching mode
                }
            }
        }
        return null;
    }

    public DisplayMode getCurrentDisplayMode()
    {
        return videocard.getDisplayMode();
    }

    public boolean displayModesMatch(DisplayMode m1, DisplayMode m2) //test for resolution if they match
    {
        if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight())
        {
            return false;
        }
        if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth())
        {
            return false;
        }
        if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate())
        {
            return false;
        }

        return true;
    }

    // make frame full screen
    public void setFullScreen(DisplayMode dm)
    {
        JFrame j=new JFrame();
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setUndecorated(true);
        j.setIgnoreRepaint(true);
        j.setResizable(false);


        videocard.setFullScreenWindow(j);

        if(dm != null && videocard.isDisplayChangeSupported())
        {
            try
            {
                videocard.setDisplayMode(dm);
            }
            catch (IllegalArgumentException ex) {}


        }
        // avoid potential deadlock in 1.4.1_02
        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    j.createBufferStrategy(2); // 2 separate panels for page flipping
                }
            });
        }
        catch (InterruptedException ex) {
            // ignore
        }
        catch (InvocationTargetException ex) {
            // ignore
        }
    }

    public Graphics2D getGraphics()
    {
        Window w = videocard.getFullScreenWindow(); //stores the full screen to a variable (w)
        if(w != null)
        {
            BufferStrategy s = w.getBufferStrategy();
            return (Graphics2D)s.getDrawGraphics();
        }
        else
        {
            return null;
        }
    }

    public void update()
    {
        Window w = videocard.getFullScreenWindow();
        if(w != null)
        {
            BufferStrategy s= w.getBufferStrategy();
            if(!s.contentsLost())
            {
                s.show(); //shows only when there's content to show
            }
        }
    }

    public Window getFullScreenWindow()
    {
        return videocard.getFullScreenWindow();
    }

    public int getWidth()
    {
        Window w= videocard.getFullScreenWindow();
        if(w!= null)
            return  w.getWidth();
        else
            return 0;
    }

    public void restoreScreen()
    {
        Window w= videocard.getFullScreenWindow();
        if(w!= null)
            w.dispose();
        videocard.getFullScreenWindow();
    }

    public BufferedImage createCompatibleImage(int width,int height,int transparency)
    {
        Window w= videocard.getFullScreenWindow();
        if(w!= null)
        {
            GraphicsConfiguration gc = w.getGraphicsConfiguration(); //gets the configuration of the monitor and stores it
            return gc.createCompatibleImage(width,height,transparency);
        }
        else
            return null;
    }

}
