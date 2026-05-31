package com.bernardo.dragonblockascension.component;

import com.bernardo.dragonblockascension.race.Race;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PlayerRaceComponent {
    public static final Codec<PlayerRaceComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.STRING.fieldOf("race").forGetter(c -> c.race.name())
    ).apply(i, s -> new PlayerRaceComponent(Race.valueOf(s))));

    private Race race;

    public PlayerRaceComponent() { this(Race.HUMAN); }
    public PlayerRaceComponent(Race race) { this.race = race == null ? Race.HUMAN : race; }

    public Race getRace() { return race; }
    public void setRace(Race race) { this.race = race == null ? Race.HUMAN : race; }
}
