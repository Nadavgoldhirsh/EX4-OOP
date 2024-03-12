package pepse.world.trees;

import java.util.Objects;
import java.util.Random;
import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;
import pepse.util.ColorSupplier;

import java.awt.*;

/**
 * This class represents a Leaf on the Tree
 */

public class StaticLeaf extends GameObject implements Observer {

    private static final float MAX_DEGS = 90f;
    private static final float TRANSITION_TIME_FOR_JUMP = 1f;
    private static final int TIME_TO_MULT = 2;
    private static final int BOUND = 4;
    private static final Color DEF_COLOR = new Color(50, 200, 30);
    private static final int TIME_TO_ADD = 3;
    private static final float START_ZERO_DEGS = 0f;
    private static final Random rnd = new Random();
    private static final int LEAF = 20;
    private static final float SDEG = 5f;
    private static final float FDEG = 15f;




    /**
     * Class Ctor
     * @param pos the leaf position
     * @param dim the dims of the leaf
     */
    public StaticLeaf(Vector2 pos, Vector2 dim){
        super(pos, dim, new RectangleRenderable(ColorSupplier.approximateColor(
                DEF_COLOR)));

        new ScheduledTask(
                this,
                rnd.nextFloat() ,
                false,
                this::t1);
        new ScheduledTask(
                this,
                rnd.nextFloat(),
                false,
                this::t2);
    }
    /**
     * 90 degs rotate
     */
    @Override
    public void changeBecauseOfJump() {
        new Transition<Float>(
                this, // the game object being changed
                renderer()::setRenderableAngle, // the method to call
                START_ZERO_DEGS,
                MAX_DEGS,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                TRANSITION_TIME_FOR_JUMP,
                Transition.TransitionType.TRANSITION_ONCE,
                null);

    }
    private void t1() {
         new Transition<Float>(
                this, // the game object being changed
                renderer()::setRenderableAngle, // the method to call
                SDEG, // initial transition value
                FDEG, // final transition value
                Transition.LINEAR_INTERPOLATOR_FLOAT,// use a cubic interpolator
                 (rnd.nextFloat() * TIME_TO_MULT)+ TIME_TO_ADD,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                null);
    }
    private void t2() {
        new Transition<Vector2>(
                this, // the game object being changed
                this::setDimensions, // the method to call
                Vector2.of(LEAF, LEAF), // initial transition value
                Vector2.of(LEAF - rnd.nextInt(BOUND), LEAF - rnd.nextInt(BOUND)), // final transition value
                Transition.LINEAR_INTERPOLATOR_VECTOR,// use a cubic interpolator
                (rnd.nextFloat() * TIME_TO_MULT)+ TIME_TO_ADD, // transition fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                null);

    }

}
