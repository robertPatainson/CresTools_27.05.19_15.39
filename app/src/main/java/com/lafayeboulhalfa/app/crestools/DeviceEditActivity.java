package com.lafayeboulhalfa.app.crestools;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeviceEditActivity extends AppCompatActivity {

    public boolean has_dependances = false;

    public Device deviceInstance;

    public Device device;

    public void initControllers(){

        Intent i = getIntent();

        device = (Device) i.getExtras().getSerializable("storedDevice");

        final int projectId = i.getIntExtra("projectId", 0);

        final ImageButton imageButtonPrevious = (ImageButton) findViewById(R.id.imageButtonLeft);

        final ImageButton buttonEdit = (ImageButton) findViewById(R.id.imageButtonRight);

        final TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        final EditText editTextSerialNumber = (EditText) findViewById(R.id.editTextSerialNumber);

        final EditText editTextProductName = (EditText) findViewById(R.id.editTextProductName);

        final EditText editTextHostname = (EditText) findViewById(R.id.editTextHostname);

        final Button buttonDelete = (Button) findViewById(R.id.buttonDelete);

        textViewTitle.setText(device.getDeviceProductName());

        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String serialNumber = String.valueOf(editTextSerialNumber.getText());

                String productName = String.valueOf(editTextProductName.getText());

                String hostname = String.valueOf(editTextHostname.getText());

                try {

                    List<Device> devicesAvailable = new ArrayList<Device>();

                    devicesAvailable = SerializableManager.readSerializable(DeviceEditActivity.this, "devicesAvailable");

                    for (Device d : devicesAvailable)
                    {
                        if(d.getDeviceId() == device.getDeviceId()){

                            d.setDeviceSerialNumber(serialNumber);

                            d.setDeviceProductName(productName);

                            d.setDeviceHostname(hostname);

                        }
                    }

                    SerializableManager.saveSerializable(DeviceEditActivity.this, devicesAvailable, "devicesAvailable");

                    finish();

                } catch (Exception e) {

                    Dialog.getDebug(DeviceEditActivity.this, e);

                }

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder deleteConfirm = new AlertDialog.Builder(DeviceEditActivity.this);

                deleteConfirm.setTitle("Delete confirm");

                deleteConfirm.setMessage("Are you sure to delete this device ?");

                deleteConfirm.setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(device.deviceHasChild(getApplicationContext()) == true){

                                    List<Device> childrenList = Device.getDeviceChildren(getApplicationContext(), device);

                                    for(Device child : childrenList){

                                        SerializableManager.removeDevice(DeviceEditActivity.this, "devicesAvailable", child.getDeviceId());

                                    }

                                    SerializableManager.removeDevice(DeviceEditActivity.this, "devicesAvailable", device.getDeviceId());

                                }else{

                                    SerializableManager.removeDevice(DeviceEditActivity.this, "devicesAvailable", device.getDeviceId());

                                }

                                finish();

                            }
                        });
                deleteConfirm.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {

                                dialog.cancel();

                            }
                        });

                deleteConfirm.create().show();

            }
        });

    }

    public void initAddresses(){

        EditText editTextSerialNumber = (EditText) findViewById(R.id.editTextSerialNumber);

        EditText editTextProductName = (EditText) findViewById(R.id.editTextProductName);

        EditText editTextHostname = (EditText) findViewById(R.id.editTextHostname);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_buttonsDeleteAddresses);

        ArrayList<Button> buttonsDeleteAddresses = new ArrayList<Button>();

        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);

        Intent i = getIntent();

        device = (Device) i.getExtras().getSerializable("storedDevice");

        int projectId = i.getIntExtra("projectId", 0);

        List<Device> devicesAvailable = new ArrayList<Device>();

        List<Device> dependingDevices = new ArrayList<Device>();

        final List<Device> associatedDevices = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(DeviceEditActivity.this, deviceInstance.devicesAvailablePath);

        for (Device d : devicesAvailable)
        {

            if(d.getDeviceMotherStatus() == true){

                if(d.getDeviceId() == device.getDeviceId()){

                    editTextHostname.setText(d.getDeviceHostname());

                    editTextProductName.setText(d.getDeviceProductName());

                    editTextSerialNumber.setText(d.getDeviceSerialNumber());

                }

            }else{

                dependingDevices.add(d);

            }

        }

        if(dependingDevices != null && !dependingDevices.isEmpty()){

            for(Device dependingDevice : dependingDevices){

                if(dependingDevice.getDeviceDependanceId() == device.getDeviceId()){

                    associatedDevices.add(dependingDevice);

                }


            }

            if(associatedDevices != null && !associatedDevices.isEmpty()){

                has_dependances = true;

            }

        }

        if(has_dependances == true){

            final Button buttonDeleteMotherAddress = new Button(DeviceEditActivity.this);

            buttonDeleteMotherAddress.setId(device.getDeviceId());

            buttonDeleteMotherAddress.setBackgroundResource(R.drawable.item_list_border);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                buttonDeleteMotherAddress.setStateListAnimator(null);
            }

            buttonDeleteMotherAddress.setText(device.getDeviceIp() + ":" + device.getDevicePort());

            buttonDeleteMotherAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

            buttonDeleteMotherAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_delete, 0);

            buttonDeleteMotherAddress.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.FireBrick)));

            buttonDeleteMotherAddress.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

            buttonDeleteMotherAddress.setPadding(30,0,0,0);

            buttonDeleteMotherAddress.setTransformationMethod(null);

            buttonsDeleteAddresses.add(buttonDeleteMotherAddress);

            layout.addView(buttonDeleteMotherAddress);

            for(Device associatedDevice : associatedDevices){

                final Button buttonDeleteAssociatedAddress = new Button(DeviceEditActivity.this);

                buttonDeleteAssociatedAddress.setId(associatedDevice.getDeviceId());

                buttonDeleteAssociatedAddress.setBackgroundResource(R.drawable.item_list_border);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    buttonDeleteAssociatedAddress.setStateListAnimator(null);
                }

                buttonDeleteAssociatedAddress.setText(associatedDevice.getDeviceIp() + ":" + associatedDevice.getDevicePort());

                buttonDeleteAssociatedAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                buttonDeleteAssociatedAddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_delete, 0);

                buttonDeleteAssociatedAddress.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.FireBrick)));

                buttonDeleteAssociatedAddress.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

                buttonDeleteAssociatedAddress.setPadding(30,0,0,0);

                buttonDeleteAssociatedAddress.setTransformationMethod(null);

                buttonsDeleteAddresses.add(buttonDeleteAssociatedAddress);

                layout.addView(buttonDeleteAssociatedAddress);

            }

            for(Button buttonDeleteAdressDependance : buttonsDeleteAddresses){

                final String address = buttonDeleteAdressDependance.getText().toString();

                final Device deviceToDelete = deviceInstance.getDeviceByAddress(DeviceEditActivity.this, projectId, address);

                if(deviceToDelete != null){

                    buttonDeleteAdressDependance.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            final AlertDialog.Builder deleteConfirm = new AlertDialog.Builder(DeviceEditActivity.this);

                            deleteConfirm.setTitle("Delete confirm");

                            deleteConfirm.setMessage("Are you sure to delete the address " + address + " ?");

                            deleteConfirm.setPositiveButton("Delete",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            if(deviceToDelete.getDeviceMotherStatus() == false){

                                                SerializableManager.removeDevice(DeviceEditActivity.this, deviceInstance.devicesAvailablePath, deviceToDelete.getDeviceId());

                                            }else if(deviceToDelete.getDeviceMotherStatus() == true){

                                                SerializableManager.removeDevice(DeviceEditActivity.this, deviceInstance.devicesAvailablePath, deviceToDelete.getDeviceId());

                                                List<Device> devicesAvailable = new ArrayList<Device>();

                                                devicesAvailable = SerializableManager.readSerializable(DeviceEditActivity.this, deviceInstance.devicesAvailablePath);

                                                Device newMotherDevice = null;

                                                for (Device d : devicesAvailable)
                                                {

                                                    if(d.getDeviceDependanceId() == deviceToDelete.getDeviceId()){

                                                        d.setDeviceMotherStatus(true);

                                                        d.setDeviceDependanceStatus(false);

                                                        d.setDeviceId(d.getDeviceDependanceId());

                                                        d.setDeviceDependanceId(0);

                                                        device = d;

                                                        break;

                                                    }


                                                }

                                                SerializableManager.saveSerializable(DeviceEditActivity.this, devicesAvailable, deviceInstance.devicesAvailablePath);

                                            }

                                            Intent intent = getIntent();

                                            intent.putExtra("storedDevice", device);

                                            finish();

                                            startActivity(intent);

                                        }
                                    });
                            deleteConfirm.setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id)
                                        {

                                            dialog.cancel();

                                        }
                                    });

                            deleteConfirm.create().show();

                        }
                    });

                }

            }

        }else if(has_dependances == false){

            final Button buttonDeleteAddress = new Button(DeviceEditActivity.this);

            buttonDeleteAddress.setId(device.getDeviceId());

            buttonDeleteAddress.setBackgroundResource(R.drawable.item_list_border);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                buttonDeleteAddress.setStateListAnimator(null);
            }

            buttonDeleteAddress.setText(device.getDeviceIp() + ":" + device.getDevicePort());

            buttonDeleteAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

            buttonDeleteAddress.setPadding(30,0,0,0);

            buttonDeleteAddress.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);

            buttonDeleteAddress.setTransformationMethod(null);

            buttonsDeleteAddresses.add(buttonDeleteAddress);

            layout.addView(buttonDeleteAddress);

            for(Button buttonDeleteAdress : buttonsDeleteAddresses){

                buttonDeleteAdress.setEnabled(false);

            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_edit);

        initControllers();

        initAddresses();

    }

}
