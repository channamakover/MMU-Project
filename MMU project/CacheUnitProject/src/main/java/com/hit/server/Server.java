package com.hit.server;

import com.hit.services.CacheUnitController;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server implements Runnable, PropertyChangeListener {
    private ServerSocket serverSocket;
    private Boolean serverCondition = false;
    HandleRequest<String> handleRequest;

    /** C'tor
     * creates a server socket
     */
    public Server() {
        try {
            serverSocket = new ServerSocket(12345);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * wait for a client connection and request, send the given request, as a new thread to handleRequest
     */
    @Override
    public void run() {
        System.out.println("Starting server...\n");
        Executor exec = Executors.newFixedThreadPool(10);
        CacheUnitController<String> controller = new CacheUnitController<String>();
        while (serverCondition) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                handleRequest = new HandleRequest<String>(clientSocket, controller);
                exec.execute(handleRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param evt gets the user command from the CLI
     * and take steps according it
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String command = evt.getNewValue().toString();
        if (command.equals("start")) {
            new Thread(this).start();
            serverCondition = true;
        } else {
            if (command.equals("shutdown")) {
                if (handleRequest!=null){
                    handleRequest.saveCache();
                }
                serverCondition = false;
                // serverSocket.close();
            }
        }
    }
}