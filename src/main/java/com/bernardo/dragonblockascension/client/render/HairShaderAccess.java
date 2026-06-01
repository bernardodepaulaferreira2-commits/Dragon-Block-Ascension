package com.bernardo.dragonblockascension.client.render;

import net.minecraft.client.gl.ShaderProgram;

public class HairShaderAccess {
    public static ShaderProgram hair1Shader = null;

    public static ShaderProgram getShader() {
        return hair1Shader;
    }
}
