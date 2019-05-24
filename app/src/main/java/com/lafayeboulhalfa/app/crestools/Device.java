package com.lafayeboulhalfa.app.crestools;

import android.content.Context;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by HP on 19.03.2019.
 */

public class Device implements Serializable {

    public Device() {

        int delete;
    }

    public static String devicesAvailablePath = "devicesAvailable";

    public static String devicesToImportPath = "devicesImported";

    public static List<Device> activeDevices = new ArrayList<Device>();

    private int deviceId;

    private String deviceIp;

    private int devicePort;

    private String deviceType;

    private String deviceUsername;

    private String devicePassword;

    private String deviceSerialNumber;

    private String deviceProductName;

    private String deviceHostname;

    private int deviceProjectId;

    private boolean deviceMotherStatus;

    private boolean deviceDependanceStatus;

    private int deviceDependanceId;

    private String deviceSerializedOutput;

    private boolean deviceSocketStatus;

    public Device(int deviceId, String deviceIp,int devicePort, String deviceType, String deviceUsername, String devicePassword,
                  String deviceSerialNumber, String deviceProductName, String deviceHostname, int deviceProjectId, boolean deviceMotherStatus,
                  boolean deviceDependanceStatus, int deviceDependanceId, String deviceSerializedOutput, boolean deviceSocketStatus) {

        this.deviceId = deviceId;

        this.deviceIp = deviceIp;

        this.devicePort = devicePort;

        this.deviceType = deviceType;

        this.deviceUsername = deviceUsername;

        this.devicePassword = devicePassword;

        this.deviceSerialNumber = deviceSerialNumber;

        this.deviceProductName = deviceProductName;

        this.deviceHostname = deviceHostname;

        this.deviceProjectId = deviceProjectId;

        this.deviceMotherStatus = deviceMotherStatus;

        this.deviceDependanceStatus = deviceDependanceStatus;

        this.deviceDependanceId = deviceDependanceId;

        this.deviceSerializedOutput = deviceSerializedOutput;

        this.deviceSocketStatus = deviceSocketStatus;

    }

    /**
     *
     * GETTERS
     *
     */
    public int getUniqId(Context context){

        int uniqId = 0;

        if(isEmptyList(context) == false){

            List<Device> devicesAvailable = new ArrayList<Device>();

            devicesAvailable = SerializableManager.readSerializable(context, devicesAvailablePath);

            for(Device device : devicesAvailable){

                uniqId = device.getDeviceId();

            }

        }

        return uniqId+1;

    }

    public int getDeviceId(){    return this.deviceId;    }

    public String getDeviceIp(){       return this.deviceIp;    }

    public String getDeviceType(){      return this.deviceType;     }

    public int getDevicePort(){        return this.devicePort;    }

    public String getDeviceUsername(){        return this.deviceUsername;    }

    public String getDevicePassword(){        return this.devicePassword;    }

    public String getDeviceSerialNumber(){        return this.deviceSerialNumber;    }

    public String getDeviceProductName(){        return this.deviceProductName;    }

    public String getDeviceHostname(){        return this.deviceHostname;    }

    public int getDeviceProjectId(){    return this.deviceProjectId;    }

    public boolean getDeviceMotherStatus(){   return this.deviceMotherStatus;   }

    public boolean getDeviceDependanceStatus(){   return this.deviceDependanceStatus;   }

    public int getDeviceDependanceId(){     return this.deviceDependanceId;     }

    public String getDeviceserializedOutput(){      return this.deviceSerializedOutput;     }

    public boolean getDeviceSocketStatus(){     return this.deviceSocketStatus;    }

    /**
     *
     * SETTERS
     *
     */
    public void setDeviceId(int deviceId){       this.deviceId = deviceId;     }

    public void setDeviceIp(String deviceIp){        this.deviceIp = deviceIp;    }

    public void setDeviceType(String deviceType){       this.deviceType = deviceType;   }

    public void setDevicePort(int devicePort){        this.devicePort = devicePort;    }

    public void setDeviceUsername(String deviceUsername){        this.deviceUsername = deviceUsername;    }

    public void setDevicePassword(String devicePassword){        this.devicePassword = devicePassword;    }

    public void setDeviceSerialNumber(String deviceSerialNumber){        this.deviceSerialNumber = deviceSerialNumber;    }

    public void setDeviceProductName(String deviceProductName){        this.deviceProductName = deviceProductName;    }

    public void setDeviceHostname(String deviceHostname){        this.deviceHostname = deviceHostname;    }

    public void setDeviceProjectId(int deviceProjectId){       this.deviceProjectId = deviceProjectId;     }

    public void setDeviceMotherStatus(boolean deviceMotherStatus){      this.deviceMotherStatus = deviceMotherStatus;   }

    public void setDeviceDependanceStatus(boolean deviceDependanceStatus){  this.deviceDependanceStatus = deviceDependanceStatus;   }

    public void setDeviceDependanceId(int deviceDependanceId){      this.deviceDependanceId = deviceDependanceId;   }

    public void setDeviceSerializedOutput(String deviceSerializedOutput){   this.deviceSerializedOutput = deviceSerializedOutput;   }

    public void setDeviceSocketStatus(boolean deviceSocketStatus){      this.deviceSocketStatus = deviceSocketStatus;   }

    /****************************************FUNCS*******************************************/
    public String toString(){

        return "id : " + this.deviceId + "\nip : " + this.deviceIp +  "\nport : " + this.devicePort + "\nprotocol : " + this.deviceType + "\nusername : " + this.deviceUsername +

                "\npassword : " + this.devicePassword + "\nserial_number : " + this.deviceSerialNumber + "\nproduct_name : " +

                this.deviceProductName + "\nhostname : " + this.deviceHostname + "\nmother status : " + this.deviceMotherStatus + "\ndependance status : " + this.deviceDependanceStatus;

    }

    public static boolean isEmptyList(Context context){

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(context, devicesAvailablePath);

        if(devicesAvailable == null || devicesAvailable.isEmpty()){

            return true;

        }

        return false;


    }

    public static boolean isEmptyListByProject(Context context, int projectId){

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(context, devicesAvailablePath);

        List<Device> devicesAvailableByProject = new ArrayList<Device>();

        if(devicesAvailable != null && !devicesAvailable.isEmpty()){

            for(Device deviceAvailable : devicesAvailable){

                if(deviceAvailable.getDeviceProjectId() == projectId)
                    devicesAvailableByProject.add(deviceAvailable);

            }

        }

        if(devicesAvailableByProject == null || devicesAvailableByProject.isEmpty()){

            return true;

        }

        return false;


    }

    public static boolean isEmptySocketList(){

        if(SocketCTP.SocketCTPAvailable != null && !SocketCTP.SocketCTPAvailable.isEmpty() ||
                SocketSSL.SocketSSLAvailable != null && !SocketSSL.SocketSSLAvailable.isEmpty() ||
                SocketSSH.SocketSSHAvailable != null && !SocketSSH.SocketSSHAvailable.isEmpty()){

            return false;

        }else{

            return true;

        }

    }

    public static Device getDeviceByIp(Context c, int projectId, String deviceIp){

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(c, "devicesAvailable");

        if(devicesAvailable != null && !devicesAvailable.isEmpty()) {

            for(final Device deviceAvailable : devicesAvailable) {

                if(deviceAvailable.getDeviceIp().equals(deviceIp) && deviceAvailable.getDeviceProjectId() == projectId) {

                    return deviceAvailable;

                }

            }

        }

        return null;

    }

    public static int getAmountDevices(Context c){

        int iterDevices = 0;

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(c, "devicesAvailable");

        for(final Device deviceAvailable : devicesAvailable) {

            iterDevices = iterDevices + 1;

        }

        return iterDevices;

    }

    public static boolean isAlreadyAddress(Context context, int projectId, String ip, int port){

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(context, devicesAvailablePath);

        List<Device> devicesAvailableByProject = new ArrayList<Device>();

        if(devicesAvailable != null && !devicesAvailable.isEmpty()){

            for(Device deviceAvailable : devicesAvailable){

                if(deviceAvailable.getDeviceProjectId() == projectId)
                    devicesAvailableByProject.add(deviceAvailable);

            }

        }

        if(devicesAvailableByProject != null && !devicesAvailableByProject.isEmpty()){

            for(Device deviceAvailableByProject : devicesAvailableByProject){

                if(deviceAvailableByProject.getDeviceIp().equals(ip) && deviceAvailableByProject.getDevicePort() == port){

                    return true;

                }

            }

        }

        return false;

    }

    public static Device getDeviceByAddress(Context c, int projectId, String deviceAddress){

        String[] parts = deviceAddress.split(":");

        String ip = parts[0];

        int port = Integer.parseInt(parts[1]);

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(c, devicesAvailablePath);

        if(devicesAvailable != null && !devicesAvailable.isEmpty()){

            for(final Device deviceAvailable : devicesAvailable) {

                if(deviceAvailable.getDeviceProjectId() == projectId){

                    if(deviceAvailable.getDeviceIp().equals(ip) && deviceAvailable.getDevicePort() == port) {

                        return deviceAvailable;

                    }

                }


            }

        }

        return null;

    }

    public static Device getDeviceById(Context c, int projectId, int deviceId){

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(c, devicesAvailablePath);

        if(devicesAvailable != null && !devicesAvailable.isEmpty()){

            for(final Device deviceAvailable : devicesAvailable) {

                if(deviceAvailable.getDeviceProjectId() == projectId){

                    if(deviceAvailable.getDeviceId() == deviceId) {

                        return deviceAvailable;

                    }

                }


            }

        }

        return null;

    }

    public void ToDelete(){}

    public static List<Device> getDeviceByProject(Context c, int projectId){

        List<Device> devicesByProject = new ArrayList<Device>();

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(c, devicesAvailablePath);

        if(devicesAvailable != null && !devicesAvailable.isEmpty()){

            for(final Device deviceAvailable : devicesAvailable) {

                if(deviceAvailable.getDeviceProjectId() == projectId){

                    devicesByProject.add(deviceAvailable);

                    //zr
                }


            }

        }

        if(devicesByProject != null){

            return devicesByProject;

        }

        return null;

    }

    public static List<Device> getMothersDeviceByProject(Context c, int projectId){

        List<Device> devicesByProject = new ArrayList<Device>();

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(c, devicesAvailablePath);

        if(devicesAvailable != null && !devicesAvailable.isEmpty()){

            for(final Device deviceAvailable : devicesAvailable) {

                if(deviceAvailable.getDeviceProjectId() == projectId && deviceAvailable.getDeviceDependanceStatus() == false && deviceAvailable.getDeviceMotherStatus() == true){

                    devicesByProject.add(deviceAvailable);

                }

            }

        }

        if(devicesByProject != null){

            return devicesByProject;

        }

        return null;

    }

    public boolean deviceHasChild(Context c){

        List<Device> devicesByProject = getDeviceByProject(c, this.getDeviceProjectId());

        if(devicesByProject != null){

            for(Device deviceByProject:devicesByProject){

                if(deviceByProject.getDeviceDependanceId() == this.getDeviceId())
                    return true;

            }

        }

        return false;

    }

    public static List<Device> getDeviceChildren(Context c, Device device){

        List<Device> devicesByProject = getDeviceByProject(c, device.getDeviceProjectId());

        List<Device> devicesChildren = new ArrayList<Device>();

        if(devicesByProject != null){

            for(Device deviceByProject:devicesByProject){

                if(deviceByProject.getDeviceDependanceId() == device.getDeviceId())
                    devicesChildren.add(deviceByProject);

            }

        }

        return devicesChildren;

    }

    public static Device getMotherDevice(Context context, Device child){

        Device mother = null;

        List<Device> mothersByProject = getMothersDeviceByProject(context, child.getDeviceProjectId());

        if(mothersByProject != null && mothersByProject.size() > 0){

            for(Device motherByProject : mothersByProject){

                if(motherByProject.getDeviceId() == child.getDeviceDependanceId())
                    mother = motherByProject;

            }

        }

        return mother;

    }

    public void serializeDeviceSocketStatus(Context context, boolean status){

        if(Device.isEmptySocketList() == false){

            List<Device> devicesAvailable = new ArrayList<Device>();

            devicesAvailable = SerializableManager.readSerializable(context, devicesAvailablePath);

            for (Device d : devicesAvailable) {

                if(d.getDeviceId() == this.getDeviceId()){

                    d.setDeviceSocketStatus(status);

                    SerializableManager.saveSerializable(context, devicesAvailable, devicesAvailablePath);

                }

            }

        }

    }

    public void serializeDeviceOutput(Context context, String output){

        if(Device.isEmptySocketList() == false){

            String currentDeviceOutput = output;

            List<Device> devicesAvailable = new ArrayList<Device>();

            devicesAvailable = SerializableManager.readSerializable(context, devicesAvailablePath);

            for (Device d : devicesAvailable) {

                if(d.getDeviceId() == this.getDeviceId()){

                    d.setDeviceSerializedOutput(currentDeviceOutput);

                    SerializableManager.saveSerializable(context, devicesAvailable, devicesAvailablePath);

                }

            }

        }

    }

    public static void addToActiveDevices(Device currentDevice){

        boolean isExisting = false;

        for(Device activeDevice : activeDevices){

            if(activeDevice.getDeviceId() == currentDevice.getDeviceId()){

                isExisting = true;

            }

        }

        if(isExisting == false)
            activeDevices.add(currentDevice);

    }

    public static void removeFromActiveDevices(Device currentDevice){

        for(Device activeDevice : activeDevices){

            if(activeDevice.getDeviceId() == currentDevice.getDeviceId()){

                activeDevices.remove(activeDevice);

                break;

            }

        }

    }

    public static void initAllDeviceSocketStatus(Context context, boolean status){

        if(isEmptyList(context) == false){

            if(isEmptySocketList() == true){

                List<Device> devicesAvailable = SerializableManager.readSerializable(context, devicesAvailablePath);

                for (Device d : devicesAvailable) {

                    if(d.getDeviceSocketStatus() == true){

                        d.setDeviceSocketStatus(false);

                    }

                }

                SerializableManager.saveSerializable(context, devicesAvailable, devicesAvailablePath);

            }

        }

    }

    public static int getDeviceDefaultPort(String deviceType){

        int port = 0;

        switch(deviceType){

            case "CTP":

                port = 41795;

                break;

            case "SSL":

                port = 41797;

                break;

            case "SSH":

                port = 22;

                break;

        }

        return port;

    }

    public static void orderListByHostname(List<Device> devicesToOrder){

        Collections.sort(devicesToOrder, new Comparator<Device>() {
            @Override
            public int compare(final Device object1, final Device object2) {
                return object1.getDeviceHostname().compareTo(object2.getDeviceHostname());
            }
        });

    }

}