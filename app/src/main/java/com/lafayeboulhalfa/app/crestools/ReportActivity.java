package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
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
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    public static Activity staticReportActivity;

    boolean editingIsShown;

    public void initCustomReports(){

        Intent i = getIntent();
        final int projectId = (int) i.getExtras().getSerializable("projectId");

        try{

            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_buttonsCustomReports);

            ArrayList<Button> buttonsOpenCustomReport = new ArrayList<Button>();

            List<Report> reportsAvailable = new ArrayList<Report>();

            reportsAvailable = SerializableManager.readSerializable(ReportActivity.this, "reportsAvailable");

            if(Report.isEmptyList(ReportActivity.this) == false){

                for(final Report reportAvailable : reportsAvailable) {

                    if(reportAvailable.getIdProject() == projectId){

                        final Button buttonOpenCustomReport = new Button(ReportActivity.this);

                        final Report report = reportAvailable;

                        buttonOpenCustomReport.setId(report.getIdReport());

                        buttonOpenCustomReport.setBackgroundResource(R.drawable.item_list_border);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            buttonOpenCustomReport.setStateListAnimator(null);
                        }

                        buttonOpenCustomReport.setText("     " + report.getNameReport());

                        buttonOpenCustomReport.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

                        buttonOpenCustomReport.setGravity(Gravity.LEFT);

                        buttonOpenCustomReport.setTransformationMethod(null);

                        buttonsOpenCustomReport.add(buttonOpenCustomReport);

                        layout.addView(buttonOpenCustomReport);

                        buttonOpenCustomReport.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                Intent i = new Intent(ReportActivity.this, ReportExecuteStep1Activity.class);

                                i.putExtra("typeReport","custom");

                                i.putExtra("projectId", projectId);

                                i.putExtra("storedReport", report);

                                startActivity(i);

                            }
                        });

                    }

                }

            }else{

                ImageButton imageButtonSave = findViewById(R.id.imageButtonRight);

                imageButtonSave.setVisibility(View.INVISIBLE);

                TextView textViewLegend = new TextView(ReportActivity.this);

                textViewLegend.setText("No custom reports added yet");

                textViewLegend.setPadding(50, 25, 0, 0);

                layout.addView(textViewLegend);

            }

        }catch(Exception e){

            Dialog.getDebug(ReportActivity.this,e);

        }

    }

    public void initControllers(){

        Intent i = getIntent();

        final int projectId = (int) i.getExtras().getSerializable("projectId");

        final ImageButton buttonCancel = (ImageButton) findViewById(R.id.imageButtonLeft);

        final ImageButton buttonEdit = (ImageButton) findViewById(R.id.imageButtonRight);

        final Button buttonCreateReport = (Button) findViewById(R.id.buttonCreateReport);

        editingIsShown = false;

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                final LinearLayout layout = (LinearLayout) findViewById(R.id.layout_buttonsCustomReports);

                if(editingIsShown == false){

                    buttonEdit.setImageResource(R.drawable.ic_save);

                    editingIsShown = true;

                    if(layout != null){

                        final int childCount = layout.getChildCount();

                        for (int i = 0; i < childCount; i++) {
                            final View v = layout.getChildAt(i);

                            if(v instanceof Button){

                                int height = ((Button) v).getHeight();

                                ((Button) v).setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_delete,0);

                                ((Button) v).setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.FireBrick)));

                                ((Button) v).setHeight(height);

                                v.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        String nameReport = ((Button) v).getText().toString().trim();

                                        final Report report = Report.getReportByName(ReportActivity.this, projectId, nameReport);

                                        if(report != null){

                                            AlertDialog.Builder deleteConfirm = new AlertDialog.Builder(ReportActivity.this);

                                            deleteConfirm.setTitle("Delete confirm");

                                            deleteConfirm.setMessage("Are you sure to delete this report ?");

                                            deleteConfirm.setPositiveButton("Delete",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {

                                                            SerializableManager.removeReport(ReportActivity.this, Report.reportsAvailablePath, report.getIdReport());

                                                            layout.removeView(v);

                                                            if(Report.isEmptyList(ReportActivity.this)){

                                                                TextView textViewLegend = new TextView(ReportActivity.this);

                                                                textViewLegend.setText("No custom reports added yet");

                                                                textViewLegend.setPadding(25, 25, 0, 0);

                                                                layout.addView(textViewLegend);

                                                            }

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

                                    }
                                });

                            }

                        }

                    }

                }else{

                    buttonEdit.setImageResource(R.drawable.ic_edit);

                    editingIsShown = false;

                    if(layout != null){

                        final int childCount = layout.getChildCount();

                        for (int i = 0; i < childCount; i++) {
                            View v = layout.getChildAt(i);

                            if(v instanceof Button){

                                ((Button) v).setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

                                final String nameReport = ((Button) v).getText().toString().trim();

                                v.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {

                                        Report report = Report.getReportByName(ReportActivity.this, projectId, nameReport);

                                        if(report != null){

                                            Intent i = new Intent(ReportActivity.this, ReportExecuteStep1Activity.class);

                                            i.putExtra("typeReport","default");

                                            i.putExtra("projectId", projectId);

                                            i.putExtra("storedReport", report);

                                            startActivity(i);

                                        }

                                    }
                                });

                            }

                        }

                    }

                }

            }
        });

        buttonCreateReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(ReportActivity.this, ReportCreateActivity.class);

                i.putExtra("typeReport","new");

                i.putExtra("projectId", projectId);

                startActivity(i);

            }
        });

        try{

            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_buttonsDefaultReports);

            ArrayList<Button> buttonsOpenDefaultReport = new ArrayList<Button>();

            List<Report> reportsAvailable = new ArrayList<Report>();

            reportsAvailable = Report.reports;

            int count = 0;

            for(final Report reportAvailable : reportsAvailable) {

                count = count + 1;

                final Button buttonOpenDefaultReport = new Button(ReportActivity.this);

                final Report report = reportAvailable;

                buttonOpenDefaultReport.setId(report.getIdReport());

                if(count == 1){

                    buttonOpenDefaultReport.setBackgroundResource(R.drawable.button);

                }else{

                    buttonOpenDefaultReport.setBackgroundResource(R.drawable.item_list_border);

                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    buttonOpenDefaultReport.setStateListAnimator(null);
                }

                buttonOpenDefaultReport.setText("     " + report.getNameReport());

                buttonOpenDefaultReport.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

                buttonOpenDefaultReport.setGravity(Gravity.LEFT);

                buttonOpenDefaultReport.setTransformationMethod(null);

                buttonsOpenDefaultReport.add(buttonOpenDefaultReport);

                layout.addView(buttonOpenDefaultReport);

                buttonOpenDefaultReport.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent i = new Intent(ReportActivity.this, ReportExecuteStep1Activity.class);

                        i.putExtra("typeReport","default");

                        i.putExtra("projectId", projectId);

                        i.putExtra("storedReport", report);

                        startActivity(i);

                    }
                });

            }

        }catch(Exception e){

            Dialog.getDebug(ReportActivity.this,e);

        }

    }

    public void loadActivity(){

        initCustomReports();

        initControllers();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        loadActivity();

        staticReportActivity = this;

    }

    @Override
    protected void onRestart() {

        super.onRestart();
        setContentView(R.layout.activity_report);

        loadActivity();

    }
}
