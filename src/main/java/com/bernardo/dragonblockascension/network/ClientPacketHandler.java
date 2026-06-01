package com.bernardo.dragonblockascension.network;

import com.bernardo.dragonblockascension.race.Race;
import com.bernardo.dragonblockascension.registry.DBAComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class ClientPacketHandler {

    public static void registerClientReceivers() {

        // Servidor -> Cliente: sincroniza raça de um player
        ClientPlayNetworking.registerGlobalReceiver(PacketHandler.SYNC_RACE, (client, handler, buf, responseSender) -> {
            UUID uuid = buf.readUuid();
            Race race = Race.valueOf(buf.readString());
            client.execute(() -> {
                if (client.world == null) return;
                for (PlayerEntity player : client.world.getPlayers()) {
                    if (player.getUuid().equals(uuid)) {
                        player.getAttached(DBAComponents.PLAYER_RACE).setRace(race);
                        break;
                    }
                }
            });
        });

        // Servidor -> Cliente: sincroniza body de um player
        ClientPlayNetworking.registerGlobalReceiver(PacketHandler.SYNC_BODY, (client, handler, buf, responseSender) -> {
            UUID uuid = buf.readUuid();
            boolean useCustom = buf.readBoolean();
            client.execute(() -> {
                if (client.world == null) return;
                MinecraftClient mc = MinecraftClient.getInstance();
                // TODO: aplicar textura customizada no player correto
                // Quando tiver o sistema de body implementado
            });
        });
    }
}
