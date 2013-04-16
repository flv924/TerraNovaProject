package fr.terranova.flv92.dinosaurus.render;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import fr.terranova.flv92.dinosaurus.entity.EntityPlesiosaur;
import fr.terranova.flv92.dinosaurus.model.ModelPlesiosaur;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderPlesiosaur extends RenderLiving
{
    public final int WaveFreq = 35;
    public final float WaveAngle = 0.17453292F;
    public boolean WaveRight = true;

    public RenderPlesiosaur(float var1)
    {
        super(new ModelPlesiosaur(), var1);
    }

    public void renderPlesiosaur(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        ((ModelPlesiosaur)this.mainModel).LandFlag = !var1.isInWater();
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderCow((EntityPlesiosaur)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderCow((EntityPlesiosaur)var1, var2, var4, var6, var8, var9);
    }

    private float func_77034_a(float var1, float var2, float var3)
    {
        float var4;

        for (var4 = var2 - var1; var4 < -180.0F; var4 += 360.0F)
        {
            ;
        }

        while (var4 >= 180.0F)
        {
            var4 -= 360.0F;
        }

        return var1 + var3 * var4;
    }

    public void renderCow(EntityPlesiosaur var1, double var2, double var4, double var6, float var8, float var9)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        this.mainModel.onGround = this.renderSwingProgress(var1, var9);

        if (this.renderPassModel != null)
        {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }

        this.mainModel.isRiding = var1.isRiding();

        if (this.renderPassModel != null)
        {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }

        try
        {
            float var10 = this.func_77034_a(var1.prevRenderYawOffset, var1.renderYawOffset, var9);
            float var11 = this.func_77034_a(var1.prevRotationYawHead, var1.rotationYawHead, var9);
            float var12 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9;
            this.renderLivingAt(var1, var2, var4, var6);
            float var13 = this.handleRotationFloat(var1, var9);
            this.rotateCorpse(var1, var13, var10, var9);
            float var14 = 0.0625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(5.0F, -5.0F, 5.0F);
            this.preRenderCallback(var1, var9);
            GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
            float var15 = var1.prevLegYaw + (var1.legYaw - var1.prevLegYaw) * var9;
            float var16 = var1.legSwing - var1.legYaw * (1.0F - var9);

            if (var1.isChild())
            {
                var16 *= 3.0F;
            }

            if (var15 > 1.0F)
            {
                var15 = 1.0F;
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            this.mainModel.setLivingAnimations(var1, var16, var15, var9);
            this.renderModel(var1, var16, var15, var13, var11 - var10, var12, var14);
            float var17;
            float var19;
            int var18;
            float var20;
            int var22;

            for (int var21 = 0; var21 < 4; ++var21)
            {
                var18 = this.shouldRenderPass(var1, var21, var9);

                if (var18 > 0)
                {
                    this.renderPassModel.setLivingAnimations(var1, var16, var15, var9);
                    this.renderPassModel.render(var1, var16, var15, var13, var11 - var10, var12, var14);

                    if (var18 == 15)
                    {
                        var17 = (float)var1.ticksExisted + var9;
                        this.loadTexture("%blur%/misc/glint.png");
                        GL11.glEnable(GL11.GL_BLEND);
                        var19 = 0.5F;
                        GL11.glColor4f(var19, var19, var19, 1.0F);
                        GL11.glDepthFunc(GL11.GL_EQUAL);
                        GL11.glDepthMask(false);

                        for (var22 = 0; var22 < 2; ++var22)
                        {
                            GL11.glDisable(GL11.GL_LIGHTING);
                            var20 = 0.76F;
                            GL11.glColor4f(0.5F * var20, 0.25F * var20, 0.8F * var20, 1.0F);
                            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                            GL11.glMatrixMode(GL11.GL_TEXTURE);
                            GL11.glLoadIdentity();
                            float var23 = var17 * (0.001F + (float)var22 * 0.003F) * 20.0F;
                            float var24 = 0.33333334F;
                            GL11.glScalef(var24, var24, var24);
                            GL11.glRotatef(30.0F - (float)var22 * 60.0F, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslatef(0.0F, var23, 0.0F);
                            GL11.glMatrixMode(GL11.GL_MODELVIEW);
                            this.renderPassModel.render(var1, var16, var15, var13, var11 - var10, var12, var14);
                        }

                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glDepthMask(true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glDepthFunc(GL11.GL_LEQUAL);
                    }

                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                }
            }

            this.renderEquippedItems(var1, var9);
            float var28 = var1.getBrightness(var9);
            var18 = this.getColorMultiplier(var1, var28, var9);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

            if ((var18 >> 24 & 255) > 0 || var1.hurtTime > 0 || var1.deathTime > 0)
            {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDepthFunc(GL11.GL_EQUAL);

                if (var1.hurtTime > 0 || var1.deathTime > 0)
                {
                    GL11.glColor4f(var28, 0.0F, 0.0F, 0.4F);
                    this.mainModel.render(var1, var16, var15, var13, var11 - var10, var12, var14);

                    for (var22 = 0; var22 < 4; ++var22)
                    {
                        if (this.inheritRenderPass(var1, var22, var9) >= 0)
                        {
                            GL11.glColor4f(var28, 0.0F, 0.0F, 0.4F);
                            this.renderPassModel.render(var1, var16, var15, var13, var11 - var10, var12, var14);
                        }
                    }
                }

                if ((var18 >> 24 & 255) > 0)
                {
                    var17 = (float)(var18 >> 16 & 255) / 255.0F;
                    var19 = (float)(var18 >> 8 & 255) / 255.0F;
                    float var27 = (float)(var18 & 255) / 255.0F;
                    var20 = (float)(var18 >> 24 & 255) / 255.0F;
                    GL11.glColor4f(var17, var19, var27, var20);
                    this.mainModel.render(var1, var16, var15, var13, var11 - var10, var12, var14);

                    for (int var26 = 0; var26 < 4; ++var26)
                    {
                        if (this.inheritRenderPass(var1, var26, var9) >= 0)
                        {
                            GL11.glColor4f(var17, var19, var27, var20);
                            this.renderPassModel.render(var1, var16, var15, var13, var11 - var10, var12, var14);
                        }
                    }
                }

                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        catch (Exception var25)
        {
            var25.printStackTrace();
        }

        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        this.passSpecialRender(var1, var2, var4, var6);
    }
}
