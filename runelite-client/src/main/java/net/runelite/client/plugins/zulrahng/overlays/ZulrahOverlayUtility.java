package net.runelite.client.plugins.zulrahng.overlays;

import java.awt.image.BufferedImage;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.runelite.api.Prayer;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.zulrahng.phases.ZulrahPhase;

@Singleton
public class ZulrahOverlayUtility {
    @Inject
    private SpriteManager spriteManager;

    public BufferedImage getPrayerImage(final ZulrahPhase phase) {
        final Prayer prayer = phase.getPrayer();

        switch (prayer) {
        case PROTECT_FROM_MISSILES:
            return spriteManager.getSprite(SpriteID.PRAYER_PROTECT_FROM_MISSILES, 0);
        case PROTECT_FROM_MAGIC:
            return spriteManager.getSprite(SpriteID.PRAYER_PROTECT_FROM_MAGIC, 0);
        default:
            return null;
        }
    }
}
