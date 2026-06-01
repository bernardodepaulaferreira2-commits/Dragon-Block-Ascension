package com.bernardo.dragonblockascension.client.render;

import com.bernardo.dragonblockascension.DragonBlockAscension;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class HairRenderer {

    private static final Identifier HAIR1_TEXTURE =
        new Identifier(DragonBlockAscension.MOD_ID, "entity/hair1");

    public static RenderLayer getHair1Layer() {
        return RenderLayer.getEntityTranslucentCull(HAIR1_TEXTURE);
    }

    public static <T extends LivingEntity> void render(
            T entity,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light) {

        VertexConsumer consumer = vertexConsumers.getBuffer(getHair1Layer());
        matrices.push();
        matrices.translate(0.0, 1.5, 0.0);
        matrices.pop();
    }
}
