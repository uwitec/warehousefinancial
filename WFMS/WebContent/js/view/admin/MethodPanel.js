Ext.define('wfms.admin.MethodPanel',{
	extend : 'Ext.grid.Panel',
	alias : 'widget.methodPanel',	    
    columnLines: true,
    loadMask : true,    
    columns: [
    	{xtype: 'rownumberer',width: 50, sortable: false,align:'center'},
    	{header: '名称',  dataIndex: 'zhName',width:150},
        {header: '别名',  dataIndex: 'enName',width:120},
        {header: '排序',  dataIndex: 'sortId',width:45},
        {header: '备注',  dataIndex: 'remark',width:180}       
    ],
	initComponent : function(){
		this.viewConfig = {
	    	stripeRows: true,
	        trackOver: true,
	        emptyText : '<div style="color:#999;margin:5px;">当前没有记录显示<div>',
	        plugins: {
	             ptype: 'gridviewdragdrop',
	             dragGroup: 'modelGridDDGroup',
	             dropGroup: 'modelGridDDGroup'
	         },
	         listeners: {
	             drop: function(node, data, dropRec, dropPosition) {
	             	var n = this.store.getCount();
	             	if(n>0){
	             		for(var i=0;i<n;i++){
	             			this.store.getAt(i).set('sortId',(i+1));
	             		}
	             		this.store.sort('sortId','ASC');
	             	}
	             	this.down('#sortBtn').setDisabled(false);
	             },scope:this
	         }
	    };
		this.store = Ext.create('Ext.data.Store', {
	        fields: ['id', 'zhName','enName','remark','flag','sortId'],        
	        proxy: {
	            type: 'ajax',
	            actionMethods : 'post',           
	            url : wfms.defaultUrl(), 
	            timeout : 1000*60*3,           
	            extraParams : {clsName:'RgMethodAction',methodName:'getMethodList'},                        
		        reader: {
		            type: 'json',
		            root: 'results'
		        }
	        },
	        autoLoad: false,
	        listeners : {
	        	'load':function(){
	        		this.down('#refreshBtn').setDisabled(false);
	        	},
	        	'beforeload':function(){
	        		this.down('#refreshBtn').setDisabled(true);
	        	},scope:this
	        }
	    });
		this.selModel = Ext.create('Ext.selection.CheckboxModel', {
	        listeners: {
	            selectionchange: function(sm, selections) {
	                var n = selections.length||0;
					this.down('#delBtn').setDisabled(n==0);					
					this.down('#editBtn').setDisabled(n!=1);
	            },scope:this
	        }
	    });
	    this.tbar = [
	    	{text:'新增',iconCls:'add',scope:this,handler:this.showAddWin},
	    	{text:'修改',iconCls:'edit',scope:this,handler:this.showEditWin,disabled:true,itemId:'editBtn'},
	    	{text:'删除',iconCls:'delete',scope:this,handler:this.deleteMethod,disabled:true,itemId:'delBtn'},	    	
	    	{text:'保存排序',handler:this.saveSort,scope:this,iconCls:'sort',itemId:'sortBtn',disabled:true},
	    	{text:'刷新',iconCls:'refresh',scope:this,handler:function(){this.store.load();},itemId:'refreshBtn'}
	    ],
		this.callParent();
		this.on('afterrender',function(gp){
			this.store.load();
			gp.view.on('itemdblclick',function(){
    			var m = gp.getSelectionModel().getSelection();
    			if(m.length==1){
    				this.showEditWin();
   				}
    		},this);
		},this);
	},
	showAddWin :function(){
		if(!this.addWin){
			this.addWin = Ext.create('wfms.admin.MethodWin',{title:'新增',fcmp :this});
		}
		this.addWin.show();
	},
	showEditWin :function(){
		if(!this.editWin){
			this.editWin = Ext.create('wfms.admin.MethodWin',{title:'修改',fcmp :this,_winType:'edit'});
		}
		this.editWin.show();
	},
	deleteMethod : function(){
		Ext.Msg.show({
			icon : Ext.Msg.QUESTION,
			buttons : Ext.Msg.YESNO,
			width : 120,
			scope : this,
			msg : '是否删除选择的方法?',
			fn : function(btn){
				if(btn=='yes'){
					var m = this.getSelectionModel().getSelection();
					var ids = [];
					for(var i=0;i<m.length;i++){
						ids.push(m[i].get('id'));
					}
					wfms.openLink({
						params : {id:ids.join(','),clsName:'RgMethodAction',methodName:'deleteMethod'},
						scope : this,
						masker : this,
						onSuccess : function(rs){
							this.store.load();
						}
					});
				}
			}
		});		
	},
	saveSort : function(){
		var n = this.store.getCount();
		if(n>0){
			var idArr = [],sort=[];
			for(var i=0;i<n;i++){
				idArr.push(this.store.getAt(i).get('id'));
				sort.push(this.store.getAt(i).get('sortId'));
			}
		}
		wfms.openLink({
			params : {ids :idArr.join(','),sort: sort.join(','),clsName:'RgMethodAction',methodName:'sort'},
			scope : this,
			masker : this,
			onSuccess : function(rs){
				this.down('#sortBtn').setDisabled(true);
				for(var i=0;i<n;i++){
					this.store.getAt(i).commit();
				}
				Ext.Msg.alert('提示','保存成功!');
			}
		});
	}
});