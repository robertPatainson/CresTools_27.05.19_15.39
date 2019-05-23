package com.lafayeboulhalfa.app.crestools;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ImportStep2Activity extends AppCompatActivity {

    Project projectInstance;

    Device deviceInstance;

    public void initControllers(){

        final ImageButton imageButtonPrevious = (ImageButton) findViewById(R.id.imageButtonLeft);

        imageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(ImportStep2Activity.this, ImportStep1Activity.class);

                startActivity(i);

                ImportStep1Activity.staticImportStep1Activity.finish();

                finish();

            }
        });

    }

    public void initStoredProjects(){

        try{

            final List<Device> devicesToImport = SerializableManager.readSerializable(ImportStep2Activity.this, deviceInstance.devicesToImportPath);

            LinearLayout layout = (LinearLayout) findViewById(R.id.layoutButtonsProjects);

            ArrayList<Button> buttonsOpenProject = new ArrayList<Button>();

            TextView textViewInfo = new TextView(this);

            List<Project> projectsAvailable = new ArrayList<Project>();

            projectsAvailable = projectInstance.getProjectsAvailable(getApplicationContext());

            if(projectInstance.isEmptyList(getApplicationContext()) == false) {

                for(final Project projectAvailable : projectsAvailable) {

                    final Button buttonOpenProject = new Button(ImportStep2Activity.this);

                    final Project project = projectAvailable;

                    buttonOpenProject.setBackgroundResource(R.drawable.item_list_border);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        buttonOpenProject.setStateListAnimator(null);
                    }

                    buttonOpenProject.setId(project.getProjectId());

                    buttonOpenProject.setText("     " + project.getProjectName());

                    buttonOpenProject.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                    buttonOpenProject.setGravity(Gravity.LEFT );

                    buttonOpenProject.setTransformationMethod(null);

                    buttonsOpenProject.add(buttonOpenProject);

                    layout.addView(buttonOpenProject);

                    buttonOpenProject.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            List<Device> devicesAvailable = new ArrayList<Device>();

                            if(Device.isEmptyList(getApplicationContext()) == false){

                                devicesAvailable = SerializableManager.readSerializable(ImportStep2Activity.this, deviceInstance.devicesAvailablePath);

                            }

                            for(Device deviceToImport : devicesToImport){

                                deviceToImport.setDeviceProjectId(project.getProjectId());

                                devicesAvailable.add(deviceToImport);

                            }

                            SerializableManager.saveSerializable(ImportStep2Activity.this, devicesAvailable, deviceInstance.devicesAvailablePath);

                            Intent i = new Intent(ImportStep2Activity.this, DeviceActivity.class);

                            i.putExtra("projectId", project.getProjectId());

                            startActivity(i);

                            ImportStep1Activity.staticImportStep1Activity.finish();

                            finish();

                        }
                    });

                    buttonOpenProject.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            // TODO Auto-generated method stub

                            AlertDialog.Builder builder = new AlertDialog.Builder(ImportStep2Activity.this);

                            builder.setTitle("Modify project");

                            builder.setIcon(R.drawable.ic_settings);

                            final EditText edittext = new EditText(ImportStep2Activity.this);

                            builder.setMessage("Rename your project");

                            builder.setView(edittext);

                            builder.setPositiveButton("Rename",

                                    new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int id)
                                        {

                                            String projectName = edittext.getText().toString();

                                            if(projectInstance.isDuplicate(getApplicationContext(), projectName) == true){

                                                Dialog.getUserInfo(ImportStep2Activity.this, "Project name is already taken !");

                                            }else if(projectName != null && !projectName.isEmpty()){

                                                try {

                                                    List<Project> projectsAvailable = new ArrayList<Project>();

                                                    projectsAvailable = projectInstance.getProjectsAvailable(getApplicationContext());

                                                    for (Project p : projectsAvailable)
                                                    {
                                                        if(p.getProjectId() == project.getProjectId()){

                                                            p.setProjectName(projectName);

                                                        }
                                                    }
                                                    SerializableManager.saveSerializable(ImportStep2Activity.this, projectsAvailable, "projectsAvailable");

                                                    Intent intent = getIntent();

                                                    finish();

                                                    startActivity(intent);

                                                } catch (Exception e) {

                                                    Dialog.getDebug(ImportStep2Activity.this, e);

                                                }

                                            }else if(projectName == null || projectName.isEmpty()){

                                                Dialog.getUserInfo(ImportStep2Activity.this, "Project name cannot be empty !");

                                            }

                                        }
                                    });

                            builder.setNeutralButton("Cancel",
                                    new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int id)
                                        {

                                            dialog.cancel();
                                        }
                                    });

                            builder.setNegativeButton("Delete",
                                    new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int id)
                                        {

                                            AlertDialog.Builder deleteConfirm = new AlertDialog.Builder(ImportStep2Activity.this);

                                            deleteConfirm.setTitle("Delete confirm");

                                            deleteConfirm.setMessage("Are you sure to delete this project ?");

                                            deleteConfirm.setPositiveButton("Delete",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {

                                                            SerializableManager.removeProject(getApplicationContext(), projectInstance.getProjectsAvailablePath(), project.getProjectId());

                                                            Intent intent = getIntent();

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
                            builder.create().show();

                            return true;
                        }
                    });

                }

            }else{

                textViewInfo.setText(" No projects yet");
                layout.addView(textViewInfo);

            }

        }catch(Exception e){

            Dialog.getDebug(ImportStep2Activity.this, e);

        }

    }

    public void initAddProject(){

        final ImageButton imageButtonAddProject = (ImageButton) findViewById(R.id.imageButtonRight);

        final Project p = new Project();

        imageButtonAddProject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ImportStep2Activity.this);

                final EditText editText = new EditText(ImportStep2Activity.this);

                alert.setTitle("New Project");

                alert.setIcon(R.drawable.ic_add);

                alert.setMessage("Name your project");

                alert.setView(editText);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String projectName = editText.getText().toString();

                        int projectId = p.setProjectId();

                        if (projectInstance.isDuplicate(getApplicationContext(), projectName) == true) {

                            Dialog.getUserInfo(ImportStep2Activity.this, "Project name is already taken !");

                        }else if(projectName != null && !projectName.isEmpty()){

                            try {

                                Project project = new Project(projectId, projectName);

                                List<Project> projectsAvailable = new ArrayList<Project>();

                                projectsAvailable = SerializableManager.readSerializable(ImportStep2Activity.this, "projectsAvailable");

                                if (projectsAvailable == null) {

                                    List<Project> firstProject = new ArrayList<Project>();

                                    firstProject.add(project);

                                    SerializableManager.saveSerializable(ImportStep2Activity.this, firstProject, "projectsAvailable");

                                } else {

                                    projectsAvailable.add(project);

                                    SerializableManager.saveSerializable(ImportStep2Activity.this, projectsAvailable, "projectsAvailable");

                                }

                                Intent intent = getIntent();

                                finish();

                                startActivity(intent);

                            } catch (Exception e) {

                                Dialog.getDebug(ImportStep2Activity.this, e);

                            }

                        }else if(projectName == null || projectName.isEmpty()){

                            Dialog.getUserInfo(ImportStep2Activity.this, "Project name cannot be empty !");

                        }



                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();

            }

        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_step2);

        initControllers();

        initStoredProjects();

        initAddProject();

    }

}
