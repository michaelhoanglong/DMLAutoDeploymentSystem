<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 28/1/18
  Time: 1:56 AM
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
                        <div class="title_right">
                            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right">
                                <a href="/data">
                                    <div style="text-align: right;">
                                        <img src="<c:url value="/resources/production/images/reload-icon.png"/>" alt="..." style="width: 10%;">
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="clearfix"></div>

                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="x_panel">
                                <div class="x_content">

                                    <!-- start data list -->
                                    <div class="table-responsive">
                                        <table class="table table-striped projects">
                                        <thead>
                                        <tr>
                                            <th style="width: 1%">ID</th>
                                            <th style="width: 20%">Model Name</th>
                                            <th style="width: 20%">Status</th>
                                            <th style="width: 20%">Download Url</th>
                                            <th style="width: 20%">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${data}" var="item">
                                            <tr>
                                                <td>${item.id}</td>
                                                <td>${item.modelName}</td>
                                                <td>
                                                    <c:if test="${item.status=='Initializing'}">
                                                        <button type="button" class="btn btn-warning btn-xs">Initializing</button>
                                                    </c:if>
                                                    <c:if test="${item.status=='Training'}">
                                                        <button type="button" class="btn btn-info btn-xs">Training</button>
                                                    </c:if>
                                                    <c:if test="${item.status=='Success'}">
                                                        <button type="button" class="btn btn-success btn-xs">Completed</button>
                                                    </c:if>
                                                    <c:if test="${item.status=='Failed'}">
                                                        <button type="button" class="btn btn-danger btn-xs">Failed</button>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:if test="${item.status!='Success'}">
                                                        <button type="button" class="btn btn-info btn-xs" style="width: 40%;">Pending</button>
                                                    </c:if>
                                                    <c:if test="${item.status=='Success'}">
                                                        <a href="${item.downloadUrl}">
                                                            <small  style="font-style: italic">download here</small>
                                                        </a>
                                                    </c:if>
                                                </td>
                                                <td>
                                                        <form:form action="/data" method="post" name="data-form-request" modelAttribute="requestFormData" commandName="requestFormData">
                                                            <div style="display: none;">
                                                                <form:input type="text" name="request-form-requestId" path="id" value="${item.id}"/>
                                                            </div>
                                                            <button type="submit" name="terminate-button" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Terminate </button>
                                                        </form:form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <!-- end project list -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </jsp:body>
    </t:template>
</html>