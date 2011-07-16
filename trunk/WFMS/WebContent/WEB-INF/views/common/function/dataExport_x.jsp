<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<title>数据导出</title>
<!-- Extjs导入 -->
<style type="text/css">
    .store-info {
        background: url('<c:url value="/imgs/store-info.gif"/>') left top no-repeat;
    }
    
    body {
        font-size: 12px;
    }
</style>
<script type="text/javascript">
    Ext.onReady(function(){
        Ext.QuickTips.init();
        Ext.form.Field.prototype.msgTarget = 'side';
        
        var individual = [{
            bodyStyle: 'padding-left:5px;',
            items: {
                xtype: 'fieldset',
                title: '导出类型',
                autoHeight: true,
                defaultType: 'radio', // each item will be a radio button
                items: [{
                    xtype: 'textfield',
                    name: 'fileName',
                    fieldLabel: '文件名',
                    allowBlank: false
                }, {
                    checked: true,
                    fieldLabel: '导出类型',
                    boxLabel: '文本文件',
                    name: 'fileType',
                    inputValue: 'text'
                }, {
                    fieldLabel: '',
                    labelSeparator: '',
                    boxLabel: 'Excel文件',
                    name: 'fileType',
                    inputValue: 'excel'
                }, {
                    fieldLabel: '',
                    labelSeparator: '',
                    boxLabel: 'PDF文件',
                    name: 'fileType',
                    inputValue: 'pdf'
                }]
            }
        }];
        
        // combine all that into one huge form
        var fp = new Ext.FormPanel({
            title: '数据导出测试',
            frame: true,
            labelWidth: 110,
            width: 600,
			isUpload: true,
            id: 'exportForm',
            renderTo: 'form-ct',
            bodyStyle: 'padding:0 10px 0;',
            items: [{
                layout: 'column',
                border: false,
                defaults: {
                    columnWidth: '.5',
                    border: false
                },
                items: individual
            }],
            buttons: [{
                text: '导出',
                handler: function(){
                    if (fp.getForm().isValid()) {
						/*window.location.href='<c:url value="/common/dataExportAction.do?method=commonExp"/>'+
						'&'+fp.getForm().getValues(true);*/
						fp.getForm().getEl().dom.action='<c:url value="/common/dataExportAction.do?method=commonExp"/>';
                        fp.getForm().getEl().dom.submit();
						Ext.MessageBox.show({
                            msg: '正在下载, 请稍等...',
                            waitConfig: {
                                text: '下载中...'
                            },
                            width: 300,
                            wait: true
                        });
                        window.onblur = function(){
                            Ext.MessageBox.hide();
                            window.onblur = null;
                            //fp.destroy();
                        }
                    }
                }
            }, {
                text: '重置',
                handler: function(){
                    fp.getForm().reset();
                }
            }]
        });
    });
</script>
</head>
<body style="text-align:center">
    <div id="form-ct">
    </div>
</body>
</html>
