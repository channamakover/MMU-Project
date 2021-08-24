package com.hit.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractAlgoCache<K,V> implements IAlgoCache<K,V> {
    Map<K,V> pages;
    protected int capacity;

    public AbstractAlgoCache(int capacity){
        this.capacity=capacity;
        pages=new HashMap<>();
    }

    abstract K findKeyToRemove();

    /**
     * remove the page that it's key has been accepted
     * @param key of the page which intended for removal
     */
    @Override
    public void removeElement(K key) {
        pages.remove(key);
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void setCapacity(int capacity){
        this.capacity=capacity;
    }
    public List<V> getCache(){
        List<V> cache=new ArrayList<>(pages.size());
        for (V value:pages.values()) {
            cache.add(value);
        }
        return cache;
    }
}

