
BaseSelectWindow = function(config) {
    Ext.apply(this, config);
    
	BaseSelectWindow.superclass.constructor.call(this, {
		modal: true,
		layout:'fit',
		autoScroll : true,
		closeAction:'close',
		plain: true,
		buttonAlign:'right',
		listeners : {
			'show' : this.onWindowShow.createDelegate(this)
		}
	});
};
 
Ext.extend(BaseSelectWindow, Ext.Window, {
    /**
    * @cfg {String} basePath
    * 在store和loader中的url链接的基本路径.
    */
	basePath: '',
    /**
    * @cfg {String} title
    * 选择框标题，默认"数据选择".
    */
	title : '\u6570\u636e\u9009\u62e9',
    /**
     * @cfg {Number} width 
     * 窗口宽度
     */
	width: 600,
    /**
     * @cfg {Number} height 
     * 窗口高度
     */
	height: 400,
    /**
     * @cfg {Number} conditionFormHeight 
     * 查询条件Form高度，默认可以放置一行查询条件
     */
	conditionFormHeight: 39,
    /**
    * @cfg {String} searchUrl
    * 搜索的URL链接 
    */
	searchUrl: '',
    /**
    * @cfg {Object} baseParams
    * 提交搜索时将把这些参数作为查询参数
    */
	baseParams: {},
    /**
    * @cfg {Boolean} isSingleSelect
    * 是否单选，true－只能选择一个；false－可以选择多个.
    */
	isSingleSelect : false,
    /**
    * @cfg {Boolean} isSearchAfterOpen
    * 是否打开窗口后立即检索，这个参数与conditions一起用，如果提供了查询条件，则是否打开窗口后立即检索数据，还是在输入查询条件后才检索数据
    */
	isSearchAfterOpen: false,
    /**
    * @cfg {Boolean} isCloseAfterSelected
    * 是否在选择后关闭窗口，如果true，则选择了数据并点击"确定"后关闭窗口，否则只有在点击"取消"后才关闭窗口
    */
	isCloseAfterSelected: true,
    /**
    * @cfg {Boolean} isClearConditionAfterOpen
    * 是否在每次打开窗口时清除掉上次查询条件数据
    */
	isClearConditionAfterOpen: true,
    /**
    * @cfg {array} conditions
    * 查询条件表单元素配置（与form中的items配置相同），如果设置了查询条件配置，则显示查询条件框
    */
	conditions: null,
    /**
     * @cfg {Number} queryDelay 
     * 文本框输入内容至查询之间的延时时间
     */
	queryDelay: 500,
    /**
    * @cfg {String} totalProperty
    * store的totalProperty属性值
    */
	totalProperty: 'totalCount', 
    /**
    * @cfg {String} root
    * store的root属性值
    */
	root: 'data', 
    /**
    * @cfg {String} successProperty
    * store的successProperty属性值
    */
	successProperty :'success',
    /**
    * @cfg {Number} pageSize
    * 分页时每页尺寸，如果不需要分页，则设置为-1
    */
	pageSize: 12, 
    /**
    * @cfg {array} fields
    * store的fields参数值
    */
	fields: null,
    /**
    * @cfg {array} columns
    * grid的ColumnModel的参数值（只包括需要显示的字段列，不要包括PagedRowNumberer和CheckboxSelectionModel）
    */
	columns: null, 
    /**
    * @cfg {String} autoExpandColumn
    * grid的自动扩展列名
    */
    autoExpandColumn : null,
    /**
    * @cfg {String} selectFieldName
    * 选中后返回数据的字段名
    */
	selectFieldName: 'id',
    /**
    * @cfg {String} displayFieldName
    * 选中后显示数据的字段名
    */
	displayFieldName: 'name',
	
//---------------------------------	以下为私有属性，不允许直接设置 ---------------------------------
    /**
    * @cfg {array} initValues
    * 初始选择值，字符串数组，在执行show方法时传入
    */
	initValues: [], 
	
    /**
    * @cfg {Ext.FormPanel} conditionForm
    * 查询条件Form，作为显示查询条件的窗体
    */
	conditionForm: null,

    /**
    * @cfg {Object} initQueryParams
    * 查询条件参数初始值，在调用show方法时传入，作为初始的查询参数值
    */
	initQueryParams: {},
	
    /**
    * @cfg {String} conditionValues
    * 存放先前查询条件窗口，用于在查询条件窗口中事件触发后比较查询条件是否改变，改变则执行查询
    */
	conditionValues: '',
	
    /**
    * @cfg {Ext.util.DelayedTask} queryTask
    * 查询延时任务
    */
	queryTask: null,
	
    /**
    * @cfg {Ext.grid.GridPanel} grid
    * 检索的数据Grid
    */
	grid: null,

    /**
    * @cfg {Ext.PagingToolbar} pageingToolbar
    * 分页工具栏
    */
	pageingToolbar: null,
	
//---------------------------------	以下为重载方法 ---------------------------------
	show : function(initQueryParams, initValues){
		if (initQueryParams) 
			Ext.apply(this.initQueryParams, initQueryParams, {});

		if (initValues)
			if (typeof initValues == 'string')
				this.initValues = initValues.split(',');
			else
				this.initValues = initValues;
		else
			this.initValues = [];
			
		BaseSelectWindow.superclass.show.call(this);
	},
	
	initComponent : function(){
        this.addEvents(
            /**
             * @event selectdata 
             * 在选择了数据列表，点击确定后，遍历每行选择的数据，并触发本事件.
             * @param {Number} index 选择行顺序号
             * @param {Record} record 选择行记录
             */
            'selectdata'
        );
        this.addEvents( 
            /**
             * @event ok
             * 在选择了数据列表后点击确定返回时触发.
             * @param {String} selectValue 选择的数据值字符串，以","分隔
             * @param {String} selectDisplayName 选择的显示名称字符串，以","分隔
             */
            'ok'
        );
        
        //如果设置了查询条件items，则创建查询条件FormPanel，并创建查询延时任务（实现当用户在查询条件中输入字符时，自动执行延时查询任务）
        if (this.conditions) {
        	this.conditionForm = new Ext.FormPanel({
				labelAlign: 'right',
				labelWidth: 55,
				autoHeight:true,
				frame:true,
				bodyStyle:'padding:8px 5px 0',
				method:'POST',
	            items: this.conditions
            });
            
			this.queryTask = new Ext.util.DelayedTask(this.queryByCondition, this);            
        } 
        
        //创建数据列表的store数据源
		var ds = new Ext.data.JsonStore({
			url: this.basePath + this.searchUrl,
			root: this.root, 
			totalProperty: this.totalProperty, 
			successProperty: this.successProperty,
			remoteSort: true,
			fields: this.fields,
			listeners : {
				'load' : this.onGridLoad.createDelegate(this)
			}
		});
    	 
		//根据是单选还是多选，相应创建行选择器或CheckBox选择器
		var sm;
		if (this.isSingleSelect)
			sm = new Ext.grid.RowSelectionModel({singleSelect: true});
		else {
			sm = new Ext.grid.CheckboxSelectionModel();
			
			//在Grid的Column配置中，加入CheckBox列至第一列
			var length = this.columns.length;
			for (var index = length; index > 0; index--)
				this.columns[index] = this.columns[index - 1];
				
			this.columns[0] = sm;
		}
			
		//创建Grid的列构造器
		var cm = new Ext.grid.ColumnModel(this.columns); 
		
		var gridConfig = {
			store: ds,
			sm: sm,
			cm: cm
		};

		if (this.pageSize > 0) {
			this.pageingToolbar = new Ext.PagingToolbar({
				pageSize: this.pageSize,
				store: ds,
				displayInfo: true,
				displayMsg: '\u663E\u793A\u7B2C {0} \u6761\u5230 {1} \u8BB0\u5F55\uFF0C\u5171 {2} \u6761',
				emptyMsg: '\u672A\u627E\u5230\u6570\u636E\u8BB0\u5F55'
			});

			gridConfig['bbar'] = this.pageingToolbar;
		}
		
		//添加自动扩展列配置
		if (this.autoExpandColumn != null)
			gridConfig['autoExpandColumn'] = this.autoExpandColumn;
		
		//创建GridPanel并添加双击事件监听函数（为了实现在客户双击数据列某行时即可返回该行的选择数据）
		this.grid = new Ext.grid.GridPanel(gridConfig);
		this.grid.on('rowdblclick', this.onGridRowdblclick, this);
        
		//如果指定了查询条件，则创建BorderLayout，顶部为查询条件FormPanel，底部为数据列表Grid
		//否则直接显示数据列表Grid
		if (this.conditionForm)
			this.items = [{
				layout:"border", border:false, items:[{
					region:"north", height:this.conditionFormHeight, border:false, layout: 'fit', items: [this.conditionForm]
				},{
					region:"center", border:false, layout:"fit", items:[this.grid]
				}]
			}];
		else
			this.items = this.grid;
			
        BaseSelectWindow.superclass.initComponent.call(this);
    },
    
//---------------------------------	以下为事件处理方法 ---------------------------------
	onRender : function(ct, position){
		//添加"选择"和"关闭"按钮
		this.addButton({
			text:'\u9009\u62e9',
            handler: this.onOk.createDelegate(this)
        });
		this.addButton({
			text:'\u5173\u95ed',
            handler: this.onCancle.createDelegate(this)
        });
        
        BaseSelectWindow.superclass.onRender.call(this, ct, position);
	},
	
	onWindowShow : function(window) {
		this.center();

		//如果设置了查询条件，则对查询条件中的字段添加事件监听函数，实现在查询条件改变时自动执行查询函数
		//isHaveListenKeyUpEvent：保证事件监听函数只会在第一次显示的时候增加一次
        if (this.conditionForm && !this.isHaveListenKeyUpEvent) {
        	this.conditionForm.getForm().items.each(function(field){
        		var xtype = field.getXType();
				if (xtype == 'hidden') 
					return;
							
				var el = field.getEl();
				
				//TODO propertychange,cut,paste事件在FF中不起作用，考虑如何在FF中也能实现在剪切、粘贴、以及DateField数据改变时也能触发查询
        		if (xtype == 'combo') {
        			field.on('select', this.onComboSelect, this);
        		} else if (xtype == 'datefield' || xtype == 'timefield' || xtype == 'trigger') {
        			el.on('propertychange', this.onFieldPropertyChange, this);
        		}

        		el.on('keyup', this.onFieldKeyUp, this);
        		
				//当剪切和粘贴时也触发查询方法
				el.on('cut', this.onFieldCut, this);
				el.on('paste', this.onFieldPaste, this);
        		
				field.on('change', this.onFieldChange, this);
				
        		if (!this.firstConditionField)
        			this.firstConditionField = field;
	        }, this);
	        
	        this.isHaveListenKeyUpEvent = true;
        }
        
        if (this.conditionForm) {
			var basicForm = this.conditionForm.getForm();

			if (this.isClearConditionAfterOpen)
	        	basicForm.reset();

			for (paramName in this.initQueryParams) {
				var field = basicForm.findField(paramName);
				if (field != null)
					field.setValue(this.initQueryParams[paramName]);
			}
        }

		//如果设置为打开时即检索，则调用查询方法检索数据
		if (this.isSearchAfterOpen)
			this.queryByCondition(); 
        
        //让第一个查询条件字段获得焦点
        if (this.firstConditionField)
        	this.firstConditionField.focus.defer(600, this.firstConditionField);
	},
	
	onGridRowdblclick : function(grid, rowIndex, e) {
		//保证只选中鼠标双击所在行，其他行取消选中
		var selectionModule = this.grid.getSelectionModel();
		selectionModule.selectRow(rowIndex, false);

		this.onOk();
	},
	
	onGridLoad : function() {
	},
	
	onComboSelect : function(field, newValue, oldValue) {
		this.queryByCondition();
	},
	
	onFieldChange : function(field, newValue, oldValue) {
		this.queryByCondition();
	},
	
	onFieldKeyUp : function(e) {
		//查询条件字段按键放开时，执行查询延时任务
		//if (!e.isSpecialKey())
			this.queryTask.delay(this.queryDelay);
	},
	
	onFieldPropertyChange : function(e) {
		this.queryByCondition();
	},
	
	onFieldCut : function(e) {
		this.queryTask.delay(this.queryDelay);
	},
	
	onFieldPaste : function(e) {
		this.queryTask.delay(this.queryDelay);
	},
	
	onOk : function() {
		var selectValue = '', selectDisplayName = '';
		
		//检查数据列中是否有选中行，没有则提示并返回
		var records = this.grid.getSelectionModel().getSelections();
		if (records.length == 0) {
			Ext.MessageBox.alert('\u63d0\u793a', '\u6ca1\u6709\u9009\u62e9\u7684\u6570\u636e\uff0c\u8bf7\u5148\u9009\u62e9\u8981\u8fd4\u56de\u7684\u6570\u636e\uff01');
			return;
		}

		//构造选中数据字符串和显示字符串
		
		var first = true;
		for(var index = 0; index < records.length; index++){
			var record = records[index];
			this.fireEvent("selectdata", index, record);
			
			if (first)
				first = false;
			else {
				selectValue += ',';
				selectDisplayName += ',';
			}
				
			selectValue += record.get(this.selectFieldName);
			selectDisplayName += record.get(this.displayFieldName);
		}
		
		if (this.isCloseAfterSelected)
			this.hide();
			
		this.fireEvent("ok", selectValue, selectDisplayName);
	},
	
	onCancle : function(){ 
		this.hide();
	},
	
//---------------------------------	以下为子类继承方法 ---------------------------------
	processQueryParam : function(params) {
		
	},
	
//---------------------------------	以下为私有方法 ---------------------------------
	queryByCondition : function() {
		var values = this.conditionForm.getForm().getValues(true);
		if (this.conditionValues == values)
			return;
			
		this.conditionValues = values;
		
		Ext.apply(this.grid.getStore().baseParams, this.conditionForm.getForm().getValues());
		this.doQuery();
	}, 
	
	doQuery : function() {
		var store = this.grid.getStore();
		this.processQueryParam(store.baseParams);

		if (this.pageingToolbar != null)
			this.pageingToolbar.doLoad(0);
		else
			store.load({params:{start:0}}); 
	}
});

