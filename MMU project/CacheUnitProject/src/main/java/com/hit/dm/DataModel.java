package com.hit.dm;
import java.lang.*;
import java.util.HashMap;
import java.util.Objects;

public class DataModel <T> implements java.io.Serializable{
    private Long id=null;
    private T content=null;

    /** Constructor
     * @param id a key
     * @param content a value
     */
    public DataModel(Long id, T content){
        this.id=id;
        this.content=content;
    }

    /**
     * @param o an object to compare
     * @return if both arguments are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModel<?> dataModel = (DataModel<?>) o;
        return Objects.equals(id, dataModel.id) && Objects.equals(content, dataModel.content);
    }

    /**
     * @return dataModel as a string
     */
    @Override
    public String toString() {
        return "DataModel {" +
                "id=" + id +
                ", content=" + content +
                '}';
    }

    public Long getDataModelId(){
        return this.id;
    }

    public void setDataModelId(java.lang.Long id){
        this.id=id;
    }

    public T getContent(){
        return this.content;
    }

    public void setContent(T content){
        this.content=content;
    }
}
