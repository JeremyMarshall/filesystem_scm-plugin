package hudson.plugins.filesystem_scm;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import hudson.FilePath.FileCallable;
import hudson.remoting.VirtualChannel;
import org.jenkinsci.remoting.RoleChecker;
import jenkins.security.Roles;

public class RemoteCopyDir implements FileCallable<Boolean> {

	private static final long serialVersionUID = 1L; 

	private String sourceDir;
	
	public RemoteCopyDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

    public void checkRoles(RoleChecker checker) throws SecurityException {
        checker.check(this, Roles.MASTER);
    }
	public Boolean invoke(File workspace, VirtualChannel channel) throws IOException {
		FileUtils.copyDirectory(new File(sourceDir), workspace);
		return Boolean.TRUE;
	}
}
