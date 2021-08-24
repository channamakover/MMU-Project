package com.hit.services;
import com.hit.dm.DataModel;

import java.io.IOException;

/**
 * a defence layer between the service and the request handler
 */
public class CacheUnitController<T> {
    private CacheUnitService CUS;

    public CacheUnitController() {
        CUS =new CacheUnitService();
    }

    public boolean update(DataModel<T>[] dataModels){
        return CUS.update(dataModels);
    }

    public boolean delete(DataModel<T>[] dataModels){
        return CUS.delete(dataModels);
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) throws IOException {
        return CUS.get(dataModels);
    }

    public String getStatistics(){
        return CUS.getStatistics();
    }

    public void saveCache(){CUS.saveCache(); }
}
