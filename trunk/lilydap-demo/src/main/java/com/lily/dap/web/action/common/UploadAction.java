package com.lily.dap.web.action.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

public class UploadAction extends BaseAction {
	/**
	 * <code>serialVersionUID</code> 描述字段名称。
	 */
	private static final long serialVersionUID = 7764670015381090518L;
	
	private final static int MAX_BUFFER_LENGTH = 64*1024;	//文件处理的缓冲区最大值

	public String upload()throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		
		MultiPartRequestWrapper multiPartRequest = (MultiPartRequestWrapper) request;
		File[] files = multiPartRequest.getFiles("upload");
		String[] fileNames = multiPartRequest.getFileNames("upload");
		
		for (int i = 0; i < fileNames.length; i++) {
			File file =  files[i];
			if (!file.exists())
				continue;
			
			String fileName = fileNames[i];
			InputStream is = new FileInputStream(file);
			
			OutputStream os = null;
	        byte[] buffer = new byte[MAX_BUFFER_LENGTH];
			try {
				os = new FileOutputStream("D:\\temp\\" + fileName);
		        int bytesRead = 0;
		        
		        while ((bytesRead = is.read(buffer, 0, MAX_BUFFER_LENGTH)) != -1) {
		            os.write(buffer, 0, bytesRead);
		            os.flush();
		        }
			}catch (Exception e) {
				Struts2Utils.renderJson(JsonHelper.combinFailJsonString(e));
				return null;
			} finally {
				buffer = null;
				
				try {
					if (os != null)
						os.close();
				} catch (Exception e){}
			}
		}
		
		Struts2Utils.renderJson(JsonHelper.combinSuccessJsonString());
		return null;
	}
}
