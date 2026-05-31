package com.example.auramod.client;

import net.minecraft.client.gl.ShaderProgram;

/**
 * Holds the reference to our custom loaded shader program.
 * Set by AuraModClient during shader registration callback.
 */
public class AuraShaderAccess {
    public static ShaderProgram auraShader = null;
}
