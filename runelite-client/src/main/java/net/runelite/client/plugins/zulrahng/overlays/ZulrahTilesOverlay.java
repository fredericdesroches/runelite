package net.runelite.client.plugins.zulrahng.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.plugins.zulrahng.ZulrahInstance;
import net.runelite.client.plugins.zulrahng.ZulrahPlugin;
import net.runelite.client.plugins.zulrahng.patterns.ZulrahPattern;
import net.runelite.client.plugins.zulrahng.phases.ZulrahPhase;
import net.runelite.client.plugins.zulrahng.phases.ZulrahPhaseUtils;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

public class ZulrahTilesOverlay extends Overlay {
    private final Client client;
    private final ZulrahPlugin plugin;

    @Inject
    public ZulrahTilesOverlay(final Client client, final ZulrahPlugin plugin) {
        this.client = client;
        this.plugin = plugin;

        this.setPosition(OverlayPosition.DYNAMIC);
        this.setPriority(OverlayPriority.HIGH);
        this.setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        final ZulrahInstance instance = this.plugin.getInstance();
        if (instance == null) {
            return null;
        }

        this.drawPlayerLocation(graphics, instance);
        this.drawBossLocation(graphics, instance);

        return null;
    }

    private void drawPlayerLocation(final Graphics2D graphics, final ZulrahInstance instance) {
        final ZulrahPattern pattern = instance.getPattern();
        final int currentPhaseCounter = instance.getCurrentPhaseCounter();

        final ZulrahPhase currentPhase = pattern.getPhase(currentPhaseCounter);
        final ZulrahPhase nextPhase = pattern.getNextPhase(currentPhaseCounter);

        if (currentPhase.getPlayerStandLocation() != nextPhase.getPlayerStandLocation()) {
            final String nextString = nextPhase.isJad() ? "Next JAD" : "Next";
            this.drawTile(graphics, currentPhase.getPlayerStandLocation().getLocation(), "Current",
                ZulrahPhaseUtils.getZulrahColor(currentPhase.getZulrah()));
            this.drawTile(graphics, nextPhase.getPlayerStandLocation().getLocation(), nextString,
                ZulrahPhaseUtils.getZulrahColor(nextPhase.getZulrah()));
        } else {
            this.drawTile(graphics, currentPhase.getPlayerStandLocation().getLocation(), "Current / Next",
                ZulrahPhaseUtils.getZulrahColor(currentPhase.getZulrah()));
        }
    }

    private void drawBossLocation(final Graphics2D graphics, final ZulrahInstance instance) {
        final ZulrahPattern pattern = instance.getPattern();
        final int currentPhaseCounter = instance.getCurrentPhaseCounter();

        final ZulrahPhase currentPhase = pattern.getPhase(currentPhaseCounter);
        final ZulrahPhase nextPhase = pattern.getNextPhase(currentPhaseCounter);

        if (currentPhase.getZulrah().getZulrahLocation() != nextPhase.getZulrah().getZulrahLocation()) {
            final String nextString = nextPhase.isJad() ? "Next JAD" : "Next";
            this.drawTile(graphics, currentPhase.getZulrah().getZulrahLocation().getLocalPoint(), "Current",
                ZulrahPhaseUtils.getZulrahColor(currentPhase.getZulrah()));
            this.drawTile(graphics, nextPhase.getZulrah().getZulrahLocation().getLocalPoint(), nextString,
                ZulrahPhaseUtils.getZulrahColor(nextPhase.getZulrah()));
        } else {
            this.drawTile(graphics, currentPhase.getZulrah().getZulrahLocation().getLocalPoint(), "Current / Next",
                ZulrahPhaseUtils.getZulrahColor(currentPhase.getZulrah()));
        }
    }

    private void drawTile(final Graphics2D graphics, final LocalPoint location, final String text, final Color color) {
        final Polygon polygon = Perspective.getCanvasTilePoly(this.client, location);
        if (polygon != null) {
            OverlayUtil.renderPolygon(graphics, polygon, color);
        }

        Point textLocation = Perspective.getCanvasTextLocation(client, graphics, location, text, 0);
        if (textLocation != null) {
            OverlayUtil.renderTextLocation(graphics, textLocation, text, Color.WHITE);
        }
    }
}
