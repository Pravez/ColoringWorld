package com.color.game.graphics;

import box2dLight.PointLight;
import box2dLight.RayHandler;
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
import com.color.game.elements.enabledelements.WindBlowerEnabled;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.Lever;
import com.color.game.elements.staticelements.platforms.*;
import com.color.game.elements.staticelements.sensors.*;
import com.color.game.levels.LevelManager;
import com.color.game.screens.GameScreen;

import java.util.HashMap;

import static com.color.game.elements.BaseElement.WORLD_TO_SCREEN;

public class GraphicManager {

    private static final int COLOR_COLS = 6;
    private static final int COLOR_ROWS = 4;

    private static final int FONT_SIZE = 18;//14;
    private static final int TEXT_WIDTH = 260;//200;
    private static final int TEXT_GAP = 20;
    private static final float NOTICE_DELAY = 0.15f;

    private HashMap<Class<?>, Array<?>> elements;
    private HashMap<ElementColor, Array<ColorPlatform>> colorPlatforms;
    private HashMap<ElementColor, Array<Enemy>> enemies;
    private HashMap<WindDirection, Array<WindBlower>> windBlowers;

    private static SpriteBatch   batch;
    private static ShapeRenderer renderer;
    private static RayHandler    rayHandler;

    private static PointLight characterLight;

    private static TextureRegion    leverRegions[];
    private static TextureRegion    colorPlatformRegions[][];
    private static TextureAnimation noticeAnimation;
    private static BitmapFontCache  fontCache;
    private static Sprite           windSprite;

    public GraphicManager() {
        this.elements       = new HashMap<>();
        this.colorPlatforms = new HashMap<>();
        this.enemies        = new HashMap<>();
        this.windBlowers    = new HashMap<>();
    }

    public static void init() {
        batch    = new SpriteBatch();
        renderer = new ShapeRenderer();

        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        rayHandler = new RayHandler(LevelManager.getCurrentLevel().getWorld());
        rayHandler.setAmbientLight(0.4f, 0.4f, 0.4f, 0.9f);
        rayHandler.setBlurNum(3);
        rayHandler.setShadows(true);

        Vector2 pos = GameScreen.character.getCenter();
        characterLight = new PointLight(rayHandler, 5, Color.WHITE, 80, pos.x, pos.y);
        characterLight.setXray(true);
        //light.attachToBody(GameScreen.character.getPhysicComponent().getBody());

        Texture texture1     = Assets.getTexture(Lever.class);
        leverRegions    = new TextureRegion[2];
        leverRegions[0] = new TextureRegion(texture1, 0, 0, texture1.getWidth()/2, texture1.getHeight());
        leverRegions[1] = new TextureRegion(texture1, texture1.getWidth()/2, 0, texture1.getWidth()/2, texture1.getHeight());

        Texture texture2          = Assets.getTexture(ColorPlatform.class);
        colorPlatformRegions = TextureRegion.split(texture2, texture2.getWidth()/COLOR_COLS, texture2.getHeight()/COLOR_ROWS);

        noticeAnimation = new TextureAnimation(Assets.getTexture(Notice.class), 2, 2, NOTICE_DELAY);
        fontCache       = new BitmapFontCache(Assets.getGroboldFont(FONT_SIZE));

        windSprite      = new Sprite(Assets.getTexture(WindBlower.class));
    }

    public void draw() {
        //rayHandler = new RayHandler(LevelManager.getCurrentLevel().getWorld());

        float delta = Gdx.graphics.getDeltaTime();

        rayHandler.setCombinedMatrix(GameScreen.camera.combined, GameScreen.camera.position.x, GameScreen.camera.position.y, GameScreen.camera.viewportWidth, GameScreen.camera.viewportHeight);
        //.setCombinedMatrix(GameScreen.camera.combined);
        /*Vector2 pos = GameScreen.character.getCenter();
        //new PointLight(rayHandler, 100, Color.WHITE, 200, pos.x, pos.y);
        PointLight light = new PointLight(rayHandler, 1000, Color.WHITE, 10, 10, 10);
        light.attachToBody(GameScreen.character.getPhysicComponent().getBody());*/
        characterLight.setPosition(GameScreen.character.getCenter());
        rayHandler.updateAndRender();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(GameScreen.camera.combined);

        //drawMovingPlatformsPath();

        renderer.end();

        batch.begin();
        batch.setProjectionMatrix(GameScreen.camera.combined);

        drawColorPlatforms();
        drawDeadlyPlatforms();
        drawFallingPlatforms();
        drawMovingPlatforms();

        drawNotices(delta);
        drawWindBlowers();
        drawTeleporters();
        drawExits();
        drawLevers();

        drawEnemies();
        batch.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        drawCharacter();
        renderer.end();
    }

    private void drawColorPlatforms() {
        Color color = batch.getColor();

        if (this.colorPlatforms.containsKey(ElementColor.RED)) {
            batch.setColor(Color.RED);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.RED));
        }
        if (this.colorPlatforms.containsKey(ElementColor.BLUE)) {
            batch.setColor(Color.BLUE);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.BLUE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.YELLOW)) {
            batch.setColor(Color.YELLOW);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.YELLOW));
        }
        if (this.colorPlatforms.containsKey(ElementColor.PURPLE)) {
            batch.setColor(Color.PURPLE);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.PURPLE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.GREEN)) {
            batch.setColor(Color.GREEN);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.GREEN));
        }
        if (this.colorPlatforms.containsKey(ElementColor.ORANGE)) {
            batch.setColor(Color.ORANGE);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.ORANGE));
        }
        /*if (this.colorPlatforms.containsKey(ElementColor.BLACK)) {
            this.batch.setColor(Color.BLACK);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.BLACK));
        }*/

        batch.setColor(color);
    }

    private void drawColorPlatforms(Array<ColorPlatform> colorPlatforms) {
        for (ColorPlatform platform : colorPlatforms) {
            Rectangle bounds = platform.getBounds();
            int col = 0;
            int row = platform.isActivated() ? 1 : 0;
            if (platform.getPhysicComponent().getUserData().getWidth() < platform.getPhysicComponent().getUserData().getHeight()) {//== 1) { // Vertical platform
                col = 3;
                float height = platform.getPhysicComponent().getUserData().getHeight();
                batch.draw(colorPlatformRegions[row][col + 2], bounds.x, bounds.y, bounds.width, WORLD_TO_SCREEN);
                for (int i = 1 ; i < height - 1 ; i ++) {
                    batch.draw(colorPlatformRegions[row][col + 1], bounds.x, bounds.y + i * WORLD_TO_SCREEN, bounds.width, WORLD_TO_SCREEN);
                }
                batch.draw(colorPlatformRegions[row][col], bounds.x, bounds.y + (height - 1) * WORLD_TO_SCREEN, bounds.width, WORLD_TO_SCREEN);

            } else { // if (platform.getPhysicComponent().getUserData().getHeight() == 1) { // Horizontal platform
                col = 0;
                float width = platform.getPhysicComponent().getUserData().getWidth();
                batch.draw(colorPlatformRegions[row][col], bounds.x, bounds.y, WORLD_TO_SCREEN, bounds.height);
                for (int i = 1 ; i < width - 1 ; i ++) {
                    batch.draw(colorPlatformRegions[row][col + 1], bounds.x + i * WORLD_TO_SCREEN, bounds.y, WORLD_TO_SCREEN, bounds.height);
                }
                batch.draw(colorPlatformRegions[row][col + 2], bounds.x + (width - 1) * WORLD_TO_SCREEN, bounds.y, WORLD_TO_SCREEN, bounds.height);
            }
            if (platform.isActivated()) {
                new PointLight(rayHandler, 100, Color.WHITE, 80, bounds.x, bounds.y);
            }
        }
    }

    private void drawDeadlyPlatforms() {
        if (!this.elements.containsKey(DeadlyPlatform.class))
            return;
        Texture texture = Assets.getTexture(DeadlyPlatform.class);
        for (DeadlyPlatform platform : (Array<DeadlyPlatform>)this.elements.get(DeadlyPlatform.class)) {
            float width = platform.getPhysicComponent().getUserData().getWidth();
            Rectangle bounds = platform.getBounds();
            for (int i = 0 ; i < width ; i ++)
                batch.draw(texture, bounds.x + WORLD_TO_SCREEN * i, bounds.y, WORLD_TO_SCREEN, bounds.height);
        }
    }

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

    private void drawMovingPlatforms() {
        if (!this.elements.containsKey(MovingPlatform.class))
            return;
        Texture texture = Assets.getTexture(MovingPlatform.class);
        for (MovingPlatform platform : (Array<MovingPlatform>)this.elements.get(MovingPlatform.class)) {
            Rectangle bounds = platform.getBounds();
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

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

    private void drawWindBlowers() {
        drawWindBlowers(this.windBlowers.get(WindDirection.NORTH));
        drawWindBlowers(this.windBlowers.get(WindDirection.EAST));
        drawWindBlowers(this.windBlowers.get(WindDirection.SOUTH));
        drawWindBlowers(this.windBlowers.get(WindDirection.WEST));
    }

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

    private void drawExits() {
        if (!this.elements.containsKey(Exit.class))
            return;
        Texture texture = Assets.getTexture(Exit.class);
        for (Exit exit : (Array<Exit>)this.elements.get(Exit.class)) {
            Rectangle bounds = exit.getBounds();
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    private void drawLevers() {
        if (!this.elements.containsKey(Lever.class))
            return;
        for (Lever lever : (Array<Lever>)this.elements.get(Lever.class)) {
            Rectangle bounds = lever.getBounds();
            if (lever.isActivated())
                batch.draw(leverRegions[1], bounds.x, bounds.y, bounds.width, bounds.height);
            else
                batch.draw(leverRegions[0], bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    private void drawWindBlowers(Array<WindBlower> windBlowers) {
        if (windBlowers != null) {
            for (WindBlower blower : windBlowers) {
                boolean transparent;
                Rectangle bounds = blower.getBounds();
                windSprite.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
                transparent = blower instanceof WindBlowerEnabled && !((WindBlowerEnabled)blower).isActivated();
                if (transparent)
                    windSprite.setAlpha(0.5f);
                windSprite.draw(batch);
                if (transparent)
                    windSprite.setAlpha(1);
            }
        }
        windSprite.rotate90(true);
    }

    private void drawEnemies() {
        Color color = batch.getColor();

        if (this.enemies.containsKey(ElementColor.RED)) {
            batch.setColor(Color.RED);
            drawEnemies(this.enemies.get(ElementColor.RED));
        }
        if (this.enemies.containsKey(ElementColor.BLUE)) {
            batch.setColor(Color.BLUE);
            drawEnemies(this.enemies.get(ElementColor.BLUE));
        }
        if (this.enemies.containsKey(ElementColor.YELLOW)) {
            batch.setColor(Color.YELLOW);
            drawEnemies(this.enemies.get(ElementColor.YELLOW));
        }
        if (this.enemies.containsKey(ElementColor.PURPLE)) {
            batch.setColor(Color.PURPLE);
            drawEnemies(this.enemies.get(ElementColor.PURPLE));
        }
        if (this.enemies.containsKey(ElementColor.GREEN)) {
            batch.setColor(Color.GREEN);
            drawEnemies(this.enemies.get(ElementColor.GREEN));
        }
        if (this.enemies.containsKey(ElementColor.ORANGE)) {
            batch.setColor(Color.ORANGE);
            drawEnemies(this.enemies.get(ElementColor.ORANGE));
        }
        /*if (this.enemies.containsKey(ElementColor.BLACK)) {
            this.batch.setColor(Color.BLACK);
            drawEnemies(this.enemies.get(ElementColor.BLACK));
        }*/

        batch.setColor(color);
    }

    private void drawEnemies(Array<Enemy> enemies) {
        Texture texture = Assets.getTexture(Enemy.class);
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                Rectangle bounds = enemy.getBounds();
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }
    }

    private void drawCharacter() {
        Rectangle bounds = GameScreen.character.getBounds();
        renderer.setColor(Color.WHITE);
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        //batch.draw(Assets.getTexture(com.color.game.elements.dynamicelements.Character.class), bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void addColorPlatform(ElementColor color, ColorPlatform platform) {
        if (!this.colorPlatforms.containsKey(color))
            this.colorPlatforms.put(color, new Array<ColorPlatform>());
        this.colorPlatforms.get(color).add(platform);
    }

    public void addWindBlower(WindDirection direction, WindBlower blower) {
        if (!this.windBlowers.containsKey(direction))
            this.windBlowers.put(direction, new Array<WindBlower>());
        this.windBlowers.get(direction).add(blower);
    }

    public void addEnemy(ElementColor color, Enemy enemy) {
        if (!this.enemies.containsKey(color))
            this.enemies.put(color, new Array<Enemy>());
        this.enemies.get(color).add(enemy);
    }

    public <T> void addElement(Class<T> type, T element) {
        if (!this.elements.containsKey(type))
            this.elements.put(type, new Array<T>());
        ((Array<T>) this.elements.get(type)).add(element);
    }
}
