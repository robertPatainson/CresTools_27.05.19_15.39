package com.lafayeboulhalfa.app.crestools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommandsActivity extends AppCompatActivity {

    Command commandInstance;

    public List<Command> toDelete;

    public Device device;

    public void initControllers(){

        Intent i = getIntent();

        device = (Device) i.getExtras().getSerializable("storedDevice");

        final ImageButton imageButtonPrevious = (ImageButton) findViewById(R.id.imageButtonLeft);

        final ImageButton buttonEdit = (ImageButton) findViewById(R.id.imageButtonRight);

        final ImageButton imageButtonDeleteCommands = (ImageButton) findViewById(R.id.imageButtonDeleteCommands);

        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();

                DeviceTerminalActivity.staticDeviceTerminalActivity.finish();

                Intent i = new Intent(CommandsActivity.this, DeviceTerminalActivity.class);

                i.putExtra("storedDevice", device);

                i.putExtra("projectId", device.getDeviceProjectId());

                startActivity(i);

            }
        });

        imageButtonDeleteCommands.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(toDelete != null){

                    if(toDelete.isEmpty()){

                        Dialog.getUserError(CommandsActivity.this, "No commands to delete");

                    }else{

                        for(Command commandToDelete : toDelete){

                            commandToDelete.removeCommand(CommandsActivity.this);

                        }

                        Intent intent = getIntent();

                        finish();

                        startActivity(intent);

                    }

                }

            }
        });

    }

    public void initCommands(){

        toDelete = new ArrayList<Command>();

        List<Command> commandsAvailable = commandInstance.getListCommands(CommandsActivity.this);

        LinearLayout layout_buttonsCommands = (LinearLayout) findViewById(R.id.layout_buttonsCommands);

        ArrayList<Button> buttonsCommand = new ArrayList<Button>();

        if(commandInstance.isEmptyList(CommandsActivity.this) == false){

            TextView alreadyTextView = (TextView)findViewById(0);

            if(alreadyTextView != null){

                layout_buttonsCommands.removeView(alreadyTextView);

            }

            for(final Command commandAvailable:commandsAvailable){

                int idCommand = commandAvailable.getIdCommand();

                String command = commandAvailable.getContentCommand();

                Button alreadyCommand = (Button)findViewById(idCommand);

                if(alreadyCommand == null){

                    final Button buttonCommand = new Button(CommandsActivity.this);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    params.leftMargin = 30;

                    params.topMargin = 15;

                    params.bottomMargin = 15;

                    buttonCommand.setId(idCommand);

                    buttonCommand.setText(command);

                    buttonCommand.setBackgroundResource(R.drawable.button_command_in_list);

                    buttonCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                    buttonCommand.setTextColor(getResources().getColor(R.color.RoyalBlue));

                    buttonCommand.setGravity(Gravity.CENTER);

                    buttonCommand.setPadding(50,0,50,0);

                    buttonCommand.setLayoutParams(params);

                    buttonCommand.setTransformationMethod(null);

                    buttonsCommand.add(buttonCommand);

                    layout_buttonsCommands.addView(buttonCommand);

                    buttonCommand.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            Command currentCommand = commandAvailable;

                            boolean isInList = false;

                            if(toDelete != null){

                                if(toDelete.isEmpty()){

                                    addToList(currentCommand);

                                    buttonCommand.setBackgroundResource(R.drawable.button_command_in_list_selected);

                                    buttonCommand.setTextColor(getResources().getColor(R.color.White));

                                }else{

                                    for(Command commandToDelete : toDelete){

                                        if(commandToDelete.getIdCommand() == currentCommand.getIdCommand())
                                            isInList = true;

                                    }

                                    if(isInList == true){

                                        removeFromList(currentCommand);

                                        buttonCommand.setBackgroundResource(R.drawable.button_command_in_list);

                                        buttonCommand.setTextColor(getResources().getColor(R.color.RoyalBlue));


                                    }else{

                                        addToList(currentCommand);

                                        buttonCommand.setBackgroundResource(R.drawable.button_command_in_list_selected);

                                        buttonCommand.setTextColor(getResources().getColor(R.color.White));

                                    }

                                }

                            }

                        }
                    });

                }

            }

        }else{

            TextView textViewCommand = new TextView(CommandsActivity.this);

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

            layout_buttonsCommands.addView(textViewCommand);

        }


    }

    public void addToList(Command toAdd){

        toDelete.add(toAdd);

    }

    public void removeFromList(Command toRemove){

        toDelete.remove(toRemove);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);

        initControllers();

        initCommands();

    }

}
