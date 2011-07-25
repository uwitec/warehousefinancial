Ext.define('wfms.admin.ModelPanel',{
	extend : 'Ext.panel.Panel',
	alias: 'widget.modelpanel',
	layout : 'border',
	initComponent : function(){
		this.westStore = Ext.create('Ext.data.TreeStore', {
			autoLoad:true,
	        proxy: {
	            type: 'ajax',
	            url : 'system/module_manage/userModuleTree.do',
	            actionMethods : 'post',
	            extraParams : {clsName:'RgModelAction',methodName:'getModelTree'}
	        },
	        root: {
	        	id : '0',
	            text: '模块',
	            expanded: true
	        }
	    });
	    
		this.west = Ext.create('Ext.tree.Panel',{
			margins : '2 2 2 2',
			region : 'west',
			title: '菜单',
		    width: 200,
		    tools : [
		    	{name:'refresh',type:'refresh',qtip:'刷新模块树',disabled :true,handler:function(){
		    		this.west.store.load();
		    		this.center.store.removeAll();
		    	},scope:this}
		    ],
		    store: this.westStore,
		    listeners : {
		    	'afterrender':function(o){
		    		o.store.on('beforeload',function(){this.west.down('[name=refresh]').disable();},this);	
		    		o.store.on('load',function(ds,records,successful){
		    			this.west.down('[name=refresh]').enable();
		    		},this);	
		    	},
		    	'itemclick':function(view, record, htme, index, e){
		    		this.center.store.load({id:record.get('id')});
		    	},scope:this
		    },
		    viewConfig: {
		        plugins: {
	                ptype: 'treeviewdragdrop',
                	appendOnly: true
	            },
	            listeners: {
	                drop: function(node, data, dropRec, dropPosition) {
	                	var targetId = dropRec.get('id');
	                	var dropId = data.records[0].get('id');
	                	this.dropNode(targetId,dropId);
	                },scope:this
	            }
		    }
		});
		
		this.centerStore = Ext.create('Ext.data.Store', {
	        fields: ['id', {name:'name',mapping:'text'},'url','path','p_id','flag','sortId'],        
	        proxy: {
	            type: 'ajax',
	            actionMethods : 'post',           
	            url : wfms.defaultUrl(), 
	            timeout : 1000*60*3,           
	            extraParams : {clsName:'RgModelAction',methodName:'getModelList'},                        
		        reader: {
		            type: 'json',
		            root: 'results'
		        }
	        },
	        autoLoad: false
	    });
	    
		this.center = Ext.create('Ext.grid.Panel',{
			margins : '2 2 2 0',
			region : 'center',
			loadMask: true,
			selModel : Ext.create('Ext.selection.CheckboxModel', {
		        listeners: {
		            selectionchange: function(sm, selections) {
		                var n = selections.length||0;
						this.center.down('#delBtn').setDisabled(n==0);					
						this.center.down('#editBtn').setDisabled(n!=1);					
						var a = 0,s=0;
						for(var i=0;i<n;i++){
							if(selections[i].get('flag')==1){
								a += 1;
							}else{
								s += 1;
							}
						}					
						if(a&&!s){
							this.center.down('#stopBtn').setDisabled(n==0);
						}else if(s&&!a){
							this.center.down('#activeBtn').setDisabled(n==0);
						}else{
							this.center.down('#stopBtn').setDisabled(true);
							this.center.down('#activeBtn').setDisabled(true);
						}
		            },scope:this
		        }
		    }),
			columnLines: true,
		    store : this.centerStore,
		    viewConfig: {
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
	                	var n = this.centerStore.getCount();
	                	if(n>0){
	                		for(var i=0;i<n;i++){
	                			this.centerStore.getAt(i).set('sortId',(i+1));
	                		}
	                		this.centerStore.sort('sortId','ASC');
	                	}
	                	this.center.down('#sortBtn').setDisabled(false);
	                },scope:this
	            }
		    },    
		    columns: [
		    	{xtype: 'rownumberer',width: 50, sortable: false/*,header:'序号'*/,align:'center'},
		    	{header: '名称',  dataIndex: 'name',width:180},
		        {header: '状态',  dataIndex: 'flag',width:50,renderer:function(v){return v==1 ? '启用':'停用'}},
		        {header: '排序',  dataIndex: 'sortId',width:50},
		        {header: 'URL(模块地址)',  dataIndex: 'url',width:300},
		        {
		        	xtype: 'actioncolumn',
	                width: 50,
	                align : 'center',
	                items: [{
	                    getClass: function(v, meta, rec) {
	                        if (rec.get('flag') == 0) {
	                            this.items[0].tooltip = '启用';
	                            return 'stop-col';
	                        } else {
	                            this.items[0].tooltip = '停用';
	                            return 'active-col';
	                        }
	                    },
	                    handler: function(grid, rowIndex, colIndex) {
	                        var rec = grid.store.getAt(rowIndex);
	                        var flag = 0;
	                        if(rec.get('flag') == 0){
	                        	flag = 1;
	                        }
	                        grid.up('panel').up('panel').toggleModel(flag,rec.get('id'));
	                    }
	                },{
	                    icon   : wfms.getAppName()+'/images/delete.gif',
	                    tooltip: '删除',
	                    stopSelection : false,
	                    handler: function(grid, rowIndex, colIndex) {
	                        grid.up('panel').up('panel').deleteModel();
	                    }
	                }]
		        }	          
		    ],
		    tbar : [
		    	{text:'新增',iconCls:'add',scope:this,handler:this.showAddWin},'-',
		    	{text:'修改',iconCls:'edit',disabled:true,itemId:'editBtn',handler:this.showEditWin,scope:this},'-',
		    	{text:'删除',iconCls:'delete',disabled:true,itemId:'delBtn',handler:this.deleteModel,scope:this},'-',
		    	{text:'停用',iconCls:'stop',disabled:true,itemId:'stopBtn',handler:function(){
		    		var m = this.center.getSelectionModel().getSelection();
					var ids = [];
					for(var i=0;i<m.length;i++){
						ids.push(m[i].get('id'));
					}
		    		this.toggleModel(0,ids.join(','));
		    	},scope:this},'-',
		    	{text:'启用',iconCls:'active',disabled:true,itemId:'activeBtn',handler:function(){
		    		var m = this.center.getSelectionModel().getSelection();
					var ids = [];
					for(var i=0;i<m.length;i++){
						ids.push(m[i].get('id'));
					}
		    		this.toggleModel(1,ids.join(','));
		    	},scope:this},'-',
		    	{text:'保存排序',tooltip:'拖动记录进行排序',handler:this.saveSort,scope:this,iconCls:'sort',itemId:'sortBtn',disabled:true},'->',
		    	{
	                width: 250,
	                fieldLabel: '模块名称',
	                labelAlign : 'right',
	                labelWidth: 70,
	                xtype: 'searchfield',
	                paramName : 'name',
	                store : this.centerStore
	            }	    	
		    ],
		    listeners : {
		    	'afterrender' : function(gp){
		    		gp.view.on('itemdblclick',function(){
		    			var m = gp.getSelectionModel().getSelection();
		    			if(m.length==1){
		    				this.showEditWin();
	    				}
		    		},this);
		    	},scope:this
		    }
		});
		this.items = [this.center,this.west];
		this.callParent();
	},
	showAddWin :function(){
		if(!this.addWin){
			this.addWin = Ext.create('wfms.admin.ModelWin',{title:'新增',fcmp :this});
		}
		this.addWin.show();
	},
	showEditWin :function(){
		if(!this.editWin){
			this.editWin = Ext.create('wfms.admin.ModelWin',{title:'修改',fcmp :this,_winType:'edit'});
		}
		this.editWin.show();
	},
	deleteModel : function(){
		Ext.Msg.show({
			icon : Ext.Msg.QUESTION,
			buttons : Ext.Msg.YESNO,
			width : 120,
			scope : this,
			msg : '是否删除选择的模块?',
			fn : function(btn){
				if(btn=='yes'){
					var m = this.center.getSelectionModel().getSelection();
					var ids = [];
					for(var i=0;i<m.length;i++){
						ids.push(m[i].get('id'));
					}
					wfms.openLink({
						params : {id:ids.join(','),clsName:'RgModelAction',methodName:'deleteModel'},
						scope : this,
						masker : this,
						onSuccess : function(rs){
							this.west.store.load();
							this.center.store.removeAll();
						}
					});
				}
			}
		});		
	},
	toggleModel : function(flag,id){
		var msg = flag==1 ? '启用' : '停用';
		
		Ext.Msg.show({
			icon : Ext.Msg.QUESTION,
			buttons : Ext.Msg.YESNO,
			width : 120,
			scope : this,
			msg : '是否'+msg+'选择的模块?',
			fn : function(btn){
				if(btn=='yes'){
					wfms.openLink({
						params : {id:id,flag:flag,clsName:'RgModelAction',methodName:'toggleModel'},
						scope : this,
						masker : this,
						onSuccess : function(rs){
							var m = this.west.getSelectionModel().getSelection();
							if(m.length==1){
								this.center.store.load({id:m[0].get('id')});
							}else{
								this.center.store.load();
							}							
						}
					});
				}
			}
		});
	},
	saveSort : function(){
		var n = this.centerStore.getCount();
		if(n>0){
			var idArr = [],sort=[];
			for(var i=0;i<n;i++){
				idArr.push(this.centerStore.getAt(i).get('id'));
				sort.push(this.centerStore.getAt(i).get('sortId'));
			}
		}
		wfms.openLink({
			params : {ids :idArr.join(','),sort: sort.join(','),clsName:'RgModelAction',methodName:'sort'},
			scope : this,
			masker : this,
			onSuccess : function(rs){
				this.west.store.load();
				this.center.down('#sortBtn').setDisabled(true);
				for(var i=0;i<n;i++){
					this.centerStore.getAt(i).commit();
				}
				Ext.Msg.alert('提示','保存成功!');
			}
		});
	},
	dropNode : function(targetId,dropId){
		wfms.openLink({
			params : {targetId :targetId,dropId: dropId,clsName:'RgModelAction',methodName:'dropNode'},
			scope : this,
			masker : this.west,
			onSuccess : function(rs){
				Ext.Msg.alert('提示','保存成功!');
				this.centerStore.removeAll();
				this.westStore.load();
				
			}
		});
	}
});