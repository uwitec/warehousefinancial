Ext.define('wfms.admin.ModelWin',{
	_winType : 'add',
	closeAction : 'hide',
	extend : 'Ext.window.Window',
	width : 500,
	resizable : false,
	modal : true,	
	initComponent : function(){
		this.form = Ext.create('Ext.form.Panel',{
			border : false,		
			bodyPadding: 5,
			defaults : {labelAlign : 'right',anchor:'90%',xtype:'textfield'},
			items :[
				{name:'id',xtype:'hiddenfield'},
				{name:'p_id',xtype:'hiddenfield'},
				{name:'path',xtype:'hiddenfield'},
				{
			        fieldLabel: '模块名称',
			        name: 'name',
			        maxLenght : 50,
			        allowBlank: false
			    },
				{
			        fieldLabel: '模块地址',
			        name: 'url',
			        maxLenght : 100,
			        allowBlank: false
			    },
			    {
					fieldLabel:'状态',
	                xtype: 'radiogroup',
	                layout: 'hbox',
	                defaults: {
	                    name: 'flag',
	                    margins: '0 50 0 0'
	                },
	                items: [{
	                    inputValue: 1,
	                    boxLabel: '激活',
	                    checked: true
	                }, {
	                    inputValue: 0,
	                    boxLabel: '停用'
	                }]
	            },
				{
			        fieldLabel: '排序',
			        name: 'sortId',
			        value: 1,
			        maxValue: 99,
			        minValue: 1,
			        xtype : 'numberfield',
			        allowBlank: false
			    }
			]
		});
		this.items = [this.form];
		this.buttons = [
			{text:'保存',scope:this,handler:this._save},
			{text:'取消',scope:this,handler:function(){this.hide();}}
		];
		this.callParent();
		this.on('hide',function(){this.form.form.reset();},this);
		this.on('show',function(){	
			var o = this.fcmp;		
			if(this._winType=='add'){				
				var tree = o.up().down('treepanel');
				var node = tree.getSelectionModel().getSelection();
				if(node.length==1){
					this.form.form.findField('p_id').setValue(node[0].get('id'));
					this.form.form.findField('path').setValue(node[0].get('id'));
				}else{
					this.form.form.findField('p_id').setValue(tree.getRootNode().get('id'));
				}
			}else{
				var m = o.center.getSelectionModel().getSelection();
				this.form.form.loadRecord(m[0]);
			}			
			this.form.form.findField('name').focus(true,true);
		},this);
	},
	_save : function(){
		if(this.form.form.isValid()){
			var dataIn = this.form.form.getValues();
			dataIn.clsName = 'RgModelAction';
			dataIn.methodName = 'addModel';
			if(this._winType!='add'){
				dataIn.methodName = 'editModel';
			}
			wfms.openLink({
				params : dataIn,
				scope : this,
				masker : this,
				onSuccess : function(rs){
					this.hide();
					this.fcmp.west.store.load();
					this.fcmp.center.store.removeAll();
				}
			});
		}
	}
});