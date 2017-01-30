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

import org.apache.log4j.Logger;

import com.tobecontinued.util.ranges.core.RangeNode.EndPointType;

/**
 * Interface defining a object that covers a range of some kind, denoted by
 * start and end points.
 * 
 * Current interface is mimicking the Guave Range class interface for easier
 * transition once we upgrade our Guava library from Release 9.
 * 
 * Copyright (c) 2013 Charles Lukas All Rights Reserved
 * 
 * @author Charles Lukas
 */
public abstract class Range<T extends Comparable<T>> {

    private RangeNode<T> _lowerNode = null;
    private RangeNode<T> _upperNode = null;

    /**
     * Get the configured starting boundary for the range.
     * 
     * @return A generic, comparable object representing the starting boundary
     *         for the range.
     */
    public abstract T lowerEndpoint();

    /**
     * Get the configured ending boundary for the range.
     * 
     * @return A generic, comparable object representing the ending boundary for
     *         the range.
     */
    public abstract T upperEndpoint();

    /**
     * Get the lower endpoint's RangeNode.
     * 
     * @return RangeNode associated with the lower endpoint.
     */
    public RangeNode<T> lowerNode() {

        if (_lowerNode == null) _lowerNode = new RangeNode<T>(EndPointType.LOWER, this);
        return _lowerNode;
    }

    /**
     * Get the upper endpoint's RangeNode.
     * 
     * @return RangeNode associated with the upper endpoint.
     */
    public RangeNode<T> upperNode() {

        if (_upperNode == null) _upperNode = new RangeNode<T>(EndPointType.UPPER, this);
        return _upperNode;
    }

    /**
     * Tests for intersection with a different {@link Range} object.
     * Intersection is end point inclusive.
     * 
     * @param range Range object to test intersection against.
     * @return True if intersection occurs.
     */
    public boolean intersects(Range<T> range) {

        if (lowerEndpoint().compareTo(range.upperEndpoint()) > 0
                || upperEndpoint().compareTo(range.lowerEndpoint()) < 0) { return false; }

        return true;
    }

    /**
     * Tests that the given range is contained entirely within the current
     * range. Contains is end point inclusive.
     * 
     * @param range Range object to test contains against.
     * @return True if the current range contains the given range entirely.
     */
    public boolean contains(Range<T> range) {

        if (lowerEndpoint().compareTo(range.lowerEndpoint()) > 0
                || upperEndpoint().compareTo(range.upperEndpoint()) < 0) { return false; }

        return true;
    }

    // @see java.lang.Object#hashCode()
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((upperEndpoint() == null) ? 0 : upperEndpoint().hashCode());
        result = prime * result + ((lowerEndpoint() == null) ? 0 : lowerEndpoint().hashCode());
        return result;
    }

    // @see java.lang.Object#equals(java.lang.Object)
    @Override
    public boolean equals(Object obj) {

        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof Range)) { return false; }
        Range<?> other = (Range<?>) obj;
        if (upperEndpoint() == null) {
            if (other.upperEndpoint() != null) { return false; }
        }
        else if (!upperEndpoint().equals(other.upperEndpoint())) { return false; }
        if (lowerEndpoint() == null) {
            if (other.lowerEndpoint() != null) { return false; }
        }
        else if (!lowerEndpoint().equals(other.lowerEndpoint())) { return false; }
        return true;
    }

    // @see java.lang.Object#toString()
    @Override
    public String toString() {

        return "Range [lowerBoundary=" + lowerEndpoint() + ", upperBoundary=" + upperEndpoint() + "]";
    }

    /**
     * Generates a new Range instance with the given lower and upper end point
     * values.
     * 
     * @param lowerBoundary Lower boundary range value.
     * @param upperBoundary Upper boundary range value.
     * @return Range instance with given lower and upper bounds.
     * @throws RangeDirectionException
     */
    public static <T extends Comparable<T>> Range<T> newRange(final T lowerBoundary, final T upperBoundary) {

        _logger.debug("Creating range...");
        _logger.debug("Lower boundary: " + lowerBoundary);
        _logger.debug("Upper boundary: " + upperBoundary);

        // if(lowerBoundary.compareTo(upperBoundary) > 0){ throw new
        // RangeException(); }

        return new Range<T>() {

            @Override
            public T lowerEndpoint() {

                return lowerBoundary;
            }

            @Override
            public T upperEndpoint() {

                return upperBoundary;
            }

        };
    }

    private static final Logger _logger = Logger.getLogger(Range.class);

}
