package com.hit.dao;

import com.google.gson.Gson;
import com.hit.dm.*;
import java.io.*;
import java.lang.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.*;
import com.google.gson.reflect.TypeToken;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
    private Map<Long, DataModel<T>> memoryArray;
    private int capacity;
    private String filePath;

    /** Constructor with default capacity
     * @param filePath the path of the 'Secondary memory' file
     */
    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
        memoryArray = new HashMap<Long, DataModel<T>>();
        this.capacity = 1024;
    }

    /** Constructor if capacity is provided
     * @param filePath the path of the 'Secondary memory' file
     * @param capacity of the 'Secondary memory' (not mandatory)
     */
    public DaoFileImpl(String filePath, int capacity) {
        this.filePath = new File("src/FilePath/resources/" + filePath).getAbsolutePath();
        memoryArray = new HashMap<Long, DataModel<T>>();
        this.capacity = capacity;
    }

    /**
     * private function that each time copy the data in the dao to a map so it could be able to set changes
     */
    private void readData() {
        Gson gson = new Gson();
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            Type type = new TypeToken<HashMap<Long, DataModel<T>>>() {
            }.getType();
            HashMap<Long, DataModel<T>> jsonData = gson.fromJson(fileReader, type);
            if (jsonData != null) {
                memoryArray = jsonData;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * updates data into the dao
     */
    private void updateData() {
        Gson gson = new Gson();
        try {
            FileOutputStream file = new FileOutputStream(filePath);
            String data = gson.toJson(memoryArray);
            file.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param entity - a dataModel to be saved in dao removes the old version if exists
     */
    @Override
    public void save(DataModel<T> entity) {
        readData();
        DataModel<T> checkExistence = find(entity.getDataModelId());
        if (checkExistence != null) {
            memoryArray.remove(entity.getDataModelId());
        }
        memoryArray.put(entity.getDataModelId(), entity);
        updateData();
    }

    /**
     * @param entity - dataModel to be deleted from dao
     * @throws IllegalArgumentException
     */
    @Override
    public void delete(DataModel<T> entity) throws IllegalArgumentException {
        readData();
        DataModel<T> checkExistence = find(entity.getDataModelId());
        if (checkExistence != null) {
            memoryArray.remove(entity.getDataModelId());
            updateData();
        }
    }

    /**
     * @param aLong a key whose value is wanted
     * @return dataModel that match the given key
     * @throws IllegalArgumentException
     */
    @Override
    public DataModel<T> find(Long aLong) throws IllegalArgumentException {
        if (memoryArray.get(aLong) != null)
            return memoryArray.get(aLong);
        return null;
    }

    public int getCapacity(){
        return this.capacity;
    }

}

