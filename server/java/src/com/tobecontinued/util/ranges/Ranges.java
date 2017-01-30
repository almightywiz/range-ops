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

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.tobecontinued.util.ranges.core.Range;
import com.tobecontinued.util.ranges.core.RangeNode;
import com.tobecontinued.util.ranges.core.RangeNode.EndPointType;

/**
 * Utility class for dealing with a collection of ranges.
 * 
 * Copyright (c) 2013 Charles Lukas All Rights Reserved
 * 
 * @author Charles Lukas
 */
public final class Ranges {

    /**
     * Private constructor that should never be accessed due to this being a
     * utility class.
     */
    private Ranges() {

    }

    /**
     * Union all ranges across a collection of a list of ranges.
     * 
     * @param rangeLists Collection of a list of ranges to be unioned.
     * @return A sorted list of ranges.
     * @throws RangeDirectionException
     */
    public static <T extends Comparable<T>> List<Range<T>> union(Collection<List<Range<T>>> rangeLists) {

        _logger.debug("Performing union of [" + rangeLists.size() + "] range lists...");

        List<Range<T>> ranges = Lists.newArrayList();

        // O(n)
        for (Collection<Range<T>> rangeList : rangeLists) {
            ranges.addAll(rangeList);
        }

        return union(ranges);
    }

    /**
     * Union all ranges.
     * 
     * @param rangeList List of ranges to be unioned.
     * @return A sorted list of ranges.
     * @throws RangeDirectionException
     */
    public static <T extends Comparable<T>> List<Range<T>> union(List<Range<T>> rangeList) {

        _logger.debug("Performing union of [" + rangeList.size() + "] ranges...");

        return join(rangeList, 1);
    }

    /**
     * Intersect all ranges across a collection of a list of ranges.
     * 
     * @param rangeLists Collection of a list of ranges to be intersected.
     * @return A sorted list of ranges.
     * @throws RangeDirectionException
     */
    public static <T extends Comparable<T>> List<Range<T>> intersect(Collection<List<Range<T>>> rangeLists) {

        _logger.debug("Performing intersection of [" + rangeLists.size() + "] range lists with threshold of ["
                + rangeLists.size() + "]...");

        List<Range<T>> ranges = Lists.newArrayList();

        // O(n)
        for (Collection<Range<T>> rangeList : rangeLists) {
            ranges.addAll(rangeList);
        }

        return join(ranges, rangeLists.size());
    }

    /**
     * Intersect all ranges.
     * 
     * @param rangeList List of ranges to be intersected.
     * @return A sorted list of ranges.
     * @throws RangeDirectionException
     */
    public static <T extends Comparable<T>> List<Range<T>> intersect(List<Range<T>> rangeList) {

        _logger.debug("Performing intersection of [" + rangeList.size() + "] ranges with threshold of [2]...");

        return join(rangeList, 2);
    }

    /**
     * Intersect all ranges given a threshold for number of total internal
     * intersections before recording a final intersection.
     * 
     * @param rangeLists Collection of a list of ranges to be intersected.
     * @param threshold Number of internal intersections required for final
     *            intersection.
     * @return A sorted list of ranges.
     * @throws RangeDirectionException
     */
    public static <T extends Comparable<T>> List<Range<T>> join(Collection<List<Range<T>>> rangeLists, int threshold) {

        _logger.debug("Performing join of [" + rangeLists.size() + "] range lists with a threshold of [" + threshold
                + "]...");

        List<Range<T>> ranges = Lists.newArrayList();

        // O(n)
        for (Collection<Range<T>> rangeList : rangeLists) {
            ranges.addAll(rangeList);
        }

        return join(ranges, threshold);
    }

    /**
     * Intersect all ranges given a threshold for number of total internal
     * intersections before recording a final intersection.
     * 
     * @param rangeList List of ranges to be intersected.
     * @param threshold Number of internal intersections required for final
     *            intersection. A threshold of 1 will result in the same results
     *            of union.
     * @return A sorted list of ranges.
     * @throws RangeDirectionException
     */
    public static <T extends Comparable<T>> List<Range<T>> join(List<Range<T>> rangeList, int threshold) {

        _logger.debug("Performing join of [" + rangeList.size() + "] ranges with a threshold of [" + threshold + "]...");

        int initialRangeListSize = (rangeList.isEmpty() ? 1 : rangeList.size());
        PriorityQueue<RangeNode<T>> rangeNodes = new PriorityQueue<RangeNode<T>>(initialRangeListSize * 2);

        // O(nlogn)
        for (Range<T> range : rangeList) {
            rangeNodes.add(range.lowerNode());
            rangeNodes.add(range.upperNode());
        }

        return join(threshold, rangeNodes);

    }

    /**
     * Join all ranges given a threshold for number of total internal
     * intersections before recording a final intersection.
     * 
     * This is done through the use of a priority queue that stores a list of
     * range nodes representing all range endpoints.
     * 
     * <strong>This is a package-private method. It should not be exposed
     * outside of this package.</strong>
     * 
     * @param threshold Number of internal intersections required for final join
     *            results. A threshold of 1 will result in the same results of
     *            union. A threshold greater than 1 will result in an
     *            intersection.
     * @param rangeList Queue of range nodes used for joining, sorted by the
     *            RangeNode.compareTo method.
     * @return A sorted list of ranges.
     * @throws RangeDirectionException
     */
    protected static <T extends Comparable<T>> List<Range<T>> join(int threshold, PriorityQueue<RangeNode<T>> rangeList) {

        _logger.debug("Performing join of [" + rangeList.size() + "] range end points with a threshold of ["
                + threshold + "]...");

        List<Range<T>> intersections = Lists.newArrayList();
        PriorityQueue<RangeNode<T>> workingRangeList = new PriorityQueue<RangeNode<T>>(rangeList);

        long startTime = System.nanoTime();

        // O(n)
        List<RangeNode<T>> workingJoinSet = Lists.newArrayList();
        T start = null;
        while (workingRangeList.peek() != null) {
            RangeNode<T> endPoint = workingRangeList.remove();

            _logger.debug("Processing end point: " + endPoint);

            if (EndPointType.LOWER.equals(endPoint.getEndpointType())) {

                _logger.debug("Adding lower boundary to working set...");

                workingJoinSet.add(endPoint);

                if (threshold == workingJoinSet.size()) {

                    _logger.debug("Lower boundary addition has met the threshold; becomes intersection lower boundary...");

                    start = endPoint.getEndpoint();
                }

            }
            else {

                _logger.debug("Reached upper boundary...");

                if (threshold == workingJoinSet.size() && endPoint.getEndpoint().compareTo(start) > 0) {
                    _logger.debug("Upper boundary pending removal is at threshold; becomes intersection upper boundary...");
                    Range<T> range = Range.newRange(start, endPoint.getEndpoint());

                    _logger.debug("Adding joining range: " + range);

                    intersections.add(range);
                }

                _logger.debug("Removing upper bound's associated lower boundary from working set: "
                        + endPoint.getSiblingEndpoint());
                workingJoinSet.remove(endPoint.getSiblingEndpoint());

            }

        }

        long duration = System.nanoTime() - startTime;

        _logger.debug("Join of [" + rangeList.size() + "] range end points complete!");
        _logger.debug("Join processing time: [" + duration / 100000 + "ms]");

        return intersections;

    }

    /**
     * Find the list of complementing data ranges for the given list of ranges,
     * with a valid lower and upper value boundary.
     * 
     * If this list contains overlapping data ranges, this method will return
     * the complement of the union of ranges.
     * 
     * @param rangeList List of ranges to find complementing ranges.
     * @param lowerBoundary Lower boundary of initial complementing range.
     * @param upperBoundary Upper boundary of final complementing range.
     * @return List of complementing ranges to the initial list of ranges.
     * @throws RangeDirectionException
     */
    public static <T extends Comparable<T>> List<Range<T>> complement(List<Range<T>> rangeList, T lowerBoundary,
            T upperBoundary) {

        _logger.debug("Performing complement requires a unioned list of ranges... ");

        List<Range<T>> union = union(rangeList);

        _logger.debug("Performing complement of [" + rangeList.size() + "] ranges...");

        int initialRangeListSize = (union.isEmpty() ? 1 : union.size());
        PriorityQueue<RangeNode<T>> rangeNodes = new PriorityQueue<RangeNode<T>>(initialRangeListSize * 2);

        // O(nlogn)
        for (Range<T> range : union) {
            rangeNodes.add(range.lowerNode());
            rangeNodes.add(range.upperNode());
        }

        return complement(rangeNodes, lowerBoundary, upperBoundary);
    }

    /**
     * Generates a list of complementing ranges for the set of ranges with given
     * lower and upper boundaries.
     * 
     * This is done through the use of a priority queue that stores a list of
     * range nodes representing all range endpoints.
     * 
     * <strong>This is a package-private method. It should not be exposed
     * outside of this package.</strong>
     * 
     * @param rangeList Queue of range nodes used for complementing, sorted by
     *            the RangeNode.compareTo method.
     * @param lowerBoundary Lower boundary of initial complementing range.
     * @param upperBoundary Upper boundary of final complementing range.
     * @return List of complementing ranges to the initial list of ranges.
     * @throws RangeDirectionException
     */
    protected static <T extends Comparable<T>> List<Range<T>> complement(PriorityQueue<RangeNode<T>> rangeList,
            T lowerBoundary, T upperBoundary) {

        int initialRangeListSize = rangeList.size();
        _logger.debug("Performing complement of [" + initialRangeListSize + "] range end points...");

        List<Range<T>> complements = Lists.newArrayList();

        // O(n)
        T start = lowerBoundary;
        T end = null;
        long startTime = System.nanoTime();

        while (rangeList.peek() != null) {
            RangeNode<T> endPoint = rangeList.remove();

            _logger.debug("Processing end point: " + endPoint);

            if (endPoint.getEndpoint().compareTo(lowerBoundary) < 0) {
                _logger.debug("End point outside lower boundary: " + lowerBoundary);
                _logger.debug("Moving on to next end point...");
                continue;
            }
            else if (endPoint.getEndpoint().compareTo(upperBoundary) > 0) {
                _logger.debug("End point outside upper boundary: " + upperBoundary);
                _logger.debug("No more end points require checking...");
                break;
            }
            else if (EndPointType.UPPER.equals(endPoint.getEndpointType())) {

                _logger.debug("Setting end point as complement range lower boundary...");
                start = endPoint.getEndpoint();
                end = null;

            }
            else if (!endPoint.getEndpoint().equals(start)) {

                _logger.debug("Setting end point as complement range upper boundary...");
                end = endPoint.getEndpoint();
                Range<T> range = Range.newRange(start, end);
                _logger.debug("Adding complement range: " + range);
                complements.add(range);

            }

        }

        if (!upperBoundary.equals(start) && end == null) {
            _logger.debug("Last complement range is incomplete.  Setting upper boundary to max value: " + upperBoundary);
            end = upperBoundary;
            Range<T> range = Range.newRange(start, end);
            _logger.debug("Adding complement range: " + range);
            complements.add(range);
        }

        long duration = System.nanoTime() - startTime;

        _logger.debug("Complement of [" + initialRangeListSize + "] range end points complete!");
        _logger.debug("Complement processing time: [" + duration / 100000 + "ms]");

        return complements;

    }

    private static final Logger _logger = Logger.getLogger(Ranges.class);

}
