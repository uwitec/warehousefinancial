<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<title>学生信息</title>
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
        //new Ext.QuickTips.init();//提示信息加载
        /*var drbStore = new Ext.data.JsonStore({
         url:contextPath+'/system/lxdmglAction.do?method=getLxdmBySjjc&dmjc=DRB',
         fields:["dmjc","dmmc"]
         });*/
        var cm = new Ext.grid.ColumnModel([{
            header: '项',
            dataIndex: 'item'
        }, {
            header: '条件符',
            dataIndex: 'op'
        }, {
            header: '值',
            dataIndex: 'val',
            editor: new Ext.gridGridEditor(new Ext.form.TextField({
                allowBlank: true
            }))
        }]);
        var condGrid = new Ext.grid.EditorGridPanel({
            cm: cm
        });
        var statTabData = [['XSJBXXB', '学生表']];
        
        var statObjData = [['ZYID', '专业'], ['XB', '性别'], ['MZ', '民族'], ['JG', '籍贯'], ['XZ', '学制'], ['XSLBDM', '学生类别'], ['SYD', '生源地'], ['ZZMM', '政治面貌']];
        
        var statCategoryData = [['专业', 'ZYID'], ['年级', 'DQSZJ'], ['班级', 'BJID']];
        
        //统计对象store
        var statObjStore = new Ext.data.SimpleStore({
            fields: ['value', 'text'],
            data: statObjData
        });
        //统计分类store
        var statCategoryStore = new Ext.data.SimpleStore({
            fields: ['text', 'value'],
            data: statCategoryData
        });
        
        var statTabStore = new Ext.data.SimpleStore({
            fields: ['value', 'text'],
            data: statTabData
        });
        
        var statTabCombo = new Ext.form.ComboBox({
            store: statTabStore,
            emptyText: '请选择',
            mode: 'local',
            triggerAction: 'all',
            valueField: 'value',
            textField: 'text',
            displayField: 'text',
            hiddenName: 'statTab',
            readOnly: true,
            id: 'tab',
            minListWidth: 220,
            pageSize: 5
        });
        
        var statObjCombo = new Ext.form.ComboBox({
            store: statObjStore,
            emptyText: '请选择',
            mode: 'local',
            triggerAction: 'all',
            valueField: 'value',
            textField: 'text',
            displayField: 'text',
            hiddenName: 'statObj',
            readOnly: true,
            id: 'obj',
            minListWidth: 220,
            pageSize: 5
        });
        
        var statCategoryCombo = new Ext.form.ComboBox({
            store: statCategoryStore,
            emptyText: '请选择',
            mode: 'local',
            triggerAction: 'all',
            valueField: 'value',
            textField: 'text',
            displayField: 'text',
            hiddenName: 'statCategory',
            readOnly: true,
            id: 'category',
            minListWidth: 220,
            pageSize: 5
        });
        
        var btnStat = new Ext.Button({
            width: 100,
            height: 35,
            text: '统计',
            handler: dataStat
        });
        
        var fp = new Ext.FormPanel({
            title: '统计选项',
            frame: true,
            labelWidth: 110,
            width: 600,
            isUpload: true,
            id: 'itemForm',
            bodyStyle: 'padding:0 10px 0;',
            items: [{
                layout: 'column',
                border: false,
                defaults: {
                    columnWidth: '.5',
                    border: false
                },
                items: [statTabCombo, statObjCombo, statCategoryCombo, btnStat]
            }]
        });
        new Ext.form.FieldSet({
            width: '100%',
            height: 50,
            title: '统计选项',
            collapsible: true,
            renderTo: 'stat_panel',
            layout: 'hbox',
            layoutConfig: {
                align: 'top'
            },
            defaults: {
                margins: '0 10 10 0'
            },
            items: [{
                border: false,
                flex: 1.5,
                bodyStyle: 'text-align:center;padding-top:80px',
                layout: 'form',
                defaults: {
                    border: false
                },
                items: [fp]
            }]
        });
        
        var condView = new Ext.form.FieldSet({
            width: '100%',
            height: 260,
            title: '统计条件',
            collapsible: true,
            renderTo: 'cond_panel',
            layout: 'hbox',
            layoutConfig: {
                align: 'stretch'
            },
            defaults: {
                margins: '0 8 0 0'
            },
            items: [condGrid]
        });
        
        var dataView = new Ext.form.FieldSet({
            width: '100%',
            height: 360,
            title: '统计数据',
            collapsible: true,
            renderTo: 'data_panel',
            layout: 'hbox',
            html: '<iframe id="stat_view" src="http://www.xunersoft.com" style="overflow:visible" width="100%" height="100%" frameborder="0">',
            layoutConfig: {
                align: 'stretch'
            },
            defaults: {
                margins: '0 8 0 0'
            },
            items: []
        });
        
        function dataStat(){
            //var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息')
            var statObj = Ext.getCmp('obj').getRawValue();
            var statCategory = Ext.getCmp('category').getRawValue();
            
            var rowKey = Ext.getCmp('obj').getValue();
            var columnKey = Ext.getCmp('category').getValue();
            if (rowKey == columnKey) {
                return;
            }
            var getUrl = '<c:url value="/common/dataStatAction.do?method=dataStat&statObj="/>' + Ext.encode(statObj) + '&statCategory=' + Ext.encode(statCategory);
            getUrl += '&row=' + rowKey + '&column=' + columnKey;
            Ext.get("stat_view").dom.src = getUrl;
            Ext.get("stat_view").dom.src = '<c:url value="statTable.html"/>';
            //document.getElementById('stat_view').src=getUrl;
        }
    });
</script>
</head>
<body style="text-align:center;">
    <input type="hidden" id='qx' value='${qxlx}'>
    <table style='margin:10px;width:95%' border='0'>
        <tr>
            <td style="width:100%;" id="stat_panel">
            </td>
        </tr>
        <tr>
            <td style="width:100%;" id="cond_panel">
                asdf
            </td>
        </tr>
        <tr>
            <td style="width:100%;" id="data_panel">
            </td>
        </tr>
    </table>
</body>
</html>
