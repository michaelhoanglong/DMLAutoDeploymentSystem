<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 27/2/18
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- jQuery -->
<script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/resources/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>
<!-- FastClick -->
<script src="<c:url value="/resources/vendors/fastclick/lib/fastclick.js"/>"></script>
<!-- NProgress -->
<script src="<c:url value="/resources/vendors/nprogress/nprogress.js"/>"></script>
<!-- Chart.js -->
<script src="<c:url value="/resources/vendors/Chart.js/dist/Chart.min.js"/>"></script>
<!-- gauge.js -->
<script src="<c:url value="/resources/vendors/gauge.js/dist/gauge.min.js"/>"></script>
<!-- bootstrap-progressbar -->
<script src="<c:url value="/resources/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"/>"></script>
<!-- iCheck -->
<script src="<c:url value="/resources/vendors/iCheck/icheck.min.js"/>"></script>
<!-- Skycons -->
<script src="<c:url value="/resources/vendors/skycons/skycons.js"/>"></script>
<!-- Flot -->
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.js"/>"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.pie.js"/>"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.time.js"/>"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.stack.js"/>"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.resize.js"/>"></script>
<!-- Flot plugins -->
<script src="<c:url value="/resources/vendors/flot.orderbars/js/jquery.flot.orderBars.js"/>"></script>
<script src="<c:url value="/resources/vendors/flot-spline/js/jquery.flot.spline.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/flot.curvedlines/curvedLines.js"/>"></script>
<!-- DateJS -->
<script src="<c:url value="/resources/vendors/DateJS/build/date.js"/>"></script>
<!-- JQVMap -->
<script src="<c:url value="/resources/vendors/jqvmap/dist/jquery.vmap.js"/>"></script>
<script src="<c:url value="/resources/vendors/jqvmap/dist/maps/jquery.vmap.world.js"/>"></script>
<script src="<c:url value="/resources/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"/>"></script>
<!-- bootstrap-daterangepicker -->
<script src="<c:url value="/resources/vendors/moment/min/moment.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.js"/>"></script>
<!-- bootstrap-progressbar -->
<script src="<c:url value="/resources/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"/>"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        if(!$("#mainbody").hasClass("nav-sm")){
            $('.child_menu').css('display','block');
        }
    });
</script>