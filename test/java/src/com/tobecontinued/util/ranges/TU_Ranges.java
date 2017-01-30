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

package com.tobecontinued.util.ranges;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.tobecontinued.util.ranges.core.Range;
import com.tobecontinued.util.ranges.core.RangeNode;

/**
 * Test class that exercises the Ranges utility class.
 * 
 * All tests will be administered using Integer typed Ranges.
 * 
 * Copyright (c) 2013 Charles Lukas All Rights Reserved
 * 
 * @author Charles Lukas
 */
public class TU_Ranges {

    @SuppressWarnings("unchecked")
    public TU_Ranges() {

        _rangeA = Mockito.mock(Range.class);
        Mockito.when(_rangeA.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_A_LOWER;
            }
        });
        Mockito.when(_rangeA.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_A_UPPER;
            }
        });
        Mockito.when(_rangeA.lowerNode()).thenCallRealMethod();
        Mockito.when(_rangeA.upperNode()).thenCallRealMethod();

        _rangeB = Mockito.mock(Range.class);
        Mockito.when(_rangeB.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_B_LOWER;
            }
        });
        Mockito.when(_rangeB.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_B_UPPER;
            }
        });
        Mockito.when(_rangeB.lowerNode()).thenCallRealMethod();
        Mockito.when(_rangeB.upperNode()).thenCallRealMethod();

        _rangeC = Mockito.mock(Range.class);
        Mockito.when(_rangeC.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_C_LOWER;
            }
        });
        Mockito.when(_rangeC.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_C_UPPER;
            }
        });
        Mockito.when(_rangeC.lowerNode()).thenCallRealMethod();
        Mockito.when(_rangeC.upperNode()).thenCallRealMethod();

        _rangeD = Mockito.mock(Range.class);
        Mockito.when(_rangeD.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_D_LOWER;
            }
        });
        Mockito.when(_rangeD.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_D_UPPER;
            }
        });
        Mockito.when(_rangeD.lowerNode()).thenCallRealMethod();
        Mockito.when(_rangeD.upperNode()).thenCallRealMethod();

        _rangeE = Mockito.mock(Range.class);
        Mockito.when(_rangeE.lowerEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_E_LOWER;
            }
        });
        Mockito.when(_rangeE.upperEndpoint()).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) {

                return RANGE_E_UPPER;
            }
        });
        Mockito.when(_rangeE.lowerNode()).thenCallRealMethod();
        Mockito.when(_rangeE.upperNode()).thenCallRealMethod();

    }

    @Test
    public void testPrivateConstructorInaccessibility() throws Exception {

        Constructor<?>[] ctors = Ranges.class.getDeclaredConstructors();

        assertEquals("Ranges class should only have one constructor.", 1, ctors.length);

        Constructor<?> ctor = ctors[0];

        assertTrue(Modifier.isPrivate(ctor.getModifiers()));
        assertFalse("Ranges class constructor should be inaccessible.", ctor.isAccessible());

        // only done to allow constructor access for test execution
        ctor.setAccessible(true);

        assertEquals("Constructor returns incorrect class.", Ranges.class, ctor.newInstance().getClass());
    }

    @Test
    public void testUnionCollection() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        Collection<List<Range<Integer>>> rangeLists = new HashSet<List<Range<Integer>>>();
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeA);
        ranges.add(_rangeC);
        rangeLists.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        rangeLists.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        rangeLists.add(ranges);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> union = Ranges.union(rangeLists);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(2, union.size());

        assertEquals(RANGE_A_LOWER, union.get(0).lowerEndpoint());
        assertEquals(RANGE_C_UPPER, union.get(0).upperEndpoint());

        assertEquals(RANGE_B_LOWER, union.get(1).lowerEndpoint());
        assertEquals(RANGE_B_UPPER, union.get(1).upperEndpoint());

    }

    @Test
    public void testUnion() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        ranges.add(_rangeC);
        ranges.add(_rangeA);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> union = Ranges.union(ranges);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(2, union.size());

        assertEquals(RANGE_A_LOWER, union.get(0).lowerEndpoint());
        assertEquals(RANGE_C_UPPER, union.get(0).upperEndpoint());

        assertEquals(RANGE_B_LOWER, union.get(1).lowerEndpoint());
        assertEquals(RANGE_B_UPPER, union.get(1).upperEndpoint());

    }

    @Test
    public void testIntersectCollection() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        Collection<List<Range<Integer>>> rangeListsWithEmpty = new HashSet<List<Range<Integer>>>();
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeA);
        ranges.add(_rangeC);
        rangeListsWithEmpty.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        rangeListsWithEmpty.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        rangeListsWithEmpty.add(ranges);

        Collection<List<Range<Integer>>> rangeListsWithoutEmpty = new HashSet<List<Range<Integer>>>();
        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeA);
        rangeListsWithoutEmpty.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeC);
        ranges.add(_rangeB);
        rangeListsWithoutEmpty.add(ranges);

        Collection<List<Range<Integer>>> rangeListsWithoutEmptyNoIntersection = new HashSet<List<Range<Integer>>>();
        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeA);
        rangeListsWithoutEmptyNoIntersection.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeC);
        rangeListsWithoutEmptyNoIntersection.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        rangeListsWithoutEmptyNoIntersection.add(ranges);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> intersectionWithEmpty = Ranges.intersect(rangeListsWithEmpty);
        List<Range<Integer>> intersectionWithoutEmpty = Ranges.intersect(rangeListsWithoutEmpty);
        List<Range<Integer>> intersectionWithoutEmptyNoIntersection = Ranges
                .intersect(rangeListsWithoutEmptyNoIntersection);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(0, intersectionWithEmpty.size());

        assertEquals(0, intersectionWithoutEmptyNoIntersection.size());

        assertEquals(1, intersectionWithoutEmpty.size());

        assertEquals(RANGE_C_LOWER, intersectionWithoutEmpty.get(0).lowerEndpoint());
        assertEquals(RANGE_A_UPPER, intersectionWithoutEmpty.get(0).upperEndpoint());

    }

    @Test
    public void testIntersect() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        ranges.add(_rangeC);
        ranges.add(_rangeA);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> intersection = Ranges.intersect(ranges);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(1, intersection.size());

        assertEquals(RANGE_C_LOWER, intersection.get(0).lowerEndpoint());
        assertEquals(RANGE_A_UPPER, intersection.get(0).upperEndpoint());

    }

    @Test
    public void testJoinCollection() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        Collection<List<Range<Integer>>> rangesUnion = new HashSet<List<Range<Integer>>>();
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeA);
        ranges.add(_rangeC);
        rangesUnion.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        rangesUnion.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        rangesUnion.add(ranges);

        Collection<List<Range<Integer>>> rangeIntersectWithEmpty = new HashSet<List<Range<Integer>>>();
        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeA);
        ranges.add(_rangeC);
        rangeIntersectWithEmpty.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        rangeIntersectWithEmpty.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        rangeIntersectWithEmpty.add(ranges);

        Collection<List<Range<Integer>>> rangeIntersectWithoutEmpty = new HashSet<List<Range<Integer>>>();
        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeA);
        rangeIntersectWithoutEmpty.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeC);
        ranges.add(_rangeB);
        rangeIntersectWithoutEmpty.add(ranges);

        Collection<List<Range<Integer>>> rangeIntersectWithoutEmptyNoIntersection = new HashSet<List<Range<Integer>>>();
        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeA);
        rangeIntersectWithoutEmptyNoIntersection.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeC);
        rangeIntersectWithoutEmptyNoIntersection.add(ranges);

        ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        rangeIntersectWithoutEmptyNoIntersection.add(ranges);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> union = Ranges.join(rangesUnion, 1);
        List<Range<Integer>> intersectWithEmpty = Ranges.join(rangeIntersectWithEmpty, 3);
        List<Range<Integer>> intersectWithoutEmpty = Ranges.join(rangeIntersectWithoutEmpty, 2);
        List<Range<Integer>> intersectNoResults = Ranges.join(rangeIntersectWithoutEmptyNoIntersection, 3);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(2, union.size());

        assertEquals(RANGE_A_LOWER, union.get(0).lowerEndpoint());
        assertEquals(RANGE_C_UPPER, union.get(0).upperEndpoint());

        assertEquals(RANGE_B_LOWER, union.get(1).lowerEndpoint());
        assertEquals(RANGE_B_UPPER, union.get(1).upperEndpoint());

        assertEquals(0, intersectWithEmpty.size());
        assertEquals(0, intersectNoResults.size());

        assertEquals(1, intersectWithoutEmpty.size());

        assertEquals(RANGE_C_LOWER, intersectWithoutEmpty.get(0).lowerEndpoint());
        assertEquals(RANGE_A_UPPER, intersectWithoutEmpty.get(0).upperEndpoint());

    }

    @Test
    public void testJoin() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        ranges.add(_rangeC);
        ranges.add(_rangeA);
        ranges.add(_rangeD);
        ranges.add(_rangeE);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> union = Ranges.join(ranges, 1);
        List<Range<Integer>> intersect = Ranges.join(ranges, 2);
        List<Range<Integer>> intersectNoResults = Ranges.join(ranges, 4);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(2, union.size());

        assertEquals(RANGE_A_LOWER, union.get(0).lowerEndpoint());
        assertEquals(RANGE_C_UPPER, union.get(0).upperEndpoint());

        assertEquals(RANGE_B_LOWER, union.get(1).lowerEndpoint());
        assertEquals(RANGE_B_UPPER, union.get(1).upperEndpoint());

        assertEquals(0, intersectNoResults.size());

        assertEquals(1, intersect.size());

        assertEquals(RANGE_C_LOWER, intersect.get(0).lowerEndpoint());
        assertEquals(RANGE_A_UPPER, intersect.get(0).upperEndpoint());

    }

    @Test
    public void testJoinSorted() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        ranges.add(_rangeC);
        ranges.add(_rangeA);
        ranges.add(_rangeD);
        ranges.add(_rangeE);

        PriorityQueue<RangeNode<Integer>> rangesSorted = new PriorityQueue<RangeNode<Integer>>();
        for (Range<Integer> range : ranges) {
            rangesSorted.add(range.lowerNode());
            rangesSorted.add(range.upperNode());
        }

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> union = Ranges.join(1, rangesSorted);
        List<Range<Integer>> intersect = Ranges.join(2, rangesSorted);
        List<Range<Integer>> intersectNoResults = Ranges.join(4, rangesSorted);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(2, union.size());

        assertEquals(RANGE_A_LOWER, union.get(0).lowerEndpoint());
        assertEquals(RANGE_C_UPPER, union.get(0).upperEndpoint());

        assertEquals(RANGE_B_LOWER, union.get(1).lowerEndpoint());
        assertEquals(RANGE_B_UPPER, union.get(1).upperEndpoint());

        assertEquals(0, intersectNoResults.size());

        assertEquals(1, intersect.size());

        assertEquals(RANGE_C_LOWER, intersect.get(0).lowerEndpoint());
        assertEquals(RANGE_A_UPPER, intersect.get(0).upperEndpoint());

    }

    @Test
    public void testJoinEmpty() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> union = Ranges.join(ranges, 1);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(0, union.size());

    }

    @Test
    public void testComplement() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        ranges.add(_rangeC);
        ranges.add(_rangeA);

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> complement = Ranges.complement(ranges, RANGE_MAX_LOWER_A, RANGE_MAX_UPPER_A);
        List<Range<Integer>> complementOverlap = Ranges.complement(ranges, RANGE_MAX_LOWER_B, RANGE_MAX_UPPER_B);
        List<Range<Integer>> complementEndPoints = Ranges.complement(ranges, RANGE_MAX_LOWER_C, RANGE_MAX_UPPER_C);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(3, complement.size());

        assertEquals(RANGE_MAX_LOWER_A, complement.get(0).lowerEndpoint());
        assertEquals(RANGE_A_LOWER, complement.get(0).upperEndpoint());

        assertEquals(RANGE_C_UPPER, complement.get(1).lowerEndpoint());
        assertEquals(RANGE_B_LOWER, complement.get(1).upperEndpoint());

        assertEquals(RANGE_B_UPPER, complement.get(2).lowerEndpoint());
        assertEquals(RANGE_MAX_UPPER_A, complement.get(2).upperEndpoint());

        assertEquals(1, complementOverlap.size());

        assertEquals(RANGE_C_UPPER, complementOverlap.get(0).lowerEndpoint());
        assertEquals(RANGE_B_LOWER, complementOverlap.get(0).upperEndpoint());

        assertEquals(1, complementEndPoints.size());

        assertEquals(RANGE_C_UPPER, complementEndPoints.get(0).lowerEndpoint());
        assertEquals(RANGE_B_LOWER, complementEndPoints.get(0).upperEndpoint());

    }

    @Test
    public void testComplementSorted() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();
        ranges.add(_rangeB);
        ranges.add(_rangeC);
        ranges.add(_rangeA);

        PriorityQueue<RangeNode<Integer>> rangesSorted = new PriorityQueue<RangeNode<Integer>>();
        rangesSorted.add(_rangeA.lowerNode());
        rangesSorted.add(_rangeC.upperNode());
        rangesSorted.add(_rangeB.lowerNode());
        rangesSorted.add(_rangeB.upperNode());

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> complement = Ranges.complement(ranges, RANGE_MAX_LOWER_A, RANGE_MAX_UPPER_A);
        List<Range<Integer>> complementOverlap = Ranges.complement(ranges, RANGE_MAX_LOWER_B, RANGE_MAX_UPPER_B);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(3, complement.size());

        assertEquals(RANGE_MAX_LOWER_A, complement.get(0).lowerEndpoint());
        assertEquals(RANGE_A_LOWER, complement.get(0).upperEndpoint());

        assertEquals(RANGE_C_UPPER, complement.get(1).lowerEndpoint());
        assertEquals(RANGE_B_LOWER, complement.get(1).upperEndpoint());

        assertEquals(RANGE_B_UPPER, complement.get(2).lowerEndpoint());
        assertEquals(RANGE_MAX_UPPER_A, complement.get(2).upperEndpoint());

        assertEquals(1, complementOverlap.size());

        assertEquals(RANGE_C_UPPER, complementOverlap.get(0).lowerEndpoint());
        assertEquals(RANGE_B_LOWER, complementOverlap.get(0).upperEndpoint());

    }

    @Test
    public void testComplementEmpty() {

        // 1. SETUP ///////////////////////////////////////////////////////////
        List<Range<Integer>> ranges = new ArrayList<Range<Integer>>();

        // 2. EXECUTE /////////////////////////////////////////////////////////
        List<Range<Integer>> complement = Ranges.complement(ranges, RANGE_MAX_LOWER_A, RANGE_MAX_UPPER_A);

        // 3. VERIFY //////////////////////////////////////////////////////////
        assertEquals(1, complement.size());

        assertEquals(RANGE_MAX_LOWER_A, complement.get(0).lowerEndpoint());
        assertEquals(RANGE_MAX_UPPER_A, complement.get(0).upperEndpoint());

    }

    private Range<Integer>       _rangeA;
    private Range<Integer>       _rangeB;
    private Range<Integer>       _rangeC;
    private Range<Integer>       _rangeD;
    private Range<Integer>       _rangeE;

    private static final Integer RANGE_A_LOWER     = 0;
    private static final Integer RANGE_A_UPPER     = 3;
    private static final Integer RANGE_B_LOWER     = 8;
    private static final Integer RANGE_B_UPPER     = 12;
    private static final Integer RANGE_C_LOWER     = 2;
    private static final Integer RANGE_C_UPPER     = 6;
    private static final Integer RANGE_D_LOWER     = 2;
    private static final Integer RANGE_D_UPPER     = 3;
    private static final Integer RANGE_E_LOWER     = 2;
    private static final Integer RANGE_E_UPPER     = 2;

    private static final Integer RANGE_MAX_LOWER_A = -1;
    private static final Integer RANGE_MAX_UPPER_A = 13;
    private static final Integer RANGE_MAX_LOWER_B = 1;
    private static final Integer RANGE_MAX_UPPER_B = 10;
    private static final Integer RANGE_MAX_LOWER_C = 0;
    private static final Integer RANGE_MAX_UPPER_C = 12;
}
