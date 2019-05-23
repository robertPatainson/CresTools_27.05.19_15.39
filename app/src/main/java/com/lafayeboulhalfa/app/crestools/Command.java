package com.lafayeboulhalfa.app.crestools;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by HP on 19.03.2019.
 */

public class Command implements Serializable {

    public Command(){}

    public static String commandsAvailablePath = "storedCommands";

    private int idCommand;

    private String contentCommand;

    public Command(int idCommand, String contentCommand){

        this.idCommand = idCommand;

        this.contentCommand = contentCommand;

    }

    /**
     *
     * GETTERS
     *
     */

    public int getIdCommand(){

        return this.idCommand;

    }

    public String getContentCommand(){

        return this.contentCommand;

    }

    /**
     *
     * SETTERS
     *
     */

    public void setIdCommand(int idCommand){

        this.idCommand = idCommand;

    }

    public void setContentCommand(String contentCommand){

        this.idCommand = idCommand;

    }

    /**
     *
     * FUNCS
     *
     */

    public static int getUniqId(Context context){

        int uniqId = 0;

        if(isEmptyList(context) == false){

            List<Command> commandsAvailable = new ArrayList<Command>();

            commandsAvailable = SerializableManager.readSerializable(context, commandsAvailablePath);

            for(Command command : commandsAvailable){

                uniqId = command.getIdCommand();

            }

        }

        return uniqId+1;

    }

    public static int getLastId(Context context){

        int lastId = 0;

        if(isEmptyList(context) == false){

            List<Command> commandsAvailable = new ArrayList<Command>();

            commandsAvailable = SerializableManager.readSerializable(context, commandsAvailablePath);

            List<Integer> ids = new ArrayList<Integer>();

            for(Command command : commandsAvailable){

                ids.add(command.getIdCommand());

            }

            if(ids != null){

                lastId = Collections.max(ids);

            }

        }

        return lastId;

    }

    public static boolean isEmptyList(Context context){

        List<Command> commandsAvailable = new ArrayList<Command>();

        commandsAvailable = SerializableManager.readSerializable(context, commandsAvailablePath);

        if(commandsAvailable == null || commandsAvailable.isEmpty()){

            return true;

        }

        return false;

    }

    public static List<Command> getListCommands(Context context){

        List<Command> commandsAvailable = new ArrayList<Command>();

        if(isEmptyList(context) == false){

            commandsAvailable = SerializableManager.readSerializable(context, commandsAvailablePath);

        }

        return commandsAvailable;

    }

    public static boolean checkDuplicate(Context context, String contentCommand){

        List<Command> commandsAvailable = getListCommands(context);

        if(commandsAvailable != null && !commandsAvailable.isEmpty()){

            for(Command commandAvailable : commandsAvailable){

                if(contentCommand.equals(commandAvailable.getContentCommand()))
                    return true;

            }

        }

        return false;

    }

    public static void addCommand(Context context, Command command){

        if(checkDuplicate(context, command.getContentCommand()) == false){

            List<Command> commandsAvailable = getListCommands(context);

            commandsAvailable.add(command);

            SerializableManager.saveSerializable(context, commandsAvailable, commandsAvailablePath);

        }

    }

    public void removeCommand(Context context){

        SerializableManager.removeCommand(context, commandsAvailablePath, this.getIdCommand());

    }

    public static Command getCommandByContent(Context context, String contentCommand){

        Command command = null;

        List<Command> commandsAvailable = getListCommands(context);

        for(Command commandAvailable : commandsAvailable){

            if(commandAvailable.getContentCommand().equals(contentCommand)){

                command = commandAvailable;

            }

        }

        return command;

    }

}

