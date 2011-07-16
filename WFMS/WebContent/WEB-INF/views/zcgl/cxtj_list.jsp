<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>招聘信息</title>
	<!-- Extjs导入 -->
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css"></link>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/yhTree.js"></script>
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
	//资产类型store
	var zclxStore = new Ext.data.JsonStore({
      	url:contextPath+'/zcgl/zclxAction.do?method=getZclxList',
		fields:['id',"lxdm","lxmc","bz"]
	});
	//仓库信息store
	var ckxxStore = new Ext.data.JsonStore({
      	url:contextPath+'/zcgl/ckxxAction.do?method=getCkxxList',
		fields:["id","ckdm","ckmc","glyid"]
	});
	ckxxStore.load();
	
	
	
	//列表数据对象
	var dataStore = new Ext.data.JsonStore({
		autoDestroy: true,
		pruneModifiedRecords:true,
		root:'dataList',
		totalProperty:'totalCount',
      	url:contextPath+'/zcgl/zcrkAction.do?method=getCkzcByPage',
		fields:["id","ckxx.id","ckxx.ckmc","ckxx.glyxm","zclx.id","zclx.lxmc","zcxx.id","zcxx.zcmc",
			"zcxx.zcdm","zcxx.zcgg","zcxx.zcdw","zcxx.sccj","zccssl","bz"]
	});
	
	zclxStore.load();
	
	
	infoView.add(new Ext.form.FieldSet({
        title: '资产信息',
        collapsible: true,
        autoScroll:true,
        labelWidth:68,
        autoHeight:true,
        defaults: {
        	readOnly:true,
        	style: {
	            border:'none',
	            background:'none'
			},
        	anchor:'90%'
	    },
        defaultType: 'textfield',
        items :[
        	{
				fieldLabel:'所属仓库',
				xtype:'combo',
				hiddenName:'ckxx.id',
				mode:'local',
				readOnly:true,
				hideTrigger:true,
				triggerAction:'all',
				displayField:"ckmc",
				valueField:'id',
				store:ckxxStore
		   },{
				fieldLabel:'资产类别',
				xtype:'combo',
				hiddenName:'zclx.id',
				mode:'local',
				readOnly:true,
				hideTrigger:true,
				triggerAction:'all',
				displayField:"lxmc",
				valueField:'id',
				store:zclxStore
		   },{
		   		fieldLabel:'资产名称',
		   		name:'zcxx.zcmc'
		   },{
				fieldLabel:'资产代码',
				name:'zcxx.zcdm'
		   },{
				fieldLabel:'库存数量',
				name:'zccssl'
		   }
		   
        ]
	}));
	
	//列表
	var dataGrid = new Ext.grid.EditorGridPanel({
		stripeRows: true,
		autoScroll:true,
		trackMouseOver:true,
		border:false,
        loadMask: true,
        height :500,
        sm:checkboxModel,
       	cm:new Ext.grid.ColumnModel([
		      		{
						header:'物资名称',
						dataIndex:'zcxx.zcmc'			
					},{
						header:'物资代码',
						dataIndex:'zcxx.zcdm'		
					},{
						header:'所属类别',
						dataIndex:'zclx.lxmc'
					},{
						header:'库存数量',
						dataIndex:'zccssl'
					}
					
					
				]),
		store:dataStore,
		bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: dataStore,
            displayInfo: true,
            beforePageText:'第',
            afterPageText:'页，共 {0} 页',
            displayMsg: '当前为第<font style="color:red">{0} - {1}</font> 条记录，共<font style="color:red">{2}</font> 条记录',
            emptyMsg: "没有找到相关信息"
        })
	});
	
	
			
			//添加列表
			dataPanel.add(dataGrid);
			initDataTbar([_refresh,_condition]);
			
			//查询条件
			RelativeActionView.add(new Ext.form.FieldSet({
	            title: '资产信息',
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
						fieldLabel:'所属仓库',
						xtype:'combo',
						hiddenName:'ckxx.id',
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"ckmc",
						valueField:'id',
						store:ckxxStore
				   },{
						fieldLabel:'资产类别',
						xtype:'combo',
						hiddenName:'zclx.id',
						mode:'local',
						readOnly:true,
						triggerAction:'all',
						displayField:"lxmc",
						valueField:'id',
						store:zclxStore
				   },{
				   		fieldLabel:'资产名称',
				   		name:'zcxx.zcmc'
				   },{
						fieldLabel:'资产代码',
						name:'zcxx.zcdm'
				   }
				],
				buttons:[
					{
						text:'查 询',
						handler:function(){
							Ext.apply(dataStore.baseParams,{
								conditions:encodeURIComponent(Ext.encode(RelativeActionView.form.getValues()))
							});   
							dataStore.load({ params: { start: 0, limit: 15 } });
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
				dataStore.reload();
			})
			

			//实例化主界面(添加所有控件后)
			var mianFrame = new MianFrame();
			setNavigate("资产管理 >> 查询统计 ","本模块提供对资产进行查询、统计的相关信息");
			dataStore.load({params:{start:0,limit:15}});
			
			
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
