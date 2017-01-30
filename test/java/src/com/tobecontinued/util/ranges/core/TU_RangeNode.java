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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.tobecontinued.util.ranges.core.RangeNode.EndPointType;

/**
 * Test class that exercises the RangeNode class.
 * 
 * Due to the RangeNode class being generic, the tests will be executed with
 * Integer parameters.
 * 
 * Copyright (c) 2013 Charles Lukas All Rights Reserved
 * 
 * @author Charles Lukas
 */
public class TU_RangeNode {

    @SuppressWarnings("unchecked")
    public TU_RangeNode() {

        _range = Mockito.mock(Range.class);
        Mockito.when(_range.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return BOUNDARY_LOWER;
            }
        });
        Mockito.when(_range.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return BOUNDARY_UPPER;
            }
        });

        _rangeMiddle = Mockito.mock(Range.class);
        Mockito.when(_rangeMiddle.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return BOUNDARY_MIDDLE;
            }
        });
        Mockito.when(_rangeMiddle.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return BOUNDARY_MIDDLE;
            }
        });

        _rangeLower = Mockito.mock(Range.class);
        Mockito.when(_rangeLower.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return BOUNDARY_LOWER;
            }
        });
        Mockito.when(_rangeLower.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return BOUNDARY_LOWER;
            }
        });

        _rangeUpper = Mockito.mock(Range.class);
        Mockito.when(_rangeUpper.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return BOUNDARY_UPPER;
            }
        });
        Mockito.when(_rangeUpper.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return BOUNDARY_UPPER;
            }
        });

        _rangeNullEndPoint = Mockito.mock(Range.class);
        Mockito.when(_rangeNullEndPoint.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return null;
            }
        });
        Mockito.when(_rangeNullEndPoint.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return null;
            }
        });

    }

    @Test
    public void testNewRangeNode() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        final RangeNode<Integer> lowerNode = new RangeNode<Integer>(EndPointType.LOWER, _range);
        final RangeNode<Integer> upperNode = new RangeNode<Integer>(EndPointType.UPPER, _range);
        RangeNode<Integer> nullNode = new RangeNode<Integer>(null, _range);

        Mockito.when(_range.lowerNode()).thenAnswer(new Answer<RangeNode<Integer>>() {

            @Override
            public RangeNode<Integer> answer(InvocationOnMock invocation) {

                return lowerNode;
            }
        });
        Mockito.when(_range.upperNode()).thenAnswer(new Answer<RangeNode<Integer>>() {

            @Override
            public RangeNode<Integer> answer(InvocationOnMock invocation) {

                return upperNode;
            }
        });

        // 2. EXECUTE /////////////////////////////////////////////////////////

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals("LOWER NODE ENDPOINT is incorrect.", BOUNDARY_LOWER, lowerNode.getEndpoint());
        assertEquals("LOWER NODE ENDPOINT TYPE is incorrect.", EndPointType.LOWER, lowerNode.getEndpointType());
        assertEquals("LOWER NODE RANGE is incorrect.", _range, lowerNode.getRange());
        assertEquals("LOWER NODE SIBLING is incorrect.", upperNode, lowerNode.getSiblingEndpoint());

        assertEquals("UPPER NODE ENDPOINT is incorrect.", BOUNDARY_UPPER, upperNode.getEndpoint());
        assertEquals("UPPER NODE ENDPOINT TYPE is incorrect.", EndPointType.UPPER, upperNode.getEndpointType());
        assertEquals("UPPER NODE RANGE is incorrect.", _range, upperNode.getRange());
        assertEquals("UPPER NODE SIBLING is incorrect.", lowerNode, upperNode.getSiblingEndpoint());

        assertNull("NULL ENDPOINT TYPE NODE ENDPOINT is incorrect.", nullNode.getEndpoint());
        assertNull("NULL ENDPOINT TYPE NODE ENDPOINT TYPE is incorrect.", nullNode.getEndpointType());
        assertEquals("NULL ENDPOINT TYPE NODE RANGE is incorrect.", _range, nullNode.getRange());
        assertNull("NULL ENDPOINT TYPE NODE SIBLING is incorrect.", nullNode.getSiblingEndpoint());

    }

    @Test
    public void testCompareTo() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        RangeNode<Integer> nodeNull = null;
        RangeNode<Integer> nodeBaseLowerMiddle = new RangeNode<Integer>(EndPointType.LOWER, _rangeMiddle);
        RangeNode<Integer> nodeSameEndpointSameType = new RangeNode<Integer>(EndPointType.LOWER, _rangeMiddle);
        RangeNode<Integer> nodeSameEndpointDiffType = new RangeNode<Integer>(EndPointType.UPPER, _rangeMiddle);
        RangeNode<Integer> nodeLessLower = new RangeNode<Integer>(EndPointType.LOWER, _rangeLower);
        RangeNode<Integer> nodeMoreLower = new RangeNode<Integer>(EndPointType.LOWER, _rangeUpper);
        RangeNode<Integer> nodeLessUpper = new RangeNode<Integer>(EndPointType.UPPER, _rangeLower);
        RangeNode<Integer> nodeMoreUpper = new RangeNode<Integer>(EndPointType.UPPER, _rangeUpper);

        // 2. EXECUTE /////////////////////////////////////////////////////////

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals("COMPARE TO NULL scenario is incorrect.", 0, nodeBaseLowerMiddle.compareTo(nodeNull));
        assertEquals("COMPARE TO SELF scenario is incorrect.", 0, nodeBaseLowerMiddle.compareTo(nodeBaseLowerMiddle));

        assertEquals("COMPARE TO EQUAL scenario is incorrect.", 0,
                nodeBaseLowerMiddle.compareTo(nodeSameEndpointSameType));
        assertEquals("COMPARE TO EQUAL symmetric scenario is incorrect.", 0,
                nodeSameEndpointSameType.compareTo(nodeBaseLowerMiddle));

        assertEquals("COMPARE TO SAME ENDPOINT DIFF TYPE scenario is incorrect. LOWER should come before UPPER.", -1,
                nodeBaseLowerMiddle.compareTo(nodeSameEndpointDiffType));
        assertEquals(
                "COMPARE TO SAME ENDPOINT DIFF TYPE symmetric scenario is incorrect.  UPPER should come after LOWER.",
                1, nodeSameEndpointDiffType.compareTo(nodeBaseLowerMiddle));

        assertEquals("COMPARE TO LESS LOWER scenario is incorrect.", 1, nodeBaseLowerMiddle.compareTo(nodeLessLower));
        assertEquals("COMPARE TO LESS LOWER symmetric scenario is incorrect.", -1,
                nodeLessLower.compareTo(nodeBaseLowerMiddle));

        nodeMoreLower.compareTo(nodeBaseLowerMiddle);

        assertEquals("COMPARE TO GREATER LOWER scenario is incorrect.", -1,
                nodeBaseLowerMiddle.compareTo(nodeMoreLower));
        assertEquals("COMPARE TO GREATER LOWER symmetric scenario is incorrect.", 1,
                nodeMoreLower.compareTo(nodeBaseLowerMiddle));

        assertEquals("COMPARE TO LESS UPPER scenario is incorrect.", 1, nodeBaseLowerMiddle.compareTo(nodeLessUpper));
        assertEquals("COMPARE TO LESS UPPER symmetric scenario is incorrect.", -1,
                nodeLessUpper.compareTo(nodeBaseLowerMiddle));

        assertEquals("COMPARE TO GREATER UPPER scenario is incorrect.", -1,
                nodeBaseLowerMiddle.compareTo(nodeMoreUpper));
        assertEquals("COMPARE TO GREATER UPPER symmetric scenario is incorrect.", 1,
                nodeMoreUpper.compareTo(nodeBaseLowerMiddle));

    }

    @Test
    public void testHashCode() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        RangeNode<Integer> nodeBaseNulls = new RangeNode<Integer>(null, null);
        RangeNode<Integer> nodeBaseNullType = new RangeNode<Integer>(null, _rangeMiddle);
        RangeNode<Integer> nodeBaseLower = new RangeNode<Integer>(EndPointType.LOWER, _rangeMiddle);
        RangeNode<Integer> nodeNullBoundary = new RangeNode<Integer>(EndPointType.LOWER, _rangeNullEndPoint);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        int hashCodeNulls = nodeBaseNulls.hashCode();
        int hashCodeNullType = nodeBaseNullType.hashCode();
        int hashCodeLower = nodeBaseLower.hashCode();
        int hashCodeNullBoundary = nodeNullBoundary.hashCode();

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertTrue("RANGE NODE HASHCODE NULLS is incorrect.", 0 < hashCodeNulls);
        assertTrue("RANGE NODE HASHCODE NULL TYPE is incorrect.", 0 < hashCodeNullType);
        assertTrue("RANGE NODE HASHCODE WITH TYPE is incorrect.", 0 < hashCodeLower);
        assertTrue("RANGE NODE HASHCODE NULL BOUNDARY is incorrect.", 0 < hashCodeNullBoundary);

    }

    @Test
    public void testEquals() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        RangeNode<Integer> nodeNull = null;
        RangeNode<Integer> nodeBaseLowerMiddle = new RangeNode<Integer>(EndPointType.LOWER, _rangeMiddle);
        RangeNode<Integer> nodeBaseUpperMiddle = new RangeNode<Integer>(EndPointType.UPPER, _rangeMiddle);
        RangeNode<Integer> nodeBaseEqual = new RangeNode<Integer>(EndPointType.LOWER, _rangeMiddle);
        RangeNode<Integer> nodeLessLower = new RangeNode<Integer>(EndPointType.LOWER, _rangeLower);
        RangeNode<Integer> nodeMoreLower = new RangeNode<Integer>(EndPointType.LOWER, _rangeUpper);
        RangeNode<Integer> nodeLessUpper = new RangeNode<Integer>(EndPointType.UPPER, _rangeLower);
        RangeNode<Integer> nodeMoreUpper = new RangeNode<Integer>(EndPointType.UPPER, _rangeUpper);
        RangeNode<Integer> nodeNullLower = new RangeNode<Integer>(EndPointType.LOWER, _rangeNullEndPoint);
        RangeNode<Integer> nodeNullUpper = new RangeNode<Integer>(EndPointType.UPPER, _rangeNullEndPoint);
        RangeNode<Integer> nodeNullLowerEqual = new RangeNode<Integer>(EndPointType.LOWER, _rangeNullEndPoint);
        RangeNode<Integer> nodeNullUpperEqual = new RangeNode<Integer>(EndPointType.UPPER, _rangeNullEndPoint);

        // 2. EXECUTE /////////////////////////////////////////////////////////

        // 3. VERIFY //////////////////////////////////////////////////////////

        assertFalse("NULL scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeNull));
        assertFalse("INSTANCEOF scenario is incorrect.", nodeBaseLowerMiddle.equals(1));

        assertFalse("SAME BOUNDARY scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeBaseUpperMiddle));
        assertFalse("SAME BOUNDARY symmetric scenario is incorrect.", nodeBaseUpperMiddle.equals(nodeBaseLowerMiddle));

        assertFalse("LESS LOWER BOUNDARY scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeLessLower));
        assertFalse("LESS LOWER BOUNDARY symmetric scenario is incorrect.", nodeLessLower.equals(nodeBaseLowerMiddle));

        assertFalse("GREATER LOWER BOUNDARY scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeMoreLower));
        assertFalse("GREATER LOWER BOUNDARY symmetric scenario is incorrect.",
                nodeMoreLower.equals(nodeBaseLowerMiddle));

        assertFalse("LESS UPPER BOUNDARY scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeLessUpper));
        assertFalse("LESS UPPER BOUNDARY symmetric scenario is incorrect.", nodeLessUpper.equals(nodeBaseLowerMiddle));

        assertFalse("GREATER UPPER BOUNDARY scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeMoreUpper));
        assertFalse("GREATER UPPER BOUNDARY symmetric scenario is incorrect.",
                nodeMoreUpper.equals(nodeBaseLowerMiddle));

        assertFalse("NULL LOWER BOUNDARY scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeNullLower));
        assertFalse("NULL LOWER BOUNDARY symmetric scenario is incorrect.", nodeNullLower.equals(nodeBaseLowerMiddle));

        assertFalse("NULL UPPER BOUNDARY scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeNullUpper));
        assertFalse("NULL UPPER BOUNDARY symmetric scenario is incorrect.", nodeNullUpper.equals(nodeBaseLowerMiddle));

        assertTrue("NULL LOWER BOUNDARY DIFF OBJECT scenario is incorrect.", nodeNullLowerEqual.equals(nodeNullLower));
        assertTrue("NULL LOWER BOUNDARY DIFF OBJECT symmetric scenario is incorrect.",
                nodeNullLower.equals(nodeNullLowerEqual));

        assertTrue("NULL UPPER BOUNDARY DIFF OBJECT scenario is incorrect.", nodeNullUpperEqual.equals(nodeNullUpper));
        assertTrue("NULL UPPER BOUNDARY DIFF OBJECT symmetric scenario is incorrect.",
                nodeNullUpper.equals(nodeNullUpperEqual));

        assertTrue("EQUAL BOUNDARY SAME TYPE scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeBaseEqual));
        assertTrue("EQUAL BOUNDARY SAME TYPE symmetric scenario is incorrect.",
                nodeBaseEqual.equals(nodeBaseLowerMiddle));

        assertTrue("SAME RANGE scenario is incorrect.", nodeBaseLowerMiddle.equals(nodeBaseLowerMiddle));

    }

    private Range<Integer>       _range;
    private Range<Integer>       _rangeLower;
    private Range<Integer>       _rangeMiddle;
    private Range<Integer>       _rangeUpper;
    private Range<Integer>       _rangeNullEndPoint;

    private static final Integer BOUNDARY_LOWER  = 0;
    private static final Integer BOUNDARY_MIDDLE = 5;
    private static final Integer BOUNDARY_UPPER  = 10;

}
