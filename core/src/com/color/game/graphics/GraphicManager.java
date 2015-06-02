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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.color.game.assets.Assets;
import com.color.game.elements.BaseElement;
import com.color.game.elements.PhysicComponent;
import com.color.game.elements.dynamicelements.enemies.Enemy;
import com.color.game.elements.dynamicplatforms.FallingPlatform;
import com.color.game.elements.dynamicplatforms.MovingPlatform;
import com.color.game.elements.enabledelements.WindBlowerEnabled;
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

    private static boolean RAY_TEST = true;
    private static boolean COLOR_TEST = true;
    private static boolean EXIT_TEST = true;

    private static final int MOVING_GAP = 4;
    private static final int MOVING_AMOUNT = 10;

    private static final float OPACITY = 0.4f;

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

    private HashMap<ColorPlatform, Array<PointLight>> colorLights;

    private static SpriteBatch   batch;
    private static ShapeRenderer renderer;
    public RayHandler    rayHandler;
    private Box2DDebugRenderer debugRenderer;

    private PointLight characterLight;
    private PointLight exitLight;

    private static OrthographicCamera lightCamera;

    private static TextureRegion    leverRegions[];
    private static TextureRegion    colorPlatformRegions[][];
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

        if (RAY_TEST) {
            this.debugRenderer = new Box2DDebugRenderer();
            this.rayHandler = new RayHandler(level.getWorld());
            //this.rayHandler.setAmbientLight(0.4f, 0.4f, 0.4f, 0.8f);
            this.rayHandler.setAmbientLight(1, 1, 1, 0.2f);
            this.characterLight = new PointLight(this.rayHandler, 50, Color.WHITE, 10, 5, 5);
        }
    }

    public static void init() {
        batch    = new SpriteBatch();
        renderer = new ShapeRenderer();

        if (RAY_TEST) {
            lightCamera = new OrthographicCamera();
            lightCamera.setToOrtho(false, Gdx.graphics.getWidth() / BaseElement.WORLD_TO_SCREEN, Gdx.graphics.getHeight() / BaseElement.WORLD_TO_SCREEN);
            //lightCamera.position.set(GameScreen.camera.position);
            /*RayHandler.setGammaCorrection(true);
            //RayHandler.useDiffuseLight(false);
            rayHandler = new RayHandler(LevelManager.getCurrentLevel().getWorld());
            rayHandler.setAmbientLight(0.4f, 0.4f, 0.4f, 0.9f);
            //rayHandler.setBlurNum(3);
            //rayHandler.setShadows(true);

            Vector2 pos = GameScreen.character.getCenter();
            characterLight = new PointLight(rayHandler, 50, Color.WHITE, 80, pos.x, pos.y);
            //characterLight.attachToBody(GameScreen.character.getPhysicComponent().getBody());
            characterLight.setStaticLight(true);
            //characterLight.setXray(true);
            //light.attachToBody(GameScreen.character.getPhysicComponent().getBody());*/

            PointLight.setContactFilter(PhysicComponent.CATEGORY_SCENERY, (short) 0, PhysicComponent.MASK_SCENERY);
        }

        Texture texture1     = Assets.getTexture(Lever.class);
        leverRegions    = new TextureRegion[2];
        leverRegions[0] = new TextureRegion(texture1, 0, 0, texture1.getWidth()/2, texture1.getHeight());
        leverRegions[1] = new TextureRegion(texture1, texture1.getWidth()/2, 0, texture1.getWidth()/2, texture1.getHeight());

        Texture texture2          = Assets.getTexture(ColorPlatform.class);
        colorPlatformRegions = TextureRegion.split(texture2, texture2.getWidth()/COLOR_COLS, texture2.getHeight()/COLOR_ROWS);

        noticeAnimation = new TextureAnimation(Assets.getTexture(Notice.class), 2, 2, NOTICE_DELAY);
        fontCache       = new BitmapFontCache(Assets.getGroboldFont(FONT_SIZE));

        windSprite      = new Sprite(Assets.getTexture(WindBlower.class));

        background      = Assets.manager.get("sprites/back.png", Texture.class);
    }

    public void draw() {
        float delta = Gdx.graphics.getDeltaTime();

        /* Ray test */
        if (RAY_TEST) {
            if (GameScreen.isRunning())
                handleCamera(lightCamera, LevelManager.getCurrentLevel().map.getWidth(), LevelManager.getCurrentLevel().map.getHeight(), GameScreen.character.getPosition());
            else
                handleMovingCamera2(GameScreen.camera, lightCamera, LevelManager.getCurrentLevel().map.getWidth(), LevelManager.getCurrentLevel().map.getHeight());
            //debugRenderer.render(LevelManager.getCurrentLevel().getWorld(), lightCamera.combined);

            //lightCamera.position.x = GameScreen.character.getPosition().x;
            //lightCamera.position.x = GameScreen.character.getPosition().x;
            //lightCamera.position.y = lightCamera.viewportHeight - GameScreen.character.getPosition().y;
            //lightCamera.update();
            rayHandler.setCombinedMatrix(lightCamera.combined);//, lightCamera.position.x, lightCamera.position.y, lightCamera.viewportWidth, lightCamera.viewportHeight);
            characterLight.setPosition(GameScreen.character.getPosition());
            rayHandler.updateAndRender();
        }

        float width   = GameScreen.camera.viewportWidth;
        float height  = GameScreen.camera.viewportHeight;
        float screenX = GameScreen.camera.position.x - width/2;
        float screenY = GameScreen.camera.position.y - height/2;

        /*batch.begin();
        batch.draw(background, screenX, screenY, width, height);
        batch.end();*/

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(GameScreen.camera.combined);

        //renderer.setColor(0f, 0f, 0f, 0.5f);
        //Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);

        //renderer.rect(screenX, screenY, width, height);

        drawMovingPlatformsPath();

        renderer.end();

        LevelManager.getCurrentLevel().showMapRenderer();

        batch.begin();
        batch.setProjectionMatrix(GameScreen.camera.combined);

        drawColorPlatforms();
        Color color = new Color(batch.getColor());
        batch.setColor(color.r, color.g, color.b, OPACITY);
        drawDeadlyPlatforms();
        drawFallingPlatforms();
        drawMovingPlatforms();

        drawWindBlowers();
        drawTeleporters();
        drawExits();
        drawLevers();
        batch.setColor(color);
        drawNotices(delta);

        drawEnemies();
        batch.end();

        //renderer.begin(ShapeRenderer.ShapeType.Filled);
        //drawCharacter();
        //renderer.setColor(0f, 0f, 0f, 0.5f);
        //Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        //renderer.rect(screenX, screenY, width, height);
        //renderer.end();
    }

    private void drawColorPlatforms() {
        Color color = batch.getColor();

        if (this.colorPlatforms.containsKey(ElementColor.RED)) {
            batch.setColor(Color.RED.r, Color.RED.g, Color.RED.b, OPACITY);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.RED));
        }
        if (this.colorPlatforms.containsKey(ElementColor.BLUE)) {
            batch.setColor(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, OPACITY);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.BLUE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.YELLOW)) {
            batch.setColor(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b, OPACITY);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.YELLOW));
        }
        if (this.colorPlatforms.containsKey(ElementColor.PURPLE)) {
            batch.setColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, OPACITY);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.PURPLE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.GREEN)) {
            batch.setColor(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, OPACITY);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.GREEN));
        }
        if (this.colorPlatforms.containsKey(ElementColor.ORANGE)) {
            batch.setColor(Color.ORANGE.r, Color.ORANGE.g, Color.ORANGE.b, OPACITY);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.ORANGE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.BLACK)) {
            batch.setColor(Color.WHITE);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.BLACK));
        }

        batch.setColor(color);
    }

    private void drawActivatedColorPlatforms() {
        Color color = batch.getColor();

        if (this.colorPlatforms.containsKey(ElementColor.RED)) {
            batch.setColor(Color.RED);
            drawActivatedColorPlatforms(this.colorPlatforms.get(ElementColor.RED));
        }
        if (this.colorPlatforms.containsKey(ElementColor.BLUE)) {
            batch.setColor(Color.BLUE);
            drawActivatedColorPlatforms(this.colorPlatforms.get(ElementColor.BLUE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.YELLOW)) {
            batch.setColor(Color.YELLOW);
            drawActivatedColorPlatforms(this.colorPlatforms.get(ElementColor.YELLOW));
        }
        if (this.colorPlatforms.containsKey(ElementColor.PURPLE)) {
            batch.setColor(Color.PURPLE);
            drawActivatedColorPlatforms(this.colorPlatforms.get(ElementColor.PURPLE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.GREEN)) {
            batch.setColor(Color.GREEN);
            drawActivatedColorPlatforms(this.colorPlatforms.get(ElementColor.GREEN));
        }
        if (this.colorPlatforms.containsKey(ElementColor.ORANGE)) {
            batch.setColor(Color.ORANGE);
            drawActivatedColorPlatforms(this.colorPlatforms.get(ElementColor.ORANGE));
        }
        /*if (this.colorPlatforms.containsKey(ElementColor.BLACK)) {
            this.batch.setColor(Color.BLACK);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.BLACK));
        }*/

        batch.setColor(color);
    }

    private void drawDeactivatedColorPlatforms() {
        Color color = batch.getColor();

        if (this.colorPlatforms.containsKey(ElementColor.RED)) {
            batch.setColor(Color.RED);
            drawDeactivatedColorPlatforms(this.colorPlatforms.get(ElementColor.RED));
        }
        if (this.colorPlatforms.containsKey(ElementColor.BLUE)) {
            batch.setColor(Color.BLUE);
            drawDeactivatedColorPlatforms(this.colorPlatforms.get(ElementColor.BLUE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.YELLOW)) {
            batch.setColor(Color.YELLOW);
            drawDeactivatedColorPlatforms(this.colorPlatforms.get(ElementColor.YELLOW));
        }
        if (this.colorPlatforms.containsKey(ElementColor.PURPLE)) {
            batch.setColor(Color.PURPLE);
            drawDeactivatedColorPlatforms(this.colorPlatforms.get(ElementColor.PURPLE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.GREEN)) {
            batch.setColor(Color.GREEN);
            drawDeactivatedColorPlatforms(this.colorPlatforms.get(ElementColor.GREEN));
        }
        if (this.colorPlatforms.containsKey(ElementColor.ORANGE)) {
            batch.setColor(Color.ORANGE);
            drawDeactivatedColorPlatforms(this.colorPlatforms.get(ElementColor.ORANGE));
        }
        if (this.colorPlatforms.containsKey(ElementColor.BLACK)) {
            batch.setColor(Color.WHITE);
            drawColorPlatforms(this.colorPlatforms.get(ElementColor.BLACK));
        }

        batch.setColor(color);
    }

    private void drawColorPlatforms(Array<ColorPlatform> colorPlatforms) {
        for (ColorPlatform platform : colorPlatforms) {
            if (RAY_TEST) {
                for (PointLight light : this.colorLights.get(platform)) {
                    light.setActive(platform.isActivated());
                }
            }
            Rectangle bounds = platform.getBounds();
            int col = 0;
            int row = 0;
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
        }
    }

    private void drawActivatedColorPlatforms(Array<ColorPlatform> colorPlatforms) {
        for (ColorPlatform platform : colorPlatforms) {
            if (!platform.isActivated())
                continue;
            Rectangle bounds = platform.getBounds();
            int col = 0;
            int row = 1;
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

    private void drawDeactivatedColorPlatforms(Array<ColorPlatform> colorPlatforms) {
        for (ColorPlatform platform : colorPlatforms) {
            if (platform.isActivated())
                continue;
            Rectangle bounds = platform.getBounds();
            int col = 0;
            int row = 0;
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
            if (EXIT_TEST) {
                if (GameScreen.isExitReached()) {
                    this.exitLight.setActive(true);
                } else if (this.exitLight.isActive()) {
                    this.exitLight.setActive(false);
                }
            }
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
        if (this.enemies.containsKey(ElementColor.BLACK)) {
            batch.setColor(Color.WHITE);
            drawEnemies(this.enemies.get(ElementColor.BLACK));
        }

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

        if (COLOR_TEST) {
            Array<PointLight> lights = new Array<>();
            Rectangle bounds = platform.getWorldBounds();

            for (int i = 0 ; i < bounds.height ; i++) {
                for (int j = 0 ; j < bounds.width ; j++) {
                    Color color1 = platform.getElementColor().getColor();
                    if (color1 == Color.BLACK)
                        color1 = Color.WHITE;
                    PointLight light = new PointLight(this.rayHandler, 5, color1, 2, bounds.x + j, bounds.y + i);
                    light.setActive(platform.isActivated());
                    lights.add(light);
                }
            }
            this.colorLights.put(platform, lights);
        }
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
        if (type == Exit.class && EXIT_TEST) {
            Vector2 position = ((BaseElement)element).getPosition();
            this.exitLight = new PointLight(this.rayHandler, 5, Color.CYAN, 10, position.x, position.y);
        }
    }

    public void disposeLights() {
        if (RAY_TEST)
            this.rayHandler.dispose();
    }

    public static void dispose() {
        batch.dispose();
        renderer.dispose();
        background.dispose();
    }

    public static void handleCamera(OrthographicCamera camera, float levelWidth, float levelHeight, Vector2 position) {
        camera.position.x = position.x;
        if (camera.viewportHeight < levelHeight)
            camera.position.y = position.y + camera.viewportHeight/4;
        else
            camera.position.y = camera.viewportHeight/4;
        stabilizeCamera(camera, levelWidth);
        if (camera.position.y > levelHeight - camera.viewportHeight / 2f && camera.viewportHeight < levelHeight) {
            camera.position.y = levelHeight - camera.viewportHeight / 2f;
        }
        camera.update();
    }

    public static void handleMovingCamera(OrthographicCamera camera, float levelWidth, float levelHeight) {
        moveCamera(camera, camera, MOVING_AMOUNT);
        stabilizeCamera(camera, levelWidth);
        if (camera.position.y > levelHeight - camera.viewportHeight / 2f) {
            if (camera.viewportHeight > levelHeight)
                camera.position.y = camera.viewportHeight/2;
            else
                camera.position.y = levelHeight - camera.viewportHeight / 2f;
        }
        camera.update();
    }

    public static void handleMovingCamera2(OrthographicCamera referenceCamera, OrthographicCamera camera, float levelWidth, float levelHeight) {
        moveCamera(referenceCamera, camera, 1.0f * MOVING_AMOUNT/BaseElement.WORLD_TO_SCREEN);
        stabilizeCamera(camera, levelWidth);
        if (camera.position.y > levelHeight - camera.viewportHeight / 2f) {
            if (camera.viewportHeight > levelHeight)
                camera.position.y = camera.viewportHeight/2;
            else
                camera.position.y = levelHeight - camera.viewportHeight / 2f;
        }
        camera.update();
    }

    private static void stabilizeCamera(OrthographicCamera camera, float levelWidth) {
        if (camera.position.x < camera.viewportWidth / 2f) {
            camera.position.x = camera.viewportWidth / 2f;
        }
        if (camera.position.y < camera.viewportHeight / 2f) {
            camera.position.y = camera.viewportHeight / 2f;
        }
        if (camera.position.x > levelWidth - camera.viewportWidth / 2f) {
            camera.position.x = levelWidth - camera.viewportWidth / 2f;
        }
    }

    private static void moveCamera(OrthographicCamera referenceCamera, OrthographicCamera movingCamera, float movingAmount) {
        if (Gdx.input.getX() > (MOVING_GAP - 1) * referenceCamera.viewportWidth/MOVING_GAP) { // going to the right
            movingCamera.position.x += movingAmount;
        } else if (Gdx.input.getX() < referenceCamera.viewportWidth/MOVING_GAP) { // going to the left
            movingCamera.position.x -= movingAmount;
        }

        if (Gdx.input.getY() > (MOVING_GAP - 1) * referenceCamera.viewportHeight/MOVING_GAP) { // going to the bottom
            movingCamera.position.y -= movingAmount;
        } else if (Gdx.input.getY() < referenceCamera.viewportHeight/MOVING_GAP) { // going to the top
            movingCamera.position.y += movingAmount;
        }
    }
}
