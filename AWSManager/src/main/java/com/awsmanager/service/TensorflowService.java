package com.awsmanager.service;

import com.awsmanager.ThreadRunnable.CommandRunnable;
import com.awsmanager.exception.TimeOutException;
import com.awsmanager.model.AwsCommand;
import com.awsmanager.model.AwsInstance;
import com.awsmanager.model.GlobalParameter;
import com.awsmanager.model.Request;
import org.springframework.scheduling.annotation.Async;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TensorflowService {
    private DBService dbService = new DBService();
    private CommandDBService commandDBService = new CommandDBService();
    DeploymentService deploymentService = new DeploymentService();
    CommandService commandService = new CommandService();

    public TensorflowService(){
    }

    public void executeUserRequest(Request request) throws SQLException, InterruptedException, TimeOutException {
        request.setStatus(GlobalParameter.Initializing);
        int requestId = dbService.createRequestData(request);
        request.setId(requestId);
        int totalInstanceCount = request.getNumberOfPS() + request.getNumberOfWorker();
//        dbService.updateInstancesRequestId(requestId,requestId-1);
//        List<AwsInstance> instanceList = dbService.getInstancesByRequestId(requestId);
        List<AwsInstance> instanceList = deploymentService.createInstance(totalInstanceCount, request);
        if(instanceList == null){
            request.setStatus(GlobalParameter.Failed);
            dbService.updateRequestData(request);
        }

        // Wait for Instance User Data to run successfully after creation
        for(AwsInstance instance : instanceList){
            String instanceId = instance.getInstanceId();
            int numberOfWaitCycle = GlobalParameter.NumberOfCycleToWaitForCreatingInstance;
            if(!deploymentService.isInstanceCreationCompleted(instanceId, numberOfWaitCycle)){
                request.setStatus(GlobalParameter.Failed);
                dbService.updateRequestData(request);
                throw new TimeOutException();
            }
        }

        int psCounter = request.getNumberOfPS();
        int workerCounter = request.getNumberOfWorker();
        ArrayList<String> psIPList = new ArrayList<>();
        ArrayList<String> workerIPList = new ArrayList<>();
        ArrayList<String> psInstanceIDList = new ArrayList<>();
        ArrayList<String> workerInstanceIDList = new ArrayList<>();
        for(AwsInstance instance : instanceList){
            deploymentService.startInstance(instance.getInstanceId());
            if(psCounter != 0){
                psInstanceIDList.add(instance.getInstanceId());
                psIPList.add(instance.getInstanceIPAddress() + GlobalParameter.InstancePortForTensorflow);
                psCounter--;
            }
            else if(workerCounter != 0){
                workerInstanceIDList.add(instance.getInstanceId());
                workerIPList.add(instance.getInstanceIPAddress() + GlobalParameter.InstancePortForTensorflow);
                workerCounter--;
            }
        }
        if(psIPList.size() != 0 && workerIPList.size() != 0){
            this.executeDistributedTraining(new ArrayList<Thread>(), request.getModelName(), psIPList, workerIPList, psInstanceIDList, workerInstanceIDList, request);
        }
        else{
            System.out.println("Cannot create ps and workers");
        }
        int numberOfCompletedWorkers = 0;
        while(numberOfCompletedWorkers < workerInstanceIDList.size()){
            for(String instanceId : workerInstanceIDList){
                List<AwsCommand> awsTrainingCommandList = commandDBService.getTrainingCommandsByInstanceId(instanceId);
                for(AwsCommand trainingCommand : awsTrainingCommandList){
                    String status = commandService.getCommandStatus(trainingCommand.getCommandId());
                    if(status.equals(GlobalParameter.Success)){
                        numberOfCompletedWorkers++;
                        continue;
                    }
                    else if(status.equals(GlobalParameter.Failed)){
                        request.setStatus(GlobalParameter.Failed);
                        dbService.updateRequestData(request);
                        return;
                    }
                    Thread.sleep(GlobalParameter.AwsCommandWaitTime);
                }
            }
        }
        request.setStatus(GlobalParameter.Success);
        dbService.updateRequestData(request);
        for(String instanceId : psInstanceIDList){
            deploymentService.stopInstance(instanceId);
        }
        for(String instanceId : workerInstanceIDList){
            deploymentService.stopInstance(instanceId);
        }
    }

    @Async
    public void executeDistributedTraining(ArrayList<Thread> threads, String modelName, ArrayList<String> psIPs, ArrayList<String> workerIPs, final ArrayList<String> psInstanceIDs, ArrayList<String> workerInstanceIDs, Request request) throws InterruptedException, SQLException {
        Boolean isTrainingCommand = true;
        String installTensorflow = "pip3 install tensorflow";
        String killPreviousPythonProcess = "killall -9 python3";
        String importTrainingAlgorithm = "echo \""+request.getTrainingAlgorithm()+"\" > distributed-tensorflow/trainingalgorithm.py";
        String importDataPreprocessing = "echo \""+request.getDataPreprocessing()+"\" > distributed-tensorflow/datapreprocessing.py";
        String importAdditionalInfo = "echo \""+request.getAdditionalInfo()+"\" > distributed-tensorflow/additionalinfo.py";
        String psIPList = psIPs.get(0);
        for(int i = 1; i < psIPs.size(); i++){
            psIPList += ',' + psIPs.get(i);
        }
        String workerIPList = workerIPs.get(0);
        for(int i = 1; i < workerIPs.size(); i++){
            workerIPList += ',' + workerIPs.get(i);
        }
        String outputFolder = modelName + '_' + new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss").format(new java.util.Date());
        String outputDirectory = GlobalParameter.TensorflowOutputParentDirectory + modelName + '/' + outputFolder;
        // haven't added data dir
        String command = "python3 distributed-tensorflow/distributed-deep-learning.py --ps_hosts=" + psIPList + " --worker_hosts=" + workerIPList + " --job_name=<job_name> --task_index=<task_index> --model_dir=" + outputDirectory;

        for(int i = 0; i < psInstanceIDs.size(); i++){
            String psCommand = command;
            psCommand = psCommand.replace("<job_name>","ps");
            psCommand = psCommand.replace("<task_index>", Integer.toString(i));
            ArrayList<String> commandList = new ArrayList<>();
            commandList.add(installTensorflow);
            commandList.add(killPreviousPythonProcess);
            commandList.add(importTrainingAlgorithm);
            commandList.add(importDataPreprocessing);
            commandList.add(importAdditionalInfo);
            commandList.add(psCommand);
            CommandRunnable commandRunnable = new CommandRunnable(psInstanceIDs.get(i), "ps", commandList, isTrainingCommand);
            Thread thread = new Thread(commandRunnable);
            thread.start();
        }

        for(int i = 0; i < workerInstanceIDs.size(); i++){
            String workerCommand = command;
            workerCommand = workerCommand.replace("<job_name>","worker");
            workerCommand = workerCommand.replace("<task_index>", Integer.toString(i));
            ArrayList<String> commandList = new ArrayList<>();
            commandList.add(installTensorflow);
            commandList.add(killPreviousPythonProcess);
            commandList.add(importTrainingAlgorithm);
            commandList.add(importDataPreprocessing);
            commandList.add(importAdditionalInfo);
            commandList.add(workerCommand);
            CommandRunnable commandRunnable = new CommandRunnable(workerInstanceIDs.get(i), "worker", commandList, isTrainingCommand);
            Thread thread = new Thread(commandRunnable);
            thread.start();
            threads.add(thread);
        }

        String outputZip = "model.zip";
        String zipPath = modelName + '/' + outputFolder;
        String downloadUrl = GlobalParameter.ModelDownloadHost + zipPath + outputZip;
        request.setDownloadUrl(downloadUrl);
        request.setStatus(GlobalParameter.Training);
        dbService.updateRequestData(request);

        for(Thread thread : threads){
            thread.join();
        }
    }
}
