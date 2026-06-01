package com.bernardo.dragonblockascension.client.gui.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WIconButton extends ButtonWidget {

    private static final Identifier ICONS = new Identifier("dragonblockascension", "textures/gui/icons.png");

    private final int uNormal;
    private final int vNormal;
    private final int uPressed;
    private final int vPressed;
    private final int uLocked;
    private final int vLocked;
    private final int btnW;
    private final int btnH;
    private final int sheetW;
    private final int sheetH;
    private boolean locked = false;
    private boolean holding = false;

    public WIconButton(int uNormal, int vNormal,
                       int uPressed, int vPressed,
                       int uLocked, int vLocked,
                       int btnW, int btnH,
                       int sheetW, int sheetH) {
        super(0, 0, btnW, btnH, Text.empty(), btn -> {}, DEFAULT_NARRATION_SUPPLIER);
        this.uNormal  = uNormal;
        this.vNormal  = vNormal;
        this.uPressed = uPressed;
        this.vPressed = vPressed;
        this.uLocked  = uLocked;
        this.vLocked  = vLocked;
        this.btnW     = btnW;
        this.btnH     = btnH;
        this.sheetW   = sheetW;
        this.sheetH   = sheetH;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!this.visible) return;
        int u, v;
        if (locked && uLocked != -1) {
            u = uLocked; v = vLocked;
        } else if (holding) {
            u = uPressed; v = vPressed;
        } else {
            u = uNormal; v = vNormal;
        }
        context.drawTexture(ICONS, this.getX(), this.getY(), u, v, btnW, btnH, sheetW, sheetH);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (locked) return false;
        holding = true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        holding = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public WIconButton setLocked(boolean locked) {
        this.locked = locked;
        this.active = !locked;
        return this;
    }

    public boolean isLocked() { return locked; }
}
