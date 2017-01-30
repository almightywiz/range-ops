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

package com.tobecontinued.util.ranges.exception;

/**
 * Thrown to indicate that a range is improperly defined, such as having a lower
 * bound that is greater than its associated upper bound.
 * 
 * Applications can subclass this class to indicate similar exceptions.
 * 
 * Copyright (c) 2013 Charles Lukas All Rights Reserved
 * 
 * @author Charles Lukas
 */
public class RangeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs an <code>RangeException</code> with no detail message.
     */
    public RangeException() {

        super();

    }

    /**
     * Constructs an <code>RangeException</code> with the specified detail
     * message.
     * 
     * @param message the detail message.
     */
    public RangeException(String message) {

        super(message);

    }

}
