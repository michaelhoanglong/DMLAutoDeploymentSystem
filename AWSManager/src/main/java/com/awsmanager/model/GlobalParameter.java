package com.awsmanager.model;

public class GlobalParameter {
    public static String SqlConnectionString = "jdbc:mysql://localhost:3306/awsmanagerdb";
    public static String SqlUser = "root";
    public static String SqlPassword = "fyp2018";
    public static String SecurityGroupName = "JavaSecurityGroup";
    public static String SecurityGroupDescription = "My Java Security Group for Distributed Machine Learning";
    public static String AmiId = "ami-325d2e4e";
    public static String KeyName = "awskeysingapore";
    public static String Arn = "Arn:aws:iam::772776665147:instance-profile/ssmrole";
    public static String AwsCommandTimeOut = "128800";
    public static String AwsDocumentName = "AWS-RunShellScript";
    public static String AwsSSMEndpoint = "https://ssm.ap-southeast-1.amazonaws.com/";
    public static Integer AwsCommandWaitTime = 10000;
    public static String TensorflowOutputParentDirectory = "s3-drive/";
    public static String AwsWorkingDirectory = "/home/ubuntu";
    public static String InstancePortForTensorflow = ":2000";
    public static String Training = "Training";
    public static String Initializing = "Initializing";
    public static String Failed = "Failed";
    public static String Success = "Success";
    public static String ModelDownloadHost = "https://s3-ap-southeast-1.amazonaws.com/michaelfyp/";
    public static int NumberOfCycleToWaitForCreatingInstance = 100;
    public static String InProgress = "InProgress";
}
