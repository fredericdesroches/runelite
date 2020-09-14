package net.runelite.client.plugins.zulrahng.phases;

import lombok.Data;
import lombok.Getter;
import net.runelite.api.Prayer;

@Data
public class ZulrahPhase
{
	private final Zulrah zulrah;
	private final boolean jad;
	private final PlayerStandLocation playerStandLocation;
	private final Prayer prayer;
}
