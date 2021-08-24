package com.hit.util;

import java.beans.PropertyChangeSupport;
import java.io.InputStream;
import java.io.OutputStream;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import java.lang.String;
import java.util.Locale;
import java.util.Scanner;

public class CLI implements java.lang.Runnable {
    private Scanner in;
    private PrintWriter out;
    private PropertyChangeSupport keepChanges;
    private Boolean isRunning = true;
    private Boolean serverCondition = false;


    /**C'tor
     * @param in inputStream
     * @param out outputStream
     */
    public CLI(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintWriter(out, true);
        this.keepChanges = new PropertyChangeSupport(this);
    }

    /**
     * @param pcl
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.keepChanges.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        this.keepChanges.removePropertyChangeListener(pcl);
    }

    public void write(String string) {
        System.out.println(string);
    }

    @Override
    public void run() {

        while (isRunning) {
            try {
                write("Please enter your command\n");
                String command = in.nextLine().toLowerCase(Locale.ROOT);
                switch (command) {
                    case "start":
                        keepChanges.firePropertyChange("Command", null, command);
                        serverCondition = true;
                        break;
                    case "shutdown":
                        if (serverCondition) {
                            isRunning = false;
                            serverCondition = false;
                            System.out.println("Shutdown server");
                            keepChanges.firePropertyChange("Command", null, command);
                        } else {
                            System.out.println("Server is already down...");
                        }
                        break;
                    default:
                        System.out.println("Not a valid command");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        in.close();
        out.close();
    }
}
