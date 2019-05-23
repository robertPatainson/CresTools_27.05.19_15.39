package com.lafayeboulhalfa.app.crestools;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 19.03.2019.
 */

public class SocketCTP implements Serializable, Runnable {

    public SocketCTP(){
    }

    private Thread t;

    private boolean interrupted = false;

    private String url;

    private int port;

    private int deviceId;

    private int projectId;

    private int timeout;

    private SocketHandler socketHandler;

    private Context context;

    private BufferedReader in;

    private DataOutputStream out;

    private Socket socket;

    private String TAG = getClass().getName();

    public static List<SocketCTP> SocketCTPAvailable = new ArrayList<SocketCTP>();

    SocketCTP(String url, int port, int deviceId, int projectId, int timeout, SocketHandler socketHandler, Context context) {

        this.url = url;

        this.port = port;

        this.deviceId = deviceId;

        this.projectId = projectId;

        this.timeout = timeout;

        this.socketHandler = socketHandler;

        this.context = context;

    }

    public String getUrl(){

        return this.url;

    }

    public int getPort(){

        return this.port;

    }

    public int getDeviceId(){

        return this.deviceId;

    }

    public int getProjectId(){

        return this.projectId;

    }

    public Context getContext(){

        return this.context;

    }

    public int getTimeout(){

        return this.timeout;

    }

    public void setUrl(String url){

        this.url = url;

    }

    public void setPort(int port){

        this.port = port;

    }


    public void setTimeout(int timeout){

        this.timeout = timeout;

    }

    public void run() {

        Exception error = null;

        int x = 0;

        int length = 8192;

        char[] chars = new char[length];

        String s;

        final Device device = Device.getDeviceByAddress(getContext(), this.projectId, this.url + ":" + this.port);

        if(device != null){

            try {

                socketHandler.loadHandler();

                socket = new Socket(this.url,this.port);

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out = new DataOutputStream(socket.getOutputStream());

                if(socket.isConnected()){

                    socketHandler.didConnect();

                    Log.d(TAG,"**INFORMATION socket connected : " + this.url + ":" + this.port);

                }

                while(socket.isConnected()) {

                    x = in.read(chars,0, length);

                    while (x !=-1){

                        s = new String(chars,0, x);

                        // System.out.print(s);

                        socketHandler.didReceiveData(s, device);

                        x = in.read(chars,0, length);

                    }

                    if(x == -1){

                        Log.e(TAG, "**WARNING Broken pipe exception : " + this.url + ":" + this.port);

                        disconnectByException();

                        socketHandler.exceptionBrokenPipe(this.getUrl() + ":" + this.getPort());

                        break;

                    }

                }

            } catch (UnknownHostException ex) {

                Log.e(TAG, "**WARNING UnknownHostException : " + this.url + ":" + this.port + " - " + ex.toString());

                error = interrupted ? null : ex;

                socketHandler.errorHandler();

                socketHandler.didDisconnect(error, device);

            } catch (IOException ex) {

                Log.e(TAG, "**WARNING IOException : " + this.url + ":" + this.port + " - " + ex.toString());

                socketHandler.didDisconnect(error, device);

                error = interrupted ? null : ex;

            } catch (Exception ex) {

                Log.e(TAG, "**WARNING Exception : " + this.url + ":" + this.port + " - " + ex.toString());

                error = interrupted ? null : ex;

            } finally {

                t.interrupt();

            }

        }

    }

    public void start () {

        SocketCTPAvailable.add(this);

        if (t == null) {

            t = new Thread (this, url);

            t.start();

        }

    }

    public void write( String data) {

        try {

            // Log.d(TAG, "write(): data = " + data);

            out.writeBytes(data + "\r");

        } catch (IOException ex) {

            Log.e(TAG, "WARNING Exception : ** write(): " + ex.toString());

        } catch (NullPointerException ex) {

            Log.e(TAG, "WARNING Exception : ** write(): " + ex.toString());

        }

    }

    public void disconnect(Device device) throws IOException{

        try{

            if(out != null)
                out.close();

        }finally{

            try{

                if(in != null)
                    in.close();

            }finally{

                if(socket != null)
                    socket.close();

                socketHandler.didDisconnect(null, device); // to change, socket closing must catch exception in SSL and SSH, CTP ok

            }

        }

    }

    public void disconnectByException(){

        Device device = Device.getDeviceByAddress(this.getContext(), this.projectId, this.getUrl() + ":" + this.getPort());

        if(device != null){

            device.setDeviceSocketStatus(false);

            device.serializeDeviceSocketStatus(this.getContext(), false);

            Device.removeFromActiveDevices(device);

            SocketCTPAvailable.remove(this);

        }

    }

}