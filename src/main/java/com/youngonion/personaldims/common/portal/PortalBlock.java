package com.youngonion.personaldims.common.portal;

import com.youngonion.personaldims.PersonalDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PortalBlock extends Block implements ITileEntityProvider {
    public PortalBlock() {
        super(Material.CIRCUITS);
        this.setRegistryName("blockPortal");
        this.setHardness(4.0f);
        this.setResistance(4000.0f);
        this.setCreativeTab(CreativeTabs.FOOD);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPortal();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote) {
            PersonalDimensions.commonProxy.openPortalGUI(worldIn, pos);
            return true;
        }
        TileEntity te = worldIn.getTileEntity(pos);
        if(!(te instanceof TileEntityPortal)) {
            return false;
        }
        if(((TileEntityPortal) te).active && !playerIn.isSneaking() && playerIn instanceof EntityPlayerMP) {
            ((TileEntityPortal) te).TeleportPlayer((EntityPlayerMP)playerIn);
            return true;
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity te = worldIn.getTileEntity(pos);

        if(!(te instanceof TileEntityPortal))
            return;

        if(worldIn.isRemote)
            return;

        if(stack.hasTagCompound()) {
            te.readFromNBT(stack.getTagCompound());
            EntityPlayerMP player = null;
            if(placer instanceof EntityPlayerMP) {
                player = (EntityPlayerMP) placer;
            }
            ((TileEntityPortal) te).linkPortal(player, true);
        }

        te.markDirty();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ItemStack drop = new ItemStack(PersonalDimensions.PORTAL_BLOCK);
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof TileEntityPortal) {
            NBTTagCompound tag = new NBTTagCompound();
            te.writeToNBT(tag);
            tag.removeTag("x");
            tag.removeTag("y");
            tag.removeTag("z");
            tag.removeTag("facing");
            drop.setTagCompound(tag);
        }

        drops.add(drop);
        super.getDrops(drops, world, pos, state, 0);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        ItemStack drop = new ItemStack(PersonalDimensions.PORTAL_BLOCK);
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof TileEntityPortal) {
//            NBTTagCompound tag = new NBTTagCompound();
//            te.writeToNBT(tag);
//            tag.removeTag("x");
//            tag.removeTag("y");
//            tag.removeTag("z");
//            tag.removeTag("facing");
//            drop.setTagCompound(tag);
        }
        return drop;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if(willHarvest) {
            dropBlockAsItem(world, pos, state, 0);
        }
        super.removedByPlayer(state, world, pos, player, willHarvest);
        return false;
    }


}
