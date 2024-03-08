package world.trees;

import java.awt.event.KeyEvent;
import java.util.Random;
import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import world.Avatar;

import java.awt.*;

public class StaticLeaf extends GameObject {

    Random rnd = new Random();
    private static final int LEAF = 20;
    private static final float SDEG = 15f;
    private static final float FDEG = 30f;
    private ScheduledTask a;
    private ScheduledTask b;
    private Transition<Float> t3;
    private Transition<Float> t1;



    public StaticLeaf(Vector2 pos, Vector2 dim){
        super(pos, dim, new RectangleRenderable(new Color(50, 200, 30)));

        a = new ScheduledTask(
                this,
                rnd.nextFloat(2),
                true,
                this::t1);
        b = new ScheduledTask(
                this,
                rnd.nextFloat(2),
                true,
                this::t2);
    }

    private void t1() {
             t1 = new Transition<Float>(
                    this, // the game object being changed
                    renderer()::setRenderableAngle, // the method to call
                    SDEG, // initial transition value
                    FDEG, // final transition value
                    Transition.CUBIC_INTERPOLATOR_FLOAT,// use a cubic interpolator
                    rnd.nextFloat(2) + 2, // transition fully over half a day
                    Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                    null);

    }
    private void t2() {

            Transition<Vector2> t2 = new Transition<Vector2>(
                    this, // the game object being changed
                    this::setDimensions, // the method to call
                    Vector2.of(LEAF, LEAF), // initial transition value
                    Vector2.of(LEAF - rnd.nextInt(2 + 2), LEAF - rnd.nextInt(2 + 2)), // final transition value
                    Transition.CUBIC_INTERPOLATOR_VECTOR,// use a cubic interpolator
                    rnd.nextFloat(2) + 2, // transition fully over half a day
                    Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                    null);

    }

}
