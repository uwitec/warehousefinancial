<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title></title>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/sq_viewport.js"></script>
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
			setNavigate("办公自动化 >> 个人信息管理","本页面提供员工个人信息维护功能");
			var editForm = new Ext.FormPanel({
				layout:'column',
				width:600,
				bodyStyle:'padding:10px',
				border:false,
				defaults:{border:false,labelAlign:'right'},
				items:[
					{
						width:280,
						defaultType:'textfield',
						layout:'form',
						labelWidth:70,
						defaults:{
							width:200	
						},
						items:[
							{
								name:'rybh',
								emptyText:'必填',
								allowBlank:false,
								fieldLabel:'人员编号',
								value:'${ry.rybh}',
								readOnly:true
							},{
								name:'ryxm',
								emptyText:'必填',
								allowBlank:false,
								value:'${ry.ryxm}',
								fieldLabel:'人员姓名'
							},{
								name:'ryxm',
								emptyText:'必填',
								allowBlank:false,
								value:'${ry.member.role.depart.name}',
								fieldLabel:'部门'
							},{
								hiddenName:'xb',
								emptyText:'必填',
								allowBlank:false,
								fieldLabel:'性别',
								value:'${ry.xb}',
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
								value:'${ry.csrq}',
								xtype:'datefield',
								format : 'Y-m-d',
								renderer:Ext.util.Format.dateRenderer('Y-m-d')
							},{
								name:'gjdm',
								fieldLabel:'国籍',
								xtype:'combo',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								value:'${ry.gjdm}',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=GJDM',
									fields:["id","dmmc"]
								})
							},{
								name:'csd',
								fieldLabel:'出生地',
								xtype:'combo',
								value:'${ry.csd}',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=JGDM',
									fields:["id","dmmc"]
								})
							},{
								name:'mzdm',
								fieldLabel:'民族',
								value:'${ry.mzdm}',
								xtype:'combo',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=MZDM',
									fields:["id","dmmc"]
								})
							}
						]
					},{
						width:280,
						layout:'form',
						style:'padding-left:70px',
						buttonAlign:'center',
						items:[
							{
								xtype:'box',
								fieldLabel:'',
								id:'pic',
								width:150,
								height:170,
								autoEl: {   
							        tag: 'img',    //指定为img标签   
							        src: contextPath+'/imgs/default-zp.jpg'    //指定url路径   
							    }
							}
						],
						buttons:[
							{
								icon:contextPath+'/imgs/add.png',
								text:'上传照片 . . .'
							}
						]
					},{
						width:280,
						defaultType:'textfield',
						layout:'form',
						labelWidth:70,
						defaults:{
							width:200	
						},
						items:[
							{
								fieldLabel:'办公室电话',
								value:'${ry.bgsdh}',
								name:'bgsdh'
							},{
								name:'sfzh',
								value:'${ry.sfzh}',
								fieldLabel:'身份证号'
							},{
								fieldLabel:'入职时间',
								name:'rzsj',
								value:'${ry.rzsj}',
								allowBlank:false,
								emptyText:'必填',
								xtype:'datefield',
								format : 'Y-m-d',
								renderer:Ext.util.Format.dateRenderer('Y-m-d')
							},{
								fieldLabel:'转职时间',
								name:'zzsj',
								value:'${ry.zzsj}',
								xtype:'datefield',
								format : 'Y-m-d',
								renderer:Ext.util.Format.dateRenderer('Y-m-d')
							},{
								fieldLabel:'离职时间',
								name:'lzsj',
								xtype:'datefield',
								format : 'Y-m-d',
								value:'${ry.lzsj}',
								renderer:Ext.util.Format.dateRenderer('Y-m-d')
							},{
								fieldLabel:'在职状态',
								hiddenName:'zt',
								xtype:'combo',
								allowBlank:false,
								emptyText:'必填',
								mode:'local',
								value:'${ry.zt}',
								readOnly:true,
								displayField:"sname",
								valueField:'sid',
								store:new Ext.data.JsonStore({
									data:[{sid:"1",sname:'在职'},{sid:"0",sname:'离职'}],
									fields:["sid","sname"]
								})
							}
						]
					},{
						width:280,
						defaultType:'textfield',
						layout:'form',
						labelWidth:70,
						defaults:{
							width:200	
						},
						items:[
							{
								fieldLabel:'手机号码',
								value:'${ry.sjhm}',
								name:'sjhm'
							},{
								fieldLabel:'健康状况',
								name:'jkqkdm',
								value:'${ry.jkqkdm}',
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
								value:'${ry.hyqk}',
								xtype:'combo',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=HYQKDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'家庭出生',
								name:'jtcsdm',
								xtype:'combo',
								value:'${ry.jtcsdm}',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=JTCSDM',
									fields:["id","dmmc"]
								})
							},{
								fieldLabel:'血型',
								name:'xx',
								xtype:'combo',
								value:'${ry.xx}',
								triggerAction:'all',
								displayField:"dmmc",
								valueField:'dmmc',
								store:new Ext.data.JsonStore({
									url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=XXDM',
									fields:["id","dmmc"]
								})
							}
						]
					},{
						width:600,
						defaultType:'textfield',
						layout:'form',
						labelWidth:70,
						defaults:{
							width:480	
						},
						items:[
							{
								fieldLabel:'备注',
								name:'bz',
								value:'${ry.bz}',
								xtype:'textarea',
								height:80
							}
						]
					}
				]
			});
			if('${ry}' == ""){
				Ext.Msg.alert("提示","您没有没有相应的人员信息，请联系系统管理员添加人员信息！");
			}else{
				Ext.getCmp('editPanel').add(editForm);
			}
			
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
