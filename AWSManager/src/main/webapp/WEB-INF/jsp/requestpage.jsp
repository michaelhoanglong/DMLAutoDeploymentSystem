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
                                        <%--<div class="item form-group">--%>
                                            <%--<label path="dataUrl" class="control-label col-md-3 col-sm-3 col-xs-12" for="data-url">Data Url <span class="required">*</span>--%>
                                            <%--</label>--%>
                                            <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                                                <%--<form:input path="dataUrl" type="url" id="data-url" name="data-url" placeholder="www.googledrive.com/...." required="required" class="form-control col-md-7 col-xs-12" value="http://"/>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
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
                                            <label path="" class="control-label col-md-3 col-sm-3 col-xs-12" for="myeditor1">Data Preprocessing <span class="required">*</span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12" style="min-height: 200px;">
                                                <div id="myeditor1" ></div>
                                                <div id="code1" style="display:none;"># Important!
# Do not change the input, output and function name below
# &lt;YourCode&gt;

# &lt;/YourCode&gt;
def getImageData():
    # Input your code here to return image data
    # &lt;YourCode&gt;

    # &lt;/YourCode&gt;
    return imageData
# &lt;YourCode&gt;

# &lt;/YourCode&gt;
def getLabelData():
    # Input your code here to return image data
    # &lt;YourCode&gt;

    # &lt;/YourCode&gt;
    return labelData
# &lt;YourCode&gt;

# &lt;/YourCode&gt;
                                                </div>
                                            </div>
                                            <form:textarea path="dataPreprocessing" id="data-preprocessing" class="form-control col-md-7 col-xs-12" style="display: none;"/>
                                        </div>
                                        <div class="item form-group">
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <form:input path="requesterName" id="requester-name" class="form-control col-md-7 col-xs-12" style="display: none;" name="requester-name" type="text" value="Admin"/>
                                            </div>
                                        </div>
                                        <div class="item form-group">
                                            <label path="" class="control-label col-md-3 col-sm-3 col-xs-12" for="myeditor">Training Algorithm <span class="required">*</span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12" style="min-height: 200px;">
                                                <div id="myeditor" ></div>
                                                <div id="code" style="display:none;"># Important!
# Do not change the input, output and function name below

import tensorflow as tf

def getDataTensorPlaceHolder():
    # Input your code here to return dataTensorPlaceHolder
    # &lt;YourCode&gt;

    # &lt;/YourCode&gt;
    return dataTensorPlaceHolder
# &lt;YourCode&gt;

# &lt;/YourCode&gt;
def getLabelTensorPlaceHolder():
    # Input your code here to return labelTensorPlaceHolder
    # &lt;YourCode&gt;

    # &lt;/YourCode&gt;
    return labelTensorPlaceHolder
# &lt;YourCode&gt;

# &lt;/YourCode&gt;
def trainingAlgorithm(dataTensorPlaceHolder):
    # Input your code here to return 2 values y_conv and keep_prob
    # &lt;YourCode&gt;

    # &lt;/YourCode&gt;
    return y_conv
# &lt;YourCode&gt;

# &lt;/YourCode&gt;
</div>
                                            </div>
                                            <form:textarea path="trainingAlgorithm" id="training-algorithm" class="form-control col-md-7 col-xs-12" style="display: none;"/>
                                        </div>
                                        <div class="item form-group">
                                            <label path="" class="control-label col-md-3 col-sm-3 col-xs-12" for="myeditor2">Additional Info <span class="required">*</span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12" style="min-height: 200px;">
                                                <div id="myeditor2" ></div>
                                                <div id="code2" style="display:none;"># Important!
# Additional Info to import to your other parts
# &lt;YourCode&gt;

# &lt;/YourCode&gt;
</div>
                                            </div>
                                            <form:textarea path="additionalInfo" id="additional-info" class="form-control col-md-7 col-xs-12" style="display: none;"/>
                                        </div>
                                        <div class="ln_solid"></div>
                                        <div class="form-group">
                                            <div class="col-md-6 col-md-offset-3">
                                                <button type="reset" class="btn btn-primary">Cancel</button>
                                                <button id="send" type="button" class="btn btn-success">Submit</button>
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
    <script src="<c:url value="/resources/Text-Editor/src-noconflict/ace.js"/>"></script>
    <script>
<%--//        function refresheditor() {--%>
<%--//            document.getElementById("myeditor").innerHTML="<div id='editor'></div>"; document.getElementById("editor").innerHTML=document.getElementById("code").innerHTML;--%>
<%--//            var editor     = ace.edit("editor")--%>
<%--//                , session  = editor.getSession()--%>
<%--//                , Range    = ace.require("ace/range").Range;--%>
<%--//            ranges    = [];--%>
<%--//--%>
<%--//            var text= document.getElementById("code").innerHTML.split("\n");--%>
<%--//            var starts=[0],ends=[];--%>
<%--//            ////--%>
<%--//            function before(obj, method, wrapper) {--%>
<%--//                var orig = obj[method];--%>
<%--//                obj[method] = function() {--%>
<%--//                    var args = Array.prototype.slice.call(arguments);--%>
<%--//                    return wrapper.call(this, function(){--%>
<%--//                        return orig.apply(obj, args);--%>
<%--//                    }, args);--%>
<%--//                }--%>
<%--//                return obj[method];--%>
<%--//            }--%>
<%--//            function intersects(range) {--%>
<%--//                return editor.getSelectionRange().intersects(range);--%>
<%--//            }--%>
<%--//            function intersectsRange(newRange) {--%>
<%--//                for (i=0;i<ranges.length;i++)--%>
<%--//                    if(newRange.intersects(ranges[i]))--%>
<%--//                        return true;--%>
<%--//                return false;--%>
<%--//            }--%>
<%--//            function preventReadonly(next, args) {--%>
<%--//                for(i=0;i<ranges.length;i++){if (intersects(ranges[i])) return;}--%>
<%--//                next();--%>
<%--//            }--%>
<%--//            function onEnd(position){--%>
<%--//                var row = position["row"],column=position["column"];--%>
<%--//                for (i=0;i<ranges.length;i++)--%>
<%--//                    if(ranges[i].end["row"] == row && ranges[i].end["column"]==column)--%>
<%--//                        return true;--%>
<%--//                return false;--%>
<%--//            }--%>
<%--//            function outSideRange(position){--%>
<%--//                var row = position["row"],column=position["column"];--%>
<%--//                for (i=0;i<ranges.length;i++){--%>
<%--//                    if(ranges[i].start["row"]< row && ranges[i].end["row"]>row)--%>
<%--//                        return false;--%>
<%--//                    if(ranges[i].start["row"]==row && ranges[i].start["column"]<column){--%>
<%--//                        if(ranges[i].end["row"] != row || ranges[i].end["column"]>column)--%>
<%--//                            return false;--%>
<%--//                    }--%>
<%--//                    else if(ranges[i].end["row"] == row&&ranges[i].end["column"]>column){--%>
<%--//                        return false;--%>
<%--//                    }--%>
<%--//                }--%>
<%--//                return true;--%>
<%--//            }--%>
<%--//            ////--%>
<%--//            text.forEach(function(line,index){--%>
<%--//                if((line.indexOf("&lt;YourCode&gt;") !== -1))ends.push(index);--%>
<%--//                if((line.indexOf("&lt;/YourCode&gt;") !== -1))starts.push(index+1);--%>
<%--//            });--%>
<%--//            ends.push(text.length);--%>
<%--//            for(i=0;i<starts.length;i++){--%>
<%--//                ranges.push(new Range(starts[i], 0,ends[i] ,0));--%>
<%--//            }--%>
<%--//            ranges.forEach(function(range){session.addMarker(range, "readonly-highlight");});--%>
<%--//            session.setMode("ace/mode/python");--%>
<%--//            editor.keyBinding.addKeyboardHandler({--%>
<%--//                handleKeyboard : function(data, hash, keyString, keyCode, event) {--%>
<%--//                    if (Math.abs(keyCode) == 13 && onEnd(editor.getCursorPosition())){--%>
<%--//                        return false;--%>
<%--//                    }--%>
<%--//                    if (hash === -1 || (keyCode <= 40 && keyCode >= 37)) return false;--%>
<%--//                    for(i=0;i<ranges.length;i++){--%>
<%--//                        if (intersects(ranges[i])) {--%>
<%--//                            return {command:"null", passEvent:false};--%>
<%--//                        }--%>
<%--//                    }--%>
<%--//                }--%>
<%--//            });--%>
<%--//            before(editor, 'onPaste', preventReadonly);--%>
<%--//            before(editor, 'onCut',   preventReadonly);--%>
<%--//            for(i=0;i<ranges.length;i++){--%>
<%--//                ranges[i].start  = session.doc.createAnchor(ranges[i].start);--%>
<%--//                ranges[i].end    = session.doc.createAnchor(ranges[i].end);--%>
<%--//                ranges[i].end.$insertRight = true;--%>
<%--//            }--%>
<%--//--%>
<%--//            var old$tryReplace = editor.$tryReplace;--%>
<%--//            editor.$tryReplace = function(range, replacement) {--%>
<%--//                return intersectsRange(range)?null:old$tryReplace.apply(this, arguments);--%>
<%--//            }--%>
<%--//            var session = editor.getSession();--%>
<%--//            var oldInsert = session.insert;--%>
<%--//            session.insert = function(position, text) {--%>
<%--//                return oldInsert.apply(this, [position, outSideRange(position)?text:""]);--%>
<%--//            }--%>
<%--//            var oldRemove = session.remove;--%>
<%--//            session.remove = function(range) {--%>
<%--//                return intersectsRange(range)?false:oldRemove.apply(this, arguments);--%>
<%--//            }--%>
<%--//            var oldMoveText = session.moveText;--%>
<%--//            session.moveText = function(fromRange, toPosition, copy) {--%>
<%--//                if (intersectsRange(fromRange) || !outSideRange(toPosition)) return fromRange;--%>
<%--//                return oldMoveText.apply(this, arguments);--%>
<%--//            }--%>
<%--//--%>
<%--//            $(document).ready(function(){--%>
<%--//                $("#send").click(function(){--%>
<%--//                    $("#training-algorithm").val(editor.getValue());--%>
<%--//                    console.log(editor.getValue());--%>
<%--//                    console.log($("#training-algorithm").val());--%>
<%--//                    $("#request-form").submit();--%>
<%--//                });--%>
<%--//            });--%>
<%--//        }--%>
<%--//        refresheditor();--%>

        document.getElementById("myeditor").innerHTML="<div id='editor'></div>"; document.getElementById("editor").innerHTML=document.getElementById("code").innerHTML;
        var editor = ace.edit("editor");
        editor.setTheme("ace/theme/tomorrow_night");
        editor.session.setMode("ace/mode/python");

        document.getElementById("myeditor1").innerHTML="<div id='editor1'></div>"; document.getElementById("editor1").innerHTML=document.getElementById("code1").innerHTML;
        var editor1 = ace.edit("editor1");
        editor1.setTheme("ace/theme/tomorrow_night");
        editor1.session.setMode("ace/mode/python");

        document.getElementById("myeditor2").innerHTML="<div id='editor2'></div>"; document.getElementById("editor2").innerHTML=document.getElementById("code2").innerHTML;
        var editor2 = ace.edit("editor2");
        editor2.setTheme("ace/theme/tomorrow_night");
        editor2.session.setMode("ace/mode/python");

        $(document).ready(function(){
            $("#send").click(function(){
                $("#training-algorithm").val(editor.getValue());
                console.log(editor.getValue());
                console.log($("#training-algorithm").val());

                $("#data-preprocessing").val(editor1.getValue());
                console.log(editor1.getValue());
                console.log($("#data-preprocessing").val());

                $("#additional-info").val(editor2.getValue());
                console.log(editor2.getValue());
                console.log($("#additional-info").val());

                $("#request-form").submit();
            });
        });
    </script>
</html>


