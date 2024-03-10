package pepse.util;

import danogl.util.Vector2;

/**
 * This class has a method that returns the intial height of the ground
 */
public class GroundValue {
    private final static float INIT_HEIGHT_MULT = (float) 2/3;

    /**
     * This method returns the initial ground height
     * @param winDims a vector of the window dims
     * @return the initial ground height
     */

    public static int GetInitialGroundValue(Vector2 winDims){
        return (int) (winDims.y()*INIT_HEIGHT_MULT);

    }
}
