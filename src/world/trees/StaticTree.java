package world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import util.ColorSupplier;
import world.Observer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.awt.*;

public class StaticTree extends GameObject implements Observer {
    /**
     * This  string represents the fruit tag
     */
    private static final int INT_BOUND = 2;
    private static final int DIVIDE_AMOUNT = 2;
    private  StaticLeaf staticLeaf;
    private  Fruit fruit;
    private static final int LEAF = 20;
    private static final int FRUIT = 13;
    private static final int BETWEENLEAF = 3;
    private static final int MINLEAF = 10;
    private static int leafamount= 11;
    private static final double COIN = 0.09;
    private static final double COINF = 0.9;
    private static final Color BASE_TREE_COLOR = new Color(100, 50, 20);
    private final List<GameObject> treeObjects= new LinkedList<>();
    private final List<Observer> treeObservers= new LinkedList<>();


    Random rnd = new Random();


    public StaticTree( Vector2 pos, Vector2 dim){
        super(pos, dim, new RectangleRenderable(ColorSupplier.approximateColor(
                BASE_TREE_COLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        //this line says that this object can't be skipped by another object in the same layer
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        // this line says that this obj doesn't move and if there is a collision it stays put
        leafamount = rnd.nextInt(INT_BOUND)+MINLEAF;
        for (int i = 0; i<leafamount; i++){
            for (int j = 0; j<leafamount; j++){
                if(rnd.nextDouble()>COIN){
                    createLeaf(pos, j, i);
                }
                if(rnd.nextDouble()>COINF){
                    createFruit(pos, j, i);
                }
            }
        }
    }

    private void createFruit(Vector2 pos, int j, int i) {
        fruit = new Fruit(Vector2.of(pos.x()-
                (float) ((LEAF + BETWEENLEAF) * (leafamount - 1)) /DIVIDE_AMOUNT
                +(LEAF+BETWEENLEAF)* j +LEAF, pos.y()-
                (float) ((LEAF + BETWEENLEAF) * (leafamount - 1)) /DIVIDE_AMOUNT
                +(LEAF+BETWEENLEAF)* i +LEAF + rnd.nextInt(FRUIT)),Vector2.of(FRUIT,FRUIT));
        fruit.setTag(Fruit.FRUIT_TAG);
        treeObjects.add(fruit);
        treeObservers.add(fruit);
    }

    private void createLeaf(Vector2 pos, int j, int i) {
        staticLeaf = new StaticLeaf(Vector2.of(pos.x()-
                (float) ((LEAF + BETWEENLEAF) * (leafamount - 1)) / DIVIDE_AMOUNT
                +(LEAF+BETWEENLEAF)* j +LEAF, pos.y()-
                (float) ((LEAF + BETWEENLEAF) * (leafamount - 1)) /DIVIDE_AMOUNT
                +(LEAF+BETWEENLEAF)* i +LEAF),Vector2.of(LEAF,LEAF));
        treeObjects.add(staticLeaf);
        treeObservers.add(staticLeaf);
    }

    /**
     * This method returns the leafs and fruits by a list of gameObjects
     * @return returns the leafs and fruits by a list of gameObjects
     */
    public List<GameObject> getTreeLeafsAndFruitsList(){
        return treeObjects;
    }

    /**
     * This method returns the leafs and fruits by a list of Observers
     * @return returns the leafs and fruits by a list of gameObjects
     */
    public List<Observer> getTreeObservers(){
        return treeObservers;
    }

    /**
     * This is the change that happens when the avatar jumps
     * the Tree changes color to different randomly selected brown
     */
    @Override
    public void changeBecauseOfJump() {
        this.renderer().setRenderable(new RectangleRenderable(ColorSupplier.approximateColor(
                BASE_TREE_COLOR)));
    }
}
