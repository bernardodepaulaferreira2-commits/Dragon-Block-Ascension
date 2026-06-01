package com.bernardo.dragonblockascension.network;

import com.bernardo.dragonblockascension.component.PlayerRaceComponent;
import com.bernardo.dragonblockascension.race.Race;
import com.bernardo.dragonblockascension.registry.DBAComponents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PacketHandler {

    // IDs dos packets
    public static final Identifier SET_RACE    = new Identifier("dragonblockascension", "set_race");
    public static final Identifier SET_BODY    = new Identifier("dragonblockascension", "set_body");
    public static final Identifier SYNC_RACE   = new Identifier("dragonblockascension", "sync_race");
    public static final Identifier SYNC_BODY   = new Identifier("dragonblockascension", "sync_body");

    // Registra os receivers no servidor
    public static void registerServerReceivers() {

        // Cliente -> Servidor: player escolheu uma raça
        ServerPlayNetworking.registerGlobalReceiver(SET_RACE, (server, player, handler, buf, responseSender) -> {
            Race race = Race.valueOf(buf.readString());
            server.execute(() -> {
                PlayerRaceComponent comp = player.getAttached(DBAComponents.PLAYER_RACE);
                comp.setRace(race);
                player.setAttached(DBAComponents.PLAYER_RACE, comp);

                // Sincroniza para todos os jogadores que veem esse player
                syncRaceToAll(player, race);
            });
        });

        // Cliente -> Servidor: player toggleou custom body
        ServerPlayNetworking.registerGlobalReceiver(SET_BODY, (server, player, handler, buf, responseSender) -> {
            boolean useCustom = buf.readBoolean();
            server.execute(() -> {
                // TODO: salvar preferência de body no componente
                // Por enquanto só sincroniza de volta
                syncBodyToAll(player, useCustom);
            });
        });
    }

    // Sincroniza raça para todos os jogadores
    public static void syncRaceToAll(ServerPlayerEntity player, Race race) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeUuid(player.getUuid());
        buf.writeString(race.name());
        player.getServerWorld().getServer().getPlayerManager().sendToAll(
            ServerPlayNetworking.createS2CPacket(SYNC_RACE, buf)
        );
    }

    // Sincroniza body para todos os jogadores
    public static void syncBodyToAll(ServerPlayerEntity player, boolean useCustom) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeUuid(player.getUuid());
        buf.writeBoolean(useCustom);
        player.getServerWorld().getServer().getPlayerManager().sendToAll(
            ServerPlayNetworking.createS2CPacket(SYNC_BODY, buf)
        );
    }

    // Cria packet C->S para setar raça
    public static PacketByteBuf createSetRacePacket(Race race) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(race.name());
        return buf;
    }

    // Cria packet C->S para setar body
    public static PacketByteBuf createSetBodyPacket(boolean useCustom) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(useCustom);
        return buf;
    }
}
