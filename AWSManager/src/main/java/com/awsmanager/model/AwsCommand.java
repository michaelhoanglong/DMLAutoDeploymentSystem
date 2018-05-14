package com.awsmanager.model;

public class AwsCommand {
    private int id;
    private String instanceId;
    private String commandId;
    private String commandStatus;
    private boolean trainingCommand;

    public AwsCommand(){}

    public AwsCommand(String instanceId, String commandId, String commandStatus, boolean trainingCommand) {
        this.instanceId = instanceId;
        this.commandId = commandId;
        this.commandStatus = commandStatus;
        this.trainingCommand = trainingCommand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getCommandStatus() {
        return commandStatus;
    }

    public void setCommandStatus(String commandStatus) {
        this.commandStatus = commandStatus;
    }

    public boolean isTrainingCommand() {
        return trainingCommand;
    }

    public void setTrainingCommand(boolean trainingCommand) {
        this.trainingCommand = trainingCommand;
    }
}
