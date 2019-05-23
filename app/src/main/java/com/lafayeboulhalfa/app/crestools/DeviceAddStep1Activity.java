package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DeviceAddStep1Activity extends AppCompatActivity {

    public static Activity staticDeviceAddStep1Activity;

    public Device deviceInstance;

    String deviceType;

    public boolean checkFields(){

        boolean isCheckedNone = ((CheckBox) findViewById(R.id.checkBoxSecurityNone)).isChecked();

        boolean isCheckedSsl = ((CheckBox) findViewById(R.id.checkBoxSecuritySSL)).isChecked();

        boolean isCheckedSsh = ((CheckBox) findViewById(R.id.checkBoxSecuritySSH)).isChecked();

        EditText editTextIp = (EditText) findViewById(R.id.editTextIp);

        EditText editTextPort = (EditText) findViewById(R.id.editTextPort);

        if(isCheckedNone == true && editTextIp.getText().toString().trim().length() > 0 && editTextPort.getText().toString().trim().length() > 0
                || isCheckedSsl == true && editTextIp.getText().toString().trim().length() > 0 && editTextPort.getText().toString().trim().length() > 0
                || isCheckedSsh == true && editTextIp.getText().toString().trim().length() > 0 && editTextPort.getText().toString().trim().length() > 0){

            return true;

        }else{

            return false;

        }

    }

    public void enableAuthenticationFields(){

        RelativeLayout relativeLayoutAuthenticationContent = (RelativeLayout) findViewById(R.id.relativeLayoutAuthenticationContent);

        TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);

        EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);

        TextView textViewPassword = (TextView) findViewById(R.id.textViewPassword);

        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextUsername.setText("crestron");

        relativeLayoutAuthenticationContent.setVisibility(View.VISIBLE);

        textViewUsername.setVisibility(View.VISIBLE);

        editTextUsername.setVisibility(View.VISIBLE);

        textViewPassword.setVisibility(View.VISIBLE);

        editTextPassword.setVisibility(View.VISIBLE);

    }

    public void disableAuthenticationFields(){

        RelativeLayout relativeLayoutAuthenticationContent = (RelativeLayout) findViewById(R.id.relativeLayoutAuthenticationContent);

        TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);

        EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);

        TextView textViewPassword = (TextView) findViewById(R.id.textViewPassword);

        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextUsername.setText("");

        editTextPassword.setText("");

        relativeLayoutAuthenticationContent.setVisibility(View.INVISIBLE);

        textViewUsername.setVisibility(View.INVISIBLE);

        editTextUsername.setVisibility(View.INVISIBLE);

        textViewPassword.setVisibility(View.INVISIBLE);

        editTextPassword.setVisibility(View.INVISIBLE);

    }

    public void setPort(String port){

        EditText editTextPort = (EditText) findViewById(R.id.editTextPort);

        editTextPort.setText(port);

    }

    public void syncControllers(){

        RelativeLayout relativeLayoutAuthenticationContent = (RelativeLayout) findViewById(R.id.relativeLayoutAuthenticationContent);

        TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);

        EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);

        TextView textViewPassword = (TextView) findViewById(R.id.textViewPassword);

        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        CheckBox checkBoxSecurityNone = (CheckBox) findViewById(R.id.checkBoxSecurityNone);

        relativeLayoutAuthenticationContent.setVisibility(View.INVISIBLE);

        textViewUsername.setVisibility(View.INVISIBLE);

        editTextUsername.setVisibility(View.INVISIBLE);

        textViewPassword.setVisibility(View.INVISIBLE);

        editTextPassword.setVisibility(View.INVISIBLE);

        checkBoxSecurityNone.setChecked(true);

        setPort("41795");

        deviceType = "CTP";

    }

    public void openActivityNewDevice(int projectId, Map deviceData){

        String typeDeviceAssociation = "new";

        Intent i = new Intent(DeviceAddStep1Activity.this, DeviceAddStep2Activity.class);

        i.putExtra("projectId", projectId);

        i.putExtra("typeDeviceAssociation", typeDeviceAssociation);

        i.putExtra("deviceData", (Serializable) deviceData);

        startActivity(i);

    }

    public void openActivityExistingDevice(int projectId, Map deviceData){

        if(Device.isEmptyListByProject(getApplicationContext(), projectId) == true){

            Dialog.getUserInfo(DeviceAddStep1Activity.this,  "You have no devices in this project !");

        }else{

            String typeDeviceAssociation = "existing";

            Intent i = new Intent(DeviceAddStep1Activity.this, DeviceAddStep2Activity.class);

            i.putExtra("projectId", projectId);

            i.putExtra("typeDeviceAssociation", typeDeviceAssociation);

            i.putExtra("deviceData", (Serializable) deviceData);

            startActivity(i);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void getUserPopup(Activity activity, View view, final int projectId, final Map deviceData) {

        Dialog.hideKeyboard(activity);

        final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();

        Dialog.applyDim(root, 0.5f);

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        // inflate the layout of the popup window
        View popupView = inflater.inflate(R.layout.static_popup, null);

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

        Button buttonNewDevice;

        Button buttonExistingDevice;

        buttonNewDevice = popupView.findViewById(R.id.buttonNewDevice);

        buttonExistingDevice = popupView.findViewById(R.id.buttonExistingDevice);

        buttonNewDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivityNewDevice(projectId, deviceData);

                popupWindow.dismiss();

            }
        });

        buttonExistingDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivityExistingDevice(projectId, deviceData);

                popupWindow.dismiss();

            }
        });

    }

    public void initControllers(){

        Intent i = getIntent();

        final int projectId = i.getIntExtra("projectId", 0);

        final ImageButton buttonCancel = (ImageButton) findViewById(R.id.imageButtonLeft);

        final ImageButton buttonNext = (ImageButton) findViewById(R.id.imageButtonRight);

        final EditText editTextIp = (EditText) findViewById(R.id.editTextIp);

        final EditText editTextPort = (EditText) findViewById(R.id.editTextPort);

        final CheckBox checkBoxSecurityNone = (CheckBox) findViewById(R.id.checkBoxSecurityNone);

        final CheckBox checkBoxSecuritySSL = (CheckBox) findViewById(R.id.checkBoxSecuritySSL);

        final CheckBox checkBoxSecuritySSH = (CheckBox) findViewById(R.id.checkBoxSecuritySSH);

        final EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);

        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        syncControllers();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });

        checkBoxSecurityNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = ((CheckBox) findViewById(R.id.checkBoxSecurityNone)).isChecked();

                if(isChecked == true){

                    deviceType = "CTP";

                    setPort("41795");

                    checkBoxSecuritySSL.setChecked(false);

                    checkBoxSecuritySSH.setChecked(false);

                    disableAuthenticationFields();

                }

            }
        });

        checkBoxSecuritySSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = ((CheckBox) findViewById(R.id.checkBoxSecuritySSL)).isChecked();

                if(isChecked == true){

                    deviceType = "SSL";

                    setPort("41797");

                    checkBoxSecurityNone.setChecked(false);

                    checkBoxSecuritySSH.setChecked(false);

                    disableAuthenticationFields();

                }


            }
        });

        checkBoxSecuritySSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean is_checked = ((CheckBox) findViewById(R.id.checkBoxSecuritySSH)).isChecked();

                if(is_checked == true){

                    deviceType = "SSH";

                    setPort("22");

                    checkBoxSecurityNone.setChecked(false);

                    checkBoxSecuritySSL.setChecked(false);

                    enableAuthenticationFields();

                }

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            public void onClick(View v) {

                final Map<String, String> deviceData = new HashMap<String, String>();

                deviceData.put("deviceIp", editTextIp.getText().toString());

                deviceData.put("devicePort", editTextPort.getText().toString());

                deviceData.put("deviceType", deviceType);

                deviceData.put("deviceUsername", editTextUsername.getText().toString());

                deviceData.put("devicePassword", editTextPassword.getText().toString());

                if(checkFields() == true){

                    getUserPopup(DeviceAddStep1Activity.this, v, projectId, deviceData);

                }else{

                    Dialog.getUserInfo(DeviceAddStep1Activity.this,"Some of the fields were not completed ! Please try again.");

                }

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add_step1);

        initControllers();

        staticDeviceAddStep1Activity = this;

    }

}
