package com.hit.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomReplacementAlgoCacheImpl<K,V> extends AbstractAlgoCache<K, V> {

    public RandomReplacementAlgoCacheImpl(int capacity){
        super(capacity);
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
        return pages.get(key);
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
        if (pages.containsKey(key)) {
            pages.put(key,value);
            return null;
        }
        if (pages.size() == capacity) {

            K keyToBeRemoved=findKeyToRemove();
            removeElement(keyToBeRemoved);
        }
        pages.put(key, value);
        return null;
    }

    /**
     * find the page which intended for removal according to the policy of choosing a random key of the pages collection.
     * @return the chosen key which intended for removal.
     */
    @Override
    K findKeyToRemove() {
        Random rand = new Random();
        V ValueToBeRemoved=  pages.get(rand.nextInt(pages.size()));
        for (Map.Entry<K,V> entry : pages.entrySet()) {
            if (entry.getValue() == ValueToBeRemoved) {
                return  entry.getKey();
            }
        }
        return null;
    }
}


