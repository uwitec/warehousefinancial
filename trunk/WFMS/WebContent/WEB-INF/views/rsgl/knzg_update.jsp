<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>修改困难职工信息</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/edit_viewport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bachStoreLoad.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectRyxxWindow.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/add_viewport.js"></script>
	<script type="text/javascript">
			Ext.onReady(function(){
			//困难类别
			var knlbdmStore = new Ext.data.JsonStore({  
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=KNZGKNLB',
				fields:["lxdm","dmmc"]
			});
			//残疾类别
			var cjlbdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=CJLBDM',
				fields:["lxdm","dmmc"]
			});
			//劳模类型
			var lmlxdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=LMLXDM',
				fields:["lxdm","dmmc"]
			});
			//住房类型
			var zflxdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=FWCQDM',
				fields:["lxdm","dmmc"]
			});
			//单位性质
			var dwxzdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=DWXZDM',
				fields:["lxdm","dmmc"]
			});
			//户口性质  
			var hkxzdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=HKXZDM',
				fields:["lxdm","dmmc"]
			});
			//查询所有人员信息
			var ryStore = new Ext.data.JsonStore({   
				url:contextPath+'/rsgl/ryglAction.do?method=getAllRy',
				fields:["id","ryxm"]
			});
			//国籍
			var gjdmStore = new Ext.data.JsonStore({   
				url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=GJDM',
				fields:["lxdm","dmmc"]
			});
			new Ext.QuickTips.init();
			var mainFrame = new MianFrame();
			setNavigate("人事管理 >> 困难职工 >> 修改困难职工信息","本模块提供困难职工的基本信息");
			var form = new Ext.form.FormPanel({
				layout:'fit',
				titile:'form',
				border:false,
				bodyStyle:'padding:10px',
				defaults:{
					xtype:'fieldset',
					margins:'4 4 4 4',
					collapsible:true
				},
				items:[
					{
						title:'人员信息',
						layout:'column',
						height:62,
						defaults:{
							border:false,
							columnWidth:.5,
							layout:'form',
							labelAlign:'right',
							defaults:{
								xtype:'textfield',
								readOnly:true,
								anchor:'95%'
							}
						},
						items:[
							{
								items:[
									{
										xtype:'hidden',
										name:'id'
									},{
										fieldLabel:'人员编号',
										readOnly:true,
										name:'ry.id'
								 	},{
										fieldLabel:'人员姓名',
										readOnly:true,
										name:'ry.ryxm'
								 	},{
										fieldLabel:'出生地',
										name:'ry.csd'
								 	}
								]
							},{	
								items:[
									{
										fieldLabel:'入职时间',
										name:'ry.rzsj'										
									},{
										fieldLabel:'国籍',
										name:'ry.gjdm',
										mode:'local',
										xtype:'combo',
										readOnly:true,
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:gjdmStore		
									},{
										fieldLabel:'手机号',
										name:'ry.sjhm'										
									}
								]							
							}
						]
					},{
						title:'修改困难职工的信息',
						layout:'column',
						defaults:{
							border:false,
							columnWidth:.5,
							layout:'form',
							labelAlign:'right',
							defaults:{
								xtype:'textfield',
								anchor:'95%'
							}
						},
						items:[
							{
								items:[
									 {
										fieldLabel:'困难类别',
										xtype:'combo',
										hiddenName:'knlbdm',
										mode:'local',
										readOnly:true,
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:knlbdmStore 
									},{
										fieldLabel:'残疾类别',
										xtype:'combo',
										hiddenName:'cjlbdm',
										mode:'local',
										readOnly:true,
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:cjlbdmStore 
									},{
										fieldLabel:'身份',
										name:'sf'
									}, {
										fieldLabel:'劳模类型',
										xtype:'combo',
										hiddenName:'lmlxdm',
										mode:'local',
										readOnly:true,
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:lmlxdmStore  
									}, {
										fieldLabel:'住房类型',
										xtype:'combo',
										hiddenName:'zflxdm',
										mode:'local',
										readOnly:true,
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:zflxdmStore  
									},{
										fieldLabel:'建筑面积',
										xtype:'numberfield',
										maxValue:99999,
										name:'jzmj'
									},{
										fieldLabel:'联系电话',
										name:'lxdh'
									},{
										fieldLabel:'工作日期',
										name:'gzrq'
									},{
										fieldLabel:'家庭住址',
										name:'jtzz'
									},{
										fieldLabel:'所属行业',
										name:'sshy'
									},{
										fieldLabel: '工作单位',
										name: 'gzdw'
									},{
										fieldLabel:'企业状况',
										name:'qyzk'
									}
								]	
							},{
								items:[
									{
										fieldLabel:'单位性质',
										hiddenName:'dwxzdm',
										xtype:'combo',
										mode:'local',
										readOnly:true,
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:dwxzdmStore   
									},{
										fieldLabel:'是否单亲',
										hiddenName:'sfdq',
										mode:'local',
										xtype:'combo',
										readOnly:true,
										triggerAction:'all',
										displayField:"sname",
										valueField:'sid',
										store:new Ext.data.JsonStore({
											data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
											fields:['sid','sname']
										})
									},{
										fieldLabel:'本人收入/月',
										xtype:'numberfield',
										maxValue:999999,
										allowBlank:false,
										name:'brypjsr'
									},{
										fieldLabel:'家庭收入/年',
										xtype:'numberfield',
										maxValue:999999999,
										allowBlank:false,
										name:'jtndzsr'
									},{
										fieldLabel:'家庭人口',
										xtype:'numberfield',
										maxValue:99,
										allowBlank:false,
										name:'jtrk'
									},{
										fieldLabel:'人均收入/年',
										xtype:'numberfield',
										maxValue:99999999,
										allowBlank:false,
										name:'jtnrjsr'
									},{
										fieldLabel:'户口所在地',
										name:'hkszdxzqh'
									},{
										fieldLabel:'户口性质',
										hiddenName:'hkxzdm',
										xtype:'combo',
										mode:'local',
										readOnly:true,
										triggerAction:'all',
										displayField:"dmmc",
										valueField:'lxdm',
										store:hkxzdmStore    
									},{
										fieldLabel:'是否入医保',
										hiddenName:'sfjryb',
										mode:'local',
										xtype:'combo',
										readOnly:true,
										triggerAction:'all',
										displayField:"sname",
										valueField:'sid',
										store:new Ext.data.JsonStore({
											data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
											fields:['sid','sname']
										})
									},{
										fieldLabel:'能否自救',
										hiddenName:'sfyydzjnl',
										mode:'local',
										xtype:'combo',
										readOnly:true,
										triggerAction:'all',
										displayField:"sname",
										valueField:'sid',
										store:new Ext.data.JsonStore({
											data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
											fields:['sid','sname']
										})
									},{
										fieldLabel:'是否无人就业',
										hiddenName:'sfwljyjt',
										mode:'local',
										xtype:'combo',
										readOnly:true,
										triggerAction:'all',
										displayField:"sname",
										valueField:'sid',
										store:new Ext.data.JsonStore({
											data:[{sid:'0',sname:'否'},{sid:'1',sname:'是'}],
											fields:['sid','sname']
										})
									}
								]
							}
						]					
					}
				]
			});
		
			Ext.getCmp('editPanel').add(form);
			form.on('beforerender',function(){
				var dataStore = new Ext.data.JsonStore({
					pruneModifiedRecords:true,
					url:contextPath+'/rsgl/knzgAction.do?method=getKnzgById&id=${id}',
					fields:["id","ry.id","ry.ryxm","ry.csd","ry.gjdm","ry.rzsj","ry.sjhm","knlbdm","cjlbdm","sf","lmlxdm","zflxdm","jzmj","lxdh","gzrq","sshy",
						"jtzz","gzdw","dwxzdm","qyzk","sfdq","brypjsr","jtndzsr","jtrk","jtnrjsr",
						"hkszdxzqh","sfjryb","sfyydzjnl","sfwljyjt","bz","hkxzdm"]
				});
				
				 bachLoad([knlbdmStore ,cjlbdmStore ,lmlxdmStore ,zflxdmStore ,dwxzdmStore ,hkxzdmStore,gjdmStore ],function(s){
					if(s){
						
						dataStore.load({
							callback : function(r,options, success) {  
						        if (success) {  
									//waitMsg.hide();
									dataStore.each(function(re){
										form.form.loadRecord(re);
									});
									initTbar(form,dataStore);
						        } 
						        else{
						        	Ext.Msg.alert("错误提示","数据加载失败！"); 
						        }
							}  
						});
					}else{
						Ext.Msg.alert('提示','数据加载失败，请联系系统管理员！');
					}
			});
		  });
		  
		  	//修改事件
			Ext.getCmp('saveBtn').on('click',function(){
				if(form.form.isValid()){
					var jsonArray = [];
					//设置当前数据
					setThisData(form.form.getValues());
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
						url: contextPath+'/rsgl/knzgAction.do?method=updateKnzg',
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
						params:{data:jsonData}
					});
				}
			});
		
			
		});
	</script>	
  </head>
  <body>
  	
  </body>
</html>
