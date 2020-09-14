/*
 * Copyright (c) 2017, Aria <aria@ar1as.space>
 * Copyright (c) 2017, Devin French <https://github.com/devinfrench>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.zulrahng.patterns;

import net.runelite.api.Prayer;
import net.runelite.client.plugins.zulrahng.phases.PlayerStandLocation;
import net.runelite.client.plugins.zulrahng.phases.ZulrahLocation;
import net.runelite.client.plugins.zulrahng.phases.ZulrahType;

public class ZulrahPatternC extends ZulrahPattern {
    public ZulrahPatternC() {
        add(ZulrahType.RANGE, ZulrahLocation.NORTH, PlayerStandLocation.TOP_EAST, Prayer.PROTECT_FROM_MISSILES);
        add(ZulrahType.RANGE, ZulrahLocation.EAST, PlayerStandLocation.TOP_EAST, Prayer.PROTECT_FROM_MISSILES);
        add(ZulrahType.MELEE, ZulrahLocation.NORTH, PlayerStandLocation.TOP_WEST, null);
        add(ZulrahType.MAGIC, ZulrahLocation.WEST, PlayerStandLocation.TOP_WEST, Prayer.PROTECT_FROM_MAGIC);
        add(ZulrahType.RANGE, ZulrahLocation.SOUTH, PlayerStandLocation.PILLAR_EAST_INSIDE, Prayer.PROTECT_FROM_MISSILES);
        add(ZulrahType.MAGIC, ZulrahLocation.EAST, PlayerStandLocation.PILLAR_EAST_INSIDE, Prayer.PROTECT_FROM_MAGIC);
        add(ZulrahType.RANGE, ZulrahLocation.NORTH, PlayerStandLocation.PILLAR_WEST_INSIDE, null);
        add(ZulrahType.RANGE, ZulrahLocation.WEST, PlayerStandLocation.PILLAR_WEST_INSIDE,
            Prayer.PROTECT_FROM_MISSILES);
        add(ZulrahType.MAGIC, ZulrahLocation.NORTH, PlayerStandLocation.PILLAR_EAST_INSIDE, Prayer.PROTECT_FROM_MAGIC);
        addJad(ZulrahType.RANGE, ZulrahLocation.EAST, PlayerStandLocation.PILLAR_EAST_INSIDE, Prayer.PROTECT_FROM_MAGIC);
        add(ZulrahType.MAGIC, ZulrahLocation.NORTH, PlayerStandLocation.TOP_EAST, null);
    }

    @Override
    public String toString() {
        return "Zulrah Pattern C";
    }
}
