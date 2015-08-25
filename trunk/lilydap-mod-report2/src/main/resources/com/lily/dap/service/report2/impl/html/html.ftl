<style>
${style}
</style>

<table<#if table.cssClass?? && table.cssClass != ''> class="${table.cssClass}"</#if> align="center">
    <colgroup>
<#list table.widths as width>
    <col width="${width?c}"/>
</#list>    
    </colgroup>
<#list table.data as row>
    <tr height="${(row.height)?c}"<#if row.cssClass?? && row.cssClass != ''> class="${row.cssClass}"</#if> style="min-height:${(row.height)?c}px; overflow:auto;">
    <#list row.data as cell>
      <td<#if cell.rowSpan != 1> rowSpan="${cell.rowSpan}"</#if>
      	 <#if cell.colSpan != 1> colSpan="${cell.colSpan}"</#if>
      	 <#if cell.cssClass?? && cell.cssClass != ''> class="${cell.cssClass}"</#if>>
      	 <#if cell.chart??>
      	 	<img src="report2Query.do?method=chart&table=${table.uid}&uid=${cell.chart.uid}" width="${(cell.width*cell.chart.width/100)?c}" height="${(cell.height*cell.chart.height/100)?c}" />
      	 <#else>
      	 	<#if cell.html>
      	 		${cell.context?html?replace(" ", "&nbsp;")?replace("\n", "<br>")}
      	 	<#else>
      	 		${cell.context}
      	 	</#if>
      	 </#if></td>
    </#list> 
    </tr>
</#list> 
</table>
<#if script?? && script != ''>
<script type="text/javascript">
${script}
</script>
</#if>