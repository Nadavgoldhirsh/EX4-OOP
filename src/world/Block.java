package world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class represents a Block to build the ground from
 */
public class Block extends GameObject {
    /**
     * represents the Block size
     */
    public static final int SIZE = 30;

    /**
     * This is the Ctor of the Class
     * @param topLeftCorner the top left corner we want the block to have
     * @param renderable a renderable obj to show as the block
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        //this line says that this object can't be skipped by another object in the same layer
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        // this line says that this obj doesn't move and if there is a collision it stays put
    }
}

