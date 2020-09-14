package net.runelite.client.plugins.zulrahng.phases;

import java.awt.Color;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NPC;

@Slf4j
public final class ZulrahPhaseUtils
{
	private static final Color RANGE_COLOR = new Color(150, 255, 0, 100);
	private static final Color MAGIC_COLOR = new Color(20, 170, 200, 100);
	private static final Color MELEE_COLOR = new Color(180, 50, 20, 100);
	private static final Color JAD_COLOR = new Color(255, 115, 0, 100);

	public static Optional<Zulrah> getZulrah(final NPC zulrah)
	{
		final Optional<ZulrahLocation> location = ZulrahLocation.valueOf(zulrah.getLocalLocation());
		final Optional<ZulrahType> type = ZulrahType.valueOf(zulrah.getId());

		if (!location.isPresent() || !type.isPresent())
		{
			return Optional.empty();
		}

		log.info("[Zulrah Phase] Type {}, Location {}", type, location);

		return Optional.of(new Zulrah(type.get(), location.get()));
	}

	public static Color getZulrahColor(final Zulrah zulrah)
	{
		switch(zulrah.getZulrahType())
		{
			case RANGE:
				return RANGE_COLOR;
			case MAGIC:
				return MAGIC_COLOR;
			case MELEE:
				return MELEE_COLOR;
		}

		return Color.PINK;
	}
}
