<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title> 供应商/销售商统计</title>
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
	String type = (String)request.getAttribute("type");
	String khid = (String)request.getAttribute("khid");
	 %>
	<script type="text/javascript">
		Ext.onReady(function(){
			new Ext.QuickTips.init();
			
			var st = '<%=stime %>';
			var et = '<%=etime %>';
			var tp = '<%=type %>';
			var kh = '<%=khid %>';
			
			var comstore = new Ext.data.SimpleStore({
				fields:['khlx','sname'],
				data:[['0','供货商(厂家)'],['1','经销商(客户)']]
			})
			//comstore.load();
			
			var sjmcstore = new Ext.data.JsonStore({
				url:contextPath+'/jxc/KhwhAction.do?method=getAllKyxxByJoin',
				fields:['id',"jdbh","jdmc","dh"]
			})
			
			var g = new Ext.Panel({
				id: "ShowQxkb",
                title: "从&nbsp;&nbsp;<%=stime %>&nbsp;&nbsp;到&nbsp;<%=etime %>，产品&nbsp;<%=type.equals("0") ? "进货" : "销售" %>&nbsp;统计",
                border: false,
                layout:'fit',
                autoScroll:true,
                autoLoad: {
                    url: contextPath + "/jxc/QueryAction.do?method=getCustomers&kh="+kh+"&type="+tp+"&ftime="+st+"&etime="+et,
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
				   		value:st,
				   		readOnly:true,
				   		format:'Y-m-d'
				   },{
						fieldLabel:'结束时间',
				   		xtype:'datefield',
				   		name:'etime',
				   		value:et,
				   		readOnly:true,
				   		format:'Y-m-d'
				   },{
						fieldLabel:'客户类型',
						hiddenName:'khlx',
						xtype:'combo',
						readOnly:true,
						triggerAction:'all',
						displayField:"sname",
						valueField:'khlx',
						store:comstore,
						mode:'local',
				   		listeners:{
							'select':function(cob,r,ind){
								sjmcstore.removeAll();
								if(r.get('khlx') == ''){
									return;
								}
								Ext.apply(sjmcstore.baseParams,{column:'khlb',khlb:r.get('khlx')});
								sjmcstore.reload();
							}
						}
				   },{
						fieldLabel:'客户名称',
						xtype:'combo',
						hiddenName:'cpid',
						mode:'local',//
						readOnly:true,
						displayField:"jdmc",
						valueField:'jdmc',
						store:sjmcstore
					}
				],
				buttons:[
					{
						text:'查 询',
						handler:function(){
							var va = Ext.encode(RelativeActionView.form.getValues());
							st = RelativeActionView.form.getValues()['ftime'];
							et = RelativeActionView.form.getValues()['etime'];
							tp = RelativeActionView.form.getValues()['khlx'];
							kh = RelativeActionView.form.getValues()['cpid'];
							window.location.href = contextPath + "/jxc/QueryAction.do?method=getInitCustom&kh="+kh+"&tp="+tp+"&ftime="+st+"&etime="+et;
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
				window.location.href = contextPath + "/jxc/QueryAction.do?method=getInitCustom&kh="+kh+"&type="+tp+"&ftime="+st+"&etime="+et+"";
			})
			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			setNavigate("统计报表 >> 销售/供货上统计","销售/供货上统计");
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
