package world;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Vector2;
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

    private static int treeHeight;
    private static float treePos;

    private static final int ROOT = 4;
    private static final int AVATARSPACE = 66;
    private static StaticTree staticTree;
    private static Function<Float, Float> gh;
    private GameObjectCollection gameObjects;
    private static float dim;



    public Flora(GameObjectCollection gameObjects, Function<Float, Float> func, float dim){
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
            if ((arr[0] > (dim/2 - AVATARSPACE)) && (arr[0] < (dim/2 + AVATARSPACE))) {
                flag = false;
            }
            for (int i = 0; i< arr.length - 1; i++){
                if (arr[i + 1] - arr[i] <= TREEWIDTH) {
                    flag = false;
                    break;
                }
                if ((arr[i+1] > (dim/2 - AVATARSPACE)) && (arr[i+1] < (dim/2 + AVATARSPACE))) {
                    flag = false;
                    break;
                }

            }
        }
        for (int i = 0; i < TREEAMOUNT; i++) {
            treeHeight = rnd.nextInt(TREEMAXHEIGHT - TREEMINHEIGHT) + TREEMINHEIGHT;
            treePos = arr[i];
            staticTree = new StaticTree(gameObjects,Vector2.of(treePos,
                    gh.apply(treePos) - treeHeight),
                    Vector2.of(TREEWIDTH,treeHeight));
            retList.add(staticTree);
            gameObjects.addGameObject(staticTree, Layer.STATIC_OBJECTS);
        }
        return retList;
    }

}
