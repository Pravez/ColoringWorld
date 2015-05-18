package com.color.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Assets class containing all the assets needed for the game handled by a {@link AssetManager}
 * Also contains the fonts and the skin
 * This class allows to generate BitmapFont with the needed size
 */
public class Assets {

    /**
     * Default size to have homogeneous texts
     */
    public final static int TITLE_SIZE = 42;
    public final static int TEXT_SIZE = 22;

    /**
     * The fileHandles for the different used fonts
     */
    private static FileHandle basicFont;
    private static FileHandle bebasFont;
    private static FileHandle groboldFont;

    final public static AssetManager manager  = new AssetManager();
    public static Skin         menuSkin;

    /**
     * Add to the loading queue of the AssetManager all the assets to load
     * It should be called only once at the beginning
     */
    public static void queueLoading() {
        // Backgrounds
        manager.load("backgrounds/background0.png", Texture.class);
        manager.load("backgrounds/background1.png", Texture.class);

        // Fonts
        basicFont = new FileHandle("fonts/Future-Earth.ttf");
        bebasFont = new FileHandle("fonts/BebasNeue.otf");
        groboldFont = new FileHandle("fonts/grobold.ttf");

        // Musics
        manager.load("musics/music.mp3", Music.class);

        // Sounds
        manager.load("sounds/click.mp3", Sound.class);
        manager.load("sounds/jump.mp3", Sound.class);
        manager.load("sounds/landing.wav", Sound.class);
        manager.load("sounds/sound.wav", Sound.class);

        // Sprites
        manager.load("sprites/bar.png", Texture.class);
        manager.load("sprites/blocks.png", Texture.class);
        manager.load("sprites/character.png", Texture.class);
        manager.load("sprites/character-idle.png", Texture.class);
        manager.load("sprites/character-walking.png", Texture.class);
        manager.load("sprites/dead.png", Texture.class);
        manager.load("sprites/door.png", Texture.class);
        manager.load("sprites/ground.png", Texture.class);
        manager.load("sprites/spike.png", Texture.class);

        manager.load("sprites/colors.png", Texture.class);

        // Levels
        //manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        //manager.load("level1.tmx", TiledMap.class);
        // TiledMap map = manager.get("level1.tmx");
        // http://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx
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

    public static BitmapFont getBebasFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Assets.bebasFont);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    public static BitmapFont getGroboldFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Assets.groboldFont);
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
