package com.lafayeboulhalfa.app.crestools;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeviceAddStep2Activity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    public Device deviceInstance;

    public void prefillInformation(Device newDevice) throws IOException, InterruptedException {

        Report reportInstance = new Report();

        Report report = new Report(0,0,"Prefill Information", new String[]{"VER"," ","HOST"});

        Button buttonPrefill = (Button)findViewById(R.id.buttonPrefillInformations);

        TextView textViewSubtitle = (TextView)findViewById(R.id.textViewSubtitle);

        EditText editTextSerialNumber = (EditText) findViewById(R.id.editTextSerialNumber);

        EditText editTextProductName = (EditText) findViewById(R.id.editTextProductName);

        EditText editTextHostname = (EditText) findViewById(R.id.editTextHostname);

        ImageButton imageButtonPrevious = (ImageButton) findViewById(R.id.imageButtonPrevious);

        buttonPrefill.setEnabled(false);

        buttonPrefill.setBackgroundResource(R.drawable.button_command_disabled);

        switch (newDevice.getDeviceType()){

            case "CTP":

                reportInstance.execPrefillInformationOnDefault(buttonPrefill, newDevice, report, editTextSerialNumber, editTextProductName, editTextHostname, textViewSubtitle, imageButtonPrevious);

                break;

            case "SSL":

                reportInstance.execPrefillInformationOnDefault(buttonPrefill, newDevice, report, editTextSerialNumber, editTextProductName, editTextHostname, textViewSubtitle, imageButtonPrevious);

                break;

            case "SSH":

                reportInstance.execPrefillInformationOnSsh(buttonPrefill, newDevice, report, editTextSerialNumber, editTextProductName, editTextHostname, textViewSubtitle, imageButtonPrevious);

                break;

        }

        // buttonPrefill.setEnabled(true);

    }

    public boolean checkFields(){

        EditText editTextSerialNumber = (EditText) findViewById(R.id.editTextSerialNumber);

        EditText editTextProductName = (EditText) findViewById(R.id.editTextProductName);

        EditText editTextHostname = (EditText) findViewById(R.id.editTextHostname);

        if(editTextSerialNumber.getText().toString().trim().length() > 0 &&
                editTextProductName.getText().toString().trim().length() > 0 &&
                editTextHostname.getText().toString().trim().length() > 0){

            return true;

        }else{

            return false;

        }

    }

    public void syncControllers(){

        TextView textViewSubtitle = (TextView) findViewById(R.id.textViewSubtitle);

        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        Button buttonCreate = (Button) findViewById(R.id.imageButtonRight);

        RelativeLayout relativeLayoutSerialNumber = (RelativeLayout) findViewById(R.id.relativeLayoutSerialNumber);

        TextView textViewSubtitle2 = (TextView) findViewById(R.id.textViewSubtitle2);

        TextView textViewSerialNumber = (TextView) findViewById(R.id.textViewSerialNumber);

        EditText editTextSerialNumber = (EditText) findViewById(R.id.editTextSerialNumber);

        RelativeLayout relativeLayoutAdvanced = (RelativeLayout) findViewById(R.id.relativeLayoutAdvanced);

        TextView textViewProductName = (TextView) findViewById(R.id.textViewProductName);

        EditText editTextProductName = (EditText) findViewById(R.id.editTextProductName);

        TextView textViewHostname = (TextView) findViewById(R.id.textViewHostname);

        EditText editTextHostname = (EditText) findViewById(R.id.editTextHostname);

        ScrollView scrollView_existingDevices = (ScrollView) findViewById(R.id.scrollView_existingDevices);

        RelativeLayout relativeLayoutPrefillInformations = (RelativeLayout) findViewById(R.id.relativeLayoutPrefillInformations);

        TextView textViewPrefillInformations = (TextView) findViewById(R.id.textViewPrefillInformations);

        Button buttonPrefillInformations = (Button) findViewById(R.id.buttonPrefillInformations);

        textViewSubtitle.setVisibility(View.INVISIBLE);

        textViewTitle.setVisibility(View.INVISIBLE);

        buttonCreate.setVisibility(View.INVISIBLE);

        relativeLayoutSerialNumber.setVisibility(View.INVISIBLE);

        textViewSubtitle2.setVisibility(View.INVISIBLE);

        textViewSerialNumber.setVisibility(View.INVISIBLE);

        editTextSerialNumber.setVisibility(View.INVISIBLE);

        relativeLayoutAdvanced.setVisibility(View.INVISIBLE);

        textViewProductName.setVisibility(View.INVISIBLE);

        editTextProductName.setVisibility(View.INVISIBLE);

        textViewHostname.setVisibility(View.INVISIBLE);

        editTextHostname.setVisibility(View.INVISIBLE);

        scrollView_existingDevices.setVisibility(View.INVISIBLE);

        relativeLayoutPrefillInformations.setVisibility(View.INVISIBLE);

        textViewPrefillInformations.setVisibility(View.INVISIBLE);

        buttonPrefillInformations.setVisibility(View.INVISIBLE);



    }

    public void initControllers(){

        Intent i = getIntent();

        final HashMap<String, String> deviceData = (HashMap<String, String>)i.getSerializableExtra("deviceData");

        final int projectId = i.getIntExtra("projectId", 0);

        String typeDeviceAssociation = i.getExtras().getString("typeDeviceAssociation");

        final Device d = new Device();

        final ImageButton imageButtonPrevious = (ImageButton) findViewById(R.id.imageButtonLeft);

        final TextView textViewSubtitle = (TextView) findViewById(R.id.textViewSubtitle);

        final TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        final Button buttonCreate = (Button) findViewById(R.id.imageButtonRight);

        final RelativeLayout relativeLayoutSerialNumber = (RelativeLayout) findViewById(R.id.relativeLayoutSerialNumber);

        final TextView textViewSubtitle2 = (TextView) findViewById(R.id.textViewSubtitle2);

        final TextView textViewSerialNumber = (TextView) findViewById(R.id.textViewSerialNumber);

        final EditText editTextSerialNumber = (EditText) findViewById(R.id.editTextSerialNumber);

        final RelativeLayout relativeLayoutAdvanced = (RelativeLayout) findViewById(R.id.relativeLayoutAdvanced);

        final TextView textViewProductName = (TextView) findViewById(R.id.textViewProductName);

        final EditText editTextProductName = (EditText) findViewById(R.id.editTextProductName);

        final TextView textViewHostname = (TextView) findViewById(R.id.textViewHostname);

        final EditText editTextHostname = (EditText) findViewById(R.id.editTextHostname);

        final ScrollView scrollView_existingDevices = (ScrollView) findViewById(R.id.scrollView_existingDevices);

        final RelativeLayout relativeLayoutPrefillInformations = (RelativeLayout) findViewById(R.id.relativeLayoutPrefillInformations);

        final TextView textViewPrefillInformations = (TextView) findViewById(R.id.textViewPrefillInformations);

        final Button buttonPrefillInformations = (Button) findViewById(R.id.buttonPrefillInformations);

        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });

        syncControllers();

        switch(typeDeviceAssociation){

            case "new":

                textViewTitle.setText("New Device");

                textViewTitle.setVisibility(View.VISIBLE);

                textViewSubtitle.setVisibility(View.VISIBLE);

                buttonCreate.setVisibility(View.VISIBLE);

                relativeLayoutSerialNumber.setVisibility(View.VISIBLE);

                textViewSubtitle2.setVisibility(View.VISIBLE);

                textViewSerialNumber.setVisibility(View.VISIBLE);

                editTextSerialNumber.setVisibility(View.VISIBLE);

                relativeLayoutAdvanced.setVisibility(View.VISIBLE);

                textViewProductName.setVisibility(View.VISIBLE);

                editTextProductName.setVisibility(View.VISIBLE);

                textViewHostname.setVisibility(View.VISIBLE);

                editTextHostname.setVisibility(View.VISIBLE);

                relativeLayoutPrefillInformations.setVisibility(View.VISIBLE);

                textViewPrefillInformations.setVisibility(View.VISIBLE);

                buttonPrefillInformations.setVisibility(View.VISIBLE);

                buttonPrefillInformations.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        int devicePort = Integer.parseInt(deviceData.get("devicePort"));

                        Device newDevice = new Device(0,deviceData.get("deviceIp"),devicePort,deviceData.get("deviceType"),
                                deviceData.get("deviceUsername"),deviceData.get("devicePassword"),
                                deviceData.get("deviceSerialNumber"),deviceData.get("deviceProductName"),
                                deviceData.get("deviceHostname"), projectId,false,false,0,"",false);

                        try {

                            prefillInformation(newDevice);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });

                buttonCreate.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if(checkFields() == true){

                            int devicePort = Integer.parseInt(deviceData.get("devicePort"));

                            boolean deviceMotherStatus = true;

                            boolean deviceDependanceStatus = false;

                            int deviceDependanceId = 0;

                            deviceData.put("deviceSerialNumber",editTextSerialNumber.getText().toString());

                            deviceData.put("deviceProductName",editTextProductName.getText().toString());

                            deviceData.put("deviceHostname",editTextHostname.getText().toString());

                            int deviceId = d.getUniqId(DeviceAddStep2Activity.this);

                            Device device = new Device(deviceId, deviceData.get("deviceIp"),devicePort, deviceData.get("deviceType"),
                                    deviceData.get("deviceUsername"),deviceData.get("devicePassword"),
                                    deviceData.get("deviceSerialNumber"),deviceData.get("deviceProductName"),
                                    deviceData.get("deviceHostname"), projectId,deviceMotherStatus, deviceDependanceStatus, deviceDependanceId,"",false);

                            try {

                                List<Device> devicesAvailable = new ArrayList<Device>();

                                if(Device.isEmptyList(getApplicationContext()) == false){

                                    devicesAvailable = SerializableManager.readSerializable(DeviceAddStep2Activity.this, deviceInstance.devicesAvailablePath);

                                }

                                devicesAvailable.add(device);

                                SerializableManager.saveSerializable(DeviceAddStep2Activity.this, devicesAvailable, deviceInstance.devicesAvailablePath);

                                DeviceActivity.staticDeviceActivity.finish();

                                Intent i = new Intent(DeviceAddStep2Activity.this, DeviceActivity.class);

                                i.putExtra("projectId", projectId);

                                startActivity(i);

                                DeviceAddStep1Activity.staticDeviceAddStep1Activity.finish();

                                finish();

                            }catch(Exception e){

                                Dialog.getDebug(DeviceAddStep2Activity.this,e);

                            }

                        }else{

                            Dialog.getUserInfo(DeviceAddStep2Activity.this,"Some of the fields must be empty ! Please try again");

                        }

                    }
                });

                break;

            case "existing":

                LinearLayout layout = (LinearLayout) findViewById(R.id.layout_existingDevices);

                View topBar = (View) findViewById(R.id.topBar);

                ArrayList<Button> buttonsChooseDevice = new ArrayList<Button>();

                textViewTitle.setText("New Device");

                textViewTitle.setVisibility(View.VISIBLE);

                textViewSubtitle.setText("Choose the device");

                textViewSubtitle.setVisibility(View.VISIBLE);

                scrollView_existingDevices.setVisibility(View.VISIBLE);

                topBar.setVisibility(View.VISIBLE);

                List<Device> devicesAvailable = new ArrayList<Device>();

                if(Device.isEmptyList(getApplicationContext()) == false){

                    devicesAvailable = SerializableManager.readSerializable(DeviceAddStep2Activity.this, deviceInstance.devicesAvailablePath);

                    for(Device deviceAvailable:devicesAvailable){

                        if(deviceAvailable.getDeviceProjectId() == projectId && deviceAvailable.getDeviceMotherStatus() == true){

                            final Button buttonChooseDevice = new Button(DeviceAddStep2Activity.this);

                            final Device existingDevice = deviceAvailable;

                            buttonChooseDevice.setId(existingDevice.getDeviceId());

                            buttonChooseDevice.setBackgroundResource(R.drawable.item_list_border);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                buttonChooseDevice.setStateListAnimator(null);
                            }

                            buttonChooseDevice.setText(Html.fromHtml("&nbsp&nbsp&nbsp&nbsp&nbsp " + existingDevice.getDeviceHostname() + "<br/>&nbsp&nbsp&nbsp&nbsp&nbsp " + existingDevice.getDeviceProductName()));

                            buttonChooseDevice.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);

                            buttonChooseDevice.setGravity(Gravity.LEFT );

                            buttonChooseDevice.setTransformationMethod(null);

                            buttonsChooseDevice.add(buttonChooseDevice);

                            layout.addView(buttonChooseDevice);

                            buttonChooseDevice.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    int deviceId = d.getUniqId(DeviceAddStep2Activity.this);

                                    int devicePort = Integer.parseInt(deviceData.get("devicePort"));

                                    boolean deviceMotherStatus = false;

                                    boolean deviceDependanceStatus = true;

                                    int deviceDependanceId = existingDevice.getDeviceId();

                                    String deviceSerialNumber = existingDevice.getDeviceSerialNumber();

                                    String deviceProductName = existingDevice.getDeviceProductName();

                                    String deviceHostname = existingDevice.getDeviceHostname();

                                    List<Device> existingDevices = new ArrayList<Device>();

                                    Device device = new Device(deviceId, deviceData.get("deviceIp"),devicePort, deviceData.get("deviceType"),
                                            deviceData.get("deviceUsername"),deviceData.get("devicePassword"),
                                            deviceSerialNumber, deviceProductName,
                                            deviceHostname, projectId,deviceMotherStatus,deviceDependanceStatus,deviceDependanceId,"",false);

                                    existingDevices = SerializableManager.readSerializable(DeviceAddStep2Activity.this, deviceInstance.devicesAvailablePath);

                                    existingDevices.add(device);

                                    SerializableManager.saveSerializable(DeviceAddStep2Activity.this, existingDevices, deviceInstance.devicesAvailablePath);

                                    DeviceActivity.staticDeviceActivity.finish();

                                    Intent i = new Intent(DeviceAddStep2Activity.this, DeviceActivity.class);

                                    i.putExtra("projectId", projectId);

                                    startActivity(i);

                                    DeviceAddStep1Activity.staticDeviceAddStep1Activity.finish();

                                    finish();


                                }
                            });
                        }

                    }


                }else{

                    Dialog.getUserInfo(DeviceAddStep2Activity.this, "No devices found");

                }

                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add_step2);

        initControllers();

    }

}
