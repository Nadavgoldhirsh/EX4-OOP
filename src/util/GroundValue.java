package util;

import danogl.util.Vector2;

public class GroundValue {
    private final static float INIT_HEIGHT_MULT = (float) 2/3;

    public static int GetInitialGroundValue(Vector2 winDims){
        return (int) (winDims.y()*INIT_HEIGHT_MULT);

    }
}
