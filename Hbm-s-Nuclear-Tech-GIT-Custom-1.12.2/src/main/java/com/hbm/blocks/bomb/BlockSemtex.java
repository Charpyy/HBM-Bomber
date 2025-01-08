package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.interfaces.IBomb;

import com.hbm.interfaces.IResponsiveBomb;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;
import com.openwar.hbmapi.CSVManager.CSVReader;
import com.openwar.hbmapi.CSVManager.HBMController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class BlockSemtex extends Block implements IBomb, IResponsiveBomb {
	
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	
	public BlockSemtex(Material mat, String s) {
		super(mat);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public Block setSoundType(SoundType sound){
		return super.setSoundType(sound);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer).getOpposite()), 2);
	}
	

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn){
		this.explode(worldIn, pos);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
		if(worldIn.isBlockIndirectlyGettingPowered(pos) > 0){
			this.explode(worldIn, pos);
		}
	}
	static class BombDetonation extends HBMController.Action<CSVReader.TrooleanResponse> {
		World world;
		BlockPos pos;
		public BombDetonation(World world, BlockPos position){
			this.world=world;
			this.pos=position;
		}

		@Override
		public void execute(CSVReader.TrooleanResponse response) {
			MainRegistry.logger.log(Level.INFO, "[BOMB] the answer was"+(response.getValue()?"YES":"NO"));
			if(response.getValue()){
				if(response.isTroll()){
					MainRegistry.logger.log(Level.INFO, "[BOMB] have a troll redirect to "+response.getTrollX()+", "+response.getTrollZ());
				}else{
					ExplosionLarge.explode(world, pos.getX(), pos.getY(), pos.getZ(), 40, true, false, false);
					world.setBlockToAir(pos);
				}
			}
		}
	}
	@Override
	public void explode(World world, BlockPos pos) {
		if(!world.isRemote) {
			//new ExplosionNT(world, null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 50).overrideResolution(64).explode();
			//ExplosionLarge.spawnParticles(world, pos.getX(), pos.getY(), pos.getZ(), ExplosionLarge.cloudFunction(15));
		}
	}

	public void explodeResponsive(World world, BlockPos pos, EntityLivingBase responsive){
		BombDetonation action=new BombDetonation(world,pos);
		String uniqueId = String.valueOf(responsive.getUniqueID());
		HBMController.generalController.askRP(uniqueId, "SEMTEX", 5, pos.getX(), pos.getZ(),action);
	}
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);
        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
	   return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
}