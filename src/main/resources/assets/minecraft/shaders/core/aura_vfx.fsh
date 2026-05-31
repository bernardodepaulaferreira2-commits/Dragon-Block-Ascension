#version 150

// ============================================
// AURA VFX - Custom Fragment Shader
// ============================================
// Creates glowing energy effect with:
// - Animated UV distortion (flowing energy)
// - Additive glow boost on bright areas
// - Edge softening via alpha
// - Flicker/pulse effect
// ============================================

uniform sampler2D Sampler0;
uniform float GameTime;
uniform vec4 ColorModulator;

in vec2 texCoord;
in vec4 vertColor;
in float vHeight;

out vec4 fragColor;

// Simple pseudo-noise function
float noise(vec2 p) {
    return fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    float t = GameTime * 1200.0;
    
    // === UV DISTORTION (flowing energy) ===
    // Distort UV based on time and position for energy flow
    float distU = sin(texCoord.y * 8.0 + t * 0.7) * 0.03;
    float distV = cos(texCoord.x * 6.0 + t * 0.5) * 0.02;
    // Upward flow: offset V over time
    float flowV = t * 0.02;
    
    vec2 uv = vec2(
        texCoord.x + distU,
        texCoord.y + distV - flowV
    );
    
    // Wrap UVs
    uv = fract(uv);
    
    // === SAMPLE TEXTURE ===
    vec4 texColor = texture(Sampler0, uv);
    
    // Second sample with different distortion for depth
    vec2 uv2 = vec2(
        texCoord.x - distU * 1.5 + 0.3,
        texCoord.y - distV * 0.8 - flowV * 1.3
    );
    uv2 = fract(uv2);
    vec4 texColor2 = texture(Sampler0, uv2);
    
    // Blend two samples for richer energy look
    vec4 baseColor = mix(texColor, texColor2, 0.4);
    
    // === APPLY VERTEX COLOR (tint + alpha from CPU) ===
    vec4 colored = baseColor * vertColor * ColorModulator;
    
    // === GLOW BOOST ===
    // Additive self-illumination on bright areas
    float luminance = dot(colored.rgb, vec3(0.299, 0.587, 0.114));
    colored.rgb += colored.rgb * luminance * 0.8;
    
    // === FLICKER ===
    // Subtle random flicker
    float flicker = 0.9 + noise(vec2(t * 0.1, vHeight * 10.0)) * 0.2;
    colored.rgb *= flicker;
    
    // === HEIGHT-BASED EFFECTS ===
    // Brighter at center height, dimmer at extremes
    float heightGlow = sin(vHeight * 3.14159) * 0.3 + 0.7;
    colored.rgb *= heightGlow;
    
    // === EDGE FADE ===
    // Soften alpha at very top for smooth flame tip
    float edgeFade = 1.0;
    if (vHeight > 0.85) {
        edgeFade = 1.0 - smoothstep(0.85, 1.0, vHeight);
    }
    colored.a *= edgeFade;
    
    // Discard invisible fragments
    if (colored.a < 0.01) discard;
    
    fragColor = colored;
}
