package com.bernardo.dragonblockascension.client;

import com.bernardo.dragonblockascension.client.aura.AuraRenderer;
import com.bernardo.dragonblockascension.client.aura.AuraShaderAccess;
import com.bernardo.dragonblockascension.client.gui.DBAKeyBindings;
import com.bernardo.dragonblockascension.client.render.DBALayerRegistry;
import com.bernardo.dragonblockascension.client.render.HairShaderAccess;
import com.bernardo.dragonblockascension.network.ClientPacketHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class DragonBlockAscensionClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DBALayerRegistry.register();
        DBAKeyBindings.register();
        ClientPacketHandler.registerClientReceivers();

        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            ctx.register(
                new Identifier("dragonblockascension", "aura_vfx"),
                VertexFormats.POSITION_TEXTURE_COLOR,
                shader -> AuraShaderAccess.auraShader = shader
            );
            ctx.register(
                new Identifier("dragonblockascension", "hair1"),
                VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                shader -> HairShaderAccess.hair1Shader = shader
            );
        });

        WorldRenderEvents.AFTER_ENTITIES.register(AuraRenderer::onRenderWorld);
    }
}
