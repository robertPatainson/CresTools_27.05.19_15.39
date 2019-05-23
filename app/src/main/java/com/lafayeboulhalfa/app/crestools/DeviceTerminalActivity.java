package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class DeviceTerminalActivity extends AppCompatActivity {

    public static Activity staticDeviceTerminalActivity;

    public class DeviceReceiver extends BroadcastReceiver {

        final EditText editTextCommand = (EditText) findViewById(R.id.editTextCommand);

        final Intent service = new Intent(DeviceTerminalActivity.this, SocketService.class);

        public void setServiceControllersOn(final Intent _service){

            Button buttonAddCommand = (Button) findViewById(R.id.buttonAddCommand);

            TextView textViewSubtitle = (TextView) findViewById(R.id.textViewSubtitle);

            final ImageButton imageButtonGoBottom = (ImageButton) findViewById(R.id.imageButtonGoBottom);

            final TextView textViewOutput = (TextView) findViewById(R.id.textViewOutput);

            textViewSubtitle.setText(Html.fromHtml("<small>" + "<font color=#FFFFFF>" + "connected" + "</font></small>"));

            editTextCommand.setEnabled(true);

            editTextCommand.setHint(" Enter Command...");

            editTextCommand.setHintTextColor(getResources().getColor(R.color.Gray));

            editTextCommand.setCursorVisible(true);

            editTextCommand.setBackgroundResource(R.drawable.edittext);

            buttonAddCommand.setEnabled(true);

            buttonAddCommand.setTextColor(getResources().getColor(R.color.colorPrimary));


            for(int i = 1; i <= commandInstance.getLastId(DeviceTerminalActivity.this); i++){

                Button buttonCommand = (Button)findViewById(i);

                if(buttonCommand != null){

                    buttonCommand.setEnabled(true);

                    buttonCommand.setBackgroundResource(R.drawable.button_command);

                }

            }

        }

        public void setServiceControllersOff(){

            scrollToBottom();

            TextView textViewSubtitle = (TextView) findViewById(R.id.textViewSubtitle);

            Button buttonAddCommand = (Button) findViewById(R.id.buttonAddCommand);

            textViewSubtitle.setText(Html.fromHtml("<small>" + "<font color=#808080>" + "disconnected" + "</font></small>"));

            editTextCommand.setEnabled(false);

            editTextCommand.getBackground().setColorFilter(getResources().getColor(R.color.colorDisabled), PorterDuff.Mode.SRC_ATOP);

            editTextCommand.setHint(" No Connection");

            editTextCommand.setHintTextColor(getResources().getColor(R.color.White));

            editTextCommand.setCursorVisible(false);

            buttonAddCommand.setEnabled(false);

            buttonAddCommand.setTextColor(getResources().getColor(R.color.colorDisabled));

            for(int i = 1; i <= commandInstance.getLastId(DeviceTerminalActivity.this); i++){

                Button buttonCommand = (Button)findViewById(i);

                if(buttonCommand != null){

                    buttonCommand.setEnabled(false);

                    buttonCommand.setBackgroundResource(R.drawable.button_command_disabled);

                }

            }

        }

        public void updateOutput(Intent arg1){

            final TextView textViewOutput = (TextView) findViewById(R.id.textViewOutput);

            String dataReceived = arg1.getExtras().getString("data");

            textViewOutput.append(dataReceived);

            scrollToBottom();

        }

        public boolean checkDevice(Intent arg1){

            int currentDeviceId = device.getDeviceId();

            int deviceReceiverId = arg1.getIntExtra("deviceId", 0);

            if(currentDeviceId == deviceReceiverId){

                return true;

            }

            return false;

        }

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            Intent i = getIntent();

            device = (Device) i.getExtras().getSerializable("storedDevice");

            int projectId = i.getIntExtra("projectId", 0);

            service.putExtra("storedDevice", device);

            service.putExtra("projectId", projectId);

            String typeData = arg1.getAction();

            switch(typeData){

                case "didReceiveData":

                    if(checkDevice(arg1) == true)
                        updateOutput(arg1);

                    break;

                case "didDisconnect":

                    if(checkDevice(arg1) == true)
                        setServiceControllersOff();

                    break;

                case "didConnect":

                    setServiceControllersOn(service);

                    initPrompt(service);

                    break;

                case "errorHandler":

                    updateOutput(arg1);

                    break;

                case "loadHandler":

                    break;

                case "exceptionBrokenPipe":

                    String deviceAddress = arg1.getExtras().getString("deviceAddress");

                    String currentDeviceAddress = device.getDeviceIp() + ":" + device.getDevicePort();

                    if(deviceAddress.equals(currentDeviceAddress)){

                        Dialog.getUserError(DeviceTerminalActivity.this, "Connection lost with " + currentDeviceAddress);

                        setServiceControllersOff();

                    }

                    break;

            }



        }

    }

    public DeviceReceiver deviceReceiver;

    public Device device;

    public Device deviceInstance;

    public Command commandInstance;

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void addCommandTextView(String command){

        TextView textViewOutput = (TextView) findViewById(R.id.textViewOutput);

        textViewOutput.append(Html.fromHtml("<strong>" + command + "</strong><br>"));

    }

    public void sendCommand(String _command, Intent service, boolean isDisplayed){

        if(!_command.trim().equals("")){ // preventing to send prompt because asking prompt restarts echo on ?

            if(isDisplayed == true)
                addCommandTextView(_command);

            EditText editTextCommand = (EditText) findViewById(R.id.editTextCommand);

            String command = _command;

            String connectionAction = null;

            service.putExtra("connectionAction", connectionAction);

            service.putExtra("command", command);

            startService(service);

            editTextCommand.setText("");

            scrollToBottom();

        }

    }

    public void initPrompt(Intent service){

        sendCommand("echo off", service, false);

    }

    public void initMenu(){

        final TextView textViewSubtitle = (TextView) findViewById(R.id.textViewSubtitle);

        final RelativeLayout relativeLayoutSubtitle = (RelativeLayout) findViewById(R.id.relativeLayoutSubtitle);

        final Button buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);

        final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);

        final Button buttonClear = (Button) findViewById(R.id.buttonClear);

        boolean isConnected = true;

        if(textViewSubtitle.getText().toString().contains("disconnected"))
            isConnected = false;

        if(isConnected == true){

            buttonConnect.setVisibility(View.INVISIBLE);

            buttonDisconnect.setVisibility(View.VISIBLE);

            buttonClear.setVisibility(View.VISIBLE);

        }else if(isConnected == false){

            buttonConnect.setVisibility(View.VISIBLE);

            buttonDisconnect.setVisibility(View.INVISIBLE);

            buttonClear.setVisibility(View.INVISIBLE);

        }

    }

    public void hideMenu(){

        final TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        final RelativeLayout relativeLayoutSubtitle = (RelativeLayout) findViewById(R.id.relativeLayoutSubtitle);

        AnimationManager resizeAnimation = new AnimationManager(
                relativeLayoutSubtitle,
                0,
                0
        );

        resizeAnimation.setDuration(250);

        relativeLayoutSubtitle.startAnimation(resizeAnimation);

        textViewTitle.setText(Html.fromHtml("<strong>" + device.getDeviceHostname() + "</strong><font color=#d9d9d9>&#x25BC</font>"));

    }

    public void showMenu(){

        final TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        final RelativeLayout relativeLayoutSubtitle = (RelativeLayout) findViewById(R.id.relativeLayoutSubtitle);

        float dip = 50f;

        Resources r = getResources();

        float pix = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );

        int px = Math.round(pix);
        
        AnimationManager resizeAnimation = new AnimationManager(
                relativeLayoutSubtitle,
                px,
                0
        );

        resizeAnimation.setDuration(250);

        relativeLayoutSubtitle.startAnimation(resizeAnimation);

        textViewTitle.setText(Html.fromHtml("<strong>" + device.getDeviceHostname() + "</strong><font color=#d9d9d9>&#x25B2</font>"));

    }

    public void initTextViewOutput(Intent service){

        final ScrollView scrollViewOutput = (ScrollView) findViewById(R.id.scrollViewOutput);

        TextView textViewOutput = (TextView) findViewById(R.id.textViewOutput);

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = SerializableManager.readSerializable(getApplicationContext(), deviceInstance.devicesAvailablePath);

        String [] output;

        for (Device d : devicesAvailable)
        {

            if(d.getDeviceId() == device.getDeviceId()){

                if(!d.getDeviceserializedOutput().equals("")){

                    output = d.getDeviceserializedOutput().split("\n");

                    StringBuilder builder = new StringBuilder();

                    boolean isPrompt;

                    if(output.length > 0){

                        for(String s : output){

                            if(s.toLowerCase().indexOf(">") != -1 ){

                                String prompt = "";

                                String command = "";

                                int start = s.toLowerCase().indexOf(">") + 1;

                                int end = s.length();

                                prompt = s.substring(0, start);

                                command = s.substring(start, end);

                                s = "<br>" + prompt + "<strong>" + command + "</strong>";

                            }else{

                                s = "<br>" + s;

                            }

                            builder.append(s);

                        }

                        builder.append("<br><br>");

                        String strOutput = builder.toString();

                        textViewOutput.setText(Html.fromHtml(strOutput));

                    }

                }

            }

        }

        scrollViewOutput.post(new Runnable() {
            @Override
            public void run() {
                scrollViewOutput.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    public void scrollToBottom(){

        final ScrollView scrollViewOutput = (ScrollView) findViewById(R.id.scrollViewOutput);

        ImageButton imageButtonGoBottom = (ImageButton) findViewById(R.id.imageButtonGoBottom);

        scrollViewOutput.post(new Runnable() {
            @Override
            public void run() {

                scrollViewOutput.fullScroll(ScrollView.FOCUS_DOWN);

            }
        });

        imageButtonGoBottom.setVisibility(View.INVISIBLE);

    }

    public void initCommands(final Intent service){

        List<Command> commandsAvailable = commandInstance.getListCommands(DeviceTerminalActivity.this);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutButtonsCommands);

        ArrayList<Button> buttonsCommand = new ArrayList<Button>();

        layout.removeViewsInLayout(0, layout.getChildCount());

        if(commandInstance.isEmptyList(DeviceTerminalActivity.this) == false){

            TextView alreadyTextView = (TextView)findViewById(0);

            if(alreadyTextView != null){

                layout.removeView(alreadyTextView);

            }

            for(final Command commandAvailable:commandsAvailable){

                int idCommand = commandAvailable.getIdCommand();

                String command = commandAvailable.getContentCommand();

                final Button buttonCommand = new Button(DeviceTerminalActivity.this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

                params.topMargin = 15;

                params.bottomMargin = 15;

                buttonCommand.setId(idCommand);

                buttonCommand.setText(command);

                buttonCommand.setBackgroundResource(R.drawable.button_command);

                buttonCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                buttonCommand.setTextColor(Color.parseColor("#ffffff"));

                buttonCommand.setGravity(Gravity.CENTER);

                buttonCommand.setPadding(50,0,50,0);

                buttonCommand.setLayoutParams(params);

                buttonCommand.setTransformationMethod(null);

                buttonsCommand.add(buttonCommand);

                layout.addView(buttonCommand);

                buttonCommand.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        String command = buttonCommand.getText().toString();

                        sendCommand(command, service, true);

                    }
                });

                buttonCommand.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        // TODO Auto-generated method stub

                        List<Command> commands = commandInstance.getListCommands(DeviceTerminalActivity.this);

                        for(Command command:commands){

                            if(command.getIdCommand() == buttonCommand.getId()){

                                command.removeCommand(DeviceTerminalActivity.this);

                                ViewGroup layout = (ViewGroup) buttonCommand.getParent();

                                if(null!=layout) //for safety only  as you are doing onClick
                                    layout.removeView(buttonCommand);

                                if(layout.getChildCount() == 0){

                                    TextView textViewCommand = new TextView(DeviceTerminalActivity.this);

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );

                                    params.setMargins(10,20,10,0);

                                    textViewCommand.setLayoutParams(params);

                                    textViewCommand.setId(0);

                                    textViewCommand.setText("No commands added yet");

                                    textViewCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

                                    textViewCommand.setGravity(Gravity.CENTER | Gravity.BOTTOM);

                                    textViewCommand.setTransformationMethod(null);

                                    layout.addView(textViewCommand);

                                }

                            }

                        }

                        return true;

                    }

                });

            }

        }else{

            TextView textViewCommand = new TextView(DeviceTerminalActivity.this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(10,20,10,0);

            textViewCommand.setLayoutParams(params);

            textViewCommand.setId(0);

            textViewCommand.setText("No commands added yet");

            textViewCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            textViewCommand.setGravity(Gravity.CENTER | Gravity.BOTTOM);

            textViewCommand.setTransformationMethod(null);

            layout.addView(textViewCommand);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initControllers(final Intent service){

        final TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        final TextView textViewSubtitle = (TextView) findViewById(R.id.textViewSubtitle);

        final ImageButton imageButtonPrevious = (ImageButton) findViewById(R.id.imageButtonLeft);

        final ImageButton imageButtonEditCommands = (ImageButton) findViewById(R.id.imageButtonEditCommands);

        final ImageButton imageButtonEditDevice = (ImageButton) findViewById(R.id.imageButtonRight);

        final RelativeLayout relativeLayoutSubtitle = (RelativeLayout) findViewById(R.id.relativeLayoutSubtitle);

        final Button buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);

        final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);

        final Button buttonClear = (Button) findViewById(R.id.buttonClear);

        final ScrollView scrollViewOutput = (ScrollView) findViewById(R.id.scrollViewOutput);

        final TextView textViewOutput = (TextView) findViewById(R.id.textViewOutput);

        final EditText editTextCommand = (EditText) findViewById(R.id.editTextCommand);

        final Button buttonAddCommand = (Button) findViewById(R.id.buttonAddCommand);

        final ImageButton imageButtonGoBottom = (ImageButton) findViewById(R.id.imageButtonGoBottom);

        Intent i = getIntent();

        device = (Device) i.getExtras().getSerializable("storedDevice");

        textViewTitle.setText(Html.fromHtml("<strong>" + device.getDeviceHostname() + "</strong><font color=#d9d9d9>&#x25BC</font>"));

        textViewSubtitle.setText(Html.fromHtml("<small>" + "<font color=#FFFFFF>" + "connected" + "</font></small>"));

        hideMenu();

        initTextViewOutput(service);

        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });

        textViewTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                initMenu();

                String status = textViewTitle.getText().toString();

                boolean isDown = false;

                if(status.contains("▲"))
                    isDown = true;

                if(isDown == false){

                    showMenu();

                }else{

                    hideMenu();

                }

            }

        });

        textViewSubtitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                initMenu();

                String status = textViewTitle.getText().toString();

                boolean isDown = false;

                if(status.contains("▲"))
                    isDown = true;

                if(isDown == false){

                    showMenu();

                }else{

                    hideMenu();

                }

            }

        });

        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                hideMenu();

                String fakeCommand = null;

                service.putExtra("connectionAction", "disconnect");

                service.putExtra("command", fakeCommand);

                startService(service);

                textViewOutput.append(Html.fromHtml("<i>disconnected</i><br><br>"));

            }
        });

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                hideMenu();

                Intent i = getIntent();

                device = (Device) i.getExtras().getSerializable("storedDevice");

                final Intent service = new Intent(DeviceTerminalActivity.this, SocketService.class);

                service.putExtra("storedDevice", device);

                service.putExtra("projectId", device.getDeviceProjectId());

                IntentFilter intentFilter = new IntentFilter();

                intentFilter.addAction("loadHandler");

                intentFilter.addAction("didConnect");

                intentFilter.addAction("didReceiveData");

                intentFilter.addAction("errorHandler");

                intentFilter.addAction("didDisconnect");

                startService(service);

            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                hideMenu();

                String output = "";

                textViewOutput.setText(output);

                device.serializeDeviceOutput(DeviceTerminalActivity.this, output);

                imageButtonGoBottom.setVisibility(View.INVISIBLE);

                // sendCommand("", service, false); asking prompt starts echo off ?

            }
        });

        scrollViewOutput.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                View view = (View) scrollViewOutput.getChildAt(0);

                int diff = (view.getBottom() - (scrollViewOutput.getHeight() + scrollViewOutput.getScrollY()));

                if (diff > 1000) {

                    imageButtonGoBottom.setVisibility(View.VISIBLE);

                }else{

                    imageButtonGoBottom.setVisibility(View.INVISIBLE);

                }

            }
        });

        imageButtonEditCommands.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(DeviceTerminalActivity.this, CommandsActivity.class);

                i.putExtra("storedDevice", device);

                startActivity(i);

            }
        });

        imageButtonEditDevice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(DeviceTerminalActivity.this, DeviceEditActivity.class);

                i.putExtra("projectId", device.getDeviceProjectId());

                i.putExtra("storedDevice", device);

                startActivity(i);

            }
        });

        buttonAddCommand.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int idCommand = commandInstance.getUniqId(DeviceTerminalActivity.this);

                String contentCommand = editTextCommand.getText().toString();

                if(contentCommand != null && !contentCommand.equals("")){

                    Command command = new Command(idCommand, contentCommand);

                    commandInstance.addCommand(DeviceTerminalActivity.this, command);

                    initCommands(service);

                }

            }
        });

        imageButtonGoBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                imageButtonGoBottom.setVisibility(View.INVISIBLE);

                scrollToBottom();

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initService(){

        final EditText editTextCommand = (EditText) findViewById(R.id.editTextCommand);

        Intent i = getIntent();

        device = (Device) i.getExtras().getSerializable("storedDevice");

        int projectId = i.getIntExtra("projectId", 0);

        final Intent service = new Intent(DeviceTerminalActivity.this, SocketService.class);

        service.putExtra("storedDevice", device);

        service.putExtra("projectId", projectId);

        deviceReceiver = new DeviceReceiver();

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("loadHandler");

        intentFilter.addAction("didConnect");

        intentFilter.addAction("didReceiveData");

        intentFilter.addAction("errorHandler");

        intentFilter.addAction("didDisconnect");

        intentFilter.addAction("exceptionBrokenPipe");

        registerReceiver(deviceReceiver, intentFilter);

        deviceReceiver.setServiceControllersOn(service);

        editTextCommand.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String command = editTextCommand.getText().toString();

                    sendCommand(command, service, true);

                    return true;

                }
                return false;
            }
        });

        initControllers(service);

        initCommands(service);

        initPrompt(service);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void loadActivity(){

        initService();

    }

    public void stopActivity(){

        try{

            if(isServiceRunning(SocketService.class) == true){

                stopService(new Intent(DeviceTerminalActivity.this, SocketService.class));

            }

            if(deviceReceiver != null)
                unregisterReceiver(deviceReceiver);

        }catch(Exception e){



        }

        final TextView textViewOutput = (TextView) findViewById(R.id.textViewOutput);

        String output = textViewOutput.getText().toString();

        Intent i = getIntent();

        device = (Device) i.getExtras().getSerializable("storedDevice");

        if(device != null)
            device.serializeDeviceOutput(DeviceTerminalActivity.this, output);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_terminal);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {
        super.onStart();

        loadActivity();

        staticDeviceTerminalActivity = this;

    }

    @Override
    protected void onStop() {
        super.onStop();

        stopActivity();

    }

}
