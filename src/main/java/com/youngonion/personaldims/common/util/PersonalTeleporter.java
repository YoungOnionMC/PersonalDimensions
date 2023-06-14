package com.youngonion.personaldims.common.util;

import com.youngonion.personaldims.common.portal.TileEntityPortal;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class PersonalTeleporter extends Teleporter {

    BlockPos pos;
    private TileEntityPortal portal;

    public PersonalTeleporter(WorldServer worldIn, BlockPos pos) {
        super(worldIn);
        this.pos = pos;
    }

    public PersonalTeleporter(WorldServer worldIn, TileEntityPortal entityIn) {
        super(worldIn);
        this.pos = entityIn.getPos();
        this.portal = entityIn;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        this.placeInExistingPortal(entityIn, rotationYaw);
    }

    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
        if(portal != null) {
            // they do math to find the new yaw if the portal doesnt already exist :thonk:
        }

        entityIn.setLocationAndAngles(pos.getX() + 0.5f, pos.getY() + 0.1f, pos.getZ() + 0.5f, 90.0f, 0.0f);
        entityIn.motionX = 0.0f;
        entityIn.motionY = 0.0f;
        entityIn.motionZ = 0.0f;
        return true;
    }

    @Override
    public boolean makePortal(Entity entityIn) {
        return true;
    }
}


