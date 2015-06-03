package com.color.game.graphics;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.color.game.assets.Assets;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.enemies.Enemy;
import com.color.game.elements.dynamicplatforms.FallingPlatform;
import com.color.game.elements.dynamicplatforms.MovingPlatform;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.Lever;
import com.color.game.elements.staticelements.platforms.*;
import com.color.game.elements.staticelements.sensors.*;
import com.color.game.levels.Level;
import com.color.game.levels.LevelManager;
import com.color.game.screens.GameScreen;

import java.util.HashMap;

import static com.color.game.elements.BaseElement.WORLD_TO_SCREEN;

public class GraphicManager {

    /**
     * Constants for camera moving
     */
    private static final int MOVING_GAP = 4;
    private static final int MOVING_AMOUNT = 10;

    private static final float OPACITY = 0.4f;

    /**
     * Light constants
     */
    private static final float COLOR_LIGHT_WIDTH = 1.6f;
    private static final float ENEMY_LIGHT_WIDTH = 6f;
    private static final Color MIN_AMBIENT_LIGHT = new Color(1, 1, 1, 0.2f);
    private static final Color MAX_AMBIENT_LIGHT = new Color(1, 1, 1, 0.4f);
    private static final float AMBIENT_CHANGE_DELAY = 3.0f;

    /**
     * Notice constants
     */
    private static final int FONT_SIZE = 18;//14;
    private static final int TEXT_WIDTH = 260;//200;
    private static final int TEXT_GAP = 20;
    private static final float NOTICE_DELAY = 0.15f;

    /**
     * HashMap of the Level elements to render
     */
    private HashMap<Class<?>, Array<?>> elements;
    private HashMap<ElementColor, Array<ColorPlatform>> colorPlatforms;
    private HashMap<ElementColor, Array<Enemy>> enemies;
    private HashMap<WindDirection, Array<WindBlower>> windBlowers;

    private HashMap<ColorPlatform, Array<PointLight>> colorLights;

    /**
     * Drawing tools
     */
    private static SpriteBatch   batch;
    private static ShapeRenderer renderer;

    /**
     * Light tools
     */
    public RayHandler  rayHandler;

    private PointLight characterLight;
    private HashMap<Exit, PointLight> exitLights;
    private HashMap<Enemy, PointLight> enemyLights;
    private HashMap<WindBlower, Array<PointLight>> windLights;

    private static OrthographicCamera lightCamera;

    private static Color ambientLight;
    private static Color ambientTarget;

    private float timePassed = 0;

    /**
     * Textures, sprites and Animations for the elements
     */
    private static TextureRegion    leverRegions[];
    private static TextureRegion    deadlyRegions[][];
    private static Texture          colorPlatformTexture;
    private static TextureAnimation noticeAnimation;
    private static BitmapFontCache  fontCache;
    private static Sprite           windSprite;
    private static Texture          background;

    public GraphicManager(Level level) {
        this.elements       = new HashMap<>();
        this.colorPlatforms = new HashMap<>();
        this.enemies        = new HashMap<>();
        this.windBlowers    = new HashMap<>();

        this.colorLights = new HashMap<>();
        this.exitLights  = new HashMap<>();
        this.enemyLights = new HashMap<>();
        this.windLights  = new HashMap<>();
        this.rayHandler  = new RayHandler(level.getWorld());
        this.rayHandler.setAmbientLight(MIN_AMBIENT_LIGHT);
        this.characterLight = new PointLight(this.rayHandler, 50, Color.WHITE, 12, 5, 5);
    }

    public static void init() {
        ambientLight = new Color(MIN_AMBIENT_LIGHT);
        ambientTarget = MAX_AMBIENT_LIGHT;

        batch    = new SpriteBatch();
        renderer = new ShapeRenderer();

        lightCamera = new OrthographicCamera();
        lightCamera.setToOrtho(false, 1.0f * Gdx.graphics.getWidth() / BaseElement.WORLD_TO_SCREEN, 1.0f * Gdx.graphics.getHeight() / BaseElement.WORLD_TO_SCREEN);
        PointLight.setContactFilter(PhysicComponent.CATEGORY_SCENERY, (short) 0, PhysicComponent.MASK_SCENERY);

        Texture leverTexture = Assets.getTexture(Lever.class);
        leverRegions    = new TextureRegion[2];
        leverRegions[0] = new TextureRegion(leverTexture, 0, 0, leverTexture.getWidth()/2, leverTexture.getHeight());
        leverRegions[1] = new TextureRegion(leverTexture, leverTexture.getWidth()/2, 0, leverTexture.getWidth()/2, leverTexture.getHeight());

        deadlyRegions = TextureRegion.split(Assets.getTexture(DeadlyPlatform.class), 32, 32);

        colorPlatformTexture = Assets.getTexture(ColorPlatform.class);

        noticeAnimation = new TextureAnimation(Assets.getTexture(Notice.class), 2, 2, NOTICE_DELAY);
        fontCache       = new BitmapFontCache(Assets.getGroboldFont(FONT_SIZE));

        windSprite      = new Sprite(Assets.getTexture(WindBlower.class));

        background      = Assets.manager.get("sprites/back.png", Texture.class);
    }

    /**
     * Method to draw the Level
     */
    public void draw() {
        float delta = GameScreen.isRunning() ? Gdx.graphics.getDeltaTime() : 0;
        this.timePassed += delta;

        handleLights(delta);

        /**
         * Draw the Moving Platforms path
         */
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(GameScreen.camera.combined);
        drawMovingPlatformsPath();
        renderer.end();

        /**
         * Draw the Tiled Map
         */
        LevelManager.getCurrentLevel().showMapRenderer();

        /**
         * Draw all the textures and animations of the Level's elements
         */
        batch.begin();
        batch.setProjectionMatrix(GameScreen.camera.combined);

        drawColorPlatforms();
        drawMovingPlatforms();
        drawDeadlyPlatforms();
        Color color = new Color(batch.getColor());
        batch.setColor(color.r, color.g, color.b, OPACITY);
        drawFallingPlatforms();

        drawWindBlowers();
        //drawTeleporters();
        drawLevers();
        batch.setColor(color);
        drawExits();
        drawNotices(delta);

        drawEnemies();
        batch.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        Rectangle bounds = GameScreen.character.getBounds();
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        renderer.end();
    }

    /**
     * Method to handle the lights : move the light Camera and set the character light to its position then render all the lights
     */
    private void handleLights(float delta) {
        /** AMBIENT LIGHT LERP **/
        if (this.timePassed > AMBIENT_CHANGE_DELAY) {
            this.timePassed = 0;
            ambientTarget = (ambientTarget == MIN_AMBIENT_LIGHT) ? MAX_AMBIENT_LIGHT : MIN_AMBIENT_LIGHT;
        }
        ambientLight.lerp(ambientTarget, delta/AMBIENT_CHANGE_DELAY);
        this.rayHandler.setAmbientLight(ambientLight);

        if (GameScreen.isRunning())
            handleCamera(lightCamera, LevelManager.getCurrentLevel().map.getWidth(), LevelManager.getCurrentLevel().map.getHeight(), GameScreen.character.getPosition());
        else
            handleMovingCamera(GameScreen.camera, lightCamera, LevelManager.getCurrentLevel().map.getWidth(), LevelManager.getCurrentLevel().map.getHeight());

        this.rayHandler.setCombinedMatrix(lightCamera.combined);
        // Make the light follow the Character
        this.characterLight.setPosition(GameScreen.character.getPosition());
        this.rayHandler.updateAndRender();
    }

    /**
     * Private method to render all the ColorPlatforms
     */
    private void drawColorPlatforms() {
        Color color = batch.getColor();

        for (ElementColor elementColor : ElementColor.values())
            drawColorPlatforms(elementColor);

        batch.setColor(color);
    }

    /**
     * Method to draw all ColorPlatforms of a certain color whith testing before if the level contains ColorPlatforms of this color
     * @param elementColor the ElementColor of the ColorPlatforms to render
     */
    private void drawColorPlatforms(ElementColor elementColor) {
        if (this.colorPlatforms.containsKey(elementColor)) {
            Color color = elementColor.getColor();
            batch.setColor(color.r, color.g, color.b, OPACITY);
            drawColorPlatforms(this.colorPlatforms.get(elementColor));
        }
    }

    /**
     * Method to draw all the ColorPlatforms specified
     * @param colorPlatforms all the ColorPlatforms to draw
     */
    private void drawColorPlatforms(Array<ColorPlatform> colorPlatforms) {
        for (ColorPlatform platform : colorPlatforms) {
            // Set active of inactive the PointLights
            for (PointLight light : this.colorLights.get(platform))
                light.setActive(platform.isActivated());

            Rectangle bounds = platform.getBounds();
            batch.draw(colorPlatformTexture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    /**
     * Private method to draw the Deadly Platforms
     */
    private void drawDeadlyPlatforms() {
        if (!this.elements.containsKey(DeadlyPlatform.class))
            return;
        for (DeadlyPlatform platform : (Array<DeadlyPlatform>)this.elements.get(DeadlyPlatform.class)) {
            float width  = platform.getPhysicComponent().getUserData().getWidth();
            float height = platform.getPhysicComponent().getUserData().getHeight();
            Rectangle bounds = platform.getBounds();
            if (bounds.width > bounds.height) {
                batch.draw(deadlyRegions[0][0], bounds.x, bounds.y, WORLD_TO_SCREEN, bounds.height);
                for (int i = 1 ; i < width - 1 ; i ++)
                    batch.draw(deadlyRegions[0][1], bounds.x + WORLD_TO_SCREEN * i, bounds.y, WORLD_TO_SCREEN, bounds.height);
                batch.draw(deadlyRegions[0][2], bounds.x + WORLD_TO_SCREEN * (width - 1), bounds.y, WORLD_TO_SCREEN, bounds.height);
            } else {
                batch.draw(deadlyRegions[1][0], bounds.x, bounds.y, bounds.width, WORLD_TO_SCREEN);
                for (int i = 1 ; i < height - 1 ; i ++)
                    batch.draw(deadlyRegions[1][1], bounds.x, bounds.y + WORLD_TO_SCREEN * i, bounds.width, WORLD_TO_SCREEN);
                batch.draw(deadlyRegions[1][2], bounds.x, bounds.y + WORLD_TO_SCREEN * (height - 1), bounds.width, WORLD_TO_SCREEN);
            }
        }
    }

    /**
     * Private method to draw the Falling Platforms
     */
    private void drawFallingPlatforms() {
        if (!this.elements.containsKey(FallingPlatform.class))
            return;
        Texture texture = Assets.getTexture(FallingPlatform.class);
        for (FallingPlatform platform : (Array<FallingPlatform>)this.elements.get(FallingPlatform.class)) {
            Rectangle bounds = platform.getBounds();
            Color color = batch.getColor();
            if (platform.isTransparent())
                batch.setColor(color.r, color.g, color.b, 0.5f);
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            if (platform.isTransparent())
                batch.setColor(color);
        }
    }

    /**
     * Private method to draw the Moving Platforms Path
     */
    private void drawMovingPlatformsPath() {
        if (!this.elements.containsKey(MovingPlatform.class))
            return;
        renderer.setColor(Color.WHITE);
        for (MovingPlatform platform : (Array<MovingPlatform>)this.elements.get(MovingPlatform.class)) {
            Rectangle bounds      = platform.getBounds();
            Array<Vector2> points = platform.getPoints();

            float halfWidth  = bounds.width / 2f;
            float halfHeight = bounds.height / 2f;
            for (int i = 0 ; i < points.size ; i++) {
                Vector2 current = new Vector2(points.get(i).x * WORLD_TO_SCREEN + halfWidth, points.get(i).y * WORLD_TO_SCREEN + halfHeight);
                Vector2 next = (i + 1 < points.size) ? points.get(i+1) : points.get(0);
                next = new Vector2(next.x * WORLD_TO_SCREEN + halfWidth, next.y * WORLD_TO_SCREEN + halfHeight);
                renderer.rectLine(current, next, 2);
            }
        }
    }

    /**
     * Private method to draw the Moving Platforms
     */
    private void drawMovingPlatforms() {
        if (!this.elements.containsKey(MovingPlatform.class))
            return;
        Texture texture = Assets.getTexture(MovingPlatform.class);
        for (MovingPlatform platform : (Array<MovingPlatform>)this.elements.get(MovingPlatform.class)) {
            Rectangle bounds = platform.getBounds();
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    /**
     * Private method to draw the Notices
     * @param delta the delta time since the last render call
     */
    private void drawNotices(float delta) {
        if (!this.elements.containsKey(Notice.class))
            return;
        TextureRegion region = noticeAnimation.getCurrentFrame(delta);
        for (Notice notice : (Array<Notice>)this.elements.get(Notice.class)) {
            Rectangle bounds = notice.getBounds();

            if (notice.displayNotice()) {
                fontCache.setWrappedText(notice.getMessage(), 0, 0, TEXT_WIDTH, BitmapFont.HAlignment.CENTER);
                float textX = bounds.x + bounds.width / 2 - fontCache.getBounds().width / 2;
                if (textX < 0)
                    textX = 0;

                fontCache.setPosition(textX, bounds.y + bounds.height + 2 * fontCache.getBounds().height + TEXT_GAP);
                fontCache.draw(batch);
            }

            batch.draw(region, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    /**
     * Private method to draw the WindBlowers
     */
    private void drawWindBlowers() {
        drawWindBlowers(this.windBlowers.get(WindDirection.NORTH));
        drawWindBlowers(this.windBlowers.get(WindDirection.EAST));
        drawWindBlowers(this.windBlowers.get(WindDirection.SOUTH));
        drawWindBlowers(this.windBlowers.get(WindDirection.WEST));
    }

    /**
     * Private method to draw the Teleporters
     */
    private void drawTeleporters() {
        if (!this.elements.containsKey(Teleporter.class))
            return;
        Texture texture = Assets.getTexture(Teleporter.class);
        Texture light   = Assets.getTexture(Vector2.class);
        float side = 2 * WORLD_TO_SCREEN;
        for (Teleporter teleporter : (Array<Teleporter>)this.elements.get(Teleporter.class)) {
            Rectangle bounds = teleporter.getBounds();
            Vector2 position = new Vector2(teleporter.getTeleportPosition().x * WORLD_TO_SCREEN, teleporter.getTeleportPosition().y * WORLD_TO_SCREEN);
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            batch.draw(light, position.x, position.y, side, side);
        }
    }

    /**
     * Method called to draw all the Exits
     */
    private void drawExits() {
        if (!this.elements.containsKey(Exit.class))
            return;
        Texture texture = Assets.getTexture(Exit.class);
        for (Exit exit : (Array<Exit>)this.elements.get(Exit.class)) {
            this.exitLights.get(exit).setActive(GameScreen.isExitReached());
            Rectangle bounds = exit.getBounds();
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    /**
     * Method called to draw all the Levers of the Level
     */
    private void drawLevers() {
        if (!this.elements.containsKey(Lever.class))
            return;
        for (Lever lever : (Array<Lever>)this.elements.get(Lever.class)) {
            Rectangle bounds = lever.getBounds();
            batch.draw(leverRegions[lever.isActivated() ? 1 : 0], bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    /**
     * Method to draw all the specified WindBlowers
     * @param windBlowers the Array of the WindBlowers to draw
     */
    private void drawWindBlowers(Array<WindBlower> windBlowers) {
        if (windBlowers != null) {
            for (WindBlower blower : windBlowers) {
                for (PointLight light : this.windLights.get(blower)) {
                    float y = light.getY() + 1;
                    if (y > blower.getWorldBounds().height/2 + blower.getPosition().y)
                        y = blower.getPosition().y - blower.getWorldBounds().height/2;
                    light.setPosition(light.getX(), y);
                }
                /*boolean transparent;
                Rectangle bounds = blower.getBounds();
                windSprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
                transparent = blower instanceof WindBlowerEnabled && !((WindBlowerEnabled)blower).isActivated();
                if (transparent)
                    windSprite.setAlpha(0.5f);
                windSprite.draw(batch);
                if (transparent)
                    windSprite.setAlpha(1);*/
            }
        }
        windSprite.rotate90(true);
    }

    /**
     * Method to draw all the Enemies of the Level
     */
    private void drawEnemies() {
        Color color = batch.getColor();

        for (ElementColor elementColor : ElementColor.values())
            drawEnemies(elementColor);

        batch.setColor(color);
    }

    /**
     * Method to draw the Enemies of the specified ElementColor by testing before if the Level contains Enemies of this color
     * @param elementColor the ElementColor of the Enemies to draw
     */
    private void drawEnemies(ElementColor elementColor) {
        if (this.enemies.containsKey(elementColor)) {
            Color color = elementColor.getColor();
            batch.setColor(color.r, color.g, color.b, OPACITY);
            drawEnemies(this.enemies.get(elementColor));
        }
    }

    /**
     * Method to draw the specified Enemies
     * @param enemies the Array of the Enemies to draw
     */
    private void drawEnemies(Array<Enemy> enemies) {
        Texture texture = Assets.getTexture(Enemy.class);
        for (Enemy enemy : enemies) {
            this.enemyLights.get(enemy).setActive(enemy.isAlive());
            if (enemy.isAlive()) {
                Rectangle bounds = enemy.getBounds();
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
                this.enemyLights.get(enemy).setPosition(enemy.getPosition());
            }
        }
    }

    /**
     * Method to add a ColorPlatform to draw
     * @param color the ElementColor of the ColorPlatform
     * @param platform the ColorPlatform to add
     */
    public void addColorPlatform(ElementColor color, ColorPlatform platform) {
        if (!this.colorPlatforms.containsKey(color))
            this.colorPlatforms.put(color, new Array<ColorPlatform>());
        this.colorPlatforms.get(color).add(platform);

        Array<PointLight> lights = new Array<>();
        Rectangle bounds = platform.getWorldBounds();

        for (int i = 0 ; i < bounds.height ; i++) {
            for (int j = 0 ; j < bounds.width ; j++) {
                PointLight light = new PointLight(this.rayHandler, 5, platform.getElementColor().getColor(), COLOR_LIGHT_WIDTH, bounds.x + 0.5f + j, bounds.y + 0.5f + i);
                light.setActive(platform.isActivated());
                lights.add(light);
            }
        }
        this.colorLights.put(platform, lights);
    }

    /**
     * Method to add a WindBlower to draw
     * @param direction the WindDirection of the WindBlower
     * @param blower the WindBlower to add
     */
    public void addWindBlower(WindDirection direction, WindBlower blower) {
        if (!this.windBlowers.containsKey(direction))
            this.windBlowers.put(direction, new Array<WindBlower>());
        this.windBlowers.get(direction).add(blower);
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
        Array<PointLight> lights = new Array<>();
        for (int i = 0 ; i < 10 ; i++) {
            lights.add(new PointLight(this.rayHandler, 5, Color.WHITE, 1.6f, bounds.x + i * bounds.width / 10, bounds.y));
            lights.add(new PointLight(this.rayHandler, 5, Color.WHITE, 1.6f, bounds.x + i * bounds.width / 10, bounds.y + 4));
        }
        this.windLights.put(blower, lights);
    }

    /**
     * Method to add an Enemy to draw
     * @param color the ElementColor of the Enemy
     * @param enemy the Enemy to add
     */
    public void addEnemy(ElementColor color, Enemy enemy) {
        if (!this.enemies.containsKey(color))
            this.enemies.put(color, new Array<Enemy>());
        this.enemies.get(color).add(enemy);
        Vector2 position = enemy.getPosition();
        this.enemyLights.put(enemy, new PointLight(this.rayHandler, 5, color.getColor(), ENEMY_LIGHT_WIDTH, position.x, position.y));
    }

    /**
     * Method to add an element to draw
     * @param type the type of the element (its class)
     * @param element the BaseElement to add
     * @param <T> the type of the element
     */
    public <T> void addElement(Class<T> type, T element) {
        if (!this.elements.containsKey(type))
            this.elements.put(type, new Array<T>());
        ((Array<T>) this.elements.get(type)).add(element);
        if (type == Exit.class) {
            Vector2 position = ((BaseElement)element).getPosition();
            this.exitLights.put(((Exit)element), new PointLight(this.rayHandler, 5, Color.CYAN, 10, position.x, position.y));
        } else if (type == Teleporter.class) {
            Teleporter teleporter = (Teleporter)element;
            drawLightEllipse(teleporter.getWorldBounds(), Color.CYAN);
            Vector2 position = teleporter.getTeleportPosition();
            drawLightEllipse(new Rectangle(position.x - 1.6f, position.y, 3.2f, 4.8f), Color.ORANGE);
        }
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

    /**
     * Method called to dispose the RayHandler
     */
    public void disposeLights() {
        this.rayHandler.dispose();
    }

    /**
     * Static method to dispose all static attributes
     */
    public static void dispose() {
        batch.dispose();
        renderer.dispose();
        background.dispose();
    }

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
        moveCamera(referenceCamera, camera, (referenceCamera == camera) ? MOVING_AMOUNT : 1.0f * MOVING_AMOUNT/BaseElement.WORLD_TO_SCREEN);
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
     * Private static method to move the Camera according to the mouse Position and the reference Camera
     * @param referenceCamera the OrthographicCamera to take as a view reference
     * @param movingCamera the OrthographicCamera which should change its position
     * @param movingAmount the moving amount the movingCamea should move
     */
    private static void moveCamera(OrthographicCamera referenceCamera, OrthographicCamera movingCamera, float movingAmount) {
        if (Gdx.input.getX() > (MOVING_GAP - 1) * referenceCamera.viewportWidth/MOVING_GAP) // going to the right
            movingCamera.position.x += movingAmount;
        else if (Gdx.input.getX() < referenceCamera.viewportWidth/MOVING_GAP) // going to the left
            movingCamera.position.x -= movingAmount;

        if (Gdx.input.getY() > (MOVING_GAP - 1) * referenceCamera.viewportHeight/MOVING_GAP) // going to the bottom
            movingCamera.position.y -= movingAmount;
        else if (Gdx.input.getY() < referenceCamera.viewportHeight/MOVING_GAP) // going to the top
            movingCamera.position.y += movingAmount;
    }
}
