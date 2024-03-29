package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * This class represents a fruit in the game
 */
public class Fruit extends GameObject implements Observer {
    /**
     * This string represents the tag of the fruit
     */
    public static final String FRUIT_TAG = "FRUIT";
    private boolean redColor;


    /**
     * Class Ctor
     * @param pos
     * @param dim
     */
    public Fruit( Vector2 pos, Vector2 dim) {
        super(pos, dim, new OvalRenderable(Color.magenta));
        redColor = true;
        this.setTag(FRUIT_TAG);
    }

    /**
     * This method changes the fruit color when being called
     */
    @Override
    public void changeBecauseOfJump() {
        if(redColor){
            this.renderer().setRenderable(new OvalRenderable(Color.yellow));
            redColor = false;
        }
        else{
            this.renderer().setRenderable(new OvalRenderable(Color.magenta));
            redColor = true;
        }
    }
}
