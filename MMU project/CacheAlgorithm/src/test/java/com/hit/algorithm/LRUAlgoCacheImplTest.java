package com.hit.algorithm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.Assert.assertEquals;


class LRUAlgoCacheImplTest {
    private LRUAlgoCacheImpl<Integer,Integer> instance=null;

    @BeforeAll
    static void beforeAll(){System.out.println("Hello from MFUAlgo");}

    @AfterAll
    static void afterAll(){System.out.println("Bye from MFUAlgo");}

    @Test
    void LRUTest(){
        instance = new LRUAlgoCacheImpl<>(3);
        Assert.assertNull(checkExistenceAndInsertion(instance,293));
        Assert.assertNull(checkExistenceAndInsertion(instance,7));
        Assert.assertNull(checkExistenceAndInsertion(instance,124));
        Assert.assertEquals(Integer.valueOf(293) , checkExistenceAndInsertion(instance,293));
        Assert.assertEquals(Integer.valueOf(7), checkExistenceAndInsertion(instance,7));
        Assert.assertEquals(Integer.valueOf(124), checkExistenceAndInsertion(instance,4));
        Assert.assertEquals(Integer.valueOf(4), checkExistenceAndInsertion(instance,4));
        Assert.assertEquals(Integer.valueOf(293), checkExistenceAndInsertion(instance,70));
        Assert.assertEquals(Integer.valueOf(7), checkExistenceAndInsertion(instance,7));
        Assert.assertEquals(Integer.valueOf(4), checkExistenceAndInsertion(instance,11));
    }


    private Integer checkExistenceAndInsertion(LRUAlgoCacheImpl<Integer,Integer> LRUInstance, Integer key){
        if (LRUInstance.getElement(key)!=null){
            return LRUInstance.getElement(key);
        }
        return LRUInstance.putElement(key, key);
    }
}