package com.awsmanager.service;

import com.awsmanager.model.AwsInstance;
import com.awsmanager.model.Request;
import com.awsmanager.model.GlobalParameter;
import com.awsmanager.model.SettingData;

import java.sql.*;
import java.util.ArrayList;

public class DBService {
    protected Connection connection;
    public DBService(){
        this.connection = this.connect();
    }
    protected Connection connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return null;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(GlobalParameter.SqlConnectionString,GlobalParameter.SqlUser, GlobalParameter.SqlPassword);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            return connection;
        } else {
            System.out.println("Failed to make connection!");
            return null;
        }
    };

    public void closeConnection(){
        try{
            this.connection.close();
        }
        catch (SQLException e){
            System.out.println("Cannot close connection!");
            e.printStackTrace();
            return;
        }
    }
    public int createRequestData(Request input) throws SQLException {
        String sqlquery = "Insert into Request (Requester,ModelName,Worker,Ps,Status,ExecutionTime,Accuracy,DownloadUrl) values(?,?,?,?,?,?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        this.setCreateAndUpdateStatement(statement,input);
        statement.executeUpdate();
        ResultSet generatedKey = statement.getGeneratedKeys();
        if(generatedKey.next()){
            return generatedKey.getInt(1);
        }
        else return 0;
    }

    // TODO: Should input all required fields of Request Object before calling update Request table
    public int updateRequestData(Request input) throws SQLException{
        String sqlquery = "UPDATE Request SET Requester=?, ModelName=?, Worker=?,Ps=?, Status=?, ExecutionTime=?,Accuracy=?, DownloadUrl=? WHERE  id=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        this.setCreateAndUpdateStatement(statement,input);
        statement.setInt(9,input.getId());
        return statement.executeUpdate();
    }

    private void setCreateAndUpdateStatement(PreparedStatement stmt, Request input) throws SQLException {
        stmt.setString(1,input.getRequesterName());
        stmt.setString(2,input.getModelName());
        stmt.setInt(3,input.getNumberOfWorker());
        stmt.setInt(4,input.getNumberOfPS());
        stmt.setString(5,input.getStatus());
        stmt.setFloat(6,input.getExecutionTime());
        stmt.setFloat(7,input.getAccuracy());
        stmt.setString(8,input.getDownloadUrl());
    }

    public ArrayList<Request> getRequestData() throws SQLException {
        String sqlquery = "Select id, Requester, ModelName, Worker, Ps, Status, ExecutionTime, Accuracy, DownloadUrl from Request";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlquery);
        ArrayList<Request> dataList = new ArrayList<Request>();
        while(resultSet.next()){
            Request data = new Request();
            data.setId(resultSet.getInt("id"));
            data.setRequesterName(resultSet.getString("Requester"));
            data.setModelName(resultSet.getString("ModelName"));
            data.setNumberOfWorker(resultSet.getInt("Worker"));
            data.setNumberOfPS(resultSet.getInt("Ps"));
            data.setStatus(resultSet.getString("Status"));
            data.setExecutionTime(resultSet.getFloat("ExecutionTime"));
            data.setAccuracy(resultSet.getFloat("Accuracy"));
            data.setDownloadUrl(resultSet.getString("DownloadUrl"));
            dataList.add(data);
        }
        return dataList;
    }

    public int deleteRequestData(int id) throws SQLException{
        String sqlquery = "delete from Request where id=?";
        PreparedStatement statement = connection.prepareStatement(sqlquery);
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    public int createSettingData(SettingData settingData) throws SQLException{
        String sqlquery = "Insert into Setting (Name,Value) values(?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setString(1,settingData.getName());
        statement.setString(2,settingData.getValue());
        return statement.executeUpdate();
    }

    public int updateSetting(SettingData settingData) throws SQLException {
        String sqlquery = "Update Setting set Name=?, Value=? where id=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setString(1, settingData.getName());
        statement.setString(2,settingData.getValue());
        statement.setInt(3,settingData.getId());
        return statement.executeUpdate();
    }

    public SettingData getSettingDataByName(String settingName) throws SQLException {
        String sqlquery = "Select * from Setting where Name=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setString(1,settingName);
        ResultSet resultSet = statement.executeQuery();
        // Setting Name is Unique
        if(resultSet.next()){
            SettingData settingData = new SettingData();
            settingData.setId(resultSet.getInt("id"));
            settingData.setName(resultSet.getString("Name"));
            settingData.setValue(resultSet.getString("Value"));
            return settingData;
        }else {
            return null;
        }
    }

    public int deleteSettingByName(String settingName) throws SQLException {
        String sqlquery = "Delete from Setting where Name=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setString(1,settingName);
        return statement.executeUpdate();
    }

    public int createInstance(AwsInstance instance) throws SQLException {
        String sqlquery = "Insert into Instance (InstanceId, InstanceIPAddress, RequestId) values (?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setString(1,instance.getInstanceId());
        statement.setString(2,instance.getInstanceIPAddress());
        statement.setInt(3,instance.getRequestId());
        return statement.executeUpdate();
    }

    public ArrayList<AwsInstance> getInstances() throws SQLException {
        String sqlquery = "Select * from Instance";
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlquery);
        ArrayList<AwsInstance> instanceList = new ArrayList<AwsInstance>();
        while(resultSet.next()){
            AwsInstance instance = new AwsInstance();
            instance.setId(resultSet.getInt("id"));
            instance.setInstanceId(resultSet.getString("InstanceId"));
            instance.setInstanceIPAddress(resultSet.getString("InstanceIPAddress"));
            instance.setRequestId(resultSet.getInt("RequestId"));
            instanceList.add(instance);
        }
        return instanceList;
    }

    public ArrayList<AwsInstance> getInstancesByRequestId(int requestId) throws SQLException {
        String sqlquery = "Select * from Instance where RequestId=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setInt(1,requestId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<AwsInstance> instanceList = new ArrayList<AwsInstance>();
        while(resultSet.next()){
            AwsInstance instance = new AwsInstance();
            instance.setId(resultSet.getInt("id"));
            instance.setInstanceId(resultSet.getString("InstanceId"));
            instance.setInstanceIPAddress(resultSet.getString("InstanceIPAddress"));
            instance.setRequestId(resultSet.getInt("RequestId"));
            instanceList.add(instance);
        }
        return instanceList;
    }

    public int deleteInstance(AwsInstance instance) throws SQLException{
        String sqlquery = "Delete from Instance where id=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setInt(1, instance.getId());
        return statement.executeUpdate();
    }

    public int deleteInstanceByRequestId(int requestId) throws SQLException {
        String sqlquery = "Delete from Instance where RequestId=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setInt(1, requestId);
        return statement.executeUpdate();
    }

    public int updateInstancesRequestId(int newRequestId, int oldRequestId) throws SQLException{
        String sqlquery = "UPDATE Instance Set RequestId=? WHERE RequestId=?";
        PreparedStatement statement = this.connection.prepareStatement(sqlquery);
        statement.setInt(1, newRequestId);
        statement.setInt(2, oldRequestId);
        return statement.executeUpdate();
    }
}
