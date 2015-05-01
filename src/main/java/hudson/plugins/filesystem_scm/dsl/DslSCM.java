package hudson.plugins.filesystem_scm.dsl;

import hudson.Extension;
import hudson.model.Describable;
import hudson.plugins.filesystem_scm.FSSCM;
import hudson.plugins.filesystem_scm.Wildcard;
import hudson.scm.SCM;
import javaposse.jobdsl.dsl.DslExtensionMethod;
import javaposse.jobdsl.dsl.helpers.ScmContext;
import javaposse.jobdsl.plugin.ContextExtensionPoint;

import java.util.Collections;

/**
 * Created by jeremymarshall on 1/01/2015.
 */

@Extension(optional = true)
public class DslSCM extends ContextExtensionPoint{

    @DslExtensionMethod(context = ScmContext.class)
    public Object fsscm(String path) {
        return fsscm(path, null);
    }

    @DslExtensionMethod(context = ScmContext.class)
    public Object fsscm(String path,
                        String localPath) {
        Describable<SCM> ret =  new FSSCM(path, localPath, false, false, false, null, Collections.<Wildcard>emptyList());
        return ret;
    }

    @DslExtensionMethod(context = ScmContext.class)
    public Object fsscm(Runnable closure){
        DslSCMContext context = new DslSCMContext();
        executeInContext(closure, context);

        return new FSSCM(context.getPath(),
                context.getLocalPath(),
                context.getClearWorkspace(),
                context.getCopyHidden(),
                context.getFilterEnabled(),
                context.getFilterType(),
                context.getWildcards());
    }

}