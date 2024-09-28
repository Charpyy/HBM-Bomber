package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityMachineAmgen extends TileEntityLoadedBase implements ITickable, IEnergyGenerator {

	public long power;
	public long maxPower = 500;
	public int age=0;
	public int powergen=0;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			long prevPower = power;

			if(this.getBlockType() == ModBlocks.machine_amgen) {
				power += RadiationSavedData.getData(world).getRadNumFromCoord(pos);
				
				RadiationSavedData.decrementRad(world, pos, 5F);
				
			} else {
				if(age==10){
					age=0;
					powergen=0;
					Block b = world.getBlockState(pos.down()).getBlock();
					if(b == ModBlocks.geysir_water) {
						powergen += 75;
					} else if(b == ModBlocks.geysir_chlorine) {
						powergen += 100;
					} else if(b == ModBlocks.geysir_vapor) {
						powergen += 50;
					} else if(b == ModBlocks.geysir_nether) {
						powergen += 500;
					} else if(b == Blocks.LAVA) {
						powergen += 100;
					} else if(b == Blocks.FLOWING_LAVA) {
						powergen += 25;
					}

					b = world.getBlockState(pos.up()).getBlock();

					if(b == Blocks.LAVA) {
						powergen += 100;

					} else if(b == Blocks.FLOWING_LAVA) {
						powergen += 25;
					}
				}
				age++;
				power+=powergen;
			}
			
			if(power > maxPower)
				power = maxPower;

			this.sendPower(world, pos);
			if(prevPower != power)
				markDirty();
		}
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}
}
