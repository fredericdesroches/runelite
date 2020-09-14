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

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.coords.LocalPoint;

@Slf4j
public enum ZulrahLocation
{
	NORTH(new LocalPoint(6720, 7616)),
	SOUTH(new LocalPoint(6720, 6208)),
	EAST(new LocalPoint(8000, 7360)),
	WEST(new LocalPoint(5440, 7360));

	@Getter
	private final LocalPoint localPoint;

	ZulrahLocation(final LocalPoint location) {
		this.localPoint = location;
	}

	public static Optional<ZulrahLocation> valueOf(final LocalPoint localPoint)
	{
//		return Arrays.stream(ZulrahLocation.values())
//			.filter(location -> location.getLocalPoint().getX() == localPoint.getX() && location.getLocalPoint().getY() == localPoint.getY())
//			.findFirst();

		if (localPoint.getX() == 6720 && localPoint.getY() == 7616)
		{
			return Optional.of(NORTH);
		}

		if (localPoint.getX() == 6720 && localPoint.getY() == 6208)
		{
			return Optional.of(SOUTH);
		}

		if (localPoint.getX() == 8000 && localPoint.getY() == 7360)
		{
			return Optional.of(EAST);
		}

		if (localPoint.getX() == 5440 && localPoint.getY() == 7360)
		{
			return Optional.of(WEST);
		}

		return Optional.empty();
	}
}
