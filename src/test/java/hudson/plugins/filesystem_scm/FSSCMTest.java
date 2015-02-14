package hudson.plugins.filesystem_scm;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.tasks.Shell;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class FSSCMTest {
    @Rule public JenkinsRule j = new JenkinsRule();
    @Test public void first() throws Exception {

        FreeStyleProject project = j.createFreeStyleProject();

        FSSCM scm = new FSSCM( "/Users/jeremymarshall/src/tools", "", false, false, false, null, Collections.<Wildcard>emptyList());
        project.setScm(scm);

        project.getBuildersList().add(new Shell("echo hello"));
        FreeStyleBuild build = project.scheduleBuild2(0).get();
        System.out.println(build.getDisplayName() + " completed");
        // TODO: change this to use HtmlUnit
        String s = FileUtils.readFileToString(build.getLogFile());
        assertThat(s, containsString("+ echo hello"));
    }
}

