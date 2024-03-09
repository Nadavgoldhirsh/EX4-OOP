package world.trees;

import java.util.List;
import java.util.Random;
import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import world.Observer;

import java.awt.*;

public class StaticLeaf extends GameObject implements Observer {

    private static final float ADDED_DEGS = 90f;
    private static final float TRANSITION_TIME_FOR_JUMP = 0.5f;
    private static final int TIME_TO_MULT = 2;
    private static final int BOUND = 2 + 2;
    private static final Color DEF_COLOR = new Color(50, 200, 30);
    Random rnd = new Random();
    private static final int LEAF = 20;
    private static final float SDEG = 5f;
    private static final float FDEG = 15f;


    @Override
    public void changeBecauseOfJump() {
        new Transition<Float>(
                this, // the game object being changed
                renderer()::setRenderableAngle, // the method to call
                0f,
                ADDED_DEGS,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                TRANSITION_TIME_FOR_JUMP,
                Transition.TransitionType.TRANSITION_ONCE,
                null);

    }

    public StaticLeaf(Vector2 pos, Vector2 dim){
        super(pos, dim, new RectangleRenderable(DEF_COLOR));

        new ScheduledTask(
                this,
                rnd.nextFloat() * TIME_TO_MULT,
                true,
                this::t1);
        new ScheduledTask(
                this,
                rnd.nextFloat() * TIME_TO_MULT,
                true,
                this::t2);
    }

    private void t1() {
         new Transition<Float>(
                this, // the game object being changed
                renderer()::setRenderableAngle, // the method to call
                SDEG, // initial transition value
                FDEG, // final transition value
                Transition.LINEAR_INTERPOLATOR_FLOAT,// use a cubic interpolator
                 rnd.nextFloat() * TIME_TO_MULT + TIME_TO_MULT, // transition fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                null);
    }
    private void t2() {
        Transition<Vector2> t2 = new Transition<Vector2>(
                this, // the game object being changed
                this::setDimensions, // the method to call
                Vector2.of(LEAF, LEAF), // initial transition value
                Vector2.of(LEAF - rnd.nextInt(BOUND), LEAF - rnd.nextInt(BOUND)), // final transition value
                Transition.LINEAR_INTERPOLATOR_VECTOR,// use a cubic interpolator
                rnd.nextFloat() * TIME_TO_MULT + TIME_TO_MULT, // transition fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                null);

    }

}
