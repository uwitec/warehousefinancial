<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title></title>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/edit_viewport.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bachStoreLoad.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bjTree.js"></script>
	<style type="text/css">
		.store-info{
			background:url('${pageContext.request.contextPath}/imgs/store-info.gif') left top no-repeat;
		}
		body{
			font-size:12px;
		}
	</style>
	<script type="text/javascript">
		Ext.onReady(function(){
			new Ext.QuickTips.init();//提示信息加载
			var mainFrame = new MianFrame();
			setNavigate("人事管理 >> 职工管理 >> 修改职工信息","本页面提供批量修改职工信息功能");
			var bmStore =  new Ext.data.JsonStore({
				url:contextPath+'/rsgl/bmglAction.do?method=getAllBmForList',
				fields:["depid","name"]
			});
			var zwdjStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=ZWDJDM',
				fields:["lxdm","dmmc"]
			});
			var zyjszgStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=ZYJSZGDJDM',
				fields:["lxdm","dmmc"]
			});
			var gqryjsdjStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=GQRYDJDM',
				fields:["lxdm","dmmc"]
			});
			var rydjStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=RYLBDM',
				fields:["lxdm","dmmc"]
			});
			var whcdStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=WHCDDM',
				fields:["lxdm","dmmc"]
			});
			var xwStore = new Ext.data.JsonStore({
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=XWDM',
				fields:["lxdm","dmmc"]
			});
			
			var editForm = new Ext.FormPanel({
				layout:'column',
				width:700,
				bodyStyle:'padding:10px',
				border:false,
				defaults:{
					border:false,
					labelAlign:'right',
					columnWidth:.5,
					defaultType:'textfield',
					layout:'form',
					labelWidth:110,
					defaults:{
						anchor:'95%'
					}
				},
				items:[
					{
						items:[
							{
								name:'rybh',
								emptyText:'必填',
								allowBlank:false,
								fieldLabel:'人员编号',
								listeners:{
									"blur":function(){
									}
								}
							},{
								name:'ryxm',
								emptyText:'必填',
								allowBlank:false,
								fieldLabel:'人员姓名'
							},{
								hiddenName:'member.role.depart.depid',
								emptyText:'必填',
								allowBlank:false,
								fieldLabel:'部门',
								xtype:'combo',
								readOnly:true,
								type:'remote',
								triggerAction:'all',
								displayField:"name",
								valueField:'depid',
								store:new Ext.data.JsonStore({
									url:contextPath+'/rsgl/bmglAction.do?method=getAllBmForList',
									fields:["depid","name"],
									autoLoad:true
								})
							},{
								hiddenName:'xb',
								emptyText:'必填',
								allowBlank:false,
								fieldLabel:'性别',
								xtype:'combo',
								mode:'local',
								readOnly:true,
								triggerAction:'all',
								displayField:"sname",
								valueField:'sid',
								store:new Ext.data.JsonStore({
									data:[{sid:"1",sname:'男'},{sid:"0",sname:'女'}],
									fields:["sid","sname"]
								})
							},{
								name:'csrq',
								fieldLabel:'出生日期',
								xtype:'datefield',
								format : 'Y-m-d'
							},{
								name:'gjdm',
								fieldLabel:'国籍',
								xtype:'combo',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=GJDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'籍贯',
								name:'jg',
								xtype:'combo',
								readOnly:true,
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=JGDM',
									fields:["id","dmmc"]
								})
							},{
								name:'csd',
								fieldLabel:'出生地',
								xtype:'combo',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=JGDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'政治面貌',
								name:'zzmm',
								readOnly:true,
								xtype:'combo',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=ZZMMDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'入党(团)时间',
								name:'ryhrdsj',
								xtype:'datefield',
								format:'Y-m-d'
							},{
								name:'mzdm',
								fieldLabel:'民族',
								xtype:'combo',
								readOnly:true,
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=MZDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'参加工作时间',
								name:'cjgzsj',
								xtype:'datefield',
								format:'Y-m-d'
							},{
								fieldLabel:'职务等级',
								hiddenName:'zwdjdm',
								xtype:'combo',
								readOnly:true,
								mode:'local',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'lxdm',
								store:zwdjStore
							},{
								fieldLabel:'职务等级批准时间',
								name:'zwdjpzsj',
								xtype:'datefield',
								format:'Y-m-d'
							},{
								fieldLabel:'专业技术资格等级',
								hiddenName:'zyjszgdjdm',
								readOnly:true,
								xtype:'combo',
								mode:'local',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'lxdm',
								store:zyjszgStore
							},{
								fieldLabel:'资格等级获得时间',
								name:'zgdjhdsj',
								xtype:'datefield',
								format:'Y-m-d'
							},{
								fieldLabel:'工勤人员技术等级',
								hiddenName:'gqryjsdjdm',
								xtype:'combo',
								readOnly:true,
								mode:'local',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'lxdm',
								store:gqryjsdjStore
							},{
								fieldLabel:'技术等级获得时间',
								name:'jsdjhdsj',
								xtype:'datefield',
								format:'Y-m-d'
							}
						]
					},{
						items:[
							{
								fieldLabel:'岗位',
								name:'gw'
							},{
								fieldLabel:'身份证号',
								name:'sfzh'
							},{
								fieldLabel:'办公室电话',
								name:'bgsdh'
							},{
								fieldLabel:'手机号码',
								name:'sjhm'
							},{
								fieldLabel:'入职时间',
								name:'rzsj',
								allowBlank:false,
								emptyText:'必填',
								xtype:'datefield',
								format : 'Y-m-d'
							},{
								fieldLabel:'转职时间',
								name:'zzsj',
								xtype:'datefield',
								format : 'Y-m-d'
							},{
								fieldLabel:'离职时间',
								name:'lzsj',
								xtype:'datefield',
								format : 'Y-m-d'
							},{
								fieldLabel:'在职状态',
								hiddenName:'zt',
								xtype:'combo',
								allowBlank:false,
								emptyText:'必填',
								mode:'local',
								readOnly:true,
								triggerAction:'all',
								displayField:"sname",
								valueField:'sid',
								store:new Ext.data.JsonStore({
									data:[{sid:"1",sname:'在职'},{sid:"0",sname:'离职'}],
									fields:["sid","sname"]
								})
							},{
								fieldLabel:'人员类别',
								hiddenName:'rylbdm',
								xtype:'combo',
								mode:'local',
								readOnly:true,
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'lxdm',
								store:rydjStore
							},{
								fieldLabel:'健康状况',
								name:'jkqkdm',
								readOnly:true,
								xtype:'combo',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=JKZKDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'婚姻状况',
								name:'hyqk',
								xtype:'combo',
								triggerAction:'all',
								readOnly:true,
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=HYQKDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'血型',
								name:'xx',
								xtype:'combo',
								readOnly:true,
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=XXDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'文化程度',
								hiddenName:'whcddm',
								xtype:'combo',
								readOnly:true,
								mode:'local',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'lxdm',
								store:whcdStore
							},{
								fieldLabel:'家庭出生',
								name:'jtcsdm',
								readOnly:true,
								xtype:'combo',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=JTCSDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'毕业院校',
								name:'byyx'
							},{
								fieldLabel:'专业与学制',
								name:'zyyxz'
							},{
								fieldLabel:'学位',
								name:'xwdm',
								xtype:'combo',
								readOnly:true,
								mode:'local',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'lxdm',
								store:xwStore
							}
						]
					},{
						columnWidth:1,
						layout:'form',
						labelWidth:110,
						defaults:{
							anchor:'97.5%'	
						},
						items:[
							{
								fieldLabel:'备注',
								name:'bz',
								xtype:'textarea',
								height:80
							},{
								xtype:'hidden',
								name:'id'
							},
							{
								xtype:'panel',
								layout:'column',
								border:false,
								items:[
								{
									columnWidth:0.14,
									xtype:'label',
									text:'所使用系统账户信息:',
									style:'text-valign:middle;'
								},{
										columnWidth:0.1,
										xtype:'checkbox',
										handler:function(chk,checked){
											if(checked)
											{
												Ext.getCmp('labUsername').show();
												Ext.getCmp('labPassword').show();
												Ext.getCmp('txtUsername').show();
												Ext.getCmp('txtPassword').show();
											}else
											{
												Ext.getCmp('txtUsername').setValue('');
												Ext.getCmp('txtPassword').setValue('');
												Ext.getCmp('txtUsername').hide();
												Ext.getCmp('txtPassword').hide();
												Ext.getCmp('labUsername').hide();
												Ext.getCmp('labPassword').hide();
											}
										}
									},
									{
										xtype:'label',
										columnWidth:0.1,
										text:'登录帐号:',
										id:'labUsername',
										hidden:true
									}
									,{
										columnWidth:0.2,
										id:'txtUsername',
										name:'member.loginid',
										xtype:'textfield',
										hidden:true,
										readOnly:true
									},
									{
										xtype:'label',
										columnWidth:0.1,
										text:'登录密码:',
										id:'labPassword',
										hidden:true
									},{
										columnWidth:0.2,
										id:'txtPassword',
										inputType:'password',
										name:'member.password',
										xtype:'textfield',
										hidden:true,
										readOnly:true
									}
								]
							},{
								xtype:'panel',
								layout:'column',
								border:false,
								items:[
									{
										xtype:'label',
										columnWidth:.14,
										text:'是否为老师:'
									},
									{
										name:'isTeacher',
										columnWidth:0.1,
										xtype:'checkbox',
										handler: function(chk, checked){
											if (checked) {
												Ext.getCmp('hidLs').setValue(Ext.getCmp('txtRyxm').getValue());
											}
										}
									},
									{
										xtype:'hidden',
										name:'ls.ls',
										id:'hidLs'
									}
								]
							},
							{
								xtype:'panel',
								id:'panBzr',
								layout:'column',
								border:false,
								listeners:{
									'afterrender':function(p)
									{
										Ext.getCmp('selectTreebjName').hide();
										Ext.getCmp('labBjxx').hide();
									}
								},
								items:[
									{
										xtype:'label',
										columnWidth:.14,
										text:'是否为班主任:'
									},
									{
										name:'isBzr',
										columnWidth:0.1,
										xtype:'checkbox',
										handler: function(chk, checked){
											if (checked) {
												Ext.getCmp('hidBzr').setValue(Ext.getCmp('txtRyxm').getValue());
												Ext.getCmp('labBjxx').show();
												Ext.getCmp('selectTreebjName').show();
											}
											else
											{
												Ext.getCmp('hidBzr').setValue('');
												Ext.getCmp('labBjxx').hide();
												Ext.getCmp('selectTreebjName').hide();
											}
										}
									},
									{
										xtype:'hidden',
										name:'bzr.bzrxm',
										id:'hidBzr'
									},{
										xtype:'hidden',
										id:'hidBjid',
										name:'bzr.bzrbj.bjdm.id'
									},{
										xtype:'label',
										text:'选择班主任带班班级:',
										id:'labBjxx'
									}
								]
							}
						]
					}
				]
			});
			
			
			Ext.getCmp('editPanel').add(editForm);
			var dataStore = new Ext.data.JsonStore({
				url:contextPath+'/rsgl/ryglAction.do?method=getRyxxByIds&ids=${ids}',
				root:'ryList',
				fields:["id","member.role.depart.depid","rybh","ryxm","csrq","gjdm","csd","mzdm","sfzh","zp","xx","jkqkdm","hyqk",
				"jtcsdm","rzsj","zzsj","lzsj","bz","zt","bgsdh","sjhm","xb","zwdjdm","rylbdm","zwdjpzsj",
				,"zyjszgdjdm","zgdjhdsj","gqryjsdjdm","jsdjhdsj","zzmm","gw","ryhrdsj","byyx","zyyxz",
				,"jg","whcddm","xwdm","cjgzsj",]
			});
			
			bachLoad([bmStore,zwdjStore,zyjszgStore,gqryjsdjStore,rydjStore,whcdStore,xwStore,dataStore],function(success){
				if(success){
					initTbar(editForm,dataStore);
				}else{
					Ext.Msg.alert('提示','数据加载失败，请联系系统管理员！');
				}
			});
			
			
			Ext.getCmp('saveBtn').on('click',function(){
				if(editForm.form.isValid()){
					var jsonArray = [];
					//设置当前数据
					setThisData(editForm.form.getValues());
					
					for(var i=0; i<dataArray.length;i++){
						jsonArray.push(dataArray[i].data);
					}
					if(jsonArray.length == 0){
						return;
					}
					var waitMsg = Ext.MessageBox.wait('数据更新中,请稍后....','提示');
					var jsonData = encodeURIComponent(Ext.encode(jsonArray));
					Ext.Ajax.request({
						method: 'POST',
						url: contextPath+'/rsgl/ryglAction.do?method=updRyxx',
						success: function(response){
							var rs = Ext.decode(response.responseText);
							if (rs.success) {
								Ext.Msg.confirm("提示","保存成功，是否继续修改？单击[yes]留在当前页面，单击[no]返回上一页面。",function(btn){
									if(btn == "no"){
										history.back();
									}
								});
							}else{
								Ext.Msg.alert("提示", rs.message);
							}
						},
						failure: function(){
							Ext.Msg.alert("提示", "更新失败,服务器无法连接!");
						},
						params:{data:jsonData }
					});
				}
			});
		});
	</script>
  </head>
  <body>
  <input type="hidden" id='qx' value='{qxlx}'>
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
