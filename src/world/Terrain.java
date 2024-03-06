package world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import util.ColorSupplier;
import util.GroundValue;
import util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class builds the entire Terrain of the simulation form the Blocks
 */
public class Terrain {
    private static final int BLOCK_SIZE_MULT = 7;
    private static final String GROUND = "ground";
    private static int groundHeightAtX0 = 0;
    private final static float INIT_HEIGHT_MULT = (float) 2/3;
    private static  int seed = 0;
    private static final Color BASE_GROUND_COLOR =new Color(212, 123,74);
    private static final int TERRAIN_DEPTH = 20;


    /**
     * Class Ctor
     * @param windowDimensions the dims of the window
     * @param seed a randomly selected integer
     */
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = GroundValue.GetInitialGroundValue(windowDimensions);
        Terrain.seed = seed;
    }

    /**
     * This method returns the height of the ground at a given x coordinate
     * @param x a given x coordinate
     * @return the height of the ground at x coordinate
     */
    public static float groundHeightAt(float x){
        NoiseGenerator noiseGenerator = new NoiseGenerator(seed, groundHeightAtX0);
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * BLOCK_SIZE_MULT);
        return groundHeightAtX0 + noise;
    }

    /**
     * This method creates the ground in the given range
     * @param minX the minimum X coords to start building the ground from
     * @param maxX the maximum X coords to finish building the ground from (this func can surpass the max and
     *             build a bit more due to integer dividing)
     * @return a list of Blocks that creates the ground
     */
    public List<Block> createInRange(int minX, int maxX){
         RectangleRenderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(
                BASE_GROUND_COLOR));
         List<Block> retBlockList = new LinkedList<Block>();
         Block curBlock ;
         int blockAmountInRow = (int) Math.ceil((double) (maxX - minX) / Block.SIZE);
         for (int i = 0; i < blockAmountInRow; i++) {
             int curXCoor = (minX+ i*Block.SIZE);
             int curYCoor;
             float curGroundHeight = groundHeightAt(curXCoor);
             for (int j = 0; j < TERRAIN_DEPTH; j++) {
                 curYCoor = (int) Math.floor(curGroundHeight / Block.SIZE) * Block.SIZE;
                 curYCoor = curYCoor+ j*Block.SIZE;
                 curBlock = new Block(new Vector2( curXCoor, curYCoor), renderable);
                 curBlock.setTag(GROUND);
                 retBlockList.add(curBlock);
             }
        }
         return retBlockList;
    }
}
