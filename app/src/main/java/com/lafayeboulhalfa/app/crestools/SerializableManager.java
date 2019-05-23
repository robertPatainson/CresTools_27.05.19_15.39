package com.lafayeboulhalfa.app.crestools;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 19.03.2019.
 */

public class SerializableManager {

    /**
     * Saves a serializable object.
     *  @param <Project> The type of the object.
     * @param context The application context.
     * @param objectToSave The object to save.
     * @param fileName The name of the file.
     */

    public static <Project extends Serializable> void saveSerializable(Context context, List<Project> objectToSave, String fileName) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(objectToSave);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public static <Device extends Serializable> void saveDeviceList(Context context, List<Device> objectToSave, String fileName) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(objectToSave);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    /**
     * Loads a serializable object.
     *
     * @param context The application context.
     * @param fileName The filename.
     * @param <Project> The object type.
     *
     * @return the serializable object.
     */

    public static<Project extends Serializable> Project readSerializable(Context context, String fileName) {
        Project objectToReturn = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectToReturn = (Project) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
        }

        return objectToReturn;
    }

    public static<SocketThread extends Serializable> SocketThread readSocketThread(Context context, String fileName) {
        SocketThread objectToReturn = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectToReturn = (SocketThread) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
        }

        return objectToReturn;
    }

    /**
     * Removes a specified file.
     *
     * @param context The application context.
     * @param filename The name of the file.
     */

    public static void removeSerializable(Context context, String filename) {
        context.deleteFile(filename);
    }

    /**
     * Removes a specified project.
     *
     * @param context The application context.
     * @param fileName The name of the file.
     * @param projectId The id of the project.
     */

    public static void removeProject(Context context, String fileName, int projectId){
        List<Project> projects = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            projects = (List<Project>) objectInputStream.readObject();
            List<Project> toRemove = new ArrayList<Project>();

            for(Project project : projects){

                if(project.getProjectId() == projectId){

                    toRemove.add(project);

                }

            }

            projects.removeAll(toRemove);

            objectInputStream.close();
            fileInputStream.close();

            SerializableManager.saveSerializable(context, projects, "projectsAvailable");

        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
        }


    }

    /**
     * Removes a specified device.
     *
     * @param context The application context.
     * @param fileName The name of the file.
     * @param deviceId The id of the project.
     */

    public static void removeDevice(Context context, String fileName, int deviceId){
        List<Device> devices = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            devices = (List<Device>) objectInputStream.readObject();
            List<Device> toRemove = new ArrayList<Device>();

            for(Device device : devices){

                if(device.getDeviceId() == deviceId){

                    toRemove.add(device);

                }

            }

            devices.removeAll(toRemove);

            objectInputStream.close();
            fileInputStream.close();

            SerializableManager.saveSerializable(context, devices, "devicesAvailable");

        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
        }


    }

    public static void removeCommand(Context context, String fileName, int commandId){
        List<Command> commands = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            commands = (List<Command>) objectInputStream.readObject();
            List<Command> toRemove = new ArrayList<Command>();

            for(Command command : commands){

                if(command.getIdCommand() == commandId){

                    toRemove.add(command);

                }

            }

            commands.removeAll(toRemove);

            objectInputStream.close();
            fileInputStream.close();

            SerializableManager.saveSerializable(context, commands, Command.commandsAvailablePath);

        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
        }


    }

    public static void removeReport(Context context, String fileName, int reportId){
        List<Report> reports = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            reports = (List<Report>) objectInputStream.readObject();
            List<Report> toRemove = new ArrayList<Report>();

            for(Report report : reports){

                if(report.getIdReport() == reportId){

                    toRemove.add(report);

                }

            }

            reports.removeAll(toRemove);

            objectInputStream.close();
            fileInputStream.close();

            SerializableManager.saveSerializable(context, reports, Report.reportsAvailablePath);

        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
        }


    }

    /**
     * Save a serialized string
     *
     * @param context The application context.
     * @param fileName The name of the file.
     * @param value The value of the string.
     */

    public static void writeString(Context context, String fileName, String value) throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(value);
        os.close();
        fos.close();
    }

    /**
     * Read a serialized string
     *
     * @param context The application context.
     * @param fileName The name of the file.
     */

    public static String readString(Context context, String fileName) throws IOException, ClassNotFoundException {

        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        String value = is.readObject().toString();
        is.close();
        fis.close();

        return value;

    }

}
