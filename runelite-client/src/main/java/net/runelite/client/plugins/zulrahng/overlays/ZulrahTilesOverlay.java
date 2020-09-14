package net.runelite.client.plugins.zulrahng.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.annotation.Nonnull;
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

        // TODO: Show current if diffrent than next, otherwise show next
        this.drawPlayerLocation(graphics, instance);
        this.drawBossLocation(graphics, instance);

        return null;
    }

    private void drawPlayerLocation(final Graphics2D graphics, final ZulrahInstance instance) {
        final ZulrahPattern pattern = instance.getPattern();
        final int currentPhaseCounter = instance.getCurrentPhaseCounter();

        final ZulrahPhase currentPhase = pattern.getPhase(currentPhaseCounter);
        final ZulrahPhase nextPhase = pattern.getNextPhase(currentPhaseCounter);

        final String currentString = currentPhase.isJad() ? "Current JAD" : "Current";
        final String nextString = nextPhase.isJad() ? "Next JAD" : "Next";

        if (currentPhase.getPlayerStandLocation() == nextPhase.getPlayerStandLocation()) {
            // draw split
            this.drawHalfTile(graphics, currentPhase.getPlayerStandLocation().getLocation(),
                String.format("%s | %s", currentString, nextString),
                ZulrahPhaseUtils.getZulrahColor(currentPhase.getZulrah()),
                ZulrahPhaseUtils.getZulrahColor(nextPhase.getZulrah()));
        } else {
            this.drawTile(graphics, currentPhase.getPlayerStandLocation().getLocation(), currentString,
                ZulrahPhaseUtils.getZulrahColor(currentPhase.getZulrah()));

            this.drawTile(graphics, nextPhase.getPlayerStandLocation().getLocation(), nextString,
                ZulrahPhaseUtils.getZulrahColor(nextPhase.getZulrah()));
        }
    }

    private void drawBossLocation(final Graphics2D graphics, final ZulrahInstance instance) {
        final ZulrahPattern pattern = instance.getPattern();
        final int currentPhaseCounter = instance.getCurrentPhaseCounter();

        final ZulrahPhase currentPhase = pattern.getPhase(currentPhaseCounter);
        final ZulrahPhase nextPhase = pattern.getNextPhase(currentPhaseCounter);

        final String currentString = currentPhase.isJad() ? "Current JAD" : "Current";
        final String nextString = nextPhase.isJad() ? "Next JAD" : "Next";

        if (currentPhase.getZulrah().getZulrahLocation() == nextPhase.getZulrah().getZulrahLocation()) {
            // draw split
            this.drawHalfTile(graphics, currentPhase.getZulrah().getZulrahLocation().getLocalPoint(),
                String.format("%s | %s", currentString, nextString),
                ZulrahPhaseUtils.getZulrahColor(currentPhase.getZulrah()),
                ZulrahPhaseUtils.getZulrahColor(nextPhase.getZulrah()));
        } else {
            this.drawTile(graphics, currentPhase.getZulrah().getZulrahLocation().getLocalPoint(), currentString,
                ZulrahPhaseUtils.getZulrahColor(currentPhase.getZulrah()));

            this.drawTile(graphics, nextPhase.getZulrah().getZulrahLocation().getLocalPoint(), nextString,
                ZulrahPhaseUtils.getZulrahColor(nextPhase.getZulrah()));
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

    private void drawHalfTile(final Graphics2D graphics, final LocalPoint location, final String text,
        final Color currentColor, final Color nextColor) {
        final Polygon left = getHalfPolyLeft(this.client, location, 1);
        final Polygon right = getHalfPolyRight(this.client, location, 1);

        if (left != null && right != null) {
            OverlayUtil.renderPolygon(graphics, left, currentColor, currentColor);
            OverlayUtil.renderPolygon(graphics, right, nextColor, nextColor);
        }

        Point textLocation = Perspective.getCanvasTextLocation(client, graphics, location, text, 0);
        if (textLocation != null) {
            OverlayUtil.renderTextLocation(graphics, textLocation, text, Color.WHITE);
        }
    }

    private static Polygon getHalfPolyLeft(
        @Nonnull
            Client client,
        @Nonnull
            LocalPoint localLocation, int size) {
        int plane = client.getPlane();
        int swX = localLocation.getX() - size * 128 / 2;
        int swY = localLocation.getY() - size * 128 / 2;
        int neX = localLocation.getX() + size * 128 / 2;
        int neY = localLocation.getY() + size * 128 / 2;
        byte[][][] tileSettings = client.getTileSettings();
        int sceneX = localLocation.getSceneX();
        int sceneY = localLocation.getSceneY();
        if (sceneX >= 0 && sceneY >= 0 && sceneX < 104 && sceneY < 104) {
            int tilePlane = plane;
            if (plane < 3 && (tileSettings[1][sceneX][sceneY] & 2) == 2) {
                tilePlane = plane + 1;
            }

            int midx = swX + ((neX - swX) / 2);
            neX = midx;

            int swHeight = getHeight(client, swX, swY, tilePlane);
            int nwHeight = getHeight(client, neX, swY, tilePlane);
            int neHeight = getHeight(client, neX, neY, tilePlane);
            int seHeight = getHeight(client, swX, neY, tilePlane);

            Point p1 = Perspective.localToCanvas(client, swX, swY, swHeight);
            Point p2 = Perspective.localToCanvas(client, neX, swY, nwHeight);
            Point p3 = Perspective.localToCanvas(client, neX, neY, neHeight);
            Point p4 = Perspective.localToCanvas(client, swX, neY, seHeight);
            if (p1 != null && p2 != null && p3 != null && p4 != null) {
                Polygon poly = new Polygon();
                poly.addPoint(p1.getX(), p1.getY());
                poly.addPoint(p2.getX(), p2.getY());
                poly.addPoint(p3.getX(), p3.getY());
                poly.addPoint(p4.getX(), p4.getY());
                return poly;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static Polygon getHalfPolyRight(
        @Nonnull
            Client client,
        @Nonnull
            LocalPoint localLocation, int size) {
        int plane = client.getPlane();
        int swX = localLocation.getX() - size * 128 / 2;
        int swY = localLocation.getY() - size * 128 / 2;
        int neX = localLocation.getX() + size * 128 / 2;
        int neY = localLocation.getY() + size * 128 / 2;
        byte[][][] tileSettings = client.getTileSettings();
        int sceneX = localLocation.getSceneX();
        int sceneY = localLocation.getSceneY();
        if (sceneX >= 0 && sceneY >= 0 && sceneX < 104 && sceneY < 104) {
            int tilePlane = plane;
            if (plane < 3 && (tileSettings[1][sceneX][sceneY] & 2) == 2) {
                tilePlane = plane + 1;
            }

            int midx = swX + ((neX - swX) / 2);
            swX = midx;

            int swHeight = getHeight(client, swX, swY, tilePlane);
            int nwHeight = getHeight(client, neX, swY, tilePlane);
            int neHeight = getHeight(client, neX, neY, tilePlane);
            int seHeight = getHeight(client, swX, neY, tilePlane);

            Point p1 = Perspective.localToCanvas(client, swX, swY, swHeight);
            Point p2 = Perspective.localToCanvas(client, neX, swY, nwHeight);
            Point p3 = Perspective.localToCanvas(client, neX, neY, neHeight);
            Point p4 = Perspective.localToCanvas(client, swX, neY, seHeight);
            if (p1 != null && p2 != null && p3 != null && p4 != null) {
                Polygon poly = new Polygon();
                poly.addPoint(p1.getX(), p1.getY());
                poly.addPoint(p2.getX(), p2.getY());
                poly.addPoint(p3.getX(), p3.getY());
                poly.addPoint(p4.getX(), p4.getY());
                return poly;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static int getHeight(
        @Nonnull
            Client client, int localX, int localY, int plane) {
        int sceneX = localX >> 7;
        int sceneY = localY >> 7;
        if (sceneX >= 0 && sceneY >= 0 && sceneX < 104 && sceneY < 104) {
            int[][][] tileHeights = client.getTileHeights();
            int x = localX & 127;
            int y = localY & 127;
            int var8 = x * tileHeights[plane][sceneX + 1][sceneY] + (128 - x) * tileHeights[plane][sceneX][sceneY] >> 7;
            int var9 =
                tileHeights[plane][sceneX][sceneY + 1] * (128 - x) + x * tileHeights[plane][sceneX + 1][sceneY + 1]
                    >> 7;
            return (128 - y) * var8 + y * var9 >> 7;
        } else {
            return 0;
        }
    }
}
