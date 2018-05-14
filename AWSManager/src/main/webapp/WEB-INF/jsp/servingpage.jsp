<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 27/3/18
  Time: 11:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../layout/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html lang="en">
    <t:template>
        <jsp:attribute name="header">
            <%@include file="../layout/link.jsp"%>
        </jsp:attribute>
        <jsp:attribute name="navbar">
            <%@include file="../layout/navbar.jsp"%>
        </jsp:attribute>
        <jsp:attribute name="script">
            <%@include file="../layout/script.jsp"%>
        </jsp:attribute>
        <jsp:body>
            <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <h3>Model Training Status</h3>
                    </div>
                    <%--<div class="title_right">--%>
                       <%----%>
                    <%--</div>--%>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="x_panel">
                            <div class="x_content">
                                <form:form id="serving-form" action="/serving" method="post" class="form-horizontal form-label-left" modelAttribute="servingInput">
                                    <span class="section">Serving Model</span>
                                    <div class="item form-group">
                                        <label path="imageUrl" class="control-label col-md-3 col-sm-3 col-xs-12" for="img-url">Test image Url <span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:input path="imageUrl" id="img-url" class="form-control col-md-7 col-xs-12" data-validate-length-range="6" data-validate-words="2" name="img-url" placeholder="http://your_image_address" required="required" type="url"/>
                                        </div>
                                    </div>
                                    <div class="item form-group">
                                        <label path="imageUrl" class="control-label col-md-3 col-sm-3 col-xs-12" for="img-url">Test image Size <span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:input path="imageSize" id="img-size" class="form-control col-md-7 col-xs-12" data-validate-length-range="6" data-validate-words="2" name="img-size" placeholder="(Width x Height x Number of Channels)" required="required" type="text"/>
                                        </div>
                                    </div>
                                    <div class="item form-group">
                                        <label path="modelName" class="control-label col-md-3 col-sm-3 col-xs-12" for="model-name">Trained Model Name <span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <%--<form:input path="modelName" id="model-name" class="form-control col-md-7 col-xs-12" data-validate-length-range="6" data-validate-words="2" name="model-name" placeholder="your_model_name" required="required" type="text"/>--%>
                                            <form:select path="modelName" id="model-name" class="form-control col-md-7 col-xs-12">
                                                <option value="Select" label="--Please Select--">--Please Select--</option>
                                                <option value="Mnist" label="Mnist">Mnist</option>
                                                <option value="Cifar10" label="Cifar10">Cifar10</option>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="item form-group">
                                        <label path="modelUrl" class="control-label col-md-3 col-sm-3 col-xs-12" for="model-url">Trained Model Url <span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:input path="modelUrl" id="model-url" class="form-control col-md-7 col-xs-12" data-validate-length-range="6" data-validate-words="2" name="model-url" placeholder="http://your_model_address" required="required" type="url"/>
                                        </div>
                                    </div>

                                    <div class="item form-group">
                                        <label path="result" class="control-label col-md-3 col-sm-3 col-xs-12" for="result">Prediction Result <span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <form:input path="result" id="result" class="form-control col-md-7 col-xs-12" data-validate-length-range="6" data-validate-words="2" name="result" placeholder="Result will be displayed here" required="required" type="text" readonly="true"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-6 col-md-offset-3">
                                            <button type="reset" class="btn btn-primary">Clear</button>
                                            <button id="send" type="submit" class="btn btn-success">Submit</button>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </jsp:body>
    </t:template>
</html>

