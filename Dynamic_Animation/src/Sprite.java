/**
 * Created by Rahul Basu on 7/19/2016.
 */
import java.awt.Image;

public class Sprite {

    private Animation animu;
    // position (pixels)
    private float x;
    private float y;
    // velocity (pixels per millisecond)
    private float vx;
    private float vy;


    public Sprite(Animation animu) {
        this.animu = animu;
    } //Updates this Sprite's Animation and its position based on the velocity.

    public void update(long elapsedTime)
    {
        x += vx * elapsedTime;
        y += vy * elapsedTime;
        animu.update(elapsedTime);
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    /**
     Gets this Sprite's width, based on the size of the
     current image.
     */
    public int getWidth()
    {
        return animu.getImage().getWidth(null);
    }

    /**
     Gets this Sprite's height, based on the size of the
     current image.
     */
    public int getHeight() {
        return animu.getImage().getHeight(null);
    }

    /**
     Gets the horizontal velocity of this Sprite in pixels
     per millisecond.
     */
    public float getVelocityX() {
        return vx;
    }

    /**
     Gets the vertical velocity of this Sprite in pixels
     per millisecond.
     */
    public float getVelocityY() {
        return vy;
    }

    /**
     Sets the horizontal velocity of this Sprite in pixels
     per millisecond.
     */
    public void setVelocityX(float dx) {
        this.vx = dx;
    }

    /**
     Sets the vertical velocity of this Sprite in pixels
     per millisecond.
     */
    public void setVelocityY(float dy) {
        this.vy = dy;
    }

    /**
     Gets this Sprite's current image.
     */
    public Image getImage() {
        return animu.getImage();
    }
}
