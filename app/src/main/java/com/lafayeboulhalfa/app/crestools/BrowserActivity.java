package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BrowserActivity extends AppCompatActivity {

    public static Activity staticBrowserActivity;

    public static List<Device> toAdd = new ArrayList<Device>();

    LayoutInflater inflater;

    public static int projectId;

    public static class AdapterManager extends BaseAdapter {

        Context context;

        String [] ipList;

        String [] serialList;

        String [] hostNamesList;

        LayoutInflater inflater;

        public AdapterManager(Context applicationContext, Context context, String[] ipList, String[] serialList, String[] hostNamesList) {

            this.context = context;

            this.ipList = ipList;

            this.serialList = serialList;

            this.hostNamesList = hostNamesList;

            inflater = (LayoutInflater.from(applicationContext));

        }

        public Context getContext(){
            return this.context;
        }

        public LayoutInflater getInflater(){
            return this.inflater;
        }

        @Override
        public int getCount() {
            return ipList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            view = inflater.inflate(R.layout.listview_browser, null);

            final String ip = ipList[i];

            final String serialNumber = serialList[i];

            final String productName = serialNumber;

            final String hostName = hostNamesList[i];

            final LinearLayout layoutLine = view.findViewById(R.id.layoutLine);

            final CheckBox checkBox = view.findViewById(R.id.checkBox);

            final TextView labelIp = view.findViewById(R.id.labelIp);

            final TextView labelHostname = view.findViewById(R.id.labelHostname);

            final TextView labelSerialNumber = view.findViewById(R.id.labelSerialNumber);

            labelIp.setText(ip);

            labelHostname.setText(hostName);

            labelSerialNumber.setText(serialNumber);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(checkBox.isChecked()){

                        enableButtonAdd();

                        Device d = new Device();

                        int port = 41795;

                        Device device = new Device(0, ip, port, "CTP",
                                "","",
                                serialNumber, productName, hostName, projectId,true, false, 0,"",false);

                        toAdd.add(device);

                    }else{

                        Iterator<Device> iter = toAdd.iterator();

                        while (iter.hasNext()) {
                            Device deviceToAdd = iter.next();

                            if(deviceToAdd.getDeviceIp().equals(labelIp.getText().toString())){

                                iter.remove();

                            }

                        }

                        if(toAdd.size() == 0){

                            disableButtonAdd();

                        }

                    }

                }
            });

            layoutLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    checkBox.performClick();

                }
            });

            return view;

        }

        public void enableButtonAdd(){

            final ImageButton buttonAdd = staticBrowserActivity.findViewById(R.id.imageButtonRight);

            buttonAdd.setEnabled(true);

            buttonAdd.setColorFilter(staticBrowserActivity.getResources().getColor(R.color.White));

        }

        public void disableButtonAdd(){

            final ImageButton buttonAdd = staticBrowserActivity.findViewById(R.id.imageButtonRight);

            buttonAdd.setEnabled(false);

            buttonAdd.setColorFilter(staticBrowserActivity.getResources().getColor(R.color.Gray));

        }

    }

    BroadcastReceiver networkStateReceiver;

    Browser browser;

    public void initWiFiListener(){

        networkStateReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                Log.w("Network Listener", "Network Type Changed : " + intent.getExtras());

                Bundle bundle = intent.getExtras();

                if (bundle != null) {

                    for (String key : bundle.keySet()) {

                        Object value = bundle.get(key);

                        //Log.d("Network Listener", String.format("%s %s (%s)", key,
                        //value.toString(), value.getClass().getName()));

                        if(key.equals("networkInfo")){

                            if(value.toString().contains("DISCONNECTED")){

                                Log.e("Network Listener","WiFi disconnected");

                                AlertDialog alertDialog = new AlertDialog.Builder(BrowserActivity.this).create();

                                alertDialog.setTitle("Error : ");

                                alertDialog.setIcon(R.drawable.ic_error);

                                alertDialog.setMessage("Browser connection lost. Check your wifi connection.");

                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",

                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();

                                                finish();

                                            }
                                        });

                                alertDialog.show();

                            }else{

                            }

                        }

                    }

                }

            }

        };

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(networkStateReceiver, filter);

    }

    public void stopWiFiListener(){

        try{

            if(networkStateReceiver != null)
                unregisterReceiver(networkStateReceiver);

        }catch(IllegalArgumentException e){

        }

    }

    public void initControllers(){

        final ImageButton buttonCancel = (ImageButton) findViewById(R.id.imageButtonLeft);

        final ImageButton buttonAdd = (ImageButton) findViewById(R.id.imageButtonRight);

        buttonAdd.setEnabled(false);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = 0;

                Device d = new Device();

                List<Device> devicesAvailable = new ArrayList<Device>();

                if(Device.isEmptyList(getApplicationContext()) == false){

                    devicesAvailable = SerializableManager.readSerializable(BrowserActivity.this, d.devicesAvailablePath);

                }

                for(Device deviceToAdd:toAdd){

                    boolean isExisting  = false;

                    String addressToAdd = deviceToAdd.getDeviceIp();

                    List<Device>devicesAvailableInProject = new ArrayList<Device>();

                    devicesAvailableInProject = Device.getDeviceByProject(BrowserActivity.this, projectId);

                    if(devicesAvailableInProject != null && devicesAvailable.size() > 0){

                        for(Device deviceAvailableInProject:devicesAvailableInProject){

                            String existingAddress = deviceAvailableInProject.getDeviceIp();

                            if(existingAddress.equals(addressToAdd))
                                isExisting = true;

                        }

                    }

                    if(!isExisting){

                        int deviceId = d.getUniqId(BrowserActivity.this) + count;

                        deviceToAdd.setDeviceId(deviceId);

                        devicesAvailable.add(deviceToAdd);

                        count = count + 1;

                    }

                }

                SerializableManager.saveSerializable(BrowserActivity.this, devicesAvailable, d.devicesAvailablePath);

                finish();

            }

        });

    }

    public void initBrowse(){

        ListView listView = findViewById(R.id.listView);

        View topBar = findViewById(R.id.topBar);

        View bottomBar = findViewById(R.id.bottomBar);

        browser = new com.lafayeboulhalfa.app.crestools.Browser();

        browser.browse(listView, topBar, bottomBar, BrowserActivity.this, inflater);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        staticBrowserActivity = this;

        Intent i = getIntent();

        projectId = i.getIntExtra("projectId", 0);

        initWiFiListener();

        initControllers();

        initBrowse();

    }

    @Override
    protected void onStop() {
        super.onStop();

        stopWiFiListener();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Browser.timer.cancel();

        Browser.sock.close();

        stopWiFiListener();

        toAdd.clear();

        finish();

    }

}
