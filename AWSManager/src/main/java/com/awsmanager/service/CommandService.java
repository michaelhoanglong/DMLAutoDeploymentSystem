package com.awsmanager.service;

import com.amazonaws.services.simplesystemsmanagement.*;
import com.amazonaws.services.simplesystemsmanagement.model.*;
import com.awsmanager.exception.TimeOutException;
import com.awsmanager.model.AwsCommand;
import com.awsmanager.model.GlobalParameter;

import java.sql.SQLException;
import java.util.*;

public class CommandService {
    private CommandDBService commandDBService = new CommandDBService();
    private AWSSimpleSystemsManagementAsyncClient awsSimpleSystemsManagementAsyncClient;
    AWSSimpleSystemsManagementAsync client= AWSSimpleSystemsManagementAsyncClientBuilder.defaultClient();
    public CommandService(){
        awsSimpleSystemsManagementAsyncClient = new AWSSimpleSystemsManagementAsyncClient();
    }

    public AwsCommand sendCommand(String instanceId, String comment, ArrayList<String> commands, Boolean isTrainingCommand) throws TimeOutException, InterruptedException, SQLException {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("commands",commands);
        ArrayList<String> workingDirectories = new ArrayList<>();
        workingDirectories.add(GlobalParameter.AwsWorkingDirectory);
        parameters.put("workingDirectory", workingDirectories);
        System.out.println(parameters);
        SendCommandRequest sendCommandRequest = new SendCommandRequest()
                .withInstanceIds(instanceId)
                .withComment(comment)
                .withDocumentName(GlobalParameter.AwsDocumentName)
                .withTimeoutSeconds(600)
                .withParameters(parameters);
        awsSimpleSystemsManagementAsyncClient.setEndpoint(GlobalParameter.AwsSSMEndpoint);

        // wait until instance is listed in run command
        ArrayList<String> instanceIDList = new ArrayList<>();
        instanceIDList.add(instanceId);
        this.waitIntanceInRunCommand(instanceIDList, awsSimpleSystemsManagementAsyncClient);

        SendCommandResult sendCommandResult = awsSimpleSystemsManagementAsyncClient.sendCommand(sendCommandRequest);
        String commandId = sendCommandResult.getCommand().getCommandId();
        String commandStatus = getCommandStatus(commandId);
        AwsCommand awsCommand = new AwsCommand(instanceId,commandId,commandStatus,isTrainingCommand);
        commandDBService.createCommand(awsCommand);
        return awsCommand;
    }

    public String getCommandStatus(String commandId) throws SQLException {
        ListCommandsRequest  listCommandsRequest = new ListCommandsRequest()
                .withCommandId(commandId);
        ListCommandsResult listCommandsResult = client.listCommands(listCommandsRequest);
        while(listCommandsResult.getCommands().size() == 0){
            listCommandsRequest = new ListCommandsRequest()
                    .withCommandId(commandId);
            listCommandsResult = client.listCommands(listCommandsRequest);
        }
        String status = listCommandsResult.getCommands().get(0).getStatus();
        System.out.println(commandId);
        System.out.println(status);
        commandDBService.updateCommandStatus(status,commandId);
        return status;
    }

    /**
     * Wait an instance until it appear in available instance list of Run AwsCommand of AWS
     * @param instanceIDs instanceID of instance we want to check
     * @param awsSimpleSystemsManagementAsyncClient
     * @throws InterruptedException
     * @throws TimeOutException throw TimeOutException if waiting time is too long (> 28800 second)
     */
    private void waitIntanceInRunCommand(ArrayList<String> instanceIDs,AWSSimpleSystemsManagementAsyncClient awsSimpleSystemsManagementAsyncClient) throws InterruptedException, TimeOutException {
        boolean[] isAppeared = new boolean[instanceIDs.size()];
        int count = 0;
        long start = Calendar.getInstance().getTimeInMillis();
        while(true){
            DescribeInstanceInformationRequest describeInstanceInformationRequest =  new DescribeInstanceInformationRequest();
            DescribeInstanceInformationResult describeInstanceInformationResult =  awsSimpleSystemsManagementAsyncClient.describeInstanceInformation(describeInstanceInformationRequest);
            ArrayList<InstanceInformation> instanceInformations = (ArrayList<InstanceInformation>) describeInstanceInformationResult.getInstanceInformationList();
            for (InstanceInformation instanceInformation : instanceInformations){
                for (int i = 0 ; i < instanceIDs.size() ; i++){
                    if (!isAppeared[i] && instanceInformation.getInstanceId().equals(instanceIDs.get(i))){
                        isAppeared[i] = true;
                        count++;
                    }
                }
                if(count == instanceIDs.size()) return;
            }
//            if (Calendar.getInstance().getTimeInMillis() - start > Integer.parseInt(GlobalParameter.AwsCommandTimeOut)) throw new TimeOutException();
            Thread.sleep(GlobalParameter.AwsCommandWaitTime);
        }
    }
}
