package com.bernardo.dragonblockascension.client;

import com.bernardo.dragonblockascension.client.aura.AuraModClient;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DragonBlockAscensionClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("DragonBlockAscensionClient");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Dragon Block Ascension client carregado.");
        // Initialize aura subsystem
        new AuraModClient().onInitializeClient();
    }
}
