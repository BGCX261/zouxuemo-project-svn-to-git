<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="utf-8"%>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/ext-files.jsp"%>
</head>
<script type="text/javascript">
//--------------------------- Global Variables Definition -----------------------------------		
	
Ext.onReady(function(){
//--------------------------- Role Grid Layout -----------------------------------	
<lilydap:extJsonStore name="roleStore" id="roleStore" url="role!list.action" model="right.Role"/>

<lilydap:extGridPanel name="roleGrid" id="roleGrid" store="roleStore" model="right.Role" autoExpandColumn="des">
	<lilydap:extTopBarButton text="新建角色" handler="rolePanel_onAdd" iconCls="role-button-add"/>
	<lilydap:extTopBarButton text="删除角色" handler="rolePanel_onRemove" iconCls="role-button-delete"/>
	<lilydap:extSelectionModel name="roleSelectionModel"/>
	<lilydap:extColumnModel name="roleColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn name="roleSelectionModel"/>
		<lilydap:extColumn field="code"/>
		<lilydap:extColumn field="name"/>
		<lilydap:extColumn field="des"/>
	</lilydap:extColumnModel>
</lilydap:extGridPanel>

var button = new Ext.Button({
	text:'&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;',
	handler:roleForm_onSave,
	iconCls:"common-button-save"
});
roleGrid.on('rowclick', roleGrid_onRowclick);
//--------------------------- Person Form Layout -----------------------------------				
		var roleForm = new Ext.FormPanel({
			title:'角色编辑',
			labelAlign: 'right',
			labelWidth: 65,
			frame:true,
			defaults: {width: 370},
			url:'role!save.action',
			bodyStyle:'padding:14px 0 0',
			method:'POST',
			reader: new Ext.data.JsonReader(    
				{root: 'data'},       
				['authority', 'code', 'des', {name: 'flag', type: 'int'}, {name: 'id', type: 'int'}, 'name']   
			), 
            items:[{
					xtype:'hidden',
					id: 'id',
					name: 'id',
					hidden:true
				},{
					xtype:'textfield',
					fieldLabel: '角色编码',
					id: 'code',
					name: 'code',
					anchor:'95%',	
					tabIndex:1
				}, {
					xtype:'textfield',
					fieldLabel: '角色名称',
					id: 'name',
					name: 'name',
					anchor:'95%',	
					tabIndex:2
				}, {
					xtype:'textarea',
					fieldLabel: '角色说明',
					id: 'des',
					name: 'des',
					height:60,
					anchor:'95%',	
					tabIndex:3
				}]
		});	
	roleForm.addButton(button);
//--------------------------- Have Role Grid Layout -----------------------------------	
<lilydap:extJsonStore name="haveRoleStore" id="haveRoleStore" url="role!listHaveRoles.action" model="right.Role"/>

<lilydap:extGridPanel name="haveRoleGrid" id="haveRoleGrid" store="haveRoleStore" model="right.Role" autoExpandColumn="name">
	<lilydap:extSelectionModel name="haveRoleSelectionModel"/>
	<lilydap:extColumnModel name="haveRoleColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn name="haveRoleSelectionModel"/>
		<lilydap:extColumn field="code"/>
		<lilydap:extColumn field="name"/>
	</lilydap:extColumnModel>
</lilydap:extGridPanel>
//--------------------------- Have Permission Grid Layout -----------------------------------	
<lilydap:extJsonStore name="permissionStore" id="permissionStore" url="role!listHavePermissions.action" model="right.Permission"/>

<lilydap:extGridPanel name="permissionGrid" id="permissionGrid" store="permissionStore" model="right.Permission" autoExpandColumn="fullName">
	<lilydap:extSelectionModel name="permissionSelectionModel"/>
	<lilydap:extColumnModel name="permissionColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn name="permissionSelectionModel"/>
		<lilydap:extColumn field="fullName"/>
	</lilydap:extColumnModel>
</lilydap:extGridPanel>
//--------------------------- Role And Permission Select Form Layout -----------------------------------	
<%@ include file="roleSelect.jsp"%>
//--------------------------- Viewport Layout -----------------------------------		
   var viewport = new Ext.Viewport({
	   layout:'fit',
	   items:[{
	   		layout:"border",
	   		title:'角色维护',
			items:[{
				region:"center",
				layout:"fit",
				border:false,
				margins:'0 0 0 0',
				items:[roleGrid]
			  },{
				layout:"border",
				region:"east",
				width:400,
				items:[{
					id:"rightHoldPanel",
					region:"center",
					layout:"fit",
					border:false,
					items:[{
						xtype:"tabpanel",
						border:false,
						activeTab:0,
						items:[{
							id:'haveRolePanel',
							xtype:'panel',
							layout:'fit',
							border:false,
							margins:'0 0 0 0',
							title:'拥有角色',
							items:[haveRoleGrid],
							tbar: [
								{text: '追加角色',handler: roleForm_onAddHaveRole, disabled:true, iconCls:"role-button-add"}, 
								{text: '去除角色',handler: roleForm_onRemoveHaveRole, disabled:true, iconCls:"role-button-delete"}
							]
						  }/*,{
							id:'havePermissionPanel',
							xtype:"panel",
							layout:"fit",
							border:false,
							margins:'0 0 0 0',
							title:'拥有许可',
							items:[permissionGrid],
							tbar: [
								{text: '追加许可',handler: roleForm_onAddHavePermission, disabled:true}, 
								{text: '去除许可',handler: roleForm_onRemoveHavePermission, disabled:true}
							]
						 }*/],
						listeners : {
							'tabchange' : rightHoldPanel_onTabchange
						}
					 }]
				  },{
					layout:'fit',
					region:"north",
					border:false,
					height:200,
					margins:'0 0 0 0',
					items:[roleForm]
				  }]
			 }]
	   }]
		
	});

	roleStore.load({params:{start:0}}); 
//--------------------------- Event Handle Function -----------------------------------		
	function roleGrid_onRowclick() {
			var rows = roleSelectionModel.getSelections();
			var id = roleStore.getById(rows[0].id).get("id");
			loadRoleDetail(id);
	}
	
	function rolePanel_onAdd() {
		var myform = roleForm.form;
		myform.findField("code").enable();
		myform.reset();

		haveRoleStore.removeAll(); 
		permissionStore.removeAll();
		
		if (Ext.getCmp("haveRolePanel").getTopToolbar().setDisabled)
			Ext.getCmp("haveRolePanel").getTopToolbar().setDisabled(true);
			
		/*if (Ext.getCmp("havePermissionPanel").getTopToolbar().setDisabled)
			Ext.getCmp("havePermissionPanel").getTopToolbar().setDisabled(true);*/
	}
	
	function rolePanel_onRemove() {
		if (roleSelectionModel.getSelections().length == 0) {
			Ext.MessageBox.alert('警告信息', '没有要删除的数据，请先选择删除数据，然后进行删除！');
			return;
		}
		
		Ext.MessageBox.confirm('提示信息', '您确认要删除吗？&nbsp;&nbsp;&nbsp;&nbsp;', function(btn) {if(btn == 'yes') {
			 var rows=roleSelectionModel.getSelections();
			 for (i = 0; i < rows.length; i++) {
				var rowData=roleGrid.store.getById(rows[i].id);
				var id = rowData.get("id");
				
				Ext.Ajax.request({method:'POST',
					url: 'role!remove.action?id='+id, 
					success: function(response) {
						var result = Ext.decode(response.responseText);
						if (result.success) {
							var index = roleStore.find("id", result.id);
							roleStore.remove(roleStore.getAt(index));
							
							var myform = roleForm.form;
							if (myform.findField("id").getValue() == result.id)
								rolePanel_onAdd();
						} else
							Ext.MessageBox.alert('提示信息', result.error);
					}
				}); 
			 }
		}});
	}
	
	function roleForm_onSave() {
		this.disabled = true;
		roleForm.form.submit({url:'role!save.action', waitMsg:'系统正在保存数据，请等待...', success:roleForm_onSuccess, failure:roleForm_onFailure});
		this.disabled = false;
	}
		
	function roleForm_onFailure(form, action) {
		var obj = action.result;
		Ext.MessageBox.alert('提示信息', obj.error);
	}

	function roleForm_onSuccess(form, action){
		roleStore.load({params:{start:0}});
		
		var id = action.result.data.id;
		loadRoleDetail(id);
	}
	
	function rightHoldPanel_onTabchange(tabPanel, tab) {
		var myform = roleForm.form;
		var roleId = myform.findField("id").getValue();
		
		if (roleId > 0) {
			tab.getTopToolbar().setDisabled(false);
		} else {
			tab.getTopToolbar().setDisabled(true);
		}
	}
	
	function roleForm_onAddHaveRole() {
		var myform = roleForm.form;
		var roleId = myform.findField("id").getValue();
		
		if (roleId == '') {
			Ext.MessageBox.alert('提示信息', '当前角色还未保存，请保存后再添加角色或许可');
			return;
		}
		
		selectRole(roleId, roleForm_onSelectHaveRole);
	}
	
	function roleForm_onSelectHaveRole(selectRoleId) {
		var myform = roleForm.form;
		var roleId = myform.findField("id").getValue();
		
		Ext.Ajax.request({method:'POST',
			url: 'role!addHaveRole.action?id='+roleId+'&haveId='+selectRoleId, 
			success: function(response) {
				var result = Ext.decode(response.responseText);
				if (result.success) {
					haveRoleStore.load({params:{start:0}}); 
				} else {
					Ext.MessageBox.alert('提示信息', result.error);
				}
			}
		}); 
	}
	
	function roleForm_onRemoveHaveRole() {
		var myform = roleForm.form;
		var roleId = myform.findField("id").getValue();
		
		 var rows=haveRoleSelectionModel.getSelections();
		 for (i = 0; i < rows.length; i++) {
			var rowData=haveRoleStore.getById(rows[i].id);
			var haveRoleId = rowData.get("id");
			
			Ext.Ajax.request({method:'POST',
				url: 'role!removeHaveRole.action?id='+roleId+'&haveId='+haveRoleId, 
				success: function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						var index = haveRoleStore.find("id", result.data);
						haveRoleStore.remove(haveRoleStore.getAt(index));
					} else
						Ext.MessageBox.alert('提示信息', result.error);
				}
			}); 
		 }
	}
	
	function roleForm_onAddHavePermission() {
		var myform = roleForm.form;
		var roleCode = myform.findField("code").getValue();
		
		if (roleCode == '') {
			Ext.MessageBox.alert('提示信息', '当前角色还未保存，请保存后再添加角色或许可');
			return;
		}
		
		selectPermission(roleForm_onSelectHavePermission);
	}
	
	function roleForm_onSelectHavePermission(objectCode, haveOperations) {
		var permission = {};
		permission.roleCode = roleForm.form.findField("code").getValue();
		permission.objectCode = objectCode;
		permission.haveOperations = haveOperations.join(',');
		
		Ext.Ajax.request({method:'POST',
			url: 'role!addHavePermission.action?'+Ext.urlEncode(permission), 
			success: function(response) {
				var result = Ext.decode(response.responseText);
				if (result.success) {
					permissionStore.load({params:{start:0}}); 
				} else
					Ext.MessageBox.alert('提示信息', result.error);
			}
		}); 
	}
	
	function roleForm_onRemoveHavePermission() {
		 var rows=permissionSelectionModel.getSelections();
		 for (i = 0; i < rows.length; i++) {
			var rowData=permissionStore.getById(rows[i].id);
			var havePermissionId = rowData.get("id");
			
			Ext.Ajax.request({method:'POST',
				url: 'role!removeHavePermission.action?havePermissionId='+havePermissionId, 
				success: function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						var index = permissionStore.find("id", result.data);
						permissionStore.remove(permissionStore.getAt(index));
					} else
						Ext.MessageBox.alert('提示信息', result.error);
				}
			}); 
		 }
	}
//--------------------------- Private Function -----------------------------------		
	function loadRoleDetail(id) {
		var myform = roleForm.form;
		myform.findField("code").disable();
		
		roleForm.load({url: 'role!get.action', method:'POST', params:{'id': id}, waitMsg: '系统正在加载数据，请稍候...'});
		
		haveRoleStore.baseParams = haveRoleStore.baseParams || {};
		haveRoleStore.baseParams["id"] = id;
		haveRoleStore.load({params:{start:0}}); 
		
		permissionStore.baseParams = permissionStore.baseParams || {};
		permissionStore.baseParams["id"] = id;
		permissionStore.load({params:{start:0}}); 
		
		if (Ext.getCmp("haveRolePanel").getTopToolbar().setDisabled)
			Ext.getCmp("haveRolePanel").getTopToolbar().setDisabled(false);
			
		/*if (Ext.getCmp("havePermissionPanel").getTopToolbar().setDisabled)
			Ext.getCmp("havePermissionPanel").getTopToolbar().setDisabled(false);*/
	}
});
</script>
<body>
	<div id="roleGridDiv"></div>
</body>
</html>