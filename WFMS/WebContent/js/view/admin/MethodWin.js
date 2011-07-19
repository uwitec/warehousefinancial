Ext.define('wfms.admin.MethodWin',{
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
				{
			        fieldLabel: '名称',
			        name: 'zhName',
			        maxLenght : 50,
			        allowBlank: false
			    },
				{
			        fieldLabel: '别名',
			        name: 'enName',
			        maxLenght : 20,
			        allowBlank: false
			    },
				{
			        fieldLabel: '排序',
			        name: 'sortId',
			        value: 1,
			        maxValue: 99,
			        minValue: 1,
			        xtype : 'numberfield',
			        allowBlank: false
			    },
				{
			        fieldLabel: '备注',
			        name: 'remark',
			        xtype : 'textareafield',
			        maxLenght : 50
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
			if(this._winType!='add'){				
				var m = o.getSelectionModel().getSelection();
				this.form.form.loadRecord(m[0]);
			}			
			this.form.form.findField('zhName').focus(true,true);
		},this);
	},
	_save : function(){
		if(this.form.form.isValid()){
			var dataIn = this.form.form.getValues();
			dataIn.clsName = 'RgMethodAction';
			dataIn.methodName = 'addMethod';
			if(this._winType!='add'){
				dataIn.methodName = 'editMethod';
			}
			wfms.openLink({
				params : dataIn,
				scope : this,
				masker : this,
				onSuccess : function(rs){
					this.hide();
					this.fcmp.store.load();
				}
			});
		}
	}
});