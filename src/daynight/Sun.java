package daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import util.GroundValue;
import world.Block;
import world.Terrain;

import java.awt.*;

/**
 * This class has a method that creates the Sun game object in the Simulation
 */
public class Sun {

    private static final String SUN = "Sun";
    public static final float HALF = 0.5f;

    /**
     * Class Ctor
     */
    public Sun() {}
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        Vector2 size = new Vector2(Block.SIZE,Block.SIZE);
        Vector2 topLeftCorner =  windowDimensions.mult(HALF).subtract(size.mult(HALF));
        GameObject sun = new GameObject(topLeftCorner,size,new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN);
        Vector2 cycleCenter = new Vector2(windowDimensions.x()*HALF,
                GroundValue.GetInitialGroundValue(windowDimensions));
        Vector2 initialSunCenter = sun.getCenter();
        new Transition<Float>(
                sun, // the game object being changed
                (Float angle) -> sun.setCenter
                        (initialSunCenter.subtract(cycleCenter)
                                .rotated(angle)
                                .add(cycleCenter))
                ,0f
                ,360f
                ,Transition.LINEAR_INTERPOLATOR_FLOAT
                ,cycleLength
                , Transition.TransitionType.TRANSITION_LOOP
                ,null);

        return sun;
    }
}
