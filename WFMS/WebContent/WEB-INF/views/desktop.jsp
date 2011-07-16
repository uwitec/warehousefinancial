<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<title>用户桌面</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/portal/Portal.css"
	type="text/css"></link>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bachStoreLoad.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/portal/Portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/portal/PortalColumn.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/portal/Portlet.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/list_viewport.js"></script>
<style type="text/css">
.new-tab {
	background-image:
		url('${pageContext.request.contextPath}/imgs/newTab.gif');
}
</style>
<script type="text/javascript">
 			//下载附件
			function down_fj(lj){
				window.location.href = contextPath + '/oa/gwgl/gwglAction.do?method=downloadGwgzFj&path=' + lj;
			}
			//显示附件路径
			function showPath(path){
				document.getElementById("showPath").innerText=path;
				document.getElementById("anchor_path").style.display='block';
			}
			//隐藏内容
			function hiddenPath(){
				document.getElementById("showPath").innerText="";
				document.getElementById("anchor_path").style.display='block';
			}
			//清空面板中的内容
			function clearFj(){
				document.getElementById("anchor_path").innerHTML="";
			}
			
 		Ext.onReady(function(){
 			//公告通知
 			var ggtzStore = new Ext.data.JsonStore({ 
				autoDestroy: false ,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url: contextPath+'/oa/message.do?method=getXwggByDESK&id=0',
				fields:["id","sendname","messagetitle","annexlist","annexpath"]
			});
			//文件下载
			var wjxzStore = new Ext.data.JsonStore({ 
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url: contextPath+'/oa/message.do?method=getXwggByDESK&id=0',
				fields:["id","sendname","messagetitle","annexlist","annexpath"]
			});
 			//代办工作
 			var dbgzStore = new Ext.data.JsonStore({ 
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url:contextPath+'/oa/gwgl/gwglAction.do?method=dbgzByPage',
				fields:["id","bt","state"]
			});
			var wjxzcheckboxModel = new Ext.grid.CheckboxSelectionModel({
				listeners: {
			           selectionchange: function(checkboxModel) {
			           }
			       }
			});
			
			var dngzcheckboxModel = new Ext.grid.CheckboxSelectionModel({
				listeners: {
			           selectionchange: function(checkboxModel) {
			           }
			       }
			});
			//附件WINDOW
			var fjWin = new Ext.Window({ 
					width : 450,
					height : 400,
					modal : true,
					plain : true,
					layout : 'fit',
					title : '附件列表',
					closable:false,
					closeAction : 'hide',
					items:[{
						id : 'fjForm',
						border : false,
						xtype : 'form',
						baseCls : 'x-plain',
						autoScroll : true,
						layout : 'column', 
						labelAlign : 'right',
						bodyStyle : 'padding:10px',
						defaults : {
							xtype : 'fieldset',
							anchor : '95%'
						},buttons : [{
							id : 'fj',
							text : '关  闭',
							handler : function() {
								clearFj();
								fjWin.hide();
							}
						}],items:[
						{
								id:'html_panel',
								columnWidth : 1,
								labelWidth : 75,
								border : false,
								defaultType : 'textarea',
								defaults : {
									anchor : '97%'
								},html:'<div style="margin-left:22px;" id="anchor_path">附件列表:</div><div id="showPath" style="margin-left:50px;"></div>'
						}]
					}]
				});	
				
				
			new Ext.QuickTips.init();//提示信息加载
			var vp = new Ext.Viewport({
				layout:'border',
				items:[
					{
						region:'center',
						margin:'4 4 4 4',
						border:false,
						xtype:'portal',
						bodyStyle:'background-image:url(${pageContext.request.contextPath}/imgs/bj.gif);padding:10px 0px 10px 10px',
						defaults:{
							style:'padding:0px 10px 0px 0px'
						},
						
						layout:'column',
						items:[
							{
								columnWidth:.6,
								items: [
									{
										title: '公告通知',
										height: 200,
										layout: 'fit',
										defaults:{
											border:false
										},
										bbar:[{
												id:'nove-next',
												text:'',
												height:20,
												disable:true
											},'->',{
												id:'nove-perv',
												text:'更多',
												height:20,
												handler: function(){
												var id=34327;
													var hrefs=contextPath+'/oa/message.do?method=toReceiveMessage';
													loadTab(hrefs,'讯息查看',id);
												},
												disable:true
											}],
										items:new Ext.grid.GridPanel({
											stripeRows: true,
											autoScroll:true,
											trackMouseOver:true,
											hrefTarget : 'center', 
											border:false,
											id:'textname',
									        loadMask: true,
									        sm:checkboxModel,
											cm:new Ext.grid.ColumnModel([
												{
													dataIndex:'messagetitle',
													header:'',
													width:350,
													sortable:true
												},{
													dataIndex:'sendname',
													header:'',
													width:107,
													sortable:true
												}
											]),
											store:ggtzStore,
											listeners:{'click':function(){
													var r =checkboxModel.getSelected();
													if(r==null || r.length==0){
														return;
													}
													var id=34327;
													var hrefs=contextPath+'/oa/message.do?method=toReceiveMessage';
													loadTab(hrefs,'讯息查看',id);
												}
											}
										})
									}
									
								]
							},{
								columnWidth:.4,
								items: [
									{
										title: '文件下载',
										height: 209,
										layout: 'fit',
										items: new Ext.grid.GridPanel({
											stripeRows: true,
											autoScroll:true,
											trackMouseOver:true,
											border:false,
									        loadMask: true,
									        sm:wjxzcheckboxModel,
											cm:new Ext.grid.ColumnModel([
												{
													dataIndex:'annexlist',
													header:'',
													width:195,
													sortable:true
												},{
													dataIndex:'sendname',
													header:'',
													width:100,
													sortable:true
												}
											]),
											store:wjxzStore,
											listeners:{
												'click':function(){
													var r = wjxzcheckboxModel.getSelected();
													if(r==null || r.length==0){
														return;
													}
													var names = r.get('annexlist').split(';');
													var path = r.get('annexpath');
													fjWin.show();
													for(var i=0;i<names.length;i++){
														if(path==null){
															alert("该文件不存在！");
															return ;
														}else{
															Ext.DomHelper.append(Ext.get("anchor_path"),'<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a  href="#" name="'+path+names[i]+'" onclick="down_fj(this.name)" onmouseover="hiddenPath()" onmouseout="hiddenPath()" ><font size="+1">'+names[i]+'</font></a>',true);
														}
													}
												}
											}
										})
									}
								]
							},{
								columnWidth:.6,
								items: [
									{
										title: '待办工作',
										height: 192,
										layout: 'fit',
										defaults:{
											border:false
										},
										bbar:[{
												id:'nove-next',
												text:'',
												height:20,
												disable:true
											},'->',{
												id:'nove-perv',
												text:'更多',
												height:20,
												handler: function(){
												var state=0;
													var hrefs=contextPath+'/oa/gwgl/gwglAction.do?method=jumpByState&state='+state;
													loadTab(hrefs,'校对公文',id);
												},
												disable:true
											}],
										items: new Ext.grid.GridPanel({
											stripeRows: true,
											autoScroll:true,
											trackMouseOver:true,
											border:false,
									        loadMask: true,
									        sm:dngzcheckboxModel,
											cm:new Ext.grid.ColumnModel([
												{
													dataIndex:'bt',
													header:'',
													width:457,
													sortable:true
												}
											]),
											store:dbgzStore,
											listeners:{
												'click':function(){
													var r = dngzcheckboxModel.getSelected();
													var state = r.get('state');
													if(r==null || r.length==0){
														return;
													}
													var id=31866;
													var hrefs=contextPath+'/oa/gwgl/gwglAction.do?method=jumpByState&state='+state;
													loadTab(hrefs,'校对公文',id);
												}
											}
										})
									}
								]
								
							},{
								columnWidth:.4,
								items: [
									{
										title: '用户信息',
										bodyStyle:'padding:5px',
										height: 200,
										layout: 'fit',
										html:'<br/>&nbsp&nbsp&nbsp&nbsp&nbsp${yh.username}，您好！ <br/><br/>&nbsp&nbsp&nbsp&nbsp&nbsp这是您第 <font color="red">${yh.dlcs}</font> 次登录本系统<br/><br/>&nbsp&nbsp&nbsp&nbsp&nbsp上次登录时间为：${yh.scdlsj}<br/><br/>&nbsp&nbsp&nbsp&nbsp&nbsp手&nbsp;机&nbsp;号：${yh.mobilephone}<br/><br/>&nbsp&nbsp&nbsp&nbsp&nbsp电子邮箱：${yh.email}'
									}
								]
							},{
								columnWidth:.6,
								items: [{
									title: '课表',
									height: 192,
									layout: 'fit',
									html: '<iframe width="100%" id="contentTab" height="100%" frameborder="0"  src="'+contextPath+'/jwgl_new/kbdyAction.do?method=getLskbBYXtzm"></iframe>',
									listeners:{
										'click':function(){
											var id=137;
											alert('qqqqq');
											var hrefs=contextPath+'/jwgl_new/kbdyAction.do?method=getLskbBYXtzmTojsp';
											loadTab(hrefs,'老师课表查询打印',id);
										}
									}
								}]
							}
						]
					}
				]
			});
			
			wjxzStore.on("load",function(r,id){
				wjxzStore.filterBy(function(record, id) {
			  		return record.get("annexlist") =="" || record.get("annexlist")==null ?false : true;
				});
			});
			function loadTab(href,tit,id){
				var tabMainView = parent.Ext.getCmp('tab_main_view');
				var tab = tabMainView.getComponent(id);
				if(tab){
					tabMainView.setActiveTab(tab);
				}else{
		            var p = tabMainView.add({
		                title:tit,
			            id:id+"",
			            hrefTarget : "_blank",
			            iconCls: 'new-tab',
			            html: '<iframe width="100%" id="contentTab'+id+'" height="100%" frameborder="0"  src="'+href+'"></iframe>',
			            closable:true
		            });
		            tabMainView.setActiveTab(p);
				}
			}
			ggtzStore.load({params:{start:0,limit:5}});
			wjxzStore.load({params:{start:0,limit:5}});
		    dbgzStore.load({params:{start:0,limit:15}});
		    
		});
 	</script>

</head>
<body>
<div id="window-win"></div>
	<table width="100%" height='100%'>
		<tr>
			<td align="center" valign="middle">
				<div id='loadDiv' style="font-size: 12px;">
					<img width='50' height='50'
						src="<c:url value='/imgs/login-load.gif'/>">
					数据初始化，请稍后 . . .
				</div>
			</td>
		</tr>
	</table>
	
</body>
</html>
