package com.hbm.explosion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.BitSet;
import java.util.Map.Entry;
import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.render.amlfrom1710.Vec3;

import com.hbm.tileentity.machine.rbmk.RBMKDials;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ExplosionLargeRay {

	public HashMap<ChunkPos, BitSet> perChunk = new HashMap<ChunkPos, BitSet>();
	public List<ChunkPos> orderedChunks = new ArrayList();
	private CoordComparator comparator = new CoordComparator();
	public boolean isContained = true;
	int posX;
	int posY;
	int posZ;
	World world;

	int radius;

	int gspNumMax;
	int gspNum;
	double gspX;
	double gspY;

	private static final int maxY = 255;
	private static final int minY = 0;

	public boolean isAusf3Complete = false;
	public int rayCheckInterval = 100;

	public ExplosionLargeRay(World world, int x, int y, int z, int radius) {
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.radius = radius;

		// Total number of points
		this.gspNumMax = (int)(RBMKDials.getGamerule(this.world,RBMKDials.KEY_MK6_RAY_MODIF) * Math.PI * Math.pow(this.radius, 2)); ///ancien = 2.5
		this.gspNum = 1;

		// The beginning of the generalized spiral points
		this.gspX = Math.PI;
		this.gspY = 0.0;
		this.rayCheckInterval = 10000/radius;
	}

	private void generateGspUp(){
		if (this.gspNum < this.gspNumMax) {
			int k = this.gspNum + 1;
			double hk = -1.0 + 2.0 * (k - 1.0) / (this.gspNumMax - 1.0);
			this.gspX = Math.acos(hk);

			double prev_lon = this.gspY;
			double lon = prev_lon + 3.6 / Math.sqrt(this.gspNumMax) / Math.sqrt(1.0 - hk * hk);
			this.gspY = lon % (Math.PI * 2);
		} else {
			this.gspX = 0.0;
			this.gspY = 0.0;
		}
		this.gspNum++;
	}

	// Get Cartesian coordinates for spherical coordinates
	// 90 X-Axis rotation for more efficient chunk scanning
	private Vec3 getSpherical2cartesian(){
		double dx = Math.sin(this.gspX) * Math.cos(this.gspY);
		double dy = Math.sin(this.gspX) * Math.sin(this.gspY);
		double dz = Math.cos(this.gspX);
		return Vec3.createVectorHelper(dx, dy, dz);
	}

	public void addPos(int x, int y, int z){
		chunk = new ChunkPos(x >> 4, z >> 4);
		BitSet hitPositions = perChunk.get(chunk);

		if(hitPositions == null) {
			hitPositions = new BitSet(65536);
			perChunk.put(chunk, hitPositions); //we re-use the same pos instead of using individualized per-chunk ones to save on RAM
		}
		hitPositions.set(((255-y) << 8) + ((x - chunk.getXStart()) << 4) + (z - chunk.getZStart()));
	}

	int age = 0;
	public void collectTip(int time) {
		if(!CompatibilityConfig.isWarDim(world)){
			isAusf3Complete = true;
			return;
		}
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		long raysProcessed = 0;
		long start = System.currentTimeMillis();
		float actres;
		float[] pseudoresistance =new float[radius+11];
		boolean[] isair =new boolean[radius+11];
		IBlockState blockState;
		Block b;
		int iX, iY, iZ;
		double rayStrength;
		Vec3 vec;
		age++;
		if(age == 120){
			System.out.println("NTM C "+raysProcessed+" "+Math.round(10000D * 100D*gspNum/(double)gspNumMax)/10000D+"% "+gspNum+"/"+gspNumMax);
			age = 0;
		}
		double dev1= RBMKDials.getGamerule(this.world,RBMKDials.KEY_MK6_GEN_MODIF);
		float dev2= (float) RBMKDials.getGamerule(this.world,RBMKDials.KEY_MK6_BACK_MODIF);
		float dev3= (float) RBMKDials.getGamerule(this.world,RBMKDials.KEY_MK6_SELF_MODIF);
		while(this.gspNumMax >= this.gspNum){
			// Get Cartesian coordinates for spherical coordinates
			vec = this.getSpherical2cartesian();

			rayStrength = Math.pow(radius,3) * dev1; //changer cste
			//calculating the pseudoresistance
			for(int i = radius+10; i>=0; i--){
				iY = (int) Math.floor(posY + (vec.yCoord * i));

				if(iY < minY || iY > maxY){
					pseudoresistance[i] = 0;
					continue;
				}

				iX = (int) Math.floor(posX + (vec.xCoord * i));
				iZ = (int) Math.floor(posZ + (vec.zCoord * i));
				pos.setPos(iX, iY, iZ);
				blockState = world.getBlockState(pos);
				b = blockState.getBlock();
				actres=getNukeResistance(blockState, b);
				if(i==radius+10){
					pseudoresistance[i]=actres;
				}else{
					//on prend en gros le truc le plus faible : la resistance du bloc ou celle de son accroche (on considère qu'il est 10% attaché par lui même)
					pseudoresistance[i]=2/(1/actres+1/(dev2*actres+dev3*pseudoresistance[i+1]));
					isair[i]= b == Blocks.AIR;
				}
			}
			//Finding the end of the ray
			for(int r = 0; r <= radius; r ++) {

				iY = (int) Math.floor(posY + (vec.yCoord * r));

				if(iY < minY || iY > maxY){
					isContained = false;
					break;
				}

				iX = (int) Math.floor(posX + (vec.xCoord * r));
				iZ = (int) Math.floor(posZ + (vec.zCoord * r));


				pos.setPos(iX, iY, iZ);
				rayStrength -= pseudoresistance[r]*Math.pow(((double) r) / ((double) radius),2);


				//save block positions in to-destroy-boolean[] until rayStrength is 0
				if(rayStrength > 0){
					if(!isair[r]) {
						//all-air chunks don't need to be buffered at all
						addPos(iX, iY, iZ);
					}
					if(r >= radius) {
						isContained = false;
					}
				} else {
					break;
				}
			}

			// Raise one generalized spiral points
			this.generateGspUp();
			raysProcessed++;
			if(raysProcessed % rayCheckInterval == 0 && System.currentTimeMillis()+1 > start + time) {
				return;
			}
		}
		orderedChunks.addAll(perChunk.keySet());
		orderedChunks.sort(comparator);

		isAusf3Complete = true;
	}

	public static float getNukeResistance(IBlockState blockState, Block b) {
		if(blockState.getMaterial().isLiquid()){
			return 0.1F;
		} else {
			if(b == Blocks.SANDSTONE) return 4F;
			if(b == Blocks.OBSIDIAN) return 18F;
			return b.getExplosionResistance(null);
		}
	}

	/** little comparator for roughly sorting chunks by distance to the center */
	public class CoordComparator implements Comparator<ChunkPos> {

		@Override
		public int compare(ChunkPos o1, ChunkPos o2) {

			int chunkX = ExplosionLargeRay.this.posX >> 4;
			int chunkZ = ExplosionLargeRay.this.posZ >> 4;

			int diff1 = Math.abs((chunkX - (int) (o1.getXStart() >> 4))) + Math.abs((chunkZ - (int) (o1.getZStart() >> 4)));
			int diff2 = Math.abs((chunkX - (int) (o2.getXStart() >> 4))) + Math.abs((chunkZ - (int) (o2.getZStart() >> 4)));

			return diff1 > diff2 ? 1 : diff1 < diff2 ? -1 : 0;
		}
	}

	public void processChunk(int time){
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() < start + time){
			processChunkBlocks(start, time);
		}
	}

	BitSet hitArray;
	ChunkPos chunk;
	boolean needsNewHitArray = true;
	int index = 0;

	public void processChunkBlocks(long start, int time){
		if(!CompatibilityConfig.isWarDim(world)){
			this.perChunk.clear();
		}
		if(this.perChunk.isEmpty()) return;
		if(needsNewHitArray){
			chunk = orderedChunks.get(0);
			hitArray = perChunk.get(chunk);
			index = hitArray.nextSetBit(0);
			needsNewHitArray = false;
		}

		int chunkX = chunk.getXStart();
		int chunkZ = chunk.getZStart();

		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int blocksRemoved = 0;
		while(index > -1) {
			pos.setPos(((index >> 4) % 16) + chunkX, 255 - (index >> 8), (index % 16) + chunkZ);
			world.setBlockToAir(pos);
			index = hitArray.nextSetBit(index+1);
			blocksRemoved++;
			if(blocksRemoved % 256 == 0 && System.currentTimeMillis()+1 > start + time){
				break;
			}
		}

		if(index < 0){
			perChunk.remove(chunk);
			orderedChunks.remove(0);
			needsNewHitArray = true;
		}
	}

	public void readEntityFromNBT(NBTTagCompound nbt) {
		radius = nbt.getInteger("radius");
		posX = nbt.getInteger("posX");
		posY = nbt.getInteger("posY");
		posZ = nbt.getInteger("posZ");
		gspNumMax = (int)(2.5 * Math.PI * Math.pow(radius, 2));
		rayCheckInterval = 10000/radius;

		if(nbt.hasKey("gspNum")){
			gspNum = nbt.getInteger("gspNum");
			isAusf3Complete = nbt.getBoolean("f3");
			isContained = nbt.getBoolean("isContained");

			int i = 0;
			while(nbt.hasKey("chunks"+i)){
				NBTTagCompound c = (NBTTagCompound)nbt.getTag("chunks"+i);

				perChunk.put(new ChunkPos(c.getInteger("cX"), c.getInteger("cZ")), BitSet.valueOf(getLongArray((NBTTagLongArray)c.getTag("cB"))));
				i++;
			}
			if(isAusf3Complete){
				orderedChunks.addAll(perChunk.keySet());
				orderedChunks.sort(comparator);
			}
		}
	}

	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("radius", radius);
		nbt.setInteger("posX", posX);
		nbt.setInteger("posY", posY);
		nbt.setInteger("posZ", posZ);

		if(true){//BombConfig.enableNukeNBTSaving
			nbt.setInteger("gspNum", gspNum);
			nbt.setBoolean("f3", isAusf3Complete);
			nbt.setBoolean("isContained", isContained);

			int i = 0;
			for(Entry<ChunkPos, BitSet> e : perChunk.entrySet()){
				NBTTagCompound c = new NBTTagCompound();
				c.setInteger("cX", e.getKey().x);
				c.setInteger("cZ", e.getKey().z);
				c.setTag("cB", new NBTTagLongArray(e.getValue().toLongArray()));
				nbt.setTag("chunks"+i, c.copy());
				i++;
			}
		}
	}

	// Who tf forgot to add a way to retrieve the long array from NBTTagLongArray??
	public static long[] getLongArray(NBTTagLongArray nbt) {
		return ObfuscationReflectionHelper.getPrivateValue(NBTTagLongArray.class, nbt, 0);
	}
}