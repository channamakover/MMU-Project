package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class CacheUnitTest {

    private LRUAlgoCacheImpl<Long, DataModel<Long>> instance;

    @BeforeAll
    void beforeAll(){System.out.println("Hi from CacheUnit test");}

    @AfterAll
    void afterAll(){System.out.println("Bye from CacheUnit test");}

    @Test
    public void cacheUnitTest() {
        IAlgoCache<Long, DataModel<String>> algoTest = new LRUAlgoCacheImpl<>(5);
        CacheUnit<String> cacheUnitTest = new CacheUnit<String>(algoTest);
        Long[] ids = {(long) 1, (long) 2, (long) 3, (long) 4, (long) 5, (long) 6};
        DataModel<String> data1 = new DataModel<String>((long) 1, "1");
        DataModel<String> data2 = new DataModel<String>((long) 2, "2");
        DataModel<String> data3 = new DataModel<String>((long) 3, "3");
        DataModel<String> data4 = new DataModel<String>((long) 4, "4");
        DataModel<String> data5 = new DataModel<String>((long) 5, "5");
        DataModel<String> data6 = new DataModel<String>((long) 6, "6");

        DataModel<String>[] dataArray = new DataModel[6];
        dataArray[0] = data1;
        dataArray[1] = data2;
        dataArray[2] = data3;
        dataArray[3] = data4;
        dataArray[4] = data5;
        dataArray[5] = data6;

        Assert.assertEquals(data1, cacheUnitTest.putDataModels(dataArray)[5]);

        DataModel<String>[] dataModelArr = null;
        try {
            dataModelArr = cacheUnitTest.getDataModels(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }

        data3.setContent("33");
        DataModel[] cacheIds = { null, data2, data3,  data4,  data5,data6};
        Assert.assertEquals("33", data3.getContent());
        Assert.assertArrayEquals(cacheIds, dataModelArr);


        DataModel<String>[] checkGet = new DataModel[6];
        checkGet[0] = null;
        checkGet[1] = data2;
        checkGet[2] = data3;
        checkGet[3] = data4;
        checkGet[4] = data5;
        checkGet[5] = data6;

        Assert.assertArrayEquals(checkGet, cacheUnitTest.getDataModels(ids));
        Long[] idsToRemove={(long) 2, (long) 5};
        cacheUnitTest.removeDataModels(idsToRemove);

        DataModel[] cacheIds2 = { null, null, data3,  data4,  null,data6};
        Assert.assertArrayEquals(cacheIds2, cacheUnitTest.getDataModels(ids));

    }
}
