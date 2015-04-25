package com.color.game.utils;


import com.badlogic.gdx.math.Vector2;

import java.awt.*;

/**
 * Class of constants used by the whole program
 */
public class Constants {

    private static Constants constants = new Constants();

    private Constants() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        APP_WIDTH = (int) screenSize.getWidth();
        APP_HEIGHT = (int) screenSize.getHeight();

        DIALOG_WIDTH = 2*APP_WIDTH/3;
        DIALOG_HEIGHT = 2*APP_HEIGHT/3;
        DIALOG_POS_X = APP_WIDTH/2 - DIALOG_WIDTH/2;
        DIALOG_POS_Y = APP_HEIGHT/2 - DIALOG_HEIGHT/2;
    }

    public static int APP_WIDTH;// = 1200;//840;
    public static int APP_HEIGHT;// = 900;//600;

    public static final int GLOBAL_STARTING_SPRITES_WIDTH = APP_WIDTH/2;
    public static final int GLOBAL_STARTING_SPRITES_HEIGHT = APP_HEIGHT/2;

    public static final float WORLD_TO_SCREEN = 2;

    public static final int VIEWPORT_WIDTH = 100;
    public static final int VIEWPORT_HEIGHT = 100;

    public static final float CHARACTER_WIDTH = 2f;
    public static final float CHARACTER_HEIGHT = 2f;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -75);

    public static final float STATIC_ELEMENTS_DENSITY = 0f;
    public static float DYNAMIC_ELEMENTS_DENSITY = 0.1f;

    /**
     * Character constants
     */
    public static final float CHARACTER_GRAVITY_SCALE = 3f;
    public static final Vector2 CHARACTER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 125);
    public static final Vector2 CHARACTER_MOVING_LINEAR_IMPULSE = new Vector2(7,0);
    public static final Vector2 CHARACTER_MAX_VELOCITY_NORMAL = new Vector2(25f, 0);

    public static final float CHARACTER_GRAVITY_SCALE_ON_WALL = 1.5f;
    public static final Vector2 CHARACTER_JUMPING_LINEAR_IMPULSE_BOOSTED = new Vector2(0, 160);
    public static final Vector2 CHARACTER_MOVING_LINEAR_IMPULSE_BOOSTED = new Vector2(10,0);
    public static final Vector2 CHARACTER_MAX_VELOCITY_BOOSTED = new Vector2(35f, 0);

    public static Vector2 CHARACTER_MAX_VELOCITY = new Vector2(25f, 0);

    public static final float CHARACTER_CHANGING_COLOR_DELAY = 5.0f;

    public static final int CHARACTER_FRAME_ROWS = 4;
    public static final int CHARACTER_FRAME_COLS = 4;

    /**
     * DialogBox bounds
     */
    public static final float DIALOG_BORDER_GAP = 15;
    public static float DIALOG_WIDTH;
    public static float DIALOG_HEIGHT;
    public static float DIALOG_POS_X;
    public static float DIALOG_POS_Y;
}
