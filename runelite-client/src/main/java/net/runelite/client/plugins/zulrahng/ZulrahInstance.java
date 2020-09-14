package net.runelite.client.plugins.zulrahng;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NPC;
import net.runelite.client.plugins.zulrahng.patterns.ZulrahPattern;
import net.runelite.client.plugins.zulrahng.patterns.ZulrahPatternA;
import net.runelite.client.plugins.zulrahng.phases.ZulrahPhase;

@Slf4j
public class ZulrahInstance {
    @Getter
    private final NPC npc;

    @Setter
    private ZulrahPattern pattern;

    @Getter
    @Setter
    private int currentPhaseCounter;

    @Getter
    @Setter
    private ZulrahPhase currentPhase;

    private final ZulrahPattern patternA = new ZulrahPatternA();

    public ZulrahInstance(final NPC zulrah) {
        this.npc = zulrah;

        this.pattern = null;
        this.currentPhaseCounter = 0;
        this.currentPhase = patternA.getPhase(0); // Same first phase
    }

    public ZulrahPattern getMatchedPattern() {
        return pattern;
    }

    public ZulrahPattern getPattern() {
        return pattern != null ? pattern : patternA;
    }

    public int incrementPhaseCounter() {
        return ++this.currentPhaseCounter;
    }

    public ZulrahPhase incrementPhase() {
        log.info("[Phase Increment] Incrementing phase for pattern : {}, counter : {}", this.pattern, this.currentPhaseCounter);
        if (this.pattern != null) {
            this.currentPhase = this.pattern.getPhase(this.currentPhaseCounter);
        } else {
            this.currentPhase = this.patternA.getPhase(this.currentPhaseCounter);
        }

        return this.currentPhase;
    }
}
