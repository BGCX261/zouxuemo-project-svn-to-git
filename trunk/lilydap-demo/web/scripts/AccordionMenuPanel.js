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
    * url���ӵĻ���·��.
    */
	basePath: '',
    /**
    * @cfg {String} accordionUrl
    * ��ȡ�˵�accordion��URL���ӣ����ӷ���JSON���ݸ�ʽ���£�
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
    * ��ȡ�˵���ĳ��accordion�Ĳ˵�����URL���ӣ����Ӻ���Ӳ���"id=xxx"
    * ���ӷ���JSON���ݸ�ʽ���£�
    * 	[{
    * 		text: "����ά��", id: "a1", leaf" :false, expanded: true, icon: "folder", children:[{
    * 			{"text" : "����ά��", "id" : "a11", "leaf" : true, "icon" : "file","link" : "system/organize/department.do"},
    * 			...
    * 			{"text" : "��λά��", "id" : "a12", "leaf" : true, "icon" : "file","link" : "system/organize/post.do"}
    * 		}]
    * 	}, ... ,{
    * 		text: "����ά��", id: "a1", leaf" :false, expanded: true, icon: "folder", children:[{
    * 			{"text" : "����ά��", "id" : "a11", "leaf" : true, "icon" : "file","link" : "system/organize/department.do"},
    * 			...
    * 			{"text" : "��λά��", "id" : "a12", "leaf" : true, "icon" : "file","link" : "system/organize/post.do"}
    * 		}]
    * 	}]
    */
	menuUrl: '',
    /**
    * @cfg {String} frameId
    * �˵���linkˢ�µ�IFrameԪ��IDֵ.
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