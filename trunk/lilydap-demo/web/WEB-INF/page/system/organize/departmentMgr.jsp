<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="utf-8"%>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/ext-files.jsp"%>
</head>
<script type="text/javascript">
//--------------------------- Global Variables Definition -----------------------------------		
	var parentDepartmentLevel;
	var toolbarPanel;
	
	<lilydap:extJsonStore name="departmentStore" id="departmentStore" url="department!list.action" model="organize.Department">
		<lilydap:extJsonReaderConvert fields="createDate" func="readJsonDate"/>
	</lilydap:extJsonStore>
	
Ext.onReady(function(){
<lilydap:dictionaryExtSimpleStore name="departmentTypeStore" catalog="departmentType"/>

//--------------------------- Department Grid Layout -----------------------------------	

<lilydap:extGridPanel name="departmentGrid" id="departmentGrid" store="departmentStore" model="organize.Department" propertys="el:'departmentGridDiv', enableDragDrop: true, ddGroup: 'departmentDD'">
	<lilydap:extSelectionModel name="departmentSelectionModel"/>
	<lilydap:extColumnModel name="departmentColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn name="departmentSelectionModel"/>
		<lilydap:extColumn field="name" propertys="width:350 "/>
		<lilydap:extColumn field="createDate" propertys="renderer:parseDate('Y-m-d'),align:'center'"/>
		<lilydap:extColumn field="id" propertys="renderer:departmentGridEnterColumn_onRender,header:'操作',align:'center'" />
	</lilydap:extColumnModel>
</lilydap:extGridPanel>

departmentGrid.on('rowdblclick', departmentGrid_onRowdblclick);
//--------------------------- Department Grid Row DragDrop Support, Implement adjust dictionary order -----------------------------------		
	var ddropTarget = new Ext.dd.DropTarget(departmentGrid.getEl(), {
		ddGroup: "departmentDD", 
		copy:false,
		notifyDrop : ddropTarget_onNotifyDrop
	}); 
		
//--------------------------- Person Form Layout -----------------------------------		
<lilydap:extFormPanel name="departmentForm" model="organize.Department" propertys="defaults: {width: 370}, bodyStyle:'padding:5px 0 0',labelWidth:65">
	<lilydap:extJsonReader root="data"/>
	
	<lilydap:extFormFields>
		<lilydap:extFormHidden field="id"/>
		<lilydap:extFormHidden field="parentLevel"/>
		<lilydap:extFormTextField field="name" propertys="allowBlank:false"/>
		<lilydap:extFormTextArea field="des" height="80"/>
	</lilydap:extFormFields> 
</lilydap:extFormPanel>	

	var departmentWin = new Ext.Window({
		id:'department-win',
		layout:'fit',
		width:440,
		height:200,
		closeAction:'hide',
		plain: true,
		resizable:false,
		modal:true,
		buttonAlign:'center',
		title:'部门编辑',
		iconCls:'common-button-window',
		items:departmentForm,
		buttons: [{
			text:'保存',
			disabled:false,
			iconCls: 'common-button-save',
			handler:departmentForm_onSave
		},{
			text: '取消',
			iconCls: 'common-button-cancel',
			handler: function(){
				departmentForm.form.reset();
				departmentWin.hide();
			}
		}/*,{
			text: 'modify',
			handler: function(){
				var field = departmentForm.form.findField('type');
				
				field.emptyText = 'abcdefg';
				field.applyEmptyText();
			}
		}*/]
	});
//--------------------------- Viewport Layout -----------------------------------	
   toolbarPanel = new Ext.Panel({
					border:false,
				    bodyStyle: 'background-color:#cad9ec;',
					tbar: [
						{text: '新建部门',iconCls:"common-button-add",handler: departmentPanel_onAdd}, 
						{text: '删除部门',iconCls:"common-button-delete",handler: departmentPanel_onRemove}
					]
				});
				
   var viewport = new Ext.Viewport({
		layout:'fit',
		items:[{
			layout:'border',
			title:'部门维护',
			items:[{
					layout:'fit',
					region:'north',
					height:48,
					border:false,
					margins:'0 0 0 0',
					items:[toolbarPanel]
				},{
					layout:'fit',
					region:'center',
					border:false,
					margins:'0 0 0 0',
					items:[departmentGrid]
				}]
		}]
	});
	
	
	viewport.loadDepartmentData = loadDepartmentData;
	viewport.loadDepartmentData(0); 

	departmentForm.form.on("actioncomplete",function(){
		var myform = departmentForm.form;
		var id = myform.findField("id").getValue();
		if(id > 0){
			setReadOnly(myform,"name");
		}
	});

//--------------------------- Event Handle Function -----------------------------------		
	function  departmentGridEnterColumn_onRender(v, p, record){
		return '<a href="#" onclick="loadDepartmentData(' + v + ')">进入下一级</a>';
	}
	
	function departmentGrid_onRowdblclick() {			
			departmentWin.show();
			var myform = departmentForm.form;

			var rows = departmentSelectionModel.getSelections();
			var id = departmentStore.getById(rows[0].id).get("id");
			departmentForm.load({url: 'department!get.action', method:'POST', params:{'id': id}, waitMsg: '系统正在加载数据，请稍候...'});
	}
	
	function departmentPanel_onAdd() {
			departmentWin.show();
			var myform = departmentForm.form;
			disReadOnly(myform,"name");
			myform.reset();
			myform.findField("parentLevel").setValue(parentDepartmentLevel);
			var temp = myform.findField("parentLevel").getValue();
	}
	
	function departmentPanel_onRemove() {
			if (departmentSelectionModel.getSelections().length == 0) {
				Ext.MessageBox.alert('警告信息', '没有要删除的数据，请先选择删除数据，然后进行删除！');
				return;
			}
			
			Ext.MessageBox.confirm('提示信息', '您确认要删除吗？&nbsp;&nbsp;&nbsp;&nbsp;', function(btn) {if(btn == 'yes') {
				 var rows=departmentSelectionModel.getSelections();
				 for (i = 0; i < rows.length; i++) {
					var rowData=departmentGrid.store.getById(rows[i].id);
					var id = rowData.get("id");
					
					Ext.Ajax.request({method:'POST',
						url: 'department!remove.action?id='+id, 
						success: function(response) {
							var result = Ext.decode(response.responseText);
							if (result.success) {
								var index = departmentStore.find("id", result.id);
								departmentStore.remove(departmentStore.getAt(index));
							} else
								Ext.MessageBox.alert('提示信息', result.error);
						}
					});  
				 }
			}});
	}
	
	function departmentForm_onSave() {
		this.disabled = true;
		if (departmentForm.getForm().isValid()) {
			departmentForm.form.submit({url:'department!save.action', waitMsg:'系统正在保存数据，请等待...', success:departmentForm_onSuccess, failure:departmentForm_onFailure});
		}
		this.disabled = false;
	}

	function departmentForm_onFailure(form, action) {
		var obj = action.result;
		Ext.MessageBox.alert('提示信息', obj.error);
	}

	function departmentForm_onSuccess(form, action){
		departmentWin.hide();
		departmentForm.form.reset();
		departmentStore.load({params:{start:0}});
	}
		
	function ddropTarget_onNotifyDrop(dd, e, data) {
		var target = Ext.lib.Event.getTarget(e);
		var rindex = departmentGrid.getView().findRowIndex(target); // index of row where item is dropped
		var lindex = data.rowIndex;

		if (rindex === false) return false;
		if (rindex == lindex) return false;
		
		var order = rindex - lindex;
		
		var rows = departmentGrid.getSelectionModel().getSelections();
		var nbRows = rows.length;
		var cindex = dd.getDragData(e).rowIndex;

		for(i = 0; i < nbRows; i++) 
		{
			var rowData = departmentStore.getById(rows[i].id); 
			var id = rowData.get("id");

			Ext.Ajax.request({
				url: 'department!adjustOrder.action?id='+id+"&order="+order, 
				method:'POST',
				success: function(response) {
					departmentStore.load({params:{start:0}}); 
				}
			}); 
		}
	}
});

//--------------------------- Private Function -----------------------------------		
	function loadDepartmentData(parentId) {
		Ext.Ajax.request({method:'POST',
			url: 'department!hierarchical.action?id='+parentId, 
			success: function(response) {
				var result = Ext.decode(response.responseText);
				if (result.success) {
					var tpl = new Ext.XTemplate(
						'<div class="x-btn x-btn-text" style="padding:5px 5px; height:100% ">',
						'<tpl for="hierarchical">', 
							'<tpl if="parentLevel != \'\'">&nbsp;->&nbsp;</tpl>', 
							'[<a href="#" onclick="loadDepartmentData({id});" title="{des}">{name}</a>]&nbsp;', 
						'</tpl>', 
						'</div>'
					);
					
					tpl.overwrite(toolbarPanel.body, result);
					
					parentDepartmentLevel = result.hierarchical[result.hierarchical.length-1].level;
				} else
					Ext.MessageBox.alert('提示信息', result.error);
			}
		}); 
		
		departmentStore.baseParams = departmentStore.baseParams || {};
		departmentStore.baseParams["parentId"] = parentId;
		departmentStore.load({params:{start:0}}); 
	}
</script>
<body>
	<div id="departmentGridDiv"></div>
</body>
</html>