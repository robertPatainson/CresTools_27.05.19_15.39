package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by HP on 19.03.2019.
 */

public class Report implements Serializable {


    public static String reportsAvailablePath = "reportsAvailable";

    private String TAG = getClass().getName();

    public Report(){}

    private int idReport;
    private int idProject;
    private String nameReport;
    private String[] commandsReport;

    static Channel channel;
    static InputStream in;
    static OutputStream out;

    static float count = 0;


    public Report(int idReport, int idProject, String nameReport, String[] commandsReport){

        this.idReport = idReport;
        this.idProject = idProject;
        this.nameReport = nameReport;
        this.commandsReport = commandsReport;

    }

    /**
     *
     * GETTERS
     *
     */
    public int getIdReport(){

        return this.idReport;

    }

    public int getIdProject(){

        return this.idProject;

    }

    public String getNameReport(){

        return this.nameReport;

    }

    public String[] getCommandsReport(){

        return this.commandsReport;

    }

    /**
     *
     * SETTERS
     *
     */
    public int setIdReport(){

        //using ts as uniqueId

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        int idReport = Integer.parseInt(ts);

        this.idReport = idReport;

        return idReport;

    }

    public void setIdProject(){

        this.idProject = idProject;

    }

    public void setNameReport(String nameReport){

        this.nameReport = nameReport;

    }

    public void setCommandsReport(String[] commandsReport){

        this.commandsReport = commandsReport;

    }

    /**
     *
     * STATIC MEMBERS
     *
     */

    private static Report report1 = new Report(1,0,"DM 8x8 - 16x16 - 32x32", new String[]{"HOSTNAME","VER-V","NVRAMREBOOT SHOW",
            "UPTIME","ESTAT /v","ETHWDOG","WHO","TASKSTAT","TOP","IPTABLE","ERRLOG","HEAPFREE","rstpstatus",
            "rstpstatus 0","rstpconfig debug","rstpstatus debug","reportdm","DUMPDMIPCONFIG","eeprom",
            "privatenetconfig","vlanslotinfo"});
    private static Report report2 = new Report(2,0,"TSW-TST-TSS", new String[]{"INFO","HOSTNAME","SHOWHW","CARDS","SHOWLICENSE",
            "OSD","VER-V","CORE3Uilevel","CORE3Version","UPTIME","IPCONFIG /ALL","ETHWDOG","SYSMSG","WHO",
            "TASKSTAT","IPTABLE","AUTODISCOVERY","CPULOAD","RAMFREE","TEMPerature",
            "8021XAU","MEDIASTREAMINFO","sipinfo","NVRAMREBOOT","ERR PLOGALL","NVRAMREBOOT"});
    private static Report report3 = new Report(3,0,"SWAMP",new String[]{"VER","cards -force","info","iptable","cnetid","estatus",
            "timedate","uptime","showhw","WHO","CIPPORT","CTPPORT","EE","ETHW","HEAP","RAMF","ERR"});
    private static Report report4 = new Report(4,0,"EX gateway",new String[]{"Ver -v","UPTIME","SHOWHw","rep","reportgwtable route",
            "reportgwtable neighbor","reportgwtable counters","REPORTFWALL","EST -v","PRTTSIDB","RPRTTSIDT",
            "RFCH","RFCHM","RFDE","RFNETL","RFPO","RFROAMING","err"});
    private static Report report5 = new Report(5,0,"DVPHD",new String[]{"Ver -V","INFO","SHOWHW","EST","FREE","RAMF","TASKSTAT",
            "WHO","VER","ERR","UPTIME","PROJINFO","DHCP","IPT","VIDEOINFO","SHOWREBOOT","USBDRIVERS","RS232"});
    private static Report report6 = new Report(6,0,"DMPS",new String[]{"INFO","SHOWHW","HOSTNAME","VER -V","UPTIME","ESTAT /v",
            "ETHWDOG","WHO","TASKSTAT","TOP","ERRLOG","HEAPFREE","rstpstatus","rstpstatus 0","rstpconfig debug",
            "rstpstatus debug","reportdm","reportdm","hdbasetconfig","DUMPDMIPCONFIG","eeprom","privatenetconfig",
            "DUMPDMSTREAMINFO","DUMPDMCARDINFO"});
    private static Report report7 = new Report(7,0,"DM 64x64 - 128x128",new String[]{"HOSTNAME","SHOWHW","OSD","VER -V","UPTIME",
            "TIMEZ","SNTP","IPCONFIG /ALL","ETHWDOG","WHO","TASKSTAT","REPORTDM","IPTABLE","DMPING","SHOWPORTMAP",
            "RSTPSTATUS","RSTPSTATUS -V","RAPMASTER STATUS","AUTODISCOVERY QUERY TABLEFORMAT","LISTENSTAT",
            "CPULOAD","RAMFREE","FAN","ERR PLOGALL","RCONPING"});
    private static Report report8 = new Report(8,0,"MD 64x128",new String[]{"HOSTNAME","SHOWHW","OSD","VER -V","UPTIME","TIMEZ",
            "SNTP","IPCONFIG /ALL","ETHWDOG","WHO","TASKSTAT","REPORTDM","IPTABLE","DMPING","RCONPING",
            "SHOWPORTMAP","RSTPSTATUS","RSTPSTATUS -V","RAPMASTERSTATUS","AUTODISCOVERY QUERY TABLE FORMAT",
            "LISTENSTAT","CPULOAD","CPULOAD","CPULOAD","RAMFREE","FAN","ERR PLOGALL"});
    private static Report report9 = new Report(9,0,"DGE",new String[]{"Ver -V","INFO","SHOWHW","PACKS","EST","UPTI","SIPINFO",
            "AUTOSELECTOUTP","CORE3VERSION","CTPP","FASTLOCKVID","FIREWALL","FLASHCONNECTTIME","FLASHVERSION",
            "FREE","RAMF","TASKSTAT","WHO"});
    private static Report report10 = new Report(10,0,"2 Series",new String[]{"HOSTNAME","VER -V","NVRAMREBOOT SHOW","UPTIME",
            "ESTAT /V","ETHWDOG","WHO","TASKSTAT","TOP","IPTABLE","PROGCOMMENTS","SPLUSTASKS","ERRLOG",
            "HEAPFREE","NVRAMREBOOT","SHOWEXTRA","ROUTESYMSTAT"});
    private static Report report11 = new Report(11,0,"3 Series",new String[]{"HOSTNAME","MYCRESTRON","SHOWHW","SHOWLICENSE","OSD",
            "VER -V","VER -ALL","UPTIME","TIMEZ","SNTP","APPSTAT","PROGREGISTER","IPCONFIG /ALL","ETHWDOG","WHO",
            "TASKSTAT","REPORTCRESNET","IPTABLE -P:ALL -T","PROGCOMMENTS:1","PROGCOMMENTS:2","PROGCOMMENTS:3",
            "PROGCOMMENTS:4","PROGCOMMENTS:5","PROGCOMMENTS:6","PROGCOMMENTS:7","PROGCOMMENTS:8","PROGCOMMENTS:9",
            "PROGCOMMENTS:10","SPLUSTASKS:1","SPLUSTASKS:2","SPLUSTASKS:3","SPLUSTASKS:4","SPLUSTASKS:5","SPLUSTASKS:6",
            "SPLUSTASKS:7","SPLUSTASKS:8","SPLUSTASKS:9","SPLUSTASKS:10","AUTODISCOVERY QUERY TABLEFORMAT",
            "LISTACTIVEMODULES","THREADPOOLINFO","LISTENSTAT","ROUTERSTATUS","ROUTERSENDCMG GET VERSION","CPULOAD",
            "RAMFREE","ROUTESYMSTAT","DHCPLEASES","USERPATLIST","SHOWPORTMAP -ALL","SHOWDISKINFO","ERR PLOGALL"});


    static final List<Report> reports = new ArrayList<Report>() {

        {

            add(report1);
            add(report2);
            add(report3);
            add(report4);
            add(report5);
            add(report6);
            add(report7);
            add(report8);
            add(report9);
            add(report10);
            add(report11);

        }

    };

    public String toString(){

        return "idReport = " + this.getIdReport() + " - idProject = " + this.getIdProject() + " - nameReport = " + this.getNameReport() + " commandsReport = "  + this.getCommandsReport();

    }

    public static boolean isEmptyList(Context context){

        List<Report> reportsAvailable = new ArrayList<Report>();

        reportsAvailable = SerializableManager.readSerializable(context, "reportsAvailable");

        if(reportsAvailable != null && !reportsAvailable.isEmpty()){
            return false;
        }else{
            return true;
        }

    }

    public static Report getReportByName(Context context, int idProject, String nameReport){

        Report report = null;

        List<Report> reportsAvailable = new ArrayList<Report>();

        reportsAvailable = SerializableManager.readSerializable(context, "reportsAvailable");

        for(Report reportAvailable:reportsAvailable){

            if(reportAvailable.getIdProject() == idProject){

                if(reportAvailable.getNameReport().equals(nameReport)){

                    report = reportAvailable;

                }

            }

        }

        return report;

    }

    public static void execPrefillInformationOnDefault(final Button buttonPrefill, final Device newDevice, final Report report, final EditText editTextSerialNumber, final EditText editTextProductName, final EditText editTextHostname, final TextView textViewSubtitle, final ImageButton imageButtonPrevious){

        final Handler handler = new Handler();

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            buttonPrefill.setEnabled(false);

                            buttonPrefill.setBackgroundResource(R.drawable.button_command_disabled);

                        }
                    });

                    StringBuilder stringBuilder = new StringBuilder();

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            textViewSubtitle.setText(Html.fromHtml("Executing"));

                        }
                    });

                    try{

                        Socket socket = null;

                        switch(newDevice.getDeviceType()){

                            case "CTP":

                                socket = new Socket(newDevice.getDeviceIp(), newDevice.getDevicePort());

                                break;

                            case "SSL":

                                TrustManager[] trustAllCerts = { new X509TrustManager()
                                {

                                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                        return null;
                                    }

                                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}

                                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}

                                }
                                };

                                SSLContext sslContext = SSLContext.getInstance("SSL");

                                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                                SocketFactory socketFactory = sslContext.getSocketFactory();

                                socket = socketFactory.createSocket(newDevice.getDeviceIp(), newDevice.getDevicePort());

                                break;

                        }

                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                        socket.setSoTimeout(2000);

                        if(socket.isConnected()){

                            System.out.print("Connected to socket ..." + "\n");

                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    textViewSubtitle.setText(Html.fromHtml("Connected"));

                                }
                            });

                            System.out.print("Executing : " + report.getNameReport() + "\n");

                            int length = report.getCommandsReport().length;

                            String [] commands = report.getCommandsReport();

                            StringBuilder buffer = new StringBuilder();

                            int x = 0;

                            out.writeBytes("echo off\r");

                            try {

                                while ((x = in.read()) > 0) {

                                    buffer.append((char) x);

                                }

                            }catch(Exception e){

                                System.out.println("Read timeout "+ e.getMessage());

                            }

                            buffer.delete(0,buffer.length());

                            System.out.println("Ready to launch prefill ...");

                            socket.setSoTimeout(0);

                            int count = 0;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    textViewSubtitle.setText(Html.fromHtml("Receiving data"));

                                }
                            });

                            while(socket.isConnected()){

                                for(int i = 0; i < length; i++){

                                    out.writeBytes(commands[i] + "\r");

                                    x = in.read();

                                    count = count + 1;

                                    do{

                                        buffer.append((char)x);

                                        x = in.read();

                                        count = count + 1;

                                    }while(x!=62);

                                    buffer.append((char)x);

                                    int last = buffer.lastIndexOf("\n");

                                    if (last >= buffer.length() - "\n".length()) { buffer.delete(last, buffer.length()); }

                                    System.out.println(commands[i]);

                                    System.out.println(buffer.toString());

                                    stringBuilder.append(buffer.toString() + "\n");

                                    buffer.delete(0,buffer.length());

                                }

                                break;

                            }

                            System.out.println("End of prefill. Received "+ count / 1024 + "Kb");

                            final String result = stringBuilder.toString();

                            final String [] lines = result.split("\n");

                            String serialNumber = "";

                            String productName = "";

                            String hostName = "";

                            for(String line : lines){

                                if(line.contains("[") && line.contains("]")){

                                    String content = line.split("[\\[\\]]")[1];

                                    if(content != null) {

                                        serialNumber = content.substring(content.indexOf('#'), content.length());

                                        serialNumber = serialNumber.substring(1, serialNumber.length());

                                        serialNumber = serialNumber.replaceAll("\\s+","");

                                    }

                                }else if(line.contains(">")){

                                    String content = line;

                                    if(content != null){

                                        productName = content.substring(0, content.indexOf('>'));

                                        productName = productName.replaceAll("\\s+","");

                                    }

                                }else if(line.contains("Host Name:")) {

                                    String content = line;

                                    if (content != null) {

                                        String _result = content.substring(content.indexOf(':'), content.length());

                                        hostName = _result.substring(1);

                                        hostName = hostName.replaceAll("\\s+","");

                                    }

                                }

                            }

                            final String finalSerialNumber = serialNumber;

                            final String finalProductName = productName;

                            final String finalHostName = hostName;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    if(finalSerialNumber.equals("") && finalProductName.equals("") && finalHostName.equals("")){

                                        textViewSubtitle.setText(Html.fromHtml("<b>Prefill failed</b>"));

                                    }

                                    textViewSubtitle.setText(Html.fromHtml("Prefill finished"));

                                    editTextSerialNumber.setText(finalSerialNumber);

                                    editTextProductName.setText(finalProductName);

                                    editTextHostname.setText(finalHostName);

                                    buttonPrefill.setBackgroundResource(R.drawable.button_success);

                                    buttonPrefill.setEnabled(true);

                                }
                            });

                        }

                    }catch(IOException ex){

                        handler.post(new Runnable() {

                            @Override
                            public void run() {

                                textViewSubtitle.setText(Html.fromHtml("<b>Prefill failed</b>"));

                                buttonPrefill.setBackgroundResource(R.drawable.button_success);

                                buttonPrefill.setEnabled(true);

                            }
                        });

                        Log.e("Report.java", "***ERROR : " + ex.toString());

                    }


                }catch(Exception ex){

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            textViewSubtitle.setText(Html.fromHtml("<b>Prefill failed</b>"));

                            buttonPrefill.setBackgroundResource(R.drawable.button_success);

                            buttonPrefill.setEnabled(true);

                        }
                    });

                    Log.e("Report.java", "***ERROR : " + ex.toString());

                }

            }
        });

        thread.start();

    }

    public static void execPrefillInformationOnSsh(final Button buttonPrefill, final Device newDevice, final Report report, final EditText editTextSerialNumber, final EditText editTextProductName, final EditText editTextHostname, final TextView textViewSubtitle, final ImageButton imageButtonPrevious){

        final Handler handler = new Handler();

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            buttonPrefill.setEnabled(false);

                            buttonPrefill.setBackgroundResource(R.drawable.button_command_disabled);

                        }
                    });

                    StringBuilder stringBuilder = new StringBuilder();

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            textViewSubtitle.setText(Html.fromHtml("Executing"));

                        }
                    });

                    try{

                        JSch jsch = new JSch();

                        Session session = jsch.getSession(newDevice.getDeviceUsername(), newDevice.getDeviceIp(), newDevice.getDevicePort());

                        session.setPassword(newDevice.getDevicePassword());

                        session.setConfig("StrictHostKeyChecking", "no");

                        session.setServerAliveInterval(2000);

                        session.setTimeout(2000);

                        session.connect(3000);

                        if(session.isConnected()){

                            channel = session.openChannel("shell");

                            in = channel.getInputStream();

                            out = channel.getOutputStream();

                            channel.connect(3000);

                            System.out.print("Connected to socket ..." + "\n");

                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    textViewSubtitle.setText(Html.fromHtml("Connected"));

                                }
                            });

                            System.out.print("Executing : " + report.getNameReport() + "\n");

                            int length = report.getCommandsReport().length;

                            String [] commands = report.getCommandsReport();

                            StringBuilder buffer = new StringBuilder();

                            int x = 0;

                            out.write("echo off\r".getBytes());

                            out.flush();

                            ExecutorService executor = Executors.newCachedThreadPool();

                            Callable<Object> task = new Callable<Object>() {
                                public Object call() {

                                    try {

                                        int y;

                                        StringBuilder buffer = new StringBuilder();

                                        while ( (y = in.read())>0 ) {

                                            buffer.append((char) y);

                                            System.out.println("PURGING PROMPT : " + buffer);

                                        }

                                    }catch(Exception e){

                                        System.out.println("Read timeout "+ e.getMessage());

                                    }

                                    return null;
                                }
                            };

                            Future<Object> future = executor.submit(task);

                            try {
                                Object result = future.get(3, TimeUnit.SECONDS);
                            } catch (TimeoutException ex) {
                                // handle the timeout
                            } catch (InterruptedException e) {
                                // handle the interrupts
                            } catch (ExecutionException e) {
                                // handle other exceptions
                            } finally {
                                future.cancel(true); // may or may not desire this
                            }

                            buffer.delete(0,buffer.length());

                            System.out.println("Ready to launch prefill ...");

                            session.setTimeout(0);

                            int count = 0;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    textViewSubtitle.setText(Html.fromHtml("Receiving data"));

                                }
                            });

                            while(session.isConnected()){

                                for(int i = 0; i < length; i++){

                                    out.write((commands[i] + "\r").getBytes());

                                    out.flush();

                                    x = in.read();

                                    count = count + 1;

                                    do{

                                        buffer.append((char)x);

                                        x = in.read();

                                        count = count + 1;

                                    }while(x!=62);

                                    buffer.append((char)x);

                                    int last = buffer.lastIndexOf("\n");

                                    if (last >= buffer.length() - "\n".length()) { buffer.delete(last, buffer.length()); }

                                    System.out.println(commands[i]);

                                    System.out.println(buffer.toString());

                                    stringBuilder.append(buffer.toString() + "\n");

                                    buffer.delete(0,buffer.length());

                                }

                                break;

                            }

                            System.out.println("End of prefill. Received "+ count / 1024 + "Kb");

                            final String result = stringBuilder.toString();

                            final String [] lines = result.split("\n");

                            String serialNumber = "";

                            String productName = "";

                            String hostName = "";

                            for(String line : lines){

                                if(line.contains("[") && line.contains("]")){

                                    String content = line.split("[\\[\\]]")[1];

                                    if(content != null) {

                                        serialNumber = content.substring(content.indexOf('#'), content.length());

                                        serialNumber = serialNumber.substring(1, serialNumber.length());

                                        serialNumber = serialNumber.replaceAll("\\s+","");

                                    }

                                }else if(line.contains(">")){

                                    String content = line;

                                    if(content != null){

                                        productName = content.substring(0, content.indexOf('>'));

                                        productName = productName.replaceAll("\\s+","");

                                    }

                                }else if(line.contains("Host Name:")) {

                                    String content = line;

                                    if (content != null) {

                                        String _result = content.substring(content.indexOf(':'), content.length());

                                        hostName = _result.substring(1);

                                        hostName = hostName.replaceAll("\\s+","");

                                    }

                                }

                            }

                            final String finalSerialNumber = serialNumber;

                            final String finalProductName = productName;

                            final String finalHostName = hostName;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    if(finalSerialNumber.equals("") && finalProductName.equals("") && finalHostName.equals("")){

                                        textViewSubtitle.setText(Html.fromHtml("<b>Prefill failed</b>"));

                                    }

                                    textViewSubtitle.setText(Html.fromHtml("Prefill finished"));

                                    editTextSerialNumber.setText(finalSerialNumber);

                                    editTextProductName.setText(finalProductName);

                                    editTextHostname.setText(finalHostName);

                                    buttonPrefill.setEnabled(true);

                                    buttonPrefill.setBackgroundResource(R.drawable.button_success);


                                }
                            });

                        }

                    }catch(IOException ex){

                        handler.post(new Runnable() {

                            @Override
                            public void run() {

                                textViewSubtitle.setText(Html.fromHtml("<b>Prefill failed</b>"));

                                buttonPrefill.setEnabled(true);

                                buttonPrefill.setBackgroundResource(R.drawable.button_success);

                            }
                        });

                        Log.e("Report.java", "***ERROR : " + ex.toString());

                    }


                }catch(Exception ex){

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            textViewSubtitle.setText(Html.fromHtml("<b>Prefill failed</b>"));

                            buttonPrefill.setEnabled(true);

                            buttonPrefill.setBackgroundResource(R.drawable.button_success);

                        }
                    });

                    Log.e("Report.java", "***ERROR : " + ex.toString());

                }

            }
        });

        thread.start();

    }

    public static void execReportDefault(final Device device, final Report report, final TextView textViewMainLegend, final TextView textViewSubLegend, final RelativeLayout relativeLayoutHeader, final TextView textViewHeader, final TextView textViewDate, final TextView textViewResult, final Button buttonAction, final ImageButton buttonDone, final Context context) throws FileNotFoundException, UnsupportedEncodingException {

        final Handler handler = new Handler();

        final Thread thread = new Thread(new Runnable() {

            public volatile boolean interrupt = false;

            @Override
            public void run() {

                try {

                    StringBuilder stringBuilder = new StringBuilder();

                    textViewHeader.setVisibility(View.INVISIBLE);

                    textViewResult.setVisibility(View.INVISIBLE);

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            buttonAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    interrupt = true;

                                }
                            });

                            textViewMainLegend.setText(Html.fromHtml("<b>Connecting ...</b>"));

                        }
                    });

                    try{

                        Socket socket = null;

                        switch(device.getDeviceType()){

                            case "CTP":

                                socket = new Socket(device.getDeviceIp(), device.getDevicePort());

                                break;

                            case "SSL":

                                TrustManager[] trustAllCerts = { new X509TrustManager()
                                {

                                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                        return null;
                                    }

                                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}

                                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}

                                }
                                };

                                SSLContext sslContext = SSLContext.getInstance("SSL");

                                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                                SocketFactory socketFactory = sslContext.getSocketFactory();

                                socket = socketFactory.createSocket(device.getDeviceIp(), device.getDevicePort());

                                break;

                        }

                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                        socket.setSoTimeout(2000);

                        if(socket.isConnected()){

                            System.out.print("Connected to socket ..." + "\n");

                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    textViewMainLegend.setText(Html.fromHtml("<b>Sending commands ...</b>"));

                                }
                            });

                            System.out.print("Executing : " + report.getNameReport() + "\n");

                            int length = report.getCommandsReport().length;

                            String [] commands = report.getCommandsReport();

                            StringBuilder buffer = new StringBuilder();

                            int x = 0;

                            out.writeBytes("echo off\r");

                            try {

                                while ((x = in.read()) > 0) {

                                    buffer.append((char) x);

                                }

                            }catch(Exception e){

                                System.out.println("Read timeout "+ e.getMessage());

                            }

                            buffer.delete(0,buffer.length());

                            Pattern tag = Pattern.compile( "\\w(?=>)");
                            Matcher match ;

                            System.out.println("Ready to launch report ...");

                            socket.setSoTimeout(0);

                            int last=0;

                            count =0;

                            String s;
                            char[] bytes = new char[8192];

                            while(socket.isConnected()){

                                for(int i = 0; i < length; i++){

                                    if(interrupt){

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {

                                                textViewMainLegend.setText("Report canceled !");

                                            }
                                        });

                                        break;

                                    }

                                    out.writeBytes(commands[i] + "\r");

                                    x = in.read(bytes);

                                    s = new String(bytes,0, x);

                                    buffer.append(s);

                                    count = count + x;

                                    match = tag.matcher(buffer.toString());

                                    if(!match.find()) {

                                        do {

                                            Counter(count,textViewSubLegend);

                                            x = in.read(bytes);

                                            s = new String(bytes, 0, x);

                                            buffer.append(s);

                                            count = count + x;

                                            match = tag.matcher(buffer.toString());

                                        } while (!match.find());

                                    }

                                    last = buffer.lastIndexOf("\n");

                                    if (last >= 0) { buffer.delete(last, buffer.length()); }

                                    System.out.println(commands[i]);

                                    System.out.println(buffer.toString());

                                    stringBuilder.append("<b>" + commands[i].toUpperCase() + "</b>" + "<br>" + buffer.toString() + "<br><br>");

                                    buffer.delete(0,buffer.length());

                                }

                                break;

                            }

                            System.out.println("End of report. Received "+ count / 1024 + "Kb");

                            String result = stringBuilder.toString();

                            result = result.replace("\r\n", "<br>");

                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                            Date date = new Date();

                            final String stringDate = dateFormat.format(date);

                            final String header = "<b>" + device.getDeviceProductName() + "</b><br>" + "<small><font color=#808080>" + device.getDeviceIp() + "</font></small>";

                            final Project project = Project.getProjectById(context, device.getDeviceProjectId());

                            final String htmlHeader = "<p align=\"justify\" style=\"font-size: 20px; font-family: Roboto-Regular;\">" + "Date : <b>" + stringDate + "</b><br>Project : <b>" + project.getProjectName() + "</b><br>Product : <b>" + device.getDeviceProductName() + "</b><br>Hostname : <b>" + device.getDeviceHostname() + "</b><br>Report : <b>" + report.getNameReport() + "</b></p>";

                            final String strHeader = "Date : " + stringDate + "\nProject : " + project.getProjectName() + "\nProduct : " + device.getDeviceProductName() + "\nHostname : " + device.getDeviceHostname() + "\nReport : " + report.getNameReport();

                            final String htmlFeatures = "<p align=\"justify\" style=\"font-size: 14px; font-family: Roboto-Regular;\"><font color=#808080>Powered by CresTools&copy;</font><br></p>";

                            final String htmlContent = "<p align=\"justify\" style=\"font-size: 16px; font-family: Roboto-Regular;\">" + result + "</p> ";

                            final String htmlFormat = "<html><style type='text/css'>@font-face { font-family: Roboto-Regular; src: url('fonts/Roboto-Regular.ttf'); } body p {font-family: Roboto-Regular;}</style>"
                                    + "<body >" + htmlHeader + htmlFeatures + "<hr>" + htmlContent + "</body></html>";

                            final String finalResult = result;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    buttonDone.setVisibility(View.VISIBLE);

                                    buttonDone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            ReportActivity.staticReportActivity.finish();

                                            ReportExecuteStep1Activity.staticReportExecuteStep1Activity.finish();

                                            ReportExecuteStep2Activity.staticReportExecuteStep2Activity.finish();

                                            ((Activity) context).finish();

                                        }
                                    });

                                    textViewMainLegend.setVisibility(View.INVISIBLE);

                                    textViewSubLegend.setVisibility(View.INVISIBLE);

                                    relativeLayoutHeader.setVisibility(View.VISIBLE);

                                    textViewHeader.setVisibility(View.VISIBLE);

                                    textViewDate.setVisibility(View.VISIBLE);

                                    textViewResult.setVisibility(View.VISIBLE);

                                    textViewHeader.setText(Html.fromHtml(header));

                                    textViewDate.setText(stringDate);

                                    textViewResult.append(Html.fromHtml(finalResult));

                                    buttonAction.setText("Share");

                                    buttonAction.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            sendReport(device, report, strHeader, htmlFormat, context);

                                        }
                                    });

                                }
                            });

                        }

                    }catch(IOException ex){

                        handler.post(new Runnable() {

                            @Override
                            public void run() {

                                textViewMainLegend.setVisibility(View.VISIBLE);

                                textViewSubLegend.setVisibility(View.INVISIBLE);

                                textViewSubLegend.setVisibility(View.INVISIBLE);

                                relativeLayoutHeader.setVisibility(View.INVISIBLE);

                                textViewHeader.setVisibility(View.INVISIBLE);

                                textViewResult.setVisibility(View.INVISIBLE);

                                textViewMainLegend.setText(Html.fromHtml("<font color=#B22222>Report execution failed !</font>"));

                                buttonDone.setVisibility(View.VISIBLE);

                                buttonDone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        ReportActivity.staticReportActivity.finish();

                                        ReportExecuteStep1Activity.staticReportExecuteStep1Activity.finish();

                                        ReportExecuteStep2Activity.staticReportExecuteStep2Activity.finish();

                                        ((Activity) context).finish();

                                    }
                                });

                            }
                        });

                        Log.e("Report.java", "***ERROR : " + ex.toString());

                    }


                }catch(Exception ex){

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            textViewMainLegend.setVisibility(View.VISIBLE);

                            textViewSubLegend.setVisibility(View.INVISIBLE);

                            textViewSubLegend.setVisibility(View.INVISIBLE);

                            relativeLayoutHeader.setVisibility(View.INVISIBLE);

                            textViewHeader.setVisibility(View.INVISIBLE);

                            textViewResult.setVisibility(View.INVISIBLE);

                            textViewMainLegend.setText(Html.fromHtml("<font color=#B22222>Report execution failed !</font>"));

                            buttonDone.setVisibility(View.VISIBLE);

                            buttonDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    ReportActivity.staticReportActivity.finish();

                                    ReportExecuteStep1Activity.staticReportExecuteStep1Activity.finish();

                                    ReportExecuteStep2Activity.staticReportExecuteStep2Activity.finish();

                                    ((Activity) context).finish();

                                }
                            });

                        }
                    });

                    Log.e("Report.java", "***ERROR : " + ex.toString());

                }

            }
        });

        thread.start();

    }

    public static void execReportSsh(final Device device, final Report report, final TextView textViewMainLegend, final TextView textViewSubLegend, final RelativeLayout relativeLayoutHeader, final TextView textViewHeader, final TextView textViewDate, final TextView textViewResult, final Button buttonAction, final ImageButton buttonDone, final Context context) throws FileNotFoundException, UnsupportedEncodingException {

        final Handler handler = new Handler();


        final Thread thread = new Thread(new Runnable() {

            public volatile boolean interrupt = false;

            @Override
            public void run() {

                try {

                    StringBuilder stringBuilder = new StringBuilder();

                    textViewHeader.setVisibility(View.INVISIBLE);

                    textViewResult.setVisibility(View.INVISIBLE);

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            buttonAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    interrupt = true;

                                }
                            });

                            textViewMainLegend.setText(Html.fromHtml("<b>Connecting ...</b>"));

                        }
                    });

                    try{

                        JSch jsch = new JSch();

                        Session session = jsch.getSession(device.getDeviceUsername(), device.getDeviceIp(), device.getDevicePort());

                        session.setPassword(device.getDevicePassword());

                        session.setConfig("StrictHostKeyChecking", "no");

                        session.setServerAliveInterval(2000);

                        session.setTimeout(2000);

                        session.connect(3000);

                        if(session.isConnected()){

                            channel = session.openChannel("shell");

                            in = channel.getInputStream();

                            out = channel.getOutputStream();

                            channel.connect(3000);

                            System.out.print("Connected to socket ..." + "\n");

                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    textViewMainLegend.setText(Html.fromHtml("<b>Sending commands ...</b>"));

                                }
                            });

                            System.out.print("Executing : " + report.getNameReport() + "\n");

                            int length = report.getCommandsReport().length;

                            String [] commands = report.getCommandsReport();

                            final StringBuilder buffer = new StringBuilder();

                            int x = 0;

                            out.write("echo off\r".getBytes());

                            out.flush();

                            ExecutorService executor = Executors.newCachedThreadPool();

                            Callable<Object> task = new Callable<Object>() {
                                public Object call() {

                                    try {

                                        int y;

                                        StringBuilder buffer = new StringBuilder();

                                        while ( (y = in.read())>0 ) {

                                            buffer.append((char) y);

                                            System.out.println("PURGING PROMPT : " + buffer);

                                        }

                                    }catch(Exception e){

                                        System.out.println("Read timeout "+ e.getMessage());

                                    }

                                    return null;
                                }
                            };

                            Future<Object> future = executor.submit(task);

                            try {
                                Object result = future.get(3, TimeUnit.SECONDS);
                            } catch (TimeoutException ex) {
                                // handle the timeout
                            } catch (InterruptedException e) {
                                // handle the interrupts
                            } catch (ExecutionException e) {
                                // handle other exceptions
                            } finally {
                                future.cancel(true); // may or may not desire this
                            }

                            buffer.delete(0,buffer.length());

                            Pattern tag = Pattern.compile( "\\w(?=>)");

                            Matcher match ;

                            System.out.println("Ready to launch report ...");

                            session.setTimeout(0);

                            int last =0;

                            String s;

                            byte[] bytes = new byte[8192];

                            count=0;

                            while(session.isConnected()){

                                for(int i = 0; i < length; i++){

                                    if(interrupt){

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {

                                                textViewMainLegend.setText("Report canceled !");

                                            }
                                        });

                                        break;

                                    }

                                    out.write((commands[i] + "\r").getBytes());

                                    out.flush();

                                    x = in.read(bytes);

                                    s = new String(bytes,0, x);

                                    buffer.append(s);

                                    count = count + x;

                                    match = tag.matcher(buffer.toString());

                                    if(!match.find()) {

                                        do {

                                            Counter(count,textViewSubLegend);

                                            x = in.read(bytes);

                                            s = new String(bytes, 0, x);

                                            buffer.append(s);

                                            count = count + x;

                                            match = tag.matcher(buffer.toString());

                                        } while (!match.find());
                                    }

                                    last = buffer.lastIndexOf("\n");

                                    if (last >= 0) { buffer.delete(last, buffer.length()); }

                                    System.out.println(commands[i]);

                                    System.out.println(buffer.toString());

                                    stringBuilder.append("<b>" + commands[i].toUpperCase() + "</b><br>" + buffer.toString() + "<br><br>");

                                    buffer.delete(0,buffer.length());

                                }

                                System.out.println("End of report. Received "+ count / 1024 + "Kb");

                                String result = stringBuilder.toString();

                                result = result.replace("\r\n", "<br>");

                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                                Date date = new Date();

                                final String stringDate = dateFormat.format(date);

                                final String header = "<b>" + device.getDeviceProductName() + "</b><br>" + "<small><font color=#808080>" + device.getDeviceIp() + "</font></small>";

                                final Project project = Project.getProjectById(context, device.getDeviceProjectId());

                                final String htmlHeader = "<p align=\"justify\" style=\"font-size: 20px; font-family: Roboto-Regular;\">" + "Date : <b>" + stringDate + "</b><br>Project : <b>" + project.getProjectName() + "</b><br>Product : <b>" + device.getDeviceProductName() + "</b><br>Hostname : <b>" + device.getDeviceHostname() + "</b><br>Report : <b>" + report.getNameReport() + "</b></p>";

                                final String strHeader = "Date : " + stringDate + "\nProject : " + project.getProjectName() + "\nProduct : " + device.getDeviceProductName() + "\nHostname : " + device.getDeviceHostname() + "\nReport : " + report.getNameReport();

                                final String htmlFeatures = "<p align=\"justify\" style=\"font-size: 14px; font-family: Roboto-Regular;\"><font color=#808080>Powered by CresTools&copy;</font><br></p>";

                                final String htmlContent = "<p align=\"justify\" style=\"font-size: 16px; font-family: Roboto-Regular;\">" + result + "</p> ";

                                final String htmlFormat = "<html><style type='text/css'>@font-face { font-family: Roboto-Regular; src: url('fonts/Roboto-Regular.ttf'); } body p {font-family: Roboto-Regular;}</style>"
                                        + "<body >" + htmlHeader + htmlFeatures + "<hr>" + htmlContent + "</body></html>";

                                final String finalResult = result;

                                handler.post(new Runnable() {

                                    @Override
                                    public void run() {

                                        buttonDone.setVisibility(View.VISIBLE);

                                        buttonDone.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                ReportActivity.staticReportActivity.finish();

                                                ReportExecuteStep1Activity.staticReportExecuteStep1Activity.finish();

                                                ReportExecuteStep2Activity.staticReportExecuteStep2Activity.finish();

                                                ((Activity) context).finish();

                                            }
                                        });

                                        textViewMainLegend.setVisibility(View.INVISIBLE);

                                        textViewSubLegend.setVisibility(View.INVISIBLE);

                                        relativeLayoutHeader.setVisibility(View.VISIBLE);

                                        textViewHeader.setVisibility(View.VISIBLE);

                                        textViewDate.setVisibility(View.VISIBLE);

                                        textViewResult.setVisibility(View.VISIBLE);

                                        textViewHeader.setText(Html.fromHtml(header));

                                        textViewDate.setText(stringDate);

                                        textViewResult.append(Html.fromHtml(finalResult));

                                        buttonAction.setText("Share");

                                        buttonAction.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                sendReport(device, report, strHeader, htmlFormat, context);

                                            }
                                        });

                                    }
                                });

                                break;

                            }

                        }

                    }catch(IOException ex){

                        handler.post(new Runnable() {

                            @Override
                            public void run() {

                                textViewMainLegend.setVisibility(View.VISIBLE);

                                textViewSubLegend.setVisibility(View.INVISIBLE);

                                textViewSubLegend.setVisibility(View.INVISIBLE);

                                relativeLayoutHeader.setVisibility(View.INVISIBLE);

                                textViewHeader.setVisibility(View.INVISIBLE);

                                textViewResult.setVisibility(View.INVISIBLE);

                                textViewMainLegend.setText(Html.fromHtml("<font color=#B22222>Report execution failed !</font>"));

                                buttonDone.setVisibility(View.VISIBLE);

                                buttonDone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        ReportActivity.staticReportActivity.finish();

                                        ReportExecuteStep1Activity.staticReportExecuteStep1Activity.finish();

                                        ReportExecuteStep2Activity.staticReportExecuteStep2Activity.finish();

                                        ((Activity) context).finish();

                                    }
                                });

                            }
                        });

                        Log.e("Report.java", "***ERROR : " + ex.toString());

                    }


                }catch(Exception ex){

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            textViewMainLegend.setVisibility(View.VISIBLE);

                            textViewSubLegend.setVisibility(View.INVISIBLE);

                            textViewSubLegend.setVisibility(View.INVISIBLE);

                            relativeLayoutHeader.setVisibility(View.INVISIBLE);

                            textViewHeader.setVisibility(View.INVISIBLE);

                            textViewResult.setVisibility(View.INVISIBLE);

                            textViewMainLegend.setText(Html.fromHtml("<font color=#B22222>Report execution failed !</font>"));

                            buttonDone.setVisibility(View.VISIBLE);

                            buttonDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    ReportActivity.staticReportActivity.finish();

                                    ReportExecuteStep1Activity.staticReportExecuteStep1Activity.finish();

                                    ReportExecuteStep2Activity.staticReportExecuteStep2Activity.finish();

                                    ((Activity) context).finish();

                                }
                            });

                        }
                    });

                    Log.e("Report.java", "***ERROR : " + ex.toString());

                }

            }
        });

        thread.start();

    }

    public static void Counter(float i, final TextView textViewSubLegend)
    {

        final float y = i;
        final Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (y< 1000) {
                    textViewSubLegend.setText(String.format("%.01f Byte",y));

                } else if(y >1000 && y<1000000) {

                    textViewSubLegend.setText(String.format("%.03f Kb",y/1000));

                }else{


                    textViewSubLegend.setText(String.format("%.03f Mb",y/1000000));

                }
            }
        });




    }

    public void saveReport(String rtfResult, String fileName) throws IOException {

        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        File file = new File(path, fileName);

        FileOutputStream stream = new FileOutputStream(file);

        try {

            stream.write(rtfResult.getBytes());

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            stream.close();

        }

    }

    public static void sendReport(Device device, Report report, String header, String result, Context context) {

        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = new Date();

            String stringDate = dateFormat.format(date);

            stringDate = stringDate.replace(" ", "T");

            stringDate = stringDate + "Z";

            String fileName = device.getDeviceProductName() + "-" + stringDate + ".html";

            report.saveReport(result, fileName);

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

            Uri path = Uri.fromFile(file);

            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setType("vnd.android.cursor.dir/email");

            String to[] = {""};

            String subject = "Report : " + report.getNameReport() + " - " + dateFormat.format(date) + " - " + device.getDeviceProductName();

            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);

            emailIntent.putExtra(Intent.EXTRA_STREAM, path);

            emailIntent.putExtra(Intent.EXTRA_TEXT, header);

            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

            context.startActivity(Intent.createChooser(emailIntent , "Share report"));

        } catch (Exception ex) {

            ex.printStackTrace();

            Log.e("Report.java", "***ERROR : " + ex.toString());

            Dialog.getUserError(context, "Please allow CresTools to access to your files to continue");

        }

    }

}