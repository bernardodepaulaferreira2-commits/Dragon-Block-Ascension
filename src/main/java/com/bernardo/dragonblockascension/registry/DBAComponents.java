package com.bernardo.dragonblockascension.registry;

import com.bernardo.dragonblockascension.component.PlayerKiComponent;
import com.bernardo.dragonblockascension.component.PlayerRaceComponent;
import com.bernardo.dragonblockascension.component.PlayerStatsComponent;
import com.bernardo.dragonblockascension.component.PlayerTransformationComponent;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;

public final class DBAComponents {
    public static final AttachmentType<PlayerKiComponent> PLAYER_KI =
        AttachmentRegistry.createPersistent(
            new Identifier("dragonblockascension", "player_ki"),
            PlayerKiComponent.CODEC
        );

    public static final AttachmentType<PlayerRaceComponent> PLAYER_RACE =
        AttachmentRegistry.createPersistent(
            new Identifier("dragonblockascension", "player_race"),
            PlayerRaceComponent.CODEC
        );

    public static final AttachmentType<PlayerStatsComponent> PLAYER_STATS =
        AttachmentRegistry.createPersistent(
            new Identifier("dragonblockascension", "player_stats"),
            PlayerStatsComponent.CODEC
        );

    public static final AttachmentType<PlayerTransformationComponent> PLAYER_TRANSFORMATION =
        AttachmentRegistry.createPersistent(
            new Identifier("dragonblockascension", "player_transformation"),
            PlayerTransformationComponent.CODEC
        );

    public static void init() {}
}
