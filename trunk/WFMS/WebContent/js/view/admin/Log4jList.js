Ext.define('wfms.admin.Log4jList',{
	extend : 'Ext.grid.Panel',
	alias : 'widget.log4jlist',	    
    columnLines: true,
    loadMask : true,    
    verticalScrollerType: 'paginggridscroller',
    disableSelection: true,
   	invalidateScrollerOnRefresh: false,	   
    columns: [//stamp,thread, infolevel,clazz,message
    	{xtype: 'rownumberer',width: 50, sortable: false,align:'center'},
    	{header: 'stamp',  dataIndex: 'stamp',width:180},
        {header: 'thread',  dataIndex: 'thread',width:180},
    	{header: 'infolevel',  dataIndex: 'infolevel',width:150},
    	{header: 'clazz',  dataIndex: 'clazz',width:150},
    	{header: 'message',  dataIndex: 'message',width:150}
    ],
	initComponent : function(){
		this.viewConfig = {
	    	stripeRows: true,
	        trackOver: true,
	        emptyText : '<div style="color:#999;margin:5px;">当前没有记录显示<div>'
	    };
		this.store = Ext.create('Ext.data.Store', {
	        fields: ['infolevel', 'clazz','message','thread','stamp'],
	        buffered: true,  
	        pageSize: 25,      
	        proxy: {
	            type: 'ajax',
	            actionMethods : 'post',           
	            url : wfms.defaultUrl(), 
	            timeout : 1000*60*3,           
	            extraParams : {clsName:'Log4jListAction',methodName:'getlogList'},                        
		        reader: {
		            type: 'json',
		            root: 'results',
		            totalProperty: 'totalCount'
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
	    this.tbar = [
	    	{text:'刷新',iconCls:'refresh',scope:this,handler:function(){this.store.load();},itemId:'refreshBtn'}
	    ],
		this.callParent();
		this.on('afterrender',function(gp){
			this.store.guaranteeRange(0, 24);
		},this);
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
	}
});