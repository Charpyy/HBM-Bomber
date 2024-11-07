package com.hbm.render.entity;

import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.EntityNukeExplosionMK6;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderNukeMK6 extends Render<EntityNukeExplosionMK6> {

	public static final IRenderFactory<EntityNukeExplosionMK6> FACTORY = (RenderManager man) -> {return new RenderNukeMK6(man);};

	protected RenderNukeMK6(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityNukeExplosionMK6 entity, double x, double y, double z, float entityYaw, float partialTicks) {}

	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNukeExplosionMK6 entity) {
		return null;
	}

}
