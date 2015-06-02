package com.color.game.levels.mapcreator;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.color.game.assets.SaveManager;
import com.color.game.elements.BaseElement;
import com.color.game.elements.enabledelements.BaseEnabledElement;
import com.color.game.levels.Level;
import com.color.game.levels.ScoreHandler;
import com.color.game.levels.mapcreator.elements.TiledElements;
import com.color.game.levels.mapcreator.elements.objects.*;
import com.color.game.levels.mapcreator.elements.statics.TiledColorPlatforms;
import com.color.game.levels.mapcreator.elements.statics.TiledDeadlyPlatforms;
import com.color.game.levels.mapcreator.elements.statics.TiledPlatforms;

import javax.swing.*;
import java.util.HashMap;

public class TiledMapLoader {

    private String[] maps = new String[]{"static", "moving", "deadly", "red", "red_deactivated",
                                         "blue", "blue_deactivated", "yellow", "yellow_deactivated",
                                         "purple", "purple_deactivated", "orange", "orange_deactivated",
                                         "green", "green_deactivated", "black", "black_deactivated",
                                         "character", "exit", "teleporter", "falling", "windblower",
                                         "enemies", "bouncing", "notice", "magnet", "lever", "enabled"};

    private TiledMap                   tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private MapLayers                  layers;

    HashMap<String, TiledElements>     tiledElements;

    private Level                      level;

    public TiledMapLoader(Level level, String path){
        this.level                      = level;
        this.tiledMap                   = new TmxMapLoader().load(path);
        this.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap, BaseElement.WORLD_TO_SCREEN/16f);

        this.layers                     = this.tiledMap.getLayers();

        this.tiledElements              = new HashMap<>();

        if (this.tiledMap.getProperties().get("index") != null) {
            level.setLevelIndex(Integer.parseInt((String) this.tiledMap.getProperties().get("index")));
        } else
            throw new NullPointerException("Level index not initialized, please update it in the editor");

        try {
            int maxDeath = Integer.parseInt((String) this.tiledMap.getProperties().get("deaths"));
            int maxTime  = Integer.parseInt((String) this.tiledMap.getProperties().get("time"));
            int bronze   = Integer.parseInt((String) this.tiledMap.getProperties().get("bronze"));
            int silver   = Integer.parseInt((String) this.tiledMap.getProperties().get("silver"));
            int gold     = Integer.parseInt((String) this.tiledMap.getProperties().get("gold"));

            level.setScoreHandler(new ScoreHandler(maxDeath, maxTime, bronze, silver, gold));

        } catch (Exception e){
            throw new NullPointerException("Impossible to initialize score system for level " + level.getLevelIndex());
        }
    }

    public void loadMap() {
        try {
            HashMap<Integer, BaseEnabledElement> enabledElements = new HashMap<>(); // the elements which can be activated / deactivated

            /**
             * Scanning all the layer name's contained in the name maps to check if the level has those layers
             */
            for (String name : this.maps) {
                if (this.layers.get(name) != null) {
                    addElement(name);
                    if (!name.equals("static"))
                        this.layers.get(name).setVisible(false);
                }
            }

            for (String name : this.tiledElements.keySet()) {
                this.tiledElements.get(name).loadElements();
                this.tiledElements.get(name).addEnabledElements(enabledElements);
            }

            if (this.tiledElements.containsKey("lever"))
                ((TiledLever) tiledElements.get("lever")).bindElements(enabledElements);

        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error has occurred, please see the generated log.");
            SaveManager.writeLog("log_level", e);
        }
    }

    /**
     * Method to add a TiledElement to the HashMap of the TiledElements of the Level
     * @param layerName the name of the layer to check
     */
    private void addElement(String layerName) {
        if (containsColor(layerName)) {
            this.tiledElements.put(layerName, new TiledColorPlatforms(level, (TiledMapTileLayer) this.layers.get(layerName), layerName));
        } else {
            switch (layerName) {
                case "static":
                    this.tiledElements.put(layerName, new TiledPlatforms(level, (TiledMapTileLayer) this.layers.get("static")));
                    break;
                case "moving":
                    this.tiledElements.put(layerName, new TiledMovingPlatforms(level, this.layers.get("moving")));
                    break;
                case "deadly":
                    this.tiledElements.put(layerName, new TiledDeadlyPlatforms(level, (TiledMapTileLayer) this.layers.get("deadly")));
                    break;
                case "character":
                    TiledElements.setCharacter(level, this.layers.get("character"));
                    break;
                case "exit":
                    this.tiledElements.put(layerName, new TiledEndObjects(level, this.layers.get("exit")));
                    break;
                case "teleporter":
                    this.tiledElements.put(layerName, new TiledTeleporters(level, this.layers.get("teleporter")));
                    break;
                case "falling":
                    this.tiledElements.put(layerName, new TiledFallingPlatforms(level, this.layers.get("falling")));
                    break;
                case "windblower":
                    this.tiledElements.put(layerName, new TiledWindBlowers(level, this.layers.get("windblower")));
                    break;
                case "enemies":
                    this.tiledElements.put(layerName, new TiledEnemies(level, this.layers.get("enemies")));
                    break;
                case "bouncing":
                    this.tiledElements.put(layerName, new TiledBouncingColorPlatform(level, this.layers.get("bouncing")));
                    break;
                case "notice":
                    this.tiledElements.put(layerName, new TiledNotices(level, this.layers.get("notice")));
                    break;
                case "magnet":
                    this.tiledElements.put(layerName, new TiledColoredMagnet(level, this.layers.get("magnet")));
                    break;
                case "lever":
                    this.tiledElements.put(layerName, new TiledLever(level, this.layers.get("lever")));
                    break;
                case "enabled":
                    this.tiledElements.put(layerName, new TiledEnabled(level, this.layers.get("enabled")));
                    break;
            }
        }
    }

    /**
     * Method to know if the text contains the name of a color
     * @param name the name of the layer to check
     * @return the result as a boolean
     */
    private boolean containsColor(String name){
        return name.contains("red") || name.contains("blue") || name.contains("yellow")
                || name.contains("purple") || name.contains("orange") || name.contains("green")
                || name.contains("black");
    }

    public TiledMap getTiledMap() {
        return this.tiledMap;
    }

    public OrthogonalTiledMapRenderer getOrthogonalTiledMapRenderer() {
        return this.orthogonalTiledMapRenderer;
    }

    /**
     * Method to change the opacity of a specific layer
     * @param layer the name of the layer
     */
    public void changeOpacity(String layer){
        for(String name : maps){
            if(name.contains(layer) && this.tiledElements.containsKey(name))
                this.tiledElements.get(name).inverseOpacity();
        }
    }
}
