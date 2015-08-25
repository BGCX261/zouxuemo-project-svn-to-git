<%@ include file="/common/taglibs.jsp"%><head>
<%@ page language="java" pageEncoding="utf-8"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<%@ include file="/common/ext-files.jsp"%>
<script type="text/javascript">
//--------------------------- Global Variables Definition -----------------------------------		
	var parentUid;
	var toolbarPanel;
	
	<lilydap:extJsonStore name="menuStore" id="menuStore" model="com.lily.dap.entity.common.Menu" url="common/menu!list.action" totalProperty="totalCount" root="data"/>

	var gridformActionModel = new Ext.ActionModel.Grid_Form({
		getUrl: 'menu!get.action',
		removeUrl: 'menu!remove.action',
		saveUrl: 'menu!save.action'
	});

//--------------------------- Menu Grid Layout -----------------------------------	
<lilydap:extGridPanel name="menuGrid" id="menuGrid" store="menuStore" model="com.lily.dap.entity.common.Menu" autoExpandColumn="text" propertys = "el: 'menuGridDiv',enableDragDrop : true,ddGroup : 'menuDD'">
	<lilydap:extSelectionModel name="menuSelectionModel"/>
	<lilydap:extColumnModel name="menuColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn name="menuSelectionModel"/>
		<lilydap:extColumn field="text" width="300" propertys="renderer:menuGridEnterColumn_onRender" header="<font color=black>菜单文本</font>"/>
		<lilydap:extColumn field="description" width="200"/>
	<%--	<lilydap:extColumn field="icon" width="200"/> --%>
		<lilydap:extColumn field="link" width="230"/>
	</lilydap:extColumnModel> 
</lilydap:extGridPanel>

var button = new Ext.Button({
	text:'&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;',
	iconCls:'common-button-save',
	handler:gridformActionModel.handleSaveFormData.createDelegate(gridformActionModel)
});
//--------------------------- Menu Form Layout -----------------------------------			
<lilydap:extFormPanel name="menuForm" model="com.lily.dap.entity.common.Menu" propertys="defaults: {width: 260}, bodyStyle:'padding:14px 0 0'">
	<lilydap:extJsonReader root="data">

	</lilydap:extJsonReader>
	<lilydap:extFormFields>
		<lilydap:extFormHidden field="id"/>
		<lilydap:extFormHidden field="uid"/>
		<lilydap:extFormHidden field="loadCode"/>
		<lilydap:extFormHidden field="parentUid"/>
		<lilydap:extFormTextField field="text"/>
		<lilydap:extFormTextField field="description"/>
		<lilydap:extFormTextField field="icon"/>
		<lilydap:extFormTextArea field="link" height="50" anchor="98%"/>
		<lilydap:extFormTextField field="accessRight"/>
	</lilydap:extFormFields> 
</lilydap:extFormPanel>	
menuForm.addButton(button);

Ext.onReady(function(){
//--------------------------- menuGrid Row DragDrop Support, Implement adjust field order -----------------------------------		
	var ddropTarget = new Ext.dd.DropTarget(menuGrid.getEl(), {
		ddGroup: "menuDD", 
		copy:false,
		notifyDrop : ddropTarget_onNotifyDrop
	}); 

//--------------------------- Viewport Layout -----------------------------------		
   toolbarPanel = new Ext.Panel({
		border:false,
		title:'菜单维护',
		bodyStyle: 'background-color:#cad9ec;',
		tbar: [
			{ text: '添加', handler: gridformActionModel.handleNewFormData.createDelegate(gridformActionModel), iconCls:"menu-button-add"}, 
			{ text: '删除', handler: gridformActionModel.handleRemoveGridSelectedData.createDelegate(gridformActionModel),  iconCls:"menu-button-delete"}
		]
	});
	
   var viewport = new Ext.Viewport({
   layout:'fit',
   items:[{
   layout:'border',
		items:[{
				layout:'fit',
				region:'north',
				height:74,
				border:false,
				margins:'0 0 0 0',
				items:[toolbarPanel]
			},{
				layout:'fit',
				region:'center',
				border:false,
				margins:'0 0 0 0',
				items:[menuGrid]
			},{
				layout:'fit',
				region:'south',
				height:220,
				border:false,
				margins:'0 0 0 0',
				items:[menuForm]
			}]
   }]
		
	});
	
	viewport.loadMenuData = loadMenuData;
	viewport.loadMenuData(""); 
});	
//--------------------------- Event Handle Function -----------------------------------		
	gridformActionModel.initialize(menuGrid, menuForm);
	gridformActionModel.handleLoadFormDataFromGrid();
	gridformActionModel.on('aftersave', function(form, action) {
		menuStore.load({params:{start:0}});
		//return
	}, this);
	gridformActionModel.on('afterload', function(form) {
		var field = form.getForm().findField("text");
		field.focus.defer(600, field);
	}, this);
	gridformActionModel.on('beforesave', function(form, params) {
		if (form.getForm().findField("id").getValue() == 0){
			menuForm.getForm().findField("parentUid").setValue(parentUid);
		}
	}, this);


	function  menuGridEnterColumn_onRender(v, p, record){
		var uid = record.get("uid");
		return '<a href="#" onclick=loadMenuData("' +  uid + '")>' + v + '</a>';
	}
	function ddropTarget_onNotifyDrop(dd, e, data) {
			var target = Ext.lib.Event.getTarget(e);
			var rindex = menuGrid.getView().findRowIndex(target); // index of row where item is dropped
			var lindex = data.rowIndex;

			if (rindex === false) return false;
			if (rindex == lindex) return false;
			
			var order = rindex - lindex;
			
			var rows = menuGrid.getSelectionModel().getSelections();
			var nbRows = rows.length;
			var cindex = dd.getDragData(e).rowIndex;

			for(i = 0; i < nbRows; i++) 
			{
				var rowData = menuStore.getById(rows[i].id); 
				var id = rowData.get("id");

				Ext.Ajax.request({
					url: 'menu!changeMenuSort.action?id='+id+"&order="+order, 
					method:'POST',
					success: function(response) {
						menuStore.load({params:{start:0}}); 
					}
				}); 
			}
	}

//--------------------------- Private Function -----------------------------------		
	function loadMenuData(_parentUid) {		
		Ext.Ajax.request({method:'POST',
			url: 'menu!hierarchical.action?uid='+_parentUid, 
			success: function(response) {
				var result = Ext.decode(response.responseText);
				if (result.success) {
					var tpl = new Ext.XTemplate(
						'<div class="x-btn x-btn-text" style="padding:5px 5px; height:100% ">',
						'<tpl for="hierarchical">', 
							'<tpl if="uid != \'\'">&nbsp;->&nbsp;</tpl>', 
							'[<a href="#" onclick="loadMenuData(\'{uid}\');" title="{description}">{text}</a>]&nbsp;', 
						'</tpl>', 
						'</div>'
					);
					
					tpl.overwrite(toolbarPanel.body, result);
					
					if (result.hierarchical.length == 0){
						parentUid = "";
					}else{
						parentUid = result.hierarchical[result.hierarchical.length-1].uid;
					}
				} else
					Ext.MessageBox.alert('<fmt:message key="message.title.tip"/>', result.error);
			}
		}); 
		menuStore.baseParams = menuStore.baseParams || {};
		menuStore.baseParams["parentUid"] = _parentUid;
		menuStore.load({params:{start:0}}); 
	}

</script>
<body>
	<div id="menuGridDiv"></div>
</body>