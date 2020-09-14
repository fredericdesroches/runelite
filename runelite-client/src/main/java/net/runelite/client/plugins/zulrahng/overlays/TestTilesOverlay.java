/*
 * [y] hybris Platform
 *
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package net.runelite.client.plugins.zulrahng.overlays;

import java.awt.*;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.plugins.zulrahng.ZulrahPlugin;
import net.runelite.client.plugins.zulrahng.phases.PlayerStandLocation;
import net.runelite.client.plugins.zulrahng.phases.ZulrahLocation;
import net.runelite.client.plugins.zulrahng.phases.ZulrahPhaseUtils;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

@Slf4j
public class TestTilesOverlay extends Overlay {

    private final Client client;
    private final ZulrahPlugin plugin;

    @Inject
    public TestTilesOverlay(final Client client, final ZulrahPlugin plugin) {
        this.client = client;
        this.plugin = plugin;

        this.setPosition(OverlayPosition.DYNAMIC);
        this.setPriority(OverlayPriority.HIGH);
        this.setLayer(OverlayLayer.ABOVE_SCENE);
    }


    @Override
    public Dimension render(final Graphics2D graphics) {
//        if (this.plugin.getInstance() == null) {
//            return null;
//        }

        this.drawTile(graphics, PlayerStandLocation.PILLAR_EAST_INSIDE.getLocation(), "Current pos",  new Color(150, 255, 0, 100));
        this.drawTile(graphics, PlayerStandLocation.PILLAR_WEST_INSIDE.getLocation(), "Next pos",   new Color(20, 170, 200, 100));

        this.drawTile(graphics, PlayerStandLocation.TOP_EAST.getLocation(), "Current \n Next pos",  new Color(180, 50, 20, 100));

        // current phase = east
        this.drawTile(graphics, ZulrahLocation.EAST.getLocalPoint(), "Current boss", new Color(150, 255, 0, 100));
        this.drawTile(graphics, ZulrahLocation.SOUTH.getLocalPoint(), "Next bpss", new Color(180, 50, 20, 100));

        this.drawTile(graphics, ZulrahLocation.NORTH.getLocalPoint(), "Current / Next boss", new Color(150, 255, 0, 100));

        // next phase = west

//        for (final PlayerStandLocation playerStandLocation : PlayerStandLocation.values()) {
//            this.drawTile(graphics, playerStandLocation.getLocation(), playerStandLocation.name(), new Color(20, 170, 200, 100));
//        }

        return null;
    }

    private void drawTile(final Graphics2D graphics, final LocalPoint location, final String text, final Color color)
    {
        final Polygon polygon = Perspective.getCanvasTilePoly(this.client, location);
        if (polygon != null)
        {
            OverlayUtil.renderPolygon(graphics, polygon, color);
        }

        Point textLocation = Perspective.getCanvasTextLocation(client, graphics, location, text, 0);
        if (textLocation != null)
        {
            OverlayUtil.renderTextLocation(graphics, textLocation, text, Color.WHITE);
        }
    }
}
