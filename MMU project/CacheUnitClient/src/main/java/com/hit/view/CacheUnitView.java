package com.hit.view;
import java.awt.*;
import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class CacheUnitView {

    private PropertyChangeSupport propertyChangeSupport;
    private JFrame frame;
    private CacheUnitScreen screen;

    public CacheUnitView() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        screen = new CacheUnitScreen(propertyChangeSupport);
        frame = new JFrame("CacheUnitUI");

    }

    /**
     * set the frame with features
     */
    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.add(screen.getPanel());
                frame.setSize(700, 600);
                frame.setLocationRelativeTo(null);
                frame.getContentPane().setBackground(new Color(125,20,240));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        propertyChangeSupport.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        propertyChangeSupport.removePropertyChangeListener(pcl);
    }

    /**
     * @param t to be updated in the frame textArea
     * @param <T>
     */
    public <T> void updateUIData(T t){
        screen.textArea.setText((String) t);
    }
}
