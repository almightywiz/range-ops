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

/**
 * End point object containing the end point type, as well as the value of the
 * end point.
 * 
 * This is used in order to properly sort end points for use in the Ranges
 * class.
 * 
 * Copyright (c) 2013 Charles Lukas All Rights Reserved
 * 
 * @author Charles Lukas
 */
public class RangeNode<T extends Comparable<T>> implements Comparable<RangeNode<T>> {

    /**
     * 
     * Endpoint Type enumeration signifying which boundary this node represents.
     * 
     * Copyright (c) 2013 Charles Lukas All Rights Reservered
     * 
     * @author Charles Lukas
     */
    public enum EndPointType {
        LOWER, UPPER
    }

    private Range<T>     _range        = null;
    private EndPointType _endPointType = null;

    /**
     * Constructor for a RangeNode object.
     * 
     * @param endPointType Enumeration signifying which endpoint this node
     *            represents.
     * @param range Parent Range object this node belongs to.
     */
    public RangeNode(EndPointType endPointType, Range<T> range) {

        _endPointType = endPointType;
        _range = range;
    }

    /**
     * Get the endpoint value for this node.
     * 
     * @return Generic comparable object representing the value of the endpoint.
     */
    public T getEndpoint() {

        if (EndPointType.LOWER.equals(_endPointType)) return _range.lowerEndpoint();
        if (EndPointType.UPPER.equals(_endPointType)) return _range.upperEndpoint();
        return null;
    }

    /**
     * Get the opposite endpoint for this node's parent Range.
     * 
     * @return RangeNode
     */
    public RangeNode<T> getSiblingEndpoint() {

        if (EndPointType.LOWER.equals(_endPointType)) return _range.upperNode();
        if (EndPointType.UPPER.equals(_endPointType)) return _range.lowerNode();
        return null;
    }

    public EndPointType getEndpointType() {

        return _endPointType;
    }

    public Range<T> getRange() {

        return _range;
    }

    // @see java.lang.Comparable#compareTo(java.lang.Object)
    @Override
    public int compareTo(RangeNode<T> arg0) {

        int result = 0;
        if (arg0 == null) { return result; }

        result = getEndpoint().compareTo(arg0.getEndpoint());
        if (result != 0) { return result; }

        return _endPointType.compareTo(arg0._endPointType);
    }

    // @see java.lang.Object#hashCode()
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEndpoint() == null) ? 0 : getEndpoint().hashCode());
        result = prime * result + ((_endPointType == null) ? 0 : _endPointType.hashCode());
        return result;
    }

    // @see java.lang.Object#equals(java.lang.Object)
    @Override
    public boolean equals(Object obj) {

        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof RangeNode)) { return false; }
        RangeNode<?> other = (RangeNode<?>) obj;
        if (getEndpoint() == null) {
            if (other.getEndpoint() != null) { return false; }
        }
        else if (!getEndpoint().equals(other.getEndpoint())) { return false; }
        if (_endPointType != other._endPointType) { return false; }
        return true;
    }

    // @see java.lang.Object#toString()

    @Override
    public String toString() {

        return "RangeNode [" + _endPointType + " : " + _range + "]";
    }

}
