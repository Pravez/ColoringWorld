package com.color.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.color.game.elements.BaseElement;

/**
 * OrthographicalCamera utilities functions
 */
public class Camera {

    /**
     * Constant for camera moving
     */
    private static final int MOVING_AMOUNT = 10;

    /**
     * Method to handle the camera in order to make it follow the Character position
     * @param camera the OrthographicCamera to handle
     * @param levelWidth the width of the level
     * @param levelHeight the height of the level
     * @param position the position of the Character
     */
    public static void handleCamera(OrthographicCamera camera, float levelWidth, float levelHeight, Vector2 position) {
        camera.position.x = position.x;
        camera.position.y = (camera.viewportHeight < levelHeight) ? position.y + camera.viewportHeight/4f : camera.viewportHeight/4f;
        stabilizeCamera(camera, levelWidth);
        if (camera.position.y > levelHeight - camera.viewportHeight / 2f && camera.viewportHeight < levelHeight)
            camera.position.y = levelHeight - camera.viewportHeight / 2f;
        camera.update();
    }

    /**
     * Public static method to handle the Camera when it should move
     * @param referenceCamera the referenceCamera to take as a reference for its viewport
     * @param camera the OrthographicCamera which should move
     * @param levelWidth the width of the level
     * @param levelHeight the height of the level
     */
    public static void handleMovingCamera(OrthographicCamera referenceCamera, OrthographicCamera camera, float levelWidth, float levelHeight) {
        moveCamera(camera, (referenceCamera == camera) ? MOVING_AMOUNT : 1.0f * MOVING_AMOUNT/ BaseElement.WORLD_TO_SCREEN);
        stabilizeCamera(camera, levelWidth);
        if (camera.position.y > levelHeight - camera.viewportHeight / 2f)
            camera.position.y = (camera.viewportHeight > levelHeight) ? camera.viewportHeight/2 : levelHeight - camera.viewportHeight / 2f;
        camera.update();
    }

    /**
     * Private static method to stabilize the OrthographicCamera according to its viewportWidth and the width of the level
     * @param camera the OrthographicCamera to stabilize
     * @param levelWidth the width of the level
     */
    private static void stabilizeCamera(OrthographicCamera camera, float levelWidth) {
        if (camera.position.x < camera.viewportWidth / 2f)
            camera.position.x = camera.viewportWidth / 2f;

        if (camera.position.y < camera.viewportHeight / 2f)
            camera.position.y = camera.viewportHeight / 2f;

        if (camera.position.x > levelWidth - camera.viewportWidth / 2f)
            camera.position.x = levelWidth - camera.viewportWidth / 2f;
    }

    /**
     * Private static method to move the Camera according to the arrow keys pressed
     * @param movingCamera the OrthographicCamera which should change its position
     * @param movingAmount the moving amount the movingCamea should move
     */
    private static void moveCamera(OrthographicCamera movingCamera, float movingAmount) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) // going to the right
            movingCamera.position.x += movingAmount;
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) // going to the left
            movingCamera.position.x -= movingAmount;

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) // going to the bottom
            movingCamera.position.y -= movingAmount;
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) // going to the top
            movingCamera.position.y += movingAmount;
    }
}
