<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<title>统计报表</title>
<!-- Extjs导入 -->
<script type="text/javascript" src="<c:url value='/js/charts/charts.js'/>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/charts/FusionCharts.js">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/charts/chartwindow.js">
</script>
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
        Ext.chart.Chart.CHART_URL = contextPath+'/extjs/resources/charts.swf';

		new Ext.QuickTips.init();//提示信息加载
		/**
		 * 统计选项
		 */
		var objStore = new Ext.data.SimpleStore(
		{
			 fields: ['value', 'text']
		});
		
		var categoryStore = new Ext.data.SimpleStore({
			fields: ['value', 'text']
		});
		
		/**
		 * 统计条件
		 */
		var statDefineStore = new Ext.data.JsonStore({
				autoDestroy: true,
				autoLoad:true,
				pruneModifiedRecords:true,
		        url: contextPath+'/common/dataStatAction.do?method=getAllStatTable',
				fields:['ssb','ssbmc','tjdx','tjfl','sql']
		});
		
        var comboData = [['>', '>'], ['=', '='], ['<', '<']];
        var relationOpData = [['&&', '且'], ['||', '或']];
        var cm = new Ext.grid.ColumnModel([{
            header: '项',
            dataIndex: 'item',
            editor: new Ext.grid.GridEditor(new Ext.form.TextField({
                allowBlank: false
            }))
        }, {
            header: '操作符',
            dataIndex: 'op',
            editor: new Ext.grid.GridEditor(new Ext.form.ComboBox({
                store: new Ext.data.SimpleStore({
                    fields: ['value', 'text'],
                    data: comboData
                }),
                emptyText: '请选择',
                mode: 'local',
                triggerAction: 'all',
                valueField: 'value',
                displayField: 'text',
                readOnly: true
            }))
        }, {
            header: '值',
            dataIndex: 'val',
            editor: new Ext.grid.GridEditor(new Ext.form.TextField({
                allowBlank: false
            }))
        }, {
            header: '关系操作符',
            dataIndex: 'relationOp',
            editor: new Ext.grid.GridEditor(new Ext.form.ComboBox({
                store: new Ext.data.SimpleStore({
                    fields: ['value', 'text'],
                    data: relationOpData
                }),
                emptyText: '请选择',
                mode: 'local',
                triggerAction: 'all',
                valueField: 'value',
                displayField: 'text',
                readOnly: true
            }))
        }]);
        
        var data = [['1', '=', 'text1', '&&'], ['2', '=', 'text2', '&&'], ['3', '=', 'text3', '&&']];
        
        var store = new Ext.data.Store({
            proxy: new Ext.data.MemoryProxy(data),
            reader: new Ext.data.ArrayReader({}, [{
                name: 'item'
            }, {
                name: 'op'
            }, {
                name: 'val'
            }, {
                name: 'relationOp'
            }])
        });
        
        store.load();
        
        var condGrid = new Ext.grid.EditorGridPanel({
            autoHeight: true,
            flex: 5.5,
            autoScroll: true,
            loadMask: true,
            store: store,
            cm: cm
        });
        
        var statTabCombo = new Ext.form.ComboBox({
            store: statDefineStore,
            emptyText: '请选择',
            mode: 'remote',
            triggerAction: 'all',
            valueField: 'ssb',
            displayField: 'ssbmc',
            hiddenName: 'statTab',
            readOnly: true,
            id: 'tab',
            minListWidth: 160,
            height: 30,
			listeners:{
				'select':function(combo,r,ind){
					var tjdxData = Ext.decode(r.get('tjdx'));
					var tjflData = Ext.decode(r.get('tjfl'));
					
					statObjCombo.getStore().loadData(tjdxData);
					statCategoryCombo.getStore().loadData(tjflData);
				}
			}
        });
        
        var statObjCombo = new Ext.form.ComboBox({
            store: objStore,
            emptyText: '请选择',
            mode: 'local',
            triggerAction: 'all',
            valueField: 'value',
            displayField: 'text',
            hiddenName: 'statObj',
            readOnly: true,
            id: 'obj',
            minListWidth: 160,
            height: 30
        });
        
        var statCategoryCombo = new Ext.form.ComboBox({
            store: categoryStore,
            emptyText: '请选择',
            mode: 'local',
            triggerAction: 'all',
            valueField: 'value',
            displayField: 'text',
            hiddenName: 'statCategory',
            readOnly: true,
            id: 'category',
            minListWidth: 160,
            height: 30
        });
        
        var btnStat = new Ext.Button({
            width: 60,
            height: 20,
            text: '统计',
            handler: dataStat
        });
        
        var fp = new Ext.FormPanel({
            title: '统计选项',
            frame: true,
            labelWidth: 110,
            width: '100%',
            autoHeight: true,
            id: 'itemForm',
            bodyStyle: 'padding:0 10px 0;',
            items: [{
                layout: 'column',
                border: false,
                defaults: {
                    columnWidth: '.25',
                    border: false
                },
                items: [statTabCombo, statObjCombo, statCategoryCombo, btnStat]
            }]
        });
		
        function dataStat(){
			var rowKey = Ext.getCmp('obj').getValue();
            var columnKey = Ext.getCmp('category').getValue();
            if (rowKey == columnKey) {
                return;
            }
			Ext.Ajax.request({
            method: 'POST',
            url: contextPath + '/common/dataStatAction.do?type=fusion.json',
            success: function(response){
			    var fusionChartData = Ext.decode(response.responseText);
				Ext.getCmp('objectView').removeAll(true);
				Ext.getCmp('categoryView').removeAll(true);
				Ext.getCmp('columnView').removeAll(true);
				
				Ext.getCmp('objectView').add(Fusion.getJSONChart(0, fusionChartData));
				Ext.getCmp('categoryView').add(Fusion.getJSONChart(0,fusionChartData,true));
				Ext.getCmp('columnView').add(Fusion.getJSONChart(1,fusionChartData));
				
				Ext.getCmp('objectView').doLayout();
				Ext.getCmp('categoryView').doLayout();
				Ext.getCmp('columnView').doLayout();
				//document.getElementById('tabFrame').reload();
				//var chartWin = new FusionWindow(fusionChartData);
				//chartWin.show();
				//Ext.get("stat_view").dom.src = contextPath + '/common/statTable.html';
            },
            failure: function(){
                Ext.Msg.alert("提示", "统计失败,服务器无法连接!");
            },
            params: {
                tableName: 'zsjygl_zsgl_xsjbxxb',
                rowKeyType: Ext.getCmp('obj').getValue(),
                columnKeyType: Ext.getCmp('category').getValue(),
                statCategory: Ext.getCmp('category').getRawValue(),
                statTarget: Ext.getCmp('obj').getRawValue(),
				conditions: ''
            }
        });
            /*var waitMsg = Ext.MessageBox.wait('数据加载中，请稍候...','提示信息')
            var statObj = Ext.getCmp('obj').getRawValue();
            var statCategory = Ext.getCmp('category').getRawValue();
            
            var rowKey = Ext.getCmp('obj').getValue();
            var columnKey = Ext.getCmp('category').getValue();
            if (rowKey == columnKey) {
                return;
            }
            var getUrl = contextPath+'/common/dataStatAction.do?method=dataStat&statObj=' + Ext.encode(statObj) + '&statCategory=' + Ext.encode(statCategory);
            getUrl += '&row=' + rowKey + '&column=' + columnKey;
			alert(getUrl);
            Ext.get("stat_view").dom.src = getUrl;*/
            //Ext.get("stat_view").dom.src = 'function/statTable.html';
            //document.getElementById('stat_view').src=getUrl;
        }
        
        new Ext.Viewport({
            id: 'mianFrame',
            layout: 'border',
            items: [{
                id: 'navigate',
                region: 'north',
                border: false,
                baseCls: 'x-plain',
                height: 40,
                margins: '0 4 0 4',
                html: '<table width="100%" height="100%"><tr><td width="20" rowspan="2"><img height="40" width="45" src="'+contextPath+'/imgs/navigate/chart.png"/></td><td style="font-size:13px;padding-left:5px;color:#15428b;font-weight:bold">综合功能 >> 数据统计</td></tr><tr><td style="font-size:11px;padding-left:5px;color:#15428b;">该模块为用户提供方便的统计视图，用户可根据自己的需求统计数据</td></tr></table>'
            }, {
                xtype: 'panel',
                region: 'east',
                collapsed: true,
                title: '相关操作',
                iconCls: 'store-info',
                width: 270,
                collapsible: true,
                margins: '4 4 4 4',
                layout: 'accordion',
                defaults: {
                    margins: '0 0 0 5'
                },
                items: [{
                    id: 'chart',
                    title: '折线图',
                    iconCls: 'store-info',
                    autoScroll: true,
                    items: [] //详细信息视图
                }, {
					id:'pieChart',
                    title: '饼图',
                    iconCls: 'store-info',
                    items: [] //相关操作视图
                }, {
					id:'barChart',
                    title: '柱状图',
                    iconCls: 'store-info',
                    items: [] //统计信息视图
                }]
            }, {
                //列表信息模块
                xtype: 'panel',
                region: 'center',
                margins: '4 0 4 4',
                layout: 'border',
                items: [{
                    region: 'center',
                    layout: 'border',
                    border: false,
                    items: new Ext.Panel({
                        region: 'center',
                        border: false,
                        defaults: {
                            paddings: '10 10 10 10'
                        },
                        items: [fp, {
                            width: '100%',
                            height: 100,
                            title: '统计条件',
                            autoScroll: true,
                            collapsible: true,
                            layout: 'hbox',
                            layoutConfig: {
                                align: 'stretch'
                            },
                            defaults: {
                                margins: '0 8 0 0'
                            },
                            items: [condGrid],
                            tbar: [{
                                tooltip: '增加',
                                icon: contextPath+'/imgs/add.png',
                                handler: function(){
                                
                                }
                            }, "-", {
                                tooltip: '删除',
                                id: 'delBtn',
                                icon: contextPath+'/imgs/delete.png'
                            }]
                        }, {
                            xtype: 'fieldset',
                            id: 'view_area',
                            width: '100%',
                            height: 400,
                            title: '统计报表',
                            collapsible: true,
                            layout: 'hbox',
                            //html: '<iframe id="stat_view" src="#" style="overflow:visible" width="100%" height="100%" frameborder="0">',
                            layoutConfig: {
                                align: 'stretch'
                            },
                            defaults: {
                                margins: '0 8 0 0'
                            },
							items: [{
                xtype: 'tabpanel',
                id: 'chartTabPanel',
                enableTabScroll: true,
                activeTab: 0,
                plain: true,
                items: [{
                	layout: 'fit',
                    title: '对象统计',
                    autoScroll: true,
                    baseCls: 'x-plain',
                    id: 'objectView',
                    items: [
                    	//Fusion.getJSONChart(0, statView)
					]
                },
                {
                    title: '分类统计',
                    id: 'categoryView',
                    autoScroll: true,
                    layout: 'fit',
                    items: [
                    	//Fusion.getJSONChart(0,statView,true)
                    ]
                },
                {
                    title: '综合统计',
                    autoScroll: true,
                    layout: 'fit',
                    id: 'columnView',
                    items: [
                    	//Fusion.getJSONChart(1,statView)
                    ]
                },
                {
                    title: '统计表格',
                    xtype: 'form',
                    layout: 'fit',
                    bodyStyle: 'padding:10px',
                    html: '<iframe id="tabFrame" width="100%" height="100%" frameborder="0" src="' + contextPath + '/common/statTable.html"/>'
                }]
            }]
                        }]
                    })
                }]
            }]
        });
        //initCharts(Ext.getCmp('chart'), 250, 180);
		//initPieChart(Ext.getCmp('pieChart'),250,180);
		//initBarChart(Ext.getCmp('barChart'),250,250);
    });
    function getTableName(){
        return '<c:out value="${tableName}"/>';
    }
</script>
</head>
<body>
</body>
</html>
