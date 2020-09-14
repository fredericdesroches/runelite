package net.runelite.client.plugins.zulrahng.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.inject.Inject;

import net.runelite.client.plugins.zulrahng.ZulrahInstance;
import net.runelite.client.plugins.zulrahng.ZulrahPlugin;
import net.runelite.client.plugins.zulrahng.patterns.ZulrahPattern;
import net.runelite.client.plugins.zulrahng.phases.ZulrahPhase;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class ZulrahNextPhaseOverlay extends Overlay {
    private final ZulrahPlugin plugin;
    private final ZulrahOverlayUtility zulrahOverlayUtility;

    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    public ZulrahNextPhaseOverlay(final ZulrahPlugin plugin, final ZulrahOverlayUtility zulrahOverlayUtility) {
        this.plugin = plugin;
        this.zulrahOverlayUtility = zulrahOverlayUtility;

        setPosition(OverlayPosition.BOTTOM_LEFT);
        setPriority(OverlayPriority.MED);

        //		this.panelComponent.setOrientation(PanelComponent.Orientation.HORIZONTAL);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        final ZulrahInstance instance = this.plugin.getInstance();

        if (instance == null) {
            return null;
        }

        if (instance.getMatchedPattern() == null) {
            return null;
        }

        final ZulrahPattern pattern = instance.getPattern();
        final int currentPhaseCounter = instance.getCurrentPhaseCounter();
        final ZulrahPhase phase = pattern.getNextPhase(currentPhaseCounter);

        this.panelComponent.getChildren().clear();
        this.panelComponent.getChildren().add(
            TitleComponent.builder().text("Next" + (phase.isJad() ? " : JAD" : "")).color(Color.RED)
                .preferredSize(new Dimension(64, 0)).build());

        final BufferedImage img = this.zulrahOverlayUtility.getPrayerImage(phase);

        if (img != null) {
            this.panelComponent.getChildren().add(new ImageComponent(this.zulrahOverlayUtility.getPrayerImage(phase)));
        }

        return panelComponent.render(graphics);
    }
}
