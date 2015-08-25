/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.mojo;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.FileUtils;

/**
 * <code>CopyWarsPackageMojo</code>
 * <p>复制从War中分离出的JAR文件到${target}\${artifactId}\WEB-INF\lib目录下</p>
 *
 * @author 邹学模
 * @date 2008-11-10
 * 
 * @goal copy-jar
 * @phase prepare-package
 * @requiresDependencyResolution runtime
 */
public class CopyWarsPackageMojo extends AbstractMojo {
    public static final String LIB_PATH = "WEB-INF/lib/";

	  /**
	   * Location of the warpath working directory. The contents of the WEB-INF/classes directory
	   * from all warpath dependencies will be extracted to jar files located in this directory.
	   *
	   * @parameter expression="${project.build.directory}/warpath"
	   * @required
	   */
	  private File workDirectory;

	    /**
	     * The directory where the webapp is built.
	     *
	     * @parameter expression="${project.build.directory}/${project.build.finalName}"
	     * @required
	     */
	    private File webappDirectory;

	/* （非 Javadoc）
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
    	File tgtDirectory = new File(webappDirectory, LIB_PATH);
    	
    	getLog().debug("copy " + workDirectory.getPath() + "'s file to " + tgtDirectory.getPath() + ".");
		try {
			FileUtils.copyDirectory(workDirectory, tgtDirectory);
		} catch (IOException e) {
			throw new MojoExecutionException("copy " + workDirectory.getPath() + "'s file to " + tgtDirectory.getPath() + " error.", e);
		}
	}
	
    protected boolean copyFile(File source,
			File destination, String targetFilename, boolean onlyIfModified)
			throws IOException {
		if (onlyIfModified && destination.lastModified() >= source.lastModified()) {
			getLog().debug(" * " + targetFilename + " is up to date.");
			return false;
		} else {
			FileUtils.copyFile(source.getCanonicalFile(), destination);
			// preserve timestamp
			destination.setLastModified(source.lastModified());
			getLog().debug(" + " + targetFilename + " has been copied.");
			return true;
		}
	}
}
