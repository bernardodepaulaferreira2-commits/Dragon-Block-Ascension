package com.bernardo.dragonblockascension.client.gui;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class DBAKeyBindings {

    public static KeyBinding openCharacterMenu;

    public static void register() {
        openCharacterMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.dragonblockascension.character_menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "category.dragonblockascension"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (openCharacterMenu.wasPressed()) {
                CharacterCreationScreen.open();
            }
        });
    }
}
