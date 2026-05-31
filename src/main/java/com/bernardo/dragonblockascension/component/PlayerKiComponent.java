package com.bernardo.dragonblockascension.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PlayerKiComponent {
    public static final Codec<PlayerKiComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.INT.fieldOf("ki").forGetter(c -> c.ki),
        Codec.INT.fieldOf("maxKi").forGetter(c -> c.maxKi),
        Codec.INT.fieldOf("kiRegenRate").forGetter(c -> c.kiRegenRate),
        Codec.INT.fieldOf("currentKi").forGetter(c -> c.currentKi)
    ).apply(i, PlayerKiComponent::new));

    private int ki, maxKi, kiRegenRate, currentKi;

    public PlayerKiComponent() { this(0, 100, 1, 100); }
    public PlayerKiComponent(int ki, int maxKi, int kiRegenRate, int currentKi) {
        this.ki = ki; this.maxKi = maxKi; this.kiRegenRate = kiRegenRate; this.currentKi = currentKi;
    }

    public int getKi() { return ki; }
    public void setKi(int ki) { this.ki = Math.max(0, Math.min(ki, maxKi)); }
    public int getMaxKi() { return maxKi; }
    public void setMaxKi(int maxKi) { this.maxKi = maxKi; }
    public int getKiRegenRate() { return kiRegenRate; }
    public void setKiRegenRate(int rate) { this.kiRegenRate = Math.max(0, rate); }
    public int getCurrentKi() { return currentKi; }
    public void setCurrentKi(int v) { this.currentKi = Math.max(0, Math.min(v, maxKi)); }
}
