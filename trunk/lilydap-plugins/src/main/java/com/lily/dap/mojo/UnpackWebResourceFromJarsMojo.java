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
 * ��Jar�ļ���ȡweb��Դ�ļ���Ҫ�����Web��Ŀ��
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
	 * Ҫ��ȡweb��Դ��jar����ǰ׺����Ĭ�������е�jar��
	 *
	 * @parameter expression=""
	 */
	private String jarPrefix;

	/**
	 * Ҫ��ȡweb��Դ��jar���еĸ�·����Ĭ����web
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
