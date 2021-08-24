package com.hit.client;

import com.hit.view.CacheUnitView;
import java.beans.PropertyChangeEvent;
import java.lang.String;
import java.beans.PropertyChangeListener;

public class CacheUnitClientObserver implements PropertyChangeListener {

    private CacheUnitClient cacheUnitClient;
    private CacheUnitView cacheUnitView;

    public CacheUnitClientObserver() {
        cacheUnitClient = new CacheUnitClient();
    }


    /**
     * @param evt updates the ui according to the current propertyChangEvent
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String res= cacheUnitClient.send((String) evt.getNewValue());
        cacheUnitView = (CacheUnitView) evt.getSource();
        cacheUnitView.updateUIData(res);
    }
}