package com.lily.dap.mojo;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.lily.dap.tool.JarPathUtils;

/**
 * 从Jar文件抽取web资源文件到要打包的Web项目中
 *
 * @author zouxuemo
 * @goal unpack-web-resource
 * @phase prepare-package
 * @requiresDependencyResolution runtime
 */
public class UnpackWebResourceFromJarsMojo extends AbstractMojo {
	/**
	 * The maven project.
	 *
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * 要抽取web资源的jar包的前缀名，默认是所有的jar包
	 *
	 * @parameter expression=""
	 */
	private String jarPrefix;

	/**
	 * 要抽取web资源在jar包中的根路径，默认是web
	 *
	 * @parameter expression="web"
	 */
	private String webRoot;

    /**
     * The directory where the webapp is built.
     *
     * @parameter expression="${project.build.directory}/${project.build.finalName}"
     * @required
     */
    private File webappDirectory;

	public void execute() throws MojoExecutionException, MojoFailureException {
		Set artifacts = project.getArtifacts();

		for (Iterator iterator = artifacts.iterator(); iterator.hasNext();) {
			Artifact artifact = (Artifact) iterator.next();
			
//			System.out.println( "Storing: maven.dependency." + artifact.getGroupId() + "." + artifact.getArtifactId() + "." + artifact.getType() + ".path=" + artifact.getFile().getPath() ); 

			if ("jar".equals(artifact.getType()) && ("".equals(jarPrefix) || artifact.getArtifactId().startsWith(jarPrefix))) {
				getLog().info("unpack web resource from jar " + artifact.getGroupId() + "." + artifact.getArtifactId() + "-" + artifact.getVersion() + "." + artifact.getType());
				
				try {
					JarPathUtils.unpackJarWebResourceIfNewer(artifact.getFile(), webRoot, webappDirectory);
				} catch (IOException e) {
					throw new MojoExecutionException("I/O error while unpack web resource from jar file.", e);
				}
			}
		}
	}
}
