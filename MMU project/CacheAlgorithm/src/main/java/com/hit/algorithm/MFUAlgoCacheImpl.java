package com.hit.algorithm;

import java.util.*;

public class MFUAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {
    Map<K, Integer> frequencyUse ;
    public MFUAlgoCacheImpl(int capacity){
        super(capacity);
        frequencyUse=new HashMap<>();
    }


    /**
     * return the page which his key was excepted if exists, else return null
     * @param key of the page we want to get
     * @return the content of the wanted page
     */
    @Override
    public V getElement(K key) {
        if (!pages.containsKey(key))
            return null;
        else {
            frequencyUse.put(key, frequencyUse.get(key) + 1);
            return pages.get(key);
        }
    }

    /**
     * provide the action of page insertion, by giving the key of the page and it's content
     * @param key of the page we want to insert
     * @param value- the content of this specific page
     * @return the content of the removed page- if there was not enough space in the pages array (the RAM),
     * else, if there was enough space- return null.
     */
    @Override
    public V putElement(K key, V value) {
        if (!pages.containsKey(key)) {
            V valueToBeRemoved = null;
            if (pages.size() == capacity) {
                K keyToRemove = findKeyToRemove();
                valueToBeRemoved = pages.get(keyToRemove);
                removeElement(keyToRemove);
                frequencyUse.remove(keyToRemove);
            }
            pages.put(key, value);
            frequencyUse.put(key, 1);
            return valueToBeRemoved;
        }
        pages.put(key,value);
        return null;
    }

    /**
     * find the page which intended for removal according to the policy of Most Frequently Used page,
     * that maintains an array that for each page keeps he number of times that it has been used.
     * @return the chosen key which intended for removal.
     */
    public K findKeyToRemove() {
        long max = 0;
        K wantedKey = null;
        for (Map.Entry<K, Integer> entry : frequencyUse.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                wantedKey = entry.getKey();
            }
        }
        return wantedKey;
    }
}
