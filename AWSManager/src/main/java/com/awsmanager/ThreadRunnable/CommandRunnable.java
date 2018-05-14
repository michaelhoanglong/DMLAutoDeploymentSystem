package com.awsmanager.ThreadRunnable;

import com.awsmanager.exception.TimeOutException;
import com.awsmanager.service.CommandService;

import java.sql.SQLException;
import java.util.ArrayList;

public class CommandRunnable implements Runnable{
    private Boolean isTrainingCommand;
    private CommandService commandService;
    private String instanceId;
    private String comment;
    private ArrayList<String> commands;

    public CommandRunnable(String instanceId, String comment, ArrayList<String> commands, Boolean isTrainingCommand){
        this.commandService = new CommandService();
        this.instanceId = instanceId;
        this.comment = comment;
        this.commands = commands;
        this.isTrainingCommand = isTrainingCommand;
    }
    @Override
    public void run() {
        try {
            commandService.sendCommand(this.instanceId, this.comment, this.commands, this.isTrainingCommand);
        } catch (TimeOutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
