package com.color.game.levels.mapcreator;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.color.game.assets.SaveManager;
import com.color.game.levels.Level;
import com.color.game.levels.ScoreHandler;
import com.color.game.levels.mapcreator.elements.TiledElements;
import com.color.game.levels.mapcreator.elements.objects.*;
import com.color.game.levels.mapcreator.elements.objects.TiledBouncingColorPlatform;
import com.color.game.levels.mapcreator.elements.statics.TiledColorPlatforms;
import com.color.game.levels.mapcreator.elements.statics.TiledDeadlyPlatforms;
import com.color.game.levels.mapcreator.elements.statics.TiledPlatforms;

import javax.swing.*;
import java.util.ArrayList;

public class TiledMapLoader {

    private String[] maps = new String[]{"static", "moving", "deadly", "red", "red_deactivated",
                                         "blue", "blue_deactivated", "yellow", "yellow_deactivated",
                                         "purple", "purple_deactivated", "orange", "orange_deactivated",
                                         "green", "green_deactivated", "black", "black_deactivated",
                                         "character", "exit", "teleporter", "falling", "windblower",
                                         "enemies", "bouncing", "notice", "magnet"};

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private MapLayers layers;

    ArrayList<TiledElements> tiledElements;
    private Level level;
    private Integer levelIndex;

    public TiledMapLoader(Level level, String path){

        this.level = level;
        this.tiledMap = new TmxMapLoader().load(path);
        this.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.layers = tiledMap.getLayers();

        this.tiledElements = new ArrayList<>();

        if(tiledMap.getProperties().get("index") != null){
            level.setLevelIndex(Integer.parseInt((String) tiledMap.getProperties().get("index")));
            this.levelIndex = level.getLevelIndex();

        }else{
            throw new NullPointerException("Level index not initialized, please update it in the editor");
        }

        ScoreHandler score;

        try{

            int maxDeath = Integer.parseInt((String) tiledMap.getProperties().get("deaths"));
            int maxTime = Integer.parseInt((String) tiledMap.getProperties().get("time"));
            int bronze = Integer.parseInt((String) tiledMap.getProperties().get("bronze"));
            int silver = Integer.parseInt((String) tiledMap.getProperties().get("silver"));
            int gold = Integer.parseInt((String) tiledMap.getProperties().get("gold"));

            score = new ScoreHandler(maxDeath, maxTime, bronze, silver, gold);
            level.setScoreHandler(score);

        }catch (Exception e){
            throw new NullPointerException("Impossible to initialize score system for level "+level.getLevelIndex());
        }
    }

    public void loadMap() {
        try {

            for(String s : maps){
                if(this.layers.get(s) != null){
                    addElement(s);
                }
            }

            for (TiledElements te : tiledElements) {
                te.loadElements();
            }

        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error has occurred, please see the generated log.");
            SaveManager.writeLog("log_level", e);
        }
    }

    private void addElement(String layerName){
        if(containsColor(layerName)){
            this.tiledElements.add(new TiledColorPlatforms(level, (TiledMapTileLayer) this.layers.get(layerName), layerName));
        }else {
            switch (layerName) {
                case "static":
                    this.tiledElements.add(new TiledPlatforms(level, (TiledMapTileLayer) this.layers.get("static")));
                    break;
                case "moving":
                    this.tiledElements.add(new TiledMovingPlatforms(level, this.layers.get("moving")));
                    break;
                case "deadly":
                    this.tiledElements.add(new TiledDeadlyPlatforms(level, (TiledMapTileLayer) this.layers.get("deadly")));
                    break;
                case "character":
                    TiledElements.setCharacter(level, this.layers.get("character"));
                    break;
                case "exit":
                    this.tiledElements.add(new TiledEndObjects(level, this.layers.get("exit"), levelIndex));
                    break;
                case "teleporter":
                    this.tiledElements.add(new TiledTeleporters(level, this.layers.get("teleporter")));
                    break;
                case "falling":
                    this.tiledElements.add(new TiledFallingPlatforms(level, this.layers.get("falling")));
                    break;
                case "windblower":
                    this.tiledElements.add(new TiledWindBlowers(level, this.layers.get("windblower")));
                    break;
                case "enemies":
                    this.tiledElements.add(new TiledEnemies(level, this.layers.get("enemies")));
                    break;
                case "bouncing":
                    this.tiledElements.add(new TiledBouncingColorPlatform(level, this.layers.get("bouncing")));
                    break;
                case "notice":
                    this.tiledElements.add(new TiledNotices(level, this.layers.get("notice")));
                    break;
                case "magnet":
                    this.tiledElements.add(new TiledColoredMagnet(level, this.layers.get("magnet")));
                    break;
            }
        }
    }

    private boolean containsColor(String s){
        return s.contains("red") || s.contains("blue") || s.contains("yellow")
                || s.contains("purple") || s.contains("orange") || s.contains("green")
                || s.contains("black");
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public OrthogonalTiledMapRenderer getOrthogonalTiledMapRenderer() {
        return orthogonalTiledMapRenderer;
    }


}
