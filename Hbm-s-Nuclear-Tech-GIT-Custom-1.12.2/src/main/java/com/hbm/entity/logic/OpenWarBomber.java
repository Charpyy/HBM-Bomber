package com.hbm.entity.logic;

import com.hbm.main.MainRegistry;
import com.hbm.render.util.CoordinateConvert;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.ForgeChunkManager;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class OpenWarBomber extends CommandBase {


    @Override
    public String getName() {
        return "sbomber";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/sbomber <x> <y> <z> [world_name] [time_ms] [hp] [damage_true_false]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length < 6) {
            sender.sendMessage(new TextComponentString("Invalid arguments. Usage: " + getUsage(sender)));
            return;
        }

        try {
            boolean damage = Boolean.parseBoolean(args[6]);
            int hp = Integer.parseInt(args[5]);
            int time = Integer.parseInt(args[4]);
            double x = parseDouble(args[0]);
            double y = parseDouble(args[1]);
            double z = parseDouble(args[2]);
            String worldName = args.length > 3 ? args[3] : null;
            World world;

            if (worldName != null) {
                int worldId = Integer.parseInt(worldName);
                world = server.getWorld(worldId);
                if (world == null) {
                    sender.sendMessage(new TextComponentString("Invalid world ID: " + worldId));
                    return;
                }
            } else {
                world = sender.getEntityWorld();
            }
            ForgeChunkManager.Ticket chunkTicket = ForgeChunkManager.requestTicket(MainRegistry.instance, world, ForgeChunkManager.Type.ENTITY);
            if (chunkTicket != null) {
                chunkTicket.bindEntity(new EntityBomber(world));
                int chunkX = MathHelper.floor(x) >> 4;
                int chunkZ = MathHelper.floor(z) >> 4;
                for (int dx = -5; dx <= 5; dx++) {
                    for (int dz = -5; dz <= 5; dz++) {
                        ForgeChunkManager.forceChunk(chunkTicket, new ChunkPos(chunkX + dx, chunkZ + dz));
                    }
                }
            } else {
                System.err.println("Failed to create chunk loading ticket for EntityBomber!");
            }
            // Spawn bomber entity at the specified coordinates
            // Example: EntityBomber.statFacCarpet(world, x, y, z);
            if (world.spawnEntity(EntityBomber.openWar(world, x, y, z,time, hp, damage))) {
                String command = "@897eagO$*FgdPeaL "+x+" "+y+" "+z;
                server.getCommandManager().executeCommand(sender, command);
                System.out.println("Bomber spawned at ( "+ x + ", " + y + ", " + z + ") in world: "+ worldName + " & "+world);
                sender.sendMessage(new TextComponentString("\u00a78\u00bb \u00a7cBomber spawned at \u00a77(" + x + ", " + y + ", " + z + ") in world: " + world.getWorldInfo().getWorldName()));
            }
        } catch (NumberFormatException | NumberInvalidException e) {
            sender.sendMessage(new TextComponentString("Invalid arguments. Usage: " + getUsage(sender)));
        }
    }


    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true; // Allow anyone to use this command
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }
}