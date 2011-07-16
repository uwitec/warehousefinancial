<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title> 月盈利查询</title>
	<!-- Extjs导入 -->
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css"></link>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bjSelector.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/rySelector.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/lsTree.js"></script>
	<style type="text/css">
		.store-info{
			background:url('${pageContext.request.contextPath}/imgs/store-info.gif') left top no-repeat;
		}
		body{
			font-size:12px;
		}
	</style>
	<%
	String stime = (String)request.getAttribute("stime");
	String etime = (String)request.getAttribute("etime");
	String xs = (String)request.getAttribute("xs");
	 %>
	<script type="text/javascript">
		Ext.onReady(function(){
			new Ext.QuickTips.init();
			
			var st = '<%=stime %>';
			var et = '<%=etime %>';
			var xs = '<%=xs %>';
			
			var g = new Ext.Panel({
				id: "ShowQxkb",
                title: "从&nbsp;&nbsp;<%=stime %>&nbsp;&nbsp;到&nbsp;<%=etime %>，<%=xs.equals("")?"人员": xs %>费用统计",
                border: false,
                layout:'fit',
                autoScroll:true,
                autoLoad: {
                    url: contextPath + "/jxc/QueryAction.do?method=getXs&xs="+xs+"&ftime="+st+"&etime="+et,
                    method: "Post"
                }
            });
            var xsmcstore = new Ext.data.JsonStore({
				url:contextPath+'/jxc/FywhAction.do?method=getAllXfdxByJoin&sid=0',
				fields:['xfid',"xfmc"]
			})
			xsmcstore.load();
			//添加列表
			dataPanel.add(g);
			initDataTbar([_refresh,_condition]);
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            //title: '',
	            collapsible: true,
	            autoScroll:true,
	            autoHeight:true,
				labelWidth:70,
				labelAlign:'right',
				defaultType:'textfield',
				defaults:{
					anchor:'85%'
				},items:[
					{
				   		fieldLabel:'开始时间',
				   		xtype:'datefield',
				   		name:'ftime',
				   		value:st,
				   		format:'Y-m-d'
				   },{
						fieldLabel:'结束时间',
				   		xtype:'datefield',
				   		name:'etime',
				   		value:et,
				   		format:'Y-m-d'
				   },{
						fieldLabel:'客户名称',
						xtype:'combo',
						hiddenName:'xs',
						mode:'local',//
						readOnly:true,
						displayField:"xfmc",
						valueField:'xfmc',
						store:xsmcstore
					}
				],
				buttons:[
					{
						text:'查 询',
						handler:function(){
							var va = Ext.encode(RelativeActionView.form.getValues());
							st = RelativeActionView.form.getValues()['ftime'];
							et = RelativeActionView.form.getValues()['etime'];
							xs = RelativeActionView.form.getValues()['xs'];
							window.location.href = contextPath + "/jxc/QueryAction.do?method=getInitXs&xs="+xs+"&ftime="+st+"&etime="+et+"";
						}
					},{
						text:'重 置',
						handler:function(){
							RelativeActionView.form.reset();
						}
					}
				]
			}));
			
			/*事件控制*****************************/
			
			//刷新事件
			getRefreshBtn().on('click',function(){
				window.location.href = contextPath + "/jxc/QueryAction.do?method=getInitXs&xs="+xs+"&ftime="+st+"&etime="+et+"";
			})
			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			setNavigate("统计报表 >> 人员销售月报表","人员销售月报表");
		});
		
	</script>
  </head>
  <body>
  <input type="hidden" id='qx' value='${qxlx}'>
  <input type="hidden" id='mkid' value='${mkid}'>
  <table width="100%" height='100%' >
  	<tr>
  		<td align="center" valign="middle">
			<div id='loadDiv' style="font-size:12px;">
			<img width='50' height='50' src='${pageContext.request.contextPath}/imgs/login-load.gif'>
			数据加载中，请稍后 . . .
			</div>
		</td>
  	</tr>
  </table>				
  </body>
</html>
