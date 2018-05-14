package com.awsmanager.service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.util.IOUtils;
import com.awsmanager.exception.TimeOutException;
import com.awsmanager.model.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.scheduling.annotation.Async;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeploymentService {
    private AmazonEC2 ec2Client;
    private DBService dbService = new DBService();
    private CommandService commandService = new CommandService();

    public DeploymentService(){
        ec2Client = AmazonEC2ClientBuilder.defaultClient();
        try{
            if(dbService.getSettingDataByName(GlobalParameter.SecurityGroupName) == null){
                createSecurityGroup(GlobalParameter.SecurityGroupName,GlobalParameter.SecurityGroupDescription);
            }
            if(dbService.getSettingDataByName(GlobalParameter.KeyName) == null){
                //createKeyPair(GlobalParameter.KeyName);
            }
        }catch (AmazonEC2Exception e){
            System.out.println("Cannot create Security Group or Key Pair!");
            e.printStackTrace();
        }catch (SQLException e){
            System.out.println("SQL operation errors");
            e.printStackTrace();
        }
    }

    private int createSecurityGroup(String groupName, String groupDescription){
        try{
            CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest();
            csgr.withGroupName(groupName).withDescription(groupDescription);
            CreateSecurityGroupResult createSecurityGroupResult = ec2Client.createSecurityGroup(csgr);
            authorizeSecurityGroupIngress(groupName);
            SettingData settingData = new SettingData(groupName, createSecurityGroupResult.getGroupId());
            return dbService.createSettingData(settingData);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    private void authorizeSecurityGroupIngress(String securityGroupName){
        IpPermission ipPermission = new IpPermission();

        IpRange ipRange1 = new IpRange().withCidrIp("0.0.0.0/0");
        //IpRange ipRange2 = new IpRange().withCidrIp("150.150.150.150/32");

        ipPermission.withIpv4Ranges(Arrays.asList(new IpRange[] {ipRange1}))
                .withIpProtocol("tcp")
                .withFromPort(22)
                .withToPort(2001);
        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = new AuthorizeSecurityGroupIngressRequest();
        authorizeSecurityGroupIngressRequest.withGroupName(securityGroupName).withIpPermissions(ipPermission);
        ec2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
    }

    private int createKeyPair(String keyName){
        try{
            CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();
            createKeyPairRequest.withKeyName(keyName);
            CreateKeyPairResult createKeyPairResult = ec2Client.createKeyPair(createKeyPairRequest);
            KeyPair keyPair = createKeyPairResult.getKeyPair();
            SettingData settingData = new SettingData(keyName, keyPair.getKeyMaterial());
            return dbService.createSettingData(settingData);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public String getUserData(){
//        String userData = "#!/bin/bash\n" +
//                "# set up tensorflow\n" +
//                "sudo apt-get update\n" +
//                "sudo apt-get install -y python3-pip\n" +
//                "sudo apt-get install -y python3-dev\n" +
//                "\n" +
//                "# Configure locale setting\n" +
//                "export LC_ALL=\"en_US.UTF-8\"\n" +
//                "export LC_CTYPE=\"en_US.UTF-8\"\n" +
//                "sudo dpkg-reconfigure locales\n" +
//                "\n" +
//                "# Install awscli and tensorflow\n" +
//                "pip3 install awscli\n" +
//                "pip3 install tensorflow\n" +
//                "\n" +
//                "# Set up ssm for remote connection\n" +
//                "wget https://s3.amazonaws.com/ec2-downloads-windows/SSMAgent/latest/debian_amd64/amazon-ssm-agent.deb\n" +
//                "sudo dpkg -i amazon-ssm-agent.deb\n" +
//                "\n" +
//                "# run training\n" +
//                "git clone https://github.com/michaelhoanglong/distributed-tensorflow.git /home/ubuntu/tensorflow";
        try {
//            String base64UserDataOld = new String(Base64.encodeBase64(userData.getBytes("UTF-8")),"UTF-8");
            ClassLoader classLoader = getClass().getClassLoader();
            String userData = IOUtils.toString(classLoader.getResourceAsStream("InstanceStartUp.sh"));
            String base64UserData = new String(Base64.encodeBase64(userData.getBytes("UTF-8")),"UTF-8");
            return base64UserData;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    @Async
    public List<AwsInstance> createInstance(int numberOfInstances, Request request) throws SQLException {
        try{
            RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                    .withImageId(GlobalParameter.AmiId)
                    .withInstanceType(InstanceType.T22xlarge)
                    .withKeyName(GlobalParameter.KeyName)
                    .withSecurityGroupIds(dbService.getSettingDataByName(GlobalParameter.SecurityGroupName).getValue())
                    .withMaxCount(numberOfInstances)
                    .withMinCount(numberOfInstances)
                    .withUserData(getUserData())
                    .withIamInstanceProfile(new IamInstanceProfileSpecification().withArn(GlobalParameter.Arn));

            RunInstancesResult runResponse = ec2Client.runInstances(runInstancesRequest);
            List<AwsInstance> awsInstanceList = new ArrayList<AwsInstance>();
            List<Instance> instances = runResponse.getReservation().getInstances();
            for(Instance instance : instances){
                AwsInstance awsInstance = new AwsInstance(instance.getInstanceId(), instance.getPrivateIpAddress(), request.getId());
                awsInstanceList.add(awsInstance);
                dbService.createInstance(awsInstance);
            }
            return awsInstanceList;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Async
    public void startInstance(String instanceId){
        StartInstancesRequest request = new StartInstancesRequest().withInstanceIds(instanceId);
        ec2Client.startInstances(request);
    }

    @Async
    public void stopInstance(String instanceId){
        StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(instanceId);
        ec2Client.stopInstances(request);
    }

    @Async
    public void terminateInstance(String instanceId){
        TerminateInstancesRequest request = new TerminateInstancesRequest().withInstanceIds(instanceId);
        ec2Client.terminateInstances(request);
    }

    public boolean isInstanceCreationCompleted(String instanceId, int numberOfWaitCycle) throws InterruptedException, TimeOutException, SQLException {
        if(numberOfWaitCycle == 0){
            return false;
        }
        ArrayList<String> commands = new ArrayList<>();
        commands.add("ls distributed-tensorflow/");
        Boolean isTrainingCommand = false;
        AwsCommand awsCommand = commandService.sendCommand(instanceId,"test for instance creation",commands, isTrainingCommand);
        String commandId = awsCommand.getCommandId();
        String status = commandService.getCommandStatus(commandId);
        System.out.println("from deployment service: " + status);
        while(!status.equals(GlobalParameter.Success)){
            if(status.equals( GlobalParameter.Failed)){
                return isInstanceCreationCompleted(instanceId, numberOfWaitCycle-1);
            }
            Thread.sleep(GlobalParameter.AwsCommandWaitTime);
            status = commandService.getCommandStatus(commandId);
        }
        return true;
    }
}
