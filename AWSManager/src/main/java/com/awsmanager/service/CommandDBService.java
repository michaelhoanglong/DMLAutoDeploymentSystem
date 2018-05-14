package com.awsmanager.service;

import com.awsmanager.model.AwsCommand;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandDBService extends DBService {
    public CommandDBService(){
        super();
    }

    public int createCommand(AwsCommand awsCommand) throws SQLException {
        String sqlquery = "Insert into Command (InstanceId, CommandId, CommandStatus, TrainingCommand) values(?,?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setString(1, awsCommand.getInstanceId());
        statement.setString(2, awsCommand.getCommandId());
        statement.setString(3, awsCommand.getCommandStatus());
        statement.setBoolean(4, awsCommand.isTrainingCommand());
        return statement.executeUpdate();
    }

    public int updateCommandStatus(String commandStatus, String commandId) throws SQLException {
        String sqlquery = "Update Command Set CommandStatus=? Where CommandId=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setString(1, commandStatus);
        statement.setString(2, commandId);
        return statement.executeUpdate();
    }

    public List<AwsCommand> getTrainingCommandsByInstanceId(String instanceId) throws SQLException {
        String sqlquery = "Select * From Command Where InstanceId=? And TrainingCommand=TRUE";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setString(1,instanceId);
        ResultSet resultSet = statement.executeQuery();
        List<AwsCommand> awsCommandList = new ArrayList<>();
        while(resultSet.next()){
            AwsCommand awsCommand = new AwsCommand();
            awsCommand.setId(resultSet.getInt("id"));
            awsCommand.setCommandId(resultSet.getString("CommandId"));
            awsCommand.setCommandStatus(resultSet.getString("CommandStatus"));
            awsCommand.setInstanceId(resultSet.getString("InstanceId"));
            awsCommand.setTrainingCommand(resultSet.getBoolean("TrainingCommand"));
            awsCommandList.add(awsCommand);
        }
        return awsCommandList;
    }

    public int deleteCommandByInstanceId(String instanceId) throws SQLException {
        String sqlquery = "Delete From Command Where InstanceId=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setString(1,instanceId);
        return statement.executeUpdate();
    }
}
