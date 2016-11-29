package dataModels.applicationModels;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ChainedMap<K, V> {

    private static final int MAX = 1024;

    private int size;
    private Map<K, V> map;
    private Queue<K> kQueue;

    public ChainedMap() {
        this(64);
    }

    public ChainedMap(int size) throws IllegalArgumentException {

        if (size <= 0) {
            throw new IllegalArgumentException("Just Positive Integers Allowed");
        }

        if (size > ChainedMap.MAX)
            throw new IllegalArgumentException("Size Cannot be Greater Than: " + ChainedMap.MAX);

        this.size = size;
        this.map = new HashMap<>(this.size);
        this.kQueue = new LinkedList<>();
    }

    public synchronized void add(K key, V value) {

        if (key == null || value == null)
            throw new NullPointerException("Null Value Not Allowed!");

        if (this.kQueue.contains(key)) {
            this.map.put(key, value);
        } else {
            this.queuing(key, value);
        }
    }

    public synchronized V get(K key) {

        if (key == null)
            return null;

        return this.map.get(key);
    }

    public synchronized void remove(K key) {

        if (key == null)
            throw new NullPointerException("Key Cannot be Null!");

        this.kQueue.remove(key);
        this.map.remove(key);
    }

    public int size() {
        return this.kQueue.size();
    }

    public void clear() {
        this.map.clear();
        this.kQueue.clear();
    }

    public Queue<K> getId() {
        return kQueue;
    }

    public Map<K, V> getMap() {
        return map;
    }


    private void queuing(K key, V value) {

        if (this.kQueue.size() < this.size) {
            if (this.kQueue.add(key)) {
                this.map.put(key, value);
            }
        } else {
            K old = this.kQueue.poll();
            if (old != null)
                this.map.remove(old);

            this.kQueue.add(key);
            this.map.put(key, value);
        }
    }
}