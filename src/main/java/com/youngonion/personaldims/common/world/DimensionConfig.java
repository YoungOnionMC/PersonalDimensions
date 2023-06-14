package com.youngonion.personaldims.common.world;

import net.minecraft.world.gen.FlatLayerInfo;

import java.util.ArrayList;

public class DimensionConfig {

    private int skyColor = 0xff0000;
    private boolean hasWeather = false;
    private boolean nightTime = false;
    private boolean generateVegetation = false;
    private boolean generateTrees = false;
    private boolean generateStructures = false;
    private ArrayList<FlatLayerInfo> layers;

    private boolean needsSaving = true;

    public DimensionConfig() {}

    public boolean getWeather() {
        return hasWeather;
    }

    public void setWeather(boolean weather) {
        hasWeather = weather;
    }

    public boolean getNightTime() {
        return nightTime;
    }

    public void setNightTime(boolean night) {
        nightTime = night;
    }

    public boolean getVeggies() {
        return generateVegetation;
    }

    public void setVeggies(boolean veggies) {
        generateVegetation = veggies;
    }

    public boolean getTrees() {
        return generateTrees;
    }

    public void setTrees(boolean trees) {
        generateTrees = trees;
    }
}
