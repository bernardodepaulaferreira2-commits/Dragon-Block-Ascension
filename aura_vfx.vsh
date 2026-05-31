#version 150

// ============================================
// AURA VFX - Custom Vertex Shader
// ============================================
// Handles vertex transformation with animated
// wave distortion for organic energy movement.
// ============================================

in vec3 Position;
in vec2 UV0;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform float GameTime;  // Minecraft provides this (0..1 cycling)

out vec2 texCoord;
out vec4 vertColor;
out float vHeight;  // Normalized height for fragment effects

void main() {
    vec3 pos = Position;
    
    // Normalized height (0 = feet, 1 = top)
    vHeight = clamp(pos.y / 2.3, 0.0, 1.0);
    
    // Animated wave distortion on XZ plane
    float t = GameTime * 1200.0; // Speed up GameTime
    float waveX = sin(pos.y * 4.0 + t + pos.x * 2.0) * 0.04;
    float waveZ = cos(pos.y * 3.5 + t * 0.8 + pos.z * 2.0) * 0.04;
    
    // Stronger distortion at the top (flame tips flicker more)
    float distortionStrength = smoothstep(0.3, 1.0, vHeight) * 1.5 + 0.5;
    pos.x += waveX * distortionStrength;
    pos.z += waveZ * distortionStrength;
    
    // Slight upward drift at top vertices
    pos.y += smoothstep(0.7, 1.0, vHeight) * sin(t * 0.5 + pos.x * 3.0) * 0.03;
    
    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1.0);
    texCoord = UV0;
    vertColor = Color;
}
