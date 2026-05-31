package com.bernardo.dragonblockascension.client.aura;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

/**
 * Client entrypoint:
 * 1. Registers our custom GLSL shader via Fabric's CoreShaderRegistrationCallback
 * 2. Registers the aura renderer on AFTER_ENTITIES event
 */
public class AuraModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register custom shader
        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            // This tells Minecraft to load aura_vfx.json/.vsh/.fsh from
            // assets/minecraft/shaders/core/
            ctx.register(
                    new Identifier("aura_vfx"),
                    VertexFormats.POSITION_TEXTURE_COLOR,
                    shader -> AuraShaderAccess.auraShader = shader
            );
        });

        // Register world render event
        WorldRenderEvents.AFTER_ENTITIES.register(AuraRenderer::onRenderWorld);
    }
}
