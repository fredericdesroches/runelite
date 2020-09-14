package net.runelite.client.plugins.zulrahng.patterns;

import java.util.ArrayList;
import java.util.List;

import net.runelite.api.Prayer;
import net.runelite.client.plugins.zulrahng.phases.Zulrah;
import net.runelite.client.plugins.zulrahng.phases.PlayerStandLocation;
import net.runelite.client.plugins.zulrahng.phases.ZulrahLocation;
import net.runelite.client.plugins.zulrahng.phases.ZulrahPhase;
import net.runelite.client.plugins.zulrahng.phases.ZulrahType;

public abstract class ZulrahPattern {
    private final List<ZulrahPhase> pattern = new ArrayList<>();

    void add(ZulrahType type, ZulrahLocation loc, PlayerStandLocation standLocation, Prayer prayer) {
        this.add(type, loc, standLocation, false, prayer);
    }

    void addJad(ZulrahType type, ZulrahLocation loc, PlayerStandLocation standLocation, Prayer prayer) {
        this.add(type, loc, standLocation, true, prayer);
    }

    private void add(ZulrahType type, ZulrahLocation loc, PlayerStandLocation standLocation, boolean jad,
        Prayer prayer) {

        this.pattern.add(new ZulrahPhase(new Zulrah(type, loc), jad, standLocation, prayer));
    }

    // Only work with 2 full rotation
    public ZulrahPhase getPhase(final int index) {
        final int size = this.pattern.size();

        // size = 15
        // index 14 = last
        // index = 15 - 15 = 0
        // index = 16 - 15 = 1
        final int adjustedIndex = index >= size ? (index - size)  : index;

        return this.pattern.get(adjustedIndex);
    }

    public ZulrahPhase getNextPhase(final int index) {
        return this.getPhase(index + 1);
    }
}
