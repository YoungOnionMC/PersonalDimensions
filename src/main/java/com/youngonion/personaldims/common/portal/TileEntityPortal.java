package com.youngonion.personaldims.common.portal;

import com.youngonion.personaldims.PersonalDimensions;
import com.youngonion.personaldims.common.util.PersonalTeleporter;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class TileEntityPortal extends TileEntity {
    public boolean active = false;

    public int targetDimID = 0;
    public BlockPos targetPos;
    public EnumFacing face, targetFace;

    public void TeleportPlayer(EntityPlayerSP playerSP) {
    }

    public void TeleportPlayer(EntityPlayerMP playerMP) {
        if(world.isRemote || !this.active) {
            return;
        }

        PersonalTeleporter tele = new PersonalTeleporter((WorldServer) world, this);

        playerMP.server.getPlayerList().transferPlayerToDimension(playerMP, targetDimID, tele);
    }

    public void linkPortal(EntityPlayerMP player, boolean createNewPortal) {
        if(!this.active)
            return;

        if(world.isRemote)
            return;

        WorldServer otherWorld = DimensionManager.getWorld(this.targetDimID);
        if(otherWorld == null) {
            DimensionManager.initDimension(this.targetDimID);
            otherWorld = DimensionManager.getWorld(this.targetDimID);
        }
        if(otherWorld == null) {
            System.out.println("you done fucked up!!!!!");
            return;
        }

        int dx = targetPos.getX(), dy = targetPos.getY(), dz = targetPos.getZ();
        kek:
        for(dx = targetPos.getX() - 1; dx <= targetPos.getX() + 1; dx++) {
            for(dy = targetPos.getY() - 1; dy <= targetPos.getY() + 1; dy++) {
                if(dy < 0 || dy > otherWorld.getHeight()) continue;
                for(dz = targetPos.getZ() - 1; dz <= targetPos.getZ() + 1; dz++) {
                    if(!otherWorld.isAirBlock(new BlockPos(dx, dy, dz))) {
                        otherWorld.getChunkProvider().loadChunk(dx << 4, dz << 4);
                    }
                    if(otherWorld.getBlockState(new BlockPos(dx, dy, dz)).getBlock() instanceof PortalBlock) {
                        break kek;
                    }
                }
            }
        }

        BlockPos portalPos = new BlockPos(dx, dy, dz);

        TileEntityPortal otherTE = null;
        if(otherWorld.getBlockState(portalPos).getBlock() == PersonalDimensions.PORTAL_BLOCK) {
            TileEntity te = otherWorld.getTileEntity(portalPos);
            if(te instanceof TileEntityPortal) {
                otherTE = (TileEntityPortal) te;
            }
        } else if (createNewPortal) {
            dx = targetPos.getX();
            dy = targetPos.getY();
            dz = targetPos.getZ();
            otherWorld.setBlockState(new BlockPos(dx, dy, dz), PersonalDimensions.PORTAL_BLOCK.getDefaultState(), 3);
            otherTE = (TileEntityPortal) otherWorld.getTileEntity(new BlockPos(dx, dy, dz));
        }
        if(otherTE != null) {
            otherTE.active = true;
            if(otherTE.targetDimID != world.provider.getDimension()) {
                if(player != null) {
                    // send shit to chat
                }
                return;
            }

            otherTE.targetDimID = world.provider.getDimension();
            otherTE.targetPos = this.pos;
            otherTE.targetFace = face;
            otherTE.markDirty();
            // logging as well
        }

    }

//    @Override
//    public void markDirty() {
//        super.markDirty();
//        if(world != null) {
//            world.markBlockRangeForRenderUpdate();
//        }
//    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setBoolean("active", active);
        tag.setInteger("x", getPos().getX());
        tag.setInteger("y", getPos().getY());
        tag.setInteger("z", getPos().getZ());
        tag.setInteger("facing", face.getIndex());
        tag.setInteger("targetFacing", targetFace.getIndex());
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if(tag.hasKey("active")) {
            this.active = tag.getBoolean("active");
        }
        int x, y, z;
        if(tag.hasKey("x") && tag.hasKey("y") && tag.hasKey("z")) {
            this.setPos(new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")));
        }

        if(tag.hasKey("facing"))
            this.face = EnumFacing.byIndex(tag.getInteger("facing"));

        if(tag.hasKey("targetFacing"))
            this.targetFace = EnumFacing.byIndex(tag.getInteger("targetFacing"));


    }
}
