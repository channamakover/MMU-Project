package com.hit.memory;

import com.hit.dm.DataModel;
import java.lang.*;
import com.hit.algorithm.*;


public class CacheUnit<T> {
    private static CacheUnit instance = null;
    private IAlgoCache<Long, DataModel<T>> algo;
    private int numOfDataModels;

    /**
     * @param algo of the MMU(LRU/MFU/Random)
     */
    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo) {
        this.algo = algo;
        numOfDataModels=0;
    }

    /**
     * @param ids a list of dataModels keys
     * @return the matching dataModels to the given keys
     */
    public DataModel<T>[] getDataModels(Long[] ids) {
        DataModel[] wantedDataModels = new DataModel[ids.length];
        for (int i = 0; i < ids.length; i++) {
            wantedDataModels[i] = algo.getElement(ids[i]);
        }
        return wantedDataModels;
    }

    /**
     * @param dataModels to be inserted to the cache(main memory)
     * @return dataModels that have been paged out of the cache according to the chosen algo
     */
    public DataModel<T>[] putDataModels(DataModel<T>[] dataModels) {
        DataModel[] returnedValues = new DataModel[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) {
            if (dataModels[i] != null) {
                returnedValues[i] = algo.putElement(dataModels[i].getDataModelId(), dataModels[i]);
            }
        }
        return returnedValues;
    }

    /**
     * @param ids of dataModels to be removed from the cache
     */
    public void removeDataModels(Long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] != null) {
                algo.removeElement(ids[i]);
            }
        }
    }


}
