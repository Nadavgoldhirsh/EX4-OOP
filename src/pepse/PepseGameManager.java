package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import daynight.Night;
import daynight.Sun;
import daynight.SunHalo;
import world.*;
import world.trees.StaticTree;
import java.util.function.*;


import java.util.List;
import java.util.Random;

public class PepseGameManager extends GameManager {

    private static final int SEED = 0;
    private static final int CYCLE_LENGTH = 30;
    private static final int AVATAR_X_DIM = 50;
    private static final int AVATAR_Y_DIM = 80;
    private static final float START_ENERGY = 100;
    private static final int COUNTERLENGTH = 50;
    private static final int FACTOR = -3;
    private static final int ROOT = 5;
    private static final Vector2 ADJ = Vector2.of(5,80);
    private static Avatar avatar;
    private static Energy energy;
    private static StaticTree staticTree;
    private static final int TREEWIDTH = 70;
    private static final int TREEMAXHEIGHT= 300;
    private static final int TREEMINHEIGHT= 200;
    private static int treeHeight;


    public static void main(String[] args) {
        new PepseGameManager().run();

    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Random random = new Random(SEED);
        createSky(windowController);
        createTerrain(windowController, random);
        GameObject night =  Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night,Layer.BACKGROUND);
        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        gameObjects().addGameObject(sun,Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        sunHalo.addComponent((float deltaTime)-> sunHalo.setCenter(sun.getCenter()));
        gameObjects().addGameObject(sunHalo,Layer.BACKGROUND);
        avatar = new Avatar(Vector2.of(windowController.getWindowDimensions().x()/2-AVATAR_X_DIM,
                Terrain.groundHeightAt(windowController.getWindowDimensions().x()/2-AVATAR_X_DIM)-AVATAR_Y_DIM),
                inputListener, imageReader);
        gameObjects().addGameObject(avatar);
        energy = new Energy(windowController.getWindowDimensions().add(
                new Vector2(COUNTERLENGTH, COUNTERLENGTH).mult(FACTOR).add(ADJ)),
                new Vector2(COUNTERLENGTH, COUNTERLENGTH),
                new TextRenderable("" + START_ENERGY), avatar::getEnergy);
        gameObjects().addGameObject(energy);
        createFlora(gameObjects(),windowController,Terrain::groundHeightAt);


    }
    private void createFlora(GameObjectCollection gameObjects, WindowController windowController,
                             Function<Float, Float> func) {
        Flora flora = new Flora(gameObjects,func);
        flora.createInRange(TREEWIDTH, (int) windowController.getWindowDimensions().x() - TREEWIDTH);
    }

    private void createSky(WindowController windowController) {
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    private void createTerrain(WindowController windowController, Random random) {
        Terrain terrain = new Terrain(windowController.getWindowDimensions(), random.nextInt());
        List<Block> myBlockList = terrain.createInRange(0,(int) windowController.getWindowDimensions().x());
        for (Block curBlock: myBlockList){
            gameObjects().addGameObject(curBlock, Layer.STATIC_OBJECTS);
        }
    }


}