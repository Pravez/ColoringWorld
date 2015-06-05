package com.color.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.color.game.assets.Assets;
import com.color.game.elements.dynamicelements.enemies.Enemy;
import com.color.game.elements.dynamicplatforms.FallingPlatform;
import com.color.game.elements.dynamicplatforms.MovingPlatform;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.Lever;
import com.color.game.elements.staticelements.platforms.ColorPlatform;
import com.color.game.elements.staticelements.platforms.DeadlyPlatform;
import com.color.game.elements.staticelements.platforms.ElementColor;
import com.color.game.elements.staticelements.sensors.Notice;
import com.color.game.elements.staticelements.sensors.Teleporter;
import com.color.game.elements.staticelements.sensors.WindBlower;
import com.color.game.elements.staticelements.sensors.WindDirection;
import com.color.game.levels.Level;
import com.color.game.levels.LevelManager;
import com.color.game.screens.GameScreen;

import java.util.HashMap;

import static com.color.game.elements.BaseElement.WORLD_TO_SCREEN;

public class GraphicManager {

    private static final float OPACITY = 0.4f;

    /**
     * Notice constants
     */
    private static final int FONT_SIZE = 36;
    private static final int TEXT_WIDTH = 260;
    private static final int TEXT_GAP = 20;
    private static final float NOTICE_DELAY = 0.15f;

    /**
     * HashMap of the Level elements to render
     */
    private HashMap<Class<?>, Array<?>> elements;
    private HashMap<ElementColor, Array<ColorPlatform>> colorPlatforms;
    private HashMap<ElementColor, Array<Enemy>> enemies;
    private HashMap<WindDirection, Array<WindBlower>> windBlowers;

    /**
     * Drawing tools
     */
    private static SpriteBatch   batch;
    private static ShapeRenderer renderer;

    public LightManager lightManager;

    private static Color characterColor;

    /**
     * Textures, sprites and Animations for the elements
     */
    private static TextureRegion    leverRegions[];
    private static TextureRegion    deadlyRegions[][];
    private static Texture          colorPlatformTexture;
    private static TextureAnimation noticeAnimation;
    private static BitmapFontCache  fontCache;
    private static Texture          background;

    public GraphicManager(Level level) {
        this.elements       = new HashMap<>();
        this.colorPlatforms = new HashMap<>();
        this.enemies        = new HashMap<>();
        this.windBlowers    = new HashMap<>();
        this.lightManager   = new LightManager(level);
    }

    public static void init() {
        LightManager.init();

        characterColor = new Color(Color.WHITE);

        batch    = new SpriteBatch();
        renderer = new ShapeRenderer();

        Texture leverTexture = Assets.getTexture(Lever.class);
        leverRegions    = new TextureRegion[2];
        leverRegions[0] = new TextureRegion(leverTexture, 0, 0, leverTexture.getWidth()/2, leverTexture.getHeight());
        leverRegions[1] = new TextureRegion(leverTexture, leverTexture.getWidth()/2, 0, leverTexture.getWidth()/2, leverTexture.getHeight());

        deadlyRegions = TextureRegion.split(Assets.getTexture(DeadlyPlatform.class), 32, 32);

        colorPlatformTexture = Assets.getTexture(ColorPlatform.class);

        noticeAnimation = new TextureAnimation(Assets.getTexture(Notice.class), 2, 2, NOTICE_DELAY);
        fontCache       = new BitmapFontCache(Assets.getMenuFont(FONT_SIZE));

        background      = Assets.manager.get("sprites/back.png", Texture.class);
    }

    /**
     * Method to draw the Level
     */
    public void draw() {
        float delta = GameScreen.isRunning() ? Gdx.graphics.getDeltaTime() : 0;

        this.lightManager.render(delta);

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

        drawColorPlatforms(delta);
        drawMovingPlatforms();
        drawDeadlyPlatforms();
        Color color = new Color(batch.getColor());
        batch.setColor(color.r, color.g, color.b, OPACITY);
        drawFallingPlatforms();

        drawWindBlowers();
        drawLevers();
        drawExits();
        batch.setColor(color);
        drawNotices(delta);

        drawEnemies();
        batch.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        drawCharacter();
        renderer.end();
    }

    /**
     * Private method to render all the ColorPlatforms
     */
    private void drawColorPlatforms(float delta) {
        Color color = batch.getColor();

        for (ElementColor elementColor : ElementColor.values())
            drawColorPlatforms(elementColor, delta);

        batch.setColor(color);
    }

    /**
     * Method to draw all ColorPlatforms of a certain color whith testing before if the level contains ColorPlatforms of this color
     * @param elementColor the ElementColor of the ColorPlatforms to render
     */
    private void drawColorPlatforms(ElementColor elementColor, float delta) {
        if (this.colorPlatforms.containsKey(elementColor)) {
            Color color = elementColor.getColor();
            batch.setColor(color.r, color.g, color.b, OPACITY);
            drawColorPlatforms(this.colorPlatforms.get(elementColor), delta);
        }
    }

    /**
     * Method to draw all the ColorPlatforms specified
     * @param colorPlatforms all the ColorPlatforms to draw
     */
    private void drawColorPlatforms(Array<ColorPlatform> colorPlatforms, float delta) {
        for (ColorPlatform platform : colorPlatforms) {
            this.lightManager.renderColorPlatformLights(platform, delta);
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
        renderer.setColor(Color.GRAY);
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
        updateBlowers();
        drawWindBlowers(this.windBlowers.get(WindDirection.NORTH));
        drawWindBlowers(this.windBlowers.get(WindDirection.EAST));
        drawWindBlowers(this.windBlowers.get(WindDirection.SOUTH));
        drawWindBlowers(this.windBlowers.get(WindDirection.WEST));
    }

    /**
     * Method called to draw all the Exits
     */
    private void drawExits() {
        if (!this.elements.containsKey(Exit.class))
            return;
        Texture texture = Assets.getTexture(Exit.class);
        for (Exit exit : (Array<Exit>)this.elements.get(Exit.class)) {
            this.lightManager.renderExitLights(exit);
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
            for (WindBlower blower : windBlowers)
                this.lightManager.renderWindBlowerLight(blower);
        }
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
            this.lightManager.renderEnemyLight(enemy);
            if (enemy.isAlive()) {
                Rectangle bounds = enemy.getBounds();
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }
    }

    /**
     * Method to draw the character
     */
    private void drawCharacter() {
        if (!GameScreen.isPaused() && !GameScreen.isRunning())
            characterColor.lerp(Color.GRAY, Gdx.graphics.getDeltaTime()/GameScreen.DEATH_DELAY);
        else
            characterColor.set(Color.WHITE);
        this.lightManager.renderCharacterLight(!GameScreen.isPaused() && !GameScreen.isRunning());
        renderer.setColor(characterColor);
        Rectangle bounds = GameScreen.character.getBounds();
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
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

        this.lightManager.addColorPlatformLights(platform);
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

        this.lightManager.addWindBlowerLights(direction, blower);
    }

    /**
     * Method to update wind particles of every blower in the level
     */
    public void updateBlowers(){
        for(WindDirection direction : windBlowers.keySet()){
            for(WindBlower blower : windBlowers.get(direction)){
                this.lightManager.generateWind(blower.getWorldBounds(), blower);
            }
        }
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

        this.lightManager.addEnemyLights(color, enemy);
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
            this.lightManager.addExitLights((Exit)element);
        } else if (type == Teleporter.class) {
            this.lightManager.addTeleporterLights((Teleporter)element);
        }
    }

    /**
     * Method called to dispose the RayHandler
     */
    public void disposeLights() {
        this.lightManager.dispose();
    }

    /**
     * Static method to dispose all static attributes
     */
    public static void dispose() {
        batch.dispose();
        renderer.dispose();
        background.dispose();
    }
}