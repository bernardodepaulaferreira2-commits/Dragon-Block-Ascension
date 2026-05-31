package com.bernardo.dragonblockascension.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PlayerStatsComponent {
    public static final Codec<PlayerStatsComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.INT.fieldOf("level").forGetter(c -> c.level),
        Codec.INT.fieldOf("experience").forGetter(c -> c.experience),
        Codec.INT.fieldOf("skillPoints").forGetter(c -> c.skillPoints)
    ).apply(i, PlayerStatsComponent::new));

    private int level, experience, skillPoints;

    public PlayerStatsComponent() { this(1, 0, 0); }
    public PlayerStatsComponent(int level, int experience, int skillPoints) {
        this.level = level; this.experience = experience; this.skillPoints = skillPoints;
    }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = Math.max(1, level); }
    public int getExperience() { return experience; }
    public void setExperience(int exp) { this.experience = Math.max(0, exp); }
    public int getSkillPoints() { return skillPoints; }
    public void setSkillPoints(int sp) { this.skillPoints = Math.max(0, sp); }
}
