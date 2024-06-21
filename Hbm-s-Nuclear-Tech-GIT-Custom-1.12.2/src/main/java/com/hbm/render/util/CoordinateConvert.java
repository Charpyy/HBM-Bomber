package com.hbm.render.util;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.ForgeChunkManager;


public class CoordinateConvert {

        /**
         * Converts block coordinates to chunk coordinates.
         * @param x The X coordinate in block units.
         * @param z The Z coordinate in block units.
         * @return An array with chunkX and chunkZ.
         */
        public static int[] blockToChunk(int x, int z) {
            int chunkX = x >> 4;
            int chunkZ = z >> 4;
            return new int[]{chunkX, chunkZ};
        }

        /**
         * Converts chunk coordinates to block coordinates.
         * @param chunkX The X coordinate in chunk units.
         * @param chunkZ The Z coordinate in chunk units.
         * @return An array with blockX and blockZ representing the starting block of the chunk.
         */
        public static int[] chunkToBlock(int chunkX, int chunkZ) {
            int blockX = chunkX << 4;
            int blockZ = chunkZ << 4;
            return new int[]{blockX, blockZ};
    }

    public static void generateChunkAt(World world, int x, int z) {
        if (world.getChunkProvider() instanceof ChunkProviderServer) {
            ChunkProviderServer chunkProviderServer = (ChunkProviderServer) world.getChunkProvider();
            chunkProviderServer.provideChunk(x, z);
            //System.out.println("CHUNK généré: "+ x + " "+z);
        }
    }
}
