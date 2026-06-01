package com.bernardo.dragonblockascension.client.render;

import com.bernardo.dragonblockascension.client.model.SayajinModel;
import com.bernardo.dragonblockascension.race.Race;
import com.bernardo.dragonblockascension.registry.DBAComponents;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

public class SayajinLayer extends FeatureRenderer<PlayerEntity, PlayerEntityModel<PlayerEntity>> {

    private final SayajinModel<PlayerEntity> model;

    public SayajinLayer(FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>> context) {
        super(context);
        this.model = new SayajinModel<>(SayajinModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                       int light, PlayerEntity player, float limbAngle, float limbDistance,
                       float tickDelta, float animationProgress, float headYaw, float headPitch) {

        Race race = player.getAttached(DBAComponents.PLAYER_RACE).getRace();
        if (race != Race.SAIYAN) return;

        this.getContextModel().copyStateTo(this.model);

        this.model.render(matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(SayajinModel.TEXTURE)),
                light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
    }
}
