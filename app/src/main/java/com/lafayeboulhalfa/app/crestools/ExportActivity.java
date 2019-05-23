package com.lafayeboulhalfa.app.crestools;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExportActivity extends AppCompatActivity {

    public Project projectInstance;

    public Device deviceInstance;

    public Xadr xadrInstance;

    public int amountDevices;

    public int amountDevicesSelected = 0;

    public void initAddresses(){

        try{

            Intent i = getIntent();

            final int projectId = i.getIntExtra("projectId", 0);

            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_cbAddresses);

            ArrayList<CheckBox> cbAddresses = new ArrayList<CheckBox>();

            List<Device> devicesAvailable = new ArrayList<Device>();

            if(Device.isEmptyListByProject(ExportActivity.this, projectId)){

                final TextView textView = new TextView(ExportActivity.this);

                textView.setText("No devices added yet");

                textView.setPadding(25,25,0,0);

                layout.addView(textView);

            }else{

                devicesAvailable = deviceInstance.getDeviceByProject(ExportActivity.this, projectId);

                if(devicesAvailable != null){

                    amountDevices = devicesAvailable.size();

                    syncAmountDevices();

                    for(Device deviceAvailable:devicesAvailable){

                        final CheckBox cbAddress = new CheckBox(ExportActivity.this);

                        cbAddress.setId(deviceAvailable.getDeviceId());

                        cbAddress.setText(Html.fromHtml("<b>" + deviceAvailable.getDeviceIp() + ":" + deviceAvailable.getDevicePort() + "</b><br>" + "   " + deviceAvailable.getDeviceHostname()));

                        cbAddress.setBackgroundResource(R.drawable.item_list_border);

                        cbAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

                        cbAddress.setGravity(Gravity.CENTER_VERTICAL);

                        cbAddress.setPadding(30,0,0,0);

                        cbAddress.setHeight(150);

                        cbAddresses.add(cbAddress);

                        layout.addView(cbAddress);

                        cbAddress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(cbAddress.isChecked()){

                                    amountDevicesSelected = amountDevicesSelected + 1;

                                    syncAmountDevices();

                                }else{

                                    amountDevicesSelected = amountDevicesSelected - 1;

                                    syncAmountDevices();

                                }

                            }
                        });

                    }

                }

            }

        }catch(Exception e){

            Dialog.getDebug(ExportActivity.this, e);

        }

    }

    public void initControllers(){

        Intent i = getIntent();

        final int projectId = i.getIntExtra("projectId", 0);

        final Button buttonSelectAll = (Button)findViewById(R.id.buttonSelectAll);

        final ImageButton buttonExport = (ImageButton) findViewById(R.id.imageButtonRight);

        final ImageButton buttonCancel = (ImageButton) findViewById(R.id.imageButtonLeft);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });

        buttonSelectAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try{

                    boolean isSelectedAll = false;

                    if(buttonSelectAll.getText().toString() == "Select All");
                    isSelectedAll = false;

                    if(buttonSelectAll.getText().toString() == "Deselect All")
                        isSelectedAll = true;

                    Intent i = getIntent();

                    final int projectId = i.getIntExtra("projectId", 0);

                    List<Device> devicesAvailable = new ArrayList<Device>();

                    if(deviceInstance.isEmptyListByProject(ExportActivity.this, projectId) == false){

                        devicesAvailable = deviceInstance.getDeviceByProject(ExportActivity.this, projectId);

                    }

                    if(devicesAvailable != null){

                        for(Device deviceAvailable:devicesAvailable){

                            int cbId = deviceAvailable.getDeviceId();

                            CheckBox cbAddress = (CheckBox)findViewById(cbId);

                            if(cbAddress != null){

                                if(isSelectedAll == false){

                                    cbAddress.setChecked(true);

                                    buttonSelectAll.setText("Deselect All");

                                    amountDevicesSelected = amountDevices;

                                    syncAmountDevices();

                                }

                                if(isSelectedAll == true){

                                    cbAddress.setChecked(false);

                                    buttonSelectAll.setText("Select All");

                                    amountDevicesSelected = 0;

                                    syncAmountDevices();

                                }

                            }

                        }

                    }

                }catch(Exception e){

                    Dialog.getDebug(ExportActivity.this, e);

                }

            }

        });

        buttonExport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {

                    List<Device> devicesToSend = new ArrayList<Device>();

                    Intent i = getIntent();

                    final int projectId = i.getIntExtra("projectId", 0);

                    Project project = projectInstance.getProjectById(ExportActivity.this, projectId);

                    LinearLayout layout = (LinearLayout) findViewById(R.id.layout_cbAddresses);

                    List<Device> devicesAvailable = new ArrayList<Device>();

                    if (deviceInstance.isEmptyListByProject(ExportActivity.this, projectId) == false) {

                        devicesAvailable = deviceInstance.getDeviceByProject(ExportActivity.this, projectId);

                    }

                    if (devicesAvailable != null) {

                        for (Device deviceAvailable : devicesAvailable) {

                            CheckBox cbAddress = layout.findViewById(deviceAvailable.getDeviceId());

                            if(cbAddress != null){

                                if(cbAddress.isChecked() == true){

                                    devicesToSend.add(deviceAvailable);

                                }

                            }

                        }

                    }

                    if(devicesToSend != null && !devicesToSend.isEmpty()){

                        String result = xadrInstance.encode(ExportActivity.this, devicesToSend);

                        Xadr xadr = new Xadr(project.getProjectName(), result);

                        xadrInstance.exportXadr(xadr, ExportActivity.this);

                    }

                }catch(Exception e){

                    Dialog.getUserError(ExportActivity.this, "Please allow CresTools to access to your files to continue");

                }


            }
        });


    }

    public void syncAmountDevices(){

        TextView textViewAmountDevices = (TextView) findViewById(R.id.textViewAmountDevices);

        Button buttonSelectAll = (Button) findViewById(R.id.buttonSelectAll);

        if(amountDevicesSelected > 0){

            buttonSelectAll.setText("Deselect All");

        }else{

            buttonSelectAll.setText("Select All");

        }

        if(amountDevices > 1){

            textViewAmountDevices.setText(amountDevicesSelected + "/" + amountDevices + " devices");

        }else{

            textViewAmountDevices.setText(amountDevicesSelected + "/" + amountDevices + " device");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(ExportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ExportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        initAddresses();

        initControllers();

    }
}
