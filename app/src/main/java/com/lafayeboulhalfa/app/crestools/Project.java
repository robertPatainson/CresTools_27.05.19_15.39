package com.lafayeboulhalfa.app.crestools;

import android.content.Context;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 19.03.2019.
 */

public class Project implements Serializable {

    public Project(){

    }

    public static String projectsAvailablePath = "projectsAvailable";

    private String projectName;

    private int projectId;

    public Project(int projectId, String projectName){

        this.projectId = projectId;

        this.projectName = projectName;

    }

    /**
     *
     * GETTERS
     *
     */
    public int getProjectId(){

        return this.projectId;

    }

    public String getProjectName(){

        return this.projectName;

    }

    public static String getProjectsAvailablePath(){

        return projectsAvailablePath;

    }

    /**
     *
     * SETTERS
     *
     */
    public int setProjectId(){

        //using ts as uniqueId

        Long tsLong = System.currentTimeMillis()/1000;

        String ts = tsLong.toString();

        int projectId = Integer.parseInt(ts);

        this.projectId = projectId;

        return projectId;
    }

    public void setProjectName(String projectName){

        this.projectName = projectName;

    }

    public static List<Project> getProjectsAvailable(Context context){

        List<Project> projectsAvailable = new ArrayList<Project>();

        projectsAvailable = SerializableManager.readSerializable(context, projectsAvailablePath);

        return projectsAvailable;
    }

    public static boolean isDuplicate(Context context, String projectName){

        List<Project> projectsAvailable = new ArrayList<Project>();

        projectsAvailable = SerializableManager.readSerializable(context, projectsAvailablePath);

        if(projectsAvailable != null){

            for(Project projectAvailable : projectsAvailable){

                if(projectAvailable.getProjectName().equals(projectName))
                    return true;

            }

        }

        return false;
    }

    public static boolean isEmptyList(Context context){

        List<Project> projectsAvailable = new ArrayList<Project>();

        projectsAvailable = SerializableManager.readSerializable(context, projectsAvailablePath);

        if(projectsAvailable == null || projectsAvailable.isEmpty()){

            return true;

        }
        return false;

    }

    public static Project getProjectById(Context c, int projectId){

        List<Project> projectsAvailable = new ArrayList<Project>();

        projectsAvailable = SerializableManager.readSerializable(c, projectsAvailablePath);

        if(projectsAvailable != null && !projectsAvailable.isEmpty()){

            for(final Project projectAvailable : projectsAvailable) {

                if(projectAvailable.getProjectId() == projectId){

                    return projectAvailable;

                }


            }

        }

        return null;

    }

    public static int getAmountOfDevicesInProject(Context context, Project project){

        int amount = 0;

        List<Device> devicesAvailable = new ArrayList<Device>();

        devicesAvailable = Device.getDeviceByProject(context, project.getProjectId());

        if(devicesAvailable != null && devicesAvailable.size() > 0){

            amount = devicesAvailable.size();

        }

        return amount;

    }

}
