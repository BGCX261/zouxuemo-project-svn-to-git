<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="utf-8"%>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/ext-files.jsp"%>
	<style>
	.tree-department {background-image:url( <c:url value='/images/silk/icons/house.gif'/>) !important;}
	.tree-post {background-image:url( <c:url value='/images/silk/icons/group.gif'/>) !important;}
	.tree-person {background-image:url( <c:url value='/images/silk/icons/user.gif'/>) !important;}
	</style>
</head>
</head>
<script type="text/javascript">
//--------------------------- Global Variables Definition -----------------------------------		
var rightHoldType, rightHoldId = 0;
	
Ext.onReady(function(){
//--------------------------- Organize Tree Layout -----------------------------------		
   var organizePanel = new Ext.tree.TreePanel({
		id:'organize',
		autoScroll : true,
		animate : true,
		border : false,
		rootVisible : false,
		root : new Ext.tree.AsyncTreeNode( { 
			id : 'root',
			text : '',
			draggable : false,
			expanded : true
		}),
		loader: new Ext.tree.TreeLoader({
			dataUrl:'../organize/department!tree.action?mode=dep-post'
		}),
		listeners : {
			'click' : organizePanel_onNodeClick
		}
	});
//--------------------------- Person Grid Layout -----------------------------------		
<lilydap:extJsonStore name="personStore" id="personStore" url="../organize/person!query.action?activate=true" totalProperty="total" root="data" model="organize.Person"/>

<lilydap:extFormPanel name="personCondForm" model="organize.Person" labelWidth="40" propertys="bodyStyle:'padding:7px 0px 0'">
	<lilydap:extFormFields>
		<lilydap:extFormColumns>
			<lilydap:extFormColumn width=".5"><lilydap:extFormTextField label="编号" name="condition.username.like" anchor="100%"/></lilydap:extFormColumn>
			<lilydap:extFormColumn width=".5"><lilydap:extFormTextField label="姓名" name="condition.name.like" anchor="100%"/></lilydap:extFormColumn>
		</lilydap:extFormColumns>
	</lilydap:extFormFields>
</lilydap:extFormPanel>
installQueryOnFieldChanged(personCondForm, doPersonQuery);

<lilydap:extGridPanel name="personGrid" id="personGrid" store="personStore" model="organize.Person" autoExpandColumn="name">
	<lilydap:extSelectionModel name="personSelectionModel" clazz="Ext.grid.RowSelectionModel"/>
	<lilydap:extColumnModel name="personColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn field="username" propertys="width:70"/>
		<lilydap:extColumn field="name"/>
		<lilydap:extColumn field="sex" propertys="width:32"/>
	</lilydap:extColumnModel>
    <lilydap:extPageingToolbar name="personPageingToolbar" displayInfo="false"/>
</lilydap:extGridPanel>
personGrid.on('rowclick', personGrid_onRowlick);
//--------------------------- Have Role Grid Layout -----------------------------------	
<lilydap:extJsonStore name="haveRoleStore" id="haveRoleStore" url="right-hold!listHaveRoles.action" model="right.Role"/>

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
<lilydap:extJsonStore name="permissionStore" id="permissionStore" url="right-hold!listHavePermissions.action" model="right.Permission"/>

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
			layout:'border',
			title:'权限设置',
			items:[{
				layout:'fit',
				region:'west',
				width:250,
				border:false,
				margins:'0 0 0 0',
				items:[{
					xtype:"tabpanel",
					border:false,
					activeTab:0,
					items:[{
						id:'organizeTabPanel',
						xtype:"panel",
						layout:"fit",
						border:false,
						margins:'0 0 0 0',
						title:"机构赋权",
						items:[organizePanel]
					  },{
						id:'personTabPanel',
						xtype:"panel",
						layout:"fit",
						border:false,
						margins:'0 0 0 0',
						title:"人员赋权",
						items:[{
							layout:"border",
							border:false,
							items:[{
								region:"center",
								layout:'fit',
								border:false,
								items: personGrid
							  },{
								region:"north",
								layout:'fit',
								border:false,
								height:40,
								items: personCondForm
							}]
						}]
					 }],
					listeners : {
						'tabchange' : organizePanel_onTabchange
					}
				 }]
	        }, {
				id:"rightHoldPanel",
				layout:'fit',
				title:'权限列表',
				region:'center',
				border:false,
				items:[{
					xtype:"tabpanel",
					border:false,
					activeTab:0,
					items:[{
						id:'haveRolePanel',
						xtype:"panel",
						layout:"fit",
						border:false,
						margins:'0 0 0 0',
						title:"拥有角色",
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
						title:"拥有许可",
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
			}]
		}]
	});
//--------------------------- Event Handle Function -----------------------------------		
	function  organizePanel_onNodeClick(node){
		loadRightHold(node.attributes.type, node.attributes._id, node.attributes.text)
	}
	
	function personGrid_onRowlick(grid, rowIndex, e) {
		var record = personStore.getAt(rowIndex);
		if (record)
			loadRightHold('person', record.data.id, record.data.name);
	}
	
	var flag = false;
	function organizePanel_onTabchange(tabPanel, tab) {
		if (tab.id == 'personTabPanel' && personStore.getTotalCount() == 0){
			var params = {};					
			params['model']='organize.Person';	
			personStore.baseParams =personStore.baseParams || {};
			Ext.apply(personStore.baseParams, params);
			personPageingToolbar.doLoad(Math.floor(personPageingToolbar.cursor/personPageingToolbar.pageSize) * personPageingToolbar.pageSize);
		}
		if (flag)
			clearRightHold();
		else
			flag = true;
	}
	
	function rightHoldPanel_onTabchange(tabPanel, tab) {
		if (rightHoldId > 0) {
			tab.getTopToolbar().setDisabled(false);
		} else {
			tab.getTopToolbar().setDisabled(true);
		}
	}
	
	function doPersonQuery() {
			var params = personCondForm.getForm().getValues()
			
			Ext.apply(personStore.baseParams, params);
			
			personPageingToolbar.doLoad(Math.floor(0/personPageingToolbar.pageSize) * personPageingToolbar.pageSize);
	}

	function roleForm_onAddHaveRole() {
		selectRole(0, roleForm_onSelectHaveRole);
	}
	
	function roleForm_onSelectHaveRole(selectRoleId) {
		Ext.Ajax.request({method:'POST',
			url: 'right-hold!addHaveRole.action?type='+rightHoldType+'&id='+rightHoldId+'&haveId='+selectRoleId, 
			success: function(response) {
				var result = Ext.decode(response.responseText);
				if (result.success) {
					haveRoleStore.load({params:{start:0}}); 
				} else
					Ext.MessageBox.alert('', result.error);
			}
		}); 
	}
	
	function roleForm_onRemoveHaveRole() {
		 var rows=haveRoleSelectionModel.getSelections();
		 for (i = 0; i < rows.length; i++) {
			var rowData=haveRoleStore.getById(rows[i].id);
			var haveRoleId = rowData.get("id");
			
			Ext.Ajax.request({method:'POST',
				url: 'right-hold!removeHaveRole.action?type='+rightHoldType+'&id='+rightHoldId+'&haveId='+haveRoleId, 
				success: function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						var index = haveRoleStore.find("id", result.data);
						haveRoleStore.remove(haveRoleStore.getAt(index));
					} else
						Ext.MessageBox.alert('', result.error);
				}
			}); 
		 }
	}
	
	function roleForm_onAddHavePermission() {
		selectPermission(roleForm_onSelectHavePermission);
	}
	
	function roleForm_onSelectHavePermission(objectCode, haveOperations) {
		var permission = {};
		permission.type = rightHoldType;
		permission.id = rightHoldId;
		permission.objectCode = objectCode;
		permission.haveOperations = haveOperations.join(',');
		
		Ext.Ajax.request({method:'POST',
			url: 'right-hold!addHavePermission.action?'+Ext.urlEncode(permission), 
			success: function(response) {
				var result = Ext.decode(response.responseText);
				if (result.success) {
					permissionStore.load({params:{start:0}}); 
				} else
					Ext.MessageBox.alert('', result.error);
			}
		}); 
	}
	
	function roleForm_onRemoveHavePermission() {
		 var rows=permissionSelectionModel.getSelections();
		 for (i = 0; i < rows.length; i++) {
			var rowData=permissionStore.getById(rows[i].id);
			var havePermissionId = rowData.get("id");
			
			Ext.Ajax.request({method:'POST',
				url: 'right-hold!removeHavePermission.action?type='+rightHoldType+'&id='+rightHoldId+'&havePermissionId='+havePermissionId, 
				success: function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						var index = permissionStore.find("id", result.data);
						permissionStore.remove(permissionStore.getAt(index));
					} else
						Ext.MessageBox.alert('', result.error);
				}
			}); 
		 }
	}
//--------------------------- Private Function -----------------------------------		
	function loadRightHold(type, id, name) {
		rightHoldType = type;
		rightHoldId = id;
		
		Ext.getCmp("rightHoldPanel").setTitle('[' + name + '] - 权限列表');
		
		haveRoleStore.baseParams = haveRoleStore.baseParams || {};
		haveRoleStore.baseParams["type"] = rightHoldType;
		haveRoleStore.baseParams["id"] = rightHoldId;
		haveRoleStore.load({params:{start:0}}); 
		
		permissionStore.baseParams = permissionStore.baseParams || {};
		permissionStore.baseParams["type"] = rightHoldType;
		permissionStore.baseParams["id"] = rightHoldId;
		permissionStore.load({params:{start:0}}); 
		
		if (Ext.getCmp("haveRolePanel").getTopToolbar().setDisabled)
			Ext.getCmp("haveRolePanel").getTopToolbar().setDisabled(false);
			
		/*if (Ext.getCmp("havePermissionPanel").getTopToolbar().setDisabled)
			Ext.getCmp("havePermissionPanel").getTopToolbar().setDisabled(false);*/
	}
	
	function clearRightHold() {
		rightHoldType = '';
		rightHoldId = 0;
		
		Ext.getCmp("rightHoldPanel").setTitle('权限列表');
		
		haveRoleStore.removeAll(); 
		permissionStore.removeAll();
		
		if (Ext.getCmp("haveRolePanel").getTopToolbar().setDisabled)
			Ext.getCmp("haveRolePanel").getTopToolbar().setDisabled(true);
			
		/*if (Ext.getCmp("havePermissionPanel").getTopToolbar().setDisabled)
			Ext.getCmp("havePermissionPanel").getTopToolbar().setDisabled(true);*/
	}
});
</script>
</html>