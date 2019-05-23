package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.daimajia.swipe.SwipeLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DeviceActivity extends AppCompatActivity {

    private class ConnectionReceiver extends BroadcastReceiver {

        public void disableLineDevice(Device currentDevice){

            String concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(currentDevice.getDeviceId());

            int idSwipe = Integer.parseInt(concatSwipe);

            String concatStatus = Integer.toString(fakeStatusId) + Integer.toString(currentDevice.getDeviceId());

            int idStatus = Integer.parseInt(concatStatus);

            String concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceId());

            int idIcon = Integer.parseInt(concatIcon);

            String concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceId());

            int idProgressBar = Integer.parseInt(concatProgressBar);

            SwipeLayout swipeLayout = (SwipeLayout) findViewById(idSwipe);

            TextView textViewDevice = (TextView) findViewById(currentDevice.getDeviceId());

            TextView textViewStatus = (TextView) findViewById(idStatus);

            ImageView iconDevice = (ImageView) findViewById(idIcon);

            ProgressBar progressBar = (ProgressBar) findViewById(idProgressBar);

            if(swipeLayout == null && textViewDevice == null && textViewStatus == null && iconDevice == null && progressBar == null){

                concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(currentDevice.getDeviceDependanceId());

                idSwipe = Integer.parseInt(concatSwipe);

                concatStatus = Integer.toString(fakeStatusId) + Integer.toString(currentDevice.getDeviceDependanceId());

                idStatus = Integer.parseInt(concatStatus);

                concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceDependanceId());

                idIcon = Integer.parseInt(concatIcon);

                concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceDependanceId());

                idProgressBar = Integer.parseInt(concatProgressBar);

                swipeLayout = (SwipeLayout) findViewById(idSwipe);

                textViewDevice = (TextView) findViewById(currentDevice.getDeviceDependanceId());

                textViewStatus = (TextView) findViewById(idStatus);

                iconDevice = (ImageView) findViewById(idIcon);

                progressBar = (ProgressBar) findViewById(idProgressBar);

            }

            swipeLayout.setEnabled(false);

            textViewDevice.setEnabled(false);

            textViewStatus.setEnabled(false);

            iconDevice.setEnabled(false);

            progressBar.setEnabled(false);

        }

        public void enableLineDevice(Device currentDevice){

            String concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(currentDevice.getDeviceId());

            int idSwipe = Integer.parseInt(concatSwipe);

            String concatStatus = Integer.toString(fakeStatusId) + Integer.toString(currentDevice.getDeviceId());

            int idStatus = Integer.parseInt(concatStatus);

            String concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceId());

            int idIcon = Integer.parseInt(concatIcon);

            String concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceId());

            int idProgressBar = Integer.parseInt(concatProgressBar);

            SwipeLayout swipeLayout = (SwipeLayout) findViewById(idSwipe);

            TextView textViewDevice = (TextView) findViewById(currentDevice.getDeviceId());

            TextView textViewStatus = (TextView) findViewById(idStatus);

            ImageView iconDevice = (ImageView) findViewById(idIcon);

            ProgressBar progressBar = (ProgressBar) findViewById(idProgressBar);

            if(swipeLayout == null && textViewDevice == null && textViewStatus == null && iconDevice == null && progressBar == null){

                concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(currentDevice.getDeviceDependanceId());

                idSwipe = Integer.parseInt(concatSwipe);

                concatStatus = Integer.toString(fakeStatusId) + Integer.toString(currentDevice.getDeviceDependanceId());

                idStatus = Integer.parseInt(concatStatus);

                concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceDependanceId());

                idIcon = Integer.parseInt(concatIcon);

                concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceDependanceId());

                idProgressBar = Integer.parseInt(concatProgressBar);

                swipeLayout = (SwipeLayout) findViewById(idSwipe);

                textViewDevice = (TextView) findViewById(currentDevice.getDeviceDependanceId());

                textViewStatus = (TextView) findViewById(idStatus);

                iconDevice = (ImageView) findViewById(idIcon);

                progressBar = (ProgressBar) findViewById(idProgressBar);

            }

            swipeLayout.setEnabled(true);

            textViewDevice.setEnabled(true);

            textViewStatus.setEnabled(true);

            iconDevice.setEnabled(true);

            progressBar.setEnabled(true);

        }

        public void setLineDeviceOn(Device currentDevice){

            if (currentDevice.getDeviceSocketStatus() == true) {

                String concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(currentDevice.getDeviceId());

                int idSwipe = Integer.parseInt(concatSwipe);

                String concatStatus = Integer.toString(fakeStatusId) + Integer.toString(currentDevice.getDeviceId());

                int idStatus = Integer.parseInt(concatStatus);

                String concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceId());

                int idIcon = Integer.parseInt(concatIcon);

                String concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceId());

                int idProgressBar = Integer.parseInt(concatProgressBar);

                SwipeLayout swipeLayout = (SwipeLayout) findViewById(idSwipe);

                TextView textViewDevice = (TextView) findViewById(currentDevice.getDeviceId());

                TextView textViewStatus = (TextView) findViewById(idStatus);

                ImageView iconDevice = (ImageView) findViewById(idIcon);

                ProgressBar progressBar = (ProgressBar) findViewById(idProgressBar);

                if(swipeLayout == null && textViewDevice == null && textViewStatus == null && iconDevice == null && progressBar == null){

                    concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(currentDevice.getDeviceDependanceId());

                    idSwipe = Integer.parseInt(concatSwipe);

                    concatStatus = Integer.toString(fakeStatusId) + Integer.toString(currentDevice.getDeviceDependanceId());

                    idStatus = Integer.parseInt(concatStatus);

                    concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceDependanceId());

                    idIcon = Integer.parseInt(concatIcon);

                    concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceDependanceId());

                    idProgressBar = Integer.parseInt(concatProgressBar);

                    swipeLayout = (SwipeLayout) findViewById(idSwipe);

                    textViewDevice = (TextView) findViewById(currentDevice.getDeviceDependanceId());

                    textViewStatus = (TextView) findViewById(idStatus);

                    iconDevice = (ImageView) findViewById(idIcon);

                    progressBar = (ProgressBar) findViewById(idProgressBar);

                }

                if(swipeLayout != null){

                    swipeLayout.setSwipeEnabled(true);

                }

                if(textViewStatus != null){

                    textViewStatus.setText("CONNECTED");

                }

                if(iconDevice != null){

                    if(progressBar != null){

                        progressBar.setVisibility(View.GONE);

                    }

                    iconDevice.setVisibility(View.VISIBLE);

                    iconDevice.setImageResource(R.drawable.ic_chevron_right);

                    final TextView finalTextViewDevice = textViewDevice;

                    iconDevice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            finalTextViewDevice.performClick();

                        }
                    });

                }

            }

            enableLineDevice(currentDevice);

        }

        public void setLineDeviceOff(Device currentDevice){

            if(currentDevice.getDeviceSocketStatus() == false){

                if(currentDevice.getDeviceMotherStatus() == false){

                    currentDevice = deviceInstance.getMotherDevice(DeviceActivity.this, currentDevice);

                }

                String concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(currentDevice.getDeviceId());

                int idSwipe = Integer.parseInt(concatSwipe);

                String concatStatus = Integer.toString(fakeStatusId) + Integer.toString(currentDevice.getDeviceId());

                int idStatus = Integer.parseInt(concatStatus);

                String concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceId());

                int idIcon = Integer.parseInt(concatIcon);

                String concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceId());

                int idProgressBar = Integer.parseInt(concatProgressBar);

                SwipeLayout swipeLayout = (SwipeLayout) findViewById(idSwipe);

                TextView textViewStatus = (TextView) findViewById(idStatus);

                ImageView iconDevice = (ImageView) findViewById(idIcon);

                ProgressBar progressBar = (ProgressBar) findViewById(idProgressBar);

                if(swipeLayout == null && textViewStatus == null && iconDevice == null && progressBar == null){

                    concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(currentDevice.getDeviceDependanceId());

                    idSwipe = Integer.parseInt(concatSwipe);

                    concatStatus = Integer.toString(fakeStatusId) + Integer.toString(currentDevice.getDeviceDependanceId());

                    idStatus = Integer.parseInt(concatStatus);

                    concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceDependanceId());

                    idIcon = Integer.parseInt(concatIcon);

                    concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceDependanceId());

                    idProgressBar = Integer.parseInt(concatProgressBar);

                    swipeLayout = (SwipeLayout) findViewById(idSwipe);

                    textViewStatus = (TextView) findViewById(idStatus);

                    iconDevice = (ImageView) findViewById(idIcon);

                    progressBar = (ProgressBar) findViewById(idProgressBar);

                }

                if(swipeLayout != null){

                    swipeLayout.setSwipeEnabled(false);

                }

                if(textViewStatus != null){

                    textViewStatus.setText(" ");

                }

                if(iconDevice != null){

                    if(progressBar != null){

                        progressBar.setVisibility(View.GONE);

                    }

                    iconDevice.setVisibility(View.VISIBLE);

                    iconDevice.setImageResource(R.drawable.ic_info);

                    final Device finalCurrentDevice = currentDevice;

                    iconDevice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent i = new Intent(getApplicationContext(), DeviceEditActivity.class);

                            i.putExtra("storedDevice", finalCurrentDevice);

                            i.putExtra("projectId", finalCurrentDevice.getDeviceProjectId());

                            startActivity(i);


                        }
                    });

                }

            }

            enableLineDevice(currentDevice);

        }

        public void setLineDeviceLoading(Device currentDevice){

            String concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceId());

            int idIcon = Integer.parseInt(concatIcon);

            String concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceId());

            int idProgressBar = Integer.parseInt(concatProgressBar);

            ImageView iconDevice = (ImageView) findViewById(idIcon);

            if(iconDevice == null){

                concatIcon = Integer.toString(fakeIconId) + Integer.toString(currentDevice.getDeviceDependanceId());

                idIcon = Integer.parseInt(concatIcon);

                iconDevice = (ImageView) findViewById(idIcon);

            }

            if(iconDevice != null){

                iconDevice.setVisibility(View.GONE);

                ProgressBar progressBar = (ProgressBar) findViewById(idProgressBar);

                if(progressBar == null){

                    concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(currentDevice.getDeviceDependanceId());

                    idProgressBar = Integer.parseInt(concatProgressBar);

                    progressBar = (ProgressBar) findViewById(idProgressBar);

                }

                if(progressBar != null){

                    progressBar.setVisibility(View.VISIBLE);

                }

            }

            disableLineDevice(currentDevice);

        }

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            String typeData = arg1.getAction();

            LinearLayout layout = findViewById(R.id.layout_listViewDevice);

            int deviceId = 0;

            int projectId = 0;

            deviceId = arg1.getIntExtra("deviceId", 0);

            projectId = arg1.getIntExtra("projectId", 0);

            if (deviceId != 0) {

                Device currentDevice = Device.getDeviceById(DeviceActivity.this, projectId, deviceId);

                TextView textViewDevice;

                if(currentDevice != null) {

                    textViewDevice = layout.findViewById(currentDevice.getDeviceId());

                    if(textViewDevice == null)
                        textViewDevice = layout.findViewById(currentDevice.getDeviceDependanceId());

                    if(textViewDevice != null){

                        String deviceAddress = currentDevice.getDeviceIp() + ":" + currentDevice.getDevicePort();

                        switch (typeData) {

                            case "didReceiveData":

                                break;

                            case "didDisconnect":

                                setLineDeviceOff(currentDevice);

                                textViewDevice.setEnabled(true);

                                break;

                            case "didConnect":

                                setLineDeviceOn(currentDevice);

                                textViewDevice.setEnabled(true);

                                enableButtonDisconnect();

                                break;

                            case "errorHandler":

                                setLineDeviceOff(currentDevice);

                                Dialog.getUserError(DeviceActivity.this, "Unable to reach connection with " + deviceAddress);

                                textViewDevice.setEnabled(true);

                                break;

                            case "loadHandler":

                                setLineDeviceLoading(currentDevice);

                                disableButtonDisconnect();

                                break;

                            case "exceptionBrokenPipe":

                                String _deviceAddress = arg1.getExtras().getString("deviceAddress");

                                AlertDialog.Builder builder = new AlertDialog.Builder(DeviceActivity.this);

                                builder.setMessage("Connection lost with " + _deviceAddress)
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.dismiss();

                                            }
                                        });

                                AlertDialog alert = builder.create();

                                alert.show();

                                break;

                        }

                    }else{

                        restartActivity();

                    }

                }

            }

        }

    }

    private class AdapterManager extends BaseAdapter {

        Context context;

        Device deviceList[];

        LayoutInflater inflater;

        public AdapterManager(Context applicationContext, Context context, Device[] deviceList) {

            this.context = context;

            this.deviceList = deviceList;

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
            return deviceList.length;
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

            final Device device = deviceList[i];

            boolean isConnected = false;

            if(device.deviceHasChild(getContext()) == true){

                List<Device> childrenList = Device.getDeviceChildren(getContext(), device);

                List<String> listIp = new ArrayList<String>();

                List<Integer> listPort = new ArrayList<Integer>();

                for(Device child : childrenList){

                    listIp.add(child.getDeviceIp());

                    listPort.add(child.getDevicePort());

                    if(child.getDeviceSocketStatus() == true || device.getDeviceSocketStatus() == true)
                        isConnected = true;

                }

                listIp.add(device.getDeviceIp());

                listPort.add(device.getDevicePort());

                final String [] arrIp = listIp.toArray(new String[listIp.size()]);

                final Integer [] arrPort = listPort.toArray(new Integer[listPort.size()]);

                view = inflater.inflate(R.layout.listview_device, null);

                final LinearLayout layoutSurface = (LinearLayout) view.findViewById(R.id.layoutSurface);

                final SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(R.id.swipeLayout);

                final TextView textViewDisconnect = (TextView) view.findViewById(R.id.textViewDisconnect);

                final TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);

                final TextView textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);

                final ImageView icon = (ImageView) view.findViewById(R.id.icon);

                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

                String concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(device.getDeviceId());

                int idSwipe = Integer.parseInt(concatSwipe);

                String concatStatus = Integer.toString(fakeStatusId) + Integer.toString(device.getDeviceId());

                int idStatus = Integer.parseInt(concatStatus);

                String concatIcon = Integer.toString(fakeIconId) + Integer.toString(device.getDeviceId());

                int idIcon = Integer.parseInt(concatIcon);

                String concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(device.getDeviceId());

                int idProgressBar = Integer.parseInt(concatProgressBar);

                swipeLayout.setId(idSwipe);

                textViewTitle.setId(device.getDeviceId());

                textViewTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                textViewStatus.setId(idStatus);

                textViewStatus.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

                icon.setId(idIcon);

                progressBar.setId(idProgressBar);

                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

                layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.START;

                icon.setLayoutParams(layoutParams);

                LinearLayout.LayoutParams layoutParamsProgressBar=new LinearLayout.LayoutParams(100, 100);

                layoutParamsProgressBar.gravity = Gravity.CENTER_VERTICAL | Gravity.START;

                progressBar.setLayoutParams(layoutParamsProgressBar);

                swipeLayout.setSwipeEnabled(false);

                textViewDisconnect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Device connectedDevice = null;

                        for(int x=0; x < arrIp.length ; x=x+1) {

                            for(Device activeDevice : deviceInstance.activeDevices){

                                if(activeDevice.getDeviceProjectId() == device.getDeviceProjectId()
                                        && activeDevice.getDeviceIp().equals(arrIp[x]) && activeDevice.getDevicePort() == arrPort[x]){

                                    String address = activeDevice.getDeviceIp() + ":" + activeDevice.getDevicePort();

                                    connectedDevice = Device.getDeviceByAddress(DeviceActivity.this, device.getDeviceProjectId(), address);

                                    disconnectDevice(connectedDevice, connectedDevice.getDeviceProjectId());

                                    swipeLayout.close();

                                }

                            }

                        }

                    }
                });

                layoutSurface.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                    @Override
                    public void onClick(View view) {

                        boolean isConnected = false;

                        Device connectedDevice = null;

                        for(int x=0; x < arrIp.length ; x=x+1) {

                            for(Device activeDevice : deviceInstance.activeDevices){

                                if(activeDevice.getDeviceProjectId() == device.getDeviceProjectId()
                                        && activeDevice.getDeviceIp().equals(arrIp[x]) && activeDevice.getDevicePort() == arrPort[x]){

                                    isConnected = true;

                                    String address = activeDevice.getDeviceIp() + ":" + activeDevice.getDevicePort();

                                    connectedDevice = Device.getDeviceByAddress(DeviceActivity.this, device.getDeviceProjectId(), address);

                                }

                            }

                        }

                        if(isConnected == true){

                            if(connectedDevice != null){

                                communicateWithSocket(connectedDevice,device.getDeviceProjectId(),-1, textViewStatus.getText().toString());

                            }

                        }else{

                            getUserPopup((Activity) getContext(), view, device.getDeviceProjectId(), arrIp, arrPort);

                        }

                    }

                });

                if(isConnected == true){

                    swipeLayout.setSwipeEnabled(true);

                    if(device.getDeviceProductName().trim().equals("")){

                        textViewTitle.setText(Html.fromHtml("<b>" + device.getDeviceHostname() + "</b>" + "<br>" + device.getDeviceIp() + ":" + device.getDevicePort()));

                    }else{

                        textViewTitle.setText(Html.fromHtml("<b>" + device.getDeviceHostname() + "</b>" + "<br>" + device.getDeviceProductName()));

                    }

                    textViewStatus.setText("CONNECTED");

                    icon.setImageResource(R.drawable.ic_chevron_right);

                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Device connectedDevice = null;

                            for(int x=0; x < arrIp.length ; x=x+1) {

                                for(Device activeDevice : deviceInstance.activeDevices){

                                    if(activeDevice.getDeviceProjectId() == device.getDeviceProjectId()
                                            && activeDevice.getDeviceIp().equals(arrIp[x]) && activeDevice.getDevicePort() == arrPort[x]){

                                        String address = activeDevice.getDeviceIp() + ":" + activeDevice.getDevicePort();

                                        connectedDevice = Device.getDeviceByAddress(DeviceActivity.this, device.getDeviceProjectId(), address);

                                    }

                                }

                            }

                            communicateWithSocket(connectedDevice,device.getDeviceProjectId(),-1, textViewStatus.getText().toString());

                        }
                    });

                }else{

                    if(device.getDeviceProductName().trim().equals("")){

                        textViewTitle.setText(Html.fromHtml("<b>" + device.getDeviceHostname() + "</b>" + "<br>" + device.getDeviceIp() + ":" + device.getDevicePort()));

                    }else{

                        textViewTitle.setText(Html.fromHtml("<b>" + device.getDeviceHostname() + "</b>" + "<br>" + device.getDeviceProductName()));

                    }
                    icon.setImageResource(R.drawable.ic_info);

                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent i = new Intent(getContext(), DeviceEditActivity.class);

                            i.putExtra("storedDevice", device);

                            i.putExtra("projectId", device.getDeviceProjectId());

                            getContext().startActivity(i);

                        }
                    });

                }

                return view;

            }else{

                view = inflater.inflate(R.layout.listview_device, null);

                final LinearLayout layoutSurface = (LinearLayout) view.findViewById(R.id.layoutSurface);

                final SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(R.id.swipeLayout);

                final TextView textViewDisconnect = (TextView) view.findViewById(R.id.textViewDisconnect);

                final TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);

                final TextView textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);

                final ImageView icon = (ImageView) view.findViewById(R.id.icon);

                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

                swipeLayout.setSwipeEnabled(false);

                String concatSwipe = Integer.toString(fakeSwipeId) + Integer.toString(device.getDeviceId());

                int idSwipe = Integer.parseInt(concatSwipe);

                String concatStatus = Integer.toString(fakeStatusId) + Integer.toString(device.getDeviceId());

                int idStatus = Integer.parseInt(concatStatus);

                String concatIcon = Integer.toString(fakeIconId) + Integer.toString(device.getDeviceId());

                int idIcon = Integer.parseInt(concatIcon);

                String concatProgressBar = Integer.toString(fakeProgressBarId) + Integer.toString(device.getDeviceId());

                int idProgressBar = Integer.parseInt(concatProgressBar);

                swipeLayout.setId(idSwipe);

                textViewTitle.setId(device.getDeviceId());

                textViewTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                textViewStatus.setId(idStatus);

                textViewStatus.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

                icon.setId(idIcon);

                progressBar.setId(idProgressBar);

                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

                layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.START;

                icon.setLayoutParams(layoutParams);

                LinearLayout.LayoutParams layoutParamsProgressBar=new LinearLayout.LayoutParams(100, 100);

                layoutParamsProgressBar.gravity = Gravity.CENTER_VERTICAL | Gravity.START;

                progressBar.setLayoutParams(layoutParamsProgressBar);

                textViewDisconnect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        disconnectDevice(device, device.getDeviceProjectId());

                        swipeLayout.close();

                    }
                });

                layoutSurface.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        communicateWithSocket(device, device.getDeviceProjectId(), i, textViewStatus.getText().toString());

                    }
                });

                if(device.getDeviceSocketStatus() == true){

                    swipeLayout.setSwipeEnabled(true);

                    if(device.getDeviceProductName().trim().equals("")){

                        textViewTitle.setText(Html.fromHtml("<b>" + device.getDeviceHostname() + "</b>" + "<br>" + device.getDeviceIp() + ":" + device.getDevicePort()));

                    }else{

                        textViewTitle.setText(Html.fromHtml("<b>" + device.getDeviceHostname() + "</b>" + "<br>" + device.getDeviceProductName()));

                    }
                    textViewStatus.setText("CONNECTED");

                    icon.setImageResource(R.drawable.ic_chevron_right);

                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            communicateWithSocket(device, device.getDeviceProjectId(), i, textViewStatus.getText().toString());

                        }
                    });

                }else{

                    if(device.getDeviceProductName().trim().equals("")){

                        textViewTitle.setText(Html.fromHtml("<b>" + device.getDeviceHostname() + "</b>" + "<br>" + device.getDeviceIp() + ":" + device.getDevicePort()));

                    }else{

                        textViewTitle.setText(Html.fromHtml("<b>" + device.getDeviceHostname() + "</b>" + "<br>" + device.getDeviceProductName()));

                    }

                    icon.setImageResource(R.drawable.ic_info);

                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent i = new Intent(getContext(), DeviceEditActivity.class);

                            i.putExtra("storedDevice", device);

                            i.putExtra("projectId", device.getDeviceProjectId());

                            getContext().startActivity(i);

                        }
                    });

                }

                return view;

            }

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void getUserPopup(Activity activity, View view, final int projectId, String[] arrayGroupedDevicesIp, Integer[] arrayGroupedDevicesPort) {

            Dialog.hideKeyboard(activity);

            final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();

            Dialog.applyDim(root, 0.5f);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            // inflate the layout of the popup window
            View popupView = inflater.inflate(R.layout.dynamic_popup, null);

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;

            int height = LinearLayout.LayoutParams.WRAP_CONTENT;

            boolean focusable = true; // lets taps outside the popup also dismiss it

            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    Dialog.clearDim(root);
                }
            });

            // show the popup window
            popupWindow.setAnimationStyle(R.style.popup_animation);
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    popupWindow.dismiss();

                    return true;

                }
            });

            LinearLayout linearLayoutAddresses;

            linearLayoutAddresses = popupView.findViewById(R.id.linearLayoutAddresses);

            Button buttonCancel;

            buttonCancel = popupView.findViewById(R.id.buttonCancel);

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    popupWindow.dismiss();

                }
            });

            for(int i = 0; i < arrayGroupedDevicesIp.length; i++){

                final String address = arrayGroupedDevicesIp[i] + ":" + arrayGroupedDevicesPort[i];

                final Device device = Device.getDeviceByAddress(getContext(), projectId, address);

                final Button buttonOpenDevice = new Button(getContext());

                buttonOpenDevice.setId(device.getDeviceId());

                buttonOpenDevice.setText(address);

                buttonOpenDevice.setTransformationMethod(null);

                buttonOpenDevice.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));

                if(i + 1 < arrayGroupedDevicesIp.length){

                    if(i + 1 == arrayGroupedDevicesIp.length - 1){

                        buttonOpenDevice.setBackgroundResource(R.drawable.button_middle);

                    }else{

                        buttonOpenDevice.setBackgroundResource(R.drawable.button);

                    }

                }else{

                    buttonOpenDevice.setBackgroundResource(R.drawable.button);

                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    buttonOpenDevice.setStateListAnimator(null);
                }

                final int finalI = i;

                buttonOpenDevice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Device currentDevice = Device.getDeviceByAddress(getContext(), projectId, buttonOpenDevice.getText().toString());

                        communicateWithSocket(currentDevice, projectId, finalI, buttonOpenDevice.getText().toString());

                        popupWindow.dismiss();

                    }
                });

                linearLayoutAddresses.addView(buttonOpenDevice);

            }

        }

        public void communicateWithSocket(Device device, int projectId, int addressPosition, String buttonText){

            boolean socketStatus = false;

            if(buttonText.toLowerCase().indexOf("CONNECTED".toLowerCase()) != -1){

                socketStatus = true;

            }

            if(socketStatus == false){

                final Intent service = new Intent(getContext(), SocketService.class);

                service.putExtra("storedDevice", device);

                service.putExtra("projectId", projectId);

                service.putExtra("addressPosition", addressPosition);

                service.putExtra("buttonText", buttonText);

                getContext().startService(service);

            }else if(socketStatus == true){

                Intent i = new Intent(getContext(), DeviceTerminalActivity.class);

                i.putExtra("storedDevice", device);

                i.putExtra("projectId", device.getDeviceProjectId());

                i.putExtra("addressPosition", addressPosition);

                getContext().startActivity(i);

            }

        }

        public void disconnectDevice(Device device, int projectId){

            final Intent service = new Intent(getContext(), SocketService.class);

            String fakeCommand = null;

            service.putExtra("storedDevice", device);

            service.putExtra("projectId", projectId);

            service.putExtra("connectionAction", "disconnect");

            service.putExtra("command", fakeCommand);

            getContext().startService(service);

            if(deviceInstance.activeDevices.size() == 1) {

                disableButtonDisconnect();

            }

        }

        public void setListViewHeightBasedOnChildren(ListView listView){

            float dip = 50f;

            Resources r = getResources();

            float pix = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );

            int px = Math.round(pix);

            ViewGroup.LayoutParams params = listView.getLayoutParams();

            params.height = listView.getCount()*px;

            listView.setLayoutParams(params);

            listView.requestLayout();

        }

    }

    public static Activity staticDeviceActivity;

    public static int staticProjectId;

    public static String ipPhone;

    public Project projectInstance;

    public Device device;

    public Device deviceInstance;

    public AdapterManager adapterManager;

    public LayoutInflater inflater;

    public BroadcastReceiver networkStateReceiver;

    public ConnectionReceiver connectionReceiver;

    public int fakeProgressBarId = 666666;

    public int fakeSwipeId = 777777;

    public int fakeStatusId = 888888;

    public int fakeIconId = 999999;

    public int getProjectId(){

        Intent i = getIntent();

        int projectId = i.getIntExtra("projectId", 0);

        return projectId;

    }

    public void restartActivity(){

        Intent intent = getIntent();

        finish();

        startActivity(intent);

    }

    public void disableButtonDisconnect(){

        Button buttonDisconnectDevices = (Button) findViewById(R.id.buttonDisconnectDevices);

        buttonDisconnectDevices.setEnabled(false);

        int textDisabledColor = getResources().getColor(R.color.Gray);

        buttonDisconnectDevices.setTextColor(textDisabledColor);

        buttonDisconnectDevices.setBackgroundResource(R.drawable.button_disabled);

    }

    public void enableButtonDisconnect(){

        Button buttonDisconnectDevices = (Button) findViewById(R.id.buttonDisconnectDevices);

        buttonDisconnectDevices.setEnabled(true);

        int textEnabledColor = getResources().getColor(R.color.FireBrick);

        buttonDisconnectDevices.setTextColor(textEnabledColor);

        buttonDisconnectDevices.setBackgroundResource(R.drawable.button);

    }

    public void initButtonDisconnect(){

        Button buttonDisconnectDevices = (Button) findViewById(R.id.buttonDisconnectDevices);

        int textDisabledColor = getResources().getColor(R.color.Gray);

        int textEnabledColor = getResources().getColor(R.color.FireBrick);

        Intent i = getIntent();

        int projectId = i.getIntExtra("projectId", 0);

        boolean isDeviceActiveInProject = false;

        if(deviceInstance.activeDevices != null && !deviceInstance.activeDevices.isEmpty()) {

            for(Device activeDevice : deviceInstance.activeDevices){

                if(activeDevice.getDeviceProjectId() == projectId){

                    isDeviceActiveInProject = true;

                }

            }

        }

        if(isDeviceActiveInProject == true){

            buttonDisconnectDevices.setEnabled(true);

            buttonDisconnectDevices.setTextColor(textEnabledColor);

            buttonDisconnectDevices.setBackgroundResource(R.drawable.button);

        }else{

            buttonDisconnectDevices.setEnabled(false);

            buttonDisconnectDevices.setTextColor(textDisabledColor);

            buttonDisconnectDevices.setBackgroundResource(R.drawable.button_disabled);

        }

    }

    public void disconnectAllDevices(){

        Intent service = new Intent(DeviceActivity.this, SocketService.class);

        Intent i = getIntent();

        int projectId = i.getIntExtra("projectId", 0);

        service.putExtra("projectId", projectId);

        service.putExtra("connectionAction", "disconnectAll");

        startService(service);

    }

    public void enableButtonBrowser(){

        Button buttonBrowser = (Button) findViewById(R.id.buttonBrowser);

        TextView textViewBrowserLegend = (TextView) findViewById(R.id.textViewBrowserLegend);

        buttonBrowser.setEnabled(true);

        int textEnabledColor = getResources().getColor(R.color.LightBlack);

        buttonBrowser.setTextColor(textEnabledColor);

        buttonBrowser.setBackgroundResource(R.drawable.button_middle);

        textViewBrowserLegend.setVisibility(View.INVISIBLE);

    }

    public void disableButtonBrowser(){

        Button buttonBrowser = (Button) findViewById(R.id.buttonBrowser);

        TextView textViewBrowserLegend = (TextView) findViewById(R.id.textViewBrowserLegend);

        buttonBrowser.setEnabled(false);

        int textDisabledColor = getResources().getColor(R.color.Gray);

        buttonBrowser.setTextColor(textDisabledColor);

        buttonBrowser.setBackgroundResource(R.drawable.button_disabled);

        textViewBrowserLegend.setVisibility(View.VISIBLE);

    }

    public void initWiFiStatus(){

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi.isConnected()) {

            enableButtonBrowser();

        }else{

            disableButtonBrowser();

        }

    }

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

                                disableButtonBrowser();

                            }else{

                                Log.d("Network Listener","WiFi connected");

                                enableButtonBrowser();

                                WifiManager myWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

                                WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();

                                int ipAddress = myWifiInfo.getIpAddress();

                                String myIp = android.text.format.Formatter.formatIpAddress(ipAddress);

                                System.out.println("Wifi personnal ip : " + myIp);

                                ipPhone = myIp;

                                enableButtonBrowser();

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

    public void initReceiver(){

        connectionReceiver = new DeviceActivity.ConnectionReceiver();

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("loadHandler");

        intentFilter.addAction("didConnect");

        intentFilter.addAction("didReceiveData");

        intentFilter.addAction("errorHandler");

        intentFilter.addAction("didDisconnect");

        intentFilter.addAction("exceptionBrokenPipe");

        registerReceiver(connectionReceiver, intentFilter);

    }

    public void stopReceiver(){

        try{

            if(connectionReceiver != null)
                unregisterReceiver(connectionReceiver);

        }catch(IllegalArgumentException e){

        }

    }

    public void initListView(){

        final Handler handler = new Handler();

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        TextView textViewLegend = findViewById(R.id.textViewLegend);

                        ProgressBar progressBarLoading = findViewById(R.id.progressBarLoading);

                        View bottomBar = findViewById(R.id.bottomBar);

                        TextView textViewAmountDevices = findViewById(R.id.textViewAmountDevices);

                        deviceInstance.initAllDeviceSocketStatus(DeviceActivity.this, false);

                        Intent i = getIntent();

                        final int projectId = i.getIntExtra("projectId", 0);

                        ListView listViewDevices;

                        List<Device> buffer = deviceInstance.getMothersDeviceByProject(DeviceActivity.this, projectId);

                        if(buffer.size() == 0){

                            textViewLegend.setVisibility(View.VISIBLE);

                        }else{

                            bottomBar.setVisibility(View.VISIBLE);

                            deviceInstance.orderListByHostname(buffer);

                            Device [] deviceList = buffer.toArray(new Device[buffer.size()]);

                            listViewDevices = (ListView) findViewById(R.id.listViewDevice);

                            adapterManager = new AdapterManager(DeviceActivity.this, DeviceActivity.this, deviceList);

                            inflater = adapterManager.getInflater();

                            listViewDevices.setAdapter(adapterManager);

                            adapterManager.setListViewHeightBasedOnChildren(listViewDevices);

                        }

                        progressBarLoading.setVisibility(View.GONE);

                        textViewAmountDevices.setVisibility(View.VISIBLE);

                        final Project project = projectInstance.getProjectById(DeviceActivity.this, projectId);

                        int amountDevices = projectInstance.getAmountOfDevicesInProject(DeviceActivity.this, project);

                        if(amountDevices > 1){

                            textViewAmountDevices.setText(amountDevices + " devices");

                        }else{

                            textViewAmountDevices.setText(amountDevices + " device");

                        }

                    }
                });

            }
        });

        thread.start();

    }

    public void initControllers(){

        final ImageButton imageButtonPrevious = (ImageButton)findViewById(R.id.imageButtonPrevious);

        final ImageButton imageButtonExport = (ImageButton) findViewById(R.id.imageButtonExport);

        final Button buttonAddDevice = (Button) findViewById(R.id.buttonAddDevice);

        final Button buttonDisconnectDevices = (Button) findViewById(R.id.buttonDisconnectDevices);

        final Button buttonMakeReport = (Button) findViewById(R.id.buttonMakeReport);

        final Button buttonBrowser = (Button) findViewById(R.id.buttonBrowser);

        final Button buttonDebugger = (Button) findViewById(R.id.buttonDebugger);

        Intent i = getIntent();

        final int projectId = i.getIntExtra("projectId", 0);

        initButtonDisconnect();

        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });

        imageButtonExport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(DeviceActivity.this, ExportActivity.class);

                i.putExtra("projectId", projectId);

                startActivity(i);

            }
        });

        buttonAddDevice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(DeviceActivity.this, DeviceAddStep1Activity.class);

                i.putExtra("projectId", projectId);

                startActivity(i);

            }
        });

        buttonDisconnectDevices.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                disconnectAllDevices();

                disableButtonDisconnect();

            }
        });

        buttonMakeReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(DeviceActivity.this, ReportActivity.class);

                i.putExtra("projectId", projectId);

                startActivity(i);

            }
        });

        buttonBrowser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(DeviceActivity.this, BrowserActivity.class);

                i.putExtra("projectId", projectId);

                startActivity(i);

            }
        });

        buttonDebugger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



            }
        });

    }

    public void initToolbar(){

        Intent i = getIntent();

        final int projectId = i.getIntExtra("projectId", 0);

        final Project project = projectInstance.getProjectById(DeviceActivity.this, projectId);

        final String projectName = project.getProjectName();

        int colorTitle = getResources().getColor(R.color.White);

        Typeface robotoBold = ResourcesCompat.getFont(DeviceActivity.this, R.font.robotobold);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapseToolbar);

        collapsingToolbar.setTitle(projectName);

        collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(colorTitle));

        collapsingToolbar.setExpandedTitleMarginStart(60);

        collapsingToolbar.setExpandedTitleMarginBottom(60);

        collapsingToolbar.setCollapsedTitleTextColor(colorTitle);

        collapsingToolbar.setExpandedTitleTypeface(robotoBold);

        collapsingToolbar.setCollapsedTitleTypeface(robotoBold);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

    }

    @Override
    protected void onStart() {

        super.onStart();
        setContentView(R.layout.activity_device);

        initToolbar();

        initControllers();

        initWiFiStatus();

        initWiFiListener();

        initReceiver();

        final LinearLayout layout = (LinearLayout) findViewById(R.id.layout_listViewDevice);

        ViewTreeObserver vto = layout.getViewTreeObserver();

        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                initListView();

            }
        });

        staticDeviceActivity = this;

        staticProjectId = getProjectId();

    }

    @Override
    protected void onStop() {
        super.onStop();

        stopWiFiListener();

        stopReceiver();

    }

}

