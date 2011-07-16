<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<title>人员信息导入</title>
<!-- Extjs导入 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common_viewport.css" type="text/css">
</link>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/list_viewport.js">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bjSelector.js">
</script>
<style type="text/css">
    .store-info {
        background: url('${pageContext.request.contextPath}/imgs/store-info.gif') left top no-repeat;
    }
    
    body {
        font-size: 12px;
    }
</style>
<script type="text/javascript">
    Ext.onReady(function(){
        new Ext.QuickTips.init();
        var main = new MianFrame();
        setNavigate("人事管理->人员导入", "");
        var form = new Ext.form.FormPanel({
            labelAlign: 'right',
            title: '文件上传',
            labelWidth: 60,
            frame: false,
            url: contextPath + '/common/dataImportAction.do?method=dataImport&tableName=JWGL_JXRWB',
            width: 300,
            height: 200,
            fileUpload: true,
            //layout:'fit',
            waitMsg: '上传中,请稍等……',
            bodyStyle: 'text-align:center;padding-top:20px',
            items: [{
                anchor: '80%',
                paddings: '5 0 0 5',
                margins: '5 0 0 5',
                title: '选择excel数据文件',
                xtype: 'fieldset',
                layout: 'column',
                items: [{
                    columnWidth: .7,
                    xtype: 'textfield',
                    fieldLabel: '文件名',
                    allowBlank: false,
                    blankText: '请选择上传文件',
                    name: 'multiFile',
                    inputType: 'file',//文件类型
                    anchor: '90%'
                }, {
                    xtype: 'panel',
                    border: false,
                    columnWidth: .02
                }, {
                    xtype: 'button',
                    text: '执行导入',
                    columnWidth: .1,
                    handler: function(){
                        form.getForm().submit();
                    }
                }]
            }, {
                bodyStyle: 'margin:5 0 0 5;text-align:left;',
                anchor: '80%',
                paddings: '5 0 0 5',
                margins: '5 0 0 5',
                title: '导入结果',
                xtype: 'fieldset',
                layout: 'column',
                collapsible: true,
                items: [{
                    autoScroll: true,
                    xtype: 'grid',
                    stripeRows: true,
                    id: 'resultGrid',
                    loadMask: true,
					title:'导入结果:',
                    columnLines: true,
					height:200,
					viewConfig: {
                		forceFit: true
            		},
                    store: new Ext.data.JsonStore({
		                data: [],
		                autoDestroy: true,
		                fields:['column0','column1','column2']
            		}),
					cm:new Ext.grid.ColumnModel([
					{
						header:'行号',
						dataIndex:'column0',
						width:10
					},
					{
						header:'类型',
						dataIndex:'column2',
						width:20
					},
					{
						header:'信息',
						dataIndex:'column1'
					}])
                }]
            }],
            onSubmit: Ext.emptyFn,
            submit: function(){
                if (form.getForm().isValid()) {
                    this.getEl().dom.action = contextPath + '/common/dataImportAction.do?method=dataImport&tableName=RYB'; //连接到服务器的url地址
                    this.getEl().dom.submit();
                    var wm = Ext.MessageBox.wait('人员信息导入中，请稍候...', '提示信息');
                }
            }
        });
		var resultGrid = Ext.getCmp('resultGrid');
		resultGrid.on('render', function(grid){
            var store = grid.getStore(); // Capture the Store.    
            var view = grid.getView(); // Capture the GridView.    
            resultGrid.tip = new Ext.ToolTip({
                target: view.mainBody, // The overall target element.    
                delegate: '.x-grid3-row', // Each grid row causes its own seperate show and hide.    
                trackMouse: true, // Moving within the row should not hide the tip.    
                renderTo: document.body, // Render immediately so that tip.body can be referenced prior to the first show.    
                listeners: { // Change content dynamically depending on which element triggered the show.    
                    beforeshow: function updateTipBody(tip){
                        var rowIndex = view.findRowIndex(tip.triggerElement);
                        //tip.body.dom.innerHTML = "Over Record ID " + store.getAt(rowIndex).id;
                    	tip.body.dom.innerHTML = store.getAt(rowIndex).get('column1');
					}
                }
            });
        });
        dataPanel.add(form);
		function importResponse(){
			var msg = "${sessionScope.msg.success}";
			var strArry = new Array();
			<c:if test="${not empty sessionScope.report.errorList}">
				<c:forEach items="${sessionScope.report.errorList}" var="error">
					strArry.push({"column0":"${error.row}","column1":"${error.msg}","column2":"失败"});
				</c:forEach>
			</c:if>
           Ext.getCmp('resultGrid').getStore().loadData(strArry);
        	<c:if test="${not empty sessionScope.msg}">
			 	Ext.getCmp('resultGrid').setTitle("导入结果：" + (msg=="true"?'成功':'失败')+" 成功导入"+"${sessionScope.report.successCount}个人员,更新了${sessionScope.report.mergeCount}个人员,失败数:${sessionScope.report.failedCount},可读excel数据量:${sessionScope.report.recordCount}");
			</c:if>
		}
		importResponse();		
		
    });
</script>
</head>
<body>
    <input type="hidden" id='qx' value='${qxlx}'><input type="hidden" id='mkid' value='${mkid}'>
    <table width="100%" height='100%'>
        <tr>
            <td align="center" valign="middle">
                <c:if test="${not empty sessionScope.msg}">
                    <script type="text/javascript">
            			var errorList = "";
						<c:forEach items="${sessionScope.report.errorList}" var="error">
							errorList+="${error.row}行:${error.msg}"+"\n";
						</c:forEach>
            			//Ext.Msg.alert('操作', "${sessionScope.msg.success?'导入成功sessionScope.msg.report.successCount':sessionScope.msg.message}\n"+errorList);
				    </script>
                </c:if>
                <%
                session.removeAttribute("msg");
                session.removeAttribute("report"); %>
                <div id='loadDiv' style="font-size:12px;">
                    <img width='50' height='50' src='${pageContext.request.contextPath}/imgs/login-load.gif'>数据加载中，请稍后 . . .
                </div>
            </td>
        </tr>
    </table>
</body>
</html>