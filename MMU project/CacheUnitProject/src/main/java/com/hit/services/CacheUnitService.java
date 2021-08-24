package com.hit.services;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

import java.util.List;


public class CacheUnitService<T> {
    public static int CACHEUNIT_CAPACITY =6;
    static LRUAlgoCacheImpl algo = new LRUAlgoCacheImpl(CACHEUNIT_CAPACITY);
    static CacheUnit cacheUnit = new CacheUnit(algo);
    private DaoFileImpl db;
    private int numOfRequests;
    private int numOfFailedRequests;
    private int numOfDataModels;
    private int numOfSwappedDataModels;


    /** C'tor
     * creates a new dao(secondary memory) or opens it if exists
     */
    public CacheUnitService() {
        this.db = new DaoFileImpl(System.getProperty("user.dir") + "\\src\\main\\resources\\memoryFile.txt");
        this.numOfRequests=0;
        this.numOfSwappedDataModels=0;
        this.numOfDataModels=0;
    }


    /**
     * @param dataModels array of dataModels that should be updated
     * put the updated dataModels into the cache and saves the pages that have been pages out of the cache to dao
     * @return if the operation was been succeeded
     */
    public boolean update(DataModel<T>[] dataModels) {
        numOfRequests++;
        numOfDataModels+=dataModels.length;
        try {
            DataModel[] pageFaults = cacheUnit.putDataModels(dataModels);
            for (DataModel dataModel:pageFaults) {
                if (dataModel!=null){
                    db.save(dataModel);
                    numOfSwappedDataModels++;
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            numOfFailedRequests++;
            return false;
        }
    }

    /**
     * @param dataModels to be removed from memory(cache or dao)
     * @return if the operation has been succeeded
     */
    public boolean delete(DataModel<T>[] dataModels) {
        numOfRequests++;
        numOfDataModels+=dataModels.length;
        try {
            for (DataModel dm : dataModels) {
                db.delete(dm);
            }
            Long[] dataModelsIds = extractDataModelsIds(dataModels);
            cacheUnit.removeDataModels(extractDataModelsIds(cacheUnit.getDataModels(dataModelsIds)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param dataModels to get from memory(cache or dao)
     * for each dataModel that is not in cache, check if it exist in dao, and bring it to cache
     * @return a list of wanted dataModels
     */
    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        numOfRequests++;
        numOfDataModels+=dataModels.length;
        Long[] dataModelsIds = extractDataModelsIds(dataModels);
        DataModel[] dm = null;
        DataModel[] returnedDataModel = cacheUnit.getDataModels(dataModelsIds);
        for (int i = 0; i < dataModelsIds.length; i++) {
            if (returnedDataModel[i] == null) {

                if (db.find(dataModelsIds[i]) != null) {

                    returnedDataModel[i] = db.find(dataModelsIds[i]);
                }
            }
        }
        dm = cacheUnit.putDataModels(returnedDataModel);
        for (DataModel item : dm) {
            if (item != null) {
                db.save(item);
                numOfSwappedDataModels++;
            }
        }
        return returnedDataModel;
    }

    /**
     * @param dataModels a list of dataModels
     * @return a list of matching keys
     */
    private Long[] extractDataModelsIds(DataModel<T>[] dataModels) {
        Long[] dataModelsIds = new Long[dataModels.length];
        int i=0;
        for (DataModel dm :dataModels) {
            if (dm != null){
                dataModelsIds[i] = dataModels[i].getDataModelId();
            }
            i++;
        }
        return dataModelsIds;
    }

    /**
     * @return all sort of statistics as a string
     */
    public String getStatistics(){
        String statistics=new String(
                "CacheUnit capacity: " + CACHEUNIT_CAPACITY
                        + "\n"
                        +"DAO capacity: " + db.getCapacity()
                        + "\n"
                        + "Algorithm: " + algo
                        + "\n"
                        + "Total number of requests:" + numOfRequests
                        + "\n"
                        + "Total number of DataModels (GET/UPDATE/DELETE):" + numOfDataModels
                        + "\n"
                        + "Total number of DataModels swap: " + numOfSwappedDataModels
        );;
        return statistics;
    }

    /**
     * save the cache (main memory) to dao in case 'shutdown' has been pressed
     */
    public void saveCache(){
        List<DataModel> cache = algo.getCache();
        for (DataModel dm : cache) {
            db.save(dm);
        }
    }
}