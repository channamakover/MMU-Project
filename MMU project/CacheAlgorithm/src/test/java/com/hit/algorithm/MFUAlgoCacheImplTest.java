package com.hit.algorithm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MFUAlgoCacheImplTest {
    private MFUAlgoCacheImpl<Integer,Integer> instance;

    @BeforeAll
    static void beforeAll(){System.out.println("Hello from MFUAlgo");}

    @AfterAll
    static void afterAll(){System.out.println("Bye from MFUAlgo");}

    @Test
    void MFUTest(){
    instance =new  MFUAlgoCacheImpl<Integer, Integer>(3);
    Assert.assertNull(checkExistanceAndInsertion(instance,293));
    Assert.assertNull(checkExistanceAndInsertion(instance,7));
    Assert.assertNull(checkExistanceAndInsertion(instance,124));
    Assert.assertEquals(Integer.valueOf(293),checkExistanceAndInsertion(instance,293));
    Assert.assertEquals(Integer.valueOf(293),checkExistanceAndInsertion(instance,4));
    Assert.assertEquals(Integer.valueOf(7),checkExistanceAndInsertion(instance,7));
    Assert.assertEquals(Integer.valueOf(7),checkExistanceAndInsertion(instance,25));
    Assert.assertEquals(Integer.valueOf(124),checkExistanceAndInsertion(instance,124));
    Assert.assertEquals(Integer.valueOf(4),checkExistanceAndInsertion(instance,4));
    Assert.assertEquals(Integer.valueOf(4),checkExistanceAndInsertion(instance,70));
    }

    Integer checkExistanceAndInsertion(MFUAlgoCacheImpl<Integer,Integer> MFUInstabce, Integer key){
        if (MFUInstabce.getElement(key)!=null){
            return MFUInstabce.getElement(key);
        }
        return MFUInstabce.putElement(key, key);
    }
}