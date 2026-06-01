package com.bernardo.dragonblockascension.client.aura;

import com.bernardo.dragonblockascension.DragonBlockAscension;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class AuraRenderer {

    private static final Identifier INNER_TEX =
            new Identifier(DragonBlockAscension.MOD_ID, "textures/vfx/aura_inner.png");
    private static final Identifier OUTLINE_TEX =
            new Identifier(DragonBlockAscension.MOD_ID, "textures/vfx/aura_outline.png");

    private static final float INNER_RADIUS = 0.5f;
    private static final float INNER_ALPHA = 0.6f;
    private static final float INNER_ROT_SPEED = 1.2f;
    private static final float INNER_R = 1.0f;
    private static final float INNER_G = 0.95f;
    private static final float INNER_B = 0.5f;

    private static final float OUTER_RADIUS = 0.85f;
    private static final float OUTER_ALPHA = 0.35f;
    private static final float OUTER_ROT_SPEED = -0.7f;
    private static final float OUTER_R = 1.0f;
    private static final float OUTER_G = 0.8f;
    private static final float OUTER_B = 0.15f;

    private static final float AURA_HEIGHT = 2.3f;
    private static final int H_SEGMENTS = 20;
    private static final int V_SEGMENTS = 12;
    private static final float PULSE_SPEED = 2.5f;
    private static final float PULSE_AMOUNT = 0.08f;
    private static final float WAVE_AMP = 0.05f;

    public static void onRenderWorld(WorldRenderContext ctx) {
        if (AuraShaderAccess.auraShader == null) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        float tickDelta = ctx.tickDelta();
        Vec3d camPos = ctx.camera().getPos();
        MatrixStack matrices = ctx.matrixStack();

        if (client.player != null) {
            if (client.options.getPerspective() != Perspective.FIRST_PERSON) {
                renderOnPlayer(client.player, matrices, camPos, tickDelta);
            }
        }

        for (PlayerEntity player : client.world.getPlayers()) {
            if (player == client.player) continue;
            renderOnPlayer(player, matrices, camPos, tickDelta);
        }
    }

    private static void renderOnPlayer(PlayerEntity player, MatrixStack matrices,
                                       Vec3d camPos, float tickDelta) {
        double px = MathHelper.lerp(tickDelta, player.prevX, player.getX()) - camPos.x;
        double py = MathHelper.lerp(tickDelta, player.prevY, player.getY()) - camPos.y;
        double pz = MathHelper.lerp(tickDelta, player.prevZ, player.getZ()) - camPos.z;

        float time = (player.age + tickDelta) * 0.05f;

        matrices.push();
        matrices.translate(px, py, pz);

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE,
                GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.enableDepthTest();
        RenderSystem.setShader(() -> AuraShaderAccess.auraShader);

        RenderSystem.setShaderTexture(0, INNER_TEX);
        renderCylinder(matrices, time, INNER_RADIUS, INNER_ALPHA,
                INNER_R, INNER_G, INNER_B, INNER_ROT_SPEED);

        RenderSystem.setShaderTexture(0, OUTLINE_TEX);
        renderCylinder(matrices, time * 0.8f, OUTER_RADIUS, OUTER_ALPHA,
                OUTER_R, OUTER_G, OUTER_B, OUTER_ROT_SPEED);

        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();

        matrices.pop();
    }

    private static void renderCylinder(MatrixStack matrices, float time, float baseRadius,
                                       float baseAlpha, float r, float g, float b,
                                       float rotSpeed) {
        Matrix4f posMatrix = matrices.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
        buf.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

        float rotation = time * rotSpeed;
        float pulse = 1.0f + (float) Math.sin(time * PULSE_SPEED) * PULSE_AMOUNT;
        float radius = baseRadius * pulse;

        int ri = clamp255(r), gi = clamp255(g), bi = clamp255(b);

        for (int i = 0; i < H_SEGMENTS; i++) {
            float angle1 = (float)(2.0 * Math.PI * i / H_SEGMENTS) + rotation;
            float angle2 = (float)(2.0 * Math.PI * (i + 1) / H_SEGMENTS) + rotation;
            float u1 = (float) i / H_SEGMENTS;
            float u2 = (float)(i + 1) / H_SEGMENTS;

            for (int j = 0; j < V_SEGMENTS; j++) {
                float v1 = (float) j / V_SEGMENTS;
                float v2 = (float)(j + 1) / V_SEGMENTS;
                float y1 = v1 * AURA_HEIGHT - 0.05f;
                float y2 = v2 * AURA_HEIGHT - 0.05f;

                float w1 = (float) Math.sin(y1 * 3.5f + time * 2.0f + angle1 * 2.0f) * WAVE_AMP;
                float w2 = (float) Math.sin(y2 * 3.5f + time * 2.0f + angle2 * 2.0f) * WAVE_AMP;

                float rad1 = (radius + w1) * taper(v1);
                float rad2 = (radius + w2) * taper(v2);

                float x1a = cos(angle1)*rad1, z1a = sin(angle1)*rad1;
                float x2a = cos(angle2)*rad1, z2a = sin(angle2)*rad1;
                float x1b = cos(angle1)*rad2, z1b = sin(angle1)*rad2;
                float x2b = cos(angle2)*rad2, z2b = sin(angle2)*rad2;

                int a1 = clamp255(baseAlpha * alphaFade(v1));
                int a2 = clamp255(baseAlpha * alphaFade(v2));

                buf.vertex(posMatrix, x1a, y1, z1a).texture(u1, v1).color(ri, gi, bi, a1).next();
                buf.vertex(posMatrix, x2a, y1, z2a).texture(u2, v1).color(ri, gi, bi, a1).next();
                buf.vertex(posMatrix, x2b, y2, z2b).texture(u2, v2).color(ri, gi, bi, a2).next();
                buf.vertex(posMatrix, x1b, y2, z1b).texture(u1, v2).color(ri, gi, bi, a2).next();
            }
        }
        tessellator.draw();
    }

    private static float taper(float v) {
        if (v < 0.12f) return 0.4f + (v / 0.12f) * 0.6f;
        if (v < 0.55f) return 1.0f;
        return 1.0f - ((v - 0.55f) / 0.45f) * 0.75f;
    }

    private static float alphaFade(float v) {
        if (v < 0.08f) return v / 0.08f;
        if (v > 0.8f) return 1.0f - (v - 0.8f) / 0.2f;
        return 1.0f;
    }

    private static int clamp255(float f) { return Math.max(0, Math.min(255, (int)(f * 255))); }
    private static float cos(float a) { return (float) Math.cos(a); }
    private static float sin(float a) { return (float) Math.sin(a); }
}
