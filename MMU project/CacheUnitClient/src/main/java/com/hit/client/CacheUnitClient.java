package com.hit.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.String;
import java.net.InetAddress;
import java.net.Socket;

public class CacheUnitClient {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private InetAddress inetAddress;

    public static final Integer PORT = 12345;

    public CacheUnitClient() {
    }

    /**
     * @param request to send to the server to be handled
     * @return server's response
     */
    public String send(String request) {
        String res = "Could not connect the server";

        try {
            inetAddress = InetAddress.getByName("localhost");
            socket = new Socket(inetAddress, PORT);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeUTF(request);
            dataOutputStream.flush();
            res = (String) dataInputStream.readUTF();
            socket.close();
            dataOutputStream.close();
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
