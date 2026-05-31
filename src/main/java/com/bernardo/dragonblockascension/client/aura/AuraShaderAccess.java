package com.bernardo.dragonblockascension.client.aura;

import net.minecraft.client.gl.ShaderProgram;

/**
 * Holds the reference to our custom loaded shader program.
 * Set by AuraModClient during shader registration callback.
 */
public class AuraShaderAccess {
    public static ShaderProgram auraShader = null;
}
