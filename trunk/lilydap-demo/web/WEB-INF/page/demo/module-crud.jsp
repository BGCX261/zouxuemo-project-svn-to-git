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
  var button = new Ext.Button({
		text:'查询',
		//iconCls:'',
		handler:query
	});
  	var moduleQueryConditionForm = new Ext.FormPanel({
		labelAlign: 'right',
		labelWidth: 70,
		autoHeight: true,
		title: '数据的增删改查演示',
		frame: true,
		method: 'POST',
		bodyStyle: 'padding:15px 0',
        items:[{
			layout: 'column',
			items: [{
				width: 200,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '模块编码',
					id: 'code',
					name: 'condition.code.like',
					anchor: '95%'
				}]
			},{
				width:200,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '模块名称',
					id: 'condition.name.like',
					name: 'condition.name.like',
					anchor: '95%'
				}]
			},{
				width:200,
				layout: 'form',
				items: [button]
			}]
		}]
	});
	
	//moduleQueryConditionForm.addButton(button);
	
	var checkboxSelectionModel = new Ext.grid.CheckboxSelectionModel();
	
	var moduleColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer({width:20}),
		checkboxSelectionModel,
		{header:'id', id:'id', dataIndex:'id', hidden:true},
		{header:'模块编码', id:'code', dataIndex:'code', width:120, sortable:true, align:'center'},
		{header:'模块名称', id:'name', dataIndex:'name', width:120, sortable:true, align:'center'},
		{header:'模块说明', id:'des', dataIndex:'des', width:100},
		{header:'创建日期', id:'createDate', dataIndex:'createDate', renderer:parseDate('Y-m-d'), width:113, align:'center'},
		{header:'操作', id:'operate', dataIndex:'id', width:100, align:'center',renderer:onRender}
	]);
	
	var moduleStore = new Ext.data.JsonStore({
		url: 'module-crud!list.action',
		root: 'data',
		totalProperty: 'count',
		successProperty: 'success',
		remoteSort: true,
		fields: ['id','code', 'name', 'des', {name: 'createDate',type: 'date', convert: readJsonDate}]
	});
	
	var pageingToolbar = new Ext.PagingToolbar({
		pageSize: 18,
		store: moduleStore,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 记录，共 {2} 条',
		emptyMsg: '未找到数据记录'
	});
	
	var moduleGrid = new Ext.grid.GridPanel({
		store: moduleStore,
		sm: checkboxSelectionModel,
		cm: moduleColumnModel,
		bbar: pageingToolbar,
		tbar:[{
			text: '添加',
			handler: addModule,
			iconCls: 'common-button-add',
			disabled: false
		},{
			text: '删除',
			handler: removeModule,
			iconCls: 'common-button-delete',
			disabled: false
		}],
		listeners: {
			'rowdblclick': function() {alert(1);}
		}
	});
	
	pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
		<%--Form--%>
	var editForm = new Ext.form.FormPanel({
		id:'editForm',
		labelAlign:'right',
		labelWidth:70,
		frame:true,
		reader: new Ext.data.JsonReader(    
			{root: 'data'},       
			[  {name:'id'},		
				'code', 'name', 'des',{name: 'defaultModule', type: 'boolean'},
				{name: 'createDate', type: 'string', convert: readJsonDate}
			]       
		), 
		items:[{
			layout: 'column',
			items: [{
				layout: 'form',
				items: [{
					xtype:'hidden',
					name: 'id',
					hidden:true
				},{
					xtype:'textfield',
					fieldLabel:'模块编码',
					name:'code',
					anchor:'100%'
				},{
					xtype:'textfield',
					fieldLabel:'模块名称',
					name:'name',
					anchor:'100%'
				},{
					xtype:'textarea',
					fieldLabel: '模块说明',
					height:80,
					name: 'des',
					anchor:'99%'
				},{
					xtype:'datefield',
					fieldLabel: '创建日期',
					name: 'createDate',
					format:'Y-m-d',
					readOnly:true,
					anchor:'40%'
				}]
			}]
		}]
	});
		<%--编辑窗口--%>
	var editWin = new Ext.Window({
		id:'editWin',
		title:'模块窗口',
		width:500,
		height:340,
		iconCls: 'common-button-window',
		closeAction:'hide',
		plain:true,
		resizable:false,
		buttonAlign:'center',
		modal:true,
		items:[editForm],
		buttons:[{
			id:'edit_savePlan',
			text:'保存',
			iconCls: 'common-button-save',
			disable:false,
			handler:Form_onSave
			
		},{
			text:'取消',
			iconCls: 'common-button-cancel',
			handler:function(){
				editWin.hide();
				editForm.getForm().reset();
			}
		}]
	});
 
	function parseDate(format){
	        return function(v){
	        		if(v!=null && v.toString().trim()!="")
	            		return Ext.util.Format.date(v, format);
	        };
	}
	
	function readJsonDate(v){
		if(v!=null && v.toString().trim()!="")
			return new Date(v);
		return "";
	} 
	
	function parseDateFunction(format){
	    return function(v){
	   		if(v != null && v.toString().trim() != '') {
	   			var date = new Date(v['time']);
	   			
	       		return Ext.util.Format.date(date, format);
	       	} else
	       		return '';
	    };
	}

	
	Ext.onReady(function(){
		var viewport = new Ext.Viewport({
			layout:'border',
			items:[{
				layout:'fit',
				region:'north',
				height:80,
				border:false,
				margins:'0 0 0 0',
				items:[moduleQueryConditionForm]
			},{
				layout:'fit',
				region:'center',
				border:false,
				margins:'0 0 0 0',
				items:[moduleGrid]
			}]
		});
	});
	function query() {
		var params = moduleQueryConditionForm.getForm().getValues()
		
		moduleStore.baseParams = moduleStore.baseParams || {};
		Ext.apply(moduleStore.baseParams, params);
		
		pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
	}	
	function onRender(v, p, record) {

		return "<a href='#' onclick='editModule()'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='removeModule()'>删除</a>";
	
	}
	function Form_onSave() {
		this.disabled = true;
		if(editForm.getForm().isValid()){
			editForm.form.submit({url:'module-crud!save.action',waitMsg:'系统正在保存数据，请等待...', success:Form_onSuccess, failure:Form_onFailure});
		}
		this.disabled = false;
	}
	function Form_onSuccess(form, action){
		editWin.hide();
		pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
	}
	function Form_onFailure(form, action) {
		var obj = action.result;
		Ext.MessageBox.alert('提示信息', obj.error);
	}
   	function editModule() {
		editWin.show();
		var rows = checkboxSelectionModel.getSelections();
		var id = moduleStore.getById(rows[0].id).get("id");		
		editForm.load({url: 'module-crud!get.action', method:'POST', params:{'id': id}, waitMsg: '系统正在加载数据，请稍候...'});
  	}
  	function addModule() {
		editWin.show();
		var myform = editForm.form;
		myform.reset();
		editForm.load({url: 'module-crud!get.action', method:'POST', params:{'id': 0}, waitMsg: '系统正在加载数据，请稍候...'});
  	}

	function removeModule(){
		if(checkboxSelectionModel.getSelections().length == 0){
			Ext.MessageBox.alert("提示信息","没有要删除的数据，请先选择删除数据，然后进行删除！");
			return false;
		}
		Ext.MessageBox.confirm('提示信息',' 您确认要删除吗？&nbsp;&nbsp;&nbsp;&nbsp;', 
			function(btn) {
				 if(btn == 'yes') {
					 var rows=checkboxSelectionModel.getSelections();
					 for (i = 0; i < rows.length; i++) {
						var rowData=moduleGrid.store.getById(rows[i].id);
						var id = rowData.get("id");
						
						Ext.Ajax.request({
							url: 'module-crud!remove.action?id='+id, 
							method:'POST',
							success: function(response) {
								var result = Ext.decode(response.responseText);
								if (result.success) {
									pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
								} else
									Ext.MessageBox.alert('提示信息', result.error);
							},
							failure: function(response) {
								
							}
						}); 
					 }
				}
			}
		);
	}
  </script>
