package com.bernardo.dragonblockascension.client.gui.widget;

public class WPlusLockedButton extends WIconButton {
    public WPlusLockedButton(int w, int h) {
        super(
            30, 20, // UV bloqueado
            -1, -1, // sem pressed
            -1, -1, // sem bloqueado separado
            w, h,   // tamanho - informar
            256, 256 // spritesheet
        );
        this.setLocked(true);
    }
}
