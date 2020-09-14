package net.runelite.client.plugins.zulrahng.patterns;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.zulrahng.phases.Zulrah;

@Slf4j
public final class ZulrahPatternDetector
{
	private static final List<ZulrahPattern> patterns = Arrays.asList(
		new ZulrahPatternA(),
		new ZulrahPatternB(),
		new ZulrahPatternC(),
		new ZulrahPatternD()
	);

	// Given Zulrah
	// Given a phase counter
	// Then find one unique pattern
	public static Optional<ZulrahPattern> findPattern(final Zulrah updatedZulrah, final int phaseCounter)
	{
		log.info("[Pattern Detector] Finding pattern for phase {}, phase counter {}", updatedZulrah, phaseCounter);

		List<ZulrahPattern> potentialPatterns = patterns.stream()
			.filter(pattern -> pattern.getPhase(phaseCounter).getZulrah().equals(updatedZulrah))
			.collect(Collectors.toList());

		if (potentialPatterns.size() == 1)
		{
			log.info("[Pattern Detector] Found pattern : {}", potentialPatterns.get(0));
			return Optional.of(potentialPatterns.get(0));
		}

		log.info("[Pattern Detector] No pattern found");

		return Optional.empty();
	}
}
