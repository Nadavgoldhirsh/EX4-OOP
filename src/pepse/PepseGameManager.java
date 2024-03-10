package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.*;
import pepse.world.trees.Flora;
import pepse.world.trees.Observer;
import pepse.world.trees.StaticTree;

import java.util.ArrayList;
import java.util.function.*;


import java.util.List;
import java.util.Random;

/**
 * This class is the game manager
 */

public class PepseGameManager extends GameManager {

    /**
     * Seed for the simulation
     */
    public static final int SEED = 7;
    private static final int CYCLE_LENGTH = 30;
    private static final int AVATAR_X_DIM = 50;
    private static final int AVATAR_Y_DIM = 80;
    private static final float START_ENERGY = 100;
    private static final int COUNTERLENGTH = 50;
    private static final int FACTOR = -3;
    private static final Vector2 ADJ = Vector2.of(5,80);
    private static final int TARGET_FRAMERATE = 50;
    private static final int DIVID_BY = 2;
    private static Avatar avatar;
    private static Energy energy;
    private static final int TREEWIDTH = 70;
    private final List<Observer> myObsList = new ArrayList<>();

    /**
     * This is the Main method of the program
     * @param args an input String list
     */
    public static void main(String[] args) {

        new PepseGameManager().run();
    }

    /**
     * This method creates all the game
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Random random = new Random(SEED);
        windowController.setTargetFramerate(TARGET_FRAMERATE);
        createSky(windowController);
        createTerrain(windowController, random);
        createNight(windowController);
        createSunAndHalo(windowController);
        createAvatar(imageReader, inputListener, windowController);
        createEnergy(windowController);
        createFlora(gameObjects(),windowController,Terrain::groundHeightAt);
    }

    private void createSunAndHalo(WindowController windowController) {
        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        gameObjects().addGameObject(sun,Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        sunHalo.addComponent((float deltaTime)-> sunHalo.setCenter(sun.getCenter()));
        gameObjects().addGameObject(sunHalo,Layer.BACKGROUND);
    }

    private void createNight(WindowController windowController) {
        GameObject night =  Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night,Layer.BACKGROUND);
    }

    /**
     * This method updates all the game objects and also notifies the leafs, trees and fruits to change
     * when the player jumps
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (Avatar.getJumpValue()){
            for(Observer obs: myObsList){
                obs.changeBecauseOfJump();
            }
        }

    }

    private void createEnergy(WindowController windowController) {
        energy = new Energy(windowController.getWindowDimensions().add(
                new Vector2(COUNTERLENGTH, COUNTERLENGTH).mult(FACTOR).add(ADJ)),
                new Vector2(COUNTERLENGTH, COUNTERLENGTH),
                new TextRenderable("" + START_ENERGY), avatar::getEnergy);
        gameObjects().addGameObject(energy);
    }

    private void createAvatar(ImageReader imageReader, UserInputListener inputListener, WindowController windowController) {
        avatar = new Avatar(Vector2.of(windowController.getWindowDimensions().x()/DIVID_BY-AVATAR_X_DIM,
                Terrain.groundHeightAt(windowController.getWindowDimensions().x()/ DIVID_BY -AVATAR_X_DIM)-AVATAR_Y_DIM),
                inputListener, imageReader, gameObjects());
        gameObjects().addGameObject(avatar);
    }

    private void createFlora(GameObjectCollection gameObjects, WindowController windowController,
                             Function<Float, Float> func) {
        Flora flora = new Flora(gameObjects,myObsList,func,windowController.getWindowDimensions().x());
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