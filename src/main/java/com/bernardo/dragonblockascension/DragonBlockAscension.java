package com.bernardo.dragonblockascension;

import com.bernardo.dragonblockascension.network.PacketHandler;
import com.bernardo.dragonblockascension.registry.DBAComponents;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DragonBlockAscension implements ModInitializer {
    public static final String MOD_ID = "dragonblockascension";
    public static final Logger LOGGER = LoggerFactory.getLogger("DragonBlockAscension");

    @Override
    public void onInitialize() {
        LOGGER.info("Dragon Block Ascension carregado.");
        DBAComponents.init();
        PacketHandler.registerServerReceivers();
    }
}
