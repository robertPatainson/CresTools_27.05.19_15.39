package com.lafayeboulhalfa.app.crestools;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HP on 16.04.2019.
 */

public class Browser {

    public static String ipPhone;

    public static String toto;
    public static Timer timer = new Timer();

    public static DatagramSocket sock = null;

    public static DatagramPacket dp = null;

    public static List<String> ipsAvailable = new ArrayList<String>();

    public static List<String> serialNumberAvailable = new ArrayList<String>();

    public static List<String> hostNameAvailable = new ArrayList<String>();

    public Browser(){

        ipPhone = DeviceActivity.ipPhone;

    }

    public void browse(final ListView listView, final View topBar, final View bottomBar, final Context context, final LayoutInflater inflater){

        final Handler handler = new Handler();

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                String search = "14:00:00:00:01:04:00:03:00:00:46:52:2d:4d:41:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00" +
                        ":00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00" +
                        ":00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:" +
                        "00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00" +
                        ":00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00" +
                        ":00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00" +
                        ":00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00";

                String[] stringArray = search.split(":");

                byte[] data = new byte[stringArray.length];

                for(int i = 0; i<data.length;i++)
                {
                    data[i] = Byte.parseByte(stringArray[i],16);

                }

                try {

                    byte[] buffer = new byte[2048];

                    InetAddress addr = InetAddress.getByName("255.255.255.255");

                    InetSocketAddress serv = new InetSocketAddress(41794);

                    sock = new DatagramSocket(null);

                    sock.bind(serv);

                    sock.setSoTimeout(10000);

                    System.out.println("Searching new product...");

                    dp = new DatagramPacket(data, data.length, addr, 41794);

                    TimerTask timertask = new TimerTask() {
                        @Override
                        public void run() {

                            try {
                                sock.send(dp);
                                System.out.println("Message Sent");
                            } catch (IOException e) {
                                System.out.println("Error in Timer : " + e.getMessage());
                            }

                        }
                    };

                    timer = new Timer();

                    timer.schedule(timertask, 0, 2000);

                    DatagramPacket rep = new DatagramPacket(buffer, buffer.length);

                    int i = 0;

                    String[] device = new String[100];

                    String address, common, hostName, serialNumber, productName;

                    String [] arrToParse;

                    while (true) {

                        sock.receive(rep);

                        if (rep.getAddress().toString().indexOf(ipPhone) < 0) {

                            device[i] = rep.getAddress().toString();

                            address = device[i].substring(1);

                            hostName = new String(Arrays.copyOfRange(rep.getData(), 10, indexOf(rep.getData(),(byte)0,11)));

                            common = new String(Arrays.copyOfRange(rep.getData(), 266, indexOf(rep.getData(),(byte)0,267)));

                            hostName = TrimNonAscii(hostName);

                            common = TrimNonAscii(common);

                            arrToParse = common.split("\\s+");

                            serialNumber = arrToParse[0];

                            productName = serialNumber;

                            boolean isAlreadyDevice = false;

                            for(String ip : ipsAvailable){

                                if(ip.equals(address))
                                    isAlreadyDevice = true;

                            }

                            if(!isAlreadyDevice){

                                System.out.println("Address : " + address + " - Hostname : " + hostName + " - Serial Number : " + serialNumber + " - Product Name : " + productName);

                                ipsAvailable.add(address);

                                hostNameAvailable.add(hostName);

                                serialNumberAvailable.add(serialNumber);

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        syncListView(listView, topBar, bottomBar, context, inflater);

                                    }
                                });

                            }

                            i++;

                        }

                    }

                } catch (Exception er) {

                    System.out.println("Error while sending data : " + er);

                } finally {

                    System.out.println("Browsing finished.");

                    ipsAvailable = new ArrayList<String>();

                    timer.cancel();

                    sock.close();

                }

            }
        });

        thread.start();

    }

    public void syncListView(ListView listView, View topBar, View bottomBar, Context context, LayoutInflater inflater){

        listView.setVisibility(View.VISIBLE);

        topBar.setVisibility(View.VISIBLE);

        bottomBar.setVisibility(View.VISIBLE);

        String[] ips = ipsAvailable.toArray(new String[ipsAvailable.size()]);

        String[] serials = serialNumberAvailable.toArray(new String[serialNumberAvailable.size()]);

        String[] hostNames = hostNameAvailable.toArray(new String[hostNameAvailable.size()]);

        BrowserActivity.AdapterManager adapterManager = new BrowserActivity.AdapterManager(context, context, ips, serials, hostNames);

        inflater = adapterManager.getInflater();

        listView.setAdapter(adapterManager);

    }

    static String TrimNonAscii(String value){

        Pattern rules = Pattern.compile("[^ -~]");
        Matcher m = rules.matcher(value);

        value = m.replaceAll("");

        return value;

    }

    private static int indexOf(byte[] array, byte target, int start) {
        for (int i = start; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int findIndex(byte[] arr, int t){

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }

}
