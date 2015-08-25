//--------------------------- Select Role Dialog Layout -----------------------------------		
<%@ page language="java" pageEncoding="utf-8" %>

<lilydap:extJsonStore name="selectRoleStore" id="selectRoleStore" url="role!select.action" model="right.Role"/>

<lilydap:extGridPanel name="selectRoleGrid" id="selectRoleGrid" store="selectRoleStore" model="right.Role" autoExpandColumn="des">
	<lilydap:extSelectionModel name="selectRoleSelectionModel"/>
	<lilydap:extColumnModel name="selectRoleColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn name="selectRoleSelectionModel"/>
		<lilydap:extColumn field="code"/>
		<lilydap:extColumn field="name"/>
		<lilydap:extColumn field="des"/>
	</lilydap:extColumnModel>
</lilydap:extGridPanel>

var selectRoleWin = new Ext.Window({
    id:'select-role-win',
    layout:'fit',
    width:540,
    height:380,
    closeAction:'hide',
    plain: true,
    modal:true,
    buttonAlign:'center',
    title:'选择角色',
    iconCls:'common-button-window',
    items:selectRoleGrid,
    buttons: [{
        text:'确定',
        iconCls:'common-button-save',
        disabled:false,
        handler: function() {
             var rows=selectRoleSelectionModel.getSelections();
             if (rows.length == 0) {
             	Ext.MessageBox.alert('提示信息', '没有选择要操作的记录！');
             	return;
             }
             
             var rows=selectRoleSelectionModel.getSelections();
             for (i = 0; i < rows.length; i++) {
                var rowData=selectRoleStore.getById(rows[i].id);
                var roleId = rowData.get("id");
                
	        	selectRoleWin.onSelect(roleId);
             }
        	
            selectRoleWin.hide();
        }
    },{
        text: '取消',
         iconCls:'common-button-cancel',
        handler: function(){
            selectRoleWin.hide();
        }
    }]
});
//--------------------------- Select Permission Dialog Layout -----------------------------------		
var rightObjectPanel = new Ext.tree.TreePanel({
    id:'rightObject',
    autoScroll : true,
    animate : true,
    border : false,
    rootVisible : true,
    bodyStyle: 'background-color:#cad9ec;',
    root : new Ext.tree.AsyncTreeNode( {
        id : 'root',
        text : '对象列表',
        draggable : false,
        expanded : true
    }),
    loader: new Ext.tree.TreeLoader({
        dataUrl:'role!listRightObject.action'
    }),
    listeners : {
        'click' : function  (node){
			if (node.attributes.type == 'object')	{
				rightOperationStore.baseParams = rightOperationStore.baseParams || {};
				rightOperationStore.baseParams["objectCode"] = node.attributes.code;
				rightOperationStore.load({params:{start:0}}); 
			}
		}
    }
});

<lilydap:extJsonStore name="rightOperationStore" id="rightOperationStore" url="role!listRightOperations.action" model="right.RightOperation"/>

<lilydap:extGridPanel name="rightOperationGrid" id="rightOperationGrid" store="rightOperationStore" model="right.RightOperation" autoExpandColumn="des">
	<lilydap:extSelectionModel name="rightOperationSelectionModel"/>
	<lilydap:extColumnModel name="rightOperationColumnModel">
		<lilydap:extColumn name="rightOperationSelectionModel"/>
		<lilydap:extColumn field="code"/>
		<lilydap:extColumn field="name"/>
		<lilydap:extColumn field="des"/>
	</lilydap:extColumnModel>
</lilydap:extGridPanel>

var selectPermissionWin = new Ext.Window({
    id:'select-permission-win',
    layout:'fit',
    width:540,
    height:380,
    closeAction:'hide',
    plain: true,
    modal:true,
    buttonAlign:'center',
    title:'选择许可',
    items:[{
        layout:"border",
		border:false,
        items:[{
            region:"center",
            layout:"fit",
            items: rightObjectPanel
          },{
            region:"east",
            title:"可用操作",
            width:300,
            layout:"fit",
            items: rightOperationGrid
        }]
    }],
    buttons: [{
        text:'确定',
        disabled:false,
        handler: function() {
             var rows=rightOperationSelectionModel.getSelections();
             if (rows.length == 0) {
             	Ext.MessageBox.alert('提示信息', '没有选择要操作的记录！');
             	return;
             }
             
             var objectCode, haveOperations = [];
             for (i = 0; i < rows.length; i++) {
                var rowData=rightOperationStore.getById(rows[i].id);
                
                if (!objectCode)
                	objectCode = rowData.get("objectCode");

				haveOperations[i] = rowData.get("code");
             }
        	
            //selectPermissionWin.hide();
            rightOperationSelectionModel.clearSelections();
            
        	selectPermissionWin.onSelect(objectCode, haveOperations);
        }
    },{
        text: '取消',
        handler: function(){
            selectPermissionWin.hide();
        }
    }]
});

//--------------------------- Public Function -----------------------------------		
function selectRole(filterId, onSelect) {
	selectRoleWin.show();
    selectRoleWin.onSelect = onSelect;
    
    selectRoleStore.baseParams = selectRoleStore.baseParams || {};
    selectRoleStore.baseParams["filterId"] = filterId;
    selectRoleStore.load({params:{start:0}}); 
}

function selectPermission(onSelect) {
	selectPermissionWin.show();
    
    rightOperationSelectionModel.clearSelections();
    
    selectPermissionWin.onSelect = onSelect;
}