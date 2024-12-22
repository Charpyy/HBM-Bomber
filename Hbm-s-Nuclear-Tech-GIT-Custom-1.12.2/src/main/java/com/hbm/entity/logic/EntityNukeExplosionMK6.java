
package com.hbm.entity.logic;

import com.hbm.config.BombConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityFalloutUnderGround;
import com.hbm.entity.mob.EntityGlowingOne;
import com.hbm.explosion.ExplosionLargeRay;
import com.hbm.explosion.ExplosionNukeRayBatched;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

public class EntityNukeExplosionMK6 extends Entity implements IChunkLoader {
	//How many rays are calculated per tick
	public int radius;

	public boolean mute = false;
	public boolean spawnFire = false;

	private Ticket loaderTicket;

	ExplosionLargeRay explosion;



	public EntityNukeExplosionMK6(World world) {
		super(world);
	}

	public EntityNukeExplosionMK6(World world, int radius) {
		super(world);
		this.radius = radius;
	}

	@Override
	public void onUpdate() {
		if(world.isRemote) return;

		if(radius == 0 || !CompatibilityConfig.isWarDim(world)) {
			this.clearLoadedChunks();
			this.unloadMainChunk();
			this.setDead();
			return;
		}
		//load own chunk
		loadMainChunk();

		float fire, blast;
		fire = blast = 0;

		//radiate until there is fallout rain

		if(ticksExisted < 2400 && ticksExisted % 10 == 0){
			fire = (spawnFire ? 10F: 2F) * (float)Math.pow(radius, 3) * (float)Math.pow(0.5, this.ticksExisted*(spawnFire?0.005:0.025));
			blast = (float)Math.pow(radius, 3) * 0.2F;
			ContaminationUtil.radiate(world, this.posX, this.posY, this.posZ, Math.min(1000, radius * 2), 0, 0F, fire, blast, this.ticksExisted * 1.5F);
		}
		//make some noise
		if(!mute) {
			if(this.radius > 30){
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, this.radius * 0.05F, 0.8F + this.rand.nextFloat() * 0.2F);
				if(rand.nextInt(5) == 0)
					this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, this.radius * 0.05F, 0.8F + this.rand.nextFloat() * 0.2F);
			}else{
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, Math.max(2F, this.radius * 0.1F), 0.8F + this.rand.nextFloat() * 0.2F);
			}
		}

		//Create Explosion Rays
		if(explosion == null) {
			explosion = new ExplosionLargeRay(world, (int) this.posX, (int) this.posY, (int) this.posZ, this.radius);
		}

		//Calculating crater
		if(!explosion.isAusf3Complete) {
			explosion.collectTip(BombConfig.mk5);

			//Excecuting destruction
		} else if(explosion.perChunk.size() > 0) {
			explosion.processChunk(BombConfig.mk5);

		} else {

			EntityFalloutRain falloutRain = new EntityFalloutRain(this.world);
			falloutRain.doFallout = false;
			falloutRain.doFlood = false;
			falloutRain.doDrain = false;
			falloutRain.posX = this.posX;
			falloutRain.posY = this.posY;
			falloutRain.posZ = this.posZ;
			if(spawnFire)
				falloutRain.spawnFire = true;
			falloutRain.setScale((int) ((this.radius * 2.5F) * BombConfig.falloutRange * 0.01F), this.radius+4);
			this.world.spawnEntity(falloutRain);

			this.clearLoadedChunks();
			unloadMainChunk();
			this.setDead();
		}
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, world, Type.ENTITY));
	}

	@Override
	public void init(Ticket ticket) {
		if(!world.isRemote && ticket != null) {

			if(loaderTicket == null) {
				loaderTicket = ticket;
				loaderTicket.bindEntity(this);
				loaderTicket.getModData();
			}

			ForgeChunkManager.forceChunk(loaderTicket, new ChunkPos(chunkCoordX, chunkCoordZ));
		}
	}


	List<ChunkPos> loadedChunks = new ArrayList<ChunkPos>();
	@Override
	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!world.isRemote && loaderTicket != null)
		{
			for(ChunkPos chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}

			loadedChunks.clear();
			loadedChunks.add(new ChunkPos(newChunkX, newChunkZ));
			loadedChunks.add(new ChunkPos(newChunkX + 1, newChunkZ + 1));
			loadedChunks.add(new ChunkPos(newChunkX - 1, newChunkZ - 1));
			loadedChunks.add(new ChunkPos(newChunkX + 1, newChunkZ - 1));
			loadedChunks.add(new ChunkPos(newChunkX - 1, newChunkZ + 1));
			loadedChunks.add(new ChunkPos(newChunkX + 1, newChunkZ));
			loadedChunks.add(new ChunkPos(newChunkX, newChunkZ + 1));
			loadedChunks.add(new ChunkPos(newChunkX - 1, newChunkZ));
			loadedChunks.add(new ChunkPos(newChunkX, newChunkZ - 1));

			for(ChunkPos chunk : loadedChunks) {
				ForgeChunkManager.forceChunk(loaderTicket, chunk);
			}
		}
	}

	public void clearLoadedChunks() {
		if(!world.isRemote && loaderTicket != null && loadedChunks != null) {
			for(ChunkPos chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}
		}
	}

	private ChunkPos mainChunk;
	public void loadMainChunk() {
		if(!world.isRemote && loaderTicket != null && this.mainChunk == null) {
			this.mainChunk = new ChunkPos((int) Math.floor(this.posX / 16D), (int) Math.floor(this.posZ / 16D));
			ForgeChunkManager.forceChunk(loaderTicket, this.mainChunk);
		}
	}
	public void unloadMainChunk() {
		if(!world.isRemote && loaderTicket != null && this.mainChunk != null) {
			ForgeChunkManager.unforceChunk(loaderTicket, this.mainChunk);
		}
	}

	private static boolean isWet(World world, BlockPos pos){
		Biome b = world.getBiome(pos);
		return b.getTempCategory() == Biome.TempCategory.OCEAN || b.isHighHumidity() || b == Biomes.BEACH || b == Biomes.OCEAN || b == Biomes.RIVER  || b == Biomes.DEEP_OCEAN || b == Biomes.FROZEN_OCEAN || b == Biomes.FROZEN_RIVER || b == Biomes.STONE_BEACH || b == Biomes.SWAMPLAND;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		radius = nbt.getInteger("radius");
		spawnFire = nbt.getBoolean("spawnFire");
		mute = nbt.getBoolean("mute");
		if(explosion == null) {
			explosion = new ExplosionLargeRay(world, (int) this.posX, (int) this.posY, (int) this.posZ, this.radius);
		}
		explosion.readEntityFromNBT(nbt);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("radius", radius);
		nbt.setBoolean("spawnFire", spawnFire);
		nbt.setBoolean("mute", mute);
		if(explosion != null) {
			explosion.writeEntityToNBT(nbt);
		}
	}

	public static EntityNukeExplosionMK6 statFac(World world, int r, double x, double y, double z) {
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized explosion at " + x + " / " + y + " / " + z + " with radius " + r + "!");

		if(r == 0)
			r = 25;

		EntityNukeExplosionMK6 mk6 = new EntityNukeExplosionMK6(world,r);


		mk6.setPosition(x, y, z);
		return mk6;
	}

	public static EntityNukeExplosionMK6 statFacFire(World world, int r, double x, double y, double z) {

		EntityNukeExplosionMK6 mk6 = statFac(world, r, x, y ,z);
		mk6.spawnFire = true;
		return mk6;
	}



	public EntityNukeExplosionMK6 mute() {
		this.mute = true;
		return this;
	}
}