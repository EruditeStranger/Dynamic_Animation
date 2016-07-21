/**
 * Created by Rahul Basu on 7/19/2016.
 */
import java.awt.Image;
import java.util.ArrayList;

public class Animation {
    private ArrayList scenes;
    private int sceneIndex;
    private long movieTime;
    private long totalTime;

    public Animation() {
        scenes = new ArrayList();
        totalTime = 0;
        start();
    }

    //add scene to ArrayList and set time for each scene
    public synchronized void addScene(Image i, long t) {
        totalTime += t;
        scenes.add(new OneScene(i, totalTime));
    }

    public synchronized void start() {
        movieTime = 0;
        sceneIndex = 0;
    }

    public synchronized void update(long timePassed) // change scenes
    {
        if (scenes.size() > 1) //if there's just one picture in the entire animation sequence
        {
            movieTime += timePassed;
            if (movieTime >= totalTime) {
                movieTime = 0;
                sceneIndex = 0;
            }
            while (movieTime > getScene(sceneIndex).endTime) //when a picture is called, it's going to run for a certain amount of time.
            //After that, the procedure is repeated for the next iteration of sceneIndex.
            {
                sceneIndex++;
            }
        }
    }

    public synchronized Image getImage() {
        if (scenes.size() == 0)
            return null;
        else
            return getScene(sceneIndex).pic;
    }

    private OneScene getScene(int x) {
        return (OneScene) scenes.get(x);
    }

    // class to convert the picture to an object
    private class OneScene {
        Image pic;
        long endTime;

        public OneScene(Image pic, long endTime) {
            this.pic = pic;
            this.endTime = endTime;
        }
    }
}