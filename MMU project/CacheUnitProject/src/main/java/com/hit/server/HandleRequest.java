package com.hit.server;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Locale;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> implements Runnable {
    private Socket socket;
    private CacheUnitController<T> controller;
    private Request<DataModel<T>[]> request;
    private Boolean requestStatus;

    /** C'tor
     * @param s a socket to connect between server and client
     * @param controller a defence layer between the service and the request handler
     */
    public HandleRequest(Socket s, CacheUnitController<T> controller) {
        socket = s;
        this.controller = controller;
    }

    /**
     * starts a new thread to take care of a request
     * listen to the socket for coming connections
     * accepts a request from the socket, sends it to the proper function for handling,
     * write the returned values into the socket, so the client could get information
     */
    public void run() {
        DataInputStream in = null;
        DataOutputStream out = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data;
        try {
            while (in.available() > 0) {
                data = in.readUTF();
                stringBuilder.append(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        data = stringBuilder.toString();
        if (data.equals("getStatistics")) {
            String statistics = controller.getStatistics();
            try {
                out.writeUTF(statistics);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Type ref = new TypeToken<Request<DataModel<T>[]>>() {
            }.getType();
            request = new Gson().fromJson(data, ref);

            switch (request.getHeaders().get("action").toLowerCase(Locale.ROOT)) {
                case "update":
                    requestStatus = controller.update(request.getBody());
                    break;
                case "get":
                    try {
                        DataModel[] getReturnArray=controller.get(request.getBody());
                        String dmString=new String();
                        for (DataModel dm : getReturnArray) {
                            if (dm!=null) {
                                dmString+=dm.toString();
                                System.out.println(dmString);
                            }

                            out.writeUTF(dmString);
                        }
                        requestStatus=true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "delete":
                    requestStatus = controller.delete(request.getBody());

                    break;
            }

            try {
                if (requestStatus) {
                    out.writeUTF("Succeeded");
                    System.out.println("Succeeded");
                } else {
                    if (!requestStatus) {
                        out.writeUTF("Failed");
                        System.out.println("Failed");
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * save the cache (main memory) to dao in case 'shutdown' has been pressed
     */
    public void saveCache(){controller.saveCache(); }
}
