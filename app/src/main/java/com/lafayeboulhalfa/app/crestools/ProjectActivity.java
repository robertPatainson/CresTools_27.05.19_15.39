package com.lafayeboulhalfa.app.crestools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

public class ProjectActivity extends AppCompatActivity {

    public static Activity staticProjectActivity;

    Project projectInstance;

    LayoutInflater inflater;

    private class AdapterManager extends BaseAdapter {

        Context context;

        Project [] projectList;

        LayoutInflater inflater;

        public AdapterManager(Context applicationContext, Context context, Project[] projectList) {

            this.context = context;

            this.projectList = projectList;

            inflater = (LayoutInflater.from(applicationContext));

        }

        public Context getContext(){
            return this.context;
        }

        public LayoutInflater getInflater(){
            return this.inflater;
        }

        @Override
        public int getCount() {
            return projectList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            view = inflater.inflate(R.layout.listview_project, null);

            final Project project = projectList[i];

            final LinearLayout layoutSurface = view.findViewById(R.id.layoutSurface);

            final SwipeLayout swipeLayout = view.findViewById(R.id.swipeLayout);

            final TextView textViewDelete = view.findViewById(R.id.textViewDelete);

            final TextView textViewEdit = view.findViewById(R.id.textViewEdit);

            final TextView textViewTitle = view.findViewById(R.id.label);

            final TextView textViewAmountDevices = view.findViewById(R.id.labelAmountDevices);

            final int amountDevices = projectInstance.getAmountOfDevicesInProject(ProjectActivity.this, project);

            swipeLayout.setId(i);

            textViewDelete.setId(i);

            textViewEdit.setId(i);

            textViewTitle.setId(i);

            textViewAmountDevices.setId(i);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

            layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.START;

            textViewTitle.setText(project.getProjectName());

            if(amountDevices > 1){

                textViewAmountDevices.setText(amountDevices + " devices");

            }else{

                textViewAmountDevices.setText(amountDevices + " device");

            }

            layoutSurface.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    openDeviceActivity(project);

                }
            });

            layoutSurface.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // TODO Auto-generated method stub

                    editProject(project);

                    return true;
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ProjectActivity.this);

                    builder.setTitle("Delete confirm");

                    builder.setIcon(R.drawable.ic_settings);

                    builder.setMessage("Are you sure to delete this project ?");

                    builder.setPositiveButton("Delete",

                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int id)
                                {

                                    SerializableManager.removeProject(getApplicationContext(), projectInstance.getProjectsAvailablePath(), project.getProjectId());

                                    initListView();

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


                    builder.create().show();

                }
            });

            textViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editProject(project);

                }
            });

            return view;

        }

        public void setListViewHeightBasedOnChildren(ListView listView){

            float dip = 50f;

            Resources r = getResources();

            float pix = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );

            int px = Math.round(pix);

            ViewGroup.LayoutParams params = listView.getLayoutParams();

            params.height = listView.getCount()*px;

            listView.setLayoutParams(params);

            listView.requestLayout();

        }

    }

    public void openDeviceActivity(Project project){

        Intent intent = new Intent(ProjectActivity.this, DeviceActivity.class);

        intent.putExtra("projectId", project.getProjectId());

        startActivity(intent);

    }

    public void editProject(final Project project){

        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectActivity.this);

        builder.setTitle("Modify project");

        builder.setIcon(R.drawable.ic_settings);

        final EditText edittext = new EditText(ProjectActivity.this);

        builder.setMessage("Rename your project");

        builder.setView(edittext);

        builder.setPositiveButton("Rename",

                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        String projectName = edittext.getText().toString();

                        if(projectInstance.isDuplicate(getApplicationContext(), projectName) == true){

                            Dialog.getUserInfo(ProjectActivity.this, "Project name is already taken !");

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
                                SerializableManager.saveSerializable(ProjectActivity.this, projectsAvailable, "projectsAvailable");

                                initListView();

                            } catch (Exception e) {

                                Dialog.getDebug(ProjectActivity.this, e);

                            }

                        }else if(projectName == null || projectName.isEmpty()){

                            Dialog.getUserInfo(ProjectActivity.this, "Project name cannot be empty !");

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

                        AlertDialog.Builder deleteConfirm = new AlertDialog.Builder(ProjectActivity.this);

                        deleteConfirm.setTitle("Delete confirm");

                        deleteConfirm.setMessage("Are you sure to delete this project ?");

                        deleteConfirm.setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        SerializableManager.removeProject(getApplicationContext(), projectInstance.getProjectsAvailablePath(), project.getProjectId());

                                        initListView();

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

    }

    public void initListView(){

        List<Project> projectsAvailable = new ArrayList<Project>();

        projectsAvailable = projectInstance.getProjectsAvailable(getApplicationContext());

        ListView listView = findViewById(R.id.listView);

        TextView textViewLegend = findViewById(R.id.textViewLegend);

        View topBar = findViewById(R.id.topBar);

        View bottomBar = findViewById(R.id.bottomBar);

        if(projectInstance.isEmptyList(getApplicationContext()) == false) {

            listView.setVisibility(View.VISIBLE);

            textViewLegend.setVisibility(View.INVISIBLE);

            topBar.setVisibility(View.VISIBLE);

            bottomBar.setVisibility(View.VISIBLE);

            ListView listViewProjects;

            Project[] projects = projectsAvailable.toArray(new Project[projectsAvailable.size()]);

            listViewProjects = findViewById(R.id.listView);

            AdapterManager adapterManager = new AdapterManager(ProjectActivity.this, ProjectActivity.this, projects);

            inflater = adapterManager.getInflater();

            listViewProjects.setAdapter(adapterManager);

            adapterManager.setListViewHeightBasedOnChildren(listViewProjects);

        }else{

            listView.setVisibility(View.INVISIBLE);

            textViewLegend.setVisibility(View.VISIBLE);

            topBar.setVisibility(View.INVISIBLE);

            bottomBar.setVisibility(View.INVISIBLE);

        }

    }

    public void initToolbar(){

        int colorTitle = getResources().getColor(R.color.White);

        Typeface robotoBold = ResourcesCompat.getFont(ProjectActivity.this, R.font.robotobold);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapseToolbar);

        collapsingToolbar.setTitle("Projects");

        collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(colorTitle));

        collapsingToolbar.setExpandedTitleMarginStart(60);

        collapsingToolbar.setExpandedTitleMarginBottom(60);

        collapsingToolbar.setCollapsedTitleTextColor(colorTitle);

        collapsingToolbar.setExpandedTitleTypeface(robotoBold);

        collapsingToolbar.setCollapsedTitleTypeface(robotoBold);

    }

    public void initControllers(){

        final ImageButton imageButtonAddProject = (ImageButton) findViewById(R.id.imageButtonAddProject);

        final Project p = new Project();

        imageButtonAddProject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ProjectActivity.this);

                final EditText editText = new EditText(ProjectActivity.this);

                alert.setTitle("New Project");

                alert.setIcon(R.drawable.ic_add);

                alert.setMessage("Name your project");

                alert.setView(editText);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String projectName = editText.getText().toString();

                        int projectId = p.setProjectId();

                        if (projectInstance.isDuplicate(getApplicationContext(), projectName) == true) {

                            Dialog.getUserInfo(ProjectActivity.this, "Project name is already taken !");

                        }else if(projectName != null && !projectName.isEmpty()){

                            try {

                                Project project = new Project(projectId, projectName);

                                List<Project> projectsAvailable = new ArrayList<Project>();

                                projectsAvailable = SerializableManager.readSerializable(ProjectActivity.this, "projectsAvailable");

                                if (projectsAvailable == null) {

                                    List<Project> firstProject = new ArrayList<Project>();

                                    firstProject.add(project);

                                    SerializableManager.saveSerializable(ProjectActivity.this, firstProject, "projectsAvailable");

                                } else {

                                    projectsAvailable.add(project);

                                    SerializableManager.saveSerializable(ProjectActivity.this, projectsAvailable, "projectsAvailable");

                                }

                                initListView();

                            } catch (Exception e) {

                                Dialog.getDebug(ProjectActivity.this, e);

                            }

                        }else if(projectName == null || projectName.isEmpty()){

                            Dialog.getUserInfo(ProjectActivity.this, "Project name cannot be empty !");

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
        setContentView(R.layout.activity_project);

        staticProjectActivity = this;

    }

    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();

        initToolbar();

        initListView();

        initControllers();

    }

}
