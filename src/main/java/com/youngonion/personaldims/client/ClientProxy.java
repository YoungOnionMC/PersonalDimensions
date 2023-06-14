package com.youngonion.personaldims.client;

import com.youngonion.personaldims.common.CommonProxy;
import com.youngonion.personaldims.common.portal.TileEntityPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

    @Override
    public void openPortalGUI(World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if(!(te instanceof TileEntityPortal)) {
            return;
        }
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if(!((TileEntityPortal) te).active && player.world.provider.getDimension() != 0) {
            System.out.println("must be in overworld fool");
            return;
        }

        if(!((TileEntityPortal) te).active) {
            Minecraft.getMinecraft().displayGuiScreen(new EditWorldGUI((TileEntityPortal)te));
        }
    }
}
