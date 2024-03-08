package world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Fruit extends GameObject {



    public Fruit(Vector2 pos, Vector2 dim, GameObjectCollection gameObjectCollection) {
        super(pos, dim, new OvalRenderable(Color.magenta));



    }

}
