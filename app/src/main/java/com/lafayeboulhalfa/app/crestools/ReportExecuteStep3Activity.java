package com.lafayeboulhalfa.app.crestools;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

public class ReportExecuteStep3Activity extends AppCompatActivity {

    public Report reportInstance;

    public void initControllers(){

        Intent intent = getIntent();

        final Report report = (Report) intent.getExtras().getSerializable("storedReport");

        final Button buttonAction = (Button) findViewById(R.id.buttonAction);

        final TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        textViewTitle.setText(report.getNameReport());

        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {

        boolean canReturn = false;

        if (canReturn == true) {
            super.onBackPressed();
        } else {

        }

    }

    public void execReport() throws FileNotFoundException, UnsupportedEncodingException {

        Intent intent = getIntent();

        final int projectId = (int) intent.getExtras().getSerializable("projectId");

        final Device device = (Device) intent.getExtras().getSerializable("storedDevice");

        final Report report = (Report) intent.getExtras().getSerializable("storedReport");

        final ImageButton buttonDone = (ImageButton) findViewById(R.id.imageButtonRight);

        final RelativeLayout relativeLayoutHeader = (RelativeLayout) findViewById(R.id.relativeLayoutHeader);

        final TextView textViewHeader = (TextView) findViewById(R.id.textViewHeader);

        final TextView textViewDate = (TextView) findViewById(R.id.textViewDate);

        final TextView textViewResult = (TextView) findViewById(R.id.textViewResult);

        final TextView textViewMainLegend = (TextView) findViewById(R.id.textViewMainLegend);

        final TextView textViewSubLegend = (TextView) findViewById(R.id.textViewSubLegend);

        final Button buttonAction = (Button) findViewById(R.id.buttonAction);

        switch (device.getDeviceType()){

            case "CTP":

                reportInstance.execReportDefault(device, report, textViewMainLegend, textViewSubLegend, relativeLayoutHeader, textViewHeader, textViewDate, textViewResult, buttonAction, buttonDone, ReportExecuteStep3Activity.this);

                break;

            case "SSL":

                reportInstance.execReportDefault(device, report, textViewMainLegend, textViewSubLegend, relativeLayoutHeader, textViewHeader, textViewDate, textViewResult, buttonAction, buttonDone, ReportExecuteStep3Activity.this);

                break;

            case "SSH":

                reportInstance.execReportSsh(device, report, textViewMainLegend, textViewSubLegend, relativeLayoutHeader, textViewHeader, textViewDate, textViewResult, buttonAction, buttonDone, ReportExecuteStep3Activity.this);

                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_execute_step3);

        initControllers();

        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(ReportExecuteStep3Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ReportExecuteStep3Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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

        try {
            execReport();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
