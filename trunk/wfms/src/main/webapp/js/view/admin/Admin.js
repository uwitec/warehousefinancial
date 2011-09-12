Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', wfms.getAppName()+'/ext4/ux');
Ext.Loader.setPath('wfms.admin', wfms.getAppName()+'/js/view/admin');
Ext.require([
    'Ext.ux.form.SearchField',
    'wfms.admin.ModelPanel',
    'wfms.admin.ModelWin',
    'wfms.admin.MethodPanel',
    'wfms.admin.MethodWin',
    'wfms.admin.Log4jList'
]);

Ext.define('wfms.admin.MainPanel',{
	extend : 'Ext.container.Viewport',
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
	            text: '仓储财务管理系统',
	            expanded: true
	        }
	    });
	    
		this.functionPanel = Ext.create('Ext.tree.Panel',{
			margins : '2 2 2 2',
			region : 'west',
			title: '功能菜单',
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
		    		//o.store.on('beforeload',function(){this.west.down('[name=refresh]').disable();},this);	
		    		//o.store.on('load',function(ds,records,successful){
		    			//this.west.down('[name=refresh]').enable();
		    		//},this);	
		    	},
		    	'itemclick':function(view, record, htme, index, e){
		    		//this.center.store.load({id:record.get('id')});
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
		this.topPanel = Ext.create('Ext.Component',{
	        region:'north',
	        el:'header',
	        border:false,
	        margins: '1 0 2 0'
		});
		
		this.center = Ext.create('Ext.tab.Panel',{
	       	enableTabScroll: true,
	       	region : 'center',
	       	activeTab : 0,
	       	layout : 'fit',
	       	items : [
	       		{xtype:'modelpanel',title:'模块管理'},
	       		{xtype:'methodPanel',title:'功能管理'},
	       		{xtype:'log4jlist',title:'日志管理'}
	       	]
		});
		this.items = [this.functionPanel,this.center,this.topPanel];
		this.callParent();
		this.on('afterrender',function(){
			Ext.get('logout-link').on('click',function(){
				Ext.Msg.show({
					icon : Ext.Msg.QUESTION,
					buttons : Ext.Msg.YESNO,
					width : 120,
					scope : this,
					msg : '是否退出系统?',
					fn : function(btn){
						if(btn=='yes'){							
							wfms.openLink({
								params : {action:'logout',clsName:'LoginAction',methodName:'logout'},
								scope : this,
								masker : this,
								onSuccess : function(rs){
									window.location.href = wfms.getAppName()+'/index.jsp';
								}
							});
						}
					}
				});	
			},this);
			
			setTimeout(function(){
                Ext.get("loading").remove();
                Ext.get("loading-mask").fadeOut({duration:1,remove:true});
            }, 100);
		},this);
	}
});

initAdminPanel = function(){
	Ext.Ajax.request({
	    url: 'system/user_manage/load.do',
	    params:{clsName : 'LoginAction',methodName : 'getUserInfo',detailId:'1'},
	    method : 'post',
	    success : function(response, opts){
	      	var rs = response.responseText?Ext.decode(response.responseText):{};		
			if(rs.success){
				new wfms.admin.MainPanel();
			}else{
				Ext.get('loading').remove();
		        Ext.get('loading-mask').fadeOut({
		            duration: .5,
		            remove: true
		        });
				Ext.Msg.show({
				     title:'Message',
				     msg: rs.msg||'Error!!',
				     width : 280,
				     buttons: Ext.Msg.OK,
				     icon: Ext.Msg.WARNING,
				     closable : false,
				     fn : function(btn){				     	
						window.location.href = wfms.getAppName()+'/login.html';
				     }
				});
				
			}				
	     },
	     failure : function(response, opts){
	     	Ext.get('loading').remove();
	        Ext.get('loading-mask').fadeOut({
	            duration: .5,
	            remove: true
	        });
	     	var rs = response.responseText?Ext.decode(response.responseText):{};
			Ext.Msg.show({
			     title:'Message',
			     msg: rs.msg||'Error!!',
			     width : 280,
			     buttons: Ext.Msg.OK,
			     icon: Ext.Msg.WARNING,
			     closable : false,
			     fn : function(btn){
					window.location.href = wfms.getAppName()+'/login.html';
			     }
			});
	     }
	});	
}