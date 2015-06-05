package com.color.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.color.game.elements.dynamicelements.enemies.Enemy;
import com.color.game.elements.dynamicplatforms.FallingPlatform;
import com.color.game.elements.dynamicplatforms.MovingPlatform;
import com.color.game.elements.staticelements.Exit;
import com.color.game.elements.staticelements.Lever;
import com.color.game.elements.staticelements.platforms.ColorPlatform;
import com.color.game.elements.staticelements.platforms.DeadlyPlatform;
import com.color.game.elements.staticelements.sensors.Notice;
import com.color.game.elements.staticelements.sensors.Teleporter;
import com.color.game.elements.staticelements.sensors.WindBlower;
import com.color.game.gui.AnimatedCube;
import com.color.game.gui.StripButton;

import java.util.HashMap;

/**
 * Assets class containing all the assets needed for the game handled by a {@link AssetManager}
 * Also contains the fonts and the skin
 * This class allows to generate BitmapFont with the needed size
 */
public class Assets {

    /**
     * The fileHandles for the different used fonts
     */
    private static FileHandle basicFont;
    private static FileHandle menuFont;

    private static HashMap<Class<?>, Texture> textures;

    final public static AssetManager manager  = new AssetManager();
    public static Skin         menuSkin;

    /**
     * Add to the loading queue of the AssetManager all the assets to load
     * It should be called only once at the beginning
     */
    public static void queueLoading() {
        // Backgrounds
        manager.load("backgrounds/background.png", Texture.class);
        manager.load("backgrounds/white.png", Texture.class);

        // Fonts
        basicFont   = new FileHandle("fonts/Future-Earth.ttf");
        menuFont    = new FileHandle("fonts/Cashew Apple Ale Bold.ttf");

        // Musics
        manager.load("musics/Ether.mp3", Music.class);
        manager.load("musics/Hydra.mp3", Music.class);

        // Sounds
        manager.load("sounds/click.mp3", Sound.class);
        manager.load("sounds/jump.mp3", Sound.class);
        manager.load("sounds/landing.wav", Sound.class);
        manager.load("sounds/sound.wav", Sound.class);

        // Sprites
        manager.load("sprites/bar.png", Texture.class);
        manager.load("sprites/moving.png", Texture.class);
        manager.load("sprites/falling.png", Texture.class);
        manager.load("sprites/enemy.png", Texture.class);
        manager.load("sprites/deadly.png", Texture.class);
        manager.load("sprites/enabled.png", Texture.class);
        manager.load("sprites/lever.png", Texture.class);
        manager.load("sprites/fireflies.png", Texture.class);
        manager.load("sprites/exit.png", Texture.class);
        manager.load("sprites/color.png", Texture.class);
        manager.load("sprites/cube.png", Texture.class);
        manager.load("sprites/triangle.png", Texture.class);

        // Colors
        manager.load("colors/red.png", Texture.class);
        manager.load("colors/blue.png", Texture.class);
        manager.load("colors/yellow.png", Texture.class);
        manager.load("colors/purple.png", Texture.class);
        manager.load("colors/green.png", Texture.class);
        manager.load("colors/orange.png", Texture.class);
        manager.load("colors/white.png", Texture.class);
    }

    public static void loadTextures() {
        Assets.textures = new HashMap<>();

        Assets.textures.put(Lever.class, Assets.manager.get("sprites/lever.png", Texture.class));
        Assets.textures.put(ColorPlatform.class, Assets.manager.get("sprites/color.png", Texture.class));
        Assets.textures.put(Notice.class, Assets.manager.get("sprites/fireflies.png", Texture.class));
        Assets.textures.put(DeadlyPlatform.class, Assets.manager.get("sprites/deadly.png", Texture.class));
        Assets.textures.put(FallingPlatform.class, Assets.manager.get("sprites/falling.png", Texture.class));
        Assets.textures.put(MovingPlatform.class, Assets.manager.get("sprites/moving.png", Texture.class));
        Assets.textures.put(Exit.class, Assets.manager.get("sprites/exit.png", Texture.class));
        Assets.textures.put(Enemy.class, Assets.manager.get("sprites/enemy.png", Texture.class));
        Assets.textures.put(AnimatedCube.class, Assets.manager.get("sprites/cube.png", Texture.class));
        Assets.textures.put(StripButton.class, Assets.manager.get("sprites/triangle.png", Texture.class));
    }

    public static Texture getTexture(Class<?> type) {
        return Assets.textures.get(type);
    }

    /**
     * Initialize the skin used in the different screens
     */
    public static void setMenuSkin() {
        if(menuSkin == null) {
            menuSkin = new Skin();
            menuSkin.add("future", getBasicFont(20));
            menuSkin.addRegions(new TextureAtlas(Gdx.files.internal("skins/uiskin.atlas")));
            menuSkin.load(Gdx.files.internal("skins/uiskin.json"));
        }
    }

    /**
     * This function gets called every render() and the AssetManager pauses the loading each frame
     * so we can still run menus and loading screens smoothly
     */
    public static boolean update() {
        return manager.update();
    }

    /**
     * Return a BitmapFont with the BasicFont of the game, at the size specified in parameter
     * @param size the size of the BitmapFont to create
     * @return the BitmapFont created
     */
    public static BitmapFont getBasicFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Assets.basicFont);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    public static BitmapFont getMenuFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Assets.menuFont);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    /**
     * Dispose all the assets loaded for the game
     */
    public static void dispose() {
        manager.dispose();
        menuSkin.dispose();
    }
}
