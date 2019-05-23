package com.lafayeboulhalfa.app.crestools;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReportCreateActivity extends AppCompatActivity {

    public Report report;

    public Command commandInstance;

    final ArrayList<String> commands = new ArrayList<String>();

    public void addCommandReport(String command){

        boolean isAlreadyCommand = false;

        command = command.toLowerCase();

        for(String c : commands){

            c = c.toLowerCase();

            if(c.equals(command))
                isAlreadyCommand = true;

        }

        if(isAlreadyCommand == false){

            commands.add(command);

            LinearLayout layout_checkBoxesCommands = findViewById(R.id.layout_checkBoxesCommands);

            if(layout_checkBoxesCommands != null){

                final int childCount = layout_checkBoxesCommands.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    View _v = layout_checkBoxesCommands.getChildAt(i);

                    if(_v instanceof CheckBox){

                        if(((CheckBox) _v).getText().toString().equals(command)){

                            CheckBox checkBoxCustomCommand = (CheckBox) _v;

                            if(checkBoxCustomCommand.isChecked() == false)
                                checkBoxCustomCommand.setChecked(true);

                        }

                    }

                }

            }

            TextView textViewLegend = (TextView) findViewById(R.id.textViewLegend);

            textViewLegend.setVisibility(View.GONE);

            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_ButtonsCustomCommands);

            ArrayList<Button> buttonsCustomCommand = new ArrayList<Button>();

            final Button buttonCustomCommand = new Button(ReportCreateActivity.this);

            buttonCustomCommand.setText(command);

            buttonCustomCommand.setBackgroundResource(R.drawable.button_command);

            buttonCustomCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            buttonCustomCommand.setGravity(Gravity.CENTER);

            buttonCustomCommand.setTextColor(getResources().getColor(R.color.White));

            buttonCustomCommand.setTransformationMethod(null);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.height = 100;

            params.leftMargin = 10;

            params.gravity = Gravity.CENTER_VERTICAL;

            buttonCustomCommand.setPadding(50,15,50,15);

            buttonCustomCommand.setLayoutParams(params);

            buttonsCustomCommand.add(buttonCustomCommand);

            layout.addView(buttonCustomCommand);

            buttonCustomCommand.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Iterator<String> iter = commands.iterator();

                    while (iter.hasNext()) {
                        String str = iter.next();

                        if(str.equals(buttonCustomCommand.getText().toString())){

                            iter.remove();

                            ViewGroup layout = (ViewGroup) buttonCustomCommand.getParent();

                            if(null!=layout){

                                layout.removeView(buttonCustomCommand);

                                LinearLayout layout_checkBoxesCommands = findViewById(R.id.layout_checkBoxesCommands);

                                if(layout_checkBoxesCommands != null){

                                    final int childCount = layout_checkBoxesCommands.getChildCount();

                                    for (int i = 0; i < childCount; i++) {
                                        View _v = layout_checkBoxesCommands.getChildAt(i);

                                        if(_v instanceof CheckBox){

                                            if(((CheckBox) _v).getText().toString().equals(buttonCustomCommand.getText().toString())){

                                                CheckBox checkBoxCustomCommand = (CheckBox) _v;

                                                checkBoxCustomCommand.setChecked(false);

                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }

                }
            });

        }

    }

    public void removeCommandReport(Command command){

        Iterator<String> iter = commands.iterator();

        while (iter.hasNext()) {
            String str = iter.next();

            if(str.equals(command.getContentCommand())){

                iter.remove();

                LinearLayout layout = findViewById(R.id.layout_ButtonsCustomCommands);

                if(layout != null){

                    final int childCount = layout.getChildCount();

                    for (int i = 0; i < childCount; i++) {
                        View v = layout.getChildAt(i);

                        if(v instanceof Button){

                            if(((Button) v).getText().toString().equals(str)){

                                Button buttonCustomCommand = (Button) v;

                                layout.removeView(buttonCustomCommand);

                            }

                        }

                    }

                }

            }

        }

    }

    public void initControllers(){

        final Report r = new Report();

        Intent i = getIntent();

        final int projectId = (int) i.getExtras().getSerializable("projectId");

        final String typeReport = (String) i.getExtras().getSerializable("typeReport");

        final ImageButton buttonCancel = (ImageButton) findViewById(R.id.imageButtonLeft);

        final ImageButton buttonCreateReport = (ImageButton) findViewById(R.id.imageButtonRight);

        final TextView textViewLegend = (TextView) findViewById(R.id.textViewLegend);

        final Button buttonAddCommand = (Button) findViewById(R.id.buttonAddCommand);

        final EditText editTextReportName = (EditText) findViewById(R.id.editTextReportName);

        final EditText editTextAddCommand = (EditText) findViewById(R.id.editTextAddCommand);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        if((typeReport).equals("custom")){

            textViewLegend.setVisibility(View.GONE);

            report = (Report) i.getExtras().getSerializable("storedReport");

            final String[] commandsReport = report.getCommandsReport();

            editTextReportName.setText(report.getNameReport());

            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_ButtonsCustomCommands);

            ArrayList<Button> buttonsCustomCommand = new ArrayList<Button>();

            for(String commandReport:commandsReport){

                commands.add(commandReport);

                final String command = commandReport;

                final Button buttonCustomCommand = new Button(ReportCreateActivity.this);

                buttonCustomCommand.setText(commandReport);

                buttonCustomCommand.setBackgroundResource(R.drawable.button_command);

                buttonCustomCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

                buttonCustomCommand.setGravity(Gravity.CENTER);

                buttonCustomCommand.setTextColor(getResources().getColor(R.color.White));

                buttonCustomCommand.setTransformationMethod(null);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                params.height = 100;

                params.leftMargin = 10;

                params.gravity = Gravity.CENTER_VERTICAL;

                buttonCustomCommand.setPadding(50,15,50,15);

                buttonCustomCommand.setLayoutParams(params);

                buttonsCustomCommand.add(buttonCustomCommand);

                layout.addView(buttonCustomCommand);

                buttonCustomCommand.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if(command.equals(buttonCustomCommand.getText().toString())){

                            commands.remove(buttonCustomCommand.getText().toString());

                            ViewGroup layout = (ViewGroup) buttonCustomCommand.getParent();

                            if(null!=layout) //for safety only  as you are doing onClick
                                layout.removeView(buttonCustomCommand);

                            LinearLayout layout_checkBoxesCommands = findViewById(R.id.layout_checkBoxesCommands);

                            if(layout_checkBoxesCommands != null){

                                final int childCount = layout_checkBoxesCommands.getChildCount();

                                for (int i = 0; i < childCount; i++) {
                                    View _v = layout_checkBoxesCommands.getChildAt(i);

                                    if(_v instanceof CheckBox){

                                        if(((CheckBox) _v).getText().toString().equals(buttonCustomCommand.getText().toString())){

                                            CheckBox checkBoxCustomCommand = (CheckBox) _v;

                                            checkBoxCustomCommand.setChecked(false);

                                        }

                                    }

                                }

                            }
                        }

                    }
                });

            }

            buttonAddCommand.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(editTextAddCommand.getText().toString().trim().length() > 0){

                        String command = editTextAddCommand.getText().toString();

                        addCommandReport(command);

                        editTextAddCommand.setText("");

                    }

                }

            });

            buttonCreateReport.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try{

                        int reportId = report.getIdReport();

                        String reportName = editTextReportName.getText().toString();

                        String[] commandsReport = commands.toArray(new String[0]);

                        if(!commands.isEmpty()){

                            try {

                                List<Report> reportsAvailable = new ArrayList<Report>();

                                reportsAvailable = SerializableManager.readSerializable(ReportCreateActivity.this, "reportsAvailable");

                                for(Report reportAvailable:reportsAvailable){

                                    if(reportAvailable.getIdReport() == reportId){

                                        reportAvailable.setNameReport(reportName);

                                        reportAvailable.setCommandsReport(commandsReport);

                                    }

                                }

                                SerializableManager.saveSerializable(ReportCreateActivity.this, reportsAvailable, "reportsAvailable");

                                finish();

                            }catch(Exception e){
                                Dialog.getDebug(ReportCreateActivity.this,e);
                            }

                        }

                    }catch(Exception e){

                        Dialog.getDebug(ReportCreateActivity.this,e);

                    }

                }

            });

        }else if((typeReport).equals("new")){

            buttonCreateReport.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(editTextReportName.getText().toString().trim().length() > 0){

                        try{

                            int reportId = r.setIdReport();

                            String reportName = editTextReportName.getText().toString();

                            String[] commandsReport = commands.toArray(new String[0]);

                            if(!commands.isEmpty()){

                                Report newReport = new Report(reportId, projectId, reportName, commandsReport);

                                try {

                                    List<Report> reportsAvailable = new ArrayList<Report>();

                                    if(Report.isEmptyList(ReportCreateActivity.this) == false){

                                        reportsAvailable = SerializableManager.readSerializable(ReportCreateActivity.this, "reportsAvailable");

                                    }

                                    reportsAvailable.add(newReport);

                                    SerializableManager.saveSerializable(ReportCreateActivity.this, reportsAvailable, "reportsAvailable");

                                    finish();

                                }catch(Exception e){
                                    Dialog.getDebug(ReportCreateActivity.this,e);
                                }

                            }

                        }catch(Exception e){

                            Dialog.getDebug(ReportCreateActivity.this,e);

                        }



                    }

                }
            });

            buttonAddCommand.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(editTextAddCommand.getText().toString().trim().length() > 0){

                        String command = editTextAddCommand.getText().toString();

                        addCommandReport(command);

                        editTextAddCommand.setText("");

                        Dialog.hideKeyboard(ReportCreateActivity.this);

                    }

                }

            });

            editTextAddCommand.setOnKeyListener(new View.OnKeyListener(){

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        String command = editTextAddCommand.getText().toString();

                        addCommandReport(command);

                        editTextAddCommand.setText("");

                        Dialog.hideKeyboard(ReportCreateActivity.this);

                        return true;

                    }
                    return false;
                }
            });

        }

    }

    public void initStoredCommands(){

        List<Command> commandsAvailable = commandInstance.getListCommands(ReportCreateActivity.this);

        LinearLayout layout_checkBoxesCommands = (LinearLayout) findViewById(R.id.layout_checkBoxesCommands);

        ArrayList<CheckBox> checkBoxesCommand = new ArrayList<CheckBox>();

        if(commandInstance.isEmptyList(ReportCreateActivity.this) == false){

            TextView alreadyTextView = (TextView)findViewById(0);

            if(alreadyTextView != null){

                layout_checkBoxesCommands.removeView(alreadyTextView);

            }

            for(final Command commandAvailable:commandsAvailable){

                int idCommand = commandAvailable.getIdCommand();

                CheckBox alreadyCommand = (CheckBox)findViewById(idCommand);

                if(alreadyCommand == null){

                    final CheckBox checkBoxCommand = new CheckBox(ReportCreateActivity.this);

                    checkBoxCommand.setId(commandAvailable.getIdCommand());

                    checkBoxCommand.setBackgroundResource(R.drawable.item_list_border);

                    checkBoxCommand.setHeight(150);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkBoxCommand.setStateListAnimator(null);
                    }

                    checkBoxCommand.setText(commandAvailable.getContentCommand());

                    checkBoxCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                    checkBoxCommand.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);

                    checkBoxCommand.setTransformationMethod(null);

                    checkBoxesCommand.add(checkBoxCommand);

                    layout_checkBoxesCommands.addView(checkBoxCommand);

                    checkBoxCommand.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            if(checkBoxCommand.isChecked() == false){

                                removeCommandReport(commandAvailable);

                            }else{

                                addCommandReport(checkBoxCommand.getText().toString());

                            }


                        }
                    });

                    Intent i = getIntent();

                    final String typeReport = (String) i.getExtras().getSerializable("typeReport");

                    if(typeReport.equals("custom")){

                        for(String command : commands){

                            if(command.equals(commandAvailable.getContentCommand())){

                                checkBoxCommand.setChecked(true);

                            }

                        }

                    }

                }

            }

        }else{

            TextView textViewCommand = new TextView(ReportCreateActivity.this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(10,20,10,0);

            textViewCommand.setLayoutParams(params);

            textViewCommand.setId(0);

            textViewCommand.setText("No commands stored yet");

            textViewCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            textViewCommand.setGravity(Gravity.CENTER | Gravity.BOTTOM);

            textViewCommand.setTransformationMethod(null);

            layout_checkBoxesCommands.addView(textViewCommand);

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_create);

        initControllers();

        initStoredCommands();

    }
}