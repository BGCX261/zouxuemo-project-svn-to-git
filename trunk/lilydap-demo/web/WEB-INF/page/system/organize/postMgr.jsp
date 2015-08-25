<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="utf-8" %>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/ext-files.jsp"%>
	<style>
	.tree-department {background-image:url( <c:url value='/images/silk/icons/house.gif'/>) !important;}
	</style>
</head>
<script type="text/javascript">
//--------------------------- Global Variables Definition -----------------------------------		
var departmentLevel = '';
	
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
			dataUrl:'department!tree.action?mode=dep'
		}),
		listeners : {
			'click' : organizePanel_onNodeClick
		}
	});
//--------------------------- Department Grid Layout -----------------------------------	
<lilydap:extJsonStore name="postStore" id="postStore" url="post!list.action" model="organize.Post"/>

<lilydap:extGridPanel name="postGrid" id="postGrid" store="postStore" model="organize.Post" autoExpandColumn="des" propertys="title:'', el:'postGridDiv', enableDragDrop: true, ddGroup: 'postDD'">
	<lilydap:extTopBarButton text="新建岗位 " handler="postGrid_onAdd" propertys="disabled:true" iconCls="common-button-add"/>
	<lilydap:extTopBarButton text="删除岗位 " handler="postGrid_onRemove" propertys="disabled:true" iconCls="common-button-delete"/>
	<lilydap:extSelectionModel name="postSelectionModel"/>
	<lilydap:extColumnModel name="postColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn name="postSelectionModel"/>
		<lilydap:extColumn field="code"/>
		<lilydap:extColumn field="name"/>
		<lilydap:extColumn field="des"/>
	</lilydap:extColumnModel>
</lilydap:extGridPanel>

postGrid.on('rowclick', postGrid_onRowclick);
//--------------------------- Post Grid Row DragDrop Support, Implement adjust post order -----------------------------------		
	var ddropTarget = new Ext.dd.DropTarget(postGrid.getEl(), {
		ddGroup: "postDD", 
		copy:false,
		notifyDrop : ddropTarget_onNotifyDrop
	}); 

	var button = new Ext.Button({
		text:'&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;',
		iconCls:'common-button-save',
		handler:postForm_onSave
	});
		
//--------------------------- Post Form Layout -----------------------------------				
	var postForm = new Ext.FormPanel({
		labelAlign: 'right',
		labelWidth: 65,
		frame:true,
		defaults: {width: 370},
		url:'post!save.action',
		bodyStyle:'padding:14px 0 0',
		method:'POST',
		reader: new Ext.data.JsonReader(    
			{root: 'data'},       
			[{name: 'clazz', type: 'int'}, 'code', 'des', {name: 'id', type: 'int'}, 'name', 'depLevel']   
		), 
		items:[{
				xtype:'hidden',
				id: 'id',
				name: 'id',
				hidden:true
			}, {
				xtype:'hidden',
				id: 'depLevel',
				name: 'depLevel',
				hidden:true
			}, {
				xtype:'hidden',
				id: 'code',
				name: 'code',
				hidden:true
			}, {
				xtype:'textfield',
				fieldLabel: '岗位名称',
				id: 'name',
				name: 'name',
				anchor:'95%',	
				tabIndex:2
			}, {
				xtype:'textarea',
				fieldLabel: '备注',//备注
				id: 'des',
				name: 'des',
				height:60,
				anchor:'95%',	
				tabIndex:3
			}]
	});	
	
	postForm.addButton(button);
//--------------------------- Have Person Grid Layout -----------------------------------	
<lilydap:extJsonStore name="havePersonStore" id="havePersonStore" url="post!listHavePerson.action" model="organize.Person"/>

<lilydap:extGridPanel name="havePersonGrid" id="havePersonGrid" store="havePersonStore" model="organize.Person" autoExpandColumn="name">
	<lilydap:extTopBarButton text="添加人员 " handler="havePersonGrid_onAddHavePerson" propertys="disabled:true" iconCls="person-button-add"/>
	<lilydap:extTopBarButton text="去除人员 " handler="havePersonGrid_onRemoveHavePerson" propertys="disabled:true" iconCls="person-button-delete"/>
	<lilydap:extSelectionModel name="havePersonSelectionModel"/>
	<lilydap:extColumnModel name="havePersonColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn name="havePersonSelectionModel"/>
		<lilydap:extColumn field="username"/>
		<lilydap:extColumn field="name"/>
		<lilydap:extColumn field="sex" propertys="align:'center'"/>
		<lilydap:extColumn field="birthday" propertys="align:'center'"/>
	</lilydap:extColumnModel>
</lilydap:extGridPanel>
//--------------------------- Person Select Form Layout -----------------------------------	
<%@ include file="postSelect.jsp"%>
//--------------------------- Viewport Layout -----------------------------------		
       var viewport = new Ext.Viewport({
            layout:'fit',
            items:[{
            	layout:'border',
	            title:'岗位维护',
            	 items:[{
					layout:'fit',
                    region:'west',
					width:250,
					border:false,
                    margins:'0 0 0 0',
					items:[organizePanel]
                },{
					layout:'border',
					region:'center',
					border:false,
					items:[{
						id:"postGridPanel",
						title:'岗位列表',
						layout:'fit',
						region:'center',
						border:false,
						items:[postGrid]
					},{
						layout:'fit',
						region:'south',
						height:250,
						border:false,
						margins:'0 0 0 0',
						items:[{
							id:"postTabPanel",
							xtype:"tabpanel",
							border:false,
							activeTab:0,
							items:[{
								id:'postEditPanel',
								xtype:"panel",
								layout:"fit",
								border:false,
								margins:'0 0 0 0',
								title:"岗位编辑",
								items:[postForm]
							  },{
								id:'postHavePersonPanel',
								xtype:"panel",
								layout:"fit",
								border:false,
								margins:'0 0 0 0',
								title:"岗位包含人员列表",
								items:[havePersonGrid]
							 }],
							listeners : {
								'tabchange' : postTabPanel_onTabchange
							}
						}]
					}]
                }]
            }]
           
		});
//--------------------------- Event Handle Function -----------------------------------		
	function  organizePanel_onNodeClick(node){
		departmentLevel = node.attributes.level;
		
		Ext.getCmp("postGridPanel").setTitle('[' + node.attributes.text + '] - 岗位列表');
		
		postStore.baseParams = postStore.baseParams || {};
		postStore.baseParams["depLevel"] = departmentLevel;
		
		postStore.load({params:{start:0}}); 
		
		postGrid.getTopToolbar().setDisabled(false);
		
		postGrid_onAdd();
	}

	function postGrid_onRowclick(grid, rowIndex, e) {
		var id = postStore.getAt(rowIndex).get("id");

		var myform = postForm.form;
		postForm.load({url: 'post!get.action', method:'POST', params:{'id': id}, waitMsg: '系统正在加载数据，请稍候...'});
		
		havePersonStore.baseParams = havePersonStore.baseParams || {};
		havePersonStore.baseParams["postId"] = id;
		havePersonStore.load({params:{start:0}}); 
		
		if (havePersonGrid.getTopToolbar().setDisabled) 
			havePersonGrid.getTopToolbar().setDisabled(false);
	}
	
	function ddropTarget_onNotifyDrop(dd, e, data) {
	}
	
	function postGrid_onAdd() {
		if (departmentLevel == '') {
			Ext.MessageBox.alert('提示信息', '未选择岗位所在部门，请先选择部门');
			return;
		}
		
		var myform = postForm.form;
		myform.reset();
		myform.findField("depLevel").setValue(departmentLevel);

		havePersonStore.removeAll(); 
		
		Ext.getCmp("postTabPanel").activate("postEditPanel");
	}
	
	function postGrid_onRemove() {
		if (postSelectionModel.getSelections().length == 0) {
			Ext.MessageBox.alert('警告信息', '没有要删除的数据，请先选择删除数据，然后进行删除！');
			return;
		}
		
		Ext.MessageBox.confirm('提示信息', '您确认要删除吗？&nbsp;&nbsp;&nbsp;&nbsp;', function(btn) {if(btn == 'yes') {
			 var rows=postSelectionModel.getSelections();
			 for (i = 0; i < rows.length; i++) {
				var rowData=postGrid.store.getById(rows[i].id);
				var id = rowData.get("id");
				
				Ext.Ajax.request({method:'POST',
					url: 'post!remove.action?id='+id, 
					success: function(response) {
						var result = Ext.decode(response.responseText);
						if (result.success) {
							var index = postStore.find("id", result.id);
							postStore.remove(postStore.getAt(index));
							
							var myform = postForm.form;
							if (myform.findField("id").getValue() == result.id)
								postGrid_onAdd();
						} else
							Ext.MessageBox.alert('提示信息', result.error);
					}
				}); 
			 }
		}});
	}
	
	function postForm_onSave() {
		if (departmentLevel == '') {
			Ext.MessageBox.alert('提示信息', '未选择岗位所在部门，请先选择部门');
			return;
		}
		
		this.disabled = true;
		postForm.form.submit({url:'post!save.action', waitMsg:'系统正在保存数据，请等待...', success:postForm_onSuccess, failure:postForm_onFailure});
		this.disabled = false;
	}
		
	function postForm_onFailure(form, action) {
		var obj = action.result;
		Ext.MessageBox.alert('提示信息', obj.error);
	}

	function postForm_onSuccess(form, action){
		postStore.load({params:{start:0}});
		
		var id = action.result.data.id;
		
		var myform = postForm.form;
		postForm.load({url: 'post!get.action', method:'POST', params:{'id': id}, waitMsg: '系统正在加载数据，请稍候...'});
		
		havePersonStore.baseParams = havePersonStore.baseParams || {};
		havePersonStore.baseParams["postId"] = id;
		havePersonStore.load({params:{start:0}}); 
		
		postGrid_onAdd();
	}
	
	function postTabPanel_onTabchange(tabPanel, tab) {
		if (tab.id == 'postHavePersonPanel') {
			var myform = postForm.form;
			var postId = myform.findField("id").getValue();
			
			if (postId) {
				havePersonGrid.getTopToolbar().setDisabled(false);
			} else {
				havePersonGrid.getTopToolbar().setDisabled(true);
			}
		}
	}
	
	function havePersonGrid_onAddHavePerson() {
		if (departmentLevel == '') {
			Ext.MessageBox.alert('提示信息', '未选择岗位所在部门，请先选择部门');
			return;
		}
		
		selectPerson(havePersonGrid_onSelectHavePerson);
	}
	
	function havePersonGrid_onSelectHavePerson(personId) {
		var myform = postForm.form;
		var postId = myform.findField("id").getValue();
		
		Ext.Ajax.request({method:'POST',
				url: 'post!addHavePerson.action?id='+postId+'&haveId='+personId, 
			success: function(response) {
				var result = Ext.decode(response.responseText);
				if (result.success) {
					havePersonStore.load({params:{start:0}}); 
				} else
					Ext.MessageBox.alert('提示信息', result.error);
			}
		}); 
	}
	
	function havePersonGrid_onRemoveHavePerson() {
		var myform = postForm.form;
		var postId = myform.findField("id").getValue();
		
		 var rows=havePersonSelectionModel.getSelections();
		 for (i = 0; i < rows.length; i++) {
			var rowData=havePersonStore.getById(rows[i].id);
			var havePersonId = rowData.get("id");
			
			Ext.Ajax.request({method:'POST',
				url: 'post!removeHavePerson.action?id='+postId+'&haveId='+havePersonId, 
				success: function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						var index = havePersonStore.find("id", result.data);
						havePersonStore.remove(havePersonStore.getAt(index));
					} else
						Ext.MessageBox.alert('提示信息', result.error);
				}
			}); 
		 }
	}
});
</script>
<body>
	<div id="postGridDiv"></div>
</body>
</html>