/*
 * Copyright (c) 2013 Charles Lukas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.tobecontinued.util.ranges.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tobecontinued.util.ranges.core.RangeNode.EndPointType;

/**
 * Test class that exercises the Range generic class.
 * 
 * Due to the Range class being generic, the tests will be executed with Integer
 * parameters.
 * 
 * Copyright (c) 2013 Charles Lukas All Rights Reserved
 * 
 * @author Charles Lukas
 */
public class TU_Range {

    @Test
    public void testNewRange() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        Range<Integer> range = Range.newRange(BOUNDARY_LOWER, BOUNDARY_UPPER);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        RangeNode<Integer> lowerNode = range.lowerNode();
        RangeNode<Integer> upperNode = range.upperNode();

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals("LOWER ENDPOINT is incorrect.", range.lowerEndpoint(), BOUNDARY_LOWER);
        assertEquals("UPPER ENDPOINT is incorrect.", range.upperEndpoint(), BOUNDARY_UPPER);

        assertNotNull("LOWER NODE should exist.", lowerNode);
        assertEquals("LOWER NODE ENDPOINT is incorrect.", lowerNode.getEndpoint(), BOUNDARY_LOWER);
        assertEquals("LOWER NODE ENDPOINT TYPE is incorrect.", lowerNode.getEndpointType(), EndPointType.LOWER);
        assertEquals("LOWER NODE RANGE is incorrect.", lowerNode.getRange(), range);
        assertEquals("LOWER NODE SIBLING is incorrect.", lowerNode.getSiblingEndpoint(), upperNode);

        assertNotNull("UPPER NODE should exist", upperNode);
        assertEquals("UPPER NODE ENDPOINT is incorrect.", upperNode.getEndpoint(), BOUNDARY_UPPER);
        assertEquals("UPPER NODE ENDPOINT TYPE is incorrect.", upperNode.getEndpointType(), EndPointType.UPPER);
        assertEquals("UPPER NODE RANGE is incorrect.", upperNode.getRange(), range);
        assertEquals("UPPER NODE SIBLING is incorrect.", upperNode.getSiblingEndpoint(), lowerNode);

    }

    @Test
    public void testHashCode() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        Range<Integer> rangeNull = Range.newRange(null, null);
        Range<Integer> rangeValid = Range.newRange(BOUNDARY_LOWER, BOUNDARY_UPPER);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        int hashCodeNulls = rangeNull.hashCode();
        int hashCodeNotNulls = rangeValid.hashCode();

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertTrue("RANGE HASHCODE NULLS is incorrect.", 0 < hashCodeNulls);
        assertTrue("RANGE HASHCODE is incorrect.", 0 < hashCodeNotNulls);

    }

    @Test
    public void testEquals() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        Range<Integer> rangeNull = null;
        Range<Integer> rangeValid = Range.newRange(BOUNDARY_LOWER, BOUNDARY_UPPER);
        Range<Integer> rangeNullLower = Range.newRange(null, BOUNDARY_UPPER);
        Range<Integer> rangeNullUpper = Range.newRange(BOUNDARY_LOWER, null);
        Range<Integer> rangeDiffLower = Range.newRange(BOUNDARY_LOWER_DIFF, BOUNDARY_UPPER);
        Range<Integer> rangeDiffUpper = Range.newRange(BOUNDARY_LOWER, BOUNDARY_UPPER_DIFF);
        Range<Integer> rangeValidEqual = Range.newRange(BOUNDARY_LOWER, BOUNDARY_UPPER);
        Range<Integer> rangeNullLowerSameUpper = Range.newRange(null, BOUNDARY_UPPER);
        Range<Integer> rangeNullUpperSameLower = Range.newRange(BOUNDARY_LOWER, null);

        Range<String> rangeValidDiffType = Range.newRange(BOUNDARY_LOWER.toString(), BOUNDARY_UPPER.toString());

        // 2. EXECUTE /////////////////////////////////////////////////////////

        // 3. VERIFY //////////////////////////////////////////////////////////

        assertFalse("NULL scenario is incorrect.", rangeValid.equals(rangeNull));
        assertFalse("INSTANCEOF scenario is incorrect.", rangeValid.equals(1));
        assertFalse("INSTANCEOF scenario is incorrect.", rangeValid.equals(rangeValidDiffType));

        assertFalse("NULL LOWER RANGE scenario is incorrect.", rangeValid.equals(rangeNullLower));
        assertFalse("NULL LOWER RANGE symmetric scenario is incorrect.", rangeNullLower.equals(rangeValid));
        assertTrue("NULL LOWER RANGE SAME UPPER scenario is incorrect.", rangeNullLowerSameUpper.equals(rangeNullLower));
        assertTrue("NULL LOWER RANGE SAME UPPER symmetric scenario is incorrect.",
                rangeNullLower.equals(rangeNullLowerSameUpper));

        assertFalse("NULL UPPER RANGE scenario is incorrect.", rangeValid.equals(rangeNullUpper));
        assertFalse("NULL UPPER RANGE symmetric scenario is incorrect.", rangeNullUpper.equals(rangeValid));
        assertTrue("NULL UPPER RANGE SAME LOWER scenario is incorrect.", rangeNullUpperSameLower.equals(rangeNullUpper));
        assertTrue("NULL UPPER RANGE SAME LOWER symmetric scenario is incorrect.",
                rangeNullUpper.equals(rangeNullUpperSameLower));

        assertFalse("LOWER RANGE scenario is incorrect.", rangeValid.equals(rangeDiffLower));
        assertFalse("LOWER RANGE symmetric scenario is incorrect.", rangeDiffLower.equals(rangeValid));

        assertFalse("UPPER RANGE scenario is incorrect.", rangeValid.equals(rangeDiffUpper));
        assertFalse("UPPER RANGE symmetric scenario is incorrect.", rangeDiffUpper.equals(rangeValid));

        assertTrue("EQUAL RANGE scenario is incorrect.", rangeValid.equals(rangeValidEqual));
        assertTrue("EQUAL RANGE symmetric scenario is incorrect.", rangeValidEqual.equals(rangeValid));

        assertTrue("SAME RANGE OBJECT scenario is incorrect.", rangeValid.equals(rangeValid));

    }

    @Test
    public void testIntersects() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        Range<Integer> range = Range.newRange(BOUNDARY_LOWER, BOUNDARY_UPPER);
        Range<Integer> rangeNoInterLower = Range.newRange(NO_INTERSECT_LOWER_LOWER, NO_INTERSECT_LOWER_UPPER);
        Range<Integer> rangeNoInterUpper = Range.newRange(NO_INTERSECT_UPPER_LOWER, NO_INTERSECT_UPPER_UPPER);
        Range<Integer> rangeInterLower = Range.newRange(INTERSECT_LOWER_LOWER, INTERSECT_LOWER_UPPER);
        Range<Integer> rangeInterUpper = Range.newRange(INTERSECT_UPPER_LOWER, INTERSECT_UPPER_UPPER);
        Range<Integer> rangeInterMiddle = Range.newRange(INTERSECT_MIDDLE_LOWER, INTERSECT_MIDDLE_UPPER);

        // 2. EXECUTE /////////////////////////////////////////////////////////

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertTrue("Initial range lower end point should be greater than intersecting range upper end point.", range
                .lowerEndpoint().compareTo(rangeNoInterLower.upperEndpoint()) > 0);
        assertFalse("Range should not intersect on the lower side of the initial range.",
                range.intersects(rangeNoInterLower));

        assertTrue("Initial range upper end point should be less than intersecting range lower end point.", range
                .upperEndpoint().compareTo(rangeNoInterUpper.lowerEndpoint()) < 0);
        assertFalse("Range should not intersect on the upper side of the initial range.",
                range.intersects(rangeNoInterUpper));

        assertTrue("Initial range lower end point should be equal to intersecting range upper end point.", range
                .lowerEndpoint().compareTo(rangeInterLower.upperEndpoint()) == 0);
        assertTrue("Range should intersect only on the lower boundary of the initial range.",
                range.intersects(rangeInterLower));

        assertTrue("Initial range upper end point should be equal to intersecting range lower end point.", range
                .upperEndpoint().compareTo(rangeInterUpper.lowerEndpoint()) == 0);
        assertTrue("Range should intersect only on the upper boundary of the initial range.",
                range.intersects(rangeInterUpper));

        assertTrue("Initial range lower end point should be less than intersecting range lower end point.", range
                .lowerEndpoint().compareTo(rangeInterMiddle.lowerEndpoint()) < 0);
        assertTrue("Initial range upper end point should be greater than intersecting range upper end point.", range
                .upperEndpoint().compareTo(rangeInterMiddle.upperEndpoint()) > 0);
        assertTrue("Range should intersect inside the initial range.", range.intersects(rangeInterMiddle));

        assertTrue("Initial range lower end point should be equal to intersecting range lower end point.", range
                .lowerEndpoint().compareTo(range.lowerEndpoint()) == 0);
        assertTrue("Initial range upper end point should be equal to intersecting range upper end point.", range
                .upperEndpoint().compareTo(range.upperEndpoint()) == 0);
        assertTrue("Range should exactly intersect the initial range.", range.intersects(range));

    }

    @Test
    public void testContains() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        Range<Integer> range = Range.newRange(BOUNDARY_LOWER, BOUNDARY_UPPER);
        Range<Integer> rangeNoInterLower = Range.newRange(NO_INTERSECT_LOWER_LOWER, NO_INTERSECT_LOWER_UPPER);
        Range<Integer> rangeNoInterUpper = Range.newRange(NO_INTERSECT_UPPER_LOWER, NO_INTERSECT_UPPER_UPPER);
        Range<Integer> rangeInterLower = Range.newRange(INTERSECT_LOWER_LOWER, INTERSECT_LOWER_UPPER);
        Range<Integer> rangeInterUpper = Range.newRange(INTERSECT_UPPER_LOWER, INTERSECT_UPPER_UPPER);
        Range<Integer> rangeInterMiddle = Range.newRange(INTERSECT_MIDDLE_LOWER, INTERSECT_MIDDLE_UPPER);

        // 2. EXECUTE /////////////////////////////////////////////////////////

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertTrue("Initial range lower end point should be greater than containing range upper end point.", range
                .lowerEndpoint().compareTo(rangeNoInterLower.upperEndpoint()) > 0);
        assertFalse("Range should not contain on the lower side of the initial range.",
                range.contains(rangeNoInterLower));

        assertTrue("Initial range upper end point should be less than containing range lower end point.", range
                .upperEndpoint().compareTo(rangeNoInterUpper.lowerEndpoint()) < 0);
        assertFalse("Range should not contain on the upper side of the initial range.",
                range.contains(rangeNoInterUpper));

        assertTrue("Initial range lower end point should be equal to containing range upper end point.", range
                .lowerEndpoint().compareTo(rangeInterLower.upperEndpoint()) == 0);
        assertFalse("Range should not contain on the lower boundary of the initial range.",
                range.contains(rangeInterLower));

        assertTrue("Initial range upper end point should be equal to containing range lower end point.", range
                .upperEndpoint().compareTo(rangeInterUpper.lowerEndpoint()) == 0);
        assertFalse("Range should not contain on the upper boundary of the initial range.",
                range.contains(rangeInterUpper));

        assertTrue("Initial range lower end point should be less than containing range lower end point.", range
                .lowerEndpoint().compareTo(rangeInterMiddle.lowerEndpoint()) < 0);
        assertTrue("Initial range upper end point should be greater than containing range upper end point.", range
                .upperEndpoint().compareTo(rangeInterMiddle.upperEndpoint()) > 0);
        assertTrue("Range should contain inside the initial range.", range.contains(rangeInterMiddle));

        assertTrue("Initial range lower end point should be equal to containing range lower end point.", range
                .lowerEndpoint().compareTo(range.lowerEndpoint()) == 0);
        assertTrue("Initial range upper end point should be equal to containing range upper end point.", range
                .upperEndpoint().compareTo(range.upperEndpoint()) == 0);
        assertTrue("Range should exactly contain the initial range.", range.contains(range));

    }

    private static final Integer BOUNDARY_LOWER           = 0;
    private static final Integer BOUNDARY_UPPER           = 10;

    private static final Integer BOUNDARY_LOWER_DIFF      = 1;
    private static final Integer BOUNDARY_UPPER_DIFF      = 9;

    private static final Integer NO_INTERSECT_LOWER_LOWER = -4;
    private static final Integer NO_INTERSECT_LOWER_UPPER = -1;
    private static final Integer NO_INTERSECT_UPPER_LOWER = 11;
    private static final Integer NO_INTERSECT_UPPER_UPPER = 14;

    private static final Integer INTERSECT_LOWER_LOWER    = -4;
    private static final Integer INTERSECT_LOWER_UPPER    = 0;
    private static final Integer INTERSECT_UPPER_LOWER    = 10;
    private static final Integer INTERSECT_UPPER_UPPER    = 14;
    private static final Integer INTERSECT_MIDDLE_LOWER   = 2;
    private static final Integer INTERSECT_MIDDLE_UPPER   = 6;

}
