package com.youngonion.personaldims.common;

import com.youngonion.personaldims.PersonalDimensions;
import com.youngonion.personaldims.common.world.DimensionConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class CommonProxy {

    protected final ArrayList<DimensionConfig> clientDimensionConfigs = new ArrayList<>();
    protected final ArrayList<DimensionConfig> serverDimensionConfigs = new ArrayList<>();

    public static ArrayList<DimensionConfig> getDimensionConfigs(boolean isClient) {
        return isClient == true ? PersonalDimensions.commonProxy.clientDimensionConfigs : PersonalDimensions.commonProxy.serverDimensionConfigs;
    }

    public void openPortalGUI(World world, BlockPos pos) {}
}
