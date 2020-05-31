package com.codeschool.Network;


import com.github.nkzawa.socketio.client.Socket;

public class WebSocket {

    private static Socket mSocket;

    public static synchronized Socket getSocket(){
        return mSocket;
    }

    public static synchronized void setSocket(Socket socket){
        WebSocket.mSocket = socket;
    }

}
