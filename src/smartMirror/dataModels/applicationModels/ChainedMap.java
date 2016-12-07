/*
 * Copyright 2016 CodeHigh
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
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package smartMirror.dataModels.applicationModels;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         Class containg a special typ of data structure that is used to facilitade manipulation of data such as post-its or
 *         bus infos
 */
public class ChainedMap<K, V>
{

    private static final int MAX = 1024;

    private int size;
    private Map<K, V> map;
    private Queue<K> kQueue;

    /**
     * Constructor that that initializes the data structure with the size of 64
     */
    public ChainedMap()
    {
        this(64);
    }

    /**
     * Constructor that initializes the data structure with the requested size never going over its max size
     *
     * @param size int with the desired size
     * @throws IllegalArgumentException if size lower than 0 or if its higher than max size
     */
    public ChainedMap(int size) throws IllegalArgumentException
    {

        if (size <= 0)
        {
            throw new IllegalArgumentException("Just Positive Integers Allowed");
        }

        if (size > ChainedMap.MAX)
            throw new IllegalArgumentException("Size Cannot be Greater Than: " + ChainedMap.MAX);

        this.size = size;
        this.map = new HashMap<>(this.size);
        this.kQueue = new LinkedList<>();
    }

    /**
     * Method responsible for adding elements to that data structure
     *
     * @param key   key of the element to be added
     * @param value value to be added
     */
    public synchronized void add(K key, V value)
    {

        if (key == null || value == null)
            throw new NullPointerException("Null Value Not Allowed!");

        if (this.kQueue.contains(key))
        {
            this.map.put(key, value);
        }
        else
        {
            this.queuing(key, value);
        }
    }

    /**
     * Method that provides the element for the requested key
     *
     * @param key key of the element
     * @return the element for that key
     */
    public synchronized V get(K key)
    {

        if (key == null)
            return null;

        return this.map.get(key);
    }

    /**
     * Method that removes the element from the data structure
     *
     * @param key key of the element
     */
    public synchronized void remove(K key)
    {

        if (key == null)
            throw new NullPointerException("Key Cannot be Null!");

        this.kQueue.remove(key);
        this.map.remove(key);
    }

    /**
     * Method that provides the size of the data structure
     *
     * @return size according to the number of elements
     */
    public int size()
    {
        return this.kQueue.size();
    }

    /**
     * Getter that provides the Queue containing all the keys
     *
     * @return list with all the keys
     */
    public Queue<K> getId()
    {
        return kQueue;
    }

    /**
     * Getter that provides the map containing the elements
     *
     * @return map with all the elements
     */
    public Map<K, V> getMap()
    {
        return map;
    }

    /**
     * Method used internally to add the element in both data structures used in this class
     *
     * @param key   key to be added
     * @param value value to be added
     */
    private void queuing(K key, V value)
    {

        if (this.kQueue.size() < this.size)
        {
            if (this.kQueue.add(key))
            {
                this.map.put(key, value);
            }
        }
        else
        {
            K old = this.kQueue.poll();
            if (old != null)
                this.map.remove(old);

            this.kQueue.add(key);
            this.map.put(key, value);
        }
    }


}