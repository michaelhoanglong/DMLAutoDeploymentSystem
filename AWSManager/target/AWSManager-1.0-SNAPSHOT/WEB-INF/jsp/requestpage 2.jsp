<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 22/1/18
  Time: 4:14 PM
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
                            <h3>Model Training Request</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>

                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="x_panel">
                                <div class="x_content">

                                    <form:form id="request-form" action="/request" method="post" class="form-horizontal form-label-left" modelAttribute="userInput">

                                        <span class="section">Request Info</span>

                                        <div class="item form-group">
                                            <label path="modelName" class="control-label col-md-3 col-sm-3 col-xs-12" for="model-name">Model Name <span class="required">*</span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <form:input path="modelName" id="model-name" class="form-control col-md-7 col-xs-12" data-validate-length-range="6" data-validate-words="2" name="model-name" placeholder="Model Name..." required="required" type="text"/>
                                            </div>
                                        </div>
                                        <div class="item form-group">
                                            <label path="dataUrl" class="control-label col-md-3 col-sm-3 col-xs-12" for="data-url">Data Url <span class="required">*</span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <form:input path="dataUrl" type="url" id="data-url" name="data-url" placeholder="www.googledrive.com/...." required="required" class="form-control col-md-7 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="item form-group">
                                            <label path="numberOfPS" class="control-label col-md-3 col-sm-3 col-xs-12" for="ps">Parameter Servers <span class="required">*</span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <form:input path="numberOfPS" type="number" id="ps" name="ps" placeholder="At least 1 parameter servers is required." required="required" class="form-control col-md-7 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="item form-group">
                                            <label path="numberOfWorker" class="control-label col-md-3 col-sm-3 col-xs-12" for="worker">Worker Servers <span class="required">*</span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <form:input path="numberOfWorker" type="number" id="worker" name="worker" placeholder="At least 2 worker servers is required." class="form-control col-md-7 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="item form-group">
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <form:input path="requesterName" id="requester-name" class="form-control col-md-7 col-xs-12" style="display: none;" name="requester-name" type="text" value="Admin"/>
                                            </div>
                                        </div>
                                        <div class="ln_solid"></div>
                                        <div class="form-group">
                                            <div class="col-md-6 col-md-offset-3">
                                                <button type="reset" class="btn btn-primary">Cancel</button>
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


