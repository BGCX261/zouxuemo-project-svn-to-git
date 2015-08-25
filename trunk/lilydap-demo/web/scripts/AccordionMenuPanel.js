AccordionMenuPanel = function(config) {
    Ext.apply(this, config);
	
	AccordionMenuPanel.superclass.constructor.call(this, {
		layout:"fit",
		border : false
	});
};

Ext.extend(AccordionMenuPanel, Ext.Panel, {
    /**
    * @cfg {String} basePath
    * url链接的基本路径.
    */
	basePath: '',
    /**
    * @cfg {String} accordionUrl
    * 获取菜单accordion的URL链接，链接返回JSON数据格式如下：
    * 	[{
    * 		id: xxx,
    * 		text: 'xxx',
    * 		icon: 'xxx'
    * 	}, ... ,{
    * 		id: xxx,
    * 		text: 'xxx',
    * 		icon: 'xxx'
    *	}] 
    */
	accordionUrl: '',
    /**
    * @cfg {String} menuUrl
    * 获取菜单中某个accordion的菜单树的URL链接，链接后将添加参数"id=xxx"
    * 链接返回JSON数据格式如下：
    * 	[{
    * 		text: "机构维护", id: "a1", leaf" :false, expanded: true, icon: "folder", children:[{
    * 			{"text" : "部门维护", "id" : "a11", "leaf" : true, "icon" : "file","link" : "system/organize/department.do"},
    * 			...
    * 			{"text" : "岗位维护", "id" : "a12", "leaf" : true, "icon" : "file","link" : "system/organize/post.do"}
    * 		}]
    * 	}, ... ,{
    * 		text: "机构维护", id: "a1", leaf" :false, expanded: true, icon: "folder", children:[{
    * 			{"text" : "部门维护", "id" : "a11", "leaf" : true, "icon" : "file","link" : "system/organize/department.do"},
    * 			...
    * 			{"text" : "岗位维护", "id" : "a12", "leaf" : true, "icon" : "file","link" : "system/organize/post.do"}
    * 		}]
    * 	}]
    */
	menuUrl: '',
    /**
    * @cfg {String} frameId
    * 菜单中link刷新的IFrame元素ID值.
    */
	frameId: '',
	
	initComponent : function(){
		Ext.Ajax.request({method:'POST',
			url: this.basePath + this.accordionUrl, 
			success: this.onCreateAccordion.createDelegate(this)
		});  
		
        AccordionMenuPanel.superclass.initComponent.call(this);
	},
	
	onCreateAccordion : function(response) {
		var accordionMenuConfigs = Ext.decode(response.responseText);
		
		var items = [];
		for (var index = 0; index < accordionMenuConfigs.length; index++) {
			var accordionMenuConfig = accordionMenuConfigs[index];
			
			var menuTree = new Ext.tree.TreePanel({
				id: accordionMenuConfig.id,
				autoScroll : true,
				animate : true,
				border : false,
				rootVisible : false,
				root : new Ext.tree.AsyncTreeNode( {
					id : accordionMenuConfig.id + '_root',
					draggable : false,
					expanded : true
				}),
				loader: new Ext.tree.TreeLoader({
					baseParams: {id: accordionMenuConfig.id},
					dataUrl:this.basePath + this.menuUrl
				})
			});
			
			menuTree.on('click', this.onMenuTreeNodeClick, this);
			
			items.push({
				title: accordionMenuConfig.text,
				layout: 'fit',
				items: menuTree
			});
		}
		
		this.add(new Ext.Panel({
			layout:"accordion",
			border : false,
			layoutConfig:{
			  activeOnTop:false,
			  animate:true,
			  autoWidth:true,
			  collapseFirst:true,
			  fill:true,
			  hideCollapseTool:false,
			  titleCollapse:true
			},
			items: items
		}));
		
		this.doLayout();
	},
	
	onMenuTreeNodeClick : function(node) {
		if (node.attributes.javascript)	{
			eval(node.attributes.javascript)
		} else if (node.attributes.link){
			Ext.get(this.frameId).dom.src = encodeURI(node.attributes.link);        
		}
	}
});