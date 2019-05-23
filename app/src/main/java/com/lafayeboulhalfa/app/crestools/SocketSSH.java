package com.lafayeboulhalfa.app.crestools;

import android.content.Context;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by HP on 19.03.2019.
 */

public class SocketSSH implements Serializable, Runnable {

    public SocketSSH(){
    }

    private Thread t;

    private boolean interrupted = false;

    private String url;

    private int port;

    private int deviceId;

    private int projectId;

    private String username;

    private String password;

    private int timeout;

    private Context context;

    private SocketHandler socketHandler;

    private InputStream in;

    private OutputStream out;

    private BufferedReader reader;

    private Session session;

    JSch jsch = new JSch();

    private String TAG = getClass().getName();

    public static List<SocketSSH> SocketSSHAvailable = new ArrayList<SocketSSH>();

    private String command;

    public boolean isInterruptedByHuman = false;

    SocketSSH(String url, int port, int deviceId, int projectId, String username, String password, int timeout, SocketHandler socketHandler, Context context) {

        this.url = url;

        this.port = port;

        this.deviceId = deviceId;

        this.projectId = projectId;

        this.username = username;

        this.password = username;

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

    public String getUsername(){

        return this.username;

    }

    public String getPassword(){

        return this.password;

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

    public void setUser(String username){

        this.username = username;

    }

    public void setPassword(String password){

        this.password = password;

    }

    public void setTimeout(int timeout){

        this.timeout = timeout;

    }

    public void run() {

        Exception error = null;

        int x = 0;

        int length = 8192;

        byte[] bytes = new byte[length];

        String s;

        final Device device = Device.getDeviceByAddress(getContext(), this.projectId, this.url + ":" + this.port);

        if(device != null){

            try {

                socketHandler.loadHandler();

                if(this.username == null || this.username.equals(""))
                    socketHandler.didDisconnect(error, device);

                session = jsch.getSession(this.username, this.url, this.port);

                session.setPassword(this.password);

                session.setConfig("StrictHostKeyChecking", "no");

                session.setServerAliveInterval(2000);

                session.connect(2000);

                if(session.isConnected()){

                    socketHandler.didConnect();

                    Channel channel = session.openChannel("shell");

                    in = channel.getInputStream();

                    out = channel.getOutputStream();

                    channel.connect(3*1000);

                    Log.d(TAG,"**INFORMATION socket connected : " + this.url + ":" + this.port);

                }

                while(session.isConnected()) {

                    x = in.read(bytes,0, length);

                    while (x !=-1){

                        s = new String(bytes,0, x);

                        // System.out.print(s);

                        socketHandler.didReceiveData(s, device);

                        x = in.read(bytes,0, length);

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

        SocketSSHAvailable.add(this);

        if (t == null) {

            t = new Thread (this, url);

            t.start();

        }

    }

    public void write(String data) {

        try {

            // Log.d(TAG, "write(): data = " + data);

            command = data;

            data = data + "\r\n";

            out.write(data.getBytes());

            out.flush();

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

            try{

                if(in != null)
                    in.close();

            }finally{

                if(session != null)
                    session.disconnect();

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

            SocketSSHAvailable.remove(this);

        }

    }

    public void letSocketBreath(){

        try {

            TimeUnit.MILLISECONDS.sleep(200);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

}