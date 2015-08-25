package com.lily.dap.util.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Ignore;

import com.lily.dap.util.file.FileUtil;

@Ignore
public class ZipUtilTest extends TestCase {
	public void testZip() {
		try {
			System.out
					.println("压缩目录[F:/JAVA_HOME/apache-maven-2.2.1]至文件[apache-maven-2.2.1.zip]");
			System.out.println("-------------------------------");

			ZipUtil.zip("F:/JAVA_HOME/apache-maven-2.2.1", "D:/",
					"apache-maven-2.2.1.zip", true, true, new ZipingFilter() {
						public boolean filter(String pathname,
								boolean isDirectory) {
							if (pathname.endsWith("_desktop.ini"))
								return false;

							if (isDirectory)
								System.out.print("<DIR> ");
							else
								System.out.print("      ");

							System.out.println(pathname);

							return true;
						}
					});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testUnZip() {
		try {
			System.out
					.println("解压缩文件[apache-maven-2.2.1.zip]至目录[F:/JAVA_HOME/apache-maven-2.2.1_unzip]");
			System.out.println("---------------------------------------");

			FileUtil.createDirectory("F:/JAVA_HOME/apache-maven-2.2.1_unzip");

			FileInputStream fis = new FileInputStream(
					"D:/apache-maven-2.2.1.zip");

			ZipUtil.unZip(fis, "F:/JAVA_HOME/apache-maven-2.2.1_unzip",
					new ZipingFilter() {
						public boolean filter(String pathname,
								boolean isDirectory) {
							if (isDirectory)
								System.out.print("<DIR> ");
							else
								System.out.print("      ");

							System.out.println(pathname);

							return true;
						}

					});

			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileUtil.removeDirectory("F:/JAVA_HOME/apache-maven-2.2.1_unzip", true);

		new File("D:/apache-maven-2.2.1.zip").delete();
	}
}
