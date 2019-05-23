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

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by HP on 19.03.2019.
 */

public class SocketSSL implements Serializable, Runnable {

    public SocketSSL(){
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

    public static List<SocketSSL> SocketSSLAvailable = new ArrayList<SocketSSL>();

    public boolean isInterruptedByHuman = false;

    SocketSSL(String url, int port, int deviceId, int projectId, int timeout, SocketHandler socketHandler, Context context) {

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

    public int getTimeout(){

        return this.timeout;

    }

    public Context getContext(){

        return this.context;

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

                TrustManager[] trustAllCerts = { new X509TrustManager()
                {

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}

                }
                };

                SSLContext context = SSLContext.getInstance("SSL");

                context.init(null, trustAllCerts, new java.security.SecureRandom());

                SocketFactory socketFactory = context.getSocketFactory();

                socket = socketFactory.createSocket(this.url,this.port);

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

                        x = in.read(chars,0,length);

                    }

                    if(x == -1 && isInterruptedByHuman == false){

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

                Log.d(TAG, "**WARNING IOException : " + this.url + ":" + this.port + " - " + ex.toString());

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

        SocketSSLAvailable.add(this);

        if (t == null) {

            t = new Thread (this, url);

            t.start();

        }

    }

    public void write(String data) {

        try {

            // Log.d(TAG, "write(): data = " + data);

            out.writeBytes(data + "\r\n");

        } catch (IOException ex) {

            Log.e(TAG, "write(): " + ex.toString());

        } catch (NullPointerException ex) {

            Log.e(TAG, "write(): " + ex.toString());

        }
    }

    public void disconnect(Device device) throws IOException{

        isInterruptedByHuman = true;

        try{

            if(out != null)
                out.close();

        }finally{

            if(socket != null)
                socket.close();

            if(in != null)
                in.close();

            socketHandler.didDisconnect(null, device); // to change, socket closing must catch exception in SSL and SSH, CTP ok

        }

    }

    public void disconnectByException(){

        Device device = Device.getDeviceByAddress(this.getContext(), this.projectId, this.getUrl() + ":" + this.getPort());

        if(device != null){

            device.setDeviceSocketStatus(false);

            device.serializeDeviceSocketStatus(this.getContext(), false);

            Device.removeFromActiveDevices(device);

            SocketSSLAvailable.remove(this);

        }

    }

}
