package com.hit.algorithm;

import java.util.HashMap;
import java.util.Map;

public class LRUAlgoCacheImpl<K,V> extends AbstractAlgoCache<K,V>{
    Map<K,Long> frequencyUse;
    public LRUAlgoCacheImpl(int capacity){
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
        else{
            frequencyUse.put(key, System.nanoTime());
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
        if (!pages.containsKey(key)){
            V valueToBeRemoved=null;
            if(pages.size() == capacity){
                K keyToRemove=findKeyToRemove();
                valueToBeRemoved=pages.get(keyToRemove);
                removeElement(keyToRemove);
                frequencyUse.remove(keyToRemove);
            }
            pages.put(key, value);
            frequencyUse.put(key,System.nanoTime());
            return valueToBeRemoved;
        }
        pages.put(key,value);
        return null;
    }

    /**
     * find the page which intended for removal according to the policy of choosing the Least Recently Used page;
     * due to it's monitoring the exact time tat each page has been called.
     * @return the chosen key which intended for removal.
     */
    @Override
    K findKeyToRemove() {
        Long min = 0L;
       // Long
        K wantedKey=null;
        for (Map.Entry<K, Long> entry : frequencyUse.entrySet()) {
            if (min.equals(0L)){
                min = entry.getValue();
                wantedKey = entry.getKey();
            }else if (entry.getValue() < min) {
                min = entry.getValue();
                wantedKey = entry.getKey();
            }
        }
        return wantedKey;
    }
}
