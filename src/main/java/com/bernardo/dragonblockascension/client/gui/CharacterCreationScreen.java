package com.bernardo.dragonblockascension.client.gui;

import com.bernardo.dragonblockascension.network.PacketHandler;
import com.bernardo.dragonblockascension.race.Race;
import com.bernardo.dragonblockascension.registry.DBAComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CharacterCreationScreen extends Screen {

    private static final Identifier BG = new Identifier("dragonblockascension", "textures/gui/gui.png");
    private static final int GUI_W = 256;
    private static final int GUI_H = 159;

    private static final Race[] RACES = {
        Race.HUMAN,
        Race.SAIYAN,
        Race.HALF_SAIYAN
    };

    private int raceIndex = 0;
    private boolean useCustomBody = false;
    private ButtonWidget bodyToggle;
    private ButtonWidget raceLabel;

    public CharacterCreationScreen() {
        super(Text.literal("Criação de Personagem"));
        if (MinecraftClient.getInstance().player != null) {
            Race current = MinecraftClient.getInstance().player
                .getAttached(DBAComponents.PLAYER_RACE).getRace();
            for (int i = 0; i < RACES.length; i++) {
                if (RACES[i] == current) { raceIndex = i; break; }
            }
        }
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;

        // Seta esquerda
        this.addDrawableChild(ButtonWidget.builder(Text.literal("<"), btn -> {
            raceIndex = (raceIndex - 1 + RACES.length) % RACES.length;
            raceLabel.setMessage(Text.literal(RACES[raceIndex].name()));
        }).dimensions(cx - 70, cy - 10, 20, 20).build());

        // Label da raça
        raceLabel = ButtonWidget.builder(Text.literal(RACES[raceIndex].name()), btn -> {})
            .dimensions(cx - 45, cy - 10, 90, 20).build();
        this.addDrawableChild(raceLabel);

        // Seta direita
        this.addDrawableChild(ButtonWidget.builder(Text.literal(">"), btn -> {
            raceIndex = (raceIndex + 1) % RACES.length;
            raceLabel.setMessage(Text.literal(RACES[raceIndex].name()));
        }).dimensions(cx + 50, cy - 10, 20, 20).build());

        // Body toggle
        bodyToggle = ButtonWidget.builder(Text.literal("Body"), btn -> {
            useCustomBody = !useCustomBody;
            bodyToggle.setMessage(Text.literal(useCustomBody ? "Custom Body" : "Body"));
            ClientPlayNetworking.send(PacketHandler.SET_BODY,
                PacketHandler.createSetBodyPacket(useCustomBody));
        }).dimensions(cx - 30, cy + 20, 60, 20).build();
        this.addDrawableChild(bodyToggle);

        // Confirmar
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Confirmar"), btn -> {
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player
                    .getAttached(DBAComponents.PLAYER_RACE)
                    .setRace(RACES[raceIndex]);
                ClientPlayNetworking.send(PacketHandler.SET_RACE,
                    PacketHandler.createSetRacePacket(RACES[raceIndex]));
            }
            this.close();
        }).dimensions(cx - 30, cy + 50, 60, 20).build());

        // Fechar
        this.addDrawableChild(ButtonWidget.builder(Text.literal("X"), btn -> this.close())
            .dimensions(cx + 100, cy - 60, 20, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        int x = (this.width - GUI_W) / 2;
        int y = (this.height - GUI_H) / 2;
        context.drawTexture(BG, x, y, 0, 0, GUI_W, GUI_H, 256, 256);
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("Criação de Personagem"),
            this.width / 2, y + 10, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }

    public static void open() {
        MinecraftClient.getInstance().setScreen(new CharacterCreationScreen());
    }
}
