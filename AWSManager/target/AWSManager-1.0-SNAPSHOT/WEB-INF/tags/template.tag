<%@tag description="template tag" pageEncoding="UTF-8" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="navbar" fragment="true" %>
<%@attribute name="script" fragment="true" %>
<head>
    <jsp:invoke fragment="header"/>
</head>
<body id="mainbody" class="nav-md footer_fixed">
    <div class="container body">
        <div class="main_container">
            <jsp:invoke fragment="navbar"/>
            <%--Partial view content--%>
            <jsp:doBody/>
        </div>
    </div>
    <jsp:invoke fragment="script"/>
</body>
