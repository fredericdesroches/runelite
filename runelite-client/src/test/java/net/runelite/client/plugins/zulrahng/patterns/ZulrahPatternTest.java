package net.runelite.client.plugins.zulrahng.patterns;

import junit.framework.TestCase;

/*
 * [y] hybris Platform
 *
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */public class ZulrahPatternTest extends TestCase {

    public void testoverlow() {
        final ZulrahPatternD pattern = new ZulrahPatternD();

        System.out.println(pattern.getPhase(12));
        System.out.println(pattern.getNextPhase(11));
    }
}