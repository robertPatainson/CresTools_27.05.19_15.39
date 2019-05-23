package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ReportExecuteStep1Activity extends AppCompatActivity {

    public static Activity staticReportExecuteStep1Activity;

    public Report report;

    public void initControllers(){

        try {

            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_buttonsDefaultReports);

            ArrayList<Button> buttonssOpenDefaultReport = new ArrayList<Button>();

            Intent i = getIntent();

            String typeReport = (String) i.getExtras().getSerializable("typeReport");

            final int projectId = (int) i.getExtras().getSerializable("projectId");

            report = (Report) i.getExtras().getSerializable("storedReport");

            final ImageButton imageButtonPrevious = (ImageButton) findViewById(R.id.imageButtonLeft);

            final TextView textViewReportName = (TextView) findViewById(R.id.textViewReportName);

            final ImageButton buttonEdit = (ImageButton) findViewById(R.id.imageButtonRight);

            final Button buttonExecuteReport = (Button) findViewById(R.id.buttonExecuteReport);

            final int textColorButton = getResources().getColor(R.color.LightBlack);

            imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onBackPressed();

                }
            });

            if((typeReport).equals("custom")){

                buttonEdit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent i = new Intent(ReportExecuteStep1Activity.this, ReportCreateActivity.class);

                        i.putExtra("typeReport","custom");

                        i.putExtra("projectId", projectId);

                        i.putExtra("storedReport", report);

                        startActivity(i);

                        finish();

                    }
                });

            }else{

                buttonEdit.setVisibility(View.GONE);

            }

            textViewReportName.setText(" " + report.getNameReport());

            String[] commandsReport = report.getCommandsReport();

            int count = 0;

            for(final String commandReport : commandsReport) {

                count = count + 1;

                final Button buttonOpenDefaultReport = new Button(ReportExecuteStep1Activity.this);

                if(count == 1){

                    buttonOpenDefaultReport.setBackgroundResource(R.drawable.button);

                }else{

                    buttonOpenDefaultReport.setBackgroundResource(R.drawable.item_list_border);

                }

                buttonOpenDefaultReport.setEnabled(false);

                buttonOpenDefaultReport.setTextColor(textColorButton);

                buttonOpenDefaultReport.setText("   " + commandReport);

                buttonOpenDefaultReport.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

                buttonOpenDefaultReport.setTextColor(getResources().getColor(R.color.Black));

                buttonOpenDefaultReport.setGravity(Gravity.LEFT);

                buttonOpenDefaultReport.setTransformationMethod(null);

                buttonssOpenDefaultReport.add(buttonOpenDefaultReport);

                layout.addView(buttonOpenDefaultReport);

            }

            buttonExecuteReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(ReportExecuteStep1Activity.this, ReportExecuteStep2Activity.class);

                    i.putExtra("projectId", projectId);

                    i.putExtra("storedReport", report);

                    startActivity(i);

                }
            });


        }catch(Exception e){

            Dialog.getDebug(ReportExecuteStep1Activity.this,e);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_execute_step1);

        initControllers();

        staticReportExecuteStep1Activity = this;

    }

}
