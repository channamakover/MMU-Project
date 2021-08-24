package com.hit.dao;

import com.hit.dm.DataModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

public class DaoFileImplTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Hello from Dao");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Bye from Dao");
    }

    @Test
    public void testDaoFile() throws IOException {
        DaoFileImpl daoFile = new DaoFileImpl<String>("src/main/resources/memoryFile.txt");
//        Long[] ids = {(long) 1, (long) 2, (long) 3, (long) 4, (long) 5, (long) 6};
        DataModel<String> data1 = new DataModel<String>((long) 7, "1");
        DataModel<String> data2 = new DataModel<String>((long) 2, "2");
        DataModel<String> data3 = new DataModel<String>((long) 3, "3");
        DataModel<String> data4 = new DataModel<String>((long) 4, "4");
        DataModel<String> data5 = new DataModel<String>((long) 5, "5");
        DataModel<String> data6 = new DataModel<String>((long) 6, "6");

        daoFile.save(data1);
        daoFile.save(data2);
        daoFile.save(data3);
        daoFile.save(data4);
        daoFile.save(data5);
        daoFile.save(data6);
        //daoFile.delete(data1);
       Assert.assertEquals(data2, daoFile.find((long)2));
    }
}