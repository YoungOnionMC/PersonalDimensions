package com.youngonion.personaldims.common.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;

public class PersonalWorldProvider extends WorldProvider {
    DimensionConfig config;

    @Override
    public void setDimension(int dim) {
        super.setDimension(dim);
    }

    @Override
    public DimensionType getDimensionType() {
        return null;
    }
}
