<%@page import="ans.client.system.user.SYS_USER"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>

<%
Object user=request.getSession().getAttribute("LgUser");
String userID=null;
if(user!=null){
    userID=((SYS_USER)user).getUSER_ID();
}

%>
        
<!DOCTYPE html>
<head>
    <meta name='gwt:property' content='locale=zh_CN'>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>smartGwt-lichKing Ria系统 V1.0</title>
    <link rel="shortcut icon" type="image/ico" href="favicon.ico" />
    <!--CSS for loading message at application Startup-->
    <style type="text/css">
        html, body { overflow:hidden }
        #loadingWrapper {
            position: absolute;
            top: 40%;
            width: 100%;
            text-align: center;
            z-index: 900001;
        }
        #loading {
            margin: 0 auto;
            border: 1px solid #ccc;
            width: 160px;
            padding: 2px;
            text-align: left;
        }

        #loading a {
            color: #225588;
        }

        #loading .loadingIndicator {
            background: white;
            font: bold 13px tahoma, arial, helvetica;
            padding: 10px;
            margin: 0;
            height: auto;
            color: #444;
        }

        #loadingMsg {
            font: normal 10px arial, tahoma, sans-serif;
        }
    </style>
    
    <script>var isomorphicDir = "projMgr/sc/"</script>
	<link rel="stylesheet" href="projMgr/Showcase.css">
	
	
	<link rel="stylesheet" href="projMgr/bootstrap-3.3.4/css/bootstrap.min.css">
	
	<link rel="stylesheet" href="projMgr/font-awesome-4.3.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="projMgr/bootstrap-3.3.4/adminLTE.min.css">
	<link rel="stylesheet" href="projMgr/bootstrap-3.3.4/adminLTE_all-skins.css">
	
	
	
	<script type="text/javascript" src="projMgr/jquery/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="projMgr/bootstrap-3.3.4/js/bootstrap.min.js"></script>
	
<!--
<script type="text/javascript" src="projMgr/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript" src="projMgr/Highcharts-4.0.3/js/highcharts.js"></script>

<script>
 		Timeline_ajax_url="projMgr/timeline_2.3.1/timeline_ajax/simile-ajax-api.js";
 		Timeline_urlPrefix='projMgr/timeline_2.3.1/timeline_js/';       
 		Timeline_parameters='bundle=true';
</script>
<script src="projMgr/timeline_2.3.1/timeline_js/timeline-api.js?bundle=true" type="text/javascript"></script>
-->


</head>
<body id="bgImg" background="images/homeBg.jpg" style="background-size:cover;" class="skin-blue">
<iframe id="__gwt_historyFrame" style="width:0;height:0;border:0"></iframe>
<iframe id="__printingFrame" style="width:0;height:0;border:0"></iframe>
<!--add loading indicator while the app is being loaded-->
<div id="loadingWrapper">
<div id="loading">
    <div class="loadingIndicator">
        <img src="images/loading.gif" style="margin-right:8px;float:left;vertical-align:top;"/>smartGwt-lichKing Ria系统<br/>
        <span id="loadingMsg">正在载入样式和图片...</span></div>
</div>
</div>
<div id="UID" val="<%=userID%>"></div>

<script type="text/javascript">document.getElementById('loadingMsg').innerHTML = '正在载入代码接口...';</script>

<!--include the SC Core API-->
<script src='projMgr/sc/modules/ISC_Core.js'></script>

<!--include SmartClient -->
<script type="text/javascript">document.getElementById('loadingMsg').innerHTML = '正在载入界面组件...';</script>
<script src='projMgr/sc/modules/ISC_Foundation.js'></script>
<script src='projMgr/sc/modules/ISC_Containers.js'></script>
<script src='projMgr/sc/modules/ISC_Grids.js'></script>
<script src='projMgr/sc/modules/ISC_Forms.js'></script>



<script type="text/javascript">document.getElementById('loadingMsg').innerHTML = '正在载入数据接口...';</script>
<script src='projMgr/sc/modules/ISC_DataBinding.js'></script>

<!--load skin-->
<script type="text/javascript">document.getElementById('loadingMsg').innerHTML = '正在载入皮肤...';</script>

<script type="text/javascript" src="projMgr/sc/skins/EnterpriseBlue/load_skin.js?isc_version=11.1p_2017-08-22.js"></script>

<!--include the application JS-->
<script type="text/javascript">document.getElementById('loadingMsg').innerHTML = '正在载入实例<br>请稍等...';</script>
<script type="text/javascript" src="projMgr/projMgr.nocache.js"></script>


</body>
</html>