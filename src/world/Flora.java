package world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Vector2;
import world.trees.Fruit;
import world.trees.StaticTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.Arrays;


public class Flora {
    private static final int TREEWIDTH = 70;
    private static final int TREEMAXHEIGHT= 400;
    private static final int TREEMINHEIGHT= 200;
    private static final int TREEAMOUNT= 4;
    private static final int DIM_DVIDER = 2;

    private static final int ROOT = 4;
    private static final int AVATARSPACE = 66;
    private static final int LEAF_LAYER = -50;
    private static StaticTree staticTree;
    private final List<Observer> myObsList;
    private static Function<Float, Float> gh;
    private final GameObjectCollection gameObjects;
    private final float dim;


    public Flora(GameObjectCollection gameObjects, List<Observer> myObsList, Function<Float, Float> func, float dim){
        this.myObsList = myObsList;
        gh = func;
        this.gameObjects = gameObjects;
        this.dim = dim;
    }

    public List<StaticTree> createInRange(int minX, int maxX) {
        List<StaticTree> retList = new LinkedList<StaticTree>();
        Random rnd = new Random();
        boolean flag = false;
        int[] arr = new int[TREEAMOUNT];
        // maybe coin flip?
        while (!flag){
            for (int i = 0; i < TREEAMOUNT; i++) {
                arr[i] = rnd.nextInt(maxX-minX) + minX;
            }
            Arrays.sort(arr);
            flag = true;
            if ((arr[0] > (dim/DIM_DVIDER - AVATARSPACE)) &&
                    (arr[0] < (dim/DIM_DVIDER + AVATARSPACE))) {
                flag = false;
            }
            for (int i = 0; i< arr.length - 1; i++){
                if (arr[i + 1] - arr[i] <= TREEWIDTH) {
                    flag = false;
                    break;
                }
                if ((arr[i+1] > (dim/ DIM_DVIDER - AVATARSPACE)) &&
                        (arr[i+1] < (dim/DIM_DVIDER + AVATARSPACE))) {
                    flag = false;
                    break;
                }
            }
        }
        addToRetList(rnd, arr, retList);
        return retList;
    }

    private void addToRetList(Random rnd, int[] arr, List<StaticTree> retList) {
        for (int i = 0; i < TREEAMOUNT; i++) {
            int treeHeight = rnd.nextInt(TREEMAXHEIGHT - TREEMINHEIGHT) + TREEMINHEIGHT;
            float treePos = arr[i];
            staticTree = new StaticTree(Vector2.of(treePos,
                    gh.apply(treePos) - treeHeight),
                    Vector2.of(TREEWIDTH, treeHeight));
            retList.add(staticTree);
            gameObjects.addGameObject(staticTree, Layer.STATIC_OBJECTS);
            addTreeFruitsAndLeafsToGameCollection();
            myObsList.addAll(staticTree.getTreeObservers());
        }
    }

    private void addTreeFruitsAndLeafsToGameCollection() {
        for(GameObject obj : staticTree.getTreeLeafsAndFruitsList()){
            if(obj.getTag().equals(Fruit.FRUIT_TAG)){
                gameObjects.addGameObject(obj,Layer.DEFAULT);
            }
            else { // it's a leaf
                gameObjects.addGameObject(obj, LEAF_LAYER);
            }
        }
    }

}
