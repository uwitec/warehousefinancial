<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>print template</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <!--media=print 这个属性可以在打印时有效-->
        <style media=print>
            .Noprint {
                display: none;
            } .PageNext {
                page-break-after: always;
            }
        </style>
        <style>
            .tdp {
                border-bottom: 1 solid #000000;
                border-left: 1 solid #000000;
                border-right: 0 solid #ffffff;
                border-top: 0 solid #ffffff;
            } .tabp {
                border-color: #000000 #000000 #000000 #000000;
                border-style: solid;
                border-top-width: 2px;
                border-right-width: 2px;
                border-bottom-width: 1px;
                border-left-width: 1px;
            } .NOPRINT {
                font-family: "宋体";
                font-size: 9pt;
            }
        </style>
		<script type="text/javascript">
			document.write('<OBJECT id="WebBrowser"'+
			' classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" width="0">'+
  			'</OBJECT>');
		</script>
