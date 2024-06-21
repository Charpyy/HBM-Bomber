package com.hbm.entity.logic;

import com.hbm.lib.RefStrings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class SmokeParticle extends Particle {
    int maxScale = 10;
    boolean isCollided;

    private static final VertexFormat VERTEX_FORMAT = new VertexFormat()
            .addElement(DefaultVertexFormats.POSITION_3F)
            .addElement(DefaultVertexFormats.COLOR_4UB)
            .addElement(DefaultVertexFormats.TEX_2F)
            .addElement(DefaultVertexFormats.NORMAL_3B)
            .addElement(DefaultVertexFormats.PADDING_1B);

    public SmokeParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        super(world, x, y, z, mx, my, mz);
        this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
        this.particleScale = this.rand.nextFloat() * 0.5F + 5.0F;
        this.particleMaxAge = (int) (16.0 / (this.rand.nextFloat() * 0.8 + 0.2)) + 2;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.isCollided && this.particleScale < this.maxScale) {
            this.particleScale += 0.8F;
        }
    }

    private float getDistance(Entity entity) {
        double dx = this.posX - entity.posX;
        double dy = this.posY - entity.posY;
        double dz = this.posZ - entity.posZ;
        return MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        bindParticleTexture();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        float f6 = (float)this.particleTexture.getMinU();
        float f7 = (float)this.particleTexture.getMaxU();
        float f8 = (float)this.particleTexture.getMinV();
        float f9 = (float)this.particleTexture.getMaxV();
        float f10 = 0.1F * this.particleScale;
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);

        buffer.begin(GL11.GL_QUADS, VERTEX_FORMAT);
        buffer.pos((double)(f11 - rotationX * f10 - rotationXY * f10), (double)(f12 - rotationZ * f10), (double)(f13 - rotationYZ * f10 - rotationXZ * f10)).tex(f7, f9).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).normal(0, 1, 0).endVertex();
        buffer.pos((double)(f11 - rotationX * f10 + rotationXY * f10), (double)(f12 + rotationZ * f10), (double)(f13 - rotationYZ * f10 + rotationXZ * f10)).tex(f7, f8).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).normal(0, 1, 0).endVertex();
        buffer.pos((double)(f11 + rotationX * f10 + rotationXY * f10), (double)(f12 + rotationZ * f10), (double)(f13 + rotationYZ * f10 + rotationXZ * f10)).tex(f6, f8).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).normal(0, 1, 0).endVertex();
        buffer.pos((double)(f11 + rotationX * f10 - rotationXY * f10), (double)(f12 - rotationZ * f10), (double)(f13 + rotationYZ * f10 - rotationXZ * f10)).tex(f6, f9).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).normal(0, 1, 0).endVertex();
        Tessellator.getInstance().draw();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void bindParticleTexture() {
        ResourceLocation textureLocation =  new ResourceLocation(RefStrings.MODID + ":textures/particle/smoke.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(textureLocation);
    }
}
