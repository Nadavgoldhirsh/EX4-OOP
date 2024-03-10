package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * This class has a method that creates the sun halo
 */
public  abstract class SunHalo {
    private final static Color HALO_COLOR = new Color(255, 255, 0, 20);
    private static final float SUN_SIZE_MULT = 4f;
    private static final String HALO = "Halo";
    private static final int MULT_FOR_GETTING_TOP_LEFT_CORNER = 2;



    /**
     * This class creates the sun halo in the simulation
     * @param sun a Sun game object
     * @return a ga,e object that represents the sun
     */
    public static GameObject create(GameObject sun){
        Vector2 sunSize = sun.getDimensions();
        Vector2 topLeftCorner = new Vector2(sun.getCenter()).subtract(
                sunSize.mult(MULT_FOR_GETTING_TOP_LEFT_CORNER));
        Vector2 size = new Vector2(sunSize.mult(SUN_SIZE_MULT));
        GameObject halo = new GameObject(topLeftCorner,size,new OvalRenderable(HALO_COLOR));
        halo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        halo.setTag(HALO);
        return halo;

    }
}

