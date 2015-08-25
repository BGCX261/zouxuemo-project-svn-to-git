//-------原始的javascript没有替换所有的方法，所以给String对象扩展此方法。-----------
 String.prototype.replaceAll = function(str1,str2){
	var temp = this;
	while(temp.indexOf(str1) != -1) {
			temp = temp.replace(str1,str2);
	}
	return temp;
}
//--------------------------- Window Event Handle Function -----------------------------------		
//remove the array by the index,and change the length of the array
Array.prototype.removeByIndex=function(dx){
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++){
        if(this[i]!=this[dx]){
            this[n++]=this[i]
        }
    }
    this.length-=1
  }

if (Ext.Window) {
	Ext.override(Ext.Window, {
		maximizable:false,
		minimizable:false,
		close:function(){
			this.hide();
		}
	});
}
//当所有页面加载Grid时，有请等待提示
if(Ext.grid.GridPanel){
	Ext.override(Ext.grid.GridPanel,{
		loadMask: {msg:'\u7CFB\u7EDF\u6B63\u5728\u52A0\u8F7D\u6570\u636E\uFF0C\u8BF7\u7A0D\u5019...'}
	});
}	
//所有Ajax请求，时间超时设置为300秒
Ext.Ajax.timeout = 300000;
var redStar = '<font color=red>*</font>';
function onEnter(elem, e) {
	if (e.getKey() === e.ENTER) {
		if(event.keyCode ==13){
			event.keyCode = 9;
		}
	}
}

function initFocus(fieldName) {
	var field = Ext.get(fieldName);
	if (field && !field.disabled) {
		field.focus.defer(600, field);
	}
	
}

function initTabEvent(formpanel){
	var items = formpanel.form.items;
	for(var i = 0; i < items.length;i++){
		var field = items.itemAt(i);
		if(field && !field.disabled){
			field.on('specialkey', onEnter);	
		}	
	}
} 

function createPagingToolbar(pageSize,ds,msgFirst,msgSecond,msgThird,msgFourth,msgEmpty){
	var pageingToolbar = new Ext.PagingToolbar({ 
					pageSize: pageSize, 
					store: ds, 
					displayInfo: true, 
					displayMsg: msgFirst + ' {0} ' + msgSecond + ' {1} ' + msgThird + ' {2}  ' + msgFourth, 
					emptyMsg: msgEmpty
	});

	return pageingToolbar;
}

function createViewport(grid){
	var viewport = new Ext.Viewport({				
			layout:"fit",		
			items:[grid]
	}); 

	return viewport;
}

//remove some records
function removeRecords(tooltip,msg,sm,ds,grid,action, callbackFunc){
	 var rows=sm.getSelections();		

	 var ids = "";
	 for (i = 0; i < rows.length; i++) {
		var rowData=grid.store.getById(rows[i].id);		
		ids = ids + rowData.get("id") + ",";
	 }	

	if(ids != "") {	
		 Ext.MessageBox.confirm(tooltip, 
			msg, function(btn) {
				if(btn == 'yes') {							
					 for (i = 0; i < rows.length; i++) {
						var rowData=grid.store.getById(rows[i].id);
						ds.remove(rowData);						
					 }							 
						
					 Ext.Ajax.request({url: action + '&ids='+ids, method:'POST'}); 
					 callbackFunc();					
				}
		});
	}else{		
		Ext.Msg.alert('\u63d0\u793a\u4fe1\u606f', '\u6ca1\u6709\u9009\u62e9\u8981\u64cd\u4f5c\u7684\u8bb0\u5f55')
	}
			
}

//operate some records
function operateRecords(form,formUrl,tooltip,msg,sm,ds,grid,action,pageSize,isRefreshParentPage){
	
	if(typeof(isRefreshParentPage)=='undefined') isRefreshParentPage = true;

	var rows=sm.getSelections();
	var ids = "";
	for (i = 0; i < rows.length; i++) {
		var rowData=grid.store.getById(rows[i].id);	
		ids = ids + rowData.get("id") + ",";
	}

	if(ids !="") {
		Ext.MessageBox.confirm(tooltip, msg, function(btn) {
			if(btn == 'yes') {
				Ext.Ajax.request({url: action + '&ids='+ids, method:'POST',success: function(response) {
					var result = Ext.decode(response.responseText);								
					if (result.success) {
						Ext.MessageBox.alert('\u7cfb\u7edf\u63d0\u793a', '\u64cd\u4f5c\u6210\u529f\uff01');						
						if(form) 
							loadFormById(sm,grid,form,formUrl,'<bean:message bundle="insurance" key="insurance.common.waiting"/>');

					}else{
						Ext.MessageBox.alert('\u7cfb\u7edf\u63d0\u793a', result.error);
					}
				}});

				if(isRefreshParentPage)	 ds.load({params:{start:0,limit:pageSize}});
			}
		});	
	}else{
		Ext.Msg.alert('\u63d0\u793a\u4fe1\u606f', '\u6ca1\u6709\u9009\u62e9\u8981\u64cd\u4f5c\u7684\u8bb0\u5f55')
	}
			
}


//operate some records
function operateRecordsAndCloseWin(tooltip,msg,sm,ds,grid,action,pageSize,closeWin, successFunc){
	 var rows=sm.getSelections();
	 var ids = "";
	 for (i = 0; i < rows.length; i++) {
		var rowData=grid.store.getById(rows[i].id);					
		ids = ids + rowData.get("id") + ",";
	 }	

	if(ids !="") { 
	 Ext.MessageBox.confirm(tooltip, 
			msg, 
			function(btn) {
				 if(btn == 'yes') {
					 Ext.Ajax.request({
						 url: action + '&ids='+ids, 
						 method:'POST',
						success: function(response) {
							 var result = Ext.decode(response.responseText);								
							 if (result.success) {
								Ext.MessageBox.alert('\u7cfb\u7edf\u63d0\u793a', '\u64cd\u4f5c\u6210\u529f\uff01');
								closeWin.hide();									
								ds.load({params:{start:0,limit:pageSize}});
								successFunc();
							 } else{ 
								Ext.MessageBox.alert('\u7cfb\u7edf\u63d0\u793a', result.error);
							 }
						 }							
					 });
				}});	
	}else{
		 Ext.Msg.alert('\u63d0\u793a\u4fe1\u606f', '\u6ca1\u6709\u9009\u62e9\u8981\u64cd\u4f5c\u7684\u8bb0\u5f55')
	}
}

//operate some records
function operateRecordsAndNotCloseWin(tooltip,msg,sm,ds,grid,action,pageSize,successFunc){
	 var ids = "";
	 var rows=sm.getSelections();	

	 for (i = 0; i < rows.length; i++) {
		var rowData=grid.store.getById(rows[i].id);					
		ids = ids + rowData.get("id") + ",";
	 }	

	 if(ids !="") { 
	 Ext.MessageBox.confirm(tooltip, 
			msg, 
			function(btn) {
				 if(btn == 'yes') {					
					 Ext.Ajax.request({
						 url: action + '&ids='+ids, 
						 method:'POST',
						success: function(response) {
							 var result = Ext.decode(response.responseText);								
							 if (result.success) {
								Ext.MessageBox.alert('\u7cfb\u7edf\u63d0\u793a', '\u64cd\u4f5c\u6210\u529f\uff01');									
								ds.load({params:{start:0,limit:pageSize}});
								successFunc();
							 } else{ 
								Ext.MessageBox.alert('\u7cfb\u7edf\u63d0\u793a', result.error);
							 }
						 }							
					 });
				}
			}
	);	
	}else{
		 Ext.Msg.alert('\u63d0\u793a\u4fe1\u606f', '\u6ca1\u6709\u9009\u62e9\u8981\u64cd\u4f5c\u7684\u8bb0\u5f55')
	}
}

function createGridPanel(gridname,title,loadMsg,ds,sm,cm,bbar,tbar){
	if (!gridname || gridname == null)
		return new Ext.grid.GridPanel({ 
			store: ds, 
			sm: sm, 
			cm: cm, 
			title:title,
			loadMask: {msg:loadMsg},
			selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),//????, ?? 
			bbar: bbar,	
			tbar:tbar
		});	
	else
		return new Ext.grid.GridPanel({ 
			el: gridname, 
			store: ds, 
			sm: sm, 
			cm: cm, 
			title:title,
			loadMask: {msg:loadMsg},
			selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),//????, ?? 
			bbar: bbar,	
			tbar:tbar
		});	
}

function refreshCurrPage(pageingToolbar){
	pageingToolbar.doLoad(Math.floor(pageingToolbar.cursor/pageingToolbar.pageSize) * pageingToolbar.pageSize);
}

function loadAddForm(form,url,waitMsg){	
	form.form.reset();
	form.load({url:url,method:'POST',params:{},waitMsg:waitMsg});
}

function loadFormById(sm,grid,form,url,waitMsg){
	form.form.reset();
	var rows=sm.getSelections();
	var id =grid.store.getById(rows[0].id).get("id");
	form.load({url:url,method:'POST',params:{'id':id},waitMsg:waitMsg});

}

function getSingleSelectRecordByGrid(sm,grid){	
	var rows=sm.getSelections();
	var id =grid.store.getById(rows[0].id).get("id");	
	return id;
}


function saveForm(form,url,waitMsg,success,failuer){
	 if(form.getForm().isValid()){
		 this.disabled = true;
		 form.getForm().submit({url:url, waitMsg:waitMsg,success:success,failure:failuer});
		 this.disabled = false;
		
	 }else{
		
		 this.disabled = false;
		 failuer();
	}
}
 
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

/**
 * reset given fields in the form
 * usage : resetFormFields(conditionForm, 'returnVisitMan','condition(returnVisitMan,in)'); 
 * @author suntun
 */
function resetFormFields(form){
	for(var i=1; i<arguments.length; i++){
		var field = form.form.findField(arguments[i]);
		if( field )
			field.reset();
	}
}

/**
 * 把conditionForm转换成sql的条件字符串（导出EXCEL时使用)
 * @author suntun
 */
function combinConditionStr(conditionForm){
		var condStr = "";
		var key = "";
		var op = "=";
		var value = "";
		var conditions = conditionForm.form.getValues(false);
		for(var cond in conditions){
			
			if(conditions[cond] == ""||cond.indexOf("condition")==-1) continue;
			key = cond.substring(cond.indexOf('(') + 1, ( cond.indexOf(',') != -1) ? cond.indexOf(',') : cond.indexOf(')') );
			if( cond.indexOf(',') != -1 ) 
				op = cond.substring( cond.indexOf(',') + 1 , cond.lastIndexOf(')') );
			var value = conditions[cond];
			condStr += " and ";
			if( op == "=" || op == ">" || op == ">=" || op == "<" || op == "<=" || op=="<>")
				condStr += key + op + " '" + value + "'";
			if( op == "like")
				condStr += key + " like '%" + value + "%'";
			if( op == "rlike")
				condStr += key + " like '" + value + "%'";
			if( op == "in" ){
				var valueArr = value.split(",");
				condStr += key + " in (";
				for(var i=0; i<valueArr.length; i++){
					condStr += "'" + valueArr[i] + "'";
				}
				condStr +=  ")";
			}
		}
		return encodeURIComponent(condStr);
}
	
//create a Standard combo by 
function createStandardCombo(store,comboName,fieldLabel,allowBlank,tabIndex,anchor){
	anchor = anchor || '100%';
			   
	var combo = new Ext.form.ComboBox({	
		xtype:"combo",	
		fieldLabel: fieldLabel,	
		name: comboName+"_display",		
		store:store,
		mode: 'local', 
		editable:false,
		forceSelection: true,
		hiddenName:comboName,	
		valueField:'id',		
		displayField:'value',	
		allowBlank:allowBlank,
		triggerAction: 'all',			
		tabIndex:tabIndex,
		anchor: anchor
	});

	return combo;
} 

//create a Readonly combo by 
function createReadonlyCombo(store,comboName,fieldLabel,tabIndex,anchor){
	anchor = anchor || '100%';
			   
	var combo = new Ext.form.ComboBox({	
		xtype:"combo",	
		fieldLabel: fieldLabel,	
		name: comboName+"_display",		
		store:store,
		mode: 'local', 
		editable:false,
		readonly:true,
		forceSelection: true,
		hiddenName:comboName,	
		valueField:'id',		
		displayField:'value',	
		anchor: anchor
	});

	return combo;
} 

//create a Standard Value combo by 
function createValueCombo(store,comboName,fieldLabel,allowBlank,tabIndex,anchor){
	anchor = anchor || '100%';
			   
	var combo = new Ext.form.ComboBox({	
		xtype:"combo",	
		fieldLabel: fieldLabel,	
		name: comboName+"_display",		
		store:store,
		mode: 'local', 
		editable:false,
		forceSelection: true,
		hiddenName:comboName,	
		valueField:'value',		
		displayField:'value',	
		allowBlank:allowBlank,
		triggerAction: 'all',			
		tabIndex:tabIndex,
		anchor: anchor
	});

	return combo;
} 

//create a Readonly Value combo by 
function createReadonlyValueCombo(store,comboName,fieldLabel,tabIndex,anchor){
	anchor = anchor || '100%';
		   
	var combo = new Ext.form.ComboBox({	
		xtype:"combo",	
		fieldLabel: fieldLabel,	
		name: comboName+"_display",		
		store:store,
		mode: 'local', 
		editable:false,
		readonly:true,
		forceSelection: true,
		hiddenName:comboName,	
		valueField:'value',		
		displayField:'value',	
		anchor: anchor
	});

	return combo;
} 


function checkMobile(mobilePhoneNumber,field,isAllowBlank){
	field.clearInvalid();	
	if(typeof(isAllowBlank)=='undefined') isAllowBlank = false;	 
	
	if(isAllowBlank)
		if(mobilePhoneNumber=="") return true;
	
	var checkNumberStr
	if(mobilePhoneNumber.length==11){
		checkNumberStr = /[1][3,5][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else if(mobilePhoneNumber.length==13){
		checkNumberStr = /[1][0][6][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else if(mobilePhoneNumber.length==14){
		checkNumberStr = /[1][0][6][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else if(mobilePhoneNumber.length==15){
		checkNumberStr = /[1][0][6][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else{
	field.markInvalid("\u8f93\u5165\u7684\u624b\u673a\u53f7\u6216\u5c0f\u7075\u901a\u53f7\u9519\u8bef");			
	}

	var r = mobilePhoneNumber.match(checkNumberStr);
	
	if(r==null||r=="") {	
	field.markInvalid("\u8f93\u5165\u7684\u624b\u673a\u53f7\u6216\u5c0f\u7075\u901a\u53f7\u9519\u8bef");			
		return false;
	}
	
	return true;
}

function installQueryOnFieldChanged(form, queryFunc) {
	this._queryForm = this._queryForm || {};

	var formId = form.getId();
	if (this._queryForm[formId])
		return;
	else
		this._queryForm[formId] = {};

	var currQueryForm = this._queryForm[formId];

	currQueryForm.form = form;
	currQueryForm.formValues = '';
	currQueryForm.queryFunc = queryFunc;
	currQueryForm.queryDelay = 300;
	
	currQueryForm.handleQuery = function() {
		var values = this.form.getForm().getValues(true);
		if (this.formValues == values)
			return;
			
		this.formValues = values;
		
		this.queryFunc();
	}
	
	currQueryForm.queryTask = new Ext.util.DelayedTask(currQueryForm.handleQuery, currQueryForm);
	
	form.on('afterlayout', function() {
		//TODO propertychange,cut,paste事件在FF中不起作用，考虑如何在FF中也能实现在剪切、粘贴、以及DateField数据改变时也能触发查询

        if (this.form && !this.isHaveListenKeyUpEvent) {
        	this.form.getForm().items.each(function(field){
        		var xtype = field.getXType();
				var el = field.getEl();
				
				if (xtype == 'hidden') 
					return;
							
        		if (xtype == 'textfield' || xtype == 'numberfield' || xtype == 'textarea') {
        			el.on('keyup', function(e) {
						//查询条件字段按键放开时，执行查询延时任务
						if (!e.isSpecialKey())
							this.queryTask.delay(this.queryDelay);
					}, this);
        		} else if (xtype == 'combo') {
        			field.on('select', function(field, newValue, oldValue) {
						this.handleQuery();
					}, this);
        		} else if (xtype == 'datefield' || xtype == 'timefield' || xtype == 'monthfield') {
        			el.on('keyup', function(e) {
						//查询条件字段按键放开时，执行查询延时任务
						if (!e.isSpecialKey())
							this.queryTask.delay(this.queryDelay);
					}, this);
					
        			el.on('propertychange', function(e) {
						if(xtype == 'monthfield'){
							this.queryTask.delay(this.queryDelay);
						}else{
							//if (e.browserEvent.propertyName == 'value') 现在不知道为什么只要有这一句自动查询就不起作用了。但放
							//开后就会查询两次，但如果不放开则不查询实现不了需求。所以即使用多查询一次也放开先实现需求再说。孙成才20091009
								this.queryTask.delay(this.queryDelay);
						}
        				
        			}, this);
        		} else {
        			field.on('change', function(field, newValue, oldValue) {
						this.handleQuery();
					}, this);
        		}

				//当剪切和粘贴时也触发查询方法
				el.on('cut', function(e) {
					this.queryTask.delay(this.queryDelay);
				}, this);
				el.on('paste', function(e) {
					this.queryTask.delay(this.queryDelay);
				}, this);

        		if (!this.firstConditionField)
        			this.firstConditionField = field;
	        }, this);
	        
	        this.isHaveListenKeyUpEvent = true;
        }
        
        //让第一个查询条件字段获得焦点
        if (this.firstConditionField)
        	this.firstConditionField.focus.defer(600, this.firstConditionField);
	}, currQueryForm, {delay: 300}); 
}

/**
 * 用于在查询窗口中实现双击显示明细信息
 * win 要弹出的窗口名
 * form 窗口中包含的Form的名字
 * sm  主列表中的SelectionModel
 * grid 主列表Grid的名字
 * parameter 它是Model的路径名字, 如shenai.RepairRecord
 *  extPro 扩展参数,如果没有,可以为空 .如果要根据客户代码得到客户名, '$clientName':'$[clientName(clientCode)]' 
 */
	function  showQueryFormDetail(win, form , sm , grid ,parameter,extPro){
		win.show();
			
		var row=sm.getSelected();
		var id =grid.store.getById(row.id).get("id");
		var config = {'id':id,'model':parameter };
		Ext.apply(config, extPro);
		form.load({
			url:'../generalQuery!get.action',
			method:'POST',
			params:config, 
			waitMsg:'waiting....'
		});	
	}


Ext.app = function(){
    var msgCt;

    function createBox(t, s){
        return ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');
    }
    return {
        msg : function(title, format){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            msgCt.alignTo(document, 't-t');
            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
            m.slideIn('br').pause(1).ghost("br", {remove:true});
        }
    };
}();

//只处理名称为condition的字段或自己添加到params里边的condition字段，不处理自己组的查询串。
function convertParams(params){
	for(var p in params){		
		if(p.indexOf('<')!=-1&&isDateString(params[p])){
			params[p] = params[p] + ' 23:59:59'
		}	
	}
	return params;
}

function isDateString(sDate){
	var iaMonthDays = [31,28,31,30,31,30,31,31,30,31,30,31]
    var iaDate = new Array(3)
    var year, month, day

    if (arguments.length != 1) return false
    iaDate = sDate.toString().split("-")
    if (iaDate.length != 3) return false
    if (iaDate[1].length > 2 || iaDate[2].length > 2) return false

    year = parseFloat(iaDate[0])
    month = parseFloat(iaDate[1])
    day=parseFloat(iaDate[2])

    if (year < 1900 || year > 2100) return false
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1]=29;
    if (month < 1 || month > 12) return false
    if (day < 1 || day > iaMonthDays[month - 1]) return false
    return true
}

/*
transform string to json array
example:
	'1-one,2-two,3-three' ->  [[1, 'one'], [2, 'two'], [3, 'three']]
	'one,two,three'       ->  [[ 'one', 'one'], ['two', 'two'], ['three', 'three']]
*/
function arrayInfo(optional){
	var array=[];
	if(optional!=''){
		 array = optional.split(",");
		
		for(i = 0; i < array.length; i++){
			var tmp = array[i].trim().split("-");
		
			if(tmp.length < 2)
				tmp[1] = tmp[0];
		
			array[i] = tmp;
		}
	}
	
	return array;
}


function setReadOnly(myform,fieldName){
	var field = myform.findField(fieldName);	
	field.getEl().dom.readOnly = true;	
	field.getEl().dom.style['background'] = "#F5F5F5";

	if(field.getXTypes() == 'component/box/field/textfield/trigger/combo'){
		field.hideTrigger = true;
		field.on('expand', function(){field.collapse()});
	}
}
function disReadOnly(myform,fieldName){
	var field = myform.findField(fieldName);	
	field.getEl().dom.readOnly = false;	
	field.getEl().dom.style['background'] = "FFFFFF";

	if(field.getXTypes() == 'component/box/field/textfield/trigger/combo'){
		field.hideTrigger = false;
		field.on('expand', function(){});
	}
}

function checkSingleMobile(mobilePhoneNumber){
	var checkNumberStr
	if(mobilePhoneNumber.length==11){
		checkNumberStr = /[1][3,5,8][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else if(mobilePhoneNumber.length==13){
		checkNumberStr = /[1][0][6][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else if(mobilePhoneNumber.length==14){
		checkNumberStr = /[1][0][6][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else if(mobilePhoneNumber.length==15){
		checkNumberStr = /[1][0][6][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}

	var r = mobilePhoneNumber.match(checkNumberStr);
	
	if(r==null||r=="") {		
		return false;
	}
	
	return true;
}


function checkMobile(mobilePhoneNumber,field,isAllowBlank){
	field.clearInvalid();	
	if(typeof(isAllowBlank)=='undefined') isAllowBlank = false;	 
	
	if(isAllowBlank)
		if(mobilePhoneNumber=="") return true; 
	
	var checkNumberStr
	if(mobilePhoneNumber.length==11){
		if(mobilePhoneNumber.charAt(0)=='1')
			checkNumberStr = /[1][3,5,8][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
		else
			checkNumberStr = /[0][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else if(mobilePhoneNumber.length==10){
		checkNumberStr = /[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else if(mobilePhoneNumber.length==12){
		checkNumberStr = /[0][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$/;
	}else{
	field.markInvalid("\u8f93\u5165\u7684\u624b\u673a\u53f7\u6216\u5c0f\u7075\u901a\u53f7\u9519\u8bef");			
	}

	var r = mobilePhoneNumber.match(checkNumberStr);
	
	if(r==null||r=="") {	
	field.markInvalid("\u8f93\u5165\u7684\u624b\u673a\u53f7\u6216\u5c0f\u7075\u901a\u53f7\u9519\u8bef");			
		return false;
	}
	
	return true;
}



//-------------------------------------------------------

if (Ext.form.NumberField) {
	Ext.override(Ext.form.NumberField, {		
		style :'text-align:right',
		setValue : function(v){
			v = parseFloat(v);
			v = isNaN(v) ? '' : String(v).replace(".", this.decimalSeparator);

			if (String(v).length > 0)  
				v = parseFloat(v).toFixed(this.decimalPrecision);  

			Ext.form.NumberField.superclass.setValue.call(this, v);
		}
	});
}

function onRenderNumberColum(v,decimalPrecision){	
	
	v = parseFloat(v);	

	if (String(v).length > 0)  
		v = parseFloat(v).toFixed(decimalPrecision);  

	return v;
}

function windowOpen(url){
	if(getBrowseType()=="MSIE"){
		var link = document.getElementById("link");
		link.href = url;
		link.click();
	}else{
		var Sw = screen.availwidth+5;
		var Sh = screen.availHeight;
		var z = window.open(url,'_blank','resizable=yes,status=yes,scrollbars=yes,left=0,top=0');
		z.resizeTo(Sw,Sh);
	}
	
}
 
function showHelpInfoWindow(code){
	var helpInfoWindow;
	var dataviewStore=new Ext.data.Store({
		url:'/energycheck/helpInfo!getContext.action?code=' + code, 
		reader: new Ext.data.JsonReader({ 
			totalProperty: 'totalCount', 
			root: 'data', 
			id:'id',
			successProperty:'success'  
		},[ 
			{name: 'id',mapping:'id',type:'int'},
			{name: 'title',mapping:'title',type:'string'},
			{name: 'code',mapping:'code',type:'string'},
			{name: 'context',mapping:'context',type:'string'}
		])		
	});   

	dataviewStore.load({params: {start:0, limit:10000}, callback: function(r, options, success){ 
		if(success){ 
			helpInfoWindow.setTitle(dataviewStore.getAt(0).get("title"));
		//	$("span:contains(aaa)").text(dataviewStore.getAt(0).get("title"));		
		}
	}});

	var tpl = new Ext.XTemplate(
		'<tpl for=".">',
			'<title>{caidantitle}</title>',
			'<div style="height:250px;width:450px;WIDTH:100%;OVERFLOW:auto;">{context}</div>',
		'</tpl>'
	);
		
	var dataView=new Ext.DataView({
		store: dataviewStore,
		tpl: tpl,
		style:'background-color:FFFFFF;',
		itemSelector:'div.message-subject',
		prepareData: function(data){    
			return data;
		}
	})	

	if(!helpInfoWindow){
		helpInfoWindow = new Ext.Window({		
			layout:'fit',
			width:500,
			height:300,
			closeAction:'hide',
			plain: true,
			buttonAlign:'center',
			items:dataView,

			buttons: [{
				text: '\u5173\u95ed',
				handler: function(){
					helpInfoWindow.hide();
				}
			}]
		});
	}	

	helpInfoWindow.show();
}

function getRecordFromStoreByUniqueproperty(store, field, value){
	for(var i = 0; i < store.getCount(); i++){
		if(store.getAt(i).get(field) == value){
		return store.getAt(i)
		}
	}
}
function getBrowseType(){ 
  
   if(navigator.userAgent.indexOf("MSIE")>0) { 
        return "MSIE"; 
   } 
   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
        return "Firefox"; 
   } 
   if(isSafari=navigator.userAgent.indexOf("Safari")>0) { 
        return "Safari"; 
   }  
   if(isCamino=navigator.userAgent.indexOf("Camino")>0){ 
        return "Camino"; 
   } 
   if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){ 
        return "Gecko"; 
   } 

   return "";
   
} 