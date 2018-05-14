<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/3/18
  Time: 2:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-3 left_col">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <a href="/request" class="site_title"><i class="fa fa-paw"></i> <span>DML System!</span></a>
        </div>

        <div class="clearfix"></div>

        <!-- menu profile quick info -->
        <div class="profile clearfix">
            <div class="profile_pic">
                <img src="<c:url value="/resources/production/images/usericon.png"/>" alt="..." class="img-circle profile_img">
            </div>
            <div class="profile_info">
                <span>Welcome,</span>
                <h2>Admin</h2>
            </div>
        </div>
        <!-- /menu profile quick info -->

        <br />

        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <h3>Menu</h3>
                <ul class="nav side-menu">
                    <li class="active"><a><i class="fa fa-jsfiddle"></i> Training <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="/request">Model Training Request</a></li>
                            <li><a href="/data">Model Training Status</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav side-menu">
                    <li class="active"><a><i class="fa fa-cubes"></i> Serving <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="/serving">Serving Trained Model</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <!-- /sidebar menu -->

        <!-- /menu footer buttons -->
        <div class="sidebar-footer hidden-small">
            <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
            </a>
            <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
            </a>
            <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
            </a>
            <a data-toggle="tooltip" data-placement="top" title="Logout" href="login.html">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
            </a>
        </div>
        <!-- /menu footer buttons -->
    </div>
</div>

<!-- top navigation -->
<div class="top_nav">
    <div class="nav_menu">
        <nav>
            <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <li class="">
                    <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <img src="<c:url value="/resources/production/images/usericon.png"/>" alt="">Admin
                        <span class=" fa fa-angle-down"></span>
                    </a>
                    <%--<ul class="dropdown-menu dropdown-usermenu pull-right">--%>
                        <%--<li><a href="#"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>--%>
                    <%--</ul>--%>
                </li>
            </ul>
        </nav>
    </div>
</div>
<!-- /top navigation -->

<!-- footer content -->
<footer>
    <div class="pull-right">
        Mobile Distributed Machine Learning System - Michael Le
    </div>
    <div class="clearfix"></div>

</footer>
<!-- /footer content -->

