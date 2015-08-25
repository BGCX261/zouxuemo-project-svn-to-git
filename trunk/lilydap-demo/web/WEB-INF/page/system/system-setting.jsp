<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="utf-8" %>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<script type="text/javascript" src="<c:url value='/scripts/ext-3.3.1/adapter/ext/ext-base.js'/>" /></script>
<script type="text/javascript" src="<c:url value='/scripts/ext-3.3.1/ext-all.js'/>" /></script>
<script type="text/javascript" src="<c:url value='/scripts/ext-3.3.1/locale/ext-lang-zh_CN.js'/>" /></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/scripts/ext-3.3.1/resources/css/ext-all.css'/>" />
<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = "<c:url value='/scripts/ext-3.3.1/resources/images/default/s.gif'/>";
</script>
<script type="text/javascript">
Ext.onReady(function(){
//--------------------------- SystemSetting Grid Layout -----------------------------------	
	var systemSettingStore = new Ext.data.JsonStore({	
		id: 'systemSettingStore',
		url: 'system-setting!get.action?model=demo',
		root: 'data',
		totalProperty: 'totalCount',
		successProperty: 'success',
		fields: ['des', {name: 'id', type: 'int'}, 'model', 'name', 'value']
	});
	    
	var systemSettingColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), 
		{id: 'name', dataIndex: 'name', header: '系统属性名称', width: 100}, 
		{id: 'value', dataIndex: 'value', header: '系统属性值', width: 100, editor:new Ext.form.TextField({})}, 
		{id: 'des', dataIndex: 'des', header: '描述', width: 150}
	]);

	var systemSettingPageingToolbar = new Ext.PagingToolbar({
		pageSize: 20, 
			store: systemSettingStore, 
			displayInfo: true, 
			displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2}  条', 
			emptyMsg: '未找到记录数据'
	});

	var systemSettingGrid = new Ext.grid.EditorGridPanel({
		id: 'systemSettingGrid',
		store: systemSettingStore,
		title: '&#x7CFB;&#x7EDF;&#x53C2;&#x6570;&#x8BBE;&#x7F6E;', 
		autoExpandColumn: 'name',
		cm: systemSettingColumnModel,
		tbar: [{
			text: '保存', 
			handler: save,
			id:'saveBtn'
		}],
		bbar: systemSettingPageingToolbar,
		autoExpandMax:220, 
		clicksToEdit : 1
	});	

	systemSettingPageingToolbar.doLoad(Math.floor(systemSettingPageingToolbar.cursor/systemSettingPageingToolbar.pageSize) * systemSettingPageingToolbar.pageSize);

	systemSettingGrid.on('afteredit', function(){
		Ext.getCmp('saveBtn').setDisabled(false);
	});
	
//--------------------------- Viewport Layout -----------------------------------		
	 var viewport = new Ext.Viewport({
		layout:'fit',
		border:false,
		items:[systemSettingGrid]
	 });
	 
	function save(){
		var records = systemSettingStore.getModifiedRecords();
		var jsonStr = '[';
		for(var i=0; i<records.length; i++){
			jsonStr += Ext.encode(records[i].data);
			if(i != records.length-1 ) jsonStr += ',';
		}
		jsonStr += ']';
		
		Ext.Ajax.request({
		   url: 'system-setting!save.action',
		   params: { modifiedRecords : jsonStr, model : 'demo' },
		   success:function(response) {
               var result = Ext.decode(response.responseText);
               if( result.success == true )
                    Ext.Msg.alert('提示信息',result.msg);
               else
                    Ext.Msg.alert('提示信息',result.error);
           }
		});
	}
});
</script>
