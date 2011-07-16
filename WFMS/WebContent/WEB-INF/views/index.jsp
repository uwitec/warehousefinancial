<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
    <title>欢迎使用仓储财务管理系统</title>
	<!-- Extjs导入 -->
	<link rel="stylesheet" href="<c:url value='/css/portal/Portal.css'/>" type="text/css"></link>
	<script type="text/javascript" src="<c:url value='/js/portal/Portal.js'/>"></script>
  	<script type="text/javascript" src="<c:url value='/js/portal/PortalColumn.js'/>"></script>
  	<script type="text/javascript" src="<c:url value='/js/portal/Portlet.js'/>"></script>
  	<script type="text/javascript" src="<c:url value='/js/index.js'/>"></script>
  	<link rel="stylesheet" href="<c:url value='/css/index.css'/>" type="text/css"></link>
  	<script type="text/javascript" src="<c:url value='/js/myportal.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/css/myportal.css'/>" type="text/css"></link>
	<style type="text/css">
		.view-menu{
			background:url('<c:url value="imgs/more/desktop/nav.png"/>') left top no-repeat;
			text-align:left;
			vertical-align:middle;
		}
		.desk-top{
			background:url('<c:url value="imgs/more/menus/system/company_menu.png"/>') left top no-repeat;
			text-align:left;
			vertical-align:middle;
		}
		.new-tab{
			text-align:left;
			background:url('<c:url value="imgs/more/menus/navigation.png"/>') left top no-repeat;
			vertical-align:middle;
		}
		.rs-remove{
			text-align:left;
			background:url('<c:url value="/imgs/rs_move.gif"/>') left top no-repeat;
		}
		.tree-collapse-all{
			background:url('<c:url value="/imgs/collapse-all.gif"/>') left top no-repeat;
			vertical-align:middle;
		}
		.tree-expand-all{
			background:url('<c:url value="/imgs/expand-all.gif"/>') left top no-repeat;
			vertical-align:middle;
		}
		#menuTree div{
			border:none;
		}
		
		
	</style>
  </head>
  <script type="text/javascript">
        /**
		*    消息构造  
		*/  
		function CLASS_MSN_MESSAGE(id,width,height,caption,title,message,target,action){  
		    this.id = id;  
		    this.title = title;  
		    this.caption= caption;  
		    this.message= message;  
		    this.target = target;  
		    this.action = action;  
		    this.width = width?width:200;  
		    this.height = height?height:120;  
		    this.timeout= 200;  
		    this.speed = 20; 
		    this.step = 1; 
		    this.right  = screen.width -1;  
		    this.bottom = screen.height; 
		    this.left = this.right - this.width; 
		    this.top = this.bottom - this.height; 
		    this.timer = 0; 
		    this.pause = false;
		    this.close = false;
		    this.autoHide = true;
		}  
		  
		/**//*  
		*    隐藏消息方法  
		*/  
		CLASS_MSN_MESSAGE.prototype.hide = function(){  
		    if(this.onunload()){  
		        var offset  = this.height>this.bottom-this.top?this.height:this.bottom-this.top; 
		        var me  = this;  
		        if(this.timer>0){   
		            window.clearInterval(me.timer);  
		        }  
		        var fun = function(){  
		            if(me.pause==false||me.close){
		                var x  = me.left; 
		                var y  = 0; 
		                var width = me.width; 
		                var height = 0; 
		                if(me.offset>0){ 
		                    height = me.offset; 
		                } 
		                y  = me.bottom - height; 
		     
		                if(y>=me.bottom){ 
		                    window.clearInterval(me.timer);  
		                    me.Pop.hide();  
		                } else { 
		                    me.offset = me.offset - me.step;  
		                } 
		                me.Pop.show(x,y,width,height);    
		            }             
		        }  
		
		        this.timer = window.setInterval(fun,this.speed)      
		    }  
		}  
		  
		/**//*  
		*    消息卸载事件，可以重写  
		*/  
		CLASS_MSN_MESSAGE.prototype.onunload = function() {  
		    return true;  
		}  
		/**//*  
		*    消息命令事件，要实现自己的连接，请重写它  
		*  
		*/  
		CLASS_MSN_MESSAGE.prototype.oncommand = function(){  
		    //this.close = true;
		    this.hide();  
			//window.location.href=contextPath+'/zhbgpt/txgl/znxxAction.do?method=initZnxxjs';
			//window.open(contextPath+'/zhbgpt/txgl/znxxAction.do?method=initZnxxjs');
		    
		    wdXxWin.show();//显示未读消息列表
		} 
		/**
		*    消息显示方法  
		*/  
		var sfxs = true;  //false:不在显示"消息提示"框
		CLASS_MSN_MESSAGE.prototype.show = function(){  
		    var oPopup = window.createPopup(); //IE5.5+  
		    this.Pop = oPopup;  
		    var w = this.width;  
		    var h = this.height;  
		    var str = "<DIV style='BORDER-RIGHT: #455690 1px solid; BORDER-TOP: #a6b4cf 1px solid; Z-INDEX: 99999; LEFT: 0px; BORDER-LEFT: #a6b4cf 1px solid; WIDTH: " + w + "px; BORDER-BOTTOM: #455690 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: " + h + "px; BACKGROUND-COLOR: #c9d3f3'>"  
		        str += "<TABLE style='BORDER-TOP: #ffffff 1px solid; BORDER-LEFT: #ffffff 1px solid' cellSpacing=0 cellPadding=0 width='100%' bgColor=#cfdef4 border=0>"  
		        str += "<TR>"  
		        str += "<TD style='FONT-SIZE: 12px;COLOR: #0f2c8c' width=30 height=24></TD>"  
		        str += "<TD style='PADDING-LEFT: 4px; FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #1f336b; PADDING-TOP: 7px' vAlign=center width='100%'>" + this.caption + "</TD>"  
		        str += "<TD style='PADDING-RIGHT: 2px; PADDING-TOP: 2px' vAlign=center align=right width=19>"  
		        str += "<SPAN title=关闭 style='FONT-WEIGHT: bold; FONT-SIZE: 12px; CURSOR: hand; COLOR: red; MARGIN-RIGHT: 4px' id='btSysClose' >×</SPAN></TD>"  
		        str += "</TR>"  
		        str += "<TR>"  
		        str += "<TD style='PADDING-RIGHT: 1px;PADDING-BOTTOM: 1px' colSpan=3 height=" + (h-28) + ">"  
		        str += "<DIV style='BORDER-RIGHT: #b9c9ef 1px solid; PADDING-RIGHT: 8px; BORDER-TOP: #728eb8 1px solid; PADDING-LEFT: 8px; FONT-SIZE: 12px; PADDING-BOTTOM: 8px; BORDER-LEFT: #728eb8 1px solid; WIDTH: 100%; COLOR: #1f336b; PADDING-TOP: 8px; BORDER-BOTTOM: #b9c9ef 1px solid; HEIGHT: 100%'>" + this.title + "<BR><BR>"  
		        str += "<DIV style='WORD-BREAK: break-all' align=left><A href='' hidefocus=false id='btCommand'><FONT color=#ff0000>" + "点击查看" + "</FONT></A>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<A href='' hidefocus=false id='ommand'><FONT color=#ff0000>不再提示</FONT></A></DIV>"  
		        str += "</DIV>"  
		        str += "</TD>"  
		        str += "</TR>"  
		        str += "</TABLE>"  
		        str += "</DIV>"  
		    oPopup.document.body.innerHTML = str; 
		    this.offset  = 0; 
		    var me  = this;
		    oPopup.document.body.onmouseover = function(){me.pause=true;}
		    oPopup.document.body.onmouseout = function(){me.pause=false;}
		    var fun = function(){  
		        var x  = me.left; 
		        var y  = 0; 
		        var width = me.width; 
		        var height = me.height; 
	            if(me.offset>me.height){ 
	                height = me.height; 
	            } else { 
	                height = me.offset; 
	            } 
		        y  = me.bottom - me.offset; 
		        if(y<=me.top){ 
		            me.timeout--; 
		            if(me.timeout==0){ 
		                window.clearInterval(me.timer);  
		                if(me.autoHide){
		                    me.hide(); 
		                }
		            } 
		        } else { 
		            me.offset = me.offset + me.step; 
		        } 
		        me.Pop.show(x,y,width,height);    
		
		    }  
		    this.timer = window.setInterval(fun,this.speed)      
		    var btClose = oPopup.document.getElementById("btSysClose");  
		    btClose.onclick = function(){  
		        me.close = true;
		        me.hide();  
		    }  
		    var btCommand = oPopup.document.getElementById("btCommand");  
		    btCommand.onclick = function(){  
		        me.oncommand();  
		    }    
			 var ommand = oPopup.document.getElementById("ommand");  
		      ommand.onclick = function(){  
		      sfxs=false;
		      me.close = true;
		      me.hide(); 
		    }   
		}  
		/** 
		** 设置速度方法 
		**/ 
		CLASS_MSN_MESSAGE.prototype.speed = function(s){ 
		    var t = 20; 
		    try { 
		        t = praseInt(s); 
		    } catch(e){} 
		    this.speed = t; 
		} 
		/**//* 
		** 设置步长方法 
		**/ 
		CLASS_MSN_MESSAGE.prototype.step = function(s){ 
		    var t = 1; 
		    try { 
		        t = praseInt(s); 
		    } catch(e){} 
		    this.step = t; 
		} 
		  
		CLASS_MSN_MESSAGE.prototype.rect = function(left,right,top,bottom){ 
		    try { 
		        this.left = left!=null?left:this.right-this.width; 
		        this.right = right!=null?right:this.left +this.width; 
		        this.bottom = bottom!=null?(bottom>screen.height?screen.height:bottom):screen.height; 
		        this.top= top!=null?top:this.bottom - this.height; 
		    } catch(e){} 
		} 
  
  	
  		//定时刷新
		/*var messageTimer = setInterval(function(){
			Ext.lib.Ajax.request(
				'POST',
				contextPath+'/zhbgpt/txgl/znxxAction.do?method=getZnxxjsByJsr',
				{
					success:function(response){
						var rs = Ext.decode(response.responseText);
						var result = parseInt(rs.message);
						var wdMail = parseInt(rs.other)
							//“wdMsg,wdMail”是index.js中的元素
							document.getElementById("wdMsg").innerText=result;
							document.getElementById("wdMail").innerText=wdMail;
							if(result>0 && sfxs==true){
								var messages = "你有"+result+"条站内短消息未阅读！";
								var MSG1 = new CLASS_MSN_MESSAGE("aa",180,110,"消息提示",messages,messages);  
							    MSG1.rect(null,null,null,screen.height-50); 
							    MSG1.speed = 30; 
							    MSG1.step = 10; 
							    MSG1.show(); 
							}
					},failure:function(){
					}
				}
			);
		}
		,20000);*/
		
			
		//列表数据对象
		var dataStore = new Ext.data.JsonStore({ 
			autoDestroy: true,
			pruneModifiedRecords:true,
			root:'dataList',
			totalProperty:'totalCount',
	        url: contextPath+'/zhbgpt/txgl/znxxAction.do?method=getZnwdxxByJsr',
			fields:["id","jsrid","jsrxm","yysj","hfxxid","sfyd","sfhf","bz","xxfs.id",
			"xxfs.fsrxm","xxfs.fssj","xxfs.xxnr","xxfs.xxlb",]
		});
		dataStore.load({params:{start:0,limit:15}});
		var checkboxModel = new Ext.grid.CheckboxSelectionModel({
			listeners: {
		    	selectionchange: function(checkboxModel) {},
		        rowselect:function(checkboxModel,ind,r){}
		   }
		});
		//标记是否阅读
		var _sfyd ={
			id:'sfyd',
			text:'标记为已阅读',
			tooltip:{title:'标记为已阅读',text:'id:sfyd'},
			icon:contextPath+'/imgs/more/btn/info/inread.png'
		};
		//工具栏
		var dataTbar = new Ext.Toolbar({ items:["工具栏："] });
		dataTbar.add(_sfyd);
		//列表
		var dataGrid = new Ext.grid.EditorGridPanel({
			stripeRows: true,
			autoScroll:true,
			trackMouseOver:true,
			border:false,
	        loadMask:true,
	        sm:checkboxModel,
	       	cm:new Ext.grid.ColumnModel([ 
				{
					header:'是否阅读',
					dataIndex:'sfyd',
					width:60,
					renderer:function(value){
						return value == '0' ? '<img src="${pageContext.request.contextPath}/imgs/more/btn/info/email.png"/>' : '<img src="${pageContext.request.contextPath}/imgs/more/btn/info/email_open.png"/>' ;
					}
				},{
					header:'是否回复',
					dataIndex:'sfhf',
					width:60,
					renderer:function(value){
						return value == '0' ? '' : '<img src="${pageContext.request.contextPath}/imgs/replied.gif"/>' ;
					}
				},{
					header:'发送人',
					dataIndex:'xxfs.fsrxm'
				},{
					header:'消息内容',
					width:300,
					dataIndex:'xxfs.xxnr'
				},{
					header:'发送时间',
					dataIndex:'xxfs.fssj'
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
		//标记为已阅读
		Ext.getCmp('sfyd').on('click',function(){
			var r = checkboxModel.getSelected();   
			if(r == null){
				Ext.Msg.alert("操作提示","请选择一条消息进行标记！");
				return;
			}
			var id = r.get('id');
			Ext.lib.Ajax.request(
				'POST',
				contextPath+'/zhbgpt/txgl/znxxAction.do?method=isRead&id='+id,
				{
					success:function(response){
						var rs = Ext.decode(response.responseText);
						if(rs.success){
							dataStore.reload();
						}else{
							Ext.Msg.alert("提示",rs.message);
						}
					},
					failure:function(){
						Ext.Msg.alert("提示","服务器连接失败!");
					}
				}
			);
		});
		//显示未读消息
		var wdXxWin = new Ext.Window({ 
			width:550,
			height:400,
			modal:true,
			plain:true,
			layout:'fit',
			resizable:false, 
			title:'未读消息', 
			xtype:'panel',
			tbar:dataTbar,
			closeAction:'hide',
			buttons:[{
					text:'关  闭',
					handler:function(){
						wdXxWin.hide();
					}
				}
			],
			items:[dataGrid]
		});
			
		
	</script>
  <body>
  	<input id="dqdlr" type=hidden value="${yh.username}"/>
  </body>
</html>