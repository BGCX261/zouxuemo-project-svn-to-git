package com.lily.dap.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**   
 * ResourceUtils ��Դ��ȡ������
 * 	�ӵ�ǰclasspath�¶�ȡָ���ļ������ļ�
 * 	�ӵ�ǰclasspath�¶�ȡָ���ļ�����ָ��Ŀ¼��ĳ����չ�����ļ�����
 *   
 * @author zouxuemo
 */
public class ResourceUtils {
	/**
	 * 
	 * @param resource
	 * @param fileExtName
	 * @return
	 */
	public static List<String> listFilePathFromResource(String resource, String fileExtName) {
		List<String> filePaths = new ArrayList<String>();
		
		fileExtName = "." + fileExtName.toLowerCase();
		
        if (StringUtils.endsWithIgnoreCase(resource, fileExtName)) {	//��Դ����ָ��classpath��ĳ����չ�����ļ�
            InputStream is = getInputStream(resource);
            if (is != null)
            	filePaths.add(resource);
    	} else {	//��Դ����ָ��classpath��ĳ��Ŀ¼������xml�ļ�
    		URL url = getResource(resource);
            if (url == null) 
                return filePaths;
            
            String path = url.getFile();
            try {
				path = URLDecoder.decode(path, "UTF-8");
			} catch (UnsupportedEncodingException e) {}
            
    		File file = new File(path);
    		if (!file.isDirectory())
    			return filePaths;
    		
    		//��������Ŀ¼�µ�����xml�ļ�
			File[] xmlFiles = file.listFiles(new MyFileFilter(fileExtName));
			
			//ѭ����xml�ļ��ж�������������Ϣ
			for (File xmlFile : xmlFiles)
				filePaths.add(resource + xmlFile.getName());
			
    	}
        
        return filePaths;
	}

    public static URL getResource(String name) {
        if (StringUtils.isBlank(name))
        	return null;
        	
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = null; 

        if (name.indexOf(":/") > -1) {
            try {
            	url = new URL(name);
            } catch (Exception e) {}
        }
        
        if (url == null) {
            try {
            	url = classLoader.getResource(name);
            } catch (Exception e) {}
        }

        if (url == null) {
            try {
            	url = classLoader.getResource('/' + name);
            } catch (Exception e) {}
        }

        if (url == null) {
            try {
            	url = classLoader.getResource("META-INF/" + name);
            } catch (Exception e) {}
        }
        	
        return url;
    }
    
    public static InputStream getInputStream(String name) {
        if (StringUtils.isBlank(name))
        	return null;

        InputStream is = null;

        URL url = getResource(name);
        if (url != null)
			try {
				is = url.openStream();
			} catch (IOException e1) {}
		else {
        	try {
				is = new FileInputStream(name);
			} catch (FileNotFoundException e) {}
        }

        return is;
    }
}


class MyFileFilter implements FileFilter {
	private String fileExtName;
	
	public MyFileFilter(String fileExtName) {
		this.fileExtName = fileExtName;
	}
	
	public boolean accept(File pathname) {
		return StringUtils.endsWithIgnoreCase(pathname.getName(), fileExtName);
	}
	
}
