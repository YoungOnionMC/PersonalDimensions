package com.youngonion.personaldims.common.portal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class PortalItemEntity extends EntityItem {
    public PortalItemEntity(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public PortalItemEntity(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    public PortalItemEntity(World worldIn) {
        super(worldIn);
    }

    public PortalItemEntity(World worldIn, Entity location, ItemStack stack) {
        super(worldIn, location.posX, location.posY, location.posZ, stack);
        this.motionX = location.motionX;
        this.motionY = location.motionY;
        this.motionZ = location.motionZ;
        this.rotationPitch = location.rotationPitch;
        this.rotationYaw = location.rotationYaw;
        this.setPickupDelay(10);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source.equals(DamageSource.OUT_OF_WORLD))
            return super.attackEntityFrom(source, amount);
        return false;
    }
}
