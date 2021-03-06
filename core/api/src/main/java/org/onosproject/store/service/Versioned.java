/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.store.service;

import java.util.function.Function;

import org.joda.time.DateTime;

import com.google.common.base.MoreObjects;

/**
 * Versioned value.
 *
 * @param <V> value type.
 */
public class Versioned<V> {

    private final V value;
    private final long version;
    private final long creationTime;

    /**
     * Constructs a new versioned value.
     * @param value value
     * @param version version
     * @param creationTime milliseconds of the creation event
     *  from the Java epoch of 1970-01-01T00:00:00Z
     */
    public Versioned(V value, long version, long creationTime) {
        this.value = value;
        this.version = version;
        this.creationTime = creationTime;
    }

    /**
     * Constructs a new versioned value.
     * @param value value
     * @param version version
     */
    public Versioned(V value, long version) {
        this(value, version, System.currentTimeMillis());
    }

    /**
     * Returns the value.
     *
     * @return value.
     */
    public V value() {
        return value;
    }

    /**
     * Returns the version.
     *
     * @return version
     */
    public long version() {
        return version;
    }

    /**
     * Returns the system time when this version was created.
     * <p>
     * Care should be taken when relying on creationTime to
     * implement any behavior in a distributed setting. Due
     * to the possibility of clock skew it is likely that
     * even creationTimes of causally related versions can be
     * out or order.
     * @return creation time
     */
    public long creationTime() {
        return creationTime;
    }

    /**
     * Maps this instance into another after transforming its
     * value while retaining the same version and creationTime.
     * @param transformer function to mapping the value
     * @param <U> value type of the returned instance
     * @return mapped instance
     */
    public <U> Versioned<U> map(Function<V, U> transformer) {
        return new Versioned<>(transformer.apply(value), version, creationTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("value", value)
            .add("version", version)
            .add("creationTime", new DateTime(creationTime))
            .toString();
    }
}
