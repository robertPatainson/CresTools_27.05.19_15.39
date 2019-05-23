package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReportExecuteStep2Activity extends AppCompatActivity {

    public static Activity staticReportExecuteStep2Activity;

    public void initControllers(){

        final ImageButton imageButtonPrevious = (ImageButton) findViewById(R.id.imageButtonLeft);

        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

    }

    public void initDevices(){

        Intent i = getIntent();

        final int projectId = i.getIntExtra("projectId", 0);

        final Report report = (Report) i.getExtras().getSerializable("storedReport");

        final LinearLayout layout_buttonsDevices = (LinearLayout) findViewById(R.id.layout_buttonsDevices);

        List<Device> motherDevices = Device.getMothersDeviceByProject(ReportExecuteStep2Activity.this, DeviceActivity.staticProjectId);

        ArrayList<TextView> buttonsDevice = new ArrayList<TextView>();

        if(Device.isEmptyListByProject(ReportExecuteStep2Activity.this, projectId)){

            final TextView textView = new TextView(ReportExecuteStep2Activity.this);

            View topBar = (View) findViewById(R.id.topBar);

            topBar.setVisibility(View.INVISIBLE);

            textView.setText("No devices added yet");

            textView.setPadding(30,60,0,0);

            layout_buttonsDevices.addView(textView);

        }else{

            for(final Device motherDevice : motherDevices){

                final TextView buttonDevice = new TextView(ReportExecuteStep2Activity.this);

                buttonDevice.setId(motherDevice.getDeviceId());

                if(motherDevice.getDeviceProductName().trim().equals("")){

                    buttonDevice.setText(Html.fromHtml("<b>" + motherDevice.getDeviceHostname() + "</b>" + "<br>" + motherDevice.getDeviceIp() + ":" + motherDevice.getDevicePort()));

                }else{

                    buttonDevice.setText(Html.fromHtml("<b>" + motherDevice.getDeviceHostname() + "</b>" + "<br>" + motherDevice.getDeviceProductName()));

                }

                buttonDevice.setBackgroundResource(R.drawable.item_list_border);

                buttonDevice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                buttonDevice.setTextColor(getResources().getColor(R.color.Black));

                buttonDevice.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                buttonDevice.setPadding(50,0,50,0);

                buttonDevice.setHeight(150);

                buttonDevice.setTransformationMethod(null);

                buttonsDevice.add(buttonDevice);

                layout_buttonsDevices.addView(buttonDevice);

                if(motherDevice.deviceHasChild(ReportExecuteStep2Activity.this) == true){

                    List<Device> childrenList = Device.getDeviceChildren(ReportExecuteStep2Activity.this, motherDevice);

                    List<String> listIp = new ArrayList<String>();

                    List<Integer> listPort = new ArrayList<Integer>();

                    for(Device child : childrenList){

                        listIp.add(child.getDeviceIp());

                        listPort.add(child.getDevicePort());

                    }

                    listIp.add(motherDevice.getDeviceIp());

                    listPort.add(motherDevice.getDevicePort());

                    final String [] arrIp = listIp.toArray(new String[listIp.size()]);

                    final Integer [] arrPort = listPort.toArray(new Integer[listPort.size()]);

                    buttonDevice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            getUserPopup(ReportExecuteStep2Activity.this, view, view.getRootView(), motherDevice.getDeviceProjectId(), arrIp, arrPort);

                        }
                    });

                }else{

                    buttonDevice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            startExecution(motherDevice);

                        }
                    });

                }

            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void getUserPopup(Activity activity, View view, final View parent, final int projectId, String[] arrayGroupedDevicesIp, Integer[] arrayGroupedDevicesPort) {

        Dialog.hideKeyboard(activity);

        final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();

        Dialog.applyDim(root, 0.5f);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
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
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 20);

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

            final Device device = Device.getDeviceByAddress(getApplicationContext(), projectId, address);

            final Button buttonOpenDevice = new Button(getApplicationContext());

            buttonOpenDevice.setId(device.getDeviceId());

            buttonOpenDevice.setText(address);

            buttonOpenDevice.setTransformationMethod(null);

            buttonOpenDevice.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));

            buttonOpenDevice.setBackgroundResource(R.drawable.item_list_border);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                buttonOpenDevice.setStateListAnimator(null);
            }

            final int finalI = i;

            buttonOpenDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Device currentDevice = Device.getDeviceByAddress(getApplicationContext(), projectId, buttonOpenDevice.getText().toString());

                    popupWindow.dismiss();

                    startExecution(currentDevice);

                }
            });

            linearLayoutAddresses.addView(buttonOpenDevice);

        }

    }

    public void startExecution(Device device){

        Intent intent = getIntent();

        final int projectId = (int) intent.getExtras().getSerializable("projectId");

        Report report = (Report) intent.getExtras().getSerializable("storedReport");

        Intent i = new Intent(ReportExecuteStep2Activity.this, ReportExecuteStep3Activity.class);

        i.putExtra("projectId", projectId);

        i.putExtra("storedReport", report);

        i.putExtra("storedDevice", device);

        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_execute_step2);

        initControllers();

        initDevices();

        staticReportExecuteStep2Activity = this;

    }
}

