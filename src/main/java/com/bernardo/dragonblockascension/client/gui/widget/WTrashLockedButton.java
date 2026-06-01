package com.bernardo.dragonblockascension.client.gui.widget;

public class WTrashLockedButton extends WIconButton {
    public WTrashLockedButton() {
        super(
            20, 20, // UV bloqueado
            -1, -1, // sem pressed
            -1, -1, // sem bloqueado separado
            10, 10, // tamanho 10x10
            256, 256 // spritesheet
        );
        this.setLocked(true);
    }
}
