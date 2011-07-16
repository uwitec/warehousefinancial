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
	
    	#niv_li
    	{
    		//float:left;
    		background-image :url(/FLECM/imgs/li2.gif);
    		
    		width:200;
    		height:30
    	}
    	#niv_li img
    	{
    		position:relative;
    		left:0px;
    		top:0px;
    	}
    	#niv_li  a
    	{
    		position:relative;
    		width:170px;
    		left:70px;
    		top:6px;
    		font-size:14px;
    		text-decoration: none;
    	}
    	.line{
				border:0;
				border-bottom:1px dashed #ccc;
				height:5px;
				margin:0;
				padding:0;
				margin-bottom:5px;
				font-size:1px;
				line-height:0px;
				clear:both;		
			}
    	
</style>
<script type="text/javascript">
			var mjAry = {};
			mjAry['-1'] = '公开';
			mjAry['0'] = '密级';
			mjAry['1'] = '机密';
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
				fields:["id","sendname","messagetitle","messageinfo","annexlist","annexpath"]
			});
 			//代办工作
 			var dbgzStore = new Ext.data.JsonStore({ 
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url:contextPath+'/oa/gwgl/gwglAction.do?method=dbgzByPage',
				fields:["id","mj","bt","ngtime","state"]
			});
			//已办工作
			var wcStore = new Ext.data.JsonStore({ 
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url:contextPath+'/oa/gwgl/gwglAction.do?method=YBGwxxPage',
				fields:["id","mj","bt","ngtime","state"]
			});
			//在办公文
			var zbwgStore = new Ext.data.JsonStore({ 
				autoDestroy: true,
				pruneModifiedRecords:true,
				root:'dataList',
				totalProperty:'totalCount',
		        url:contextPath+'/oa/gwgl/gwglAction.do?method=getAllZbgw',
				fields:["id","mj","bt","ngtime","state"]
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
					width : 400,
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
						
							columnWidth:.33,
								items: [
									{
									tools:[{
										id : "close",
										handler : function(e, target, panel) {
										panel.ownerCt.remove(panel, true);
										},
											qtip : "关闭"
										}],
										title: '<img src="/FLECM/imgs/more/login/user.png"/>&nbsp;&nbsp;用户信息',
										bodyStyle:'padding:5px',
										height: 190,
										layout: 'fit',
										bbar:[{
												id:'nove-next',
												text:'个人信息',
												height:25,
												handler:function(){
													var id=210;
													var hrefs=contextPath+'/rsgl/ryglAction.do?method=initRygrxx&id='+id;
													loadTab(hrefs,'个人信息',id);
												},
												disable:true
											},'->',{
												id:'nove-perv',
												text:'安全中心',
												height:25,
												handler: function(){
												var id=42025;
													var hrefs=contextPath+'/system/yhglAction.do?method=initYhmmwh&id='+id;
													loadTab(hrefs,'密码维护',id);
												},
												disable:true
											}],
										html:'<table style="font-size:12px;width:260px"><tr><td rowspan="5"><img src="../imgs/default-zp.jpg" width="100" height="120"/></td></tr><tr><td>姓名:${yh.yhxm}</td></tr><tr><td>用户名:${yh.dlzh}</td></tr><tr><td>邮箱:${yh.dzyx}</td></tr><tr><td>手机号:${yh.sjh}</td></tr></table>'
									}
								]
						
								
							},
							{
								columnWidth:.37,
								items: [
									{
										tools:[{
										id : "close",
										handler : function(e, target, panel) {
										panel.ownerCt.remove(panel, true);
										},
											qtip : "关闭"
										}],
										title: '<img src="/FLECM/imgs/more/menus/info/message_menu.png"/>&nbsp;&nbsp;通知公告',
										height: 193,
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
													header:'标题',
													width:185,
													sortable:true
												},{
													dataIndex:'sendname',
													header:'内容',
													width:135,
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
													loadTab(hrefs,'公告通知',id);
												}
											}
										})
									}
									
								]
							},	{
						
							columnWidth:.3,
								items: [
									{
									tools:[{
										id : "close",
										handler : function(e, target, panel) {
										panel.ownerCt.remove(panel, true);
										},
											qtip : "关闭"
										}],
										title: '天气',
										bodyStyle:'padding:5px',
										height: 203,
										layout: 'fit',
										bodyStyle : 'padding:1px;background:url(/FLECM/imgs/whether.gif) no-repeat top center'
										
									}
								]
						
								
							},
							{
								columnWidth:.33,
								items:[{
										title : '系统导航',
								height :445,
								
									items: [
										{
											html:'<div id="niv_li"><a href="#" target="_blank">协同办公系统</a>'
										},{
											html:'<div  class="line"></div><div id="niv_li"><a href="#" target="_blank">教育管理系统</a>'
										},{
											html:'<div class="line"></div><div id="niv_li"><a href="#" target="_blank" >教育资源库</a>'
										},{
											html:'<div class="line"></div><div id="niv_li"><a href="#" target="_blank" >网络教学平台</a>'
										},{
											html:'<div class="line"></div><div id="niv_li"><a href="#" target="_blank" >精品课程平台</a>'
										},{
											html:'<div class="line"></div><div id="niv_li"><a href="#" target="_blank" >教育博客</a>'
										},{
											html:'<div class="line"></div><div id="niv_li"><a href="#" target="_blank" >教育论坛</a>'
										},{
											html:'<div class="line"></div><div id="niv_li"><a href="#" target="_blank" >平安校园</a>'
										}
									]
								}]
							},
							{
								columnWidth:.37,
								items: [
									{
									title : '<img src="/FLECM/imgs/more/menus/admin/book_menu.png">&nbsp;&nbsp;我的工作',
									height : 200,
									layout : 'fit',
								
									items : new Ext.TabPanel({
										activeTab : 0,
										enableTabScroll : true,
										frame : true,
										items : [{
											title : '待办工作',
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
													var hrefs=contextPath+'/oa/gwgl/gwglAction.do?method=initJiaogao';
													loadTab(hrefs,'校稿',id);
												},
												disable:true
											}],
											items : new Ext.grid.GridPanel({
														height: 180,
														stripeRows : true,
														autoScroll : true,
														trackMouseOver : true,
														border : false,
														loadMask : true,
														sm:dngzcheckboxModel,
														cm : new Ext.grid.ColumnModel([{
																	dataIndex : 'mj',
																	header : '工作类别',
																	width : 120,
																	renderer:function(value){
																		return mjAry[value];
																	},
																	sortable : true
																}, {
																	dataIndex : 'bt',
																	header : '工作标题',
																	width : 240,
																	sortable : true
																}, {
																	dataIndex : 'ngtime',
																	header : '发布时间',
																	width : 115,
																	sortable : true
																}]),
														store:dbgzStore,
														listeners:{
														'click':function(){
															var r = dngzcheckboxModel.getSelected();
															var state = r.get('state');
															if(r==null || r.length==0){
																return;
															}
															var id=31866;
															var hrefs=contextPath+'/oa/gwgl/gwglAction.do?method=initJiaogao';
															loadTab(hrefs,'校稿',id);
														}
													}
													})
										}, {
											title : '已办工作',
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
											items : new Ext.grid.GridPanel({
														height: 180,
														stripeRows : true,
														autoScroll : true,
														trackMouseOver : true,
														border : false,
														loadMask : true,
														sm:dngzcheckboxModel,
														cm : new Ext.grid.ColumnModel([{
																	dataIndex : 'mj',
																	header : '工作类别',
																	width : 120,
																	renderer:function(value){
																		return mjAry[value];
																	},
																	sortable : true
																}, {
																	dataIndex : 'bt',
																	header : '工作标题',
																	width : 240,
																	sortable : true
																}, {
																	dataIndex : 'ngtime',
																	header : '发布时间',
																	width : 115,
																	sortable : true
																}]),
														store:wcStore,
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
										}, {
											title : '在办工作',
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
											items : new Ext.grid.GridPanel({
														height: 180,
														stripeRows : true,
														autoScroll : true,
														trackMouseOver : true,
														border : false,
														loadMask : true,
														sm:dngzcheckboxModel,
														cm : new Ext.grid.ColumnModel([{
																	dataIndex : 'mj',
																	header : '工作类别',
																	width : 120,
																	renderer:function(value){
																		return mjAry[value];
																	},
																	sortable : true
																}, {
																	dataIndex : 'bt',
																	header : '工作标题',
																	width : 240,
																	sortable : true
																}, {
																	dataIndex : 'ngtime',
																	header : '发布时间',
																	width : 115,
																	sortable : true
																}]),
														
														store:zbwgStore,
														listeners:{
														'click':function(){
															var r = dngzcheckboxModel.getSelected();
															var state = r.get('state');
															if(r==null || r.length==0){
																return;
															}
															var id=31866;
															var hrefs=contextPath+'/oa/gwgl/gwglAction.do?method=jumpByState&state='+state;
															loadTab(hrefs,'s',id);
														}
													}
													})
										}]
								
									})
									}
								]
							},
							{
								columnWidth:.3,
								items: [
									{
										title: '<img src="/FLECM/imgs/more/communicate/letter.png"/>&nbsp;&nbsp;短信息',
										height: 190,
										layout: 'fit',
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
													var hrefs=contextPath+'/oa/message/index.html';
													loadTab(hrefs,'消息管理',id);
												},
												disable:true
											}],
										items: new Ext.grid.GridPanel({
											stripeRows: true,
											autoScroll:true,
											trackMouseOver:true,
											border:false,
									        loadMask: true,
									        autoWidth :true,
									        sm:wjxzcheckboxModel,
											cm:new Ext.grid.ColumnModel([
											{
													dataIndex:'messagetitle',
													header:'标题',
													width:105,
													sortable:true
												},
												{
													dataIndex:'messageinfo',
													header:'内容',
													width:150,
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
													var id=34327;
													var hrefs=contextPath+'/oa/message/index.html';
													loadTab(hrefs,'消息管理',id);
												}
											}
										})
									}
								]
							},
							{
								columnWidth:.37,
								items: [
									{
								title : '<img src="/FLECM/imgs/more/btn/flow/view.png"/>&nbsp;&nbsp;新闻公告',
								height : 223,
								layout : 'fit',
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
													var hrefs=contextPath+'/oa/message/index.html';
													loadTab(hrefs,'消息管理',id);
												},
												disable:true
											}],
								items : new Ext.grid.GridPanel({
											stripeRows : true,
											autoScroll : true,
											trackMouseOver : true,
											border : false,
											loadMask : true,
											cm : new Ext.grid.ColumnModel([{
														dataIndex : 'xwType',
														header : '新闻类别',
														width : 80,
														sortable : true
													}, {
														dataIndex : 'xwTitle',
														header : '新闻标题',
														width : 140,
														sortable : true
													}, {
														dataIndex : 'xwTime',
														header : '发布时间',
														sortable : true
													}]),
											store : new Ext.data.JsonStore({
														data : [{
																	id : '1',
																	xwType : '招聘新闻',
																	xwTitle : '章华公司招聘机电焊工',
																	xwTime : '2010-01-12'
																}, {
																	id : '2',
																	xwType : '招聘新闻',
																	xwTitle : '城采公司招聘机电工20名',
																	xwTime : '2010-02-01'
																}, {
																	id : '3',
																	xwType : '招聘新闻',
																	xwTitle : '华富机电有限公司招聘数控机工',
																	xwTime : '2010-02-19'
																}, {
																	id : '4',
																	xwType : '招聘新闻',
																	xwTitle : '凡微公司招聘机电工5名',
																	xwTime : '2010-01-18'
																}, {
																	id : '5',
																	xwType : '招聘新闻',
																	xwTitle : '宗申公司招聘机电工',
																	xwTime : '2010-01-12'
																}],
														fields : ["id", "xwType", 'xwTitle', 'xwTime']
													})
										})
									}
								]
							},{
								columnWidth:.3,
								items: [{
									title : '<img src="/FLECM/imgs/more/flag/department.jpg"/>&nbsp;&nbsp;通信录',
									height : 220,
									layout : 'anchor',
									bodyStyle : 'padding:5px 5px 5px 5px',
									items : [{
												xtype : 'fieldset',
												title : '<span style="font-size:14px">最近联系人</span>',
												layout : 'fit',
												height : 140,
												items : [{
															xtype : 'checkboxgroup',
															columns : 2,
															items : [{
																		fieldLabel : '',
																		boxLabel : '张学辉',
																		name : ''
																	}, {
																		fieldLabel : '',
																		boxLabel : '李世民',
																		name : ''
																	}, {
																		fieldLabel : '',
																		boxLabel : '王军',
																		name : ''
																	}, {
																		fieldLabel : '',
																		boxLabel : '张学辉',
																		name : ''
																	}, {
																		fieldLabel : '',
																		boxLabel : '李世民',
																		name : ''
																	}, {
																		fieldLabel : '',
																		boxLabel : '王军',
																		name : ''
																	}, {
																		fieldLabel : '',
																		boxLabel : '张学辉',
																		name : ''
																	}, {
																		fieldLabel : '',
																		boxLabel : '李世民',
																		name : ''
																	}, {
																		fieldLabel : '',
																		boxLabel : '王军',
																		name : ''
																	}]
														}]
											}],
												buttons : [{
															text : '<span style="font-size:12px">发消息</span>'
														}, {
															text : '<span style="font-size:12px">打开通信录</span>'
														}]
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
		    wcStore.load({params:{start:0,limit:15}});
		    zbwgStore.load({params:{start:0,limit:15}});
		    
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
