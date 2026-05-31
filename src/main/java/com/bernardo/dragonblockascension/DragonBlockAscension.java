package com.bernardo.dragonblockascension;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DragonBlockAscension implements ModInitializer {
    public static final String MOD_ID = "dragonblockascension";
    public static final Logger LOGGER = LoggerFactory.getLogger("DragonBlockAscension");

    @Override
    public void onInitialize() {
        LOGGER.info("Dragon Block Ascension carregado.");
    }
}
