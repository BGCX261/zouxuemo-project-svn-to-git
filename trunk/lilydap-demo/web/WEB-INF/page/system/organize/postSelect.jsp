    var personQueryConditionForm = new Ext.FormPanel({
		labelAlign: 'right',
		labelWidth: 60,
		autoHeight:true,
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		method:'POST',
		items:[{
			layout: 'column',
			items: [{
				columnWidth:.5,
				layout: 'form',
				items: [{
					xtype:'textfield',
					fieldLabel: '\u4EBA\u5458\u7F16\u53F7',	// 人员编号
					id: 'condition.username.like',
					name: 'condition.username.like',
					anchor:'95%'
				}]
			},{
				columnWidth:.5,
				layout: 'form',
				items: [{
					xtype:'textfield',
					fieldLabel: '\u4EBA\u5458\u59D3\u540D',	// 人员姓名
					id: 'condition.name.like',
					name: 'condition.name.like',
					anchor:'95%'
				}]
			}]
		}]
	});

<lilydap:extJsonStore name="personStore" id="personStore" url="person!query.action?activate=true" totalProperty="total" root="data" model="organize.Person"/>

<lilydap:extGridPanel name="personGrid" id="personGrid" store="personStore" model="organize.Person" autoExpandColumn="name">
	<lilydap:extSelectionModel name="personSelectionModel"/>
	<lilydap:extColumnModel name="personColumnModel">
		<lilydap:extColumn clazz="Ext.grid.RowNumberer"/>
		<lilydap:extColumn name="personSelectionModel"/>
		<lilydap:extColumn field="username"/>
		<lilydap:extColumn field="name"/>
		<lilydap:extColumn field="sex" propertys="align:'center'"/>
		<lilydap:extColumn field="birthday" propertys="align:'center'"/>
	</lilydap:extColumnModel>
    <lilydap:extPageingToolbar name="pageingToolbar" pageSize="15"/>
</lilydap:extGridPanel>

var selectPersonWin = new Ext.Window({
    id:'select-person-win',
    layout:'fit',
    width:630,
    height:482,
    closeAction:'hide',
    plain: true,
    resizable:false,
    modal:true,
    buttonAlign:'center',
    iconCls:"person-button-add",
    title:'\u6DFB\u52A0\u4EBA\u5458',	// 添加人员
    items:[{
        layout:'border',
        items:[{
                layout:'fit',
                region:'north',
                height:43,
                border:false,
                margins:'0 0 0 0',
                iconCls:"person-button-add",
                items:[personQueryConditionForm]
            },{
                layout:'fit',
                region:'center',
                border:false,
                margins:'0 0 0 0',
                items:[personGrid]
            }]
        }],
    buttons: [{
            text: '\u67E5\u627E',	// 查找
            iconCls:"common-button-find",
            handler: function(){
                var params = personQueryConditionForm.getForm().getValues()
                
                personStore.baseParams = personStore.baseParams || {};
                Ext.apply(personStore.baseParams, params);
                
                pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
            }
        },{
            text:'\u786E\u5B9A',// 确定
            iconCls:"common-button-save",
            handler: function() {
                 var rows=personSelectionModel.getSelections();
                 if (rows.length == 0) {
                    Ext.MessageBox.alert('\u63D0\u793A\u4FE1\u606F', '\u6CA1\u6709\u9009\u62E9\u8981\u64CD\u4F5C\u7684\u8BB0\u5F55\uFF01');
                    return;
                 }
                 
                 var rows=personSelectionModel.getSelections();
                 for (i = 0; i < rows.length; i++) {
                    var rowData=personStore.getById(rows[i].id);
                    var personId = rowData.get("id");
                    
                    selectPersonWin.onSelect(personId);
                 }
                
                selectPersonWin.hide();
            }
        },{
            text: '\u53D6\u6D88',	// 取消
            iconCls:"common-button-cancel",
            handler: function(){
                selectPersonWin.hide();
            }
        }]
});
//--------------------------- Public Function -----------------------------------		
function selectPerson(onSelect) {
	selectPersonWin.show();
    selectPersonWin.onSelect = onSelect;
    
    personQueryConditionForm.getForm().reset();
    pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
}