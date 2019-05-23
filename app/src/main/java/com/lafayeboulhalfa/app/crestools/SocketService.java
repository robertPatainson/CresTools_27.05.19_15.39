package com.lafayeboulhalfa.app.crestools;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by HP on 19.03.2019.
 */

public class SocketService extends IntentService {

    public Device deviceInstance;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.hp.crestools_v01.action.FOO";
    private static final String ACTION_BAZ = "com.example.hp.crestools_v01.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.hp.crestools_v01.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.hp.crestools_v01.extra.PARAM2";

    public SocketService() {
        super("SocketService");
    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        onHandleIntent(intent);

        return START_NOT_STICKY;

    }*/

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SocketService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SocketService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public void letServiceBreath(){

        try {

            TimeUnit.MILLISECONDS.sleep(200);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

    public void connectDevice(Device device){

        device.setDeviceSocketStatus(true);

        deviceInstance.addToActiveDevices(device);

        device.serializeDeviceSocketStatus(SocketService.this, true);

    }

    public void disconnectDevice(Device device, int projectId){

        device.setDeviceSocketStatus(false);

        device.serializeDeviceSocketStatus(SocketService.this, false);

        deviceInstance.removeFromActiveDevices(device);

        switch(device.getDeviceType()){

            case "CTP":

                Iterator<SocketCTP> iterCtpSocket = SocketCTP.SocketCTPAvailable.iterator();

                while (iterCtpSocket.hasNext()) {

                    SocketCTP ctpS = iterCtpSocket.next();

                    String deviceAddress = ctpS.getUrl() + ":" + ctpS.getPort();

                    Device ctpDevice = Device.getDeviceByAddress(getApplicationContext(), projectId, deviceAddress);

                    if (ctpS.getProjectId() == projectId) {

                        if(ctpDevice != null && ctpDevice.getDeviceId() == device.getDeviceId()){

                            letServiceBreath();

                            iterCtpSocket.remove();

                        }

                    }

                }

                break;

            case "SSL":

                Iterator<SocketSSL> iterSslSocket = SocketSSL.SocketSSLAvailable.iterator();

                while (iterSslSocket.hasNext()) {

                    SocketSSL sslS = iterSslSocket.next();

                    String deviceAddress = sslS.getUrl() + ":" + sslS.getPort();

                    Device sslDevice = Device.getDeviceByAddress(getApplicationContext(), projectId, deviceAddress);

                    if (sslS.getProjectId() == projectId) {

                        if(sslDevice != null && sslDevice.getDeviceId() == device.getDeviceId()){

                            letServiceBreath();

                            iterSslSocket.remove();

                        }

                    }

                }

                break;

            case "SSH":

                Iterator<SocketSSH> iterSshSocket = SocketSSH.SocketSSHAvailable.iterator();

                while (iterSshSocket.hasNext()) {

                    SocketSSH sshS = iterSshSocket.next();

                    String deviceAddress = sshS.getUrl() + ":" + sshS.getPort();

                    Device sshDevice = Device.getDeviceByAddress(getApplicationContext(), projectId, deviceAddress);

                    if (sshS.getProjectId() == projectId) {

                        if(sshDevice != null && sshDevice.getDeviceId() == device.getDeviceId()){

                            letServiceBreath();

                            iterSshSocket.remove();

                        }

                    }

                }

                break;

        }

    }

    public void disconnectAllDevices(int projectId, Intent intentDiffuser){

        Device activeDevice;

        Iterator<SocketCTP> iterCtpSocket = SocketCTP.SocketCTPAvailable.iterator();

        Iterator<SocketSSL> iterSslSocket = SocketSSL.SocketSSLAvailable.iterator();

        Iterator<SocketSSH> iterSshSocket = SocketSSH.SocketSSHAvailable.iterator();

        while (iterCtpSocket.hasNext()) {

            SocketCTP ctpS = iterCtpSocket.next();

            if (ctpS.getProjectId() == projectId){

                letServiceBreath();

                String deviceAddress = ctpS.getUrl() + ":" + ctpS.getPort();

                activeDevice = deviceInstance.getDeviceByAddress(getApplicationContext(), projectId, deviceAddress);

                deviceInstance.removeFromActiveDevices(activeDevice);

                activeDevice.setDeviceSocketStatus(false);

                activeDevice.serializeDeviceSocketStatus(SocketService.this, false);

                intentDiffuser.setAction("didDisconnect");

                intentDiffuser.putExtra("deviceId", activeDevice.getDeviceId());

                intentDiffuser.putExtra("projectId", activeDevice.getDeviceProjectId());

                sendBroadcast(intentDiffuser);

                try {

                    ctpS.disconnect(activeDevice);

                } catch (IOException e) {

                    e.printStackTrace();

                }

                iterCtpSocket.remove();

            }

        }

        while (iterSslSocket.hasNext()) {

            SocketSSL sslS = iterSslSocket.next();

            if (sslS.getProjectId() == projectId){

                letServiceBreath();

                String deviceAddress = sslS.getUrl() + ":" + sslS.getPort();

                activeDevice = Device.getDeviceByAddress(getApplicationContext(), projectId, deviceAddress);

                deviceInstance.removeFromActiveDevices(activeDevice);

                activeDevice.setDeviceSocketStatus(false);

                activeDevice.serializeDeviceSocketStatus(SocketService.this, false);

                intentDiffuser.setAction("didDisconnect");

                intentDiffuser.putExtra("deviceId", activeDevice.getDeviceId());

                intentDiffuser.putExtra("projectId", activeDevice.getDeviceProjectId());

                sendBroadcast(intentDiffuser);

                try {

                    sslS.disconnect(activeDevice);

                } catch (IOException e) {

                    e.printStackTrace();

                }

                iterSslSocket.remove();

            }

        }

        while (iterSshSocket.hasNext()) {

            SocketSSH sshS = iterSshSocket.next();

            if (sshS.getProjectId() == projectId){

                letServiceBreath();

                String deviceAddress = sshS.getUrl() + ":" + sshS.getPort();

                activeDevice = Device.getDeviceByAddress(getApplicationContext(), projectId, deviceAddress);

                deviceInstance.removeFromActiveDevices(activeDevice);

                activeDevice.setDeviceSocketStatus(false);

                activeDevice.serializeDeviceSocketStatus(SocketService.this, false);

                intentDiffuser.setAction("didDisconnect");

                intentDiffuser.putExtra("deviceId", activeDevice.getDeviceId());

                intentDiffuser.putExtra("projectId", activeDevice.getDeviceProjectId());

                sendBroadcast(intentDiffuser);

                try {

                    sshS.disconnect(activeDevice);

                } catch (IOException e) {

                    e.printStackTrace();

                }

                iterSshSocket.remove();

            }

        }

    }
    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            final Device device = (Device) intent.getExtras().getSerializable("storedDevice");

            final int projectId = intent.getIntExtra("projectId", 0);

            final int addressPosition = intent.getIntExtra("addressPosition", 0);

            final String buttonText = intent.getStringExtra("buttonText");

            final String command = intent.getStringExtra("command");

            final String connectionAction = intent.getStringExtra("connectionAction");

            final Intent intentDiffuser = new Intent();

            SocketCTP ctpSocket = null;

            SocketSSL sslSocket = null;

            SocketSSH sshSocket = null;

            List<Device> devicesAvailable = new ArrayList<Device>();

            SocketHandler ch = new SocketHandler() {
                @Override
                public void didReceiveData(String data, Device device) {

                    if(device != null){

                        intentDiffuser.setAction("didReceiveData");

                        intentDiffuser.putExtra("data", data);

                        intentDiffuser.putExtra("deviceId", device.getDeviceId());

                        intentDiffuser.putExtra("projectId", projectId);

                        sendBroadcast(intentDiffuser);

                    }

                }

                @Override
                public void didDisconnect(Exception error, Device device) {

                    if(device != null){

                        intentDiffuser.setAction("didDisconnect");

                        intentDiffuser.putExtra("deviceId", device.getDeviceId());

                        intentDiffuser.putExtra("projectId", projectId);

                        sendBroadcast(intentDiffuser);

                    }

                }

                @Override
                public void didConnect() {

                    if(device != null) {

                        connectDevice(device);

                        intentDiffuser.setAction("didConnect");

                        intentDiffuser.putExtra("deviceId", device.getDeviceId());

                        intentDiffuser.putExtra("projectId", projectId);

                        intentDiffuser.putExtra("addressPosition", addressPosition);

                        intentDiffuser.putExtra("buttonText", buttonText);

                        sendBroadcast(intentDiffuser);

                    }

                }

                @Override
                public void errorHandler() {

                    if(device != null){

                        intentDiffuser.setAction("errorHandler");

                        intentDiffuser.putExtra("data", " Error from " + device.getDeviceIp() + " \n");

                        intentDiffuser.putExtra("deviceId", device.getDeviceId());

                        intentDiffuser.putExtra("projectId", projectId);

                        sendBroadcast(intentDiffuser);

                    }

                }

                @Override
                public void loadHandler() {

                    intentDiffuser.setAction("loadHandler");

                    intentDiffuser.putExtra("data", "");

                    intentDiffuser.putExtra("deviceId", device.getDeviceId());

                    intentDiffuser.putExtra("projectId", projectId);

                    sendBroadcast(intentDiffuser);

                }

                @Override
                public void exceptionBrokenPipe(String deviceAddress) {

                    intentDiffuser.setAction("exceptionBrokenPipe");

                    intentDiffuser.putExtra("deviceAddress", deviceAddress);

                    sendBroadcast(intentDiffuser);

                }

            };

            if(device != null){

                try{

                    if(Device.isEmptyList(getApplicationContext()) == false) {

                        devicesAvailable = SerializableManager.readSerializable(SocketService.this, deviceInstance.devicesAvailablePath);

                        for (Device d : devicesAvailable) {

                            if (d.getDeviceId() == device.getDeviceId()) {

                                if (d.getDeviceSocketStatus() == false) {

                                    switch(d.getDeviceType()){

                                        case "CTP":

                                            ctpSocket = new SocketCTP(device.getDeviceIp(), device.getDevicePort(), device.getDeviceId(), device.getDeviceProjectId(), 0, ch, getApplicationContext());

                                            ctpSocket.start();

                                            break;

                                        case "SSL":

                                            sslSocket = new SocketSSL(device.getDeviceIp(), device.getDevicePort(), device.getDeviceId(), device.getDeviceProjectId(), 0, ch, getApplicationContext());

                                            sslSocket.start();

                                            break;

                                        case "SSH":

                                            sshSocket = new SocketSSH(device.getDeviceIp(), device.getDevicePort(), device.getDeviceId(), device.getDeviceProjectId(),
                                                    device.getDeviceUsername(), device.getDevicePassword(), 0, ch, getApplicationContext());

                                            sshSocket.start();

                                            break;

                                    }

                                }else{

                                    switch(d.getDeviceType()){

                                        case "CTP":

                                            for (SocketCTP ctpS : SocketCTP.SocketCTPAvailable) {

                                                String ip = ctpS.getUrl();

                                                int port = ctpS.getPort();

                                                int deviceId = ctpS.getDeviceId();

                                                if (ip.equals(device.getDeviceIp()) && port == device.getDevicePort() && deviceId == device.getDeviceId()) {

                                                    ctpSocket = ctpS;

                                                    if(command == null && connectionAction == null){

                                                        ch.didConnect();

                                                    }

                                                    if (command != null) {

                                                        ctpSocket.write(command);

                                                    }

                                                    if (connectionAction != null && connectionAction != "") {

                                                        switch(connectionAction){

                                                            case "disconnect":

                                                                disconnectDevice(device, projectId);

                                                                ctpSocket.disconnect(device);

                                                                break;

                                                        }

                                                    }

                                                }

                                            }

                                            break;

                                        case "SSL":

                                            for (SocketSSL sslS : SocketSSL.SocketSSLAvailable) {

                                                String ip = sslS.getUrl();

                                                int port = sslS.getPort();

                                                int deviceId = sslS.getDeviceId();

                                                if (ip.equals(device.getDeviceIp()) && port == device.getDevicePort() && deviceId == device.getDeviceId()) {

                                                    sslSocket = sslS;

                                                    if(command == null && connectionAction == null){

                                                        ch.didConnect();

                                                    }

                                                    if (command != null) {

                                                        sslSocket.write(command);

                                                    }

                                                    if (connectionAction != null && connectionAction != "") {

                                                        switch(connectionAction){

                                                            case "disconnect":

                                                                disconnectDevice(device, projectId);

                                                                sslSocket.disconnect(device);

                                                                break;

                                                        }

                                                    }

                                                }

                                            }

                                            break;

                                        case "SSH":

                                            for (SocketSSH sshS : SocketSSH.SocketSSHAvailable) {

                                                String ip = sshS.getUrl();

                                                int port = sshS.getPort();

                                                int deviceId = sshS.getDeviceId();

                                                if (ip.equals(device.getDeviceIp()) && port == device.getDevicePort() && deviceId == device.getDeviceId()) {

                                                    sshSocket = sshS;

                                                    if(command == null && connectionAction == null){

                                                        ch.didConnect();

                                                    }

                                                    if (command != null) {

                                                        sshSocket.write(command);

                                                    }

                                                    if (connectionAction != null && connectionAction != "") {

                                                        switch(connectionAction){

                                                            case "disconnect":

                                                                disconnectDevice(device, projectId);

                                                                sshSocket.disconnect(device);

                                                                break;

                                                        }

                                                    }

                                                }

                                            }

                                            break;

                                    }

                                }

                            }

                        }

                    }

                }catch(Exception e){

                    // Dialog.getDebug(getApplicationContext(), e);

                }

            }else{

                if(connectionAction != null && connectionAction.equals("disconnectAll")){

                    disconnectAllDevices(projectId, intentDiffuser);

                }

            }

        }

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
