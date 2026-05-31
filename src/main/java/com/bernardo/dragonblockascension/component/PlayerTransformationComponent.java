package com.bernardo.dragonblockascension.component;

import com.bernardo.dragonblockascension.transformation.Transformation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PlayerTransformationComponent {
    public static final Codec<PlayerTransformationComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.STRING.fieldOf("transformation").forGetter(c -> c.transformation.name())
    ).apply(i, s -> new PlayerTransformationComponent(Transformation.valueOf(s))));

    private Transformation transformation;

    public PlayerTransformationComponent() { this(Transformation.NONE); }
    public PlayerTransformationComponent(Transformation t) { this.transformation = t == null ? Transformation.NONE : t; }

    public Transformation getTransformation() { return transformation; }
    public void setTransformation(Transformation t) { this.transformation = t == null ? Transformation.NONE : t; }
}
