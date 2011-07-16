<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>产品统计</title>
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
	String cpid = (String)request.getAttribute("cpid");
	 %>
	<script type="text/javascript">
		Ext.onReady(function(){
			new Ext.QuickTips.init();
			
			var st = '<%=stime %>';
			var et = '<%=etime %>';
			var cp = '<%=cpid %>';
			
			var zclxStore = new Ext.data.JsonStore({
		      	url:contextPath+'/zcgl/zclxAction.do?method=getZclxList',
				fields:['id',"lxdm","lxmc","bz"]
			});
			zclxStore.load();
			
			var zcxxStore = new Ext.data.JsonStore({
		      	url:contextPath+'/zcgl/zcxxAction.do?method=getZcxxList',
				fields:['id',"zcdm","zcmc","zclx","zcgg","zcdw","scrq","bz"]
			});
			
			var g = new Ext.Panel({
				id: "ShowQxkb",
                title: "从&nbsp;&nbsp;<%=stime %>&nbsp;&nbsp;到&nbsp;<%=etime %>，产品统计",
                border: false,
                layout:'fit',
                autoScroll:true,
                autoLoad: {
                    url: contextPath + "/jxc/QueryAction.do?method=getProducts&cpid="+cp+"&ftime="+st+"&etime="+et,
                    method: "Post"
                }
            });
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
				   		format:'Y-m-d'
				   },{
						fieldLabel:'结束时间',
				   		xtype:'datefield',
				   		name:'etime',
				   		format:'Y-m-d'
				   },{
				   		fieldLabel:'费用类型',
						hiddenName:'fylx',
						xtype:'combo',
						readOnly:true,
						triggerAction:'all',
						displayField:"lxmc",
						valueField:'id',
						store:zclxStore,
						mode:'local',
				   		listeners:{
							'select':function(cob,r,ind){
								//zclxStore.removeAll();
								if(r.get('id') == ''){
									return;
								}
								Ext.apply(zcxxStore.baseParams,{column:'zclx',zclx:r.get('id')});
								zcxxStore.reload();
							}
						}
				   },{
						fieldLabel:'资产名称',
						xtype:'combo',
						hiddenName:'cpid',
						mode:'local',//
						readOnly:true,
						displayField:"zcmc",
						valueField:'id',
						store:zcxxStore
					}
				],
				buttons:[
					{
						text:'查 询',
						handler:function(){
							var va = Ext.encode(RelativeActionView.form.getValues());
							st = RelativeActionView.form.getValues()['ftime'];
							et = RelativeActionView.form.getValues()['etime'];
							cp = RelativeActionView.form.getValues()['cpid'];
							window.location.href = contextPath + "/jxc/QueryAction.do?method=getInitProducts&cpid="+cp+"&ftime="+st+"&etime="+et+"";
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
				window.location.href = contextPath + "/jxc/QueryAction.do?method=getInitProducts&cpid="+cp+"&ftime="+st+"&etime="+et+"";
			})
			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			setNavigate("统计报表 >> 产品统计报表","产品统计报表");
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
