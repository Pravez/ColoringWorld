package com.color.game.graphics;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.enemies.Enemy;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.platforms.ColorPlatform;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.elements.staticelements.sensors.Teleporter;
import com.color.game.elements.staticelements.sensors.WindBlower;
import com.color.game.elements.staticelements.sensors.WindDirection;
import com.color.game.levels.Level;
import com.color.game.levels.LevelManager;
import com.color.game.screens.GameScreen;

import java.util.HashMap;
import java.util.Random;

/**
 * The class which handles every light effects
 */
public class LightManager {

    /**
     * Light constants
     */
    private static final float COLOR_LIGHT_WIDTH = 1.6f;
    private static final float ENEMY_LIGHT_WIDTH = 6f;
    // Ambient light animation

    private static final float AMBIENT_CHANGE_DELAY = 3.0f;
    // Color light animation
    private static final float COLOR_LIGHT_ACTIVATION_DELAY = 0.2f;
    private static final float COLOR_LIGHT_DEACTIVATION_DELAY = 0.5f;
    // Character
    private static final float CHARACTER_LIGHT_WIDTH = 20f;
    // Exit
    private static final float EXIT_LIGHT_WIDTH = 10f;
    private static final float EXIT_LIGHT_MIN = 5f;
    // Windblower
    private static final float BLOWER_LIGHT_INTENSITY = 2.0f;

    private HashMap<ColorPlatform, Array<PointLight>> colorLights;

    private Color minAmbientLight;
    private Color maxAmbientLight;

    /**
     * Light tools
     */
    public RayHandler rayHandler;

    private PointLight characterLight;
    private HashMap<Exit, PointLight> exitLights;
    private HashMap<Enemy, PointLight> enemyLights;
    private HashMap<WindBlower, Array<PointLight>> windLights;

    private static Color ambientLight;
    private static Color ambientTarget;

    private static OrthographicCamera lightCamera;

    private float timePassed = 0;

    public LightManager(Level level) {

        minAmbientLight = randomizeBackgroundColor(0.2f);
        maxAmbientLight = randomizeBackgroundColor(0.45f);

        this.colorLights = new HashMap<>();
        this.exitLights  = new HashMap<>();
        this.enemyLights = new HashMap<>();
        this.windLights  = new HashMap<>();
        this.rayHandler  = new RayHandler(level.getWorld());
        this.rayHandler.setAmbientLight(minAmbientLight);
        this.characterLight = new PointLight(this.rayHandler, 50, Color.WHITE, CHARACTER_LIGHT_WIDTH, 5, 5);

        ambientLight = new Color(minAmbientLight);
        ambientTarget = maxAmbientLight;
    }

    public void dispose() {
        this.rayHandler.dispose();
    }

    public static void init() {

        lightCamera = new OrthographicCamera();
        lightCamera.setToOrtho(false, 1.0f * Gdx.graphics.getWidth() / BaseElement.WORLD_TO_SCREEN, 1.0f * Gdx.graphics.getHeight() / BaseElement.WORLD_TO_SCREEN);
        PointLight.setContactFilter(PhysicComponent.CATEGORY_SCENERY, (short) 0, PhysicComponent.MASK_SCENERY);
    }

    public Color randomizeBackgroundColor(float alpha){
        Random r = new Random();
        return new Color(r.nextFloat() * (0.5f - 0.2f) + 0.1f,r.nextFloat() * (0.7f - 0.3f) + 0.1f,r.nextFloat() * (0.7f - 0.3f) + 0.1f, alpha);
    }

    public void makeItBloody(){
        minAmbientLight.r += 0.025f;
        maxAmbientLight.r += 0.025f;
    }

    /**
     * Method to handle the lights : move the light Camera and set the character light to its position then render all the lights
     */
    public void render(float delta) {
        this.timePassed += delta;
        /** AMBIENT LIGHT LERP **/
        if (this.timePassed > AMBIENT_CHANGE_DELAY) {
            this.timePassed = 0;
            ambientTarget = (ambientTarget == minAmbientLight) ? maxAmbientLight : minAmbientLight;
        }
        ambientLight.lerp(ambientTarget, delta/AMBIENT_CHANGE_DELAY);
        this.rayHandler.setAmbientLight(ambientLight);

        if (GameScreen.isPaused())
            Camera.handleMovingCamera(GameScreen.camera, lightCamera, LevelManager.getCurrentLevel().map.getWidth(), LevelManager.getCurrentLevel().map.getHeight());
        else
            Camera.handleCamera(lightCamera, LevelManager.getCurrentLevel().map.getWidth(), LevelManager.getCurrentLevel().map.getHeight(), GameScreen.character.getPosition());

        this.rayHandler.setCombinedMatrix(lightCamera.combined);
        // Make the light follow the Character
        this.characterLight.setPosition(GameScreen.character.getPosition());
        this.rayHandler.updateAndRender();
    }

    public void renderColorPlatformLights(ColorPlatform platform, float delta) {
        // Set active of inactive the PointLights
        for (PointLight light : this.colorLights.get(platform)) {
            if (platform.isActivated() && light.getDistance() < COLOR_LIGHT_WIDTH) {
                float width = light.getDistance() + (COLOR_LIGHT_WIDTH )/(COLOR_LIGHT_ACTIVATION_DELAY/delta);
                if (width > COLOR_LIGHT_WIDTH)
                    width = COLOR_LIGHT_WIDTH;
                light.setDistance(width);

            } else if (!platform.isActivated() && light.getDistance() > 0) {
                float width = light.getDistance() - (COLOR_LIGHT_WIDTH)/(COLOR_LIGHT_DEACTIVATION_DELAY/delta);
                if (width < 0)
                    width = 0;
                light.setDistance(width);
            }
        }
    }

    public void renderExitLights(Exit exit) {
        if (GameScreen.isExitReached())
            this.exitLights.get(exit).setDistance(this.exitLights.get(exit).getDistance() + (Gdx.graphics.getDeltaTime() / GameScreen.WIN_DELAY) * EXIT_LIGHT_WIDTH);
        else if (this.exitLights.get(exit).getDistance() != EXIT_LIGHT_MIN)
            this.exitLights.get(exit).setDistance(EXIT_LIGHT_MIN);
    }

    public void renderWindBlowerLight(WindBlower blower) {
        for (PointLight light : this.windLights.get(blower)) {

            WindDirection direction = blower.getDirection();

            Vector2 changing = direction.toCoordinates().scl(light.getPosition());
            changing.x = Math.abs(changing.x);
            changing.y = Math.abs(changing.y);
            Vector2 maxValue = direction.adaptBlowerMaxValue(blower.getWorldBounds().width/2, blower.getWorldBounds().height/2, blower.getPosition());
            float moving = ((new Random().nextInt(21) - 10) / 18.0f);
            direction.addValue(changing, 0.5f);
            if(changing.x == 0){
                changing.x += light.getX() + moving;
            }else{
                changing.y += light.getY() + moving;
            }
            float distance = direction.calculatePercentage(changing, maxValue, blower.getPosition());

            //Modifying position
            light.setPosition(changing);

            if (direction.isReached(changing, maxValue)) {
                light.setActive(false);
                this.windLights.get(blower).removeValue(light, true);
            }else if (distance > 50){
                //Calculating the percentage of distance between 0.5 and 1.0
                // distance = 0.75 will give 0.5
                float value = (distance*50)/100;
                //Then calculate the percentage according to the blower light intensity with the value
                light.setDistance(((100-value)*BLOWER_LIGHT_INTENSITY)/100);
            }
        }
    }

    /**
     * Method to generate a point of wind from a given blower
     * @param bounds bounds of the blower
     * @param blower the blower
     */
    public void generateWind(Rectangle bounds, WindBlower blower){
        Random r = new Random();
        Vector2 position = blower.getDirection().getBase(bounds, true);

        this.windLights.get(blower).add(new PointLight(this.rayHandler, 4, Color.WHITE, BLOWER_LIGHT_INTENSITY, position.x, position.y));
    }

    public void renderEnemyLight(Enemy enemy) {
        PointLight light = this.enemyLights.get(enemy);
        if (enemy.isAlive() && !light.isActive())
            light.setActive(true);
        else if (!enemy.isAlive() && light.isActive())
            light.setActive(false);

        if (enemy.isAlive())
            light.setPosition(enemy.getPosition());
    }

    public void renderCharacterLight(boolean killed) {
        if (killed)
            this.characterLight.setDistance(this.characterLight.getDistance() - (Gdx.graphics.getDeltaTime()/GameScreen.DEATH_DELAY) * (CHARACTER_LIGHT_WIDTH - 2f));
        else
            this.characterLight.setDistance(CHARACTER_LIGHT_WIDTH);
    }

    public void addColorPlatformLights(ColorPlatform platform) {
        Array<PointLight> lights = new Array<>();
        Rectangle bounds = platform.getWorldBounds();

        for (int i = 0 ; i < bounds.height ; i++)
            for (int j = 0 ; j < bounds.width ; j++)
                lights.add(new PointLight(this.rayHandler, 5, platform.getElementColor().getColor(), platform.isActivated() ? COLOR_LIGHT_WIDTH : 0, bounds.x + 0.5f + j, bounds.y + 0.5f + i));

        this.colorLights.put(platform, lights);
    }

    public void addWindBlowerLights(WindDirection direction, WindBlower blower) {
        Rectangle bounds = blower.getWorldBounds();
        float size = 4;
        if (direction == WindDirection.NORTH) {
            drawLightEllipse(new Rectangle(bounds.x, bounds.y, bounds.width, size), Color.WHITE);
        } else if (direction == WindDirection.SOUTH) {
            drawLightEllipse(new Rectangle(bounds.x, bounds.y + bounds.height - size, bounds.width, size), Color.WHITE);
        } else if (direction == WindDirection.EAST) {
            drawLightEllipse(new Rectangle(bounds.x, bounds.y, size, bounds.height), Color.WHITE);
        } else if (direction == WindDirection.WEST) {
            drawLightEllipse(new Rectangle(bounds.x + bounds.width - size, bounds.y, size, bounds.height), Color.WHITE);
        }

        this.windLights.put(blower, new Array<PointLight>());
    }

    public void addEnemyLights(ElementColor color, Enemy enemy) {
        Vector2 position = enemy.getPosition();
        this.enemyLights.put(enemy, new PointLight(this.rayHandler, 5, color.getColor(), ENEMY_LIGHT_WIDTH, position.x, position.y));
    }

    public void addExitLights(Exit exit) {
        Vector2 position = exit.getPosition();
        this.exitLights.put(exit, new PointLight(this.rayHandler, 5, Color.CYAN, EXIT_LIGHT_MIN, position.x, position.y));
    }

    public void addTeleporterLights(Teleporter teleporter) {
        drawLightEllipse(teleporter.getWorldBounds(), Color.CYAN);
        Vector2 position = teleporter.getTeleportPosition();
        drawLightEllipse(new Rectangle(position.x - 1.6f, position.y, 3.2f, 4.8f), Color.ORANGE);
    }

    private void drawLightEllipse(Rectangle bounds, Color color) {
        int segments = 30;
        float angle = 2 * MathUtils.PI / segments;

        float cx = bounds.x + bounds.width / 2, cy = bounds.y + bounds.height / 2;
        for (int i = 0; i < segments; i++) {
            float posX = cx + (bounds.width * 0.5f * MathUtils.cos(i * angle));
            float posY = cy + (bounds.height * 0.5f * MathUtils.sin(i * angle));
            new PointLight(this.rayHandler, 5, color, 1.6f, posX, posY);
        }
    }
}
