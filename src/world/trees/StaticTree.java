package world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.GameObjectPhysics;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.util.Random;
import java.awt.*;

public class StaticTree extends GameObject {
    private GameObjectCollection gameObjects;
    private static StaticLeaf staticLeaf;
    private static Fruit fruit;
    private static final int LEAF = 20;
    private static final int FRUIT = 13;

    private static final int BETWEENLEAF = 3;
    private static final int MINLEAF = 10;

    private static int leafamount= 11;
    private static final double COIN = 0.09;
    private static final double COINF = 0.9;
    private static final int LEAFLAYER = -50;

    Random rnd = new Random();



    public StaticTree(GameObjectCollection gameObjects, Vector2 pos, Vector2 dim){
        super(pos, dim, new RectangleRenderable(new Color(100, 50, 20)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        //this line says that this object can't be skipped by another object in the same layer
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        // this line says that this obj doesn't move and if there is a collision it stays put
        leafamount = rnd.nextInt(2)+MINLEAF;
        for (int i = 0; i<leafamount; i++){
            for (int j = 0; j<leafamount; j++){
                if(rnd.nextDouble(1)>COIN){
                    staticLeaf = new StaticLeaf(Vector2.of(pos.x()-(LEAF+BETWEENLEAF)*(leafamount-1)/2
                            +(LEAF+BETWEENLEAF)*j+LEAF, pos.y()-(LEAF+BETWEENLEAF)*(leafamount-1)/2
                            +(LEAF+BETWEENLEAF)*i+LEAF),Vector2.of(LEAF,LEAF));
                    gameObjects.addGameObject(staticLeaf, LEAFLAYER);
                }
                if(rnd.nextDouble(1)>COINF){
                    fruit = new Fruit(Vector2.of(pos.x()-(LEAF+BETWEENLEAF)*(leafamount-1)/2
                            +(LEAF+BETWEENLEAF)*j+LEAF, pos.y()-(LEAF+BETWEENLEAF)*(leafamount-1)/2
                            +(LEAF+BETWEENLEAF)*i+LEAF + rnd.nextInt(FRUIT)),Vector2.of(FRUIT,FRUIT), gameObjects);
                    fruit.setTag("FRUIT");
                    gameObjects.addGameObject(fruit, Layer.DEFAULT);
                }
            }

        }
    }
}
