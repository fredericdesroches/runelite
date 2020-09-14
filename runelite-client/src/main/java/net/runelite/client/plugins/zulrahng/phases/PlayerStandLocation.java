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
package net.runelite.client.plugins.zulrahng.phases;

import lombok.Getter;
import net.runelite.api.coords.LocalPoint;

public enum PlayerStandLocation
{
	WEST(new LocalPoint(1, 1)),
	EAST(new LocalPoint(1, 1)),
	SOUTH(new LocalPoint(6720, 6848)),
	SOUTH_WEST(new LocalPoint(6080, 7488)),
	SOUTH_EAST(new LocalPoint(7360, 7488)),
	TOP_EAST(new LocalPoint(7488, 7872)),
	TOP_WEST(new LocalPoint(6208, 8000)),
	PILLAR_WEST_INSIDE(new LocalPoint(6208, 7232)),
	PILLAR_WEST_OUTSIDE(new LocalPoint(6208, 6976)),
	PILLAR_EAST_INSIDE(new LocalPoint(7232, 7232)),
	PILLAR_EAST_OUTSIDE(new LocalPoint(7232, 6976)),
	BETWEEN_PILLAR_WEST(new LocalPoint(6080, 7104));

	@Getter
	private final LocalPoint location;

	PlayerStandLocation(LocalPoint location)
	{
		this.location = location;
	}
}
