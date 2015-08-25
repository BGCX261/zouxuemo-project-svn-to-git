package com.lily.dap.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarPathUtils {
	public static void unpackJarWebResourceIfNewer(File jarFile, String jarWebRootDirectory, File targetDirectory) throws IOException {
		jarWebRootDirectory = jarWebRootDirectory.replace('\\', '/');
		if (jarWebRootDirectory.charAt(jarWebRootDirectory.length() - 1) != '/')
			jarWebRootDirectory += '/' ;
		
		int jarWebRootDirectoryLength = jarWebRootDirectory.length();
		String targetDirectoryFilePath = targetDirectory.getAbsolutePath();
		
		JarFile jar = new JarFile(jarFile);
		Enumeration<JarEntry> enumeration = jar.entries(); 
		while (enumeration.hasMoreElements()) {
			JarEntry entry = enumeration.nextElement();
			String name = entry.getName(); 
			
			if (name.startsWith(jarWebRootDirectory) && name.length() != jarWebRootDirectoryLength) {
				String subName = name.substring(jarWebRootDirectoryLength);
				long lastModifiedTime = entry.getTime();
			
				if (entry.isDirectory()) {
					File f = new File(targetDirectoryFilePath + File.separator + subName.substring(0, subName.length() - 1));
					f.mkdir();
					f.setLastModified(lastModifiedTime);
				} else {
					int pos = subName.lastIndexOf('/');
					
					String fileName = subName;
					if (pos != -1) {
						createDirectory(targetDirectoryFilePath, subName.substring(0,
								subName.lastIndexOf("/")));

						targetDirectoryFilePath = targetDirectoryFilePath.substring(
								targetDirectoryFilePath.lastIndexOf("/") + 1, targetDirectoryFilePath.length());
						
						fileName = subName.substring(pos + 1);
					}

					int pos2 = fileName.indexOf("$$");
					if (pos2 != -1) {
						String fragment = fileName.substring(0, pos2);
						String mergerFileName = fileName.substring(pos2 + 2);
						
						// TODO 考虑如何实现文件合并的解决方案
					} else {
						File f = new File(targetDirectoryFilePath + File.separator
								+ subName);
						
						if (f.exists() && lastModifiedTime <= f.lastModified())
							continue;
						
						f.createNewFile();

						InputStream in = jar.getInputStream(entry);
						FileOutputStream out = new FileOutputStream(f);

						byte[] by = new byte[1024];
						int c;
						while ((c = in.read(by)) != -1)
							out.write(by, 0, c);

						out.close();
						in.close();
						
						f.setLastModified(lastModifiedTime);
					}
				}
			}
		}
	}

	/**
	 * 从指定目录下创建子目录
	 * 
	 * @param directory
	 *            需创建文件目录的根路径,要求是现实存在目录，例如：D:\\casemanage 或者 D:/casemanage
	 * @param subDirectory
	 *            要创建的目录，例如：j2sdk1.4.2_09\\include\\win32
	 */
	private static boolean createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if ("".equals(subDirectory) && fl.exists() != true)
				if (!fl.mkdir())
					return false;
				else if (!"".equals(subDirectory)) {
					dir = subDirectory.replace('\\', '/').split("/");

					StringBuffer buf = new StringBuffer(directory);
					for (int i = 0; i < dir.length; i++) {
						buf.append(File.separator).append(dir[i]);

						File subFile = new File(buf.toString());
						if (subFile.exists() == false)
							if (!subFile.mkdir())
								return false;
					}
				}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

			return false;
		}

		return true;
	}
	
	public static void main(String[] args) throws IOException {
		File jarFile = new File("F:\\JAVA_HOME\\Workspaces.0\\lilydap3\\lilydap-mod-system\\target\\lilydap-mod-system-3.0.jar");
		String jarWebRootDirectory = "web";
		File targetDirectory = new File("D:\\TEMP");
		
		JarPathUtils.unpackJarWebResourceIfNewer(jarFile, jarWebRootDirectory, targetDirectory);
	}
}
