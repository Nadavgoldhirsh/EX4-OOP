package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * This class has a method that created the night in the simulation
 */
public class Night {

    private static final String NIGHT = "Night";
    private static final Float MIDNIGHT_OPACITY = 0.5f;
    private static final float HALF_DAY = 0.5f;

    /**
     * Class Ctor
     */
    public Night() {}

    /**
     * This method creates a game obj that represents the night in the simulation
     * @param windowDimensions the dims of the window
     * @param cycleLength
     * @return a game obj that represents the night in the simulation
     */
    public static GameObject create(Vector2 windowDimensions,float cycleLength){
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.black));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT);
        new Transition<Float>(
                night, // the game object being changed
                night.renderer()::setOpaqueness, // the method to call
                0f, // initial transition value
                MIDNIGHT_OPACITY, // final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT,// use a cubic interpolator
                cycleLength* HALF_DAY, // transition fully over half a day
        Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
        null); // nothing further to execute upon reaching final value
        return night;
    }

}
