package com.bernardo.dragonblockascension.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

public class DBALayerRegistry {

    @SuppressWarnings("unchecked")
    public static void register() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, renderer, registrationHelper, context) -> {
            if (entityType == EntityType.PLAYER && renderer instanceof PlayerEntityRenderer) {
                FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>> ctx =
                    (FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>>) renderer;
                registrationHelper.register(new SayajinLayer(ctx));
            }
        });
    }
}
