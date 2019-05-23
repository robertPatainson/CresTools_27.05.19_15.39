package com.lafayeboulhalfa.app.crestools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 19.03.2019.
 */

public class Xadr {

    public static String tempDevicesToImport = "tempDevicesToImport";

    private String xadrName;

    private String xadrContent;

    public Xadr(String xadrName, String xadrContent){

        this.xadrName = xadrName + ".xadr";

        this.xadrContent = xadrContent;

    }

    public String getXadrName(){    return this.xadrName;    }

    public String getXadrContent(){    return this.xadrContent;   }

    public void setXadrNameName(String xadrName){

        this.xadrName = xadrName;

    }

    public void setXadrContentContent(String xadrContent){

        this.xadrContent = xadrContent;

    }

    public static void writeXadr(Xadr xadr, Context context) throws IOException {

        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        File file = new File(path, xadr.getXadrName());

        FileOutputStream stream = new FileOutputStream(file);

        try {

            stream.write(xadr.getXadrContent().getBytes());

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            stream.close();

        }

    }

    public static String encode(Context context, List<Device> devices){

        String toXadr = "[Crestron_Toolbox_Address_Book]\n" +
                "\n" +
                "[Entries]\n" +
                "This .xadr file was created using Crestools\n" +
                "It is not compatible with this version of Toolbox.=:slot 02\n" +
                "Please open a .adr Address Book to continue.=:slot 03\n" +
                "\n" +
                "[AddressBookInfo]\n" +
                "Schema=2.0\n" +
                "Version=1.0\n" +
                "DefaultEntry=\n" +
                "\n" +
                "[ComSpecs]\n";

        StringBuilder strComSpecs = new StringBuilder();

        int [] portDefaults = new int [3];

        portDefaults[0] = 22;

        portDefaults[1] = 41795;

        portDefaults[2] = 41797;

        for(Device device:devices){

            boolean isSsh = false;

            if(device.getDeviceType().equals("SSH"))
                isSsh = true;

            boolean isDefault = false;

            for(int portDefault:portDefaults){

                if(portDefault == device.getDevicePort())
                    isDefault = true;

            }

            if(isSsh == false){

                if(isDefault == true){

                    strComSpecs.append(device.getDeviceHostname().replaceAll("\\s+","") + "=" + device.getDeviceType().toLowerCase() + " " + device.getDeviceIp() + "\n");

                }

                if(isDefault == false){

                    strComSpecs.append(device.getDeviceHostname().replaceAll("\\s+","") + "=" + device.getDeviceType().toLowerCase() + " " + device.getDeviceIp() + "," + device.getDevicePort() + "\n");

                }


            }else if(isSsh == true){

                if(isDefault == true){

                    strComSpecs.append(device.getDeviceHostname().replaceAll("\\s+","") + "=" + device.getDeviceType().toLowerCase() + " " + device.getDeviceIp() + ";" + "username " + device.getDeviceUsername() + ";" + "password " + device.getDevicePassword() + "\n");

                }

                if(isDefault == false){

                    strComSpecs.append(device.getDeviceHostname().replaceAll("\\s+","") + "=" + device.getDeviceType().toLowerCase() + " " + device.getDeviceIp() + "," + device.getDevicePort() + ";" + "username " + device.getDeviceUsername() + ";" + "password " + device.getDevicePassword() + "\n");

                }

            }

        }

        toXadr = toXadr + strComSpecs.toString();

        toXadr = toXadr + "\n[Notes]\n";

        StringBuilder strNotes = new StringBuilder();

        for(Device device:devices){

            strNotes.append(device.getDeviceHostname().replaceAll("\\s+","") + "=" + "\n");

        }

        toXadr = toXadr + strNotes.toString();

        return toXadr;

    }

    public static String readXadr(InputStream inputStream){

        String ret = "";

        try {

            if ( inputStream != null ) {

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String receiveString = "";

                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {

                    stringBuilder.append(receiveString + "\n");

                }

                inputStream.close();

                ret = stringBuilder.toString();

            }
        }
        catch (FileNotFoundException e) {

            Log.e("login activity", "File not found: " + e.toString());

        } catch (IOException e) {

            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;

    }

    public static List<Device> decode(Context context, String xadrContent){

        List<Device> devicesAvailable = new ArrayList<Device>();

        try{

            Device deviceInstance = new Device();

            if(xadrContent != null && !xadrContent.equals("")){

                String [] arr = xadrContent.split("\r\n|\r|\n");

                int start = 0;

                int end = 0;

                for(int i = 0; i < arr.length; i++){

                    if(arr[i].equals("[ComSpecs]"))
                        start = i + 1;

                    if(arr[i].equals("[Notes]"))
                        end = i - 1;

                }

                if(start != 0 && end != 0){

                    StringBuilder stringBuilder = new StringBuilder();

                    String str;

                    for(int i = start; i < end; i++){

                        stringBuilder.append(arr[i] + "\n");

                    }

                    if(stringBuilder != null){

                        str = stringBuilder.toString();

                        String [] strDevices = str.split("\r\n|\r|\n");

                        int uniqId = deviceInstance.getUniqId(context);

                        for(String strDevice : strDevices){

                            int startHostName = 0;

                            int len = strDevice.length();

                            int endHostName = strDevice.indexOf("=");

                            String hostName = strDevice.substring(startHostName,endHostName);

                            int startProtocol = strDevice.indexOf("=") + 1;

                            strDevice = strDevice.substring(startProtocol, strDevice.length());

                            int endProtocol = strDevice.indexOf(" ");

                            String protocol = strDevice.substring(0, endProtocol).toUpperCase();

                            if(protocol.equals("AUTO"))
                                protocol = "CTP";

                            int startIp = strDevice.indexOf(" ") + 1;

                            strDevice = strDevice.substring(startIp, strDevice.length());

                            boolean isDefault = true;

                            boolean isSsh = false;

                            if(strDevice.contains(","))
                                isDefault = false;

                            if(strDevice.contains(";"))
                                isSsh = true;

                            String ip = null;

                            int port = 0;

                            String username = "";

                            String password = "";

                            if(isSsh == false){

                                if(isDefault == true){

                                    ip = strDevice.substring(0, strDevice.length());

                                    port = Device.getDeviceDefaultPort(protocol.toUpperCase());

                                }

                                if(isDefault == false){

                                    int endIp = strDevice.indexOf(",");

                                    int startPort = strDevice.indexOf(",") + 1;

                                    int endPort = strDevice.length();

                                    ip = strDevice.substring(0, endIp);

                                    port = Integer.parseInt(strDevice.substring(startPort, endPort));

                                }

                            }

                            if(isSsh == true){

                                if(isDefault == true){

                                    int endIp = strDevice.indexOf(";");

                                    ip = strDevice.substring(0, endIp);

                                    port = Device.getDeviceDefaultPort(protocol.toUpperCase());

                                }

                                if(isDefault == false){

                                    int endIp = strDevice.indexOf(",");

                                    int startPort = strDevice.indexOf(",") + 1;

                                    int endPort = strDevice.indexOf(";");

                                    ip = strDevice.substring(0, endIp);

                                    port = Integer.parseInt(strDevice.substring(startPort, endPort));

                                }

                                int startUsername = strDevice.indexOf(";") + 1;

                                int endUsername = strDevice.lastIndexOf(";");

                                String usernameField = strDevice.substring(startUsername, endUsername);

                                String [] usernames = usernameField.split("\\s+");

                                username = usernames[1];

                                int startPassword = strDevice.lastIndexOf(";") + 1;

                                int endPassword = strDevice.length();

                                String passwordField = strDevice.substring(startPassword, endPassword);

                                String [] passwords = passwordField.split("\\s+");

                                if(passwords.length > 1){

                                    password = passwords[1];

                                }else{

                                    password = "";

                                }


                            }

                            Device deviceAvailable = new Device(uniqId, ip, port, protocol, username, password, "",
                                    "", hostName, 0, true, false, 0,
                                    "", false);

                            devicesAvailable.add(deviceAvailable);

                            uniqId = uniqId + 1;

                        }

                    }

                }

            }

        }catch(Exception e){

            Dialog.getUserError(context, "Some of the entries were not compatible and were ignored.");

        }

        return devicesAvailable;

    }

    public static void sendXadr(Xadr xadr, Context context){

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), xadr.getXadrName());

        Uri path = Uri.fromFile(file);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setType("vnd.android.cursor.dir/email");

        String to[] = {""};

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);

        emailIntent.putExtra(Intent.EXTRA_STREAM, path);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Address book " + xadr.getXadrName());

        context.startActivity(Intent.createChooser(emailIntent , "Share address book"));

    }

    public static void exportXadr(Xadr xadr, Context context) throws IOException {

        writeXadr(xadr, context);

        sendXadr(xadr, context);

    }

}
