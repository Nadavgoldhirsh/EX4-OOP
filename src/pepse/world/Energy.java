package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.util.function.*;

/**
 * This class job is to hold the energy level of the avatar and presents it on the screen
 */
public class Energy extends GameObject {
    private static final String EMPTY_STR_FOR_CONCATING = "";
    private static TextRenderable energyText;
    private static Supplier <Float> vu;
    private Float energy;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Energy(Vector2 topLeftCorner, Vector2 dimensions,
                  TextRenderable renderable, Supplier<Float> func) {
        super(topLeftCorner, dimensions, renderable);
        energyText = renderable;
        vu = func;
    }

    /**
     * this is
     * @param energy
     */

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        energy = vu.get();
        energyText.setString(EMPTY_STR_FOR_CONCATING + energy.intValue());
    }
}
