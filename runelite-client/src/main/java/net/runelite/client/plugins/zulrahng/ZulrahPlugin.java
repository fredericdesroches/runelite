package net.runelite.client.plugins.zulrahng;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.inject.Inject;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.zulrahng.overlays.TestTilesOverlay;
import net.runelite.client.plugins.zulrahng.overlays.ZulrahCurrentPhaseOverlay;
import net.runelite.client.plugins.zulrahng.overlays.ZulrahNextPhaseOverlay;
import net.runelite.client.plugins.zulrahng.overlays.ZulrahTilesOverlay;
import net.runelite.client.plugins.zulrahng.patterns.ZulrahPattern;
import net.runelite.client.plugins.zulrahng.patterns.ZulrahPatternDetector;
import net.runelite.client.plugins.zulrahng.phases.Zulrah;
import net.runelite.client.plugins.zulrahng.phases.ZulrahPhaseUtils;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(name = "Zulrah NG", description = "Newgen Zulrah helper", loadWhenOutdated = true)
@Slf4j
public class ZulrahPlugin extends Plugin {
    private static final String ZULRAH_NPC_NAME = "Zulrah";

    @Inject
    private Client client;

    //	@Inject
    //	private QueryRunner queryRunner;

    @Getter
    private ZulrahInstance instance;

    // Overlays
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ZulrahTilesOverlay zulrahTilesOverlay;

    @Inject
    private ZulrahCurrentPhaseOverlay zulrahCurrentPhaseOverlay;

    @Inject
    private ZulrahNextPhaseOverlay zulrahNextPhaseOverlay;

    @Inject
    private TestTilesOverlay testTilesOverlay;

    @Override
    protected void startUp() throws Exception {
        this.overlayManager.add(this.zulrahTilesOverlay);
        this.overlayManager.add(this.zulrahCurrentPhaseOverlay);
        this.overlayManager.add(this.zulrahNextPhaseOverlay);
//        this.overlayManager.add(this.testTilesOverlay);

        this.instance = null;
    }

    @Override
    protected void shutDown() throws Exception {
        this.overlayManager.remove(this.zulrahTilesOverlay);
        this.overlayManager.remove(this.zulrahCurrentPhaseOverlay);
        this.overlayManager.remove(this.zulrahNextPhaseOverlay);
//        this.overlayManager.remove(this.testTilesOverlay);

        this.instance = null;
    }

    @Subscribe
    public void onNpcSpawned(final NpcSpawned event) {
        final NPC npc = event.getNpc();

        if (npc.getName() != null && npc.getName().equalsIgnoreCase(ZULRAH_NPC_NAME)) {
            log.info("Zulrah NG -- New instance detected.");
            this.instance = new ZulrahInstance(npc);
        }
    }

    @Subscribe
    public void onNpcDespawned(final NpcDespawned event) {
        final NPC npc = event.getNpc();

        if (npc.getName() != null && npc.getName().equalsIgnoreCase(ZULRAH_NPC_NAME)) {
            log.info("Zulrah NG -- Instance end detected.");
            this.instance = null;
        }
    }

    @Schedule(period = 600, unit = ChronoUnit.MILLIS)
    public void update() {
        if (this.instance == null) {
            log.debug("No ongoing Zulrah instance.");
            return;
        }

        LocalPoint localPoint = client.getLocalPlayer().getLocalLocation();
        log.info("[Player] LocalPoint X: {}, Y: {}", localPoint.getX(), localPoint.getY());

        //		optZulrahNpc.ifPresent(npc -> {
        //			final Optional<Zulrah> optionalZulrah = ZulrahUtility.getZulrah(npc);
        //
        //			final WorldPoint worldLocation = npc.getWorldLocation();
        //			log.debug("Zulrah NG -- [{}] {} @ WorldPoint X: {}, Y: {}, Z: {}, Plane: {}", npc.getId(), npc.getName(), worldLocation.getX(), worldLocation.getY(), worldLocation.getPlane());
        //
        //			final LocalPoint localPoint = npc.getLocalLocation();
        //			log.debug("Zulrah NG -- [{}] {} @ LocalPoint X: {}, Y: {}, Z: {}, Plane: {}", npc.getId(), npc.getName(), localPoint.getX(), localPoint.getY(), worldLocation.getPlane());
        //		});

        final NPC zulrahNpc = this.instance.getNpc();

        // Step 1 : Get current phase
        final Optional<Zulrah> optUpdatedZulrah = ZulrahPhaseUtils.getZulrah(zulrahNpc);
        if (!optUpdatedZulrah.isPresent()) {
            log.debug("No phase found.");
            return;
        }

        // Step 2 : Check if new phase
        final Zulrah zulrah = optUpdatedZulrah.get();

        // Same phase
        if (this.instance.getCurrentPhase().getZulrah().equals(zulrah)) {
            return;
        }

        // New phase
        final int newPhaseCounter = this.instance.incrementPhaseCounter();
        log.info("[New phase detected] New counter : {}", newPhaseCounter);

        // Matched pattern
        if (this.instance.getMatchedPattern() == null) {
            // Find pattern and set pattern
            final Optional<ZulrahPattern> potentialPattern = ZulrahPatternDetector.findPattern(zulrah, newPhaseCounter);

            potentialPattern.ifPresent(pattern -> {
                log.info("[Pattern] Setting pattern : {}", pattern);
                this.instance.setPattern(pattern);
            });

        }

        this.instance.incrementPhase();
    }
}
