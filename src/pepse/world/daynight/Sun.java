package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.GroundValue;
import pepse.world.Block;

import java.awt.*;

/**
 * This class has a method that creates the Sun game object in the Simulation
 */
public abstract class Sun {

    private static final String SUN = "Sun";
    private static final float HALF = 0.5f;
    private static final float TRANS_INIT_VAL = 0f;
    private static final float TRANS_FINAL_VAL = 360f;


    /**
     * This method creates the sun
     * @param windowDimensions the dims of the window
     * @param cycleLength the cycle time of the sun
     * @return the Sun obj
     */
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
                , TRANS_INIT_VAL
                , TRANS_FINAL_VAL
                ,Transition.LINEAR_INTERPOLATOR_FLOAT
                ,cycleLength
                , Transition.TransitionType.TRANSITION_LOOP
                ,null);

        return sun;
    }
}
