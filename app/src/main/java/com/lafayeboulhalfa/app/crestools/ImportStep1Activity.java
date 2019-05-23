package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImportStep1Activity extends AppCompatActivity {

    public static Activity staticImportStep1Activity;

    public Project projectInstance;

    public Device deviceInstance;

    public Xadr xadrInstance;

    public int amountDevices;

    public int amountDevicesSelected = 0;

    public String readFile() throws IOException, ClassNotFoundException {

        String result;

        Uri uri = getIntent().getData();

        if(uri != null){

            InputStream in = getContentResolver().openInputStream(uri);

            result = xadrInstance.readXadr(in);

            SerializableManager.writeString(ImportStep1Activity.this, xadrInstance.tempDevicesToImport, result);

        }else{

            result = SerializableManager.readString(ImportStep1Activity.this, xadrInstance.tempDevicesToImport);

        }

        return result;

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

    public void initAddresses(){

        try{

            String result = null;

            try {

                result = readFile();

            } catch (IOException e) {

                Dialog.getDebug(ImportStep1Activity.this, e);

            }

            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_cbAddresses);

            ArrayList<CheckBox> cbAddresses = new ArrayList<CheckBox>();

            List<Device> devicesAvailable = new ArrayList<Device>();

            devicesAvailable = xadrInstance.decode(ImportStep1Activity.this, result);

            if(devicesAvailable != null){

                amountDevices = devicesAvailable.size();

                syncAmountDevices();

                for(Device deviceAvailable:devicesAvailable){

                    final CheckBox cbAddress = new CheckBox(ImportStep1Activity.this);

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

        }catch(Exception e){

            Dialog.getDebug(ImportStep1Activity.this, e);

        }

    }

    public void initControllers() throws ClassNotFoundException {

        final ImageButton buttonCancel = (ImageButton)findViewById(R.id.imageButtonLeft);

        final ImageButton buttonImport = (ImageButton)findViewById(R.id.imageButtonRight);

        final Button buttonSelectAll = (Button)findViewById(R.id.buttonSelectAll);

        String result = null;

        try {

            result = readFile();

        } catch (IOException e) {

            Dialog.getDebug(ImportStep1Activity.this, e);

        }

        final List<Device> devicesAvailable = xadrInstance.decode(ImportStep1Activity.this, result);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();

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

                    Dialog.getDebug(ImportStep1Activity.this, e);

                }

            }

        });

        buttonImport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {

                    List<Device> devicesToSend = new ArrayList<Device>();

                    LinearLayout layout = (LinearLayout) findViewById(R.id.layout_cbAddresses);

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

                        SerializableManager.saveSerializable(ImportStep1Activity.this, devicesToSend, deviceInstance.devicesToImportPath);

                        Intent i = new Intent(ImportStep1Activity.this, ImportStep2Activity.class);

                        startActivity(i);

                    }

                }catch(Exception e){

                    Dialog.getDebug(ImportStep1Activity.this, e);

                }




            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_step1);

        staticImportStep1Activity = this;

        initAddresses();

        try {

            initControllers();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }

    }

}
